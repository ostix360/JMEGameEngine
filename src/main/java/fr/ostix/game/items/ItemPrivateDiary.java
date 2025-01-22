package fr.ostix.game.items;

public class ItemPrivateDiary extends Item {
    public ItemPrivateDiary(int id, String name, String description) {
        super(id, name, description, 10,"privateDiary", ItemType.ALL);
    }

    @Override
    public void onItemUse() {
        System.out.println("You have used the item: " + this.getName());
    }
}
