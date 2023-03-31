package fr.ostix.game.entity.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AITasks {

    private final List<AITask> tasks = new ArrayList<>();
    private final List<AITask> executingTasks = new ArrayList<>();
    private int tickCount;

    public void addTask(int priority, AIAction task) {
        this.tasks.add(new AITask(priority, task));
    }

    public void removeTask(AIAction task) {
        Iterator<AITask> iterator = this.tasks.iterator();

        while (iterator.hasNext()) {
            AITask currentTask = iterator.next();
            AIAction entityaibase = currentTask.task;

            if (entityaibase == task) {
                if (this.executingTasks.contains(currentTask)) {
                    entityaibase.reset();
                    this.executingTasks.remove(currentTask);
                }
                iterator.remove();
            }
        }
    }


    static class AITask {
        public int priority;
        public AIAction task;

        public AITask(int priority, AIAction task) {
            this.priority = priority;
            this.task = task;
        }
    }
}
