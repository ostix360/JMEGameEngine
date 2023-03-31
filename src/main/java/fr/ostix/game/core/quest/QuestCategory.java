package fr.ostix.game.core.quest;

import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.listener.quest.QuestCategoryListener;
import fr.ostix.game.core.events.quest.QuestCategoryStartEvent;
import fr.ostix.game.core.events.quest.QuestStartedEvent;
import fr.ostix.game.core.loader.json.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestCategory {

    public HashMap<Integer, Quest> quests;
    public int id;
    private final String title;
    private QuestStatus status;
    private int nextQuest;
    private QuestCategoryListener listener;

    private Quest questingQuest;

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
        EventManager.getInstance().unRegister(this.listener);
        final StringBuilder content = new StringBuilder();
        content.append(this.id).append(";").append(this.title).append(";").append(this.quests.size()).append(';').append(status.toString()).append(';').append(nextQuest).append("\n");
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
        Quest q = quests().get(0);
        int i = 0;
        do{
            if (q.getStatus() == QuestStatus.DONE) {
                q = quests().get(q.getId());
                continue;
            }
            if (q.getStatus() == QuestStatus.UNAVAILABLE)
                return;
            this.status = QuestStatus.QUESTING;
            EventManager.getInstance().callEvent(new QuestStartedEvent(q.getId(),2));
            return;
        } while (quests.size() > ++i);


        this.status = QuestStatus.DONE;

    }

    public void setQuestingQuest(Quest questingQuest) {
        this.questingQuest = questingQuest;
    }

    public Quest getQuestingQuest() {
        return questingQuest;
    }

    public void complete(int questID) {
        if (questID != this.id)
            return;
        EventManager.getInstance().unRegister(this.listener);
        this.status = QuestStatus.DONE;
//        this.save();

        if (this.nextQuest != -1){
            QuestManager.INSTANCE.getQuesting(this.nextQuest).status = QuestStatus.AVAILABLE;
            EventManager.getInstance().callEvent(new QuestCategoryStartEvent(this.nextQuest, 2));
        }

    }

    public void endQuesting() {
        this.status = QuestStatus.AVAILABLE;
    }
}
