package fr.ostix.game.core.quest;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.quest.serialization.*;
import fr.ostix.game.entity.*;

public abstract class Quest implements IQuestSerializer {
    private final int id;
    private final int npcID;
    private final String title;
    private final String description;
    private final Rewards rewards;
    private QuestStatus status;
    protected Listener listener;

    private final boolean isStarted = false;
    private final boolean isFinished = false;


    public Quest() {
        this.id = 0;
        this.npcID = -1;
        this.title = "Null Quest";
        this.description = "The Quest is null";
        this.rewards = new Rewards();
        this.status = QuestStatus.UNAVAILABLE;
    }

    public abstract void execute();

    public void done(Player p) {
        this.status = QuestStatus.DONE;
        EventManager.getInstance().unRegister(this.listener);
        this.rewards.reward(p);
    }

    public int getNpcID() {
        return npcID;
    }

    public String getDescription() {
        return description;
    }

    public Rewards getRewards() {
        return rewards;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setStatus(QuestStatus status) {
        this.status = status;
    }
}
