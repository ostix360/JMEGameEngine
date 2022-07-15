package fr.ostix.game.core.events.listener.keyMouselisteners;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.keyEvent.*;
import fr.ostix.game.core.events.listener.*;


public interface KeyMouseListener extends Listener {

    @EventHandler
    void onKeyPress(KeyMousePressedEvent e);

    @EventHandler
    void onKeyReleased(KeyMouseReleasedEvent e);

    @EventHandler
    void onKeyMaintained(KeyMouseMaintainedEvent e);
}
