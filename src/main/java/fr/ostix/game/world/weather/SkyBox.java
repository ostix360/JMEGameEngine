package fr.ostix.game.world.weather;

import fr.ostix.game.core.camera.*;
import fr.ostix.game.core.loader.*;
import fr.ostix.game.graphics.model.*;

public class SkyBox {


    private final MeshModel box;
    private final ICamera cam;
    private SkyTexture texture1;
    private SkyTexture texture2;
    private float blendFactor;
    private float rotation;

    public SkyBox(ICamera camera) {
        cam = camera;
        box = Loader.INSTANCE.loadToVAO(VERTICES, 3);
    }

    public MeshModel getBox() {
        return box;
    }

    public ICamera getCam() {
        return cam;
    }

    public void setTextures(SkyTexture texture1, SkyTexture texture2) {
        this.texture1 = texture1;
        this.texture2 = texture2;
    }

    public void setBlendFactor(float blendFactor) {
        this.blendFactor = blendFactor;
    }

    public void update(float deltaTime) {
        this.rotation += deltaTime *0.4f;
    }

    public SkyTexture getPrimaryTexture() {
        return texture1;
    }

    public SkyTexture getSecondaryTexture() {
        return texture2;
    }

    public float getBlendFactor() {
        return blendFactor;
    }

    public float getRotation() {
        return rotation;
    }

    public float getX(){
        return cam.getPosition().x();
    }

    public float getY(){
        return 0f;
    }

    public float getZ(){
        return cam.getPosition().z();
    }

    private static final float SIZE = 400f;
    private static final float[] VERTICES = {
            -SIZE, SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,

            -SIZE, -SIZE, SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,
            -SIZE, SIZE, SIZE,
            -SIZE, -SIZE, SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE, SIZE,
            -SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, -SIZE, SIZE,
            -SIZE, -SIZE, SIZE,

            -SIZE, SIZE, -SIZE,
            SIZE, SIZE, -SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            -SIZE, SIZE, SIZE,
            -SIZE, SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE, SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE, SIZE,
            SIZE, -SIZE, SIZE
    };
}
