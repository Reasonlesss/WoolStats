package codes.reason.wool.database.analytics;

import java.util.Map;

public record CommandStats(Map<String, Integer> stats, int total) {
}
