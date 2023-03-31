package fr.ostix.game.graphics.skybox;

import fr.ostix.game.core.camera.Camera;
import fr.ostix.game.toolBox.OpenGL.OpenGlUtils;
import fr.ostix.game.toolBox.OpenGL.VAO;
import fr.ostix.game.world.weather.SkyBox;
import fr.ostix.game.world.weather.Weather;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL11.*;

public class SkyboxRenderer {

    private final SkyboxShader shader;

    public SkyboxRenderer(Matrix4f projectionMatrix) {
        shader = new SkyboxShader();
        shader.bind();
        shader.connectTextureUnits();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.unBind();
    }

    private void bindTextures(SkyBox skybox) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, skybox.getPrimaryTexture().getTextureId());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, skybox.getSecondaryTexture().getTextureId());
        shader.loadBlendFactor(skybox.getBlendFactor());
    }

    public void render(Camera cam, Weather weather) {
        OpenGlUtils.goWireframe(false);
        OpenGlUtils.enableDepthTesting(false);
        SkyBox skybox = weather.getSky().getSkyBox();
        glDepthMask(false);
        shader.bind();
        shader.loadViewMatrix(cam, skybox.getRotation());
        shader.loadFogColor(weather.getSky().getSkyColour());

        skybox.getBox().getVAO().bind(0);
        bindTextures(skybox);
        glDrawArrays(GL_TRIANGLES, 0, skybox.getBox().getVertexCount());
        VAO.unbind(0);
        glBindTexture(GL_TEXTURE_2D, 0);
        shader.unBind();
        glDepthMask(true);
        OpenGlUtils.enableDepthTesting(true);
    }

    public void cleanUp() {
        this.shader.cleanUp();
    }
}
