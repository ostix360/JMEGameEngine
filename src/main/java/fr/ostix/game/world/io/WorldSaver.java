package fr.ostix.game.world.io;

import fr.ostix.game.core.quest.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.inventory.*;
import fr.ostix.game.world.*;

import java.io.*;

public class WorldSaver {

    private final Player player;
    private final World world;
    private PlayerInventory PI;
    private final QuestManager questManager;
    private int time;

    public WorldSaver(World world) {
        this.world = world;
        this.player = world.getPlayer();
        this.PI = world.getPlayer().getInventory();
        this.questManager = QuestManager.INSTANCE;
    }

    //save Player position rotation, player inventory, questManager, time from a file
    public void saveWorld() {
        File file = new File("save.txt");
        try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
            String line;
            line = "Player;" + player.getPosition().x + ";" + player.getPosition().y + ";" + player.getPosition().z + ";" + player.getRotation().x + ";" + player.getRotation().y + ";" + player.getRotation().z;
            br.write(line);
            br.newLine();
            line = "Time;" + time;
            br.write(line);
            br.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        PI.saveInventory();
        questManager.save();
    }

    public int getTime() {
        return time;
    }
}
