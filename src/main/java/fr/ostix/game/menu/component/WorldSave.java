package fr.ostix.game.menu.component;

import fr.ostix.game.core.Game;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.states.StateChangeEvent;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.menu.WorldLoadingMenu;
import org.joml.Vector2f;

public class WorldSave extends HorizontalButton{

    private static final int TEXTURE = ResourcePack.getTextureByName("creatNewWorld").getID();
    private final GUIText title;

    public WorldSave(float x, float y, float width, float height, String saveName) {
        super(x, y, width, height, TEXTURE, (b) -> {
            EventManager.getInstance().callEvent(new StateChangeEvent("WorldLoading",
                new WorldLoadingMenu(), 4));
        });
        title = new GUIText(saveName, 1, Game.gameFont, new Vector2f(x+100, y+50),
                600, false);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        MasterFont.add(title);
    }

    @Override
    public void cleanUp() {
        MasterFont.remove(title);
        super.cleanUp();
    }
}
