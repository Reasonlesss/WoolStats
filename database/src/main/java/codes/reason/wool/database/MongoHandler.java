package codes.reason.wool.database;

import codes.reason.wool.common.Config;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoHandler {

    //


    private final MongoClient client;

    private final String database;

    public MongoHandler() {
        this.database = Config.getString("MONGO_DATABASE");

        MongoClientSettings.Builder settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(Config.getString("MONGO_URL")));

        if (Config.getString("MONGO_USERNAME") == null) {
            MongoCredential credential = MongoCredential.createCredential(
                    Config.getString("MONGO_USERNAME"),
                    this.database,
                    Config.getString("MONGO_PASSWORD").toCharArray());

            settings.credential(credential);
        }

        this.client = MongoClients.create(settings.build());
    }

    public MongoClient getClient() {
        return client;
    }

    public MongoDatabase getDatabase() {
        return this.getClient().getDatabase(this.database);
    }

}
