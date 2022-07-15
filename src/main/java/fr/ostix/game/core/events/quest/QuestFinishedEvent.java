package fr.ostix.game.core.events.quest;

public class QuestFinishedEvent extends QuestEvent {

    public QuestFinishedEvent(int questID, int priority) {
        super(questID, priority);
    }
}
