package fr.ostix.game.core.loader;

import fr.ostix.game.core.quest.QuestCategory;
import fr.ostix.game.core.quest.QuestManager;
import fr.ostix.game.toolBox.ToolDirectory;

import java.io.File;
import java.util.Objects;

public class QuestLoader {
    public static void loadAllQuest() {
        for (File f : Objects.requireNonNull(new File(ToolDirectory.RES_FOLDER, "/quests/").listFiles())) {
            QuestManager.INSTANCE.addQuest(QuestCategory.load(f.getAbsolutePath()));
        }
    }


}
