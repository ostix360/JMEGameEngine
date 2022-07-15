package fr.ostix.game.core.events.entity;

import fr.ostix.game.core.events.*;
import fr.ostix.game.entity.*;

public class EntityEvent extends Event {
    private final Entity e;

    public EntityEvent(Entity e, int priority) {
        super(priority);
        this.e = e;
    }

    public Entity getEntity() {
        return e;
    }
}
