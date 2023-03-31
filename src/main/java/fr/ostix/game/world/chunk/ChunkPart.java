package fr.ostix.game.world.chunk;


import fr.ostix.game.entity.Entity;
import fr.ostix.game.world.Terrain;

import java.util.ArrayList;
import java.util.List;

public class ChunkPart {
    private final List<Entity> entities = new ArrayList<>();
    private Terrain terrain;
    private final int x;
    private final int z;

    public ChunkPart(Terrain terrain, int x, int z) {
        this.terrain = terrain;
        this.x = x;
        this.z = z;
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
}
