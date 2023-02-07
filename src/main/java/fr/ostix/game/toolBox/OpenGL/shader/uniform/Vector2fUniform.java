package fr.ostix.game.toolBox.OpenGL.shader.uniform;

import org.joml.*;
import org.lwjgl.opengl.*;

public class Vector2fUniform extends Uniform{
    public Vector2fUniform(String name) {
        super(name);
    }

    public void loadVector2fToUniform(Vector2f value){
        GL20.glUniform2f(super.getLocation(), value.x(), value.y());
    }
}
