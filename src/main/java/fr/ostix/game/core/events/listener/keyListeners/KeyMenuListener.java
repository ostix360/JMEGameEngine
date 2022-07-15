package fr.ostix.game.core.events.listener.keyListeners;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.gui.*;
import fr.ostix.game.core.events.keyEvent.*;
import fr.ostix.game.menu.*;

import static org.lwjgl.glfw.GLFW.*;

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
