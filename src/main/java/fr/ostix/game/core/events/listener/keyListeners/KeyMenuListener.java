package fr.ostix.game.core.events.listener.keyListeners;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.gui.GuiCloseEvent;
import fr.ostix.game.core.events.keyEvent.KeyMaintainedEvent;
import fr.ostix.game.core.events.keyEvent.KeyPressedEvent;
import fr.ostix.game.core.events.keyEvent.KeyReleasedEvent;
import fr.ostix.game.menu.Screen;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class KeyMenuListener implements KeyListener {

    private Screen currentScreen;

    public KeyMenuListener(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

    @Override
    @EventHandler
    public void onKeyPress(KeyPressedEvent e) {
        if (e.getKEY() == GLFW_KEY_ESCAPE) {
            EventManager.getInstance().callEvent(new GuiCloseEvent(currentScreen, 0));
        }
    }

    @Override
    @EventHandler
    public void onKeyReleased(KeyReleasedEvent e) {

    }

    @Override
    @EventHandler
    public void onKeyMaintained(KeyMaintainedEvent e) {

    }
}
