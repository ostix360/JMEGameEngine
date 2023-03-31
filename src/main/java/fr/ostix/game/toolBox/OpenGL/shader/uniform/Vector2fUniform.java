package fr.ostix.game.toolBox.OpenGL.shader.uniform;

import fr.ostix.game.toolBox.Logger;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL20;

public class Vector2fUniform extends Uniform {
    public Vector2fUniform(String name) {
        super(name);
    }

    public void loadVector2fToUniform(Vector2f value) {
        GL20.glUniform2f(super.getLocation(), value.x(), value.y());
        Logger.errGL("Error while loading Vector2f "+ this.name +" to uniform");
    }
}
