package codes.reason.wool.database.serializers;

import codes.reason.wool.common.TextColor;
import codes.reason.wool.database.player.Player;
import codes.reason.wool.common.PrestigeIcon;
import codes.reason.wool.database.player.RankData;
import org.bson.Document;

import java.util.UUID;

public class PlayerSerializer implements Serializer<Player> {

    public static PlayerSerializer INSTANCE = new PlayerSerializer();

    @Override
    public Player fromDocument(Document document) {
        Document rankData = document.get("rank", Document.class);
        try {
            return new Player(
                    document.getString("username"),
                    UUID.fromString(document.getString("uuid")),
                    document.getInteger("star"),
                    document.getDouble("networkLevel"),
                    document.getDouble("experience"),
                    document.getLong("playtime"),
                    document.getLong("wool"),
                    document.getInteger("layers"),
                    new RankData(
                            rankData.getString("rank"),
                            rankData.getString("prefix"),
                            TextColor.valueOf(rankData.getString("color")),
                            TextColor.valueOf(rankData.getString("plusColor"))
                    ),
                    StatisticsSerializer.INSTANCE.fromDocument(document.get("stats", Document.class)),
                    document.getInteger("winstreak"),
                    document.getInteger("highestWinstreak"),
                    document.getLong("lastUpdated"),
                    LayoutSerializer.INSTANCE.fromDocument(document.get("layouts", Document.class)),
                    document.getLong("experience_rank"),
                    PrestigeIcon.valueOf(document.getString("icon")));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Document toDocument(Player value) {
        RankData rankData = value.getRankData();
        return new Document("uuid", value.getUuid().toString())
                .append("username", value.getUsername())
                .append("star", value.getStar())
                .append("networkLevel", value.getNetworkLevel())
                .append("experience", value.getExperience())
                .append("playtime", value.getPlaytime())
                .append("wool", value.getWool())
                .append("layers", value.getLayers())
                .append("rank", new Document()
                        .append("rank", rankData.getRank())
                        .append("prefix", rankData.getPrefix())
                        .append("color", rankData.getColor())
                        .append("plusColor", rankData.getPlusColor()))
                .append("stats", StatisticsSerializer.INSTANCE.toDocument(value.getStats()))
                .append("winstreak", value.getWinstreak())
                .append("highestWinstreak", value.getMaxWinstreak())
                .append("lastUpdated", System.currentTimeMillis())
                .append("layouts", LayoutSerializer.INSTANCE.toDocument(value.getLayouts()))
                .append("experience_rank", value.getRank())
                .append("icon", value.getIcon().toString())
                ;
    }
}
