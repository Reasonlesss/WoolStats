package codes.reason.wool.common;

import java.util.UUID;

public class SkinHelper {

    public static String getFaceURL(UUID uuid) {
        return String.format("https://crafthead.net/avatar/%s/16.png", uuid);
    }

}
