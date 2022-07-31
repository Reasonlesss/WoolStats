package codes.reason.wool.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class TimeUtil {

    public static Map<Long, String> SHORTENED = new LinkedHashMap<>();

    static {
        SHORTENED.put(21600000L, "hour");
        SHORTENED.put(60000L, "minute");
        SHORTENED.put(1000L, "second");
    }

    public static String toRelativeTime(long time) {
        long relative = System.currentTimeMillis() - time;
        for (Long aLong : SHORTENED.keySet()) {
            if (relative > aLong) {
                long value = relative / aLong;

                if (value > 1) {
                    return value + " " + SHORTENED.get(aLong) + "s ago";
                }

                return value + " " + SHORTENED.get(aLong) + " ago";
            }
        }
        return "Just now";
    }

}
