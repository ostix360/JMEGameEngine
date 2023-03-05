package fr.ostix.game.gui;

import fr.ostix.game.toolBox.*;
import fr.ostix.game.toolBox.OpenGL.shader.*;
import fr.ostix.game.toolBox.OpenGL.shader.uniform.*;
import org.joml.*;

public class GuiShader extends ShaderProgram {

    private final MatrixUniform transformationMatrix = new MatrixUniform("transformationMatrix");
    private final Vector4fUniform layerColor = new Vector4fUniform("layer");


    public GuiShader() {
        super("gui");
        super.storeAllUniformsLocations(transformationMatrix);
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
