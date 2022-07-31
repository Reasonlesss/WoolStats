package codes.reason.wool.util;

public class StatUtil {

    /*
        1 -> 2 = 1k (1k total)
        2 -> 3 = 2k (3k total)
        3 -> 4 = 3k (6k total)
        4 -> 5 = 4k (10k total)
        5 -> 6 = 5k (15k total)

        any higher is 5k
     */
    public static int getProgress(int level, int experience) {
        return switch (level % 100) {
            case 1 -> experience % 1000;
            case 2 -> (experience - 1000) % 2000;
            case 3 -> (experience - 3000) % 3000;
            case 4 -> (experience - 6000) % 4000;
            default -> experience % 5000;
        };
    }

    public static int getStar(int experience) {
        int prestige = experience / 485000;
        int remain = experience % 485000;

        int level;

        if (remain < 1000) level = 1;
        else if (remain < 3000) level = 2;
        else if (remain < 6000) level = 3;
        else if (remain < 10_000) level = 4;
        else level = remain / 5000 + 3;

        return prestige * 100 + level;
    }

    public static int getRemaining(int level, int experience) {
        return getRequired(level) - getProgress(level, experience);
    }

    public static int getRequired(int level) {
        return Math.min(level, 5) * 1000;
    }

}
