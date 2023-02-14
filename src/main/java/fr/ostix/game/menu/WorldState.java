package fr.ostix.game.menu;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.graphics.font.rendering.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.inventory.*;
import fr.ostix.game.world.*;

import static org.lwjgl.glfw.GLFW.*;

public class WorldState extends Screen {
    private final World world;
    public PlayerInventory playerInventory;
    private final InGameMenu hotBar;
    private boolean worldInitialized = false;

    private boolean worldCanBeUpdated = true;
    private boolean openInventory = false;
    private Inventory currentInventory;

    private Screen overWorld;

    public WorldState() {
        super("World");
        world = new World(this);

        hotBar = new InGameMenu();

//        EventManager.getInstance().register(new InventoryListener(this));
    }

    public boolean isWorldInitialized() {
        return worldInitialized;
    }



    public void init(ResourcePack pack) {
        super.init();
        playerInventory = new PlayerInventory("Player Inventory");
        world.initWorld(pack,playerInventory);
        hotBar.init(world.getPlayer());
        worldInitialized = world.isInit();

    }

    public void render() {
        world.render();
        if (overWorld != null) {
            overWorld.render();
        }
    }


    public World getWorld() {
        return world;
    }

    @Override
    public void update() {
        super.update();
        checkInputs();
        if (overWorld != null) {
            overWorld.update();
        }else{
            world.update();
        }
    }

    private void checkInputs() {
//        if (Input.keyPressed(GLFW_KEY_TAB)) {
//            openInventory = !openInventory;
//            worldCanBeUpdated = !openInventory;
//            if (currentInventory != null) currentInventory.close();
//            currentInventory = null;
//        }

        if (Input.keys[GLFW_KEY_ESCAPE]) {
            openInventory = false;
            worldCanBeUpdated = true;
            if (currentInventory != null) currentInventory.close();
            currentInventory = null;
        }
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    public void setWorldCanBeUpdated(boolean worldCanBeUpdated) {
        this.worldCanBeUpdated = worldCanBeUpdated;
    }

    public void setOpenInventory(boolean openInventory) {
        this.openInventory = openInventory;
    }

    public void setCurrentInventory(Inventory currentInventory) {
        this.currentInventory = currentInventory;
    }

    @Override
    public void close() {
        if (hasScreenOverWorld()) {
            overWorld.close();
            overWorld.cleanUp();
            overWorld = null;
        }
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        world.cleanUp();
        worldInitialized = false;
        if (hasScreenOverWorld()) {
            overWorld.close();
            overWorld.cleanUp();
            overWorld = null;
        }

    }

    public void notifyStateOverWorldSet(Screen screen) {
        if (screen != null) {
            if (!screen.isInit()){
                screen.init();
            }
            screen.open();
            world.pause();
        }else{
            world.resume();
        }
        if (overWorld != null) {
            overWorld.close();
            overWorld.cleanUp();
        }
        overWorld = screen;
    }

    public boolean hasScreenOverWorld() {
        return overWorld != null;
    }
}


