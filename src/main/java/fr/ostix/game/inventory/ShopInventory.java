package fr.ostix.game.inventory;

import fr.ostix.game.core.Game;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.items.*;
import fr.ostix.game.toolBox.Color;
import org.joml.Vector2f;

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
