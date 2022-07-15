package fr.ostix.game.core.events.listener;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.inventoryEvent.*;
import fr.ostix.game.menu.*;

public class InventoryListener implements Listener {

    private final WorldState worldState;

    public InventoryListener(WorldState worldState) {
        this.worldState = worldState;
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        worldState.setOpenInventory(true);
        worldState.setCurrentInventory(e.getInv());
        worldState.setWorldCanBeUpdated(false);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        worldState.setWorldCanBeUpdated(true);
        worldState.setOpenInventory(false);
        worldState.setCurrentInventory(null);
    }
}
