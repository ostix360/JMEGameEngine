package fr.ostix.game.core.ressourceProcessor;

public class GLRunnableRequest extends GLRequest{

    private final Runnable runnable;

    public GLRunnableRequest(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    protected void execute() {
        runnable.run();
    }
}
