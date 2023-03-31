package fr.ostix.game.core.events.inventoryEvent;

import fr.ostix.game.core.events.InventoryEvent;
import fr.ostix.game.inventory.Inventory;

public class InventoryOpenEvent extends InventoryEvent {

    public InventoryOpenEvent(int priority, Inventory inv) {
        super(priority, inv);
    }
}
