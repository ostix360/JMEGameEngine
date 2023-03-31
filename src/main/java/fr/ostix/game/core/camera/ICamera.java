package fr.ostix.game.core.camera;


import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface ICamera {

    Matrix4f getViewMatrix();

    Matrix4f getProjectionMatrix();

    Matrix4f getProjectionViewMatrix();

    void move();

    Vector3f getPosition();

}
