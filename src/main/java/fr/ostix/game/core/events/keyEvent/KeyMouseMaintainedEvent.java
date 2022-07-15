package fr.ostix.game.core.events.keyEvent;

import org.joml.*;

public class KeyMouseMaintainedEvent extends KeyMouseEvent {
    public KeyMouseMaintainedEvent(int priority, int key, Vector2f position) {
        super(priority, key, position);
    }
}
