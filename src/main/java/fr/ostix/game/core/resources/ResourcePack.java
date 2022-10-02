package fr.ostix.game.core.resources;

import fr.ostix.game.audio.*;
import fr.ostix.game.entity.animated.animation.animatedModel.*;
import fr.ostix.game.entity.animated.animation.animation.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.graphics.textures.*;
import fr.ostix.game.world.chunk.*;

import java.util.*;

public class ResourcePack {
    private static HashMap<String, Texture> textures;
    private final HashMap<String, SoundSource> sounds;
    private final HashMap<String, Model> models;
    private static HashMap<AnimatedModel, HashMap<String, Animation>> animations;
    private final HashMap<String, AnimatedModel> animatedModels;
    private final HashMap<Integer, String> components;

    public ResourcePack(HashMap<String, Texture> textures, HashMap<String, SoundSource> sounds,
                        HashMap<String, Model> models, HashMap<String, AnimatedModel> animatedModelByName,
                        HashMap<AnimatedModel, HashMap<String, Animation>> animations, HashMap<Integer, String> components) {
        ResourcePack.textures = textures;
        this.sounds = sounds;
        this.models = models;
        this.animatedModels = animatedModelByName;
        ResourcePack.animations = animations;
        this.components = components;
        Chunk.setResourcePack(this);
    }

    public static HashMap<String, Texture> getTextureByName() {
        return textures;
    }

    public HashMap<String, SoundSource> getSoundByName() {
        return sounds;
    }

    public Model getModelByName(String model) throws NullPointerException {
        Model m;
        if ((m = models.get(model)) == null){
            throw new NullPointerException("Model " + model + " not found");
        }
        return m;
    }

    public static HashMap<AnimatedModel, HashMap<String, Animation>> getAnimationByName() {
        return animations;
    }

    public HashMap<String, AnimatedModel> getAnimatedModelByName() {
        return animatedModels;
    }


    public HashMap<Integer, String> getComponents() {
        return components;
    }

    public boolean isModelAnimated(String modelName) {
        if (animatedModels.containsKey(modelName)){
            return true;
        }else{
            return false;
        }
    }
}
