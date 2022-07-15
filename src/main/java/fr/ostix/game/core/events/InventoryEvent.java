package fr.ostix.game.core.events;

import fr.ostix.game.inventory.*;

public abstract class InventoryEvent extends Event {
    private final Inventory inv;

    public InventoryEvent(int priority, Inventory inv) {
        super(priority);
        this.inv = inv;
    }

    public Inventory getInv() {
        return inv;
    }
}
