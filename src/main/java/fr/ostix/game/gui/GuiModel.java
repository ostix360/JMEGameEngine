package fr.ostix.game.gui;

import fr.ostix.game.core.logics.Callback;
import fr.ostix.game.core.logics.ressourceProcessor.GLGuiModelRequest;
import fr.ostix.game.core.logics.ressourceProcessor.GLRequestProcessor;
import fr.ostix.game.toolBox.OpenGL.VAO;

public class GuiModel {
    public static final GuiModel QUAD = new GuiModel(new float[]{
            0, 0,
            0, 1,
            1, 0,
            1, 1
    });


    private final float[] vertices = new float[]{
            -1, 1, 0,
            -1, -1, 0,
            1, 1, 0,
            1, -1, 0
    };
    private final int[] indices = new int[]{
            0, 1, 2,
            2, 1, 3
    };
    private final float[] textureCoords;


    private VAO vao;

    public GuiModel(float[] textureCoords) {
        this.textureCoords = textureCoords;
    }

    public void loadModel(Callback<Boolean> callback) {
        GLRequestProcessor.sendRequest(new GLGuiModelRequest(vertices, indices, textureCoords, (model) -> {
            this.vao = model.getVAO();
            callback.call(true);
        }));
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public VAO getVao() {
        return vao;
    }
}
