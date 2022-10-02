package fr.ostix.game.core.events.listener.keyListeners;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.keyEvent.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.entity.entities.npc.*;
import org.lwjgl.glfw.*;

import static org.yaml.snakeyaml.tokens.Token.ID.Key;

public class NPCReaderListener implements KeyListener {

    private final NPCGui gui;
    public NPCReaderListener(NPCGui npcGui) {
        this.gui = npcGui;
    }

    @Override
    @EventHandler
    public void onKeyPress(KeyPressedEvent e) {
        if (e.getKEY() == GLFW.GLFW_KEY_SPACE){
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
