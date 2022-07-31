package codes.reason.wool.api.response;

import codes.reason.wool.api.statistics.Leaderboard;
import codes.reason.wool.api.statistics.StatisticCategory;
import codes.reason.wool.api.statistics.StatisticType;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class LeaderboardResponse extends APIResponse {

    @SerializedName("general")
    private GeneralLeaderboards generalLeaderboards;
    private Map<StatisticCategory, Map<StatisticType, Leaderboard>> stats;

    public GeneralLeaderboards getGeneralLeaderboards() {
        return generalLeaderboards;
    }

    public Leaderboard getStatisticLeaderboard(StatisticCategory category, StatisticType type) {
        return stats.get(category).get(type);
    }

    public static class GeneralLeaderboards {
        private Leaderboard experience;
        private Leaderboard winstreak;
        @SerializedName("highest_winstreak")
        private Leaderboard highestWinstreak;

        public Leaderboard getExperience() {
            return experience;
        }

        public Leaderboard getWinstreak() {
            return winstreak;
        }

        public Leaderboard getHighestWinstreak() {
            return highestWinstreak;
        }
    }

}
