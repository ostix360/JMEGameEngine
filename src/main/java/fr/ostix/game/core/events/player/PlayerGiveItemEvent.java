package fr.ostix.game.core.events.player;

import fr.ostix.game.entity.Player;
import fr.ostix.game.world.World;

public class PlayerGiveItemEvent extends PlayerEvent {

    private final World world;

    public PlayerGiveItemEvent(Player player,World world,int priority) {
        super(player, priority);
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

}
