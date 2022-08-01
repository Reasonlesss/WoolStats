package codes.reason.wool.database;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import javax.print.Doc;
import java.util.concurrent.CompletableFuture;

public class MongoHandler {


    private final MongoClient client;

    public MongoHandler(String url) {
        this.client = MongoClients.create("mongodb://127.0.0.1:27017");
    }

    public MongoClient getClient() {
        return client;
    }

    public FindIterable<Document> getDocuments(String collectionName, String key, Object value) {
        MongoDatabase mongoDatabase = this.client.getDatabase("test");
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
            MongoDatabase mongoDatabase = this.client.getDatabase("test");
            MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

            Document filter = new Document(key, value);
            Document update = new Document("$set", document);
            collection.updateOne(filter, update, new UpdateOptions().upsert(true));
        });
    }




}
