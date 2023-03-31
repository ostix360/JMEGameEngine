package fr.ostix.game.core.events.entity.npc;

import fr.ostix.game.entity.entities.NPC;
import fr.ostix.game.world.World;

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
