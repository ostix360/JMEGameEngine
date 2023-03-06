package fr.ostix.game.core.logics.ressourceProcessor;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.graphics.model.MeshModel;
import fr.ostix.game.toolBox.OpenGL.VAO;

public class GLGuiModelRequest extends GLRequest {
    private final float[] vertices;
    private final int[] indices;
    private final float[] textureCoords;

    private MeshModel model;

    private final GLRequestCallback<MeshModel> callback;

    public GLGuiModelRequest(float[] vertices, int[] indices, float[] textureCoords, GLRequestCallback<MeshModel> callback) {
        this.vertices = vertices;
        this.indices = indices;
        this.textureCoords = textureCoords;
        this.callback = callback;
    }

    @Override
    public void execute() {
        model = Loader.INSTANCE.loadToVAO(vertices, textureCoords, indices);
        callback.call(model);
    }

    public MeshModel getModel() {
        return model;
    }
}
