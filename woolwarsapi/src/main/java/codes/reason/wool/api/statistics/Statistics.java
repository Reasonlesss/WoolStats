package codes.reason.wool.api.statistics;

import java.util.Map;

public class Statistics {

    private final Map<StatisticType, Statistic> stats;

    public Statistics(Map<StatisticType, Statistic> stats) {
        this.stats = stats;
    }

    public Statistic getStat(StatisticType type) {
        return stats.get(type);
    }

    public static class Statistic {

        private final Number value;
        private final int position;

        public Statistic(Number value, int position) {
            this.value = value;
            this.position = position;
        }

        public Number getValue() {
            return value;
        }

        public int getPosition() {
            return position;
        }

    }

    @Override
    public String toString() {
        return "Statistics{" +
                "stats=" + stats +
                '}';
    }
}
