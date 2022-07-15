package fr.ostix.game.core.quest;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.quest.*;
import fr.ostix.game.core.loader.json.*;

import java.util.*;

public class QuestDialog extends Quest {
    private final List<String> dialogs;

    public QuestDialog() {

        this.dialogs = new ArrayList<>();
    }

    @Override
    public void execute() {
        EventManager.getInstance().register(this.listener = new QuestTalkListener(this));
    }

    public static QuestDialog load(String questData) {
        return JsonUtils.gsonInstance().fromJson(questData, QuestDialog.class);
    }

    public List<String> getDialogs() {
        return dialogs;
    }

    @Override
    public String save() {
        return JsonUtils.gsonInstance().toJson(this);
    }
}
