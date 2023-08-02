package codes.reason.wool.database;

import codes.reason.wool.common.Config;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.apache.ApacheHttpClient;
import net.hypixel.api.http.HypixelHttpClient;

public class WoolData {

    public static MongoHandler MONGO_HANDLER = new MongoHandler();
    public static HypixelAPI HYPIXEL_API;

    static {
        HypixelHttpClient client = new ApacheHttpClient(Config.getUUID("HYPIXEL_API"));
        HYPIXEL_API = new HypixelAPI(client);
    }

}
