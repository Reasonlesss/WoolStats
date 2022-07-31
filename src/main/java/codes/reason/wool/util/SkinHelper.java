package codes.reason.wool.util;

import java.util.List;
import java.util.UUID;

public class SkinHelper {

    private static final List<UUID> CLOWN_STATUS = List.of(
            UUID.fromString("02b2284f-62d0-48ee-ae4b-b07693043b46") // 3fi is a clown
    );

    public static String getFaceURL(UUID uuid) {
        if (CLOWN_STATUS.contains(uuid)) {
            return "https://media.discordapp.net/attachments/980049744569204740/981493346168086548/unknown.png";
        } else {
            return String.format("https://crafthead.net/avatar/%s/16.png", uuid);
        }
    }

}
