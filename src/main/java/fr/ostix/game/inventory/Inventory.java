package fr.ostix.game.inventory;

import com.google.gson.annotations.Expose;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.inventoryEvent.InventoryCloseEvent;
import fr.ostix.game.core.events.inventoryEvent.InventoryOpenEvent;
import fr.ostix.game.core.loader.json.JsonUtils;
import fr.ostix.game.core.loader.json.typeAdapter.InventoryTypeAdapter;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.items.Item;
import fr.ostix.game.items.ItemStack;
import fr.ostix.game.menu.Screen;
import fr.ostix.game.toolBox.Logger;
import fr.ostix.game.toolBox.ToolDirectory;
import org.joml.Vector2f;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class Inventory extends Screen {
    @Expose
    private Collection<ItemStack> items;

    protected final HashMap<Item, ItemStack> itemStackByItem;
    private final GuiTexture backGround;
    private boolean isOpen = false;

    public Inventory() {
        super("Inventory");
        this.itemStackByItem = new HashMap<>();
        this.backGround = new GuiTexture(ResourcePack.getTextureByName("inventory").getID(),
                new Vector2f(0), new Vector2f(1920,
                1080));
        init();
    }

    public Inventory(String title) {
        super(title);
        this.itemStackByItem = new HashMap<>();
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
        super.update();
        if (isOpen) {
            this.render();
        }
    }

    public void render() {
    }

    public void addItems(ItemStack... items) {
        for (ItemStack stack : items) {
            if (itemStackByItem.containsKey(stack.getItem())) {
                itemStackByItem.get(stack.getItem()).addItem(stack.getCount());
            } else {
                itemStackByItem.put(stack.getItem(), stack);
            }
        }
    }

    public boolean removeItems(ItemStack... items) {
        boolean success = true;
        for (ItemStack stack : items) {
            if (itemStackByItem.containsKey(stack.getItem())) {
                if (!itemStackByItem.get(stack.getItem()).removeItems(stack.getCount())) {
                    Logger.err("Not enough item in inventory to remove " + stack.getCount() + " " + stack.getItem().getName() + "(s)");
                    success = false;
                } else {
                    if (itemStackByItem.get(stack.getItem()).getCount() == 0) {
                        itemStackByItem.remove(stack.getItem());
                    } else if (itemStackByItem.get(stack.getItem()).getCount() < 0) {
                        Logger.err("Item count is negative");
                        success = false;
                    }
                }
            } else {
                Logger.err("Item " + stack.getItem().getName() + " not found in inventory");
                success = false;
            }
        }
        return success;
    }


    public void close() {
        MasterGui.removeGui(backGround);
        EventManager.getInstance().callEvent(new InventoryCloseEvent(1, this));
        isOpen = false;
    }

    public boolean has(ItemStack item) {
        return getItems().stream().anyMatch(itemStack ->
                itemStack.getItem().getId() == item.getItem().getId()
                        && itemStack.getCount() >= item.getCount());
    }

    public void loadInventory() {
        // load inventory from file
        File file = new File(ToolDirectory.RES_FOLDER, "world/inventory/" + title + ".inv");
        if (!file.exists()) {
            System.err.println("Inventory " + title + " not found");
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        String content = JsonUtils.loadJson(file.getAbsolutePath());
        if (content == null || content.isEmpty()) {
            System.err.println("Inventory " + title + " not found");
            return;
        }
        Inventory inv = JsonUtils.gsonInstance(Inventory.class, new InventoryTypeAdapter(), true).fromJson(content, Inventory.class);

        this.addItems(inv.getItems().toArray(new ItemStack[0]));
    }

    public void saveInventory() {
        // save inventory to file
        this.items = getItems();
        File file = new File(ToolDirectory.RES_FOLDER, "world/inventory/" + title + ".inv");
        String content = JsonUtils.gsonInstance(Inventory.class, new InventoryTypeAdapter(), true).toJson(this);
        JsonUtils.saveJson(file.getAbsolutePath(), content);
    }

    public Collection<ItemStack> getItems() {
        return itemStackByItem.values();
    }
}
