package fr.ostix.game.core.events.entity;

import fr.ostix.game.entity.Entity;
import fr.ostix.game.world.World;

public class EntityCloseEvent extends EntityEvent {
    public EntityCloseEvent(Entity entity, World world, int priority) {
        super(entity, priority);
    }
}
