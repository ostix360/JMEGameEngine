package fr.ostix.game.graphics.model;


import fr.ostix.game.entity.animated.colladaParser.dataStructures.MeshData;

public class ModelData {
    private final float[] vertices;
    private final float[] texcoords;
    private final int[] indices;
    private final float[] normals;
    private int[] jointsId;
    private float[] vertexWeights;


    public ModelData(float[] vertices, float[] texcoords, int[] indices, float[] normals, int[] jointsId, float[] vertexWeights) {
        this.vertices = vertices;
        this.texcoords = texcoords;
        this.indices = indices;
        this.normals = normals;
        this.jointsId = jointsId;
        this.vertexWeights = vertexWeights;
    }

    public ModelData(float[] vertices, float[] texcoords, float[] normals, int[] indices) {
        this.vertices = vertices;
        this.texcoords = texcoords;
        this.indices = indices;
        this.normals = normals;
    }

    public ModelData(MeshData meshData) {
        this.vertices = meshData.getVertices();
        this.texcoords = meshData.getTextureCoords();
        this.indices = meshData.getIndices();
        this.normals = meshData.getNormals();
        this.jointsId = meshData.getJointIds();
        this.vertexWeights = meshData.getVertexWeights();
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getTexcoords() {
        return texcoords;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getNormals() {
        return normals;
    }

    public int[] getJointsId() {
        return jointsId;
    }

    public float[] getVertexWeights() {
        return vertexWeights;
    }
}
