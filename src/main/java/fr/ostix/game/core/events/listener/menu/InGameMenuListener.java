package fr.ostix.game.core.events.listener.menu;

import fr.ostix.game.core.Game;
import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.entity.EntityCloseEvent;
import fr.ostix.game.core.events.entity.EntityInteractEvent;
import fr.ostix.game.core.events.listener.Listener;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.entities.NPC;
import fr.ostix.game.entity.entities.Shop;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.menu.InGameMenu;
import fr.ostix.game.toolBox.Color;
import org.joml.Vector2f;

public class InGameMenuListener implements Listener {

    private final InGameMenu inGameMenu;

    private final GuiTexture bgInteraction;
    private final GUIText interactionText;

    private Entity entityClose;

    public InGameMenuListener(InGameMenu inGameMenu) {
        this.inGameMenu = inGameMenu;
        bgInteraction = new GuiTexture(ResourcePack.getTextureByName("bgInteraction").getID(),
                new Vector2f(1920 / 2f - 60, 850), new Vector2f(120, 40));
        interactionText = new GUIText("Ouvrir", 1, Game.gameFont, new Vector2f(1920 / 2f - 50, 860),
                20, false);

        interactionText.setColour(Color.WHITE);
    }

    @EventHandler
    public void onInteract(EntityInteractEvent e){
        removeToRenderer();
    }

    @EventHandler
    public void onEntityClose(EntityCloseEvent e) {
        if (e.getEntity() == null){
            removeToRenderer();
            return;
        }
        if (e.getEntity() instanceof NPC) {
            interactionText.setText("Parler");
            bgInteraction.setPosition(new Vector2f(1920 / 2f - 62, 855));
            bgInteraction.setScale(new Vector2f(100, 35));
            sendToRenderer(e.getEntity());
        } else if (e.getEntity() instanceof Shop) {
            interactionText.setText("Acheter");
            bgInteraction.setScale(new Vector2f(110, 35));
            sendToRenderer(e.getEntity());
        } else {
            removeToRenderer();
        }
    }



    public void sendToRenderer(Entity e) {
        if (entityClose != null) {
            if (e.equals(entityClose)) return;
        }
        entityClose = e;
        MasterFont.add(interactionText);
        MasterGui.addGui(bgInteraction);
    }

    public void removeToRenderer() {
        if (entityClose == null) return;
        entityClose = null;
        MasterFont.remove(interactionText);
        MasterGui.removeGui(bgInteraction);
    }
}
