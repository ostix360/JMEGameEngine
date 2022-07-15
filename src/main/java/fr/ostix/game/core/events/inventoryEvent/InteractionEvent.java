package fr.ostix.game.core.events.inventoryEvent;

import fr.ostix.game.core.events.*;

public class InteractionEvent extends Event {
    private final boolean isNearFromPlayer;

    public InteractionEvent(boolean isNearFromPlayer) {
        super(Event.LOW);

        this.isNearFromPlayer = isNearFromPlayer;

    }

    public boolean isNearFromPlayer() {
        return isNearFromPlayer;
    }
}
