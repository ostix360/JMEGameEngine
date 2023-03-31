package fr.ostix.game.core.events.gui;

import fr.ostix.game.core.events.Event;
import fr.ostix.game.menu.Screen;

public class GuiEvent extends Event {

    private final Screen gui;

    public GuiEvent(Screen gui, int priority) {
        super(priority);
        this.gui = gui;
    }

    public Screen getGui() {
        return gui;
    }
}
