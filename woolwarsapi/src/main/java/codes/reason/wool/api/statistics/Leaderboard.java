package codes.reason.wool.api.statistics;

import java.util.List;
import java.util.UUID;

public class Leaderboard {

    private final List<LeaderboardEntry> leaderboard;

    public Leaderboard(List<LeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public List<LeaderboardEntry> getEntries() {
        return leaderboard;
    }

    public LeaderboardEntry getEntry(int position) {
        return leaderboard.get(position);
    }

    public static class LeaderboardEntry {
        private final UUID uuid;
        private final String ign;
        private final Number value;

        public LeaderboardEntry(UUID uuid, String ign, Number value) {
            this.uuid = uuid;
            this.ign = ign;
            this.value = value;
        }

        public UUID getUuid() {
            return uuid;
        }

        public String getIgn() {
            return ign;
        }

        public Number getValue() {
            return value;
        }
    }

}
