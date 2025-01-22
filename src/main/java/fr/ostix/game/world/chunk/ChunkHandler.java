package fr.ostix.game.world.chunk;

import fr.ostix.game.core.camera.Camera;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.world.Terrain;
import fr.ostix.game.world.World;
import org.joml.Math;
import org.joml.Vector2f;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        if (executor.isShutdown()) {
            executor = Executors.newCachedThreadPool();
        }
    }

    public void run() {
        executor.execute(() -> {

            int playerChunkX = (int) Math.floor(cam.getPosition().x() / Terrain.getSIZE() / ((float) CHUNK_FILE_SIZE)); // Get
            // the current chunk that the player is in on the X - Axis
            int playerChunkZ = (int) Math.floor(cam.getPosition().z() / Terrain.getSIZE() / ((float) CHUNK_FILE_SIZE)); // Get
            // the current chunk that the player is in on the Z - Axis

            // System.out.println(playerChunkX + ", " + playerChunkZ);

            int viewDistanceChunkFile = cam.viewDistance;
            if (viewDistanceChunkFile <= CHUNK_FILE_SIZE) {
                viewDistanceChunkFile = CHUNK_FILE_SIZE + 1;
            }
            for (int x1 = 0; x1 <= viewDistanceChunkFile; x1 += CHUNK_FILE_SIZE) { // X - Axis iteration
                for (int z1 = 0; z1 <= viewDistanceChunkFile; z1 += CHUNK_FILE_SIZE) { // Z - Axis iteration
                    int x = (x1 / CHUNK_FILE_SIZE);
                    int z = (z1 / CHUNK_FILE_SIZE);
                    putChunkFile(playerChunkX, playerChunkZ, x, 0);

                    putChunkFile(playerChunkX, playerChunkZ, 0, z);

                    putChunkFile(playerChunkX, playerChunkZ, x, z);

                    putChunkFile(playerChunkX, playerChunkZ, x, -z);

                    putChunkFile(playerChunkX, playerChunkZ, 0, -z);

                    putChunkFile(playerChunkX, playerChunkZ, -x, -z);

                    putChunkFile(playerChunkX, playerChunkZ, -x, z);

                    putChunkFile(playerChunkX, playerChunkZ, -x, 0);
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

            unLoadChunks(playerChunkX, playerChunkZ, -cam.viewDistance, -cam.viewDistance, cam.viewDistance, cam.viewDistance);

            // synchronized (chunkList) {
            //
            // chunkList.clear();
            // chunkList.putAll(Collections.synchronizedMap(new HashMap<>(chunkList)));
            // }
            // synchronized (entities) {
            // entities.clear();
            // entities.addAll(entitiesChunk);
            // }
        });
    }

    public void exit() {

        executor.shutdown();

    }

    private void unLoadChunks(int playerChunkX, int playerChunkZ, int xMin, int zMin, int xMax, int zMax) {
        Chunk chunk;

        for (int x = xMin - 1; x <= xMax + 1; x++) {
            chunk = chunkList.remove(new Vector2f(playerChunkX + x, playerChunkZ + zMax + 1));
            World.getPhysics().remove(chunk);

            chunk = chunkList.remove(new Vector2f(playerChunkX + x, playerChunkZ + zMin - 1));
            World.getPhysics().remove(chunk);
        }

        for (int z = zMin - 1; z <= zMax + 1; z++) {
            chunk = chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ + z));
            World.getPhysics().remove(chunk);

            chunk = chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ + z));
            World.getPhysics().remove(chunk);
        }

        // Remove corner chunks
        chunk = chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ + zMax + 1));
        World.getPhysics().remove(chunk);

        chunk = chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ + zMax + 1));
        World.getPhysics().remove(chunk);

        chunk = chunkList.remove(new Vector2f(playerChunkX + xMax + 1, playerChunkZ + zMin - 1));
        World.getPhysics().remove(chunk);

        chunk = chunkList.remove(new Vector2f(playerChunkX + xMin - 1, playerChunkZ + zMin - 1));
        World.getPhysics().remove(chunk);
    }

    private void loadChunks(int playerChunkX, int playerChunkZ, int x, int z) {
        int chunkFileIndexX;
        int chunkFileIndexZ;

        int chunkFileIndexXPositive = (int) Math.floor((playerChunkX + x) / (float) CHUNK_FILE_SIZE);

        int chunkFileIndexZPositive = (int) Math.floor((playerChunkZ + z) / (float) CHUNK_FILE_SIZE);

        // if (!(chunkList.containsKey(new Vector2f(playerChunkX, playerChunkZ)))) {
        // chunkFileIndexX = playerChunkX / CHUNK_FILE_SIZE;
        // chunkFileIndexZ = playerChunkZ / CHUNK_FILE_SIZE;
        // Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX,
        // chunkFileIndexZ)).load(playerChunkX, playerChunkZ);
        // chunkList.put(new Vector2f(playerChunkX, playerChunkZ), chunk); // Create new
        // chunk
        // }

        if (!(chunkList.containsKey(new Vector2f(playerChunkX + x, playerChunkZ)))) {
            chunkFileIndexX = chunkFileIndexXPositive;
            chunkFileIndexZ = (int) Math.floor(playerChunkZ / (float) CHUNK_FILE_SIZE);

            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX + x, playerChunkZ);
            addChunk(chunk, x, 0, playerChunkX, playerChunkZ);
        }

        if (!(chunkList.containsKey(new Vector2f(playerChunkX, playerChunkZ + z)))) {
            chunkFileIndexX = (int) Math.floor(playerChunkX / (float) CHUNK_FILE_SIZE);
            chunkFileIndexZ = chunkFileIndexZPositive;
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX, playerChunkZ + z);
            addChunk(chunk, 0, z, playerChunkX, playerChunkZ);
        }

        if (!(chunkList.containsKey(new Vector2f(playerChunkX + x, playerChunkZ + z)))) {
            chunkFileIndexX = chunkFileIndexXPositive;
            chunkFileIndexZ = chunkFileIndexZPositive;
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX + x, playerChunkZ + z);
            addChunk(chunk, x, z, playerChunkX, playerChunkZ);
        }
        if (!(chunkList.containsKey(new Vector2f(playerChunkX + x, playerChunkZ - z)))) {
            chunkFileIndexX = chunkFileIndexXPositive;
            chunkFileIndexZ = (int) Math.floor((playerChunkZ - z) / (float) CHUNK_FILE_SIZE);
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX + x, playerChunkZ - z);
            addChunk(chunk, x, -z, playerChunkX, playerChunkZ);
        }

        if (!(chunkList.containsKey(new Vector2f(playerChunkX, playerChunkZ - z)))) {
            chunkFileIndexX = (int) Math.floor(playerChunkX / ((float) CHUNK_FILE_SIZE));
            chunkFileIndexZ = (int) Math.floor((playerChunkZ - z) / ((float) CHUNK_FILE_SIZE));
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX, playerChunkZ - z);
            addChunk(chunk, 0, -z, playerChunkX, playerChunkZ);
        }

        if (!(chunkList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ - z)))) {
            chunkFileIndexX = (int) Math.floor((playerChunkX - x) / ((float) CHUNK_FILE_SIZE));
            chunkFileIndexZ = (int) Math.floor((playerChunkZ - z) / ((float) CHUNK_FILE_SIZE));
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX - x, playerChunkZ - z);
            addChunk(chunk, -x, -z, playerChunkX, playerChunkZ);
        }

        if (!(chunkList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ + z)))) {
            chunkFileIndexX = (int) Math.floor((playerChunkX - x) / ((float) CHUNK_FILE_SIZE));
            chunkFileIndexZ = chunkFileIndexZPositive;
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX - x, playerChunkZ + z);
            addChunk(chunk, -x, z, playerChunkX, playerChunkZ);
        }
        if (!(chunkList.containsKey(new Vector2f(playerChunkX - x, playerChunkZ)))) {
            chunkFileIndexX = (int) Math.floor((playerChunkX - x) / ((float) CHUNK_FILE_SIZE));
            chunkFileIndexZ = (int) Math.floor(playerChunkZ / ((float) CHUNK_FILE_SIZE));
            Chunk chunk = chunksFileList.get(new Vector2f(chunkFileIndexX, chunkFileIndexZ)).load(playerChunkX - x, playerChunkZ);
            addChunk(chunk, -x, 0, playerChunkX, playerChunkZ);
        }

    }

    private void addChunk(Chunk chunk, int x, int z, int playerChunkX, int playerChunkZ) {
        if (chunk != null) {
            chunkList.put(new Vector2f(playerChunkX + x, playerChunkZ + z), chunk);
            entitiesChunk.addAll(chunk.getEntities());
            World.getPhysics().add(chunk);
        }
    }

    private void putChunkFile(int playerChunkX, int playerChunkZ, int x, int z) {
        Vector2f currentChunkFile = new Vector2f(playerChunkX + x, playerChunkZ + z);
        if (!(chunksFileList.containsKey(currentChunkFile))) {
            chunksFileList.put(currentChunkFile, new ChunksFile((int) currentChunkFile.x, (int) currentChunkFile.y));
            chunksFileList.get(currentChunkFile).load();
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
