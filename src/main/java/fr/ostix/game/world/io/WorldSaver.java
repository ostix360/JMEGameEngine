package fr.ostix.game.world.io;

import fr.ostix.game.core.quest.QuestManager;
import fr.ostix.game.entity.Player;
import fr.ostix.game.inventory.PlayerInventory;
import fr.ostix.game.toolBox.ToolDirectory;
import fr.ostix.game.world.World;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WorldSaver {

    private final World world;
    private final QuestManager questManager;

    public WorldSaver(World world) {
        this.world = world;
        this.questManager = QuestManager.INSTANCE;
    }

    //save Player position rotation, player inventory, questManager, time from a file
    public void saveWorld() {
        Player player = world.getPlayer();
        PlayerInventory PI = world.getPlayer().getInventory();
        File file = new File(ToolDirectory.RES_FOLDER,"world/world/save.txt");
        try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
            String line;
            line = "Player;" + player.getPosition().x + ";" + player.getPosition().y + ";" + player.getPosition().z + ";" + player.getRotation().x + ";" + player.getRotation().y + ";" + player.getRotation().z;
            br.write(line);
            br.newLine();
            line = "Time;" + world.getTime();
            br.write(line);
            br.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        PI.saveInventory();
        questManager.save();
    }
}
