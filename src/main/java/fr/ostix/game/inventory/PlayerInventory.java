package fr.ostix.game.inventory;

import fr.ostix.game.core.resources.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.items.*;
import fr.ostix.game.menu.component.*;
import fr.ostix.game.toolBox.*;
import org.joml.*;

import java.util.*;

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
        this.backGround = new GuiTexture(ResourcePack.getTextureByName().get("inventory").getID(),
                new Vector2f(0), new Vector2f(1920,
                1080));
        this.right_button = new Button(1830, 125, 90, 950,
                ResourcePack.getTextureByName().get("right_button").getID(), (b) -> {
            Logger.log("right button");
        });
        recipeTab = ItemTab.newEmptyTab("RecipeTab", 35);
        super.init();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        MasterGui.addGui(backGround);
        this.addComponent(right_button);
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
        super.addItems(items);
        this.recipeTab.addItems(items);
    }

    public void removeItems(List<ItemStack> items) {

    }


    @Override
    public void update() {
        super.update();
        recipeTab.update();
    }


}
