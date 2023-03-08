package fr.ostix.game.core.events.listener.quest;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.quest.*;
import fr.ostix.game.core.quest.*;

public class QuestManagerListener implements Listener {
    private final QuestManager questManager;

    public QuestManagerListener(QuestManager questManager) {
        this.questManager = questManager;
    }

    @EventHandler
    public void onQuestCategoryStart(QuestCategoryStartEvent event) {
        this.questManager.addToQuesting(event.getQuestID());
        this.questManager.getQuesting(event.getQuestID()).start();

    }

    @EventHandler
    public void onQuestCategoryComplete(QuestCategoryCompleteEvent event) {
        this.questManager.removeFromQuesting(event.getQuestID());
        this.questManager.getQuesting(event.getQuestID()).complete(event.getQuestID());
    }
}
