package fr.ostix.game.toolBox.OpenGL.shader.uniform;

import fr.ostix.game.toolBox.Logger;

import static org.lwjgl.opengl.GL20.glUniform1f;

public class BooleanUniform extends Uniform {
    public BooleanUniform(String name) {
        super(name);
    }

    public void loadBooleanToUniform(boolean v) {
        float fValue = 0;
        if (v) {
            fValue = 1;
        }
        glUniform1f(super.getLocation(), fValue);
        Logger.errGL("Error while loading boolean " + this.name + " to uniform");
    }
}
