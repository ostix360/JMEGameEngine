package fr.ostix.game.menu.component;

import fr.ostix.game.gui.GuiModel;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.toolBox.Color;
import org.joml.Vector2f;

public class VerticalButton extends Button {

    /**
     * The relative position of the button
     */
    private final float r;


    /**
     * Create an horizontal button
     * @param x the x position of the button
     * @param y the y position of the button
     * @param width the width of the texture
     * @param height the height of the texture
     * @param texture the texture of the button
     * @param press the action to do when the button is pressed
     */
    public VerticalButton(float x, float y, float width, float height, int texture, IPressable press) {
        super(x, y, width, height, texture, press);
        int textureSize = (int) height;
        this.r = (textureSize - height) / 2f;
        float ratio = this.width / (float) textureSize;
        this.texture = new GuiTexture(texture, new Vector2f(x, y), new Vector2f(this.width, this.height));
        final float[] texCoords = new float[]{
                0, 0,
                0, 1,
                ratio, 0,
                ratio, 1
        };
        this.texture.setModel(new GuiModel(texCoords));

        this.texture.setLayer(new Color(0.9f, 0.45f, 0.5f, 0.80f));
    }





}
