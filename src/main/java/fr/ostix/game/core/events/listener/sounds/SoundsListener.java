package fr.ostix.game.core.events.listener.sounds;

import fr.ostix.game.audio.SoundListener;
import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.listener.Listener;
import fr.ostix.game.core.events.sounds.StartSoundsEvent;
import fr.ostix.game.core.events.sounds.StopSoundsEvent;

public class SoundsListener implements Listener {

    private SoundSource ambientSounds;

    public SoundsListener(SoundListener listener) {
    }


    @EventHandler
    public void onSoundsStarted(StartSoundsEvent e){
        boolean isAmbient = e.getSound().isAmbient();
        if (ambientSounds != null && isAmbient) {
            ambientSounds.stop();
        }
//        e.getSound().play();
        if (isAmbient){
            ambientSounds = e.getSound();
        }
    }

    @EventHandler
    public void onSoundsStopped(StopSoundsEvent e){
        e.getSound().stop();
    }
}
