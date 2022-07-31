package codes.reason.wool.api.serializers;

import codes.reason.wool.api.statistics.Leaderboard;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LeaderboardSerializer implements JsonDeserializer<Leaderboard> {
    @Override
    public Leaderboard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray array = json.getAsJsonArray();

            List<Leaderboard.LeaderboardEntry> entries = new ArrayList<>();
            for (JsonElement element : array) {

                JsonObject object = element.getAsJsonObject();
                entries.add(new Leaderboard.LeaderboardEntry(
                        UUID.fromString(object.get("uuid").getAsString()),
                        object.get("ign").getAsString(),
                        object.get("value").getAsLong()
                ));

            }

            return new Leaderboard(entries);

    }
}
