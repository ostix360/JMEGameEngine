package fr.ostix.game.core.events.listener.keyListeners;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.entity.*;
import fr.ostix.game.core.events.entity.npc.NPCTalkEvent;
import fr.ostix.game.core.events.keyEvent.*;
import fr.ostix.game.core.events.player.*;
import fr.ostix.game.core.events.states.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.entity.entities.NPC;
import fr.ostix.game.inventory.*;
import fr.ostix.game.menu.*;
import fr.ostix.game.menu.stat.PauseMenu;
import fr.ostix.game.world.*;
import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;

public class KeyInGameListener implements KeyListener {
    private final World world;
    private final WorldState worldState;
    private final Player p;
    private final PlayerInventory pi;

    private final QuestHandlerMenu questHandlerMenu;

    public KeyInGameListener(WorldState state, Player p, PlayerInventory pi) {
        this.world = state.getWorld();
        this.worldState = state;
        this.p = p;
        this.pi = pi;
        this.questHandlerMenu = new QuestHandlerMenu();
    }

    @Override
    @EventHandler
    public void onKeyPress(KeyPressedEvent e) {

        if (e.getKEY() == GLFW_KEY_Q) {
            if (!pi.isOpen()) {
                EventManager.getInstance().callEvent(new StateOverWorldEvent("Player Inventory",pi,2));
            } else {
                EventManager.getInstance().callEvent(new StateOverWorldEvent("world",null,2));
            }
        }
        if (e.getKEY() == GLFW_KEY_ESCAPE) { // Resume menu
            if (worldState.hasScreenOverWorld()) {
                EventManager.getInstance().callEvent(new StateOverWorldEvent("world",null,2));
            }else{
                EventManager.getInstance().callEvent(new StateOverWorldEvent("Resume Menu",new PauseMenu(),2));
            }

        }

        if (e.getKEY() == GLFW_KEY_TAB) {
            if (!QuestHandlerMenu.isOpened()) {
                EventManager.getInstance().callEvent(new StateOverWorldEvent("Quest Handler Menu",questHandlerMenu,2));
            }else{
                EventManager.getInstance().callEvent(new StateOverWorldEvent("world",null,2));
            }
        }
        if (e.getKEY() == GLFW_KEY_E) {
            if (!world.getEntitiesNear().isEmpty()) {
                Entity entity = world.getEntitiesNear().get(0);
                if (entity instanceof NPC) {
                    EventManager.getInstance().callEvent(new NPCTalkEvent(world, 1, (NPC) entity));
                }
                EventManager.getInstance().callEvent(new PlayerGiveItemEvent(world.getPlayer(), world, 1));
                EventManager.getInstance().callEvent(new EntityInteractEvent(world.getEntitiesNear().get(0), world, 3));

            }
        }
    }

    @Override
    @EventHandler
    public void onKeyReleased(KeyReleasedEvent e) {
    }

    @Override
    @EventHandler
    public void onKeyMaintained(KeyMaintainedEvent e) {
//        if (e.getKEY() == GLFW_KEY_W || e.getKEY() == GLFW_KEY_UP) {
//            p.setMovement(Entity.MovementType.FORWARD);
//            EventManager.getInstance().callEvent(new PlayerMoveEvent(p, 1));
//        } else if (e.getKEY() == GLFW_KEY_S || e.getKEY() == GLFW_KEY_DOWN) {
//            p.setMovement(Entity.MovementType.BACK);
//            EventManager.getInstance().callEvent(new PlayerMoveEvent(p, 1));
//        }
//        if (e.getKEY() == GLFW_KEY_A || e.getKEY() == GLFW_KEY_LEFT) {
//            p.increaseRotation(new Vector3f(0, 1.8f, 0));
//        } else if (e.getKEY() == GLFW_KEY_D || e.getKEY() == GLFW_KEY_RIGHT) {
//            p.increaseRotation(new Vector3f(0, -1.8f, 0));
//        }
//        if (e.getKEY() == GLFW_KEY_LEFT_SHIFT) {
//            EventManager.getInstance().callEvent(new PlayerMoveEvent(p, 1));
//            p.increasePosition(new Vector3f(0,-2,0));
//        }

//        if (e.getKEY() == GLFW_KEY_W || e.getKEY() == GLFW_KEY_UP) {
//            p.setMovement(Entity.MovementType.FORWARD);
//            EventManager.getInstance().callEvent(new PlayerMoveEvent(p, 1));
//        } else if (e.getKEY() == GLFW_KEY_S || e.getKEY() == GLFW_KEY_DOWN) {
//            p.setMovement(Entity.MovementType.BACK);
//            EventManager.getInstance().callEvent(new PlayerMoveEvent(p, 1));
//        } else {
//            p.setMovement(Entity.MovementType.STATIC);
//            EventManager.getInstance().callEvent(new PlayerMoveEvent(p, 1));
//        }
//
//
//        if (e.getKEY() == GLFW_KEY_A || e.getKEY() == GLFW_KEY_LEFT) {
//            p.increaseRotation(new Vector3f(0, 1.8f, 0));
//        } else if (e.getKEY() == GLFW_KEY_D || e.getKEY() == GLFW_KEY_RIGHT) {
//            p.increaseRotation(new Vector3f(0, -1.8f, 0));
//        }
//        if (e.getKEY() == GLFW_KEY_LEFT_SHIFT) {
////            EventManager.getInstance().callEvent(new PlayerMoveEvent(p, 1));
//            p.getControl().setWalkDirection(new Vector3f(0, -10, 0));
//        }
    }


    public void update() {
        if (Input.keys[GLFW_KEY_W] || Input.keys[GLFW_KEY_UP]) {
            p.setMovement(Entity.MovementType.FORWARD);
            EventManager.getInstance().callEvent(new PlayerMoveEvent(p, 1));
        } else if (Input.keys[GLFW_KEY_S] || Input.keys[GLFW_KEY_DOWN]) {
            p.setMovement(Entity.MovementType.BACK);
            EventManager.getInstance().callEvent(new PlayerMoveEvent(p, 1));
        } else {
            p.setMovement(Entity.MovementType.STATIC);
            EventManager.getInstance().callEvent(new PlayerMoveEvent(p, 1));
        }
        if (Input.keys[GLFW_KEY_SPACE]) {
            p.setMovement(Entity.MovementType.JUMP);
            p.getControl().jump();
        }

        if (Input.keys[GLFW_KEY_A] || Input.keys[GLFW_KEY_LEFT]) {
            p.increaseRotation(new Vector3f(0, 1.8f, 0));
        } else if (Input.keys[GLFW_KEY_D] || Input.keys[GLFW_KEY_RIGHT]) {
            p.increaseRotation(new Vector3f(0, -1.8f, 0));
        }
        if (Input.keys[GLFW_KEY_LEFT_SHIFT]) {
//            EventManager.getInstance().callEvent(new PlayerMoveEvent(p, 1));
            p.getControl().setWalkDirection(new Vector3f(0, -10, 0));
        }

    }
}
