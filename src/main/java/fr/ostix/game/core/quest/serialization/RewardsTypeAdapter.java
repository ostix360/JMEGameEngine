package fr.ostix.game.core.quest.serialization;

import com.google.gson.*;
import com.google.gson.stream.*;
import fr.ostix.game.core.quest.*;
import fr.ostix.game.items.*;

import java.io.*;
import java.util.*;

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
