package fr.ostix.game.menu.stat;

import fr.ostix.game.audio.*;
import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.listener.sounds.*;
import fr.ostix.game.core.events.sounds.*;
import fr.ostix.game.core.events.states.*;
import fr.ostix.game.core.loader.*;
import fr.ostix.game.core.logics.Scheduler;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.graphics.*;
import fr.ostix.game.graphics.font.meshCreator.*;
import fr.ostix.game.graphics.font.rendering.*;
import fr.ostix.game.graphics.particles.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.menu.*;
import fr.ostix.game.toolBox.*;
import org.joml.*;
import org.lwjgl.openal.*;


public class StateManager {

    private final Loader loader;
    private final WorldState world;
    private ResourcePack pack;
    protected static MainMenu mainMenu;
    private Screen currentScreen;
    private Screen toCleanUp;


    private final StateListener stateListener;

    private SoundListener playerSoundListener;

    private SoundsListener soundsListener;

    private String currentStatName;

    public StateManager(Loader loader) {
        this.loader = loader;
        world = new WorldState();
        this.stateListener = new StateListener(this);
        EventManager.getInstance().register(stateListener);
    }

    public void init(MasterGui masterGui) {
        LoaderMenu loaderMenu = new LoaderMenu();
        loaderMenu.init(loader, masterGui);
        loaderMenu.cleanUp();
        pack = loaderMenu.getPack();
        Game.gameFont = new FontType(ResourcePack.getTextureByName("candara").getID(), "candara");

//        QuestLoader.loadAllQuest();
        mainMenu = new MainMenu();

        EventManager.getInstance().callEvent(new StateChangeEvent(States.MAIN_MENU.getName(), mainMenu, 4));
//        Listener keyMainMenuListener = new KeyMenuListener(mainMenu);
//


        //EventManager.getInstance().register(keyMainMenuListener);
        //EventManager.getInstance().unRegister(keyMainMenuListener);
    }

    public Screen update() {
        if (!world.isWorldInitialized()) {
            world.init(pack);
            MasterParticle.init(loader, MasterRenderer.getProjectionMatrix());
            Logger.log("World is Loaded");
            Player player = world.getWorld().getPlayer();
            mainMenu.setWorld(world);
            playerSoundListener = new SoundListener(player);
            soundsListener = new SoundsListener(playerSoundListener);
            EventManager.getInstance().register(soundsListener);
            startMenuSounds();
        }
        if (mainMenu.startWorld) {
            playerSoundListener.updateTransform();
        }
        return currentScreen;
    }

    private void startMenuSounds() {
        SoundSource back = pack.getSoundByName().get("menu");
        back.setGain(0.2f);
        back.setPosition(new Vector3f(0, 0, 0));
        back.setLooping(true);
        back.setProperty(AL10.AL_SOURCE_RELATIVE, AL10.AL_TRUE);
        EventManager.getInstance().callEvent(new StartSoundsEvent(2, back));
    }


    public void cleanUp() {
        world.cleanUp();
        mainMenu.cleanUp();
        EventManager.getInstance().unRegister(stateListener);
        EventManager.getInstance().unRegister(soundsListener);
    }

    public void setCurrentScreen(Screen gui) {
        currentScreen = gui;
    }

    public void notifyStatChange(String stat, Screen screen) {
        this.currentStatName = stat;
        if (!screen.isInit()) {
            screen.init();
        }
        if (currentScreen != null) {
            this.toCleanUp = currentScreen;
            Scheduler.schedule(() -> {
                toCleanUp.close();
                this.toCleanUp.cleanUp();
                this.toCleanUp = null;
            }, 400);
        }
        MasterFont.clear();
        MasterGui.removeAllGui();
        setCurrentScreen(screen);

        screen.open();
    }


    public void notifyStateOverWorldSet(Screen screen) {
        this.currentStatName = States.WORLD.getName();
        world.notifyStateOverWorldSet(screen);
    }
}
