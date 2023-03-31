package fr.ostix.game.entity;

import com.jme3.bullet.control.BetterCharacterControl;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.inventory.PlayerInventory;
import fr.ostix.game.world.World;
import org.joml.Vector3f;

public class Player extends Entity {

    private static final float RUN_SPEED = 160;
    private static final float TURN_SPEED = 780;
    public static final float GRAVITY = 0.12f;
    private static final float JUMP_POWER = 2;

    private final float currentSpeed = 0;
    private final float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    public boolean canJump = true;

    private final int health = 10;
    private int sprintTime = 60 * 5;
    private final boolean isSprinting = false;

    private PlayerInventory inventory;



    public Player(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(-1, model, position, rotation, scale);
        this.physic = new BetterCharacterControl(1f, 4f, 20f);
        ((BetterCharacterControl)this.physic).getJumpForce().mul(2.8f);

    }


//    public Player(Entity e) {
//        super(e.getModel(), e.getPosition(), e.getRotation(), e.getScale());
//    }

    @Override
    public void update() {
        super.update();
        this.move();
        if (this.getMovement() == MovementType.FORWARD) {
            this.sprintTime--;
            if (this.sprintTime < 0) {
                sprintTime = 0;
            }
        } else {
            this.sprintTime++;
            if (this.sprintTime > 60 * 5) {
                sprintTime = 60 * 5;
            }
        }
//        ( (BetterCharacterControl)physic).update(1/60f);
    }

    private void move() {

        super.increaseRotation(new Vector3f(0, this.currentTurnSpeed * 0.0023f, 0));
//        float distance = currentSpeed ;
//        float dx = distance * Math.sin(Math.toRadians(super.getRotation().y()));
//        float dz = distance * Math.cos(Math.toRadians(super.getRotation().y()));
//
//
//        forceToCenter.add(new Vector3(dx, 0, dz));
        //upwardsSpeed -= GRAVITY;

        if (upwardsSpeed <= -9.18f) {
            upwardsSpeed = -9.18f;
        }

        this.upwardsSpeed = 0;
        //super.increasePosition(new Vector3f(dx, upwardsSpeed, dz));
        //if (!canJump) {
        //  }
        float terrainHeight = World.getTerrainHeight(this.getPosition().x(), this.getPosition().z())-25f;
        if (this.getPosition().y() <= terrainHeight) {
//            canJump = true;

            ((BetterCharacterControl) physic).setWalkDirection(((BetterCharacterControl) physic).getWalkDirection().add(0,-9.81f,0));
            position.set(this.getPosition().x(), terrainHeight, this.getPosition().z());
            ((BetterCharacterControl) physic).setPhysicsLocation(this.getPosition());
        }

    }

    public void jump() {
        //if (canJump) {
        this.upwardsSpeed = 2;
        canJump = false;
        // }
    }

    @Override
    public BetterCharacterControl getControl() {
        return (BetterCharacterControl) super.getControl();
    }

    public int getHealth() {
        return health;
    }

    public float getSprintTime() {
        return (float) sprintTime / 5;
    }


    public PlayerInventory getInventory() {
        return inventory;
    }

    public void setInventory(PlayerInventory playerInventory) {
        this.inventory = playerInventory;
    }

}

