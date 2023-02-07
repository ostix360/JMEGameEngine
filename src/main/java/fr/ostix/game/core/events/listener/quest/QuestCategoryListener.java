package fr.ostix.game.core.events.listener.quest;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.quest.*;
import fr.ostix.game.core.quest.*;

public class QuestCategoryListener implements Listener {
    private final QuestCategory questCategory;

    public QuestCategoryListener(QuestCategory questCategory) {
        this.questCategory = questCategory;
    }

    @EventHandler
    public void onQuestStart(QuestStartedEvent event) {
        try {
            questCategory.quests().get(event.getQuestID() - 1).execute();
            Quest q = questCategory.quests().get(event.getQuestID() - 1);
            q.setStatus(QuestStatus.QUESTING);
            questCategory.setQuestingQuest(q);
        } catch (Exception e) {
            System.err.println("Exception for " + questCategory.getName()); // ???
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onQuestComplete(QuestFinishedEvent event) {
        try {
            questCategory.quests().get(event.getQuestID() - 1).done(event.getP());
        } catch (Exception e) {
            System.err.println("Exception for " + questCategory.getName()); // ???
            e.printStackTrace();
        }
        if (questCategory.quests().size() == event.getQuestID()) {
            EventManager.getInstance().callEvent(new QuestCategoryCompleteEvent(questCategory.getId(), 2));
            return;
        }
        try {
            questCategory.quests().get(event.getQuestID()).setStatus(QuestStatus.AVAILABLE);
        } catch (Exception e) {
            System.err.println("Exception for " + questCategory.getName()); // ???
            e.printStackTrace();
        }
        EventManager.getInstance().callEvent(new QuestStartedEvent(event.getQuestID() + 1, 2));
    }
}
