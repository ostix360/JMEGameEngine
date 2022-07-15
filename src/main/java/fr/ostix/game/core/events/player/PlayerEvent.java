package fr.ostix.game.core.events.player;

import fr.ostix.game.core.events.*;
import fr.ostix.game.entity.*;

public abstract class PlayerEvent extends Event {
    private final Player player;

    public PlayerEvent(Player player, int priority) {
        super(priority);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
