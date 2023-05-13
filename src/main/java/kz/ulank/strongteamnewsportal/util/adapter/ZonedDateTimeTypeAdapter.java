package kz.ulank.strongteamnewsportal.util.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Ulan on 5/13/2023
 */
public class ZonedDateTimeTypeAdapter implements JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss z");

    @Override
    public JsonElement serialize(ZonedDateTime zonedDateTime, Type srcType,
                                 JsonSerializationContext context) {

        return new JsonPrimitive(formatter.format(zonedDateTime));
    }

    @Override
    public ZonedDateTime deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {

        return ZonedDateTime.parse(json.getAsString(), formatter);
    }
}