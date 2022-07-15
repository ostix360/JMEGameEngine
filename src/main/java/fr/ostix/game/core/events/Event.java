package fr.ostix.game.core.events;

public class Event {

    protected static final int LOW = 0;
    protected static final int HIGH = 1;


    private final int priority;

    public Event(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
