package fr.ostix.game.core.camera;


import org.joml.*;

public interface ICamera {

    Matrix4f getViewMatrix();

    Matrix4f getProjectionMatrix();

    Matrix4f getProjectionViewMatrix();

    void move();

    Vector3f getPosition();

}
