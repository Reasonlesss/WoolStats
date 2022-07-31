package codes.reason.wool.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.apache.ApacheHttpClient;
import net.hypixel.api.http.HypixelHttpClient;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WoolData {

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(15);

    public static MongoHandler MONGO_HANDLER = new MongoHandler("");
    public static Gson GSON = new GsonBuilder().create();
    public static HypixelAPI HYPIXEL_API;

    static {
        HypixelHttpClient client = new ApacheHttpClient(UUID.fromString("d6192a62-5ce7-40ec-bb30-851906c57b5c"));
        HYPIXEL_API = new HypixelAPI(client);
    }

}
