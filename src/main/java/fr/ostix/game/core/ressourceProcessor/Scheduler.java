package fr.ostix.game.core.ressourceProcessor;

import java.util.*;
import java.util.concurrent.*;

public class Scheduler {
    private static final Timer timer = new Timer("Timer");

    private static final ScheduledExecutorService  executor = Executors.newSingleThreadScheduledExecutor();


    public static void schedule(Runnable runnable, long delay) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, delay);
    }

    public static void scheduleAtFixedRate(Runnable runnable, long delay, long period) {
        executor.scheduleAtFixedRate(runnable, delay, period, TimeUnit.MILLISECONDS);
    }

    public static void stop() {
        timer.cancel();
        executor.shutdown();
    }
}
