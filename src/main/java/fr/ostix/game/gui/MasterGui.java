package fr.ostix.game.gui;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.logics.ressourceProcessor.GLRequestProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MasterGui {
    private static final List<GuiTexture> guis = new ArrayList<>();
    private static final List<GuiTexture> tempGuis = new ArrayList<>();

    private static final HashMap<GuiModel, List<GuiTexture>> guisByModel = new HashMap<>();
    private final GuiRenderer renderer;


    public MasterGui(Loader loader) {
        renderer = new GuiRenderer(loader);
        GuiModel.QUAD.loadModel((bool) -> {
            List<GuiTexture> batch = new ArrayList<>();
            guisByModel.put(GuiModel.QUAD, batch);
        });
        GLRequestProcessor.forceRequest();
        GLRequestProcessor.executeRequest();
    }


    public static void addGui(GuiTexture... gui) {
        guis.addAll(Arrays.asList(gui));
        for (GuiTexture g : gui) {
            addGuiByModel(g);
        }
    }

    public static void addTempGui(GuiTexture... gui) {
        tempGuis.addAll(Arrays.asList(gui));
        guis.addAll(Arrays.asList(gui));
        for (GuiTexture g : gui) {
            addGuiByModel(g);
        }

    }

    public static void removeGui(GuiTexture... gui) {
        guis.removeAll(Arrays.asList(gui));
        for (GuiTexture g : gui) {
            removeGuiByModel(g);
        }
    }

    public void render() {
        renderer.render(guisByModel);
        removeGui(tempGuis.toArray(new GuiTexture[0]));
        tempGuis.clear();
    }

    public static void removeAllGui() {
        guis.clear();
        tempGuis.clear();
        guisByModel.clear();
    }

    private static void addGuiByModel(GuiTexture g) {
        GuiModel model = g.getModel();
        List<GuiTexture> batch = guisByModel.get(model);
        if (batch != null) {
            batch.add(g);
        } else {
            g.getModel().loadModel((bool) -> {
                List<GuiTexture> batch2 = guisByModel.get(model);
                if (batch2 != null) {
                    batch2.add(g);
                }else{
                    List<GuiTexture> newBatch = new ArrayList<>();
                    newBatch.add(g);
                    guisByModel.put(g.getModel(), newBatch);
                }
            });
        }
    }

    public static void removeGuiByModel(GuiTexture g) {
        GuiModel model = g.getModel();
        List<GuiTexture> batch = guisByModel.get(model);
        if (batch != null) {
            batch.remove(g);
        }
    }

    public void cleanUp() {
        removeAllGui();
        renderer.cleanUp();
    }
}
