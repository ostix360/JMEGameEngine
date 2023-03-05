package fr.ostix.game.toolBox.OpenGL.shader.uniform;

import fr.ostix.game.toolBox.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;

public class Uniform {
    protected final String name;
    private int location;

    public Uniform(String name) {
        this.name = name;
    }

    public void storeUniform(int programID) {
        location = GL20.glGetUniformLocation(programID, name);
        if (location == -1) {
            Logger.err("No uniform variable called " + name + " found for the program " + programID + "!");
        }
        Logger.errGL("Error while loading uniform " + name + " to program " + programID);
    }

    protected int getLocation() {
        return this.location;
    }

    public String getName() {
        return name;
    }
}
