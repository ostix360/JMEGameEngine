package fr.ostix.game.core.quest;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.quest.*;
import fr.ostix.game.core.events.quest.*;
import fr.ostix.game.core.loader.json.*;

import java.util.*;

public class QuestCategory {

    public HashMap<Integer, Quest> quests;
    public int id;
    private final String title;
    private QuestStatus status;
    private int nextQuest;
    private QuestCategoryListener listener;

    public QuestCategory() {
        this.id = -1;
        this.title = "";
        this.quests = new HashMap<>();
    }

    public QuestCategory(HashMap<Integer, Quest> quests, int id, String title, QuestStatus status, int nextQuest) {
        this.quests = quests;
        this.id = id;
        this.title = title;
        this.status = status;
        this.nextQuest = nextQuest;
    }

    public static QuestCategory load(String questFile) {
        String content = JsonUtils.loadJson(questFile);
        HashMap<Integer, Quest> quests = new HashMap<>();
        String[] lines = content.split("\n");
        String[] values = lines[0].split(";");
        int id = Integer.parseInt(values[0]);
        String title = values[1];
        int nextQuest = Integer.parseInt(values[4]);
        QuestStatus status = QuestStatus.valueOf(values[3]);
        for (int i = 1; i < lines.length; i++) {
            Quest q;
            switch (lines[i]) {
                case "QuestItem":
                    q = QuestItem.load(lines[++i]);
                    break;
                case "QuestLocation":
                    q = QuestLocation.load(lines[++i]);
                    break;
                case "QuestDialog":
                    q = QuestDialog.load(lines[++i]);
                    break;
                default:
                    new Exception("Quest type not found");
                    i++;
                    continue;
            }
            quests.put(q.getId(), q);

        }
        return new QuestCategory(quests, id, title, status, nextQuest);
    }

    public String save() {
        final StringBuilder content = new StringBuilder();
        content.append(this.id).append(";").append(this.title).append(";").append(this.quests.size()).append(';').append(status.toString()).append("\n");
        for (Quest q : this.quests.values()) {
            if (q instanceof QuestItem) {
                content.append("QuestItem").append("\n");
            } else if (q instanceof QuestLocation) {
                content.append("QuestLocation").append("\n");
            } else {
                content.append("QuestDialog").append("\n");
            }
            content.append(q.save()).append("\n");
        }
        return content.toString();
    }

    public List<Quest> quests() {
        return new ArrayList<>(this.quests.values());
    }

    public String getName() {
        return this.title;
    }

    public int getId() {
        return id;
    }

    public QuestStatus getStatus() {
        return status;
    }


    public void start() {
        EventManager.getInstance().register(listener = new QuestCategoryListener(this));
        EventManager.getInstance().callEvent(new QuestStartedEvent(quests().get(0).getId(),2));
    }

    public void complete() {
        EventManager.getInstance().unRegister(listener);
        if (this.nextQuest != -1)
            EventManager.getInstance().callEvent(new QuestCategoryStartEvent(this.nextQuest, 2));
    }
}
