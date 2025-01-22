package fr.ostix.game.graphics;

import fr.ostix.game.core.camera.Camera;
import fr.ostix.game.core.logics.ressourceProcessor.GLRequestProcessor;
import fr.ostix.game.core.logics.ressourceProcessor.GLRunnableRequest;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.component.light.Light;
import fr.ostix.game.graphics.entity.ClassicShader;
import fr.ostix.game.graphics.entity.EntityRenderer;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.skybox.SkyboxRenderer;
import fr.ostix.game.graphics.terrain.TerrainRenderer;
import fr.ostix.game.graphics.terrain.TerrainShader;
import fr.ostix.game.graphics.water.WaterRenderer;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.OpenGL.DisplayManager;
import fr.ostix.game.toolBox.OpenGL.OpenGlUtils;
import fr.ostix.game.world.chunk.Chunk;
import fr.ostix.game.world.water.WaterFrameBuffers;
import fr.ostix.game.world.water.WaterTile;
import fr.ostix.game.world.weather.Weather;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class MasterRenderer {

    public static final float FOV = (float) Math.toRadians(70f);
    public static final float NEAR = 0.1f;
    public static final float FAR = 1250;

    private static final Vector4f NO_CLIP = new Vector4f(0, 0, 0, 1);

    private Color skyColor = new Color(0.5444f, 0.62f, 0.69f);
    private static final Vector3f SUN_DIR = new Vector3f(0, -1, 0);

    private final EntityRenderer entityRenderer;
    private final ClassicShader shader = new ClassicShader();

    private final TerrainRenderer terrainRenderer;
    private final TerrainShader terrainShader = new TerrainShader();
    private final SkyboxRenderer skyboxRenderer;

    private final WaterFrameBuffers waterFbos = new WaterFrameBuffers();
    private final WaterRenderer waterRenderer;

    private Map<Vector2f, Chunk> terrains;
    private static Matrix4f projectionMatrix;

    private final Map<Model, List<Entity>> entities = new HashMap<>();
    private final Weather weather;

    public MasterRenderer(Weather weather, Map<Vector2f, Chunk> worldChunk) {
        OpenGlUtils.cullBackFaces(true);
        this.weather = weather;
        this.terrains = worldChunk;
        projectionMatrix = createProjectionMatrix();
        this.entityRenderer = new EntityRenderer(shader, projectionMatrix);
        this.terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        this.skyboxRenderer = new SkyboxRenderer( projectionMatrix);
        this.waterRenderer = new WaterRenderer(waterFbos);
    }


    public void initFrame() {
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(skyColor.getRed(), skyColor.getGreen(), skyColor.getBlue(), skyColor.getAlpha());
        createProjectionMatrix();
    }


    private void processEntity(Entity e) {
        Model model = e.getModel();
        List<Entity> batch = entities.get(model);
        if (batch != null) {
            batch.add(e);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(e);
            entities.put(model, newBatch);
        }
    }

    public void renderScene(List<Entity> entities, List<WaterTile> waterTiles, List<Light> lights, Camera camera) {
        for (Entity entity : entities) {
            processEntity(entity);
        }
        skyColor = weather.getSky().getSkyColour();
        if (!waterTiles.isEmpty()){
            GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
            renderWaterRefractionPass(lights, camera);
            renderWaterReflectionPass(lights, waterTiles, camera);
            GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
        }
        render(lights, waterTiles, camera);
    }

    private void renderWaterReflectionPass(List<Light> lights, List<WaterTile> waterTiles, Camera camera) {
        waterFbos.bindReflectionFrameBuffer();
        this.initFrame();
        camera.reflect(waterTiles.get(0).getHeight());
        skyboxRenderer.render(camera, weather);
        entityRenderer.render(entities, lights, camera, skyColor, new Vector4f(0, 1, 0, -waterTiles.get(0).getHeight() - 0.001f));
        terrainRenderer.render(terrains, lights,camera, skyColor,new Vector4f(0, 1, 0, -waterTiles.get(0).getHeight()-0.1f));
        camera.reflect(waterTiles.get(0).getHeight());
        waterFbos.unbindCurrentFrameBuffer();
    }

    private void renderWaterRefractionPass(List<Light> lights, Camera camera) {
        waterFbos.bindRefractionFrameBuffer();
        this.initFrame();
        entityRenderer.render(entities, lights, camera, skyColor, new Vector4f(0, -1, 0, -10f));
        terrainRenderer.render(terrains, lights, camera, skyColor, new Vector4f(0, -1, 0, -10f));
        waterFbos.unbindCurrentFrameBuffer();
    }

    private void render(List<Light> lights, List<WaterTile> waterTiles, Camera cam) {
        this.initFrame();


        skyboxRenderer.render(cam, weather);

        entityRenderer.render(entities, lights, cam, skyColor, NO_CLIP);
        shader.unBind();

        terrainRenderer.render(terrains, lights, cam, skyColor, NO_CLIP);
        terrainShader.unBind();

        waterRenderer.render(waterTiles, cam, SUN_DIR);

        entities.clear();
    }

    public void cleanUp() {
        GLRequestProcessor.sendRequest(new GLRunnableRequest(() -> {
            waterFbos.cleanUp();
            this.shader.cleanUp();
            this.terrainShader.cleanUp();
            this.skyboxRenderer.cleanUp();
            this.waterRenderer.cleanUp();
            glDisable(GL_BLEND);
        }));
    }

    private Matrix4f createProjectionMatrix() {
        projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        float aspectRatio = (float) DisplayManager.getWidth() / (float) DisplayManager.getHeight();
        projectionMatrix.perspective(FOV, aspectRatio, NEAR, FAR);
        return projectionMatrix;
//        projectionMatrix = new Matrix4f();
//        float aspectRatio = (float) DisplayManager.getWidth() / (float) DisplayManager.getHeight();
//        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
//        float x_scale = y_scale / aspectRatio;
//        float frustum_length = far - near;
//
//        projectionMatrix.set(0,0,x_scale);
//        projectionMatrix.set(1,1,y_scale);
//        projectionMatrix.set(2,2,-((far + near) / frustum_length));
//        projectionMatrix.set(2,3, -1);
//        projectionMatrix.set(3,2, -((2 * near * far) / frustum_length));
//        projectionMatrix.set(3,3, 0);
    }

    public static Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
