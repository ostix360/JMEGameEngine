package fr.ostix.game.menu;

import fr.ostix.game.menu.component.*;

import java.util.*;

public abstract class Screen {
    private final List<Component> components = new ArrayList<>();
    protected String title;
    private Screen previousScreen;

    public Screen(String title) {
        this.title = title;
    }

    public void init() {
    }

    protected void addComponent(Component c) {
        components.add(c);
        c.init();
    }

    protected void removeComponent(Component c) {
        components.remove(c);
        c.cleanUp();
    }

    public void update() {
        for (Component c : components) {
            c.update();
        }
    }


    public void cleanUp() {
        for (Component c : components) {
            c.cleanUp();
        }
    }

    public List<Component> getComponents() {
        return components;
    }

    public Screen getPreviousScreen() {
        return previousScreen;
    }

    public String getTitle() {
        return title;
    }
}
