package fr.ostix.game.entity;


import com.jme3.bullet.control.PhysicsControl;
import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.collision.CollisionComponent;
import fr.ostix.game.entity.component.particle.ParticleComponent;
import fr.ostix.game.graphics.model.Model;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    protected PhysicsControl physic;

    private final List<Component> components = new ArrayList<>();

    public Entity(int id, Model model, Vector3f position, Vector3f rotation, float scale) {
        this.id = id;
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = new Vector3f(scale);
        this.transform = new Transform(position, rotation, scale);
    }
    public Entity(Entity entity) {
        this.model = entity.model;
        this.position = new Vector3f(entity.position);
        this.rotation = new Vector3f(entity.rotation);
        this.scale = new Vector3f(entity.scale);
        this.transform = entity.transform;
        this.textureIndex = entity.textureIndex;
        this.id = entity.id;
        this.movement = MovementType.STATIC;
    }

    public void initBeforeSpawn(){

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
        if (physic != null) {
            physic.update(1/60f);
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
//        return new Vector3f(1);
        return position;
    }

    public Vector3f getRPosition(){
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

    public void setPhysic(PhysicsControl physic) {
        this.physic = physic;
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

    public Object getControl() {
        if (this.physic == null){
            collision.getPhysic().setSpatial(this);
            return collision.getPhysic();
        }
        physic.setSpatial(this);
        return physic;
    }

    public void cleanUp() {

    }

    public boolean isInteracting() {
        return false;
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
