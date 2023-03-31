package fr.ostix.game.toolBox.OpenGL.shader.uniform;

import fr.ostix.game.toolBox.Logger;
import org.lwjgl.opengl.GL20;

public class IntUniform extends Uniform {
    public IntUniform(String name) {
        super(name);
    }

    public void loadIntToUniform(int value) {
        GL20.glUniform1i(super.getLocation(), value);
        Logger.errGL("Error while loading int "+ this.name +" to uniform");
    }
}
