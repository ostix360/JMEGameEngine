package fr.ostix.game.core.events.gui;

import fr.ostix.game.core.events.*;
import fr.ostix.game.menu.*;

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
