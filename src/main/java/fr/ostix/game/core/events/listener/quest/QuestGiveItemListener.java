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
            Registered.getNPC(this.quest.getNpcID()).talke(event.getWorld(),null,"Desole vous n'avez pas assez de " + this.quest.getItem().getItem().getName());
            return;
        }else{
//            Registered.getNPC(this.quest.getNpcID()).talke("Merci pour le " + this.quest.getItem().getItem().getName(),event.getWorld());
            if (!event.getPlayer().getInventory().removeItems(this.quest.getItem())) {
                Registered.getNPC(this.quest.getNpcID()).talke(event.getWorld(),null,"Desole vous n'avez pas assez de " + this.quest.getItem().getItem().getName());
                return;
            }
        }
        EventManager.getInstance().callEvent(new QuestFinishedEvent(quest.getId(),1,event.getPlayer()));
    }
}
