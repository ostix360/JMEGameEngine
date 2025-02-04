package fr.ostix.game.core.events.states;

import fr.ostix.game.core.events.Event;
import fr.ostix.game.menu.Screen;

public class StateChangeEvent extends Event {

    private final String stat;


    private final Screen screen;

    public StateChangeEvent(String stat, Screen screen, int priority) {
        super(priority);
        this.stat = stat;
        this.screen = screen;
    }

    public String getStat() {
        return stat;
    }



    public Screen getScreen() {
        return screen;
    }
}
