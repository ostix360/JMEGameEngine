package fr.ostix.game.toolBox.OpenGL.shader.uniform;

import org.lwjgl.opengl.*;

public class IntUniform extends Uniform {
    public IntUniform(String name) {
        super(name);
    }

    public void loadIntToUniform(int value) {
        GL20.glUniform1i(super.getLocation(), value);
    }
}
