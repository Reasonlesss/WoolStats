package codes.reason.wool.api.serializers;

import codes.reason.wool.api.statistics.StatisticCategory;
import com.google.gson.*;

import java.lang.reflect.Type;

public class StatisticCategorySerializer implements JsonDeserializer<StatisticCategory> {

    @Override
    public StatisticCategory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return StatisticCategory.valueOf(json.getAsString().toUpperCase());
    }
}
