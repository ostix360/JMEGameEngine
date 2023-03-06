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

    private final GuiShader shader;

    public GuiRenderer(Loader loader) {
        shader = new GuiShader();
    }

    public void render(HashMap<GuiModel,List<GuiTexture>> guis) {
        shader.bind();
        OpenGlUtils.enableAlphaBlending();
        OpenGlUtils.enableDepthTesting(false);
        for (GuiModel model : guis.keySet()) {
            model.getVao().bind(0,1,2);
            for (GuiTexture gui : guis.get(model)) {
                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                GL11.glBindTexture(GL_TEXTURE_2D, gui.getTexture());
                Matrix4f matrix4f = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
                shader.loadTransformationMatrix(matrix4f);
                shader.loadLayer(gui.getLayer());
                glDrawElements(GL_TRIANGLES,model.getVao().getVertexCount(),GL_UNSIGNED_INT,0);
                Logger.errGL("Error while rendering gui");
                Texture.unBindTexture();
            }
        }
        OpenGlUtils.enableDepthTesting(true);
        OpenGlUtils.disableBlending();
        VAO.unbind(0);
        shader.unBind();
    }

    public void cleanUp() {
        shader.cleanUp();
    }
}
