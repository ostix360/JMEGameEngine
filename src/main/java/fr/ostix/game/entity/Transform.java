package fr.ostix.game.entity;

import fr.ostix.game.toolBox.Maths;
import org.joml.Math;
import org.joml.*;

public class Transform {
    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;
    private Matrix3f rotationMatrix;

    public Transform(Vector3f position, Vector3f rotation, float scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = new Vector3f(scale);
    }

    public static Transform load(String values) {
        String[] value = values.split(";");
        int index = 0;
        Vector3f pos = new Vector3f(Float.parseFloat(value[index++]), Float.parseFloat(value[index++]),
                Float.parseFloat(value[index++]));
        Vector3f rot = new Vector3f(Float.parseFloat(value[index++]), Float.parseFloat(value[index++]),
                Float.parseFloat(value[index++]));
        float scale = Float.parseFloat(value[index]);
        return new Transform(pos, rot, scale);
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setQ(Quaternionf q) {
        rotationMatrix = new Matrix3f().identity();
        rotationMatrix.rotate(q);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Matrix4f getTransformation() {
        Matrix4f m = Maths.createTransformationMatrix(this.position, this.rotation, this.scale);
        if (rotationMatrix != null) m.mul(rotationMatrix.get(new Matrix4f()));
        return m;
    }

    public Quaternionf getQRotation(){
        Matrix3f matrix = new Matrix3f();

        matrix.rotate(Math.toRadians(this.rotation.z()), new Vector3f(0, 0, 1));
        matrix.rotate(Math.toRadians(this.rotation.y()), new Vector3f(0, 1, 0));
        matrix.rotate(Math.toRadians(this.rotation.x()), new Vector3f(1, 0, 0));
        return matrix.getNormalizedRotation(new Quaternionf());
    }

    @Override
    public Object clone(){
        return new Transform(new Vector3f(this.getPosition()),new Vector3f(this.getRotation()),this.getScale().x());
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
