package fr.ostix.game.core.events.listener;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.entity.*;
import fr.ostix.game.core.events.entity.npc.*;
import fr.ostix.game.entity.entities.*;

public class NPCTalkListener extends EntityListener {


    public NPCTalkListener(NPC npc) {
        super(npc);
    }

    @EventHandler
    public void onTalk(NPCTalkEvent e) {
        if (e.getNpc().getId() != this.e.getId()) return;
        ((NPC) this.e).talke(e.getWorld(),null,e.getNpc().getDefaultMessage());
    }

    @Override
    @EventHandler
    public void onEntityInteract(EntityInteractEvent e) {
        super.onEntityInteract(e);
    }
}
