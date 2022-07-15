package fr.ostix.game.core.events.entity.npc;

import fr.ostix.game.entity.entities.*;

public class NPCTalkEvent extends NPCEvent {

    public NPCTalkEvent(int priority, NPC npc) {
        super(priority, npc);
    }
}
