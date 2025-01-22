package fr.ostix.game.core.events.listener.quest;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.listener.Listener;
import fr.ostix.game.core.events.player.PlayerMoveEvent;
import fr.ostix.game.core.events.quest.QuestFinishedEvent;
import fr.ostix.game.core.quest.QuestLocation;

public class QuestLocationListener implements Listener {

    private final QuestLocation quest;

    public QuestLocationListener(QuestLocation quest) {
        this.quest = quest;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getPlayer().getPosition().equals(quest.getPos(), quest.getRange())) {
            EventManager.getInstance().callEvent(new QuestFinishedEvent(quest.getId(), quest.getCategoryID(),1,e.getPlayer()));
        }
    }
}
