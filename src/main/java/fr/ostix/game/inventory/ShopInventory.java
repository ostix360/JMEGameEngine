package fr.ostix.game.inventory;

import fr.ostix.game.items.*;

public class ShopInventory extends Inventory {

    private final ItemTab shopTab;

    public ShopInventory() {
        super("Shop");
        shopTab = ItemTab.newEmptyTab("Shop", 35);
        setItems();
    }

    private void setItems() {
        this.items.add(new ItemStack(Items.potion, 10));
        int i = 0;
        shopTab.getSlots()[i].setStack(new ItemStack(Items.potion, 10));
    }

    @Override
    public void open() {
        super.open();
        shopTab.startRendering();
    }

    @Override
    public void update() {
        super.update();
        if (isOpen()) shopTab.update();
    }

    @Override
    public void render() {
        shopTab.render();
    }

    @Override
    public void close() {
        super.close();
        shopTab.stopRendering();
    }
}
