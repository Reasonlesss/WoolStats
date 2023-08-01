package codes.reason.wool.database.serializers;
import codes.reason.wool.api.statistics.StatisticCategory;
import codes.reason.wool.api.statistics.StatisticType;
import codes.reason.wool.api.statistics.Statistics;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class StatisticsSerializer implements Serializer<Map<StatisticCategory, Statistics>> {

    public static StatisticsSerializer INSTANCE = new StatisticsSerializer();

    @Override
    public Map<StatisticCategory, Statistics> fromDocument(Document document) {
        try {
            Map<StatisticCategory, Statistics> categories = new HashMap<>();

            for (StatisticCategory category : StatisticCategory.values()) {
                Document categoryDoc = document.get(category.toString(), Document.class);
                Map<StatisticType, Statistics.Statistic> statisticMap = new HashMap<>();

                for (StatisticType type : StatisticType.values()) {
                    if (categoryDoc.containsKey(type.toString())) {
                        Document statDocument = categoryDoc.get(type.toString(), Document.class);
                        if (statDocument.get("value") instanceof Number value) {
                            statisticMap.put(type, new Statistics.Statistic(
                                    value,
                                    statDocument.getInteger("position")
                            ));
                        }
                    }
                }

                categories.put(category, new Statistics(statisticMap));
            }

            return categories;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Document toDocument(Map<StatisticCategory, Statistics> playerStats) {
        Document document = new Document();

        for (StatisticCategory category : StatisticCategory.values()) {
            Document categoryDocument = new Document();

            Statistics statistics = playerStats.get(category);
            for (StatisticType type : StatisticType.values()) {
                Statistics.Statistic statistic = statistics.getStat(type);
                if (statistic != null) {
                    if (statistic.getValue().doubleValue() % 1 == 0) {
                        categoryDocument.append(type.toString(), new Document()
                                .append("value", statistic.getValue().longValue())
                                .append("position", statistic.getPosition())
                        );
                    }else {
                        categoryDocument.append(type.toString(), new Document()
                                .append("value", statistic.getValue().doubleValue())
                                .append("position", statistic.getPosition())
                        );
                    }

                }
            }

            document.append(category.toString(), categoryDocument);
        }

        return document;
    }
}
