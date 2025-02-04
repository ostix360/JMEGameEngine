package fr.ostix.game.inventory;


import fr.ostix.game.core.logics.Callback;
import fr.ostix.game.entity.entities.shop.Product;
import fr.ostix.game.items.Item;
import fr.ostix.game.items.ItemStack;
import fr.ostix.game.items.ItemType;
import fr.ostix.game.toolBox.Logger;

import java.util.HashMap;
import java.util.List;

public class ItemTab {
    private final String name;
    private final Slot[] slots;

    private final ItemType type;

    private ItemTab(String name, Slot[] slots, ItemType type) {
        this.name = name;
        this.slots = slots;
        this.type = type;
    }

    public static ItemTab newEmptyTab(String name, int slotCount, ItemType type, Callback<Product> callback) {
        return new ItemTab(name, generate(slotCount, callback), type);
    }

    public static ItemTab newEmptyTab(String name, int slotCount, ItemType type) {
        return newEmptyTab(name, slotCount, type, null);
    }

    private static Slot[] generate(int slotCount, Callback<Product> callback) {
        Slot[] slots = new Slot[slotCount];
        int index = 0;
        int size = 140;
        for (int x = 0; x < slotCount / 5; x++) {
            for (int y = 0; y < slotCount / 7; y++) {
                slots[index++] = new Slot(495 + x * size + x * 48, 240 + y * size + y * 17, size, callback);
            }
        }
        return slots;
    }

    public void update() {
        for (Slot slot : slots) {
            slot.update();
        }
    }

    public void startRendering() {
        for (Slot slot : slots) {
            slot.startRendering();
        }
    }

    public void render() {
        for (Slot slot : slots) {
            slot.render();
        }
    }

    public void stopRendering() {
        for (Slot slot : slots) {
            slot.stopRendering();
        }
    }

    public Slot[] getSlots() {
        return slots;
    }


    public String getName() {
        return name;
    }

    private void clearSlots() {
        for (Slot slot : slots) {
            slot.setStack(new ItemStack(null, 0));
        }
    }

    public boolean setItems(HashMap<Item, ItemStack> items) {
        clearSlots();
        for (Item item : items.keySet()) {
            if (item.getType() != type && type != ItemType.ALL && item.getType() != ItemType.ALL) continue;
            ItemStack stack = items.get(item);
            Slot s;
            if ((s = slotsContain(stack.getItem())) != null) {
                s.getStack().setStack(stack);
            } else {
                boolean found = false;
                for (Slot slot : slots) {
                    if (slot.isEmpty()) {
                        slot.getStack().addItems(stack.getItem(), stack.getCount());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Logger.log("Inventory full");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean removeItems(List<ItemStack> items) {
        for (ItemStack stack : items) {
            if (!removeItem(stack)) {
                Logger.log("Couldn't remove item");
                return false;
            }
        }
        return true;
    }

    public boolean removeItem(ItemStack stack) {
        Slot s;
        if ((s = slotsContain(stack.getItem())) != null) {
            return s.getStack().removeItems(stack.getItem(), stack.getCount());
        } else {
            return false;
        }
    }

    private Slot slotsContain(Item i) {
        for (Slot slot : slots) {
            if (slot.isEmpty())
                continue;
            if (!slot.getStack().getItem().equals(i))
                continue;
            return slot;
        }
        return null;
    }
}
