package fr.ostix.game.core.events.entity;

import fr.ostix.game.entity.Entity;
import fr.ostix.game.world.World;

public class EntityInteractEvent extends EntityEvent {

    private final World world;
    public EntityInteractEvent(Entity e, World world, int priority) {
        super(e, priority);
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
