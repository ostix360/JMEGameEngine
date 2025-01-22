package fr.ostix.game.core.quest;

import com.google.gson.annotations.Expose;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.listener.Listener;
import fr.ostix.game.core.quest.serialization.IQuestSerializer;
import fr.ostix.game.entity.Player;

public abstract class Quest implements IQuestSerializer {
    @Expose
    private final int id;
    @Expose
    private final int npcID;
    @Expose
    private final String title;
    @Expose
    private final String description;
    @Expose
    private final Rewards rewards;
    @Expose
    private QuestStatus status;

    protected int categoryID;

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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
