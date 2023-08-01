package codes.reason.wool.database.analytics;

import codes.reason.wool.common.Common;
import codes.reason.wool.database.WoolData;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AnalyticsHandler {

    private static final FindOneAndUpdateOptions OPTIONS = new FindOneAndUpdateOptions()
            .upsert(true);

    private static final long DAY = 86400000L;

    public static void incrementCommandStat(String command) {
        Common.EXECUTOR_SERVICE.submit(() -> {
            MongoDatabase database = WoolData.MONGO_HANDLER.getDatabase();
            MongoCollection<Document> commandStats = database.getCollection("commands");
            commandStats.findOneAndUpdate(Filters.empty(), Updates.inc(command, 1), OPTIONS);
        });
    }

    public static CompletableFuture<CommandStats> getCommandStats() {
        return CompletableFuture.supplyAsync(() -> {
            MongoDatabase database = WoolData.MONGO_HANDLER.getDatabase();

            Document document = database.getCollection("commands")
                    .find()
                    .first();

            Map<String, Integer> stats = new HashMap<>();

            if (document == null) {
                return new CommandStats(new HashMap<>(), 0);
            }

            int total = 0;
            for (String key : document.keySet()) {
                int amount = document.getInteger(key, 0);
                stats.put(key, amount);
                total += amount;
            }

            return new CommandStats(stats, total);
        });
    }

    public static void updateDailyStats(int guilds) {
        Common.EXECUTOR_SERVICE.submit(() -> {
            MongoDatabase database = WoolData.MONGO_HANDLER.getDatabase();
            MongoCollection<Document> dailyStats = database.getCollection("daily_stats");

            Document document = dailyStats.find()
                    .limit(1)
                    .sort(Sorts.descending("timestamp"))
                    .first();

            if (document != null) {
                if (document.getLong("timestamp") + DAY > System.currentTimeMillis()) {
                    return;
                }
            }

            getCommandStats().thenAccept(stats -> {
                dailyStats.insertOne(new Document("timestamp", System.currentTimeMillis())
                        .append("guilds", guilds)
                        .append("commands", stats.total()));
            });
        });
    }

    public static CompletableFuture<List<DailyStats>> getDailyStats() {
        return CompletableFuture.supplyAsync(() -> {
            MongoDatabase database = WoolData.MONGO_HANDLER.getDatabase();
            MongoCollection<Document> dailyStats = database.getCollection("daily_stats");

            FindIterable<Document> documents = dailyStats.find()
                    .sort(Sorts.descending("timestamp"))
                    .limit(10);

            List<DailyStats> stats = new ArrayList<>();
            for (Document document : documents) {
                stats.add(new DailyStats(document.getInteger("commands"), document.getInteger("guilds")));
            }
            Collections.reverse(stats);
            return stats;
        }, Common.EXECUTOR_SERVICE);
    }

}
