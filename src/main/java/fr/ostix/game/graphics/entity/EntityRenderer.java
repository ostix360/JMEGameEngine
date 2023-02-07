package fr.ostix.game.graphics.entity;

import fr.ostix.game.core.camera.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.entity.animated.animation.animatedModel.*;
import fr.ostix.game.entity.component.light.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.graphics.textures.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.toolBox.OpenGL.*;
import fr.ostix.game.world.*;
import org.joml.*;
import org.lwjgl.opengl.*;

import java.util.*;

import static org.lwjgl.opengl.GL11.*;

public class EntityRenderer {

    ClassicShader shader;

    public EntityRenderer(ClassicShader shader, Matrix4f projectionMatrix) {

        OpenGlUtils.cullBackFaces(true);
        this.shader = shader;
        this.shader.bind();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.connectTextureUnits();
        this.shader.unBind();
    }

    public void render(Map<Model, List<Entity>> entities, List<Light> lights, Camera cam, Color skyColor, Vector4f clipPlane) {
        prepare(lights, skyColor, cam, clipPlane);
        for (Model model : entities.keySet()) {
            OpenGlUtils.goWireframe(false);
            if (model instanceof AnimatedModel) {
                prepareAnimatedTexturedModel((AnimatedModel) model);
            } else {
                prepareTexturedModel(model);
            }
            List<Entity> batch = entities.get(model);
            for (Entity entity : batch) {
                prepareInstance(entity);
                glDrawElements(GL_TRIANGLES, entity.getModel().getMeshModel().getVertexCount(), GL_UNSIGNED_INT, 0);
            }

        }
        finish();
    }

    private void prepare(List<Light> lights, Color skyColor, Camera cam, Vector4f clipPlane) {
        shader.bind();
        shader.loadLight(lights);
        shader.loadSkyColor(skyColor);
        shader.loadViewMatrix(cam);
        shader.clipPlane.loadVec4fToUniform(clipPlane);
        OpenGlUtils.goWireframe(false);
    }

    private void prepareAnimatedTexturedModel(AnimatedModel model) {
        MeshModel mesh = model.getMeshModel();
        mesh.getVAO().bind(0, 1, 2, 3, 4);
        shader.isAnimated.loadBooleanToUniform(true);
        shader.jointTransforms.loadMatrixArray(model.getJointTransforms());

        Texture texture = model.getTexture();
        shader.loadSpecular(texture.getReflectivity(), texture.getShineDamper());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        texture.bindTexture();
        shader.useSpecularMap.loadBooleanToUniform(texture.hasSpecularMap());
        shader.numberOfRows.loadFloatToUniform(texture.getNumbersOfRows());
        if (texture.hasSpecularMap()) {
            GL13.glActiveTexture(GL13.GL_TEXTURE1);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getSpecularMap());
        }
    }

    public void prepareInstance(Entity entity) {
         shader.loadTransformationMatrix(entity.getTransform().getTransformation());
        shader.offset.loadVector2fToUniform(new Vector2f(entity.getTextureXOffset(), entity.getTextureYOffset()));
    }

    public void prepareTexturedModel(Model model) {
        if (model.equals(World.CUBE)) {
            OpenGlUtils.goWireframe(true);
        }
        MeshModel meshModel = model.getMeshModel();
        meshModel.getVAO().bind(0, 1, 2);
        shader.isAnimated.loadBooleanToUniform(false);

        Texture texture = model.getTexture();
        shader.loadSpecular(texture.getReflectivity(), texture.getShineDamper());
        shader.numberOfRows.loadFloatToUniform(texture.getNumbersOfRows());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        texture.bindTexture();
        shader.useSpecularMap.loadBooleanToUniform(texture.hasSpecularMap());
        if (texture.hasSpecularMap()) {
            GL13.glActiveTexture(GL13.GL_TEXTURE1);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getSpecularMap());
        }
    }

    public void finish() {
        OpenGlUtils.cullBackFaces(true);
        VAO.unbind(0, 1, 2, 3, 4);
        Texture.unBindTexture();

    }


}
