package codes.reason.wool.util;

import codes.reason.wool.WoolStats;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MongoHelper {

    public static CompletableFuture<Map<String, Integer>> getCommandStats(String... commands) {
        CompletableFuture<Map<String, Integer>> future = new CompletableFuture<>();
        WoolStats.INSTANCE.getExecutorService().submit(() -> {
            MongoClient mongoClient = WoolStats.INSTANCE.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> commandStats = database.getCollection("commands");

            Map<String, Integer> stats = new HashMap<>();
            int total = 0;
            for (String command : commands) {
                Document document = commandStats.find(new Document("name", command)).first();
                if (document == null) {
                    stats.put(command, 0);
                    continue;
                }
                int amount = document.get("times_ran", 0);
                total+=amount;
                stats.put(command, amount);
            }
            stats.put("total", total);
            future.complete(stats);
        });
        return future;
    }


    public static void incrementCommandStat(String command) {
        WoolStats.INSTANCE.getExecutorService().submit(() -> {
            MongoClient mongoClient = WoolStats.INSTANCE.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> commandStats = database.getCollection("commands");

            commandStats.findOneAndUpdate(Filters.eq("name", command),
                    new Document("$inc", new Document("times_ran", 1)),
                    new FindOneAndUpdateOptions().upsert(true));

            updateDailyStats();
        });
    }

    private static final long DAY = 86400000L;

    public static void updateDailyStats() {
        WoolStats.INSTANCE.getExecutorService().submit(() -> {
            MongoClient mongoClient = WoolStats.INSTANCE.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> dailyStats = database.getCollection("daily_stats");

            Document document = dailyStats.find().limit(1).sort(new Document("$natural", -1)).first();

            if (document != null) {
                if (document.get("timestamp", Long.class) + DAY > System.currentTimeMillis()) {
                    return;
                }
            }

            String[] commands = WoolStats.INSTANCE.getCommandManager().getCommandList().toArray(new String[]{});

            getCommandStats(commands).thenAccept(stats -> {
                dailyStats.insertOne(new Document("timestamp", System.currentTimeMillis())
                        .append("guilds", WoolStats.INSTANCE.getGuilds())
                        .append("commands", stats.get("total")));
            });
        });

    }

    // change this
    public static CompletableFuture<List<Map<String, Integer>>> getDailyStats() {
        CompletableFuture<List<Map<String, Integer>>> future = new CompletableFuture<>();

        WoolStats.INSTANCE.getExecutorService().submit(() -> {
            MongoClient mongoClient = WoolStats.INSTANCE.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> dailyStats = database.getCollection("daily_stats");

            FindIterable<Document> documents = dailyStats.find()
                    .sort(Sorts.ascending("timestamp"))
                    .limit(10);

            List<Map<String, Integer>> stats = new ArrayList<>();
            for (Document document : documents) {
                stats.add(Map.of(
                        "guilds", document.getInteger("guilds"),
                        "commands", document.getInteger("commands")
                ));
            }
            future.complete(stats);
        });

        return future;
    }

}
