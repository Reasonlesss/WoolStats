package codes.reason.wool.database;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.apache.ApacheHttpClient;
import net.hypixel.api.http.HypixelHttpClient;

import java.util.UUID;

public class WoolData {

    public static MongoHandler MONGO_HANDLER = new MongoHandler();
    public static HypixelAPI HYPIXEL_API;

    static {
        HypixelHttpClient client = new ApacheHttpClient(UUID.fromString(System.getenv("HYPIXEL_API")));
        HYPIXEL_API = new HypixelAPI(client);
    }

}
