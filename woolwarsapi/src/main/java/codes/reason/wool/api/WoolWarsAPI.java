package codes.reason.wool.api;

import codes.reason.wool.api.statistics.StatisticCategory;
import codes.reason.wool.api.statistics.StatisticType;
import codes.reason.wool.common.WebUtil;
import codes.reason.wool.api.response.LeaderboardResponse;
import codes.reason.wool.api.response.PlayerResponse;
import codes.reason.wool.api.serializers.LeaderboardSerializer;
import codes.reason.wool.api.serializers.StatisticTypeSerializer;
import codes.reason.wool.api.serializers.StatisticsSerializer;
import codes.reason.wool.api.statistics.Leaderboard;
import codes.reason.wool.api.response.APIResponse;
import codes.reason.wool.api.serializers.StatisticCategorySerializer;
import codes.reason.wool.api.statistics.Statistics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * API wrapper for the woolwars.net API
 */
public class WoolWarsAPI {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(StatisticCategory.class, new StatisticCategorySerializer())
            .registerTypeAdapter(StatisticType.class, new StatisticTypeSerializer())
            .registerTypeAdapter(Statistics.class, new StatisticsSerializer())
            .registerTypeAdapter(Leaderboard.class, new LeaderboardSerializer())
            .create();
    public static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(15);
    private static final String API_URL = "http://woolwars.net/";

    public static CompletableFuture<PlayerResponse> getPlayer(String name) {
        return getFromAPI("player", Map.of(
            "name", name,
            "ranks", true
        ), PlayerResponse.class);
    }

    public static CompletableFuture<PlayerResponse> getPlayer(UUID uuid) {
        return getFromAPI("player", Map.of(
                "uuid", uuid,
                "ranks", true
        ), PlayerResponse.class);
    }

    public static CompletableFuture<LeaderboardResponse> getLeaderboards() {
        return getFromAPI("leaderboard/detailed", Collections.emptyMap(), LeaderboardResponse.class);
    }

    private static <R extends APIResponse> CompletableFuture<R> getFromAPI(String endpoint, Map<String, Object> parameters, Class<R> rClass) {
        final CompletableFuture<R> future = new CompletableFuture<>();

        String url = API_URL + endpoint + buildParameters(parameters);

        EXECUTOR_SERVICE.submit(() -> {
            try {
                JsonObject object = JsonParser.parseString(WebUtil.getContent(url)).getAsJsonObject();

                if (!object.get("success").getAsBoolean()) {
                    future.completeExceptionally(new APIErrorException(object.get("error").getAsString()));
                    return;
                }


                future.complete(GSON.fromJson(object, rClass));

            } catch (IOException e) {
                e.printStackTrace();
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    private static String buildParameters(Map<String, Object> parameters) {
        if (parameters != null) {
            StringBuilder params = new StringBuilder();
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                params.append(params.length() == 0 ? "?" : "&");
                params.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());
            }
            return params.toString();
        }
        return "";
    }

}
