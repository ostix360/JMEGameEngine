package fr.ostix.game.items;

public class ItemPotion extends Item {
    public ItemPotion(int id, String name, String description) {
        super(id, name, description, "potion", ItemType.CONSUMABLE);
    }
}
