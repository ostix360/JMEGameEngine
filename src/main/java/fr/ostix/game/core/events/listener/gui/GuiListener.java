package fr.ostix.game.core.events.listener.gui;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.ExitGameEvent;
import fr.ostix.game.core.events.gui.GuiCloseEvent;
import fr.ostix.game.core.events.gui.GuiOpenEvent;
import fr.ostix.game.core.events.listener.Listener;
import fr.ostix.game.menu.MainMenu;
import fr.ostix.game.menu.state.StateManager;

public class GuiListener implements Listener { // TODO : what is it for?
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
