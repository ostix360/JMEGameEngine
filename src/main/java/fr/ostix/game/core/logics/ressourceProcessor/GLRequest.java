package fr.ostix.game.core.logics.ressourceProcessor;

public abstract class GLRequest {
    protected boolean isExecuted = false;
    protected synchronized void execute(){
        isExecuted = true;
    }

    public synchronized boolean isExecuted() {
        return isExecuted;
    }
}
