package util;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ISO_LOCAL_DATE; // Formato "yyyy-MM-dd"

    @Override
    public JsonElement serialize(LocalDate data, Type tipoOrigem, JsonSerializationContext contexto) {
        return new JsonPrimitive(data.format(FORMATO_DATA)); // Converte LocalDate para String no JSON
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type tipoDestino, JsonDeserializationContext contexto) throws JsonParseException {
        return LocalDate.parse(json.getAsString(), FORMATO_DATA); // Converte String do JSON para LocalDate
    }
}
