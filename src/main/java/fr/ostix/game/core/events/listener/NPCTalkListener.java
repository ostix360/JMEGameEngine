package fr.ostix.game.core.events.listener;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.entity.EntityInteractEvent;
import fr.ostix.game.core.events.entity.npc.NPCTalkEvent;
import fr.ostix.game.entity.entities.NPC;

public class NPCTalkListener extends EntityListener {


    public NPCTalkListener(NPC npc) {
        super(npc);
    }

    @EventHandler
    public void onTalk(NPCTalkEvent e) {
        if (e.getNpc().getId() != this.e.getId()) return;
        ((NPC) this.e).talke(e.getWorld(), e.getNpc().getDefaultMessage());
    }

    @Override
    @EventHandler
    public void onEntityInteract(EntityInteractEvent e) {
        super.onEntityInteract(e);
    }
}
