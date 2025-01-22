package fr.ostix.game.menu.state;

import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.states.StateChangeEvent;
import fr.ostix.game.core.events.states.StateOverWorldEvent;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.menu.Screen;
import fr.ostix.game.menu.component.Button;
import fr.ostix.game.menu.component.HorizontalButton;

public class PauseMenu extends Screen {

    private Button resumeButton;
    private Button quitButton;
    private Button settingsButton;

    public PauseMenu() {
        super("Pause");
    }

    @Override
    public void init() {
        super.init();
        resumeButton = new HorizontalButton((float) 1920 / 2 - 150,
                (float) 1080 / 2 - 200, 300, 100,
                ResourcePack.getTextureByName("resumeButton").getID(), 300,(b) -> {
            resume();
        });
        settingsButton = new HorizontalButton((float) 1920 / 2 - 177.5f,
                (float) 1080 / 2 - 50, 355, 95,
                ResourcePack.getTextureByName("settingsButton").getID(), 400, (b) -> {
            settings();

        });
        quitButton = new HorizontalButton((float) 1920 / 2 - 107.5f,
                (float) 1080 / 2 + 100, 215, 95,
                ResourcePack.getTextureByName("quitButton").getID(), 255,(b) -> {
            quit();
        });
        this.addComponent(resumeButton);
        this.addComponent(quitButton);
        this.addComponent(settingsButton);
    }

    private void resume() {
        EventManager.getInstance().callEvent(new StateOverWorldEvent("World", null, 1));
    }

    private void quit() {
        EventManager.getInstance().callEvent(new StateChangeEvent("MainMenu", StateManager.mainMenu, 1));
        this.cleanUp();
    }

    private void settings() {
//      EventManager.getInstance().callEvent(new SettingsEvent());
    }

    @Override
    public void update() {
        super.update();
    }


    @Override
    public void cleanUp() {
        super.cleanUp();
    }
}
