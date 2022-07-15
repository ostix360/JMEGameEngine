package fr.ostix.game.core.quest;

import java.util.*;

public class QuestManager {
    public static final QuestManager INSTANCE = new QuestManager();
    private final Map<Integer, QuestCategory> quests;
    private final List<Integer> questing;

    //TODO Threaded event handler

    public QuestManager() {
        quests = new HashMap<>();
        questing = new ArrayList<>();
    }

    public void addQuest(QuestCategory quest) {
        quests.put(quest.getId(), quest);
    }

    public void addToQuesting(int q) {
        this.questing.add(q);
    }

    public void removeFromQuesting(int q) {
        this.questing.remove(q);
    }


    public Map<Integer, QuestCategory> getQuests() {
        return quests;
    }

    public QuestCategory getQuest(int id) {
        return quests.get(id);

    }


    public List<Integer> getQuest() {
        return questing;
    }
}
