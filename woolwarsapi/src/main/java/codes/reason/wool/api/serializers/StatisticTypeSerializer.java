package codes.reason.wool.api.serializers;

import codes.reason.wool.api.statistics.StatisticType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class StatisticTypeSerializer implements JsonDeserializer<StatisticType> {

    @Override
    public StatisticType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return StatisticType.valueOf(json.getAsString().toUpperCase());
    }
}
