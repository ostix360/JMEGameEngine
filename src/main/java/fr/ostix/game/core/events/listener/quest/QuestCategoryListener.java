package fr.ostix.game.core.events.listener.quest;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.quest.*;
import fr.ostix.game.core.quest.*;

public class QuestCategoryListener implements Listener {
    private final QuestCategory questCategory;

    public QuestCategoryListener(QuestCategory questManager) {
        this.questCategory = questManager;
    }

    @EventHandler
    public void onQuestStart(QuestStartedEvent event) {
        questCategory.quests().get(event.getQuestID() - 1).execute();
    }

    @EventHandler
    public void onQuestComplete(QuestFinishedEvent event) {
        questCategory.quests().get(event.getQuestID() - 1).done();
    }
}
