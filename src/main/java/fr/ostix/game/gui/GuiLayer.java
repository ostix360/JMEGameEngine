package fr.ostix.game.gui;

import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.OpenGL.DisplayManager;
import org.joml.Vector2f;

public class GuiLayer {
    private final Vector2f relativePos;
    private final Vector2f scale;

    private final Color layer;

    public GuiLayer(Vector2f relativePos, Vector2f scale, Color layer) {
        this.relativePos = convertToGL(relativePos);
        this.scale = scale;
        this.layer = layer;
    }

    private Vector2f convertToGL(Vector2f relativePos) {
        return new Vector2f(relativePos.x() / DisplayManager.getWidth(), relativePos.y() / DisplayManager.getHeight());
    }

    public Vector2f getRelativePos() {
        return relativePos;
    }

    public Vector2f getScale() {
        return scale;
    }

    public Color getLayer() {
        return layer;
    }
}
