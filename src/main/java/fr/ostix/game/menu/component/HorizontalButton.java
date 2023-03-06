package fr.ostix.game.menu.component;

import fr.ostix.game.core.Input;
import fr.ostix.game.core.logics.ressourceProcessor.GLGuiModelRequest;
import fr.ostix.game.gui.GuiLayer;
import fr.ostix.game.gui.GuiModel;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.OpenGL.DisplayManager;
import org.joml.Vector2f;

public class HorizontalButton extends Button {

    /**
     * The texture size of the button
     */
    private int textureSize;

    /**
     * The relative position of the button
     */
    private float r;

    public HorizontalButton(float x, float y, float width, float height, int texture, IPressable press) {
        super(x, y, width, height, texture, press);
        this.textureSize = (int) width;
        this.r = (this.textureSize - height) / 2f;
        this.texture = new GuiTexture(texture, new Vector2f(x, y), new Vector2f(this.textureSize, this.textureSize));
        final float[] texCoords = new float[]{
                0, 0,
                0, 1,
                r, 0,
                r, 1
        };
        this.texture.setModel(new GuiModel(texCoords));

        this.layer = new GuiLayer(new Vector2f(0, r),
                new Vector2f(this.width / this.textureSize, this.height / this.textureSize),
                new Color(0.45f, 0.45f, 0.5f, 0.85f));
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
