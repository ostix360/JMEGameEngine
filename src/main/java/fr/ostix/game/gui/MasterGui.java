package fr.ostix.game.gui;

import fr.ostix.game.core.loader.*;

import java.util.*;

public class MasterGui {
    private static final List<GuiTexture> guis = new ArrayList<>();
    private static final List<GuiTexture> tempGuis = new ArrayList<>();
    private final GuiRenderer renderer;


    public MasterGui(Loader loader) {
        renderer = new GuiRenderer(loader);
    }

    public static void addGui(GuiTexture... gui) {
        guis.addAll(Arrays.asList(gui));
    }

    public static void addTempGui(GuiTexture... gui) {
        tempGuis.addAll(Arrays.asList(gui));
        guis.addAll(Arrays.asList(gui));
    }

    public static void removeGui(GuiTexture... gui) {
        guis.removeAll(Arrays.asList(gui));
    }

    public void render() {
        renderer.render(guis);
        guis.removeAll(tempGuis);
        tempGuis.clear();
    }

    public void removeAllGui() {
        guis.clear();
    }

    public void cleanUp() {
        removeAllGui();
        renderer.cleanUp();
    }
}
