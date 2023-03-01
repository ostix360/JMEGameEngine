package fr.ostix.game.toolBox;

import com.google.gson.annotations.Expose;
import org.joml.Vector3f;

public class Vec3f {

    @Expose
    private float x;
    @Expose
    private float y;
    @Expose
    private float z;

    public Vec3f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Vector3f toVector3f() {
        return new Vector3f(x,y,z);
    }
}
