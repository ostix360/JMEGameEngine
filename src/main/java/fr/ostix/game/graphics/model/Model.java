package fr.ostix.game.graphics.model;

import fr.ostix.game.graphics.textures.Texture;

public class Model {

    private final MeshModel meshModel;
    private final Texture texture;

    public Model(MeshModel meshModel, Texture texture) {
        this.meshModel = meshModel;
        this.texture = texture;
    }

    public MeshModel getMeshModel() {
        return meshModel;
    }

    public Texture getTexture() {
        return texture;
    }
}
