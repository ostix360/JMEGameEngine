package fr.ostix.game.core.events.quest;

import fr.ostix.game.entity.Player;

public class QuestFinishedEvent extends QuestEvent {
    private Player p;
    private int categoryID;

    public QuestFinishedEvent(int questID, int categoryID, int priority, Player p) {
        super(questID, priority);
        this.p = p;
        this.categoryID = categoryID;
    }

    public Player getP() {
        return p;
    }

    public int getCategoryID() {
        return categoryID;
    }
}
