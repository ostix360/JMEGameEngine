package fr.ostix.game.core.events.player;

import fr.ostix.game.entity.*;
import fr.ostix.game.items.*;

public class PlayerGiveItemEvent extends PlayerEvent {
    private final ItemStack stack;

    public PlayerGiveItemEvent(Player player, int priority, ItemStack stack) {
        super(player, priority);
        this.stack = stack;
    }

    public ItemStack getStack() {
        return stack;
    }
}
