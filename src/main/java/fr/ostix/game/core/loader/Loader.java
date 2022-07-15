package fr.ostix.game.core.loader;

import de.matthiasmann.twl.utils.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.graphics.textures.*;
import fr.ostix.game.toolBox.OpenGL.*;
import org.lwjgl.opengl.*;

import java.io.*;
import java.nio.*;
import java.util.*;

import static fr.ostix.game.toolBox.ToolDirectory.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Loader {

    public static final Loader INSTANCE = new Loader();

    private final List<VAO> VAOs = new ArrayList<>();
    private final List<TextureLoader> textureLoaders = new ArrayList<>();

    public MeshModel loadToVAO(float[] position, float[] texturesCoords,
                               float[] normals, int[] indices) {
        VAO vao = VAO.createVAO();
        VAOs.add(vao);
        vao.bind();
        vao.storeIndicesInVAO(indices);
        vao.storePositionInAttributeList(0, 3, position);
        vao.storeDataInAttributeList(1, 2, texturesCoords);
        vao.storeDataInAttributeList(2, 3, normals);
        VAO.unbind();
        return new MeshModel(vao);
    }

    public MeshModel loadToVAO(int[] indices, float[] position, float[] texturesCoords, float[] normals, int[] jointIDs, float[] vertexWeights) {
        VAO vao = VAO.createVAO();
        VAOs.add(vao);
        vao.bind();
        vao.storeIndicesInVAO(indices);
        vao.storePositionInAttributeList(0, 3, position);
        vao.storeDataInAttributeList(1, 2, texturesCoords);
        vao.storeDataInAttributeList(2, 3, normals);
        vao.storeIntDataInAttributeList(3, 3, jointIDs);
        vao.storeDataInAttributeList(4, 3, vertexWeights);
        VAO.unbind();
        return new MeshModel(vao);
    }

    public MeshModel loadFontToVAO(float[] pos, float[] texturesCoords) {
        VAO vao = VAO.createVAO();
        VAOs.add(vao);
        vao.bind();
        vao.storeDataInAttributeList(0, 2, pos);
        vao.storeDataInAttributeList(1, 2, texturesCoords);
        VAO.unbind();
        return new MeshModel(vao);
    }


    public void addInstance(VAO vao, VBO vbo, int attrib, int dataSize, int instanceDataLength, int offset) {
        vao.addInstance(vbo, attrib, dataSize, instanceDataLength, offset);
    }

    public void updateVBO(VBO vbo, float[] data, FloatBuffer buffer) {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        vbo.updateVBO(buffer);
    }


    public MeshModel loadToVAO(float[] vertices, int dimension) {
        VAO vao = VAO.createVAO();
        VAOs.add(vao);
        vao.bind();
        vao.storeDataInAttributeList(0, dimension, vertices);
        vao.setVertexCount(vertices.length / dimension);
        VAO.unbind();
        return new MeshModel(vao);
    }

    public MeshModel loadToVAO(float[] position, int[] indices) {
        VAO vao = VAO.createVAO();
        VAOs.add(vao);
        vao.bind();
        vao.storeIndicesInVAO(indices);
        vao.storePositionInAttributeList(0, 2, position);
        VAO.unbind();
        return new MeshModel(vao);
    }

    //TEXTURES


    public int loadCubMap(String[] fileNames) {
        int texID = glGenTextures();
        //glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_CUBE_MAP, texID);

        for (int i = 0; i < fileNames.length; i++) {
            TextureData data = decodeTextureFile(fileNames[i]);
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA, data.getWight(), data.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data.getBuffer());
        }
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
        textureLoaders.add(new TextureLoader(texID, 0));
        return texID;
    }

    private TextureData decodeTextureFile(String fileName) {
        int width = 0;
        int height = 0;
        ByteBuffer buffer = null;
        InputStream fis;
        try {
            fis = new FileInputStream(RES_FOLDER + "/textures/skybox/" + fileName + ".png");
            PNGDecoder decoder = new PNGDecoder(fis);
            width = decoder.getWidth();
            height = decoder.getHeight();
            buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            fis.close();
        } catch (IOException e) {
            System.exit(-1);
        }
        return new TextureData(width, height, buffer);
    }


    public TextureLoader loadTexture(String fileName) {
        TextureLoader textureLoader = TextureLoader.loadTexture(fileName, TextureUtils.MIPMAP_ANISOTROPIC_MODE, false);
        textureLoaders.add(textureLoader);
        return textureLoader;
    }

    public TextureLoader loadTextureFont(String textureFontName) {
        TextureLoader textureLoader = TextureLoader.loadTexture("font/" + textureFontName, 0, false);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0);
        textureLoaders.add(textureLoader);
        return textureLoader;
    }

    public void cleanUp() {
        for (VAO vao : VAOs) {
            vao.cleanUP();
        }
        VAOs.clear();
        for (TextureLoader t : textureLoaders) {
            GL11.glDeleteTextures(t.getId());
        }
        textureLoaders.clear();
    }
}
