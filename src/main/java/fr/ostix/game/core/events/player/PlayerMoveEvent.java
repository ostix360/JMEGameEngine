package fr.ostix.game.core.events.player;

import fr.ostix.game.entity.Player;

public class PlayerMoveEvent extends PlayerEvent {


    public PlayerMoveEvent(Player player, int priority) {
        super(player, priority);
    }

}
