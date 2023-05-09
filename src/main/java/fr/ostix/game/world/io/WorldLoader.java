package fr.ostix.game.world.io;

import fr.ostix.game.entity.Player;
import fr.ostix.game.inventory.PlayerInventory;
import fr.ostix.game.toolBox.ToolDirectory;
import fr.ostix.game.world.World;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class WorldLoader extends IO{

    private float time;

    public WorldLoader(World world, String name) {
        super(world, name);
    }

    //Load Player position rotation, player inventory, questManager, time from a file
    public void loadWorld() {
        Player player = this.world.getPlayer();
        PlayerInventory PI = this.world.getPlayer().getInventory();
        File file = new File(ToolDirectory.RES_FOLDER, this.name + "/world/save.txt");
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
                } else if (data[0].equals("Time")) {
                    this.time = Float.parseFloat(data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        PI.loadInventory();
        this.questManager.reload(name);
    }



    public float getTime() {
        return time;
    }
}
