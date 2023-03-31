package fr.ostix.game.world.chunk;


import fr.ostix.game.toolBox.Logger;
import fr.ostix.game.toolBox.ToolDirectory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class ChunksFile {
    private final List<Chunk> chunks;
    private final int x;
    private final int z;
    private String content;

    private final float[][] heights  = new float[513][513];

    private final float[] cHeights = new float[512*512];

    private static final int MAX_HEIGHT = 100;

//    private final TerrainControl control;

    public ChunksFile(int x, int z) {
        this.x = x;
        this.z = z;
        chunks = new ArrayList<>();
//        this.control = new TerrainControl(this,0);
    }

    public void load() {
        String content = "";
        try (FileChannel fc = openReadableFile()) {
            if (fc == null) {
                this.content = "";
                return;
            }
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int noOfBytesRead = fc.read(buffer);
            StringBuilder sb = new StringBuilder();
            while (noOfBytesRead != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    sb.append((char) buffer.get());
                }
                buffer.clear();
                noOfBytesRead = fc.read(buffer);
            }
            content = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.content = content;
        String[] chunksContent = this.content.split("\n");
        if (chunksContent.length > 0) {
            loadHeightMap(chunksContent[0]);
        } else {
            loadHeightMap("default1024");
        }
    }

    public Chunk load(int x, int z) {
//        for (Chunk c : chunks) {
//            if (c.getX() == x && c.getZ() == z) {
//                return c;
//            }
//        }
        String[] chunksContent = this.content.split("\n");
        for (int i = 0; i < chunksContent.length; i++) {
            StringBuilder sb = new StringBuilder();
            if (chunksContent[i].contains("CHUNK " + x + ";" + z)) {
                i++;
                while (!chunksContent[i].contains("CHUNK")) {
                    sb.append(chunksContent[i]).append("\n");
                    i++;
                    if (i >= chunksContent.length) break;
                }
                float[][] heights = chooseHeight(x, z);
                return this.addPart(Chunk.load(sb.toString(), x, z, heights));
            }
        }
        //System.err.println("Chunk not found in file " + content);
        return null;
    }

    private float[][] chooseHeight(int x, int z) {
        float[][] heights = new float[18][18];
        int xIndex;
        int zIndex;
        if (this.x >= 0) {
            xIndex = (x / (this.x + 1)) * 15;
            if (this.z >= 0) {
                zIndex = (z / (this.z + 1)) * 15;
                for (int z1 = 0; z1 < 18; z1++) {            //Boucle de generation de la hauteur
                    for (int x1 = 0; x1 < 18; x1++) {
                        heights[x1][z1] = this.heights[xIndex + x1][zIndex + z1];
                    }
                }
            } else {
                zIndex = (z / (this.z) - 1) * 15;
                for (int z1 = 17; z1 >= 0; z1--) {            //Boucle de generation de la hauteur
                    for (int x1 = 0; x1 < 18; x1++) {
                        heights[x1][17 - z1] = this.heights[xIndex + x1][zIndex + z1];
                    }
                }
            }
        } else {
            xIndex = (x / (this.x) - 1) * 15;
            if (this.z >= 0) {
                zIndex = (z / (this.z + 1)) * 15;
                for (int z1 = 0; z1 < 18; z1++) {            //Boucle de generation de la hauteur
                    for (int x1 = 17; x1 >= 0; x1--) {
                        heights[17 - x1][z1] = this.heights[xIndex + x1][zIndex + z1];
                    }
                }
            } else {
                zIndex = (z / (this.z) - 1) * 15;
                for (int z1 = 17; z1 >= 0; z1--) {            //Boucle de generation de la hauteur
                    for (int x1 = 17; x1 >= 0; x1--) {
                        heights[17 - x1][17 - z1] = this.heights[xIndex + x1][zIndex + z1];
                    }
                }
            }
        }
//        for (int z1 = 0; z1 < 18; z1++) {            //Boucle de generation de la hauteur
//            for (int x1 = 0; x1 < 18; x1++) {
//                heights[x1][z1] = this.heights[xIndex+x1][zIndex+z1];
//            }
//        }

        return heights;
    }

    public void loadHeightMap(String map) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(ToolDirectory.RES_FOLDER + "/textures/terrain/heightMap/" + map + ".png"));
        } catch (IOException e) {
            Logger.err("Couldn't load heightMap :" + ToolDirectory.RES_FOLDER + "/textures/terrain/heightMap/" + map + ".png" , e);
        }
        assert image != null;
        if (image.getHeight() != 512 || image.getWidth() != 512) {
            Logger.err("Votre heightMap doit être de 512 x 512");
        }
        for (int z = 0; z < 512; z++) {            // Boucle de generation de monde
            for (int x = 0; x < 512; x++) {
                heights[x][z] = cHeights[x+z] = getHeight(x, z, image);
            }
        }
//        control.setSpatial(this);
//        control.setPhysicsSpace(World.getPhysics());

    }

    private float getHeight(int x, int z, BufferedImage image) {
        if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
            return 0;
        }
        float height = image.getRGB(x, z);
        height += 256 * 256 * 256f / 2f;
        height /= 256 * 256 * 256f / 2f;
        height *= MAX_HEIGHT;
        return height;
    }

    public Chunk addPart(Chunk chunk) {
        this.chunks.add(chunk);
        return chunk;
    }

    private FileChannel openReadableFile() throws IOException {
        File f = new File(ToolDirectory.RES_FOLDER + "/world/X" + x + "Z" + z + ".chks");
        if (!f.exists()) {
            return null;
        }
        FileInputStream fos = new FileInputStream(f);
        return fos.getChannel();
    }

    public float[] getHeights() {
        return cHeights;
    }

    public int getZ() {
        return z;
    }

    public int getX() {
        return x;
    }
}
