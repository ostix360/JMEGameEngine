package fr.ostix.game.core.events.menu;

import fr.ostix.game.core.events.Event;

public class QuestSelectedEvent extends Event {

    private final int questID;

    public QuestSelectedEvent(int questID, int priority) {
        super(priority);
        this.questID = questID;
    }

    public int getQuestID() {
        return questID;
    }
}

