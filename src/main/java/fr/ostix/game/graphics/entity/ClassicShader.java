package fr.ostix.game.graphics.entity;

import fr.ostix.game.core.camera.Camera;
import fr.ostix.game.entity.component.light.Light;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.Maths;
import fr.ostix.game.toolBox.OpenGL.shader.ShaderProgram;
import fr.ostix.game.toolBox.OpenGL.shader.uniform.*;
import fr.ostix.game.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;

public class ClassicShader extends ShaderProgram {

    private final int MAX_LIGHTS = World.MAX_LIGHTS;

    private final MatrixUniform transformationMatrix = new MatrixUniform("transformationMatrix");
    private final MatrixUniform projectionMatrix = new MatrixUniform("projectionMatrix");
    private final MatrixUniform viewMatrix = new MatrixUniform("viewMatrix");
    public final BooleanUniform useFakeLighting = new BooleanUniform("useFakeLighting");
    public final BooleanUniform useSpecularMap = new BooleanUniform("useSpecularMap");
    public final Vector2fUniform offset = new Vector2fUniform("offset");
    public final FloatUniform numberOfRows = new FloatUniform("numberOfRows");
    private final FloatUniform reflectivity = new FloatUniform("reflectivity");
    private final FloatUniform shine = new FloatUniform("shine");
    private final Vector3fUniformArray lightPos = new Vector3fUniformArray("lightPos", MAX_LIGHTS);
    private final Vector3fUniformArray lightColor = new Vector3fUniformArray("lightColor", MAX_LIGHTS);
    private final Vector3fUniformArray lightAttenuation = new Vector3fUniformArray("attenuation", MAX_LIGHTS);
    private final FloatUniformArray lightPower = new FloatUniformArray("lightPower", MAX_LIGHTS);
    private final IntUniform specularMap = new IntUniform("specularMap");
    public final MatrixUniformArray jointTransforms = new MatrixUniformArray("jointTransforms", 50);
    public final BooleanUniform isAnimated = new BooleanUniform("isAnimated");
    private final Vector3fUniform skyColor = new Vector3fUniform("skyColor");
    private final IntUniform diffuseMap = new IntUniform("textureSampler");
    private final IntUniform normalMap = new IntUniform("normalMap");
    public final Vector4fUniform clipPlane = new Vector4fUniform("clipPlane");

    public ClassicShader() {
        super("shader");
        super.storeAllUniformsLocations(transformationMatrix, projectionMatrix, viewMatrix,
                reflectivity, shine, skyColor, jointTransforms, isAnimated, useSpecularMap,
                specularMap, diffuseMap, normalMap, offset, numberOfRows, useFakeLighting, lightPos, lightColor,
                lightAttenuation, lightPower, clipPlane);
    }

    public void connectTextureUnits() {
        diffuseMap.loadIntToUniform(0);
        specularMap.loadIntToUniform(1);
        normalMap.loadIntToUniform(2);
    }

    @Override
    protected void bindAllAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normals");
        super.bindAttribute(3, "jointIndices");
        super.bindAttribute(4, "weights");
    }


    // light

    public void loadLight(List<Light> lights) {
        Vector3f[] pos = new Vector3f[MAX_LIGHTS];
        Vector3f[] color = new Vector3f[MAX_LIGHTS];
        Vector3f[] attenuation = new Vector3f[MAX_LIGHTS];
        float[] power = new float[MAX_LIGHTS];
        for (int i = 0; i < MAX_LIGHTS - 1; i++) {
            if (i < lights.size()) {
                Light light = lights.get(i);
                pos[i] = light.getPosition();
                color[i] = light.getColourVec3f();
                attenuation[i] = light.getAttenuation();
                power[i] = light.getPower();
            } else {
                pos[i] = new Vector3f(0, 0, 0);
                color[i] = new Vector3f(0, 0, 0);
                attenuation[i] = new Vector3f(1, 0, 0);
                power[i] = 0F;
            }
        }
        Light sun = lights.get(lights.size() - 1);
        pos[MAX_LIGHTS-1] = sun.getPosition();
        color[MAX_LIGHTS-1] = sun.getColourVec3f();
        attenuation[MAX_LIGHTS-1] = sun.getAttenuation();
        power[MAX_LIGHTS-1] = sun.getPower();
        lightPos.loadVector3fToUniform(pos);
        lightColor.loadVector3fToUniform(color);
        lightAttenuation.loadVector3fToUniform(attenuation);
        lightPower.loadFloatToUniform(power);

    }

    public void loadSpecular(float reflectivity, float shineDamper) {
        this.reflectivity.loadFloatToUniform(reflectivity);
        this.shine.loadFloatToUniform(shineDamper);
    }


    // Projection Transformation View Matrix

    public void loadTransformationMatrix(Matrix4f matrix) {
        transformationMatrix.loadMatrixToUniform(matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        projectionMatrix.loadMatrixToUniform(matrix);
    }

    public void loadViewMatrix(Camera cam) {
        viewMatrix.loadMatrixToUniform(Maths.createViewMatrix(cam));
    }


    public void loadSkyColor(Color skyColor) {
        this.skyColor.loadVector3fToUniform(skyColor.getVec3f());
    }
}
