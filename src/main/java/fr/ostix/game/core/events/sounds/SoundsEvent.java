package fr.ostix.game.core.events.sounds;

import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.core.events.Event;

public class SoundsEvent extends Event {

    private final SoundSource sound;

    public SoundsEvent(int priority, SoundSource sound) {
        super(priority);
        this.sound = sound;
    }

    public SoundSource getSound() {
        return sound;
    }
}
