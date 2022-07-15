package fr.ostix.game.core.events.inventoryEvent;

import fr.ostix.game.core.events.*;
import fr.ostix.game.inventory.*;

public class InventoryOpenEvent extends InventoryEvent {

    public InventoryOpenEvent(int priority, Inventory inv) {
        super(priority, inv);
    }
}
