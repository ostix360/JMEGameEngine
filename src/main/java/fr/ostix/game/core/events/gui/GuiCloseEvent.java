package fr.ostix.game.core.events.gui;

import fr.ostix.game.menu.Screen;

public class GuiCloseEvent extends GuiEvent {

    public GuiCloseEvent(Screen gui, int priority) {
        super(gui, priority);
    }
}
