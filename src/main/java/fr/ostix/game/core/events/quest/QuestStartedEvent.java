package fr.ostix.game.core.events.quest;

public class QuestStartedEvent extends QuestEvent {
    private int categoryID;
    public QuestStartedEvent(int questID, int categoryID, int priority) {
        super(questID, priority);
        this.categoryID = categoryID;
    }

    public int getCategoryID() {
        return categoryID;
    }
}
