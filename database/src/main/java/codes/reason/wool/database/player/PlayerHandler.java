package codes.reason.wool.database.player;

import codes.reason.wool.api.WoolWarsAPI;
import codes.reason.wool.api.response.PlayerResponse;
import codes.reason.wool.api.statistics.StatisticCategory;
import codes.reason.wool.api.statistics.StatisticType;
import codes.reason.wool.common.Common;
import codes.reason.wool.common.TextColor;
import codes.reason.wool.common.WebUtil;
import codes.reason.wool.database.WoolData;
import codes.reason.wool.database.serializers.PlayerSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import net.hypixel.api.reply.PlayerReply;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerHandler {

    /**
     * Players should only be cached in MongoDB for 15 minutes
     */
    public static final Long CACHE_TIME = 900_000L;

    public static CompletableFuture<Player> getPlayer(String name) {
        CompletableFuture<Player> future = new CompletableFuture<>();

        Common.EXECUTOR_SERVICE.submit(() -> {
            try {
                String content = WebUtil.getContent("https://playerdb.co/api/player/minecraft/" + name);
                getPlayer(UUID.fromString(JsonParser.parseString(content).getAsJsonObject().getAsJsonObject("data")
                        .getAsJsonObject("player").get("id").getAsString())).thenAccept(future::complete);
            } catch (IOException e) {
                future.completeExceptionally(e);
            }
        });

        return future;

    }

    public static CompletableFuture<Player> getPlayer(UUID uuid) {
        CompletableFuture<Player> future = new CompletableFuture<>();

        Common.EXECUTOR_SERVICE.submit(() -> {
            Document document = WoolData.MONGO_HANDLER.getDatabase()
                    .getCollection("player_data")
                    .find(Filters.eq("uuid", String.valueOf(uuid)))
                    .first();
            if (document == null ||
                    System.currentTimeMillis() > document.getLong("lastUpdated") + CACHE_TIME) {
                fetchUpToDatePlayer(uuid).thenAccept(future::complete);
            }else {
                future.complete(PlayerSerializer.INSTANCE.fromDocument(document));
            }
        });


        return future;
    }

    public static void savePlayer(Player player) {
        Document document = PlayerSerializer.INSTANCE.toDocument(player);
        String uuid = String.valueOf(player.getUuid());
        WoolData.MONGO_HANDLER.getDatabase()
                .getCollection("player_data")
                .replaceOne(Filters.eq("uuid", uuid), document, new ReplaceOptions().upsert(true));
    }

    private static CompletableFuture<Player> fetchUpToDatePlayer(UUID uuid) {
        CompletableFuture<Player> future = new CompletableFuture<>();
        WoolWarsAPI.getPlayer(uuid).thenAccept(response -> {
            WoolData.HYPIXEL_API.getPlayerByUuid(uuid).thenAccept(hypixelReply -> {
                Player player = new Player(
                        response.getIgn(),
                        response.getUuid(),
                        response.getGeneralInfo().getStar(),
                        hypixelReply.getPlayer().getNetworkLevel(),
                        response.getGeneralInfo().getExperience(),
                        calculatePlaytime(hypixelReply, response),
                        response.getGeneralInfo().getWool(),
                        response.getGeneralInfo().getLayers(),
                        new RankData(
                                hypixelReply.getPlayer().getHighestRank(),
                                hypixelReply.getPlayer().getStringProperty("prefix", null),
                                TextColor.valueOf(hypixelReply.getPlayer().getSuperstarTagColor()),
                                TextColor.valueOf(hypixelReply.getPlayer().getSelectedPlusColor())
                        ),
                        response.getStatistics(),
                        response.getGeneralInfo().getWinstreak(),
                        response.getGeneralInfo().getHighestWinstreak(),
                        System.currentTimeMillis(),
                        getLayouts(hypixelReply),
                        response.getGeneralInfo().getRank()
                );

                savePlayer(player);
                future.complete(player);
            }).exceptionally(e -> {
                e.printStackTrace();
                return null;
            });
        });
        return future;
    }

    private static final long WEEKLY_QUEST_CHANGE = 1677110400000L;

    private static long calculatePlaytime(PlayerReply hypixelReply, PlayerResponse response) {
        int dailyWins = 0, weeklyShears = 0, weeklyShearsPost = 0, weeklyPlay = 0;

        if (hypixelReply.getPlayer().hasProperty("quests")) {
            JsonObject quests = hypixelReply.getPlayer().getObjectProperty("quests");

            if (quests.has("wool_wars_daily_wins") ) {
                JsonObject jsonObject = quests.getAsJsonObject("wool_wars_daily_wins");
                if (jsonObject.has("completions")) {
                    dailyWins = jsonObject.getAsJsonArray("completions").size();
                }
            }

            if (quests.has("wool_wars_weekly_shears")) {
                JsonObject jsonObject = quests.getAsJsonObject("wool_wars_weekly_shears");
                if (jsonObject.has("completions")) {
                    JsonArray jsonElements = jsonObject.getAsJsonArray("completions");
                    for (JsonElement jsonElement : jsonElements) {
                        JsonObject object = jsonElement.getAsJsonObject();
                        if (object.get("time").getAsLong() > WEEKLY_QUEST_CHANGE) {
                            weeklyShearsPost++;
                        } else {
                            weeklyShears++;
                        }
                    }
                }
            }

            if (quests.has("wool_weekly_play")) {
                JsonObject jsonObject = quests.getAsJsonObject("wool_weekly_play");
                if (jsonObject.has("completions")) {
                    weeklyPlay = jsonObject.getAsJsonArray("completions").size();
                }
            }
        }

        double questXP = (dailyWins * 400) + (weeklyPlay * 3000) + (weeklyShears * 300) + (weeklyShearsPost * 3000);
        double exp = response.getGeneralInfo().getExperience();
        long wins = response.getStatistics(StatisticCategory.OVERALL).getStat(StatisticType.WINS).getValue().longValue();
        double trueExp = exp - (25 * wins) - questXP;
        return (long) Math.floor(trueExp * 2329.43);
    }

    private static final Map<StatisticCategory, Map<Integer, String>> DEFAULT_LAYOUTS = Map.of(
            StatisticCategory.TANK, makeLayout("WOOD_SWORD", "WOOD_PICKAXE", "SHEARS", "WOOL"),
            StatisticCategory.ASSAULT, makeLayout("WOOD_SWORD", "SHEARS", "IRON_PICKAXE", "STONE_SPADE",
                    "POTION_16396", "POTION_16421", "WOOL"),
            StatisticCategory.ARCHER, makeLayout("BOW", "WOOD_PICKAXE", "WOOD_AXE", "SHEARS", "ARROW", "WOOL"),
            StatisticCategory.SWORDSMAN, makeLayout("STONE_SWORD", "WOOD_PICKAXE", "SHEARS", "POTION_16389", "WOOL"),
            StatisticCategory.GOLEM, makeLayout("STONE_SWORD", "WOOL"),
            StatisticCategory.ENGINEER, makeLayout("WOOD_SWORD", "BOW", "ARROW", "SHEARS", "STONE_PICKAXE",
                    "POTION_16387", "WOOL")
    );

    private static Map<Integer, String> makeLayout(String... strings) {
        Map<Integer, String> layout = new HashMap<>();
        int i = 0;
        for (String string : strings) {
            layout.put(i, string);
            i++;
        }
        layout.put(8, "KEYSTONE");
        return layout;
    }

    private static Map<StatisticCategory, List<String>> getLayouts(PlayerReply reply) {
        Map<StatisticCategory, List<String>> layouts = new HashMap<>();

        JsonObject apiLayouts = reply.getPlayer().getObjectProperty("stats")
                .getAsJsonObject("WoolGames")
                .getAsJsonObject("wool_wars")
                .getAsJsonObject("layouts");


        for (StatisticCategory category : StatisticCategory.values()) {
            if (category == StatisticCategory.OVERALL) continue;

            List<String> layout = new ArrayList<>();
            if (apiLayouts != null && apiLayouts.has(category.name().toLowerCase())) {
                JsonObject apiLayout = apiLayouts.getAsJsonObject(category.name().toLowerCase());
                for (int i = 0; i < 9; i++) {
                    if (apiLayout.has(String.valueOf(i))) {
                        layout.add(apiLayout.get(String.valueOf(i)).getAsString());
                    } else {
                        layout.add(null);
                    }
                }
                layouts.put(category, layout);
            }else {
                for (int i = 0; i < 9; i++) {
                    layout.add(DEFAULT_LAYOUTS.get(category).get(i));
                }
            }
            layouts.put(category, layout);
        }

        return layouts;
    }



}
