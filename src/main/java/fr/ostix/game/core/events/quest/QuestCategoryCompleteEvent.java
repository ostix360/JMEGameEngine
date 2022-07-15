package fr.ostix.game.core.events.quest;

public class QuestCategoryCompleteEvent extends QuestEvent {

    public QuestCategoryCompleteEvent(int questID, int priority) {
        super(questID, priority);
    }
}
