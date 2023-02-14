package fr.ostix.game.world.chunk;

import fr.ostix.game.core.camera.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.world.*;
import org.joml.Math;
import org.joml.*;

import java.util.*;
import java.util.concurrent.*;

public class ChunkHandler {

    private final Map<Vector2f, Chunk> chunkList;
    private final Map<Vector2f, ChunksFile> chunksFileList = new HashMap<>();
    private final Camera cam;

    private static final int CHUNK_FILE_SIZE = 33;

    private final List<Entity> entitiesChunk = new ArrayList<>();

    private static ExecutorService executor = Executors.newCachedThreadPool();

    private final World world;

    public ChunkHandler(Camera cam, Map<Vector2f, Chunk> chunkList, World world) {
        this.cam = cam;
        this.chunkList = chunkList;
        this.world = world;
        if (executor.isShutdown()){
            executor = Executors.newCachedThreadPool();
        }
    }

    public void run() {
        executor.execute(() -> {


            int playerChunkX = (int) Math.floor(cam.getPosition().x() / Terrain.getSIZE() / ((float) CHUNK_FILE_SIZE)); // Get the current chunk that the player is in on the X - Axis
            int playerChunkZ = (int) Math.floor(cam.getPosition().z() / Terrain.getSIZE() / ((float) CHUNK_FILE_SIZE)); // Get the current chunk that the player is in on the Z - Axis

            // System.out.println(playerChunkX + ", " + playerChunkZ);

            int viewDistanceChunkFile = cam.viewDistance;
            if (viewDistanceChunkFile <= CHUNK_FILE_SIZE) {
                viewDistanceChunkFile = CHUNK_FILE_SIZE + 1;
            }
            for (int x1 = 0; x1 <= viewDistanceChunkFile; x1 += CHUNK_FILE_SIZE) { // X - Axis iteration
                for (int z1 = 0; z1 <= viewDistanceChunkFile; z1 += CHUNK_FILE_SIZE) { // Z - Axis iteration
                    int x = (int) (x1 / CHUNK_FILE_SIZE);
                    int z = (int) (z1 / CHUNK_FILE_SIZE);
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


                    if (!(chunksFileList.containsKey(new Vector2f(playerChunkX + x, playerChunkZ - z)))) {
                        chunksFileList.put(new Vector2f(playerChunkX + x, playerChunkZ - z), new ChunksFile(playerChunkX + x, playerChunkZ - z));
                        chunksFileList.get(new Vector2f(playerChunkX + x, playerChunkZ - z)).load();
                    }

                    if (!(chunksFileList.containsKey(new Vector2f(playerChunkX, playerChunkZ - z)))) {
                        chunksFileList.put(new Vector2f(playerChunkX, playerChunkZ - z), new ChunksFile(playerChunkX, playerChunkZ - z));
                        chunksFileList.get(new Vector2f(playerChunkX, playerChunkZ - z)).load();
                    }


                    if (!(chunksFileList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ - z)))) {
                        chunksFileList.put(new Vector2f(playerChunkX - x, playerChunkZ - z), new ChunksFile(playerChunkX - x, playerChunkZ - z));
                        chunksFileList.get(new Vector2f(playerChunkX - x, playerChunkZ - z)).load();
                    }


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
            viewDistanceChunkFile = cam.viewDistance;
            chunksFileList.remove(new Vector2f(playerChunkX, playerChunkZ + viewDistanceChunkFile + 1));
            chunksFileList.remove(new Vector2f(playerChunkX, playerChunkZ - viewDistanceChunkFile - 1));
            chunksFileList.remove(new Vector2f(playerChunkX + viewDistanceChunkFile + 1, playerChunkZ));
            chunksFileList.remove(new Vector2f(playerChunkX - viewDistanceChunkFile - 1, playerChunkZ));

            chunksFileList.remove(new Vector2f(playerChunkX + viewDistanceChunkFile + 1, playerChunkZ + viewDistanceChunkFile + 1));
            chunksFileList.remove(new Vector2f(playerChunkX - viewDistanceChunkFile - 1, playerChunkZ - viewDistanceChunkFile - 1));
            chunksFileList.remove(new Vector2f(playerChunkX + viewDistanceChunkFile + 1, playerChunkZ - viewDistanceChunkFile - 1));
            chunksFileList.remove(new Vector2f(playerChunkX + viewDistanceChunkFile - 1, playerChunkZ + viewDistanceChunkFile + 1));


            for (int x = 0; x < cam.viewDistance / CHUNK_FILE_SIZE + 1; x++) {
                chunksFileList.remove(new Vector2f(playerChunkX + x, playerChunkZ + viewDistanceChunkFile + 1));
                chunksFileList.remove(new Vector2f(playerChunkX - x, playerChunkZ + viewDistanceChunkFile + 1));
                chunksFileList.remove(new Vector2f(playerChunkX + x, playerChunkZ - viewDistanceChunkFile - 1));
                chunksFileList.remove(new Vector2f(playerChunkX - x, playerChunkZ - viewDistanceChunkFile - 1));
            }

            for (int z = 0; z < cam.viewDistance / CHUNK_FILE_SIZE + 1; z++) {
                chunksFileList.remove(new Vector2f(playerChunkX + viewDistanceChunkFile + 1, playerChunkZ + z));
                chunksFileList.remove(new Vector2f(playerChunkX + viewDistanceChunkFile + 1, playerChunkZ - z));
                chunksFileList.remove(new Vector2f(playerChunkX - viewDistanceChunkFile - 1, playerChunkZ + z));
                chunksFileList.remove(new Vector2f(playerChunkX - viewDistanceChunkFile - 1, playerChunkZ - z));
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

//            synchronized (chunkList) {
//
//                chunkList.clear();
//                chunkList.putAll(Collections.synchronizedMap(new HashMap<>(chunkList)));
//            }
//            synchronized (entities) {
//                entities.clear();
//                entities.addAll(entitiesChunk);
//            }
        });
    }

    public void exit() {

        executor.shutdown();

    }

    private void unLoadChunks(int playerChunkX, int playerChunkZ, int xMin, int zMin, int xMax, int zMax) {
        Chunk chunk;

        chunk = chunkList.remove(new Vector2f(playerChunkX, playerChunkZ + zMax + 1));
        if (chunk != null) World.getPhysics().remove(chunk);

        chunk = chunkList.remove(new Vector2f(playerChunkX, playerChunkZ + zMin - 1));
        if (chunk != null) World.getPhysics().remove(chunk);

        chunk = chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ));
        if (chunk != null) World.getPhysics().remove(chunk);

        chunk = chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ));
        if (chunk != null) World.getPhysics().remove(chunk);


        chunk = chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ + zMax + 1));
        if (chunk != null) World.getPhysics().remove(chunk);

        chunk = chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ + zMin - 1));
        if (chunk != null) World.getPhysics().remove(chunk);

        chunk = chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ + zMin - 1));
        if (chunk != null) World.getPhysics().remove(chunk);

        chunk = chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ + zMax + 1));
        if (chunk != null) World.getPhysics().remove(chunk);

        for (int x = 0; x < cam.viewDistance + 1; x++) {
            chunk = chunkList.remove(new Vector2f(playerChunkX + x, playerChunkZ + zMax + 1));
            if (chunk != null) World.getPhysics().remove(chunk);

            chunk = chunkList.remove(new Vector2f(playerChunkX - x, playerChunkZ + zMax + 1));
            if (chunk != null) World.getPhysics().remove(chunk);

            chunk = chunkList.remove(new Vector2f(playerChunkX + x, playerChunkZ + zMin - 1));
            if (chunk != null) World.getPhysics().remove(chunk);

            chunk = chunkList.remove(new Vector2f(playerChunkX - x, playerChunkZ + zMin - 1));
            if (chunk != null) World.getPhysics().remove(chunk);
        }

        for (int z = 0; z < cam.viewDistance + 1; z++) {
            chunk = chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ + z));
            if (chunk != null) World.getPhysics().remove(chunk);

            chunk = chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ - z));
            if (chunk != null) World.getPhysics().remove(chunk);

            chunk = chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ + z));
            if (chunk != null) World.getPhysics().remove(chunk);

            chunk = chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ - z));
            if (chunk != null) World.getPhysics().remove(chunk);
        }
    }

    private void loadChunks(int playerChunkX, int playerChunkZ, int x, int z) {
        int chunkFileIndexX;
        int chunkFileIndexZ;

        int chunkFileIndexXPositive = (int) Math.floor((playerChunkX + x) / CHUNK_FILE_SIZE);

        int chunkFileIndexZPositive = (int) Math.floor((playerChunkZ + z) / CHUNK_FILE_SIZE);

//        if (!(chunkList.containsKey(new Vector2f(playerChunkX, playerChunkZ)))) {
//            chunkFileIndexX = playerChunkX / CHUNK_FILE_SIZE;
//            chunkFileIndexZ = playerChunkZ / CHUNK_FILE_SIZE;
//            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX, playerChunkZ);
//            chunkList.put(new Vector2f(playerChunkX, playerChunkZ), chunk); // Create new chunk
//        }

        if (!(chunkList.containsKey(new Vector2f(playerChunkX + x, playerChunkZ)))) {
            chunkFileIndexX = chunkFileIndexXPositive;
            chunkFileIndexZ = (int) Math.floor(playerChunkZ / CHUNK_FILE_SIZE);

            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ))
                    .load(playerChunkX + x, playerChunkZ);
            if (chunk != null) {
                chunkList.put(new Vector2f(playerChunkX + x, playerChunkZ), chunk); // Create new chunk
                entitiesChunk.addAll(chunk.getEntities());
                World.getPhysics().add(chunk);
            }
        }

        if (!(chunkList.containsKey(new Vector2f(playerChunkX, playerChunkZ + z)))) {
            chunkFileIndexX = (int) Math.floor(playerChunkX / CHUNK_FILE_SIZE);
            chunkFileIndexZ = chunkFileIndexZPositive;
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ))
                    .load(playerChunkX, playerChunkZ + z);
            if (chunk != null) {
                chunkList.put(new Vector2f(playerChunkX, playerChunkZ + z), chunk); // Create new chunk
                entitiesChunk.addAll(chunk.getEntities());
                World.getPhysics().add(chunk);
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
                World.getPhysics().add(chunk);
            }
        }
        if (!(chunkList.containsKey(new Vector2f(playerChunkX + x, playerChunkZ - z)))) {
            chunkFileIndexX = chunkFileIndexXPositive;
            chunkFileIndexZ = (int) Math.floor((playerChunkZ - z) / CHUNK_FILE_SIZE);
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ))
                    .load(playerChunkX + x, playerChunkZ - z);
            if (chunk != null) {
                chunkList.put(new Vector2f(playerChunkX + x, playerChunkZ - z), chunk); // Create a new chunk
                entitiesChunk.addAll(chunk.getEntities());
                World.getPhysics().add(chunk);
            }
        }


        if (!(chunkList.containsKey(new Vector2f(playerChunkX, playerChunkZ - z)))) {
            chunkFileIndexX = (int) Math.floor(playerChunkX / ((float) CHUNK_FILE_SIZE));
            chunkFileIndexZ = (int) Math.floor((playerChunkZ - z) / ((float) CHUNK_FILE_SIZE));
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ))
                    .load(playerChunkX, playerChunkZ - z);
            if (chunk != null) {
                chunkList.put(new Vector2f(playerChunkX, playerChunkZ - z), chunk); // Create a new chunk
                entitiesChunk.addAll(chunk.getEntities());
                World.getPhysics().add(chunk);
            }
        }

        if (!(chunkList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ - z)))) {
            chunkFileIndexX = (int) Math.floor((playerChunkX - x) / ((float) CHUNK_FILE_SIZE));
            chunkFileIndexZ = (int) Math.floor((playerChunkZ - z) / ((float) CHUNK_FILE_SIZE));
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ))
                    .load(playerChunkX - x, playerChunkZ - z);
            if (chunk != null) {
                chunkList.put(new Vector2f(playerChunkX - x, playerChunkZ - z), chunk); // Create new chunk
                entitiesChunk.addAll(chunk.getEntities());
                World.getPhysics().add(chunk);
            }
        }


        if (!(chunkList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ + z)))) {
            chunkFileIndexX = (int) Math.floor((playerChunkX - x) / ((float) CHUNK_FILE_SIZE));
            chunkFileIndexZ = chunkFileIndexZPositive;
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX - x, playerChunkZ + z);
            if (chunk != null) {
                chunkList.put(new Vector2f(playerChunkX - x, playerChunkZ + z), chunk); // Create new chunk
                entitiesChunk.addAll(chunk.getEntities());
                World.getPhysics().add(chunk);
            }
        }
        if (!(chunkList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ)))) {
            chunkFileIndexX = (int) Math.floor((playerChunkX - x) / ((float) CHUNK_FILE_SIZE));
            chunkFileIndexZ = (int) Math.floor(playerChunkZ / ((float) CHUNK_FILE_SIZE));
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX - x, playerChunkZ);
            if (chunk != null) {
                chunkList.put(new Vector2f(playerChunkX - x, playerChunkZ), chunk); // Create new chunk
                entitiesChunk.addAll(chunk.getEntities());
                World.getPhysics().add(chunk);
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
}
