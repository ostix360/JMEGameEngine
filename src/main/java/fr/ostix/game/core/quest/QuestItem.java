package fr.ostix.game.core.quest;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.quest.*;
import fr.ostix.game.core.loader.json.*;
import fr.ostix.game.items.*;


public class QuestItem extends Quest {

    private final ItemStack item;

    public QuestItem() {
        this.item = new ItemStack(null, 0);
    }

    @Override
    public void execute() {
        EventManager.getInstance().register(this.listener = new QuestGiveItemListener(this));
    }

    public static QuestItem load(String questData) {
        return JsonUtils.gsonInstance().fromJson(questData, QuestItem.class);
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public String save() {
        return JsonUtils.gsonInstance().toJson(this);
    }
}
