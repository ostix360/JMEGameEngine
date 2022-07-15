package fr.ostix.game.core.events.keyEvent;

import fr.ostix.game.core.events.*;
import org.joml.*;

public abstract class KeyMouseEvent extends Event {
    private final int KEY;
    private final Vector2f position;

    public KeyMouseEvent(int priority, int key, Vector2f position) {
        super(priority);
        this.KEY = key;
        this.position = position;
    }

    public Vector2f getPosition() {
        return position;
    }

    public int getKEY() {
        return KEY;
    }
}
