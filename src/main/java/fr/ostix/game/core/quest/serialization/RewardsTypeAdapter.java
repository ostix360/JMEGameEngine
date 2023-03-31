package fr.ostix.game.core.quest.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.ostix.game.core.quest.Rewards;
import fr.ostix.game.items.Item;
import fr.ostix.game.items.ItemStack;
import fr.ostix.game.items.Items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RewardsTypeAdapter extends TypeAdapter<Rewards> {

    @Override
    public void write(JsonWriter jw, Rewards rewards) throws IOException {
        jw.beginObject();
        jw.name("items").beginArray();
        for (ItemStack stack : rewards.getRewardsItems()) {
            jw.beginObject();
            jw.name("item").value(stack.getItem().getId());
            jw.name("count").value(stack.getCount());
            jw.endObject();
        }
        jw.endArray();
        jw.name("money").value(rewards.getMoneyAmount());
        jw.endObject();
    }

    @Override
    public Rewards read(JsonReader jr) throws IOException {
        int money = 0;
        final List<ItemStack> itemStacks = new ArrayList<>();
        Item i = null;
        int itemCount = 0;

        jr.beginObject();
        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "items":
                    jr.beginArray();

                    while (jr.hasNext()) {
                        jr.beginObject();

                        while (jr.hasNext()) {
                            switch (jr.nextName()) {
                                case "item":
                                    i = Items.getItem(jr.nextInt());
                                    break;
                                case "count":
                                    itemCount = jr.nextInt();
                            }
                        }
                        itemStacks.add(new ItemStack(i, itemCount));
                        jr.endObject();
                    }
                    jr.endArray();
                    break;
                case "money":
                    money = jr.nextInt();
            }
        }
        jr.endObject();

        return new Rewards(itemStacks, money);
    }
}
