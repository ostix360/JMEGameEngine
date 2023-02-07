package fr.ostix.game.core.loader;

import fr.ostix.game.toolBox.*;

import java.util.*;

public class ProgressManager {

    private static final List<ProgressBar> progressBars = new ArrayList<>();

    public static ProgressBar addProgressBar(String title, int allStep) {
        ProgressBar bar = new ProgressBar(title, allStep);
        progressBars.add(bar);
        return bar;
    }

    public static void remove(ProgressBar bar) {
        if (bar.getAllSteps() != bar.getCurrentStep())
            throw new IllegalStateException("can't remove unfinished ProgressBar " + bar.getTitle());
        progressBars.remove(bar);
        long now = System.nanoTime();
        String time = String.format("%.3f", ((float) (now - bar.start) / 1_000_000 / 1_000));
        Logger.log("The step " + bar.getTitle() + " took : " + time + " s");
    }

    public static Iterator<ProgressBar> getProgressBars() {
        return progressBars.iterator();
    }


    public static class ProgressBar {
        private final String title;
        private final int allSteps;
        private final long start = System.nanoTime();
        private int currentStep = 0;
        private String message;

        private ProgressBar(String title, int allSteps) {
            this.title = title;
            this.allSteps = allSteps;
        }

        public void update(String message) {
            if (currentStep >= allSteps) throw new IllegalStateException("there is too much step for : " + title);
            currentStep++;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public String getTitle() {
            return title;
        }

        public int getAllSteps() {
            return allSteps;
        }

        public int getCurrentStep() {
            return currentStep;
        }
    }
}
