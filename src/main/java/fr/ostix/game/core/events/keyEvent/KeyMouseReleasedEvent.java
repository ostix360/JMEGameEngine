package fr.ostix.game.core.events.keyEvent;


import org.joml.*;

public class KeyMouseReleasedEvent extends KeyMouseEvent {
    public KeyMouseReleasedEvent(int priority, int key, Vector2f position) {
        super(priority, key, position);
    }
}
