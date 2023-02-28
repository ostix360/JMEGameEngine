package fr.ostix.game.items;

import com.google.gson.annotations.*;
import fr.ostix.game.toolBox.*;

public class ItemStack {
    @Expose
    private Item item;
    @Expose
    private int count;

    public ItemStack(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public void addItems(Item item, int number){
        if (this.item == null){
            this.item = item;
            this.count = number;
        }else if(this.item == item){
            this.count += number;
        }else{
            Logger.warn("you couldn't had item to an itemStack if there is not the same skip...");
        }
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addItem(int count) {
         if (item != null) {
            this.count += count;
        }
    }

    public void increaseItemCount() {
        if (this.item != null) {
            this.count++;
        }
    }

    public void decreaseItemCount() {
        count--;
        if (count < 1) {
            count = 0;
            this.item = null;
        }
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void removeItems(Item item, int number) {
        if (this.item == item){
            this.count -= number;
        }else{
            Logger.warn("you couldn't remove item to an itemStack if there is not the same...");
        }
        if (this.count <= 0){
            this.count = 0;
        }
    }

    public boolean removeItems(int number) {
        this.count -= number;
        if (this.count == 0){
            this.item = null;
        }else {
            Logger.warn("Error Youu want to remove more item than you have");
            return false;
        }
        return true;
    }

    public void setStack(ItemStack stack) {
        this.item = stack.getItem();
        this.count = stack.getCount();
    }
}
