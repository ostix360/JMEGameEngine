package fr.ostix.game.toolBox.OpenGL.shader.uniform;

import fr.ostix.game.toolBox.Logger;
import org.lwjgl.opengl.GL20;

public class Uniform {
    private final String name;
    private int location;

    public Uniform(String name) {
        this.name = name;
    }

    public void storeUniform(int programID) {
        location = GL20.glGetUniformLocation(programID, name);
        if (location == -1) {
            Logger.err("No uniform variable called " + name + " found for the program " + programID + " !");
        }
    }

    protected int getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
