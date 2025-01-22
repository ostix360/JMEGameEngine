package fr.ostix.game.world;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.core.Game;
import fr.ostix.game.core.Registered;
import fr.ostix.game.core.camera.Camera;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.listener.Listener;
import fr.ostix.game.core.events.listener.keyListeners.KeyInGameListener;
import fr.ostix.game.core.events.listener.player.PlayerInteractListener;
import fr.ostix.game.core.events.listener.player.PlayerListener;
import fr.ostix.game.core.events.sounds.StartSoundsEvent;
import fr.ostix.game.core.logics.ressourceProcessor.GLRequestProcessor;
import fr.ostix.game.core.logics.ressourceProcessor.GLRunnableRequest;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.Player;
import fr.ostix.game.entity.animated.animation.animatedModel.AnimatedModel;
import fr.ostix.game.entity.component.LoadComponents;
import fr.ostix.game.entity.component.ai.AIProperties;
import fr.ostix.game.entity.component.animation.AnimationComponent;
import fr.ostix.game.entity.component.light.Light;
import fr.ostix.game.entity.entities.npc.NPCGod;
import fr.ostix.game.graphics.MasterRenderer;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.particles.*;
import fr.ostix.game.graphics.particles.particleSpawn.Sphere;
import fr.ostix.game.graphics.textures.Texture;
import fr.ostix.game.inventory.PlayerInventory;
import fr.ostix.game.menu.WorldState;
import fr.ostix.game.toolBox.Action;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.OpenGL.OpenGlUtils;
import fr.ostix.game.world.chunk.Chunk;
import fr.ostix.game.world.chunk.ChunkHandler;
import fr.ostix.game.world.texture.TerrainTexture;
import fr.ostix.game.world.texture.TerrainTexturePack;
import fr.ostix.game.world.water.WaterTile;
import fr.ostix.game.world.weather.Weather;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.openal.AL10;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class World {

    private MasterRenderer renderer;

    private boolean isInit = false;

    public static final int MAX_LIGHTS = 11;


    private static final List<Entity> entities = new CopyOnWriteArrayList<>();
    private static final List<Entity> aabbs = new ArrayList<>();
    private static final List<Light> lights = new ArrayList<>();
    private static final Map<Vector2f, Chunk> worldChunk = new ConcurrentHashMap<>();
    public static Model CUBE;
    private final List<WaterTile> waterTiles = new ArrayList<>();
    private final List<Listener> worldListeners = new ArrayList<>();

    private ChunkHandler chunkHandler;

    private Player player;
    private Camera cam;
    private Weather weather;
    private float time = 0000.0F;
    private final WorldState state;

    private static final List<Action> toDo = new ArrayList<>();

    public KeyInGameListener keyWorldListener;

    private static final BulletAppState physics = new BulletAppState();
    private final List<Entity> entitiesNear = new ArrayList<>();

    public World(WorldState state) {
        this.state = state;


    }

    public static void addLight(Light light) {
        lights.add(light);
    }

//    public static Entity addAABB(Vector3 bodyPosition, Vector3 size) {
//        Entity entity = new Entity(-1, CUBE, Maths.toVector3f(bodyPosition), new Vector3f(), 1);
//        entity.setScale(Maths.toVector3f(size));
//        aabbs.add(entity);
//        return entity;
//    }

    public static void doAABBToRender() {
        entities.addAll(aabbs);
    }

    public final void initWorld(ResourcePack pack, PlayerInventory playerInventory) {
        HashMap<String, Texture> textures = ResourcePack.getTextureByName();
        physics.startPhysics();
        physics.setDebugEnabled(true);
        physics.getPhysicsSpace().setGravity(new Vector3f(0, -9.81f, 0).mul(10f));


        AnimatedModel an = pack.getAnimatedModelByName().get("player2");
        player = new Player(an, new Vector3f(1450, 30, 2250), new Vector3f(0), 0.5f);
        player.setInventory(playerInventory);
        ParticleTargetProperties targetProperties = new ParticleTargetProperties(0, 6, 0, 80, 6);
        ParticleSystem system = new ParticleSystem(new ParticleTexture(textures.get("fire").getID(), 8, true),
                4000, 0.1f, -0, 60 * 2.2f, 4);
        system.randomizeRotation();
        system.setLifeError(0.2f);
        system.setDirection(new Vector3f(0, 0.1f, 0), 0.01f);
        system.setTarget(new ParticleTarget(targetProperties, player));
        system.setPositionOffset(new Vector3f(0, 4, 25));
        system.setSpeedError(0.5f);
        system.setScaleError(0.1f);
        system.setSpawn(((Sphere) SpawnParticleType.SPHERE.getSpawn()).setRadius(10));
        AIProperties ai = new AIProperties(2f, 1, 0.25f, 0.25f, 0.65f, 6, 3, new Vector3f(200, 5, 200), 30);
        //player.addComponent(new AIComponent(player, ai));
        // player.addComponent(new ParticleComponent(system, player));
        player.addComponent(new AnimationComponent(player, ResourcePack.getAnimationByName().get(an)));
//        CollisionComponent cp = (CollisionComponent) ComponentType.COLLISION_COMPONENT.loadComponent(player, pack.getComponents().get(-1940279936));
//        player.setCollision(cp);
        physics.getPhysicsSpace().add(player);

        PlayerListener PL = new PlayerListener();
        keyWorldListener = new KeyInGameListener(state, this.getPlayer(), player.getInventory());
        PlayerInteractListener PLI = new PlayerInteractListener(this, entities);
        EventManager.getInstance().register(keyWorldListener);
        this.worldListeners.add(PL);
        this.worldListeners.add(PLI);


        cam = new Camera(player);
        chunkHandler = new ChunkHandler(cam, worldChunk, this);

        weather = new Weather(cam);
        renderer = new MasterRenderer(weather, worldChunk);


        entities.add(player);
        CUBE = pack.getModelByName("cube");

        Model test = pack.getModelByName("fontaine");
        Entity testE = new Entity(45, test, new Vector3f(5, getTerrainHeight(2050, 2050), 5), new Vector3f(0, 0, 0), 1f);
        LoadComponents.loadComponents(pack.getComponents().get(1995130752), testE);
//        entities.add(testE);

//        testE.setPhysic(new RigidBodyControl(0));
//        physics.getPhysicsSpace().add(testE);
//        this.initEntity(testE);
//
//        Model cube = models.get("box");
//        Entity cubeE = new Entity(cube, new Vector3f(50, 0, 20), new Vector3f(2000, 90, 2000), 20);
//        LoadComponents.loadComponents(pack.getComponents().get(2026772471), cubeE);
//        entities.add(cubeE);


        Light sun = new Light(new Vector3f(100000, 100000, -100000), Color.SUN, 0.5f, null);
        //lights.add(sun);
        //  lights.add(sunc);

//        initEntity();
        initWater();
        GUIText text1 = new GUIText("Bienvenue dans ce jeu magique", 1f, Game.gameFont, new Vector2f(0, 0), 1920f, true);
        text1.setColour(Color.RED);
        MasterFont.add(text1);


        SoundSource back2 = pack.getSoundByName().get("worldMusic");
//        initTerrain(worldChunk);

        back2.setGain(0.2f);
        back2.setPosition(new Vector3f(0, 0, 0));
        back2.setLooping(true);
        back2.setProperty(AL10.AL_SOURCE_RELATIVE, AL10.AL_TRUE);
        addToDo(w -> EventManager.getInstance().callEvent(new StartSoundsEvent(2, back2)));


        Registered.registerNPC(NPCGod.getInstance());

        chunkHandler.run();


        isInit = true;
    }

    private void initWater() {
        float waterHeight = -10f;
        waterTiles.add(new WaterTile(5, 5, waterHeight));
    }

    private void initTerrain(Map<Vector2f, Chunk> worldChunk) {
        TerrainTexture backgroundTexture = new TerrainTexture(ResourcePack.getTextureByName("grassy2").getID());
        TerrainTexture rTexture = new TerrainTexture(ResourcePack.getTextureByName("mud").getID());
        TerrainTexture gTexture = new TerrainTexture(ResourcePack.getTextureByName("grassFlowers").getID());
        TerrainTexture bTexture = new TerrainTexture(ResourcePack.getTextureByName("path").getID());

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(ResourcePack.getTextureByName("blendMap").getID());

//        worldIndex = new int[2][2];
        for (int x = 0; x < 1; x++) {
            for (int z = 0; z < 1; z++) {
                worldChunk.put(new Vector2f(x, z), new Chunk(x, z, new ArrayList<>()).setTerrain(new Terrain(x, z, texturePack, blendMap, new float[][]{})));
//                worldIndex[x][z] = index;
            }
        }

    }


    private void initEntity(Entity e) {


        // Model treeModel = new Model(LoadModel.loadModel("tree",".dae", loader), new TextureModel(loader.loadTexture("tree")).setTransparency(true));
//        Model grassModel = new Model(LoadModel.loadModel("grassModel", loader),
//                new TextureModel(loader.loadTexture("flower")));
//        grassModel.getTexture().setTransparency(true).setUseFakeLighting(true);
//        Model fernModel = new Model(LoadModel.loadModel("fern", loader),
//                new TextureModel(loader.loadTexture("fern")));
//        fernModel.getTexture().setTransparency(true);
        Random r = new Random();
        for (int i = 0; i < 2000; i++) {
            float x = r.nextFloat() * 400;
            float z = r.nextFloat() * 400;
            Entity te = new Entity(e);
            te.setPosition(new Vector3f(x, 0, z));
            entities.add(te);
            te.setPhysic(new RigidBodyControl(0));
            physics.getPhysicsSpace().add(te);
//            entities.add(new Entity(treeModel, new Vector3f(x, getTerrainHeight(x, z), z),
//                    new Vector3f(0, 0, 0), 1f));
//            x = r.nextFloat() * 1600;
//            z = r.nextFloat() * 1600;
//            entities.add(new Entity(grassModel, new Vector3f(x, getTerrainHeight( x, z),z),
//                    new Vector3f(0, 0, 0), 0.6f));
//            x = r.nextFloat() * 1600;
//            z = r.nextFloat() * 1600;
//            entities.add(new Entity(fernModel, new Vector3f(x, getTerrainHeight(x, z),z),
//                    new Vector3f(0, 0, 0), 5f));
        }
    }


    public void update() {

        cam.move();

        physics.update(1 / 60f);


        //entity.increaseRotation(new Vector3f(0, 1, 0));


//        if (Input.keys[GLFW.GLFW_KEY_O]) {
        updateTime();
//        }
        for (Entity e : entities) {
            e.update();
        }
        weather.update(time);

        MasterParticle.update(cam);

        entities.clear();
        entities.addAll(chunkHandler.getEntities());
        entities.add(player);
        toDo.forEach(a -> a.action(this));
        toDo.clear();
    }

    private void updateTime() {
        time += 1 / 60f * 80;
        time %= 24000;
    }


    public void render() {
        OpenGlUtils.goWireframe(false);
        physics.render();
        renderer.renderScene(entities, waterTiles, lights, cam);
        OpenGlUtils.goWireframe(false);
        MasterParticle.render(cam);
    }

    public static float getTerrainHeight(float worldX, float worldZ) {
        int x = (int) Math.floor(worldX / Terrain.getSIZE());
        int z = (int) Math.floor(worldZ / Terrain.getSIZE());
        try {
            return worldChunk.get(new Vector2f(x, z)).getTerrain().getHeightOfTerrain(worldX, worldZ);
        } catch (Exception e) {
            //Logger.err("World doesn't exist in this coordinates xIndex : " + x + " ZIndex : " + z);
        }
        return 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void cleanUp() {
        EventManager.getInstance().removeAll(worldListeners);
        EventManager.getInstance().unRegister(keyWorldListener);
        entities.forEach(e -> physics.getPhysicsSpace().remove(e));
        entities.forEach(Entity::cleanUp);
        physics.stopPhysics();
        chunkHandler.exit();
        worldChunk.clear();
        entities.clear();
        lights.clear();
        toDo.clear();
        renderer.cleanUp();
        GLRequestProcessor.sendRequest(new GLRunnableRequest(MasterParticle::cleanUp));

    }

    public void pause() {
        EventManager.getInstance().removeAll(worldListeners);
        state.setWorldCanBeUpdated(false);
        player.getControl().setWalkDirection(new Vector3f(0));
    }

    public void resume() {
        worldListeners.forEach(wl -> EventManager.getInstance().register(wl));
        state.setWorldCanBeUpdated(true);

    }

    public static void addToDo(Action action) {
        toDo.add(action);
    }

    public static void removeToDo(Action action) {
        toDo.remove(action);
    }

    public boolean isInit() {
        return isInit;
    }

    public List<Entity> getEntitiesNear() {
        return entitiesNear;
    }

    public Camera getCamera() {
        return cam;
    }

    public static PhysicsSpace getPhysics() {
        return physics.getPhysicsSpace();
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
