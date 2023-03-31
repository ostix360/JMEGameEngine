package fr.ostix.game.core.events.listener.menu;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.listener.Listener;
import fr.ostix.game.core.events.menu.QuestSelectedEvent;
import fr.ostix.game.core.quest.QuestManager;
import fr.ostix.game.core.quest.QuestStatus;
import fr.ostix.game.menu.QuestHandlerMenu;

public class QuestHandlerMenuListener implements Listener {
    private final QuestHandlerMenu questHandlerMenu;

    private final QuestManager questManager;


    public QuestHandlerMenuListener(QuestHandlerMenu questHandlerMenu) {
        this.questHandlerMenu = questHandlerMenu;
        this.questManager = questHandlerMenu.questManager;
    }

    @EventHandler
    public void onQuestSelected(QuestSelectedEvent event) {
        if (this.questManager.getQuesting(event.getQuestID()).getStatus().equals(QuestStatus.AVAILABLE)) {
            questHandlerMenu.notifyQuestSelected(event.getQuestID());
            this.questManager.getQuesting(this.questManager.getQuesting().get(0)).endQuesting();
            this.questManager.addToQuesting(event.getQuestID());
        }
    }

}
