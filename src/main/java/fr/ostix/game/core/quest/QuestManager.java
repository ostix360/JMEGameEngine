package fr.ostix.game.core.quest;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.quest.*;
import fr.ostix.game.core.events.quest.*;
import fr.ostix.game.core.loader.*;
import fr.ostix.game.toolBox.Logger;
import fr.ostix.game.toolBox.ToolDirectory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
        if (quest.getStatus() == QuestStatus.AVAILABLE) {
            EventManager.getInstance().callEvent(new QuestCategoryStartEvent(quest.getId(), 1));
        }
    }

    public void reload() {
        quests.clear();
        questing.clear();
        QuestLoader.loadAllQuest();
    }

    public void addToQuesting(int q) {
        this.questing.add(q);
    }

    public void removeFromQuesting(int q) {
        this.questing.remove((Object) q);
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

    public void save() {
        for (QuestCategory quest : quests.values()) {
            String q = quest.save();
            File f = openFile(quest);
            try (FileOutputStream fos = new FileOutputStream(f);
                 FileChannel fc = fos.getChannel()) {
                byte[] data = q.getBytes();
                ByteBuffer bytes = ByteBuffer.allocate(data.length);
                bytes.put(data);
                bytes.flip();
                fc.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private File openFile(QuestCategory quest) {
        String name = quest.getName();
        File questFile = new File(ToolDirectory.RES_FOLDER + "\\quests\\", name + "." + quest.getId() + ".quest");
        if (!questFile.exists()) {
            Logger.err("The quest file " + name + " was not found!", new IOException(questFile.getAbsolutePath() + " not found"));
        }
        return questFile;
    }
}
