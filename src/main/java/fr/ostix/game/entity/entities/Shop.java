package fr.ostix.game.entity.entities;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.listener.EntityListener;
import fr.ostix.game.core.events.states.StateOverWorldEvent;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.graphics.font.meshCreator.*;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.inventory.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.world.World;
import org.joml.*;
import org.lwjgl.glfw.GLFW;

public class Shop extends Entity implements Interact {

    private final ShopInventory inventory;


    public Shop(Model model, Vector3f position, Vector3f rotation, float scale, int id) {
        super(id, model, position, rotation, scale);
        inventory = new ShopInventory();
        inventory.init();
        this.canInteract = true;



    }

    @Override
    public void initBeforeSpawn() {
        super.initBeforeSpawn();
//        EventManager.getInstance().register(new EntityListener(this, (e) -> {
//            MasterGui.addGui(bgInteraction);
//            MasterFont.addTempFont(interactionText);
//            EventManager.getInstance().register(keyListener);// TODO Remove the current KeyListener and add a menu listener
//            if (Input.keyPressed(GLFW.GLFW_KEY_E)) {
//                if (inventory.isOpen()) {
//                    inventory.close();
//                } else {
//                    inventory.open();
//                }
//            }
//        }));
    }



    @Override
    public boolean canInteract() {
        return true;
    }

    @Override
    public void update() {
        super.update();
        inventory.update();
    }

    public ShopInventory getInventory() {
        return inventory;
    }


    @Override
    public void interact(World world) {
        if (inventory.isOpen()) {
            EventManager.getInstance().callEvent(new StateOverWorldEvent("Shop",inventory, 2));
        } else {
            EventManager.getInstance().callEvent(new StateOverWorldEvent("Shop",null, 2));
        }
    }
}
