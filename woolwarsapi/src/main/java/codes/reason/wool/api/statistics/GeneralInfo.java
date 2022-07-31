package codes.reason.wool.api.statistics;

import com.google.gson.annotations.SerializedName;

public class GeneralInfo {

    @SerializedName("available_layers")
    private int layers;
    private float experience;
    @SerializedName("experience_rank")
    private long rank;
    @SerializedName("coins")
    private long wool;
    private int star;
    @SerializedName("selected_class")
    private StatisticCategory selectedClass;

    private int winstreak;
    @SerializedName("highest_winstreak")
    private int highestWinstreak;

    public int getLayers() {
        return layers;
    }

    public float getExperience() {
        return experience;
    }

    public long getWool() {
        return wool;
    }

    public int getStar() {
        return star;
    }

    public StatisticCategory getSelectedClass() {
        return selectedClass;
    }

    public int getWinstreak() {
        return winstreak;
    }

    public int getHighestWinstreak() {
        return highestWinstreak;
    }

    public long getRank() {
        return rank;
    }
}
