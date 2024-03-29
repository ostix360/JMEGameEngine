package fr.ostix.game.core.logics.ressourceProcessor;


import fr.ostix.game.audio.AudioManager;
import fr.ostix.game.audio.SoundSource;

public class SoundRequest extends GLRequest{
    private final String path;
    private final boolean isAmbient;
    private SoundSource sound;

    public SoundRequest(String path, boolean isAmbient) {
        this.path = path;
        this.isAmbient = isAmbient;
    }

    public SoundSource getSound() {
        return sound;
    }

    @Override
    protected void execute() {
        this.sound = AudioManager.loadSound(path,isAmbient);
        super.execute();
    }

    @Override
    public String toString() {
        return "SoundRequest{" +
                "isExecuted=" + isExecuted +
                ", path='" + path + '\'' +
                ", isAmbient=" + isAmbient +
                ", sound=" + sound +
                '}';
    }
}
