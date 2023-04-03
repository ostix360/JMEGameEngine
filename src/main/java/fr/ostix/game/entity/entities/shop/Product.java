package fr.ostix.game.entity.entities.shop;

import fr.ostix.game.items.ItemStack;

public class Product {

    private final ItemStack productStack;
    private final int price;

    public Product(ItemStack productStack, int price) {
        this.productStack = productStack;
        this.price = price;
    }

    public ItemStack getProductStack() {
        return productStack;
    }

    public int getPrice() {
        return price;
    }
}
