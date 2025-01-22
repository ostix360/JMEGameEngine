package fr.ostix.game.core.events.listener.keyListeners;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.keyEvent.KeyMaintainedEvent;
import fr.ostix.game.core.events.keyEvent.KeyPressedEvent;
import fr.ostix.game.core.events.keyEvent.KeyReleasedEvent;
import fr.ostix.game.entity.entities.npc.gui.NPCGui;
import org.lwjgl.glfw.GLFW;

public class NPCReaderListener implements KeyListener {

    private final NPCGui gui;
    public NPCReaderListener(NPCGui npcGui) {
        this.gui = npcGui;
    }

    @Override
    @EventHandler
    public void onKeyPress(KeyPressedEvent e) {
        if (e.getKEY() == GLFW.GLFW_KEY_SPACE || e.getKEY() == GLFW.GLFW_KEY_ENTER) {
            gui.next();
        }
    }

    @Override
    public void onKeyReleased(KeyReleasedEvent e) {

    }

    @Override
    public void onKeyMaintained(KeyMaintainedEvent e) {

    }
}
