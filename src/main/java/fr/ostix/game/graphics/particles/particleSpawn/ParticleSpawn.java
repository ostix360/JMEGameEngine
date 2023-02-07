package fr.ostix.game.graphics.particles.particleSpawn;

import org.joml.*;

public interface ParticleSpawn {
    Random random = new Random();

    Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale);

    void load(String[] values);
}
