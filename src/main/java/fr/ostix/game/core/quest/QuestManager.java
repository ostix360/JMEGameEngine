package fr.ostix.game.core.quest;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.quest.*;
import fr.ostix.game.core.events.quest.*;

import java.util.*;

public class QuestManager {
    public static final QuestManager INSTANCE = new QuestManager();
    private final Map<Integer, QuestCategory> quests;

    private final List<Integer> questing;

    //TODO Threaded event Manager ?

    public QuestManager() {
        quests = new HashMap<>();
        questing = new ArrayList<>();
        QuestManagerListener QML = new QuestManagerListener(this);
        EventManager.getInstance().register(QML);
    }

    public void addQuest(QuestCategory quest) {
        Registered.registerQuest(quest);
        quests.put(quest.getId(), quest);
        if (quest.getStatus() == QuestStatus.AVAILABLE){
            EventManager.getInstance().callEvent(new QuestCategoryStartEvent(quest.getId(), 1));
        }
    }

    public void reload(){

    }

    public void addToQuesting(int q) {
        this.questing.add(q);
    }

    public void removeFromQuesting(int q) {
        this.questing.remove((Object)q);
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
