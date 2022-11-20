package fr.ostix.game.world;

import com.jme3.bullet.*;
import com.jme3.bullet.control.*;
import fr.ostix.game.audio.*;
import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.entity.*;
import fr.ostix.game.core.events.entity.npc.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.listener.keyListeners.*;
import fr.ostix.game.core.events.listener.player.*;
import fr.ostix.game.core.quest.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.entity.animated.animation.animatedModel.*;
import fr.ostix.game.entity.camera.*;
import fr.ostix.game.entity.component.*;
import fr.ostix.game.entity.component.ai.*;
import fr.ostix.game.entity.component.animation.*;
import fr.ostix.game.entity.component.light.*;
import fr.ostix.game.entity.entities.npc.*;
import fr.ostix.game.graphics.*;
import fr.ostix.game.graphics.font.meshCreator.*;
import fr.ostix.game.graphics.font.rendering.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.graphics.particles.*;
import fr.ostix.game.graphics.particles.particleSpawn.*;
import fr.ostix.game.graphics.textures.*;
import fr.ostix.game.inventory.*;
import fr.ostix.game.menu.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.world.chunk.*;
import fr.ostix.game.world.texture.*;
import fr.ostix.game.world.water.*;
import fr.ostix.game.world.weather.*;
import org.joml.*;
import org.lwjgl.openal.*;

import java.lang.Math;
import java.util.Random;
import java.util.*;
import java.util.concurrent.*;

public class World {

    private MasterRenderer renderer;

    private boolean isInit = false;

    public static final int MAX_LIGHTS = 11;


    private static final List<Entity> entities = new ArrayList<>();
    private static final List<Entity> aabbs = new ArrayList<>();
    private static final List<Light> lights = new ArrayList<>();
    private static final Map<Vector2f, Chunk> worldChunk = new ConcurrentHashMap<>();
    public static Model CUBE;
    private final List<WaterTile> waterTiles = new ArrayList<>();
    private final List<Listener> worldListeners = new ArrayList<>();

    private ChunkHandler chunkHandler;

    private SoundListener listener;
    private Player player;
    private Camera cam;
    private Weather weather;
    private float time = 0000.0F;
    private final WorldState state;

    private static final List<Action> toDo = new ArrayList<>();

    private KeyInGameListener keyWorldListener;

    private static final BulletAppState physics = new BulletAppState();
    private final List<Entity> entitiesNear = new ArrayList<>();

    public World(WorldState state) {
        this.state = state;

        physics.startPhysics();
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

        physics.setDebugEnabled(true);
        physics.getPhysicsSpace().setGravity(new Vector3f(0,-9.81f,0).mul(10f));



        AnimatedModel an = pack.getAnimatedModelByName().get("player2");
        player = new Player(an, new Vector3f(1450, 70, 2250), new Vector3f(0), 0.5f);
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
        keyWorldListener = new KeyInGameListener(this, this.getPlayer(), player.getInventory());
        PlayerInteractListener PLI = new PlayerInteractListener(this, entities);
        this.worldListeners.add(keyWorldListener);
        this.worldListeners.add(PL);
        this.worldListeners.add(PLI);


        listener = new SoundListener(player.getPosition(), new Vector3f(), player.getRotation());
        cam = new Camera(player);
        chunkHandler = new ChunkHandler(cam, worldChunk, this);

        weather = new Weather(cam);
        renderer = new MasterRenderer(weather,worldChunk);



        entities.add(player);
        CUBE = pack.getModelByName("cube");

        Model test = pack.getModelByName("fontaine");
        Entity testE = new Entity(45,test, new Vector3f(5, getTerrainHeight(2050, 2050), 5), new Vector3f(0, 0, 0), 1f);
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



        SoundSource back = pack.getSoundByName().get("ambient");

        SoundSource back2 = AudioManager.loadSound("test1", true);
//        initTerrain(worldChunk);

        back.setGain(0.2f);
        back.setPosition(new Vector3f(0, 0, 0));
        back.setLooping(true);
        back.setProperty(AL10.AL_SOURCE_RELATIVE, AL10.AL_TRUE);
//        back.play();
        back2.setGain(0.2f);
        back2.setPosition(new Vector3f(0, 0, 0));
        back2.setLooping(true);
        back2.setProperty(AL10.AL_SOURCE_RELATIVE, AL10.AL_TRUE);
        // back2.play();

        Registered.registerNPC(NPCGod.getInstance());

        chunkHandler.run();


        isInit = true;
    }

    private void initWater() {
        float waterHeight = -10f;
        waterTiles.add(new WaterTile(5, 5, waterHeight));
    }

    private void initTerrain(Map<Vector2f, Chunk> worldChunk) {
        TerrainTexture backgroundTexture = new TerrainTexture(ResourcePack.getTextureByName().get("grassy2").getID());
        TerrainTexture rTexture = new TerrainTexture(ResourcePack.getTextureByName().get("mud").getID());
        TerrainTexture gTexture = new TerrainTexture(ResourcePack.getTextureByName().get("grassFlowers").getID());
        TerrainTexture bTexture = new TerrainTexture(ResourcePack.getTextureByName().get("path").getID());

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(ResourcePack.getTextureByName().get("blendMap").getID());

//        worldIndex = new int[2][2];
        for (int x = 0; x < 1; x++) {
            for (int z = 0; z < 1; z++) {
                worldChunk.put(new Vector2f(x,z),new Chunk(x,z,new ArrayList<>()).setTerrain(new Terrain(x, z, texturePack, blendMap, new float[][]{})));
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
            te.setPosition(new Vector3f(x,0,z));
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
        keyWorldListener.update();
        cam.move();

        physics.update(1/60f);


        //entity.increaseRotation(new Vector3f(0, 1, 0));



//        if (Input.keys[GLFW.GLFW_KEY_O]) {
            updateTime();
//        }
        for (Entity e : entities) {
            e.update();
        }
        weather.update(time);


        listener.updateTransform(player.getPosition(), player.getRotation());
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
        physics.render();
        renderer.renderScene(entities, waterTiles, lights, cam);
        MasterParticle.render(cam);
    }

    public static float getTerrainHeight(float worldX, float worldZ) {
        int x = (int) Math.floor(worldX / Terrain.getSIZE());
        int z = (int) Math.floor(worldZ / Terrain.getSIZE());
        try {
            return worldChunk.get(new Vector2f(x,z)).getTerrain().getHeightOfTerrain(worldX, worldZ);
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
        physics.stopPhysics();
        chunkHandler.exit();
        renderer.cleanUp();
        MasterParticle.cleanUp();
    }

    public void pause(){
        EventManager.getInstance().removeAll(worldListeners);
        state.setWorldCanBeUpdated(false);
        player.getControl().setWalkDirection(new Vector3f(0));
    }

    public void resume(){
        worldListeners.forEach(wl -> EventManager.getInstance().register(wl));
        state.setWorldCanBeUpdated(true);

    }
    public static void addToDo(Action action){
        toDo.add(action);
    }

    public static void removeToDo(Action action){
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
}