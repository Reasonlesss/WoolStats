package codes.reason.wool.api.response;

import codes.reason.wool.api.statistics.GeneralInfo;
import codes.reason.wool.api.statistics.StatisticCategory;
import codes.reason.wool.api.statistics.Statistics;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class PlayerResponse extends APIResponse {

    private UUID uuid;
    private String ign;

    private Map<StatisticCategory, Map<Integer, String>> layouts;

    @SerializedName("stats")
    private Map<StatisticCategory, Statistics> statistics;

    @SerializedName("general")
    private GeneralInfo generalInfo;

    private List<Long> sessions;

    public String getIgn() {
        return ign;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Map<Integer, String> getLayout(StatisticCategory category) {
        if (category == StatisticCategory.OVERALL) {
            throw new IllegalArgumentException("You can not have an OVERALL layout.");
        }
        return layouts.get(category);
    }

    public Statistics getStatistics(StatisticCategory category) {
        return statistics.get(category);
    }

    public Map<StatisticCategory, Statistics> getStatistics() {
        return statistics;
    }

    public GeneralInfo getGeneralInfo() {
        return generalInfo;
    }
}
