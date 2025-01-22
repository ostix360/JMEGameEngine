package fr.ostix.game.world.io;

import fr.ostix.game.core.quest.QuestManager;
import fr.ostix.game.toolBox.ToolDirectory;
import fr.ostix.game.world.World;

import java.io.File;
import java.io.IOException;

public abstract class IO {

    protected final World world;
    protected final QuestManager questManager;
    protected final String name;

    public IO(World world, String name) {
        this.world = world;
        this.questManager = QuestManager.INSTANCE;
        this.name = name;
    }

     protected void createFile() {
        File file = new File(ToolDirectory.RES_FOLDER + name + "/world/", "save.txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
