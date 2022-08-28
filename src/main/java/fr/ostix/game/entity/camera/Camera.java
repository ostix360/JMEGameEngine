package fr.ostix.game.entity.camera;

import fr.ostix.game.core.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.graphics.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.world.*;
import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;

import java.lang.Math;

public class Camera implements ICamera {

    private final SmoothFloat distanceFromPlayer = new SmoothFloat(50, 8);
    private final SmoothFloat angleAroundPlayer = new SmoothFloat(0, 10);
    public int viewDistance = 15;
    private Matrix4f projection;

    private final Vector3f position;
    float pitch = 20;
    float yaw = 0;
    private final float roll = 0;

    float elapsedMouseDY;

    private final Player player;

    public Camera(Player player) {
        this.player = player;
        this.position = new Vector3f(player.getPosition());
    }

    private float terrainHeight;

    @Override
    public Matrix4f getViewMatrix() {
        return Maths.createViewMatrix(this);
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return projection;
    }

    @Override
    public Matrix4f getProjectionViewMatrix() {
        return projection.mul(getViewMatrix());
    }

    public void move() {
        this.terrainHeight = World.getTerrainHeight(this.position.x(), this.position.z()) + 2;
        calculateZoom();
        calculateAngleAroundPlayerAndPitch();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        float yOffset = updateSmooth(player.getPosition().y() + 4 + verticalDistance, this.position.y());
        caculateCameraPosition(horizontalDistance, yOffset);
        this.yaw = 180 - (player.getRotation().y() + angleAroundPlayer.get());
        this.projection = MasterRenderer.getProjectionMatrix();
    }

    private float updateSmooth(float target, float actual) {
        float offset = target - actual;
        float change = offset * 1 / 60 * 5;
        return actual + change;
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer.get() * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer.get() * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        float zoomLevel = Input.getMouseDWhell();
        float target = this.distanceFromPlayer.getTarget();
        target -= zoomLevel;
        if (target <= 3) {
            target = 3;
        }
        if (target >= 105) {
            target = 105;
        }
        distanceFromPlayer.setTarget(target);
    }

    private void caculateCameraPosition(float horzontalDistance, float yOffset) {
        float theta = player.getRotation().y() + angleAroundPlayer.get();
        float xoffset = (float) (horzontalDistance * Math.sin(Math.toRadians(theta)));
        float zoffset = (float) (horzontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x() - xoffset;
        position.y = yOffset;
//        if (position.y < terrainHeight) {
//            position.y = terrainHeight;
//        }
        position.z = player.getPosition().z() - zoffset;
    }

    private void calculateAngleAroundPlayerAndPitch() {
        if (Input.keysMouse[GLFW_MOUSE_BUTTON_1]) {
            float angleChange = Input.mouseDX * 0.1f;
            angleAroundPlayer.increaseTarget(-angleChange);
            float pitchChange = Input.mouseDY * 0.1f;
            pitch += pitchChange;
            if (pitch >= 90) {
                pitch = 90;
            }
            if (pitch <= -4) {
                if (elapsedMouseDY < pitchChange) distanceFromPlayer.increaseTarget((float) (pitchChange * 1.4));
                pitch = -4;
            }
            elapsedMouseDY = pitchChange;
        }
        angleAroundPlayer.update(1 / 60f);
        distanceFromPlayer.update(1f / 60f);
    }

    public void invertPitch() {
        this.pitch = -pitch;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void reflect(float height) {
        this.invertPitch();
        this.position.y = position.y - 2 * (position.y - height);
    }
}
