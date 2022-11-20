package fr.ostix.game.core.events;

import fr.ostix.game.core.events.entity.npc.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.quest.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

public class EventManager {
    private static final EventManager INSTANCE = new EventManager();
    private final Set<Listener> listeners;

    public EventManager() {
        listeners = ConcurrentHashMap.newKeySet();
    }

    public static EventManager getInstance() {
        return INSTANCE;
    }

    public void callEvent(Event e) {
        //
        if (e instanceof NPCTalkEvent){
            System.out.printf("Event %s called\n", e.getClass().getSimpleName());
        }
        //long nano = System.nanoTime();
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
                        System.err.println(ex.getMessage());
                        System.err.println("Error during invoking method " + method.getName() + " for the event " + e.getClass().getName());
                        ex.printStackTrace(System.err);
                    }
                }
            }
        });
        //System.out.printf("listener call took %s s.\n",(System.nanoTime()-nano));
    }

    public void removeAll(List<Listener> listeners) {
        listeners.forEach(this.listeners::remove);
    }

    public void register(Listener listener) {
        this.listeners.add(listener);
    }

    public void unRegister(Listener listener) {
        this.listeners.remove(listener);
    }
}
