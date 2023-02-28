package fr.ostix.game.core.loader.json.typeAdapter;

import com.google.gson.*;
import fr.ostix.game.inventory.*;
import fr.ostix.game.items.*;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryTypeAdapter implements JsonSerializer<Inventory>, JsonDeserializer<Inventory> {

    @Override
    public JsonElement serialize(Inventory src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        JsonArray itemsArray = new JsonArray();
        for (ItemStack itemStack : src.getItems()) {
            JsonObject itemObject = new JsonObject();
            itemObject.addProperty("id", itemStack.getItem().getId());
            itemObject.addProperty("count", itemStack.getCount());
            itemsArray.add(itemObject);
        }
        json.add("items", itemsArray);

        json.addProperty("title", src.getTitle());

        return json;
    }

    @Override
    public Inventory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String title = jsonObject.get("title").getAsString();
        Inventory inventory = new Inventory(title);
        List<ItemStack> items = new ArrayList<>();
        JsonArray itemsArray = jsonObject.get("items").getAsJsonArray();
        for (JsonElement itemElement : itemsArray) {
            JsonObject itemObject = itemElement.getAsJsonObject();
            int id = itemObject.get("item").getAsJsonObject().get("id").getAsInt();
            int count = itemObject.get("count").getAsInt();
            items.add(new ItemStack(Items.getItem(id), count));
        }
        inventory.addItems(items.toArray(new ItemStack[0]));

        return inventory;
    }
}
