package fr.ostix.game.core.events.player;

import fr.ostix.game.entity.Player;
import fr.ostix.game.world.World;

public class PlayerGiveItemEvent extends PlayerEvent {

    private final World world;
    private final int entityId;

    public PlayerGiveItemEvent(Player player,World world, int entityId, int priority) {
        super(player, priority);
        this.world = world;
        this.entityId = entityId;
    }

    public World getWorld() {
        return world;
    }

    public int getEntityId() {
        return entityId;
    }

}
