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
        ((NPC) this.e).talke(e.getNpc().getDefaultMessage(),e.getWorld());
    }

    @Override
    @EventHandler
    public void onEntityInteract(EntityInteractEvent e) {
        super.onEntityInteract(e);
    }
}
