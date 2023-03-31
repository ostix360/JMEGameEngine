package fr.ostix.game.core.events.listener.keyMouselisteners;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.keyEvent.KeyMouseMaintainedEvent;
import fr.ostix.game.core.events.keyEvent.KeyMousePressedEvent;
import fr.ostix.game.core.events.keyEvent.KeyMouseReleasedEvent;
import fr.ostix.game.core.events.listener.Listener;


public interface KeyMouseListener extends Listener {

    @EventHandler
    void onKeyPress(KeyMousePressedEvent e);

    @EventHandler
    void onKeyReleased(KeyMouseReleasedEvent e);

    @EventHandler
    void onKeyMaintained(KeyMouseMaintainedEvent e);
}
