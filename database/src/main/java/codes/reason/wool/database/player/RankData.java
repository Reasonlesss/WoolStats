package codes.reason.wool.database.player;

import codes.reason.wool.common.TextColor;

public class RankData {

    private final String rank;
    private final String prefix;
    private final TextColor color;
    private final TextColor plusColor;

    public RankData(String rank, String prefix, TextColor color, TextColor plusColor) {
        this.rank = rank;
        this.prefix = prefix;
        this.color = color;
        this.plusColor = plusColor;
    }

    public String getRank() {
        return rank;
    }

    public TextColor getColor() {
        return color;
    }

    public TextColor getPlusColor() {
        return plusColor;
    }

    public String getPrefix() {
        return prefix;
    }
}
