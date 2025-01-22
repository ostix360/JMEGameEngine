package fr.ostix.game.core.events.quest;

import fr.ostix.game.core.events.Event;

public class QuestEvent extends Event {
    private final int questID;



    public QuestEvent(int questID, int priority) {
        super(priority);
        this.questID = questID;
    }

    public int getQuestID() {
        return questID;
    }
}
