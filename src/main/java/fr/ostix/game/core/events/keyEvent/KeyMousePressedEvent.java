package fr.ostix.game.core.events.keyEvent;

import org.joml.Vector2f;

public class KeyMousePressedEvent extends KeyMouseEvent {
    public KeyMousePressedEvent(int priority, int key, Vector2f position) {
        super(priority, key, position);
    }
}
