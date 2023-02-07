package fr.ostix.game.graphics.particles.particleSpawn;

import org.joml.*;

public class Point implements ParticleSpawn {
    @Override
    public Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
        return new Vector3f(x, y, z);
    }

    @Override
    public void load(String[] values) {

    }
}
