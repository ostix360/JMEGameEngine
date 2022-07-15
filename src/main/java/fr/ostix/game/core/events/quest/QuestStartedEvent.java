package fr.ostix.game.core.events.quest;

public class QuestStartedEvent extends QuestEvent {
    public QuestStartedEvent(int questID, int priority) {
        super(questID, priority);
    }
}
