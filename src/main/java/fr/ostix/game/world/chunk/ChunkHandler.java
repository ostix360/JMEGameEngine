package fr.ostix.game.world.chunk;

import fr.ostix.game.entity.*;
import fr.ostix.game.entity.camera.*;
import fr.ostix.game.world.*;
import org.joml.Math;
import org.joml.*;

import java.util.*;

public class ChunkHandler extends Thread{

    private final Map<Vector2f, Chunk> chunkList = Collections.synchronizedMap(new HashMap<>());
    private final Map<Vector2f, ChunksFile> chunksFileList = new HashMap<>();
    private final Camera cam;
    private final List<Entity> entitiesChunk = new ArrayList<>();
    private boolean isInit;
    private boolean isExit;

    public ChunkHandler(Camera cam) {
        this.cam = cam;
    }

    public void run() {
        while (!isExit) {
            try {
                int playerChunkX = (int) Math.floor(cam.getPosition().x() / Terrain.getSIZE() / 3); // Get the current chunk that the player is in on the X - Axis
                int playerChunkZ = (int) Math.floor(cam.getPosition().z() / Terrain.getSIZE() / 3); // Get the current chunk that the player is in on the Z - Axis

                // System.out.println(playerChunkX + ", " + playerChunkZ);

                int viewDistanceChunkFile = cam.viewDistance;
                for (int x1 = 0; x1 <= viewDistanceChunkFile; x1 += 3) { // X - Axis iteration
                    for (int z1 = 0; z1 <= viewDistanceChunkFile; z1 += 3) { // Z - Axis iteration
                        int x = x1 / 3;
                        int z = z1 / 3;
                        if (!(chunksFileList.containsKey(new Vector2f(playerChunkX + x, playerChunkZ)))) {
                            chunksFileList.put(new Vector2f(playerChunkX + x, playerChunkZ), new ChunksFile(playerChunkX + x, playerChunkZ));
                            chunksFileList.get(new Vector2f(playerChunkX + x, playerChunkZ)).load();
                        }

                        if (!(chunksFileList.containsKey(new Vector2f(playerChunkX, playerChunkZ + z)))) {
                            chunksFileList.put(new Vector2f(playerChunkX, playerChunkZ + z), new ChunksFile(playerChunkX, playerChunkZ + z));
                            chunksFileList.get(new Vector2f(playerChunkX, playerChunkZ + z)).load();
                        }

                        if (!(chunksFileList.containsKey(new Vector2f(playerChunkX + x, playerChunkZ + z)))) {
                            chunksFileList.put(new Vector2f(playerChunkX + x, playerChunkZ + z), new ChunksFile(playerChunkX + x, playerChunkZ + z));
                            chunksFileList.get(new Vector2f(playerChunkX + x, playerChunkZ + z)).load();
                        }

                        if ((playerChunkZ - z) > 0) {
                            if (!(chunksFileList.containsKey(new Vector2f(playerChunkX + x, playerChunkZ - z)))) {
                                chunksFileList.put(new Vector2f(playerChunkX + x, playerChunkZ - z), new ChunksFile(playerChunkX + x, playerChunkZ - z));
                                chunksFileList.get(new Vector2f(playerChunkX + x, playerChunkZ - z)).load();
                            }

                            if (!(chunksFileList.containsKey(new Vector2f(playerChunkX, playerChunkZ - z)))) {
                                chunksFileList.put(new Vector2f(playerChunkX, playerChunkZ - z), new ChunksFile(playerChunkX, playerChunkZ - z));
                                chunksFileList.get(new Vector2f(playerChunkX, playerChunkZ - z)).load();
                            }

                            if ((playerChunkX - x) > 0) {
                                if (!(chunksFileList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ - z)))) {
                                    chunksFileList.put(new Vector2f(playerChunkX - x, playerChunkZ - z), new ChunksFile(playerChunkX - x, playerChunkZ - z));
                                    chunksFileList.get(new Vector2f(playerChunkX - x, playerChunkZ - z)).load();
                                }
                            }
                        }

                        if ((playerChunkX - x) > 0) {
                            if (!(chunksFileList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ + z)))) {
                                chunksFileList.put(new Vector2f(playerChunkX - x, playerChunkZ + z), new ChunksFile(playerChunkX - x, playerChunkZ + z));
                                chunksFileList.get(new Vector2f(playerChunkX - x, playerChunkZ + z)).load();
                            }
                            if (!(chunksFileList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ)))) {
                                chunksFileList.put(new Vector2f(playerChunkX - x, playerChunkZ), new ChunksFile(playerChunkX - x, playerChunkZ));
                                chunksFileList.get(new Vector2f(playerChunkX - x, playerChunkZ)).load();
                            }
                        }
                    }
                }


                playerChunkX = (int) Math.floor(cam.getPosition().x() / Terrain.getSIZE());
                playerChunkZ = (int) Math.floor(cam.getPosition().z() / Terrain.getSIZE());
                Terrain.setWorldChunk(chunkList);
                for (int x = 0; x < cam.viewDistance; x++) { // X - Axis iteration
                    for (int z = 0; z < cam.viewDistance; z++) { // Z - Axis iteration
                        loadChunks(playerChunkX, playerChunkZ, x, z);

                    }
                }


                unLoadChunks(playerChunkX, playerChunkZ, -cam.viewDistance, -cam.viewDistance,
                        cam.viewDistance, cam.viewDistance);

//            synchronized (worldChunk) {
//
//                worldChunk.clear();
//                worldChunk.putAll(Collections.synchronizedMap(new HashMap<>(chunkList)));
//            }
//            synchronized (entities) {
//                entities.clear();
//                entities.addAll(entitiesChunk);
//            }
            }catch(Exception e){
                e.printStackTrace();
            }
            isInit = true;
            try {
                Thread.yield();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        chunkList.clear();
        chunksFileList.clear();
        entitiesChunk.clear();
    }

    public void exit(){
        this.isExit = true;
    }

    private void unLoadChunks(int playerChunkX, int playerChunkZ, int xMin, int zMin, int xMax, int zMax) {

        chunkList.remove(new Vector2f(playerChunkX, playerChunkZ + zMax + 1));
        chunkList.remove(new Vector2f(playerChunkX, playerChunkZ + zMin - 1));
        chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ));
        chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ));

        chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ + zMax + 1));
        chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ + zMin - 1));
        chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ + zMin - 1));
        chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ + zMax + 1));
        for (int x = 0; x < cam.viewDistance + 1; x++) {

            chunkList.remove(new Vector2f(playerChunkX + x, playerChunkZ + zMax + 1));
            chunkList.remove(new Vector2f(playerChunkX - x, playerChunkZ + zMax + 1));
            chunkList.remove(new Vector2f(playerChunkX + x, playerChunkZ + zMin - 1));
            chunkList.remove(new Vector2f(playerChunkX - x, playerChunkZ + zMin - 1));
        }

        for (int z = 0; z < cam.viewDistance + 1; z++) {
            chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ + z));
            chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ - z));
            chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ + z));
            chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ - z));
        }
    }

    private void loadChunks(int playerChunkX, int playerChunkZ, int x, int z) {
        int chunkFileIndexX;
        int chunkFileIndexZ;

        int chunkFileIndexXPositive = (playerChunkX + x) / 3;

        int chunkFileIndexZPositive = (playerChunkZ + z) / 3;

//        if (!(chunkList.containsKey(new Vector2f(playerChunkX, playerChunkZ)))) {
//            chunkFileIndexX = playerChunkX / 3;
//            chunkFileIndexZ = playerChunkZ / 3;
//            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX, playerChunkZ);
//            chunkList.put(new Vector2f(playerChunkX, playerChunkZ), chunk); // Create new chunk
//        }

        if (!(chunkList.containsKey(new Vector2f(playerChunkX + x, playerChunkZ)))) {
            chunkFileIndexX = chunkFileIndexXPositive;
            chunkFileIndexZ = playerChunkZ / 3;
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX + x, playerChunkZ);
            if (chunk != null) {
                chunkList.put(new Vector2f(playerChunkX + x, playerChunkZ), chunk); // Create new chunk
                entitiesChunk.addAll(chunk.getEntities());
            }
        }

        if (!(chunkList.containsKey(new Vector2f(playerChunkX, playerChunkZ + z)))) {
            chunkFileIndexX = playerChunkX / 3;
            chunkFileIndexZ = chunkFileIndexZPositive;
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX, playerChunkZ + z);
            if (chunk != null) {
                chunkList.put(new Vector2f(playerChunkX, playerChunkZ + z), chunk); // Create new chunk
                entitiesChunk.addAll(chunk.getEntities());
            }
        }

        if (!(chunkList.containsKey(new Vector2f(playerChunkX + x, playerChunkZ + z)))) {
            chunkFileIndexX = chunkFileIndexXPositive;
            chunkFileIndexZ = chunkFileIndexZPositive;
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ))
                    .load(playerChunkX + x, playerChunkZ + z);
            if (chunk != null) {
                chunkList.put(new Vector2f(playerChunkX + x, playerChunkZ + z), chunk); // Create new chunk
                entitiesChunk.addAll(chunk.getEntities());
            }
        }
        if ((playerChunkZ - z) > 0) {
            if (!(chunkList.containsKey(new Vector2f(playerChunkX + x, playerChunkZ - z)))) {
                chunkFileIndexX = chunkFileIndexXPositive;
                chunkFileIndexZ = (playerChunkZ - z) / 3;
                Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX + x, playerChunkZ - z);
                if (chunk != null) {
                    chunkList.put(new Vector2f(playerChunkX + x, playerChunkZ - z), chunk); // Create a new chunk
                    entitiesChunk.addAll(chunk.getEntities());
                }
            }

            if (!(chunkList.containsKey(new Vector2f(playerChunkX, playerChunkZ - z)))) {
                chunkFileIndexX = playerChunkX / 3;
                chunkFileIndexZ = (playerChunkZ - z) / 3;
                Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX, playerChunkZ - z);
                if (chunk != null) {
                    chunkList.put(new Vector2f(playerChunkX, playerChunkZ - z), chunk); // Create a new chunk
                    entitiesChunk.addAll(chunk.getEntities());
                }
            }

            if ((playerChunkX - x) > 0) {
                if (!(chunkList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ - z)))) {
                    chunkFileIndexX = (playerChunkX - x) / 3;
                    chunkFileIndexZ = (playerChunkZ - z) / 3;
                    Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX - x, playerChunkZ - z);
                    if (chunk != null) {
                        chunkList.put(new Vector2f(playerChunkX - x, playerChunkZ - z), chunk); // Create new chunk
                        entitiesChunk.addAll(chunk.getEntities());
                    }
                }
            }
        }
        if ((playerChunkX - x) > 0) {
            if (!(chunkList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ + z)))) {
                chunkFileIndexX = (playerChunkX - x) / 3;
                chunkFileIndexZ = chunkFileIndexZPositive;
                Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX - x, playerChunkZ + z);
                if (chunk != null) {
                    chunkList.put(new Vector2f(playerChunkX - x, playerChunkZ + z), chunk); // Create new chunk
                    entitiesChunk.addAll(chunk.getEntities());
                }
            }
            if (!(chunkList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ)))) {
                chunkFileIndexX = (playerChunkX - x) / 3;
                chunkFileIndexZ = playerChunkZ / 3;
                Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX - x, playerChunkZ);
                if (chunk != null) {
                    chunkList.put(new Vector2f(playerChunkX - x, playerChunkZ), chunk); // Create new chunk
                    entitiesChunk.addAll(chunk.getEntities());
                }
            }
        }
    }

    public Map<Vector2f, Chunk> getChunkMap() {
        return new HashMap<>(Collections.synchronizedMap(chunkList));
    }


    public void addEntity(Entity entityPicked) {
        entitiesChunk.add(entityPicked);
    }

    public void addAllEntities(List<Entity> aabbs) {
        entitiesChunk.addAll(aabbs);
    }

    public void remove(Entity e) {
        entitiesChunk.remove(e);
    }

    public List<Entity> getEntities() {
        return Collections.synchronizedList(entitiesChunk);
    }

    public boolean isInit() {
        return isInit;
    }
}
