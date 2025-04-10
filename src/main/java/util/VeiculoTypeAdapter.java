package util;

import com.google.gson.*;
import model.Veiculo;
import model.Carro;
import model.Moto;
import model.Caminhao;

import java.lang.reflect.Type;

public class VeiculoTypeAdapter implements JsonDeserializer<Veiculo>, JsonSerializer<Veiculo> {

    @Override
    public Veiculo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Verifica se existe um campo "tipo" no JSON
        if (!jsonObject.has("tipo") || jsonObject.get("tipo").isJsonNull()) {
            throw new JsonParseException("Campo 'tipo' ausente no JSON do veículo.");
        }

        String tipo = jsonObject.get("tipo").getAsString();

        switch (tipo) {
            case "Carro":
                return context.deserialize(json, Carro.class);
            case "Moto":
                return context.deserialize(json, Moto.class);
            case "Caminhão":
                return context.deserialize(json, Caminhao.class);
            default:
                throw new JsonParseException("Tipo de veículo desconhecido: " + tipo);
        }
    }

    @Override
    public JsonElement serialize(Veiculo src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = context.serialize(src).getAsJsonObject();
        jsonObject.addProperty("tipo", src.getTipo()); // Adiciona o tipo ao JSON
        return jsonObject;
    }
}
