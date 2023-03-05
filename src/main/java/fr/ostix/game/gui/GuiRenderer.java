package fr.ostix.game.gui;

import fr.ostix.game.core.loader.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.graphics.textures.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.toolBox.OpenGL.*;
import org.joml.*;
import org.lwjgl.opengl.*;

import java.util.*;

import static org.lwjgl.opengl.GL11.*;

public class GuiRenderer {

    private final MeshModel quadModel;
    private final GuiShader shader;

    private final LayerShader layerShader;

    public GuiRenderer(Loader loader) {
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quadModel = loader.loadToVAO(positions, 2);
        shader = new GuiShader();
        layerShader = new LayerShader();
    }

    public void render(List<GuiTexture> guis) {

        shader.bind();
        quadModel.getVAO().bind(0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
        for (GuiTexture gui : guis) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL_TEXTURE_2D, gui.getTexture());
            Matrix4f matrix4f = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
            shader.loadTransformationMatrix(matrix4f);
            if (gui.hasLayer()) {
                GuiLayer layer = gui.getLayer();
                Vector2f pos = layer.getRelativePos();
                Vector2f scale = new Vector2f(gui.getScale());
                scale.mul(layer.getScale());
                Matrix4f matrix4f1 = new Matrix4f().identity();
                matrix4f1.translate(pos.x(), pos.y(), 0);
                matrix4f1.scale(scale.x(), scale.y(), 0);
                layerShader.bind();
                layerShader.loadTransformationMatrix(matrix4f1);
                layerShader.loadLayer(layer.getLayer());
            }
            glDrawArrays(GL_TRIANGLE_STRIP, 0, quadModel.getVertexCount());
            Logger.errGL("Error while rendering gui");
            Texture.unBindTexture();
            if (gui.hasLayer()) {
                layerShader.unBind();
            }

        }
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
        VAO.unbind(0);
        shader.unBind();
    }

    public void cleanUp() {
        shader.cleanUp();
        layerShader.cleanUp();
    }
}
