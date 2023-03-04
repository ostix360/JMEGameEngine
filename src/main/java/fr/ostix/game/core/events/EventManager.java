package fr.ostix.game.core.events;

import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.sounds.*;
import fr.ostix.game.core.events.states.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

public class EventManager {

    // TODO Event sur un thread séparé LinkedBlockingQueue

    private static final EventManager INSTANCE = new EventManager();

    private final List<Listener> listeners;

    public EventManager() {
        this.listeners = new CopyOnWriteArrayList<>();

    }


    public static EventManager getInstance() {
        return INSTANCE;
    }

    private void executeEvent(Event e) {

        //
        if (e instanceof StartSoundsEvent) {
            System.out.printf("Event %s called\n", e.getClass().getSimpleName());
        }
        //long nano = System.nanoTime();
        sendEvent(e);
        //System.out.printf("listener call took %s s.\n",(System.nanoTime()-nano));
    }

    public void callEvent(Event e) {
        if (e instanceof StateChangeEvent) {
            System.out.printf("Event %s called\n", e.getClass().getSimpleName());
        }
        //long nano = System.nanoTime();
        sendEvent(e);
//        executorService.submit(() -> {
//            executeEvent(e);
//        });
    }

    private void sendEvent(Event e) {
        this.listeners.forEach(listener -> {
            for (Method method : listener.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(EventHandler.class)) {
                    try {
                        if (method.getParameterCount() == 1) {
                            if (e.getClass().isAssignableFrom(method.getParameterTypes()[0])) {
                                method.invoke(listener, e);
                            }
                        } else {
                            System.err.println("The method " + method.getName() + "has to contain 1 parameter. ");
                        }
                    } catch (Exception ex) {
                        System.err.println("Error during invoking method " + method.getName() + " for the event " + e.getClass().getName());
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public void removeAll(List<Listener> listeners) {
        listeners.forEach(this.listeners::remove);
    }

    public void register(Listener listener) {
        this.listeners.add(listener);
    }

    public void unRegister(Listener listener) {
        if (listeners == null)
            return;
        if (listeners.contains(listener)) {
            this.listeners.remove(listener);
        }
    }
}
