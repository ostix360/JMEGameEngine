package fr.ostix.game.core.loader;

import fr.ostix.game.audio.*;
import fr.ostix.game.core.loader.json.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.entity.animated.animation.animatedModel.*;
import fr.ostix.game.entity.animated.animation.animation.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.graphics.textures.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.toolBox.OpenGL.*;
import fr.ostix.game.toolBox.*;
import org.joml.*;

import java.io.*;
import java.util.*;

import static fr.ostix.game.toolBox.OpenGL.OpenGlUtils.*;

public class ResourcePackLoader {

    private final Loader loader;
    private final String DATA = "data";
    private final HashMap<String, Texture> textureByName = new HashMap<>();
    private final HashMap<String, SoundSource> soundByName = new HashMap<>();
    private final HashMap<String, Model> modelByName = new HashMap<>();
    private final HashMap<String, AnimatedModel> animatedModelByName = new HashMap<>();
    private final HashMap<AnimatedModel, HashMap<String, Animation>> animationByName = new HashMap<>();
    private final HashMap<Integer, String> componentsByID = new HashMap<>();

    private boolean isLoaded = false;

    public ResourcePackLoader(Loader loader) {
        this.loader = loader;
    }

    public void loadAllResource(MasterGui masterGui) {
        TextureLoader progress = loader.loadTexture("menu/loader/progress");
        Vector2f pos = new Vector2f(150, 1080 - 150);

        ProgressManager.ProgressBar resourcesBar = ProgressManager.addProgressBar("Loading All Resource", 5);

        resourcesBar.update("Loading Textures... ");
        GuiTexture bar = new GuiTexture(progress.getId(), pos,
                new Vector2f((float) (0.2 * (1920 - 180)), 80));
        prepareScreen(masterGui, bar);
        try {
            loadAllTextures();
        } catch (Exception e) {
            Logger.err("Error while loading textures", e);
        }


        resourcesBar.update("Loading Sounds... ");
        bar = new GuiTexture(progress.getId(), pos,
                new Vector2f((float) (0.4 * (1920 - 180)), 80));
        prepareScreen(masterGui, bar);
        try {
            loadAllSounds();
        } catch (Exception e) {
            Logger.err("Error while loading sounds", e);
        }


        resourcesBar.update("Loading Models... ");
        bar = new GuiTexture(progress.getId(), pos,
                new Vector2f((float) (0.6 * (1920 - 180)), 80));
        prepareScreen(masterGui, bar);
        try {
            loadAllModels();
        } catch (Exception e) {
            Logger.err("Error while loading models", e);
        }


        resourcesBar.update("Loading Animations... ");
        bar = new GuiTexture(progress.getId(), pos,
                new Vector2f((float) (0.8 * (1920 - 180)), 80));
        prepareScreen(masterGui, bar);
        try {
            loadAllAnimations();
        } catch (Exception e) {
            Logger.err("Error while loading animations", e);
        }


        resourcesBar.update("Loading Components... ");
        bar = new GuiTexture(progress.getId(), pos,
                new Vector2f((float) (1920 - 180), 80));
        prepareScreen(masterGui, bar);
        try {
            loadAllComponents();
        } catch (Exception e) {
            Logger.err("Error while loading components", e);
        }

        ProgressManager.remove(resourcesBar);
        isLoaded = true;
    }

    private void prepareScreen(MasterGui masterGui, GuiTexture bar) {
        clearGL();
        MasterGui.addTempGui(bar);
        masterGui.render();
        DisplayManager.updateDisplay();
    }

    private void loadAllComponents() throws Exception {
        final File components = new File(ToolDirectory.RES_FOLDER, "/component/");
        ProgressManager.ProgressBar componentsBar = ProgressManager.addProgressBar("Loading All Components", Objects.requireNonNull(components.listFiles()).length);
        for (File currentFile : Objects.requireNonNull(components.listFiles())) {
            String name = currentFile.getName();

            componentsBar.update(name);
            String fileContent = JsonUtils.loadJson(currentFile.getAbsolutePath());
            assert fileContent != null;
            if (fileContent.isEmpty()) {
                throw new Exception("A json a texture is empty... " + currentFile.getAbsolutePath());
            }
            componentsByID.put(Integer.parseInt(name.replaceAll(".component", "")), fileContent);
        }
        ProgressManager.remove(componentsBar);
    }

    private void loadAllTextures() throws Exception {
        File textureFolder = new File(ToolDirectory.RES_FOLDER + "/textures/", DATA);
        List<File> files = new ArrayList<>();
        FileUtils.listFile(textureFolder, files);
        ProgressManager.ProgressBar texturesBar = ProgressManager.addProgressBar("Loading All Textures", files.size());

        for (File currentFile : files) {

            String json = JsonUtils.loadJson(currentFile.getAbsolutePath());
            assert json != null;
            if (json.isEmpty()) {
                throw new Exception("a json a texture is empty... " + currentFile.getAbsolutePath());
            }
            TextureResources current = JsonUtils.gsonInstance().fromJson(json, TextureResources.class);
            if (current == null) {
                throw new NullPointerException("The file cannot " + currentFile.getName() + " be read");
            }

            String name = current.getName();
            texturesBar.update(name);
            TextureProperties prop = current.getTextureProperties();
            if (prop.getNormalMapName() != null) {
                TextureLoader normalMap = loader.loadTexture("entities/normal/" + prop.getNormalMapName());
                prop.setNormalMapID(normalMap.getId());
            }
            if (prop.getSpecularMapName() != null) {
                TextureLoader normalMap = loader.loadTexture("entities/specularMap/" + prop.getSpecularMapName());
                prop.setSpecularMapID(normalMap.getId());
            }
            Texture tex = new Texture(loader.loadTexture(current.getPath()), prop);
            textureByName.put(name, tex);
        }
        ProgressManager.remove(texturesBar);
    }

    private void loadAllSounds() throws Exception {
        File soundFolder = new File(ToolDirectory.RES_FOLDER + "/sounds/", DATA);
        ProgressManager.ProgressBar soundsBar = ProgressManager.addProgressBar("Loading All Sounds", Objects.requireNonNull(soundFolder.listFiles()).length);

        for (File currentFile : Objects.requireNonNull(soundFolder.listFiles())) {
            String json = JsonUtils.loadJson(currentFile.getAbsolutePath());
            assert json != null;
            if (json.isEmpty()) {
                throw new Exception("a json a sound is empty... " + currentFile.getAbsolutePath());
            }
            SoundResources current = JsonUtils.gsonInstance().fromJson(json, SoundResources.class);
            if (current == null) {
                throw new NullPointerException("The file cannot " + currentFile.getName() + " be read");
            }
            String name = current.getName();
            soundsBar.update(name);
            SoundSource source = AudioManager.loadSound(current.getPath(), current.isAmbient());
            soundByName.put(name, source);
        }
        ProgressManager.remove(soundsBar);
    }

    private void loadAllModels() throws Exception {
        File modelFolder = new File(ToolDirectory.RES_FOLDER + "/models/", DATA);
        ProgressManager.ProgressBar modelBar = ProgressManager.addProgressBar("Loading All Models", Objects.requireNonNull(modelFolder.listFiles()).length);

        for (File currentFile : Objects.requireNonNull(modelFolder.listFiles())) {
            String json = JsonUtils.loadJson(currentFile.getAbsolutePath());
            assert json != null;
            if (json.isEmpty()) {
                throw new Exception("a json a model is empty... " + currentFile.getAbsolutePath());
            }
            ModelResources current = JsonUtils.gsonInstance().fromJson(json, ModelResources.class);
            if (current == null) {
                throw new NullPointerException("The file cannot " + currentFile.getName() + " be read");
            }
            String name = current.getName();
            modelBar.update(name);
            Texture texture;
            if (current.getTexture() != null) {
                texture = textureByName.get(current.getTexture());
            } else {
                texture = null;
            }
            if (current.canAnimated()) {
                AnimatedModel model = ResourceLoader.loadTexturedAnimatedModel(current.getPath(), texture, loader);
                animatedModelByName.put(name, model);
            } else {
                Model model = ResourceLoader.loadTexturedModel(current.getPath(), texture, loader);
                modelByName.put(name, model);
            }
        }
        ProgressManager.remove(modelBar);
    }

    private void loadAllAnimations() throws Exception {
        File modelFolder = new File(ToolDirectory.RES_FOLDER + "/animations/", DATA);
        ProgressManager.ProgressBar animationBar = ProgressManager.addProgressBar("Loading All Animations", Objects.requireNonNull(modelFolder.listFiles()).length);

        for (File currentFile : Objects.requireNonNull(modelFolder.listFiles())) {
            String json = JsonUtils.loadJson(currentFile.getAbsolutePath());
            assert json != null;
            if (json.isEmpty()) {
                throw new Exception("a json a model is empty... " + currentFile.getAbsolutePath());
            }
            AnimationResources current = JsonUtils.gsonInstance().fromJson(json, AnimationResources.class);
            if (current == null) {
                throw new NullPointerException("The file cannot " + currentFile.getName() + " be read");
            }
            current.loadAnimation();
            animationBar.update(current.getAnimationName());
            optimizeAnimation(current);
        }
        ProgressManager.remove(animationBar);
    }

    private void optimizeAnimation(AnimationResources current) {
        AnimatedModel model = animatedModelByName.get(current.getModelName());
        HashMap<String, Animation> batch = animationByName.get(model);
        if (batch != null) {
            batch.put(current.getAnimationName(), current.getAnimation());
        } else {
            HashMap<String, Animation> newBatch = new HashMap<>();
            newBatch.put(current.getAnimationName(), current.getAnimation());
            animationByName.put(model, newBatch);
        }

    }

    public boolean isLoaded() {
        return isLoaded;
    }


    public HashMap<String, Texture> getTextureByName() {
        return textureByName;
    }

    public HashMap<String, SoundSource> getSoundByName() {
        return soundByName;
    }

    public HashMap<String, Model> getModelByName() {
        return modelByName;
    }

    public HashMap<String, AnimatedModel> getAnimatedModelByName() {
        return animatedModelByName;
    }

    public HashMap<AnimatedModel, HashMap<String, Animation>> getAnimationByName() {
        return animationByName;
    }

    public HashMap<Integer, String> getComponentsByID() {
        return componentsByID;
    }
}
