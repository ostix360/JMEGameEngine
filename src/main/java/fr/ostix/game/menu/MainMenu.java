package fr.ostix.game.menu;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.keyListeners.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.menu.component.*;

public class MainMenu extends Screen {

    public boolean startWorld = false;
    private Button start;
    private KeyInGameListener keyInGameListener;

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
            EventManager.getInstance().register(keyInGameListener);
        });
        this.addComponent(start);

    }

    @Override
    public void update() {
        super.update();
    }

    public void setKeyWorldListener(KeyInGameListener keyWorldListener) {
        this.keyInGameListener = keyWorldListener;
    }
}
