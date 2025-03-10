package util;

import com.google.gson.*;
import model.Administrador;
import model.Atendente;
import model.Gerente;
import model.Usuario;

import java.lang.reflect.Type;

public class UsuarioTypeAdapter implements JsonDeserializer<Usuario>, JsonSerializer<Usuario> {

    @Override
    public Usuario deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String tipo = jsonObject.get("tipo").getAsString(); // Identifica a subclasse correta

        switch (tipo) {
            case "Administrador":
                return context.deserialize(json, Administrador.class);
            case "Gerente":
                return context.deserialize(json, Gerente.class);
            case "Atendente":
                return context.deserialize(json, Atendente.class);
            default:
                throw new JsonParseException("Tipo de usuário desconhecido: " + tipo);
        }
    }

    @Override
    public JsonElement serialize(Usuario src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = context.serialize(src).getAsJsonObject();
        jsonObject.addProperty("tipo", src.getTipo()); // Adiciona o tipo ao JSON
        return jsonObject;
    }
}
