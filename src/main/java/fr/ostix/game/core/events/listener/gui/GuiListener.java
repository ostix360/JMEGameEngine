package fr.ostix.game.core.events.listener.gui;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.gui.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.menu.*;
import fr.ostix.game.menu.stat.*;

public class GuiListener implements Listener {
    private final StateManager stateManager;

    public GuiListener(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @EventHandler
    public void onGuiOpen(GuiOpenEvent e) {
        stateManager.setCurrentScreen(e.getGui());
    }

    @EventHandler
    public void onGuiClose(GuiCloseEvent e) {
        if (e.getGui() instanceof MainMenu) {
            EventManager.getInstance().callEvent(new ExitGameEvent(5));
            return;
        }
        EventManager.getInstance().callEvent(new GuiOpenEvent(e.getGui().getPreviousScreen(), 1));
    }

}
