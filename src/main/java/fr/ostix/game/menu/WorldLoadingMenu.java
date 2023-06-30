package fr.ostix.game.menu;

import fr.ostix.game.core.Game;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import org.joml.Vector2f;

public class WorldLoadingMenu extends Screen {

    private final GUIText loadingText;

    public WorldLoadingMenu() {
        super("World Loading Menu");
        loadingText = new GUIText("Loading...", 1, Game.gameFont,
                new Vector2f(), 600, false);
    }

    @Override
    public void init() {
        MasterFont.add(loadingText);
        super.init();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void cleanUp() {
        MasterFont.remove(loadingText);
        super.cleanUp();
    }
}
