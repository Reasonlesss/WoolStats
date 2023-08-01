package codes.reason.wool.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoHandler {


    private final MongoClient client;

    private final String database;

    public MongoHandler() {
        this.database = System.getenv("MONGO_DATABASE");

        MongoClientSettings.Builder settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(System.getenv("MONGO_URL")));

        if (System.getenv("DEV") == null) {
            MongoCredential credential = MongoCredential.createCredential(
                    System.getenv("MONGO_USERNAME"),
                    this.database,
                    System.getenv("MONGO_PASSWORD").toCharArray());

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
