package fr.ostix.game.inventory;


import fr.ostix.game.items.*;

import java.util.*;

public class ItemTab {
    private final String name;
    private final Slot[] slots;

    private ItemTab(String name, Slot[] slots) {
        this.name = name;
        this.slots = slots;
    }


    public static ItemTab newEmptyTab(String name, int slotCount) {

        return new ItemTab(name, generate(slotCount));
    }

    private static Slot[] generate(int slotCount) {
        Slot[] slots = new Slot[slotCount];
        int index = 0;
        int size = 140;
        for (int x = 0; x < slotCount / 5; x++) {
            for (int y = 0; y < slotCount / 7; y++) {
                slots[index++] = new Slot(495 + x * size + x * 48, 240 + y * size + y * 17, size);
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

    public void addItems(List<ItemStack> items) {
        for (ItemStack stack : items) {
            Slot s;
            if ((s = slotsContain(stack.getItem())) != null)  {
                s.getStack().addItems(stack.getItem(),stack.getCount());
            }else{
                for (Slot slot : slots) {
                    if (slot.isEmpty()){
                        slot.getStack().addItems(stack.getItem(),stack.getCount());
                        return;
                    }
                }
            }
        }
        // TODO return boolean possibility

    }

    public void removeItems(List<ItemStack> items) {
        // TODO return boolean possibility
    }

    private Slot slotsContain(Item i){
        for(Slot slot : slots){
            if (slot.isEmpty())
                continue;
            if (!slot.getStack().getItem().equals(i))
                continue;
            return slot;
        }
        return null;
    }
}
