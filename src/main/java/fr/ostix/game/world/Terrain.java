package fr.ostix.game.world;

import com.jme3.bullet.control.*;
import fr.ostix.game.core.ressourceProcessor.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.graphics.textures.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.world.chunk.*;
import fr.ostix.game.world.texture.*;
import org.joml.*;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.nio.*;
import java.util.*;
import java.lang.Math;

public class Terrain {
    private static final int SIZE = 100;
    private static final int MAX_HEIGHT = 40;
    private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;

    private float[][] heights;
    private final float x;
    private final float z;
    private MeshModel model;
    private final TerrainTexturePack texturePack;
    private final TerrainTexture blendMap;
    private static Map<Vector2f, Chunk> worldChunk;
    private ModelLoaderRequest modelRequest;


    private float[] fHeightMap;

    private TerrainControl control = null;

    public Terrain(float gridX, float gridZ, TerrainTexturePack texturePack, TerrainTexture blendMap, float[][] heights) {
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.heights = heights;
        generateTerrain();
        this.texturePack = texturePack;
        this.blendMap = blendMap;
        this.control = new TerrainControl(this,0);
    }

    public static void setWorldChunk(Map<Vector2f, Chunk> worldChunk) {
        Terrain.worldChunk = worldChunk;
    }

    public float getHeightOfTerrain(float worldX, float worldZ) {
        float terrainX = worldX - this.x;
        float terrainZ = worldZ - this.z;
        float gridSquareSize = SIZE / ((float) heights.length - 1);  // cacul de la grille donc nombre de vertex - 1
        int gridX = (int) Math.floor(terrainX / gridSquareSize);
        int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
        if (gridX < 0 || gridX >= heights.length - 1 || gridZ < 0 || gridZ >= heights.length - 1) {
            return 0;
        }
        float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
        float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
        float answer;
        if (xCoord <= (1 - zCoord)) {
            answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
                    heights[gridX + 1][gridZ], 0), new Vector3f(0,
                    heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        } else {
            answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
                    heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
                    heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        }
        return answer;
    }

    private void regenerateTerrain() {
        this.model = null;
        int VERTEX_COUNT = 16;
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        int vertexPointer = 0;
        for (int z = 0; z < VERTEX_COUNT; z++) {            // Boucle de generation de monde
            for (int x = 0; x < VERTEX_COUNT; x++) {
                vertices[vertexPointer * 3] = (float) x / ((float) VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer * 3 + 1] = getHeight(x,z);
                vertices[vertexPointer * 3 + 2] = (float) z / ((float) VERTEX_COUNT - 1) * SIZE;
                Vector3f normal = calculateNormal(x, z);
                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;
                vertexPointer++;
            }
        }
        ModelData data = modelRequest.getData();
        modelRequest = new ModelLoaderRequest(new ModelData(vertices, data.getTexcoords(), normals, data.getIndices()));
        GLRequestProcessor.sendRequest(modelRequest);
    }

    private void generateTerrain() {





        int VERTEX_COUNT = 16;
//        heights = new float[VERTEX_COUNT][VERTEX_COUNT];
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
        int vertexPointer = 0;
        int index = 0;
        fHeightMap = new float[count];
        for (int z = 0; z < VERTEX_COUNT; z++) {            // Boucle de generation de monde
            for (int x = 0; x < VERTEX_COUNT; x++) {
                fHeightMap[index] = getHeight(x, z);
                index++;
            }
        }
//        if (worldChunk != null && worldChunk.size() > 0) {
//            smoothTerrain(worldChunk.get(new Vector2f(x / SIZE - 1, z / SIZE)),
//                    worldChunk.get(new Vector2f(x / SIZE + 1, z / SIZE)),
//                    worldChunk.get(new Vector2f(x / SIZE, z / SIZE + 1))
//                    , worldChunk.get(new Vector2f(x / SIZE, z / SIZE - 1)));
//        }
        for (int z = 0; z < VERTEX_COUNT; z++) {            // Boucle de generation de monde
            for (int x = 0; x < VERTEX_COUNT; x++) {
                vertices[vertexPointer * 3] = (float) x / ((float) VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer * 3 + 1] = getHeight(x, z);
                vertices[vertexPointer * 3 + 2] = (float) z / ((float) VERTEX_COUNT - 1) * SIZE;
                Vector3f normal = calculateNormal(x, z);
                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;
                textureCoords[vertexPointer * 2] = (float) x / ((float) VERTEX_COUNT - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) z / ((float) VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {       //boucle de generation des indices
            for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
                int topLeft = (gz * VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        modelRequest = new ModelLoaderRequest(new ModelData(vertices, textureCoords, normals, indices));
        GLRequestProcessor.sendRequest(modelRequest);
    }

    private void smoothTerrain(Chunk leftC, Chunk rightC, Chunk upC, Chunk bottomC) {
//        int edgeL = (int) Math.sqrt(left.heights.length);
//        int edgeR = (int) Math.sqrt(right.heights.length);
//        int edgeU = (int) Math.sqrt(up.heights.length);
//        int edgeD = (int) Math.sqrt(bottom.heights.length);

        boolean leftIsModified = false;
        boolean rightIsModified = false;
        boolean upIsModified = false;
        boolean bottomIsModified = false;
        Terrain t;
        for (int z = 0; z < 16; z++) {
            if (leftC == null && rightC == null) {
                break;
            }
            if (leftC == null) {
                //this.heights[z][0] = (0 + this.heights[z][0])/2;
            } else {
                t = leftC.getTerrain();
                //    if (t.heights[z][0] != 0) {
                t.heights[15][z] = this.heights[0][z]  = (t.heights[15][z]  + this.heights[0][z] ) / 2;
                leftIsModified = true;
                //}

            }
            if (rightC == null) {
                //this.heights[z][63] = (0 + this.heights[z][63])/2;
            } else {
                t = rightC.getTerrain();
                //if (t.heights[z][0] != 0) {
                t.heights[0][z] = this.heights[15][z]  = (t.heights[0][z]  + this.heights[15][z] ) / 2;
                rightIsModified = true;
                //}

            }
        }
        for (int x = 0; x < 16; x++) {
            if (upC == null && bottomC == null) {
                break;
            }
            if (upC == null) {
                //this.heights[0][x] = (0 + this.heights[0][x])/2;
            } else {
                t = upC.getTerrain();
                //   if (t.heights[0][x] != 0) {
                this.heights[x][15] = (t.heights[x][0] + this.heights[x][15]) / 2;
                t.heights[x][0] = this.heights[x][15];
                upIsModified = true;
                //  }

            }
            if (bottomC == null) {
                //this.heights[63][x] = (0 + this.heights[63][x])/2;
            } else {
                t = bottomC.getTerrain();
                // if (t.heights[0][x] != 0) {
                this.heights[x][0] = (t.heights[x][15] + this.heights[x][0]) / 2;
                t.heights[x][15] = this.heights[x][0];
                bottomIsModified = true;
                // }
            }
        }

        if (leftIsModified) {
            leftC.getTerrain().regenerateTerrain();
        }
        if (rightIsModified) {
            rightC.getTerrain().regenerateTerrain();
        }
        if (upIsModified) {
            upC.getTerrain().regenerateTerrain();
        }
        if (bottomIsModified) {
            bottomC.getTerrain().regenerateTerrain();
        }

    }
    private Vector3f calculateNormal(int x, int z) {
        float heightL = getHeight(x - 1, z);
        float heightR = getHeight(x + 1, z);
        float heightD = getHeight(x, z - 1);
        float heightU = getHeight(x, z + 1);
        Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
        normal.normalize();
        return normal;
    }



    private Vector3f calculateNormal(int x, int z, BufferedImage image) {
        float heightL = getHeight(x - 1, z, image);
        float heightR = getHeight(x + 1, z, image);
        float heightD = getHeight(x, z - 1, image);
        float heightU = getHeight(x, z + 1, image);
        Vector3f normal = new Vector3f(heightL - heightR, 1f, heightD - heightU);
        normal.normalize();
        return normal;
    }

    private float getHeight(int x, int z, BufferedImage image) {
        if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
            return 0;
        }
        float height = image.getRGB(x, z);
        height += MAX_PIXEL_COLOR / 2;
        height /= MAX_PIXEL_COLOR / 2;
        height *= MAX_HEIGHT;
        return height;
    }

    private float getHeight(int x, int z) {
        if (x < -1 || x >= 17 || z < -1 || z >= 17) {
            return 0;
        }
        return heights[x+1][z+1];
    }

    public void setModel() {
        if (modelRequest.isExecuted()) {
            this.model = modelRequest.getModel();
            control.setSpatial(this);
            control.setPhysicsSpace(World.getPhysics());
        }
    }


    public float[] getfHeightMap() {
        return fHeightMap;
    }

    public static int getSIZE() {
        return SIZE;
    }

    public float[][] getHeights() {
        return heights;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public MeshModel getModel() {
        return model;
    }

    public TerrainTexturePack getTexturePack() {
        return texturePack;
    }

    public TerrainTexture getBlendMap() {
        return blendMap;
    }

    public void setControl(TerrainControl tc) {

        this.control = tc;
    }

    public TerrainControl getControl() {
        return control;
    }
}
