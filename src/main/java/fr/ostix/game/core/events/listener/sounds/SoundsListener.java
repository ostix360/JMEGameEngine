package fr.ostix.game.core.events.listener.sounds;

import fr.ostix.game.audio.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.sounds.*;

public class SoundsListener implements Listener {
    private final SoundListener listener;

    private SoundSource ambientSounds;

    public SoundsListener(SoundListener listener) {
        this.listener = listener;
    }


    @EventHandler
    public void onSoundsStarted(StartSoundsEvent e){
        if (ambientSounds != null) {
            ambientSounds.stop();
        }
//        e.getSound().play();
        if (e.getSound().isAmbient()){
            ambientSounds = e.getSound();
        }
    }

    @EventHandler
    public void onSoundsStopped(StopSoundsEvent e){
        e.getSound().stop();
    }
}
