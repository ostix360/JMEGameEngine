package fr.ostix.game.entity;


import fr.ostix.game.entity.component.*;
import fr.ostix.game.entity.component.collision.*;
import fr.ostix.game.entity.component.particle.*;
import fr.ostix.game.graphics.model.*;
import org.joml.*;

import java.util.*;


public class Entity {

    private final int id;
    private final Model model;
    protected Vector3f position;
    protected Vector3f rotation;
    protected Vector3f scale;
    private final Transform transform;
    protected MovementType movement = MovementType.STATIC;
    private CollisionComponent collision;
    private int textureIndex = 1;
    protected boolean canInteract = false;

    private final List<Component> components = new ArrayList<>();

    public Entity(int id, Model model, Vector3f position, Vector3f rotation, float scale) {
        this.id = id;
        this.model = model;
        this.position = position;
        this.rotation = rotation.add(0, 0, 0);
        this.scale = new Vector3f(scale);
        this.transform = new Transform(position, rotation, scale);
    }


    public CollisionComponent getCollision() {
        return collision;
    }

    public void setCollision(CollisionComponent collision) {
        this.collision = collision;
    }

    public void addComponent(Component c) {
        components.add(c);
    }

    public void update() {
        for (Component c : components) {
            if (c instanceof ParticleComponent) {
                ((ParticleComponent) c).setOffset(new Vector3f(0, 8.5f, 0));
            }
            c.update();
        }
    }


    public boolean canInteract() {
        return canInteract;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void increasePosition(Vector3f value) {
        position.add(value);
    }

    public void increaseRotation(Vector3f value) {
        rotation.add(value);
        //transform.setRotation(rotation);
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Transform getTransform() {
        transform.setRotation(rotation);
        transform.setPosition(position);
        transform.setScale(scale);
        return transform;
    }

    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }

    public float getTextureXOffset() {
        if (model != null && model.getTexture() != null) {
            float column = textureIndex % model.getTexture().getNumbersOfRows();
            return column / model.getTexture().getNumbersOfRows();
        }
        return 1;
    }

    public float getTextureYOffset() {
        if (model != null && model.getTexture() != null) {
            float row = textureIndex / (float) model.getTexture().getNumbersOfRows();
            return row / model.getTexture().getNumbersOfRows();
        }
        return 1;
    }

    public int getId() {
        return id;
    }

    public MovementType getMovement() {
        return movement;
    }

    public void setMovement(MovementType movement) {
        this.movement = movement;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Entity entity = (Entity) o;
        return Objects.equals(getModel(), entity.getModel()) && Objects.equals(getPosition(), entity.getPosition()) && Objects.equals(getRotation(), entity.getRotation()) && Objects.equals(getScale(), entity.getScale()) && Objects.equals(components, entity.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModel(), getPosition(), getRotation(), getScale(), components);
    }

    public enum MovementType {
        FORWARD("run"),
        BACK("back"),
        JUMP("jump"),
        STATIC("staying");
        private final String id;

        MovementType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }


}
