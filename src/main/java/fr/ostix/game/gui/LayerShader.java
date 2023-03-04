package fr.ostix.game.gui;

import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.OpenGL.shader.ShaderProgram;
import fr.ostix.game.toolBox.OpenGL.shader.uniform.MatrixUniform;
import fr.ostix.game.toolBox.OpenGL.shader.uniform.Vector4fUniform;
import org.joml.Matrix4f;

public class LayerShader extends ShaderProgram {

    private final MatrixUniform transformationMatrix = new MatrixUniform("transformationMatrix");

    private final Vector4fUniform layerColor = new Vector4fUniform("layer");


    public LayerShader() {
        super("layer");
        super.storeAllUniformsLocations(transformationMatrix, layerColor);
    }

    @Override
    protected void bindAllAttributes() {
        super.bindAttribute(0, "position");
    }

     public void loadLayer(Color layer) {
        layerColor.loadVec4fToUniform(layer.getVec4f());
    }

     public void loadTransformationMatrix(Matrix4f matrix) {
        transformationMatrix.loadMatrixToUniform(matrix);
    }


}
