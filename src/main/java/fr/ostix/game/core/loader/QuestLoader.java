package fr.ostix.game.core.loader;

import fr.ostix.game.core.quest.*;
import fr.ostix.game.toolBox.*;

import java.io.*;
import java.util.*;

public class QuestLoader {
    public static void loadAllQuest() {
        for (File f : Objects.requireNonNull(new File(ToolDirectory.RES_FOLDER, "/quests/").listFiles())) {
            QuestManager.INSTANCE.addQuest(QuestCategory.load(f.getAbsolutePath()));
        }
    }


}
