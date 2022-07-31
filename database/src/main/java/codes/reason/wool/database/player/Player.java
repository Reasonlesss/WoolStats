package codes.reason.wool.database.player;

import codes.reason.wool.api.statistics.StatisticCategory;
import codes.reason.wool.api.statistics.Statistics;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Player {

    private final UUID uuid;

    private final String username;
    private final double networkLevel;
    private final long playtime;

    private final double experience;
    private final long rank;
    private final int star;
    private final long wool;
    private final int layers;

    private final RankData rankData;
    private final Map<StatisticCategory, Statistics> stats;
    private final Map<StatisticCategory, List<String>> layouts;

    private final long cacheTime;

    private final int winstreak;
    private final int maxWinstreak;

    public Player(String username, UUID uuid, int star, double networkLevel, double experience, long playtime, long wool,
                  int layers, RankData rankData, Map<StatisticCategory, Statistics> playerStats, int winstreak,
                  int maxWinstreak, long cacheTime, Map<StatisticCategory, List<String>> layouts, long rank) {
        this.username = username;
        this.uuid = uuid;
        this.star = star;
        this.networkLevel = networkLevel;
        this.experience = experience;
        this.playtime = playtime;
        this.wool = wool;
        this.layers = layers;
        this.rankData = rankData;
        this.stats = playerStats;
        this.winstreak = winstreak;
        this.maxWinstreak = maxWinstreak;
        this.cacheTime = cacheTime;
        this.layouts = layouts;
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getStar() {
        return star;
    }

    public double getNetworkLevel() {
        return networkLevel;
    }

    public double getExperience() {
        return experience;
    }

    public long getWool() {
        return wool;
    }

    public int getLayers() {
        return layers;
    }

    public RankData getRankData() {
        return rankData;
    }

    public Map<StatisticCategory, Statistics> getStats() {
        return stats;
    }

    public Map<StatisticCategory, List<String>> getLayouts() {
        return layouts;
    }

    public int getWinstreak() {
        return winstreak;
    }

    public int getMaxWinstreak() {
        return maxWinstreak;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public long getPlaytime() {
        return playtime;
    }

    public long getRank() {
        return rank;
    }
}
