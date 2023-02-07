package fr.ostix.game.audio;

import fr.ostix.game.entity.*;
import org.joml.*;

import java.lang.Math;

import static org.lwjgl.openal.AL10.*;

public class SoundListener {

    private final Player player;

    public SoundListener(Player player) {
        this.player = player;
        this.updateTransform();
    }


    public void setVelocity() {
        Vector3f v = player.getControl().getVelocity();
        alListener3f(AL_VELOCITY, v.x(), v.y(), v.z());
    }

    public void updateTransform() {
        Vector3f pos = player.getPosition();
        Vector3f rotation = player.getRotation();
        alListener3f(AL_POSITION, pos.x(), pos.y(), pos.z());
        Matrix4f matrix4f = new Matrix4f().identity();
        matrix4f.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        matrix4f.translate(-pos.x(), -pos.y(), -pos.z());
        Vector3f at = new Vector3f();
        matrix4f.positiveZ(at).negate();
        Vector3f up = new Vector3f();
        matrix4f.positiveY(up);
        float[] data = new float[6];
        data[0] = at.x();
        data[1] = at.y();
        data[2] = -at.z();
        data[3] = up.x();
        data[4] = up.y();
        data[5] = up.z();
        alListenerfv(AL_ORIENTATION, data);
        setVelocity();
    }
}
