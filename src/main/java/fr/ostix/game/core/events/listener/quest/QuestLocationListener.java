package fr.ostix.game.core.events.listener.quest;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.player.*;
import fr.ostix.game.core.events.quest.*;
import fr.ostix.game.core.quest.*;

public class QuestLocationListener implements Listener {

    private final QuestLocation quest;

    public QuestLocationListener(QuestLocation quest) {
        this.quest = quest;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getPlayer().getPosition().equals(quest.getPos(), quest.getRange())) {
            EventManager.getInstance().callEvent(new QuestFinishedEvent(quest.getId(),1));
        }
    }
}
