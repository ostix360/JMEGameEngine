package fr.ostix.game.core.events.listener.quest;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.player.*;
import fr.ostix.game.core.events.quest.*;
import fr.ostix.game.core.quest.*;

public class QuestGiveItemListener implements Listener {

    private final QuestItem quest;

    public QuestGiveItemListener(QuestItem quest) {
        this.quest = quest;
    }

    @EventHandler
    public void onItemGive(PlayerGiveItemEvent event) {
        if (!event.getPlayer().getInventory().has(this.quest.getItem())) {
            Registered.getNPC(this.quest.getNpc()).talke("Désolé vous n'avez pas assez de " + this.quest.getItem().getItem().getName(),event.getWorld());
            return;
        }
        EventManager.getInstance().callEvent(new QuestFinishedEvent(quest.getId(),1));
    }
}
