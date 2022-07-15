package fr.ostix.game.world.chunk;


import fr.ostix.game.toolBox.*;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class ChunksFile {
    private final List<Chunk> chunks;
    private final int x;
    private final int z;
    private String content;

    public ChunksFile(int x, int z) {
        this.x = x;
        this.z = z;
        chunks = new ArrayList<>();
    }

    public void load() {
        String content = "";
        try (FileChannel fc = openReadableFile()) {
            if (fc == null) {
                this.content = "";
                return;
            }
            ByteBuffer buffer = ByteBuffer.allocate(6);
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
    }

    public Chunk load(int x, int z) {
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
                return this.addPart(Chunk.load(sb.toString(), x, z));
            }
        }
       // System.err.println("Chunk not found in file X" + this.x + "Z" + this.z + " for the chunk in x : " + x + " z : " + z);
        return null;
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
}
