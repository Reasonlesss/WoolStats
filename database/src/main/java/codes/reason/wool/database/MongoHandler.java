package codes.reason.wool.database;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.concurrent.CompletableFuture;

public class MongoHandler {


    private final MongoClient client;

    public MongoHandler(String url) {
        this.client = MongoClients.create("mongodb://127.0.0.1:27017");
    }

    public CompletableFuture<FindIterable<Document>> getDocuments(String collectionName, String key, Object value) {
        CompletableFuture<FindIterable<Document>> future = new CompletableFuture<>();
        WoolData.EXECUTOR_SERVICE.submit(() -> {
            try {
                MongoDatabase mongoDatabase = this.client.getDatabase("test");
                MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

                future.complete(collection.find(Filters.eq(key, value)));
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
        return future;
    }

    public CompletableFuture<Document> getSingleDocument(String collectionName, String key, Object value) {
        CompletableFuture<Document> future = new CompletableFuture<>();
        getDocuments(collectionName, key, value).thenAccept(iterable -> {
            try(MongoCursor<Document> iterator = iterable.limit(1).iterator()) {
                if (iterator.hasNext()) {
                    future.complete(iterator.next());
                } else {
                    future.complete(null);
                }
            }
        });
        return future;
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
