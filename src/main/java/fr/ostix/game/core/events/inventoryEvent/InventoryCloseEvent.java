package fr.ostix.game.core.events.inventoryEvent;

import fr.ostix.game.core.events.InventoryEvent;
import fr.ostix.game.inventory.Inventory;

public class InventoryCloseEvent extends InventoryEvent {
    public InventoryCloseEvent(int priority, Inventory inv) {
        super(priority, inv);
    }
}
