package fr.ostix.game.inventory;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.inventoryEvent.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.items.*;
import fr.ostix.game.menu.*;
import org.joml.*;

import java.util.*;

public abstract class Inventory extends Screen {
    protected final List<ItemStack> items = new ArrayList<>();
    private final GuiTexture backGround;
    private boolean isOpen = false;


    public Inventory(String title) {
        super(title);
        this.backGround = new GuiTexture(ResourcePack.getTextureByName("inventory").getID(),
                new Vector2f(0), new Vector2f(1920,
                1080));
        init();
    }

    public void open() {
        MasterGui.addGui(backGround);
        EventManager.getInstance().callEvent(new InventoryOpenEvent(1, this));
        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void update() {
        if (isOpen) {
            this.render();
        }
    }

    public void render() {
    }

    public void addItems(List<ItemStack> items) {
        this.items.addAll(items);
    }

    public void removeItems(List<ItemStack> items) {
        List<ItemStack> iToRemove = new ArrayList<>();
        items.forEach(stack ->{
            if (stack.getCount() <= 0){
                iToRemove.add(stack);
            }
        });
        items.removeAll(iToRemove);
    }


    public void close() {
        MasterGui.removeGui(backGround);
        EventManager.getInstance().callEvent(new InventoryCloseEvent(1, this));
        isOpen = false;
    }

    public boolean has(ItemStack item) {
        return items.stream().anyMatch(itemStack ->
                itemStack.getItem().getId() == item.getItem().getId()
                        && itemStack.getCount() >= item.getCount());
    }
}
