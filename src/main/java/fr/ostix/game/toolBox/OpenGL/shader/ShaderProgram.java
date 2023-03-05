package fr.ostix.game.toolBox.OpenGL.shader;

import fr.ostix.game.toolBox.*;
import fr.ostix.game.toolBox.OpenGL.shader.uniform.*;
import org.lwjgl.opengl.GL11;

import java.io.*;
import java.util.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {

    private int vertexShaderID;
    private int fragmentShaderID;
    private int programID;

    private String name;

    public ShaderProgram(String shadersName) {
        this.name = shadersName;
        loadShaders(shadersName);
        processProgram();
    }

    protected abstract void bindAllAttributes();

    protected void storeAllUniformsLocations(Uniform... uniforms) {
        for (Uniform uniform : uniforms) {
            uniform.storeUniform(programID);
        }
        this.validateProgram();
    }

    private void validateProgram() {
        glValidateProgram(programID);
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == GL_FALSE) {
            Logger.err("Failed to validate shader program : " + glGetProgramInfoLog(programID),
                    new IllegalStateException("Could not compile shader"));
        }
        Logger.errGL("Error while validating program");
    }

    protected void bindAttribute(int attribute, String variableName) {
        glBindAttribLocation(programID, attribute, variableName);
        Logger.errGL("Error while binding attribute");
    }

    public void bind() {
        glUseProgram(programID);
        Logger.errGL("Error while binding program");
    }

    public void unBind() {
        glUseProgram(0);
    }


    private void loadShaders(String shadersName) {
        StringBuilder vertexSource = readShader(shadersName + ".vert");
        StringBuilder fragmentSource = readShader(shadersName + ".frag");

        vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderID, vertexSource);
        Logger.errGL("Error while loading vertex shader");
        processShader(vertexShaderID);

        fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderID, fragmentSource);
        Logger.errGL("Error while loading fragment shader");
        processShader(fragmentShaderID);
    }

    private StringBuilder readShader(String file) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(
                                    ShaderProgram.class.getResourceAsStream("/shader/" + file))));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb;
    }

    private void processShader(int shaderID) {
        glCompileShader(shaderID);
        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            Logger.err("Failed to compile shader : " + shaderID + " || GL error : " + glGetShaderInfoLog(shaderID),
                    new IllegalStateException("Could not compile shader"));
        }
        Logger.errGL("Error while compiling shader");

    }


    private void processProgram() {
        programID = glCreateProgram();
        System.out.println(name + " program ID : " + programID);
        Logger.errGL("Error while creating program");

        glAttachShader(programID, vertexShaderID);
        Logger.errGL("Error while attaching vertex shader");

        glAttachShader(programID, fragmentShaderID);
        Logger.errGL("Error while attaching fragment shader");

        bindAllAttributes();

        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            Logger.err("Failed to linked shader program : " + glGetProgramInfoLog(programID),
                    new IllegalStateException("Could not compile shader "));
        }
        Logger.errGL("Error while linking program");

        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);

        //storeAllUniformsLocations();


    }

    public void cleanUp() {
        unBind();
        glDeleteProgram(programID);
    }
}
