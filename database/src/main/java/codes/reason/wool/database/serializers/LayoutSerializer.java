package codes.reason.wool.database.serializers;

import codes.reason.wool.api.statistics.StatisticCategory;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayoutSerializer implements Serializer<Map<StatisticCategory, List<String>>> {

    public static LayoutSerializer INSTANCE = new LayoutSerializer();

    @Override
    public Map<StatisticCategory, List<String>> fromDocument(Document document) {
        Map<StatisticCategory, List<String>> layouts = new HashMap<>();

        for (StatisticCategory category : StatisticCategory.values()) {
            if (category == StatisticCategory.OVERALL) continue;

            List<?> list = document.get(category.toString(), List.class);
            List<String> layout = new ArrayList<>();

            for (Object o : list) {
                if (o instanceof String string) {
                    layout.add(string);
                }else {
                    layout.add(null);
                }
            }

            layouts.put(category, layout);

        }
        return layouts;
    }

    @Override
    public Document toDocument(Map<StatisticCategory, List<String>> statisticCategoryListMap) {
        Document document = new Document();
        for (StatisticCategory category : StatisticCategory.values()) {
            if (category == StatisticCategory.OVERALL) continue;
            document.append(category.toString(), statisticCategoryListMap.get(category));
        }
        return document;
    }
}
