package fr.ostix.game.gui;

import fr.ostix.game.toolBox.*;
import fr.ostix.game.toolBox.OpenGL.*;
import org.joml.*;

public class GuiTexture {
    private Vector2f glPos;
    private Vector2f glScale;

    private Vector2f pos;
    private Vector2f scale;

    private final int texture;
    private Color layer;
    private boolean hasLayer = false;

    private GuiModel model = GuiModel.QUAD;

    /**
     * @param position correspond a une pourcentage au niveau de la fenetre sur 1000
     */
    public GuiTexture(int texture, Vector2f position, Vector2f scale) {
        this.pos = position;
        this.scale = scale;
        this.texture = texture;

        convertPercentToPos();
        convertScale();
    }

    private void convertPercentToPos() {
        Vector2f newPos = new Vector2f(pos.x() * DisplayManager.getWidth() / 1920, pos.y() * DisplayManager.getHeight() / 1080);
        Vector2f newScale = new Vector2f(scale.x() * DisplayManager.getWidth() / 1920, scale.y() * DisplayManager.getHeight() / 1080);
        convertPos(newPos, newScale);
    }

    private void convertPos(Vector2f pos, Vector2f scale) {
        convertCoordinates(new Vector2f(pos.x() + scale.x() / 2, pos.y() + scale.y() / 2));
    }

    private void convertCoordinates(Vector2f value) {
        glPos = new Vector2f(value.x() / DisplayManager.getWidth() * 2 - 1, 1 - (value.y() / DisplayManager.getHeight() * 2));
    }

    private void convertScale() {
        glScale = new Vector2f(scale.x() / 1920, scale.y() / 1080);
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
        convertPercentToPos();
        convertScale();
    }

    public void setPosition(Vector2f position) {
        this.pos = position;
        convertPercentToPos();
    }

    public Vector2f getPosition() {
        return glPos;
    }

    public Vector2f getGlScale() {
        return glScale;
    }

    public int getTexture() {
        return texture;
    }

    public boolean hasLayer() {
        return hasLayer;
    }

    public void hasLayer(boolean value) {
        hasLayer = value;
    }

    public Color getLayer() {
        if (hasLayer) {
            return layer;
        } else {
            return Color.NONE;
        }
    }

    public void setLayer(Color layer) {
        this.layer = layer;
        hasLayer = true;
    }

    public void setModel(GuiModel model) {
        this.model = model;
    }

    public GuiModel getModel() {
        return model;
    }


}

