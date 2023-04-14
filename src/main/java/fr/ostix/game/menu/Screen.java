package fr.ostix.game.menu;

import com.google.gson.annotations.Expose;
import fr.ostix.game.menu.component.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Screen {
    private final List<Component> components = new CopyOnWriteArrayList<>();

    @Expose
    protected String title;
    private Screen previousScreen;

    private boolean init;

    public Screen(String title) {
        this.title = title;
    }

    public void init() {
        init = true;
    }

    public void open() {

    }

    public void close() {

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

    public void render() {
        for (Component c : components) {
            c.render();
        }
    }


    public void cleanUp() {
        for (Component c : components) {
            removeComponent(c);
        }
    }

    public boolean isInit() {
        return init;
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
