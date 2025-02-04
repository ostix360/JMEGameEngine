package fr.ostix.game.menu;

import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.states.StateChangeEvent;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.menu.component.Button;
import fr.ostix.game.menu.component.HorizontalButton;
import fr.ostix.game.menu.state.States;

public class MainMenu extends Screen {

    public boolean startWorld = false;
    private Button start;

    private WorldState world;

    public MainMenu() {
        super("Main Menu");

    }

    @Override
    public void init() {
        super.init();
        start = new HorizontalButton((float) 1920 / 2 - 125,
                (float) 1080 / 2 - 200, 250, 125,
                ResourcePack.getTextureByName("startButton").getID(), (b) -> {
            startWorld = true;
            start.cleanUp();
            EventManager.getInstance().callEvent(new StateChangeEvent(States.WORLD.getName(), world, 4));

        });
        start.init();

    }

    @Override
    public void open() {
        this.addComponent(start);
    }

    @Override
    public void close() {

    }

    @Override
    public void update() {
        super.update();
    }


    public void setWorld(WorldState world) {
        this.world = world;
    }
}
