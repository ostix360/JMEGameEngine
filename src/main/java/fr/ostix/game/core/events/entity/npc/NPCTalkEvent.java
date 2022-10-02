package fr.ostix.game.core.events.entity.npc;

import fr.ostix.game.entity.entities.*;
import fr.ostix.game.world.*;

public class NPCTalkEvent extends NPCEvent {

    private final World world;

    public NPCTalkEvent(World world, int priority, NPC npc) {
        super(priority, npc);
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
