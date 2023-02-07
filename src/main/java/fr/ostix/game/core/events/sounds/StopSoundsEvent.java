package fr.ostix.game.core.events.sounds;

import fr.ostix.game.audio.*;

public class StopSoundsEvent extends SoundsEvent{

    public StopSoundsEvent(int priority, SoundSource sound) {
        super(priority, sound);
    }
}
