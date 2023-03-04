package fr.ostix.game.menu.component;

import fr.ostix.game.core.Input;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.toolBox.OpenGL.DisplayManager;
import org.joml.Vector2f;

public class HorizontalButton extends Button{

    private int textureSize;

    public HorizontalButton(float x, float y, float width, float height, int texture, IPressable press) {
        super(x, y, width, height, texture, press);
        this.textureSize = (int) width;
        this.texture = new GuiTexture(texture, new Vector2f(x, y), new Vector2f(this.textureSize, this.textureSize));
        this.init();
    }

    @Override
    public boolean isIn() {
         float mX = (float) Input.getMouseX() / DisplayManager.getWidth() * 1920;
        float mY = (float) Input.getMouseY() / DisplayManager.getHeight() * 1080;

        float removeEmptySpace = (this.textureSize - height) / 2;

        return mX >= this.x && mY >= this.y &&
                mX < (this.x + this.width) && mY < (this.y + this.height + removeEmptySpace);
    }

    @Override
    public boolean mouseIn(Vector2f MousePos) {
        float mX = MousePos.x() / DisplayManager.getWidth() * 1920;
        float mY = MousePos.y() / DisplayManager.getHeight() * 1080;

        float removeEmptySpace = (this.textureSize - height) / 2;

        return pressed = mX >= this.x && mY >= this.y &&
                mX < (this.x + this.width) && mY < (this.y + this.height + removeEmptySpace);
    }
}
