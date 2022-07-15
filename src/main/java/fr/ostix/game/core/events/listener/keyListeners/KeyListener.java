package fr.ostix.game.core.events.listener.keyListeners;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.keyEvent.*;
import fr.ostix.game.core.events.listener.*;

public interface KeyListener extends Listener {
    @EventHandler
    void onKeyPress(KeyPressedEvent e);

    @EventHandler
    void onKeyReleased(KeyReleasedEvent e);

    @EventHandler
    void onKeyMaintained(KeyMaintainedEvent e);
}
