package fr.ostix.game.core.events.listener;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.inventoryEvent.InventoryCloseEvent;
import fr.ostix.game.core.events.inventoryEvent.InventoryOpenEvent;
import fr.ostix.game.menu.WorldState;

public class InventoryListener implements Listener {

    private final WorldState worldState;

    public InventoryListener(WorldState worldState) {
        this.worldState = worldState;
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        worldState.setWorldCanBeUpdated(false);
        worldState.setOpenInventory(true);
        worldState.setCurrentInventory(e.getInv());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        worldState.setOpenInventory(false);
        worldState.setCurrentInventory(null);
        worldState.setWorldCanBeUpdated(true);
    }
}
