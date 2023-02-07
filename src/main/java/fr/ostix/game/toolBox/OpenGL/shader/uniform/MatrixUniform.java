package fr.ostix.game.toolBox.OpenGL.shader.uniform;

import org.joml.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

public class MatrixUniform extends Uniform {
    public MatrixUniform(String name) {
        super(name);
    }

    public void loadMatrixToUniform(Matrix4f m) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            m.get(fb);
            GL20.glUniformMatrix4fv(super.getLocation(), false, fb);
        }
    }
}
