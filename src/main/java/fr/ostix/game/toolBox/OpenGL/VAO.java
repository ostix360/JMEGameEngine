package fr.ostix.game.toolBox.OpenGL;

import com.jme3.jme3tools.VertexBuffer;
import fr.ostix.game.toolBox.Logger;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class VAO {
    public final int id;
    private final List<VBO> VBOs = new ArrayList<>();
    private int vertexCount;
    private VertexBuffer position;
    private int[] indices;

    private VAO(int id) {
        this.id = id;
    }

    public static VAO createVAO() {
        int id = GL30.glGenVertexArrays();
        return new VAO(id);
    }
    public VBO createEmptyVBO(int count) {
        VBO vbo = VBO.createEmptyVBO(count);
        VBOs.add(vbo);
        return vbo;
    }

    public void storeIndicesInVAO(int[] indices) {
        VBO vbo = VBO.createVBO();
        VBOs.add(vbo);
        vbo.storeIndicesDataInAttributeList(indices);
        this.vertexCount = indices.length;
        this.indices = indices;
    }
    public void storePositionInAttributeList(int attrib, int dataSize, float[] position) {
        VBO vbo = VBO.createVBO();
        VBOs.add(vbo);
        vbo.storeDataInAttributeList(attrib, dataSize, position);
        this.position = new VertexBuffer(VertexBuffer.Type.Position);
        this.position.setupData(VertexBuffer.Usage.Dynamic,dataSize , VertexBuffer.Format.Float , vbo.getBuffer());
    }

    public void storeDataInAttributeList(int attrib, int dataSize, float[] data) {
        VBO vbo = VBO.createVBO();
        VBOs.add(vbo);
        vbo.storeDataInAttributeList(attrib, dataSize, data);
    }


    public void storeIntDataInAttributeList(int attrib, int dataSize, int[] data) {
        VBO vbo = VBO.createVBO();
        VBOs.add(vbo);
        vbo.storeIntDataInAttributeList(attrib, dataSize, data);
    }

    public void bind(int... attributes) {
        this.bind();
        for (int i : attributes) {
            GL20.glEnableVertexAttribArray(i);
            Logger.errGL("Error while binding VBOS");
        }
    }

    public static void unbind(int... attributes) {
        for (int i : attributes) {
            GL20.glDisableVertexAttribArray(i);
        }
        unbind();
    }

    public void cleanUP() {
        GL30.glDeleteVertexArrays(id);
        for (VBO vbo : VBOs) {
            vbo.delete();
        }
        VBOs.clear();
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    private void bind() {
        GL30.glBindVertexArray(id);
        Logger.errGL("Error while binding VAO");
    }

    private static void unbind() {
        GL30.glBindVertexArray(0);
    }

    public VertexBuffer getPosition() {
        return position;
    }

    public int[] getIndices() {
        return indices;
    }

    public void addInstance(VBO vbo, int attrib, int dataSize, int instanceDataLength, int offset) {
        this.bind();
        vbo.addInstance(attrib,dataSize,instanceDataLength,offset);
        VAO.unbind();
    }

}
