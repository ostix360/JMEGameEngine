package fr.ostix.game.core.events.entity;

import fr.ostix.game.entity.*;

public class EntityInteractEvent extends EntityEvent {
    public EntityInteractEvent(Entity e, int priority) {
        super(e, priority);
    }
}
