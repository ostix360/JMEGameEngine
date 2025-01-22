package fr.ostix.game.core.events.listener;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.states.StateChangeEvent;
import fr.ostix.game.core.events.states.StateOverWorldEvent;
import fr.ostix.game.menu.state.StateManager;

public class StateListener implements Listener{

    private final StateManager statManager;

    public StateListener(StateManager statManager) {
        this.statManager = statManager;
    }

    @EventHandler
    public void onStatChange(StateChangeEvent event) {
        statManager.notifyStatChange(event.getStat(), event.getScreen());
    }

    @EventHandler
    public void onStateOverWorldSet(StateOverWorldEvent event){
        statManager.notifyStateOverWorldSet(event.getScreen());
    }
}
