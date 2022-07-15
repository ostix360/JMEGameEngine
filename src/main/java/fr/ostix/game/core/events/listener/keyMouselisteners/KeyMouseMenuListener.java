package fr.ostix.game.core.events.listener.keyMouselisteners;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.keyEvent.*;
import fr.ostix.game.menu.*;

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
