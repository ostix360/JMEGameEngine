package fr.ostix.game.core.events.listener.menu;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.menu.*;
import fr.ostix.game.menu.*;

public class QuestHandlerMenuListener implements Listener {
    private final QuestHandlerMenu questHandlerMenu;


    public QuestHandlerMenuListener(QuestHandlerMenu questHandlerMenu) {
        this.questHandlerMenu = questHandlerMenu;
    }

    @EventHandler
    public void onQuestSelected(QuestSelectedEvent event) {
        questHandlerMenu.notifyQuestSelected(event.getQuestID());
    }

}
