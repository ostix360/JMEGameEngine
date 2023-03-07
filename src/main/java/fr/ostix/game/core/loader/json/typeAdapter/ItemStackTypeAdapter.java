package fr.ostix.game.core.loader.json.typeAdapter;

import java.lang.reflect.Type;

import com.google.gson.*;

import fr.ostix.game.items.ItemStack;
import fr.ostix.game.items.Items;

public class ItemStackTypeAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        JsonObject item = new JsonObject();
        item.addProperty("id", src.getItem().getId());
        item.addProperty("name", src.getItem().getName());
        obj.add("item", item);
        obj.addProperty("count", src.getCount());
        return obj;
    }

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject itemObject = json.getAsJsonObject();
        int id = itemObject.get("item").getAsJsonObject().get("id").getAsInt();
        int count = itemObject.get("count").getAsInt();
        return new ItemStack(Items.getItem(id), count);
    }
}
