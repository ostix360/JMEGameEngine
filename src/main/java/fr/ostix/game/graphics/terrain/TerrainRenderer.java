package fr.ostix.game.graphics.terrain;

import fr.ostix.game.entity.camera.*;
import fr.ostix.game.entity.component.light.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.graphics.textures.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.toolBox.OpenGL.*;
import fr.ostix.game.world.*;
import fr.ostix.game.world.chunk.*;
import fr.ostix.game.world.texture.*;
import org.joml.*;
import org.lwjgl.opengl.*;

import java.util.*;

import static org.lwjgl.opengl.GL11.*;

public class TerrainRenderer {
    private final TerrainShader shader;

    public TerrainRenderer(TerrainShader terrainShader, Matrix4f projectionMatrix) {
        this.shader = terrainShader;
        shader.bind();
        shader.connectTerrainUnits();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.unBind();
    }

    public void render(Map<Vector2f, Chunk> terrains, List<Light> lights, Camera cam, Color skyColor, Vector4f clipPlane) {
        prepare(lights, skyColor, cam, clipPlane);
        OpenGlUtils.goWireframe(false);
        // shader.loadShaderMapSpace(toShadowSpace);
        for (Chunk chunk : terrains.values()) {
            Terrain ter = chunk.getTerrain();
            if (ter.getModel() == null) {
                ter.setModel();
                continue;
            }
            prepareTerrain(ter);
            loadModelMatrix(ter);
            glDrawElements(GL_TRIANGLES, ter.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
        }
    }

    private void prepare(List<Light> lights, Color skyColor, Camera cam, Vector4f clipPlane) {
        shader.bind();
        shader.loadClipPlane(clipPlane);
        shader.loadSkyColour(skyColor);
        shader.loadLight(lights);
        shader.loadViewMatrix(cam);
    }

    private void prepareTerrain(Terrain terrain) {
        MeshModel model = terrain.getModel();
        model.getVAO().bind(0, 1, 2);
        shader.loadSpecular(0, 1);
        bindTexture(terrain);
    }

    private void bindTexture(Terrain ter) {
        TerrainTexturePack texturePack = ter.getTexturePack();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, texturePack.getrTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, texturePack.getgTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        glBindTexture(GL_TEXTURE_2D, texturePack.getbTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        glBindTexture(GL_TEXTURE_2D, ter.getBlendMap().getTextureID());
    }

    private void unbindTexturedModel() {
        Texture.unBindTexture();
        VAO.unbind(0, 1, 2);
    }

    private void loadModelMatrix(Terrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()),
                new Vector3f(0, 0, 0), new Vector3f(1));
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
