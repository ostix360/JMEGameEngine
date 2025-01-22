package fr.ostix.game.menu.state;

public enum States {

    MAIN_MENU(0, "Main Menu"),
    QUEST_MENU(1, "Quest Menu"),
    INVENTORY_MENU(2, "Inventory Menu"),
    OPTIONS_MENU(3, "Options Menu"),
    RESUME_MENU(4, "Resume Menu"),
    WORLD(5, "World");

    private final int id;

    private final String name;

    States(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
