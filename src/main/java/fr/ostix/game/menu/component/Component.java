package fr.ostix.game.menu.component;

import fr.ostix.game.core.*;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.gui.*;
import fr.ostix.game.toolBox.Logger;
import fr.ostix.game.toolBox.OpenGL.*;
import org.joml.*;

public abstract class Component {

    protected float x, y;
    protected float width, height;

    protected GuiTexture texture;


    public Component(float x, float y, float width, float height, int texture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public abstract void update();

    public abstract void render();

    public void init() {
        if (texture == null) {
            texture = new GuiTexture(ResourcePack.getTextureByName("missing").getID(), new Vector2f(x, y), new Vector2f(width, height));
            Logger.err("Texture is", new NullPointerException());
        }
        MasterGui.addGui(texture);
    }

    public void cleanUp() {
        MasterGui.removeGui(texture);
    }


    public boolean isIn() {
        float mX = (float) Input.getMouseX() / DisplayManager.getWidth() * 1920;
        float mY = (float) Input.getMouseY() / DisplayManager.getHeight() * 1080;
        return mX >= this.x && mY >= this.y &&
                mX < (this.x + this.width) && mY < (this.y + this.height);
    }


}
