package fr.ostix.game.core.quest;

import com.google.gson.annotations.Expose;
import fr.ostix.game.core.Registered;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.entity.npc.NPCTalkEvent;
import fr.ostix.game.core.events.listener.quest.QuestTalkListener;
import fr.ostix.game.core.loader.json.JsonUtils;
import fr.ostix.game.core.quest.serialization.RewardsTypeAdapter;
import fr.ostix.game.entity.Player;
import fr.ostix.game.entity.entities.npc.NPCGod;
import fr.ostix.game.world.World;

import java.util.ArrayList;
import java.util.List;

public class QuestDialog extends Quest {
    @Expose
    private final List<String> dialogs;
    @Expose
    private int dialogLine;

    public QuestDialog() {

        this.dialogs = new ArrayList<>();
    }

    @Override
    public void execute() {
        if (getNpcID() == 0) {
            World.addToDo((w -> EventManager.getInstance().callEvent(new NPCTalkEvent(w, 2, NPCGod.getInstance()))));
        } else {
            Registered.getNPC(this.getNpcID()).unRegisterDefaultDialog();
        }
        EventManager.getInstance().register(this.listener = new QuestTalkListener(this));
    }

    @Override
    public void done(Player p) {
        super.done(p);
        Registered.getNPC(this.getNpcID()).registerDefaultDialog();
    }

    public static QuestDialog load(String questData) {
        return JsonUtils.gsonInstance(Rewards.class, new RewardsTypeAdapter(), true).fromJson(questData, QuestDialog.class);
    }

    public int getDialogLine() {
        return dialogLine;
    }

    public List<String> getDialogs() {
        return dialogs;
    }

    @Override
    public String save() {
        EventManager.getInstance().unRegister(this.listener);
        return JsonUtils.gsonInstance(Rewards.class, new RewardsTypeAdapter(), true).toJson(this);
    }
}
