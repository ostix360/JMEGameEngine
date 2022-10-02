package fr.ostix.game.menu;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.keyListeners.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.menu.component.*;
import fr.ostix.game.world.*;

public class MainMenu extends Screen {

    public boolean startWorld = false;
    private Button start;

    private World world;

    public MainMenu() {
        super("Main Menu");

    }

    @Override
    public void init() {
        super.init();
        start = new Button((float) 1920 / 2 - 125,
                (float) 1080 / 2 - 200, 250, 125,
                ResourcePack.getTextureByName().get("startButton").getID(), (b) -> {
            startWorld = true;
            start.cleanUp();
            world.resume();

        });
        this.addComponent(start);

    }

    @Override
    public void update() {
        super.update();
    }


    public void setWorld(World world) {
        this.world = world;
    }
}
