package fr.ostix.game.core.quest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.quest.*;
import fr.ostix.game.core.loader.json.*;
import fr.ostix.game.core.loader.json.typeAdapter.ItemStackTypeAdapter;
import fr.ostix.game.core.quest.serialization.RewardsTypeAdapter;
import fr.ostix.game.items.*;


public class QuestItem extends Quest {
    @Expose
    private final ItemStack item;

    public QuestItem() {
        this.item = new ItemStack(null, 0);
    }

    @Override
    public void execute() {
        EventManager.getInstance().register(this.listener = new QuestGiveItemListener(this));
    }

    public static QuestItem load(String questData) {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Rewards.class, new RewardsTypeAdapter())
                .registerTypeAdapter(ItemStack.class, new ItemStackTypeAdapter());
        return builder.create().fromJson(questData, QuestItem.class);
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public String save() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Rewards.class, new RewardsTypeAdapter())
                .registerTypeAdapter(ItemStack.class, new ItemStackTypeAdapter());
        return builder.create().toJson(this);
    }
}
