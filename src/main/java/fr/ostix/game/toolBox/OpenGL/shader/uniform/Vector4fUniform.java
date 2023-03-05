package fr.ostix.game.toolBox.OpenGL.shader.uniform;

import fr.ostix.game.toolBox.Logger;
import org.joml.*;
import org.lwjgl.opengl.*;

public class Vector4fUniform extends Uniform {
    public Vector4fUniform(String name) {
        super(name);
    }

    public void loadVec4fToUniform(Vector4f value) {
        GL20.glUniform4f(super.getLocation(), value.x(), value.y(), value.z(), value.w());
        Logger.errGL("Error while loading Vector4f "+ this.name +" to uniform");
    }
}
