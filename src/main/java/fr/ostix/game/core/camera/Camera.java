package fr.ostix.game.core.camera;

import fr.ostix.game.core.Input;
import fr.ostix.game.entity.Player;
import fr.ostix.game.entity.Transform;
import fr.ostix.game.graphics.MasterRenderer;
import fr.ostix.game.toolBox.Maths;
import fr.ostix.game.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class Camera implements ICamera {

    private final SmoothFloat distanceFromObject = new SmoothFloat(50, 8);

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
        if (projection == null) {
            projection = MasterRenderer.getProjectionMatrix();
        }
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
        return (float) (distanceFromObject.get() * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromObject.get() * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        float zoomLevel = Input.getMouseDWhell();
        float target = this.distanceFromObject.getTarget();
        target -= zoomLevel;
        if (target <= 3) {
            target = 3;
        }
        if (target >= 105) {
            target = 105;
        }
        distanceFromObject.setTarget(target);
    }

    private void caculateCameraPosition(float horizontalDistance, float yOffset) {
        float theta = player.getRotation().y() + angleAroundPlayer.get();
        float xoffset = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float zoffset = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x() - xoffset;
        position.y = yOffset;
        if (position.y < terrainHeight) {
            position.y = terrainHeight;
        }
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
                if (elapsedMouseDY < pitchChange) distanceFromObject.increaseTarget((float) (pitchChange * 1.4));
                pitch = -4;
            }
            elapsedMouseDY = pitchChange;
        }
        angleAroundPlayer.update(1 / 60f);
        distanceFromObject.update(1f / 60f);
    }

    public void invertPitch() {
        this.pitch = -pitch;
    }

    public Vector3f getPosition() {
        return new Vector3f(position);
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

    public void goTo(Transform t) {

        float zoom = 10;
        float pitchP = 20;


        float angleAroundObject = 180;
        float horizontalDistance = (float) (zoom * Math.cos(Math.toRadians(pitch)));
        float verticalDistance = (float) (zoom * Math.sin(Math.toRadians(pitch)));

        float yOffset = t.getPosition().y() + verticalDistance;
        float theta = t.getRotation().y() + angleAroundObject;
        float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        float xP = t.getPosition().x() - xOffset;
        float yP = yOffset;
        if (yP < terrainHeight) {
            yP = terrainHeight;
        }
        float zP = t.getPosition().z() - zOffset;

        float yawP = 180 - (t.getRotation().y() + angleAroundObject);
        this.projection = MasterRenderer.getProjectionMatrix();


        SmoothFloat x = new SmoothFloat(position.x(),5f);
        SmoothFloat y = new SmoothFloat(position.y(),5f);
        SmoothFloat z = new SmoothFloat(position.z(),5f);
        SmoothFloat yaw = new SmoothFloat(this.yaw,5f);
        SmoothFloat pitch = new SmoothFloat(this.pitch,5f);
        x.setTarget((int) (xP));
        y.setTarget((int) (yP));
        z.setTarget((int) (zP));
        yaw.setTarget(yawP);
        pitch.setTarget(pitchP);
        float delta = 1 / 200f;
        Thread thread = new Thread(() -> {
            while (true) {
                x.update(delta);
                y.update(delta);
                z.update(delta);
                yaw.update(delta);
                pitch.update(delta);
                this.position.x = x.get();
                this.position.y = y.get();
                this.position.z = z.get();
                this.yaw = yaw.get();
                this.pitch = pitch.get();
                if (Maths.almostEqual(this.position.x(),x.getTarget(),1f) && Maths.almostEqual(this.position.y(),y.getTarget(),1f) && Maths.almostEqual(this.position.z(),z.getTarget(),1f)){
                    break;
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.position.x = x.get();
            this.position.y = y.get();
            this.position.z = z.get();

        });
        thread.start();
    }
}
