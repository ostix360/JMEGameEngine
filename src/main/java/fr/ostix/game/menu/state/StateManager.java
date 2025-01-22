package fr.ostix.game.menu.state;

import fr.ostix.game.audio.SoundListener;
import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.core.Game;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.listener.StateListener;
import fr.ostix.game.core.events.listener.sounds.SoundsListener;
import fr.ostix.game.core.events.sounds.StartSoundsEvent;
import fr.ostix.game.core.events.states.StateChangeEvent;
import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.logics.Scheduler;
import fr.ostix.game.core.quest.QuestManager;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.graphics.MasterRenderer;
import fr.ostix.game.graphics.font.meshCreator.FontType;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.graphics.particles.MasterParticle;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.menu.LoaderMenu;
import fr.ostix.game.menu.MainMenu;
import fr.ostix.game.menu.Screen;
import fr.ostix.game.menu.WorldState;
import fr.ostix.game.toolBox.Logger;
import org.joml.Vector3f;
import org.lwjgl.openal.AL10;


public class StateManager {

    private final Loader loader;
    private final WorldState world;
    private ResourcePack pack;
    protected static MainMenu mainMenu;
    private Screen currentScreen;
    private Screen toCleanUp;
    private boolean shouldLoadQuest = true;


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
        playerSoundListener = new SoundListener(new Vector3f());
        soundsListener = new SoundsListener(playerSoundListener);
        EventManager.getInstance().register(soundsListener);

        //EventManager.getInstance().register(keyMainMenuListener);
        //EventManager.getInstance().unRegister(keyMainMenuListener);
    }

    public Screen update() {
        mainMenu.setWorld(world);
        if (!world.isWorldInitialized()) {
            startMenuSounds();
            world.init(pack);
            shouldLoadQuest = true;
            Logger.log("World is Loaded");
            MasterParticle.init(loader, MasterRenderer.getProjectionMatrix());
        }
        if (mainMenu.startWorld) {
            if (shouldLoadQuest) {
                QuestManager.INSTANCE.reload("world");
                shouldLoadQuest = false;
            }
            playerSoundListener.updateTransform(world.getWorld().getPlayer());
        }
        return currentScreen;
    }

    private void startMenuSounds() {
        SoundSource back = pack.getSoundByName().get("menu");
        back.setGain(0.2f);
        back.setPosition(new Vector3f(0, 0, 0));
        back.setLooping(true);
        back.setProperty(AL10.AL_SOURCE_RELATIVE, AL10.AL_TRUE);
//        back.play();
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
