package fr.ostix.game.core.events.player;

import fr.ostix.game.entity.*;
import fr.ostix.game.items.*;
import fr.ostix.game.world.*;

public class PlayerGiveItemEvent extends PlayerEvent {
    private final ItemStack stack;

    private final World world;

    public PlayerGiveItemEvent(Player player,World world,int priority, ItemStack stack) {
        super(player, priority);
        this.stack = stack;
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public ItemStack getStack() {
        return stack;
    }
}
