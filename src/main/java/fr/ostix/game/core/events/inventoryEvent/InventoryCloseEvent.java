package fr.ostix.game.core.events.inventoryEvent;

import fr.ostix.game.core.events.*;
import fr.ostix.game.inventory.*;

public class InventoryCloseEvent extends InventoryEvent {
    public InventoryCloseEvent(int priority, Inventory inv) {
        super(priority, inv);
    }
}
