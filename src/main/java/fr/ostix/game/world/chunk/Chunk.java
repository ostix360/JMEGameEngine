package fr.ostix.game.world.chunk;


import fr.ostix.game.core.resources.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.entity.component.*;
import fr.ostix.game.entity.entities.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.world.*;
import fr.ostix.game.world.texture.*;

import java.util.*;

public class Chunk {

    private final List<Entity> entities;
    private Terrain terrain;
    private static ResourcePack res;

    private final int x,z;

    public Chunk(int x, int z, List<Entity> entities) {
        this.entities = entities;
        this.x = x;
        this.z = z;
    }


    public Chunk setTerrain(Terrain t) {
        this.terrain = t;
        return this;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public static Chunk load(String content, int x, int z, float[][] heights) {
        Terrain t = importTerrain(content,heights);
        List<Entity> entities = importEntities(content);
        return new Chunk(x,z,entities).setTerrain(t);
    }

    private static List<Entity> importEntities(String content) {
        String[] lines = content.split("\n");
        List<Entity> entities = new ArrayList<>();
        int index = 4;
//        while (!lines[index].equals("ENTITIES")){
//            index++;
//        }
//        index++;
        while (index < lines.length) {
            String[] values = lines[index++].split(";");
            String entityName = values[0];
            int id = Integer.parseInt(values[1]);
            int component = Integer.parseInt(values[2]);
            Model m;
            if (!res.isModelAnimated(entityName))
                m = res.getModelByName(entityName);
            else
                m = res.getAnimatedModelByName().get(entityName);
            Transform t = Transform.load(lines[index++]);
            String entityType = values[3];
            Entity e;
            switch (entityType) {
                case "NPC":
                    e = new NPC(id, m, t.getPosition(), t.getRotation(), t.getScale().y(),entityName);
                    break;
                case "Shop":
                    e = new Shop(m, t.getPosition(), t.getRotation(), t.getScale().y(),id);
                    break;
                default:
                    e = new Entity(id, m, t.getPosition(), t.getRotation(), t.getScale().y());
            }
            LoadComponents.loadComponents(res.getComponents().get(component), e);

            entities.add(e);
        }
        return entities;
    }

    public static void setResourcePack(ResourcePack res) {
        Chunk.res = res;
    }

    private static Terrain importTerrain(String content,float[][] heights) {
        String[] lines = content.split("\n");
        int index = 0;

        String[] values = lines[index++].split(";");

        float x = Float.parseFloat(values[0]) * Terrain.getSIZE();
        float z = Float.parseFloat(values[1]) * Terrain.getSIZE();
        TerrainTexturePack ttp = TerrainTexturePack.load(lines[index++]);
        values = lines[index].split(";");
        TerrainTexture blendMap = TerrainTexture.load(values[0],true);
        return new Terrain(x / Terrain.getSIZE(), z / Terrain.getSIZE(), ttp, blendMap, heights);

    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
