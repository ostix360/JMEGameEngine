package fr.ostix.game.core.events.keyEvent;

import fr.ostix.game.core.events.Event;

public abstract class KeyEvent extends Event {
    private final int KEY;

    public KeyEvent(int priority, int key) {
        super(priority);
        this.KEY = key;
    }

    public int getKEY() {
        return KEY;
    }
}
