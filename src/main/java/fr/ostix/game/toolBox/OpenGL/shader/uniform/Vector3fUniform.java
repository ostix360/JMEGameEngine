package fr.ostix.game.toolBox.OpenGL.shader.uniform;

import org.joml.*;
import org.lwjgl.opengl.*;

public class Vector3fUniform extends Uniform {

    public Vector3fUniform(String name) {
        super(name);
    }

    public void loadVector3fToUniform(Vector3f value) {
        GL20.glUniform3f(super.getLocation(), value.x(), value.y(), value.z());
    }
}
