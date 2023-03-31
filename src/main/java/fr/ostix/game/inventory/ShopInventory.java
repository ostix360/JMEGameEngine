package fr.ostix.game.inventory;

import fr.ostix.game.items.ItemStack;
import fr.ostix.game.items.ItemType;
import fr.ostix.game.items.Items;

public class ShopInventory extends Inventory {

    private final ItemTab shopTab;



    public ShopInventory() {
        super("Shop");
        shopTab = ItemTab.newEmptyTab("Shop", 35, ItemType.ALL);
        setItems();

    }

    private void setItems() {
        this.addItems(new ItemStack(Items.getItem(0), 10));
    }

    @Override
    public void open() {
        super.open();
        shopTab.setItems(itemStackByItem);
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
