package fr.ostix.game.core.events.keyEvent;

import org.joml.*;

public class KeyMousePressedEvent extends KeyMouseEvent {
    public KeyMousePressedEvent(int priority, int key, Vector2f position) {
        super(priority, key, position);
    }
}
