package fr.ostix.game.gui;

import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.graphics.textures.Texture;
import fr.ostix.game.toolBox.Logger;
import fr.ostix.game.toolBox.Maths;
import fr.ostix.game.toolBox.OpenGL.OpenGlUtils;
import fr.ostix.game.toolBox.OpenGL.VAO;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class GuiRenderer {

    private final GuiShader shader;

    public GuiRenderer(Loader loader) {
        shader = new GuiShader();
    }

    public void render(HashMap<GuiModel,List<GuiTexture>> guis) {
        shader.bind();
        OpenGlUtils.enableAlphaBlending();
        OpenGlUtils.enableDepthTesting(false);
        for (GuiModel model : guis.keySet()) {
            model.getVao().bind(0,1);
            for (GuiTexture gui : guis.get(model)) {
                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                GL11.glBindTexture(GL_TEXTURE_2D, gui.getTexture());
                Matrix4f matrix4f = Maths.createTransformationMatrix(gui.getPosition(), gui.getGlScale());
                shader.loadTransformationMatrix(matrix4f);
                shader.loadLayer(gui.getLayer());
                Logger.errGL("Error while rendering gui");
                glDrawElements(GL_TRIANGLES, model.getVao().getVertexCount(),GL_UNSIGNED_INT,0);
                Logger.errGL("Error while rendering gui");
                Texture.unBindTexture();
            }
        }
        OpenGlUtils.enableDepthTesting(true);
        OpenGlUtils.disableBlending();
        VAO.unbind(0,1);
        shader.unBind();
    }

    public void cleanUp() {
        shader.cleanUp();
    }
}
