package fr.ostix.game.core.events.listener.keyMouselisteners;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.keyEvent.KeyMouseMaintainedEvent;
import fr.ostix.game.core.events.keyEvent.KeyMousePressedEvent;
import fr.ostix.game.core.events.keyEvent.KeyMouseReleasedEvent;
import fr.ostix.game.menu.Screen;

public class KeyMouseMenuListener implements KeyMouseListener {

    private final Screen gui;

    public KeyMouseMenuListener(Screen gui) {
        this.gui = gui;
    }

    @Override
    @EventHandler
    public void onKeyPress(KeyMousePressedEvent e) {

    }

    @Override
    @EventHandler
    public void onKeyReleased(KeyMouseReleasedEvent e) {

    }

    @Override
    @EventHandler
    public void onKeyMaintained(KeyMouseMaintainedEvent e) {

    }


}
