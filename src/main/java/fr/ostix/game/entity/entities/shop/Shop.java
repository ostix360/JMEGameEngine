package fr.ostix.game.entity.entities.shop;

import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.listener.EntityListener;
import fr.ostix.game.core.events.states.StateOverWorldEvent;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.entities.Interact;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.inventory.ShopInventory;
import fr.ostix.game.world.World;
import org.joml.Vector3f;

public class Shop extends Entity implements Interact {

    private final ShopInventory inventory;
    private final EntityListener listener;


    public Shop(Model model, Vector3f position, Vector3f rotation, float scale, int id) {
        super(id, model, position, rotation, scale);
        inventory = new ShopInventory();
        inventory.init();
        this.canInteract = true;
        this.listener = new EntityListener(this);
    }

    @Override
    public void initBeforeSpawn() {
        super.initBeforeSpawn();
        EventManager.getInstance().register(listener);
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
        inventory.setPlayer(world.getPlayer());
        if (!inventory.isOpen()) {
            EventManager.getInstance().callEvent(new StateOverWorldEvent("Shop",inventory, 2));
        } else {
            EventManager.getInstance().callEvent(new StateOverWorldEvent("Shop",null, 2));
        }
    }

    @Override
    public void cleanUp() {
        EventManager.getInstance().unRegister(listener);
    }
}
