package fr.ostix.game.core.collision;

public class AABoundingBox {
    public float minX,minY,minZ;
    public float maxX,maxY,maxZ;


    public AABoundingBox(float x, float y, float z, float w, float h, float d) {
        this.minX = x;
        this.minY = y;
        this.minZ = z;
        this.maxX = x + w;
        this.maxY = y + h;
        this.maxZ = z + d;
    }
}