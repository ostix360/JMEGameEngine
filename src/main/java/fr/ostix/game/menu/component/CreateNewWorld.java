package fr.ostix.game.menu.component;

import fr.ostix.game.core.Game;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import org.joml.Vector2f;

public class CreateNewWorld extends HorizontalButton{

    private static final int TEXTURE = ResourcePack.getTextureByName("creatNewWorld").getID();
    private final GUIText title;

    public CreateNewWorld(float x, float y, float width, float height) {
        super(x, y, width, height, TEXTURE, CreateNewWorld::createWorld);
        title = new GUIText("Create New World", 1, Game.gameFont, new Vector2f(x+100, y+50),
                600, false);
    }

    @Override
    public void update() {

    }

    private static void createWorld(Button button) {
        // TODO
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
