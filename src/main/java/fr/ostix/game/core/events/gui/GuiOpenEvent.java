package fr.ostix.game.core.events.gui;

import fr.ostix.game.menu.Screen;

public class GuiOpenEvent extends GuiEvent {
    public GuiOpenEvent(Screen gui, int priority) {
        super(gui, priority);
    }
}
