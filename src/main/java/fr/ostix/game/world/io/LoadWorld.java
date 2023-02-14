package fr.ostix.game.world.io;

import fr.ostix.game.core.quest.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.inventory.*;
import fr.ostix.game.world.*;
import org.joml.*;

import java.io.*;

public class LoadWorld {

    private Player player;
    private final World world;
    private PlayerInventory PI;
    private QuestManager questManager;
    private int time;

    public LoadWorld(World world) {
        this.world = world;
    }

    //Load Player position rotation, player inventory, questManager, time from a file
    public void loadWorld() {
        File file = new File("save.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
           String line;
           while ((line = br.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data[0].equals("Player")) {
                        float x = Float.parseFloat(data[1]);
                        float y = Float.parseFloat(data[2]);
                        float z = Float.parseFloat(data[3]);
                        float rx = Float.parseFloat(data[4]);
                        float ry = Float.parseFloat(data[5]);
                        float rz = Float.parseFloat(data[6]);
                        player.setPosition(new Vector3f(x, y, z));
                        player.setRotation(new Vector3f(rx, ry, rz));
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        PI = new PlayerInventory("Player Inventory");
        PI.loadInventory();
    }

}
