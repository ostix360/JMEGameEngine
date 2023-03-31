package fr.ostix.game.toolBox.OpenGL.shader.uniform;

import org.joml.Matrix4f;

public class MatrixUniformArray extends Uniform{

    private final MatrixUniform[] matrixUniforms;

    public MatrixUniformArray(String name, int size) {
        super(name);
        matrixUniforms = new MatrixUniform[size];
        for (int i = 0; i < size; i++) {
            matrixUniforms[i] = new MatrixUniform(name + "[" + i + "]");
        }
    }

    @Override
    public void storeUniform(int programID) {
        for (MatrixUniform matrixUniform : matrixUniforms) {
            matrixUniform.storeUniform(programID);
        }
    }

    public void loadMatrixArray(Matrix4f[] matrices) {
        for (int i = 0; i < matrices.length; i++) {
            matrixUniforms[i].loadMatrixToUniform(matrices[i]);
        }
    }

}
