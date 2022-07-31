package codes.reason.wool.api.serializers;

import codes.reason.wool.api.statistics.Statistics;
import codes.reason.wool.api.statistics.StatisticType;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class StatisticsSerializer implements JsonDeserializer<Statistics> {

    @Override
    public Statistics deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        Map<StatisticType, Statistics.Statistic> stats = new HashMap<>();

        for (StatisticType type : StatisticType.values()) {
            String typeLower = type.name().toLowerCase();

            if (!jsonObject.has(typeLower)) continue;

            Number amount = jsonObject.get(typeLower).getAsNumber();

            Statistics.Statistic statistic;
            if (jsonObject.has(typeLower + "_rank")) {
                int position = jsonObject.get(typeLower + "_rank").getAsInt();
                statistic = new Statistics.Statistic(amount, position);
            }else {
                statistic = new Statistics.Statistic(amount, 0);
            }

            stats.put(type, statistic);
        }

        return new Statistics(stats);
    }

}
