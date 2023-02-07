package fr.ostix.game.core.events.sounds;

import fr.ostix.game.audio.*;

public class StartSoundsEvent extends SoundsEvent {

    public StartSoundsEvent(int priority, SoundSource sound) {
        super(priority, sound);
    }
}
