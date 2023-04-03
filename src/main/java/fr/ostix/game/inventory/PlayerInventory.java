package fr.ostix.game.inventory;

import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.items.ItemStack;
import fr.ostix.game.items.ItemType;
import fr.ostix.game.menu.component.Button;
import fr.ostix.game.menu.component.VerticalButton;
import fr.ostix.game.toolBox.Logger;
import org.joml.Vector2f;

import java.util.List;

public class PlayerInventory extends Inventory {


    private GuiTexture backGround;
    private boolean isOpen = false;
    private ItemTab recipeTab;
    private Button right_button;

    public PlayerInventory(String title) {
        super(title);
        this.title = title;
    }

    public void init() {
        this.backGround = new GuiTexture(ResourcePack.getTextureByName("inventory").getID(),
                new Vector2f(0), new Vector2f(1920,
                1080));
        this.right_button = new VerticalButton(1845, 380, 65, 500,
                ResourcePack.getTextureByName("right_button").getID(), (b) -> {
            Logger.log("right button");
        });
        this.right_button.setScale(new Vector2f(1f, 1.62f));
        recipeTab = ItemTab.newEmptyTab("RecipeTab", 35, ItemType.CONSUMABLE);
        super.init();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        MasterGui.addGui(backGround);
        this.addComponent(right_button);
        recipeTab.setItems(itemStackByItem);
        recipeTab.startRendering();
        isOpen = true;
    }


    public void render() {
        recipeTab.render();
    }


    public void close() {
        MasterGui.removeGui(backGround);
        this.removeComponent(right_button);
        this.recipeTab.stopRendering();
        isOpen = false;
    }

    public void addItems(List<ItemStack> items) {
        super.addItems(items.toArray(new ItemStack[0]));
    }

    public void removeItems(List<ItemStack> items) {
        super.removeItems(items.toArray(new ItemStack[0]));
    }

    @Override
    public void update() {
        super.update();
        recipeTab.update();
    }


}
