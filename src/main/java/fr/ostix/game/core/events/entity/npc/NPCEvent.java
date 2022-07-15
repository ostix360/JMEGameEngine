package fr.ostix.game.core.events.entity.npc;

import fr.ostix.game.core.events.entity.*;
import fr.ostix.game.entity.entities.*;

public abstract class NPCEvent extends EntityEvent {

    protected final NPC npc;

    public NPCEvent(int priority, NPC npc) {
        super(npc, priority);
        this.npc = npc;
    }

    public NPC getNpc() {
        return npc;
    }
}
