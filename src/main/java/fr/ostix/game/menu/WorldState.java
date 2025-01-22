package fr.ostix.game.menu;

import fr.ostix.game.core.Input;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.inventory.Inventory;
import fr.ostix.game.inventory.PlayerInventory;
import fr.ostix.game.menu.ingame.InGameMenu;
import fr.ostix.game.world.World;
import fr.ostix.game.world.io.WorldLoader;
import fr.ostix.game.world.io.WorldSaver;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class WorldState extends Screen {
    private final World world;
    public PlayerInventory playerInventory;
    private final InGameMenu hotBar;
    private boolean worldInitialized = false;

    private boolean worldCanBeUpdated = true;
    private boolean openInventory = false;
    private Inventory currentInventory;

    private Screen overWorld;

    private final WorldLoader worldLoader;

    private final WorldSaver worldSaver;

    public WorldState() {
        super("World");
        world = new World(this);

        hotBar = new InGameMenu();

        worldLoader = new WorldLoader(world, "world");
        worldSaver = new WorldSaver(world, "world");
//        EventManager.getInstance().register(new InventoryListener(this));
    }

    public boolean isWorldInitialized() {
        return worldInitialized;
    }


    public void init(ResourcePack pack) {
        super.init();
        playerInventory = new PlayerInventory("Player Inventory");
        world.initWorld(pack, playerInventory);
        hotBar.init(world.getPlayer());
        worldInitialized = world.isInit();
        worldLoader.loadWorld();
        world.setTime(worldLoader.getTime());
        world.resume();
    }

    public void render() {
        world.render();
        if (overWorld != null) {
            overWorld.render();
        }else{
            hotBar.render();
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
        } else {
            world.update();
            hotBar.update();
        }
    }

    private void checkInputs() {
//        if (Input.keyPressed(GLFW_KEY_TAB)) {
//            openInventory = !openInventory;
//            worldCanBeUpdated = !openInventory;
//            if (currentInventory != null) currentInventory.close();
//            currentInventory = null;
//        }
        this.world.keyWorldListener.update();
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
        worldSaver.saveWorld();
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
        hotBar.cleanUp();

    }

    public void notifyStateOverWorldSet(Screen screen) {
        if (screen != null) {
            if (overWorld != null) {
                overWorld.close();
                overWorld.cleanUp();
            }
            if (!screen.isInit()) {
                screen.init();
            }
            screen.open();
            world.pause();
        } else {
            overWorld.close();
            overWorld.cleanUp();
            world.resume();
        }
        overWorld = screen;
    }

    public boolean hasScreenOverWorld() {
        return overWorld != null;
    }
}


