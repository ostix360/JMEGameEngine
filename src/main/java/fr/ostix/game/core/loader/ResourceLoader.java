package fr.ostix.game.core.loader;

import fr.ostix.game.entity.animated.animation.animatedModel.*;
import fr.ostix.game.entity.animated.animation.loaders.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.graphics.textures.*;

public class ResourceLoader {


    public static Model loadTexturedModel(String path, Texture texture, Loader loader) {
        if (texture == null) {
            new IllegalArgumentException("texture for the model " + path + "is not available ");
        }
        assert texture != null;
        return new Model(LoadMeshModel.loadModel(path, loader), texture);
    }

    public static AnimatedModel loadTexturedAnimatedModel(String path, Texture texture, Loader loader) {
        if (texture == null) {
            new IllegalArgumentException("texture for the model " + path + "is not available ");
        }
        assert texture != null;
        return AnimatedModelLoader.loadEntity(path, texture, 3, loader);
    }
}
