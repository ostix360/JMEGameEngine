package fr.ostix.game.core.events.quest;

public class QuestCategoryStartEvent extends QuestEvent {
    public QuestCategoryStartEvent(int questID, int priority) {
        super(questID, priority);
    }
}
