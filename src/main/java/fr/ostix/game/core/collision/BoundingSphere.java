package fr.ostix.game.core.collision;

public class BoundingSphere {

    public float x,y,z;
    public float radius;


    public BoundingSphere(float x, float y, float z, float radius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "BoundingSphere{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", radius=" + radius +
                '}';
    }
}