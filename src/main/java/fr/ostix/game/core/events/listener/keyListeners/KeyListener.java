package fr.ostix.game.core.events.listener.keyListeners;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.keyEvent.KeyMaintainedEvent;
import fr.ostix.game.core.events.keyEvent.KeyPressedEvent;
import fr.ostix.game.core.events.keyEvent.KeyReleasedEvent;
import fr.ostix.game.core.events.listener.Listener;

public interface KeyListener extends Listener {
    @EventHandler
    void onKeyPress(KeyPressedEvent e);

    @EventHandler
    void onKeyReleased(KeyReleasedEvent e);

    @EventHandler
    void onKeyMaintained(KeyMaintainedEvent e);
}
