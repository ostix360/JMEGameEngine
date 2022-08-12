package fr.ostix.game.world.chunk;


import fr.ostix.game.core.resources.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.entity.component.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.world.*;
import fr.ostix.game.world.texture.*;

import java.util.*;

public class Chunk {

    private final List<Entity> entities;
    private Terrain terrain;
    private static ResourcePack res;

    public Chunk(int x, int z,List<Entity> entities) {
        this.entities = entities;
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

    public static Chunk load(String content, int x, int z) {
        Terrain t = importTerrain(content);
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
            Model m = res.getModelByName(entityName);
            Transform t = Transform.load(lines[index++]);
            Entity e = new Entity(id, m, t.getPosition(), t.getRotation(), t.getScale().y());
            LoadComponents.loadComponents(res.getComponents().get(component), e);

            entities.add(e);
        }
        return entities;
    }

    public static void setResourcePack(ResourcePack res) {
        Chunk.res = res;
    }

    private static Terrain importTerrain(String content) {
        String[] lines = content.split("\n");
        int index = 0;

        String[] values = lines[index++].split(";");

        float x = Float.parseFloat(values[0]) * Terrain.getSIZE();
        float z = Float.parseFloat(values[1]) * Terrain.getSIZE();
        TerrainTexturePack ttp = TerrainTexturePack.load(lines[index++]);
        values = lines[index].split(";");
        TerrainTexture blendMap = TerrainTexture.load(values[0],true);
        String heightMap = values[1];
        return new Terrain(x / Terrain.getSIZE(), z / Terrain.getSIZE(), ttp, blendMap, heightMap);

    }
}
