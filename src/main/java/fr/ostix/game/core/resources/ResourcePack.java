package fr.ostix.game.core.resources;

import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.entity.animated.animation.animatedModel.AnimatedModel;
import fr.ostix.game.entity.animated.animation.animation.Animation;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.textures.Texture;
import fr.ostix.game.toolBox.Logger;
import fr.ostix.game.world.chunk.Chunk;

import java.util.HashMap;

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

    public static Texture getTextureByName(String name) {
        Texture texture;
        if ((texture = textures.get(name)) == null){
            texture = textures.get("missing");
            Logger.err("Texture " + name + " not found", new Exception());
        }
        return texture;
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
