package codes.reason.wool.util;

import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class NumberFormatter {

    public static Map<Long, String> SHORTENED = new LinkedHashMap<>();

    static {
        SHORTENED.put(1_000_000_000L, "b");
        SHORTENED.put(1_000_000L, "m");
        SHORTENED.put(1_000L, "k");
    }

    public static String shortenNumber(double number) {
        for (Long aLong : SHORTENED.keySet()) {
            if (number >= aLong) {
                double value = number / aLong;
                value = Math.floor(value * 10) / 10;
                String formatted = value + SHORTENED.get(aLong);
                return formatted.replace(".0", "");
            }
        }
        return formatNumber(number);
    }

    public static String formatNumber(Number number) {
        return NumberFormat.getInstance(Locale.US).format(number);
    }

    public static String formatTime(long time) {
        int hours = (int) Math.floor(time / 3600000d);
        int minutes = (int) Math.floor((time % 3600000d) / 60000);
        return hours + "h " + minutes + "m";
    }

}
