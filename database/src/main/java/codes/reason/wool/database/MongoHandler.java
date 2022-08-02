package codes.reason.wool.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import javax.print.Doc;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class MongoHandler {


    private final MongoClient client;

    private final String database;

    public MongoHandler() {
        this.database = System.getenv("MONGO_DATABASE");

        MongoClientSettings.Builder settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(System.getenv("MONGO_URL")));

        if (!System.getenv("DEV").equals("true")) {
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

    public FindIterable<Document> getDocuments(String collectionName, String key, Object value) {
        MongoDatabase mongoDatabase = getDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        return collection.find(Filters.eq(key, value));
    }

    public Document getSingleDocument(String collectionName, String key, Object value) {
        try (MongoCursor<Document> it = getDocuments(collectionName, key, value).limit(1).iterator()) {
            if (it.hasNext()) return it.next();
        }
        return null;
    }

    public void updateOrInsertDocument(String collectionName, String key, Object value, Document document) {
        WoolData.EXECUTOR_SERVICE.submit(() -> {
            MongoDatabase mongoDatabase = getDatabase();
            MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

            Document filter = new Document(key, value);
            Document update = new Document("$set", document);
            collection.updateOne(filter, update, new UpdateOptions().upsert(true));
        });
    }




}
