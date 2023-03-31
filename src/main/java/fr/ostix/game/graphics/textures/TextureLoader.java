package fr.ostix.game.graphics.textures;

import fr.ostix.game.toolBox.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static fr.ostix.game.toolBox.ToolDirectory.RES_FOLDER;
import static org.lwjgl.opengl.GL11.*;

public class TextureLoader {

    private final int id;
    private final int width;

    public TextureLoader(int id, int width) {
        this.id = id;
        this.width = width;
    }

    public static TextureLoader loadTexture(String file, int mode, boolean isClampEdge) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(RES_FOLDER + "/textures/" + file + ".png"));
        } catch (IOException e) {
            Logger.err("impossible de lire " + RES_FOLDER + "/textures/" + file + ".png");
            e.printStackTrace();
        }
        assert image != null : "impossible de lire " + RES_FOLDER + "/textures/" + file + ".png";
        int w = image.getWidth();
        int h = image.getHeight();


        int[] pixels = image.getRGB(0,0,w,h,null,0,w);

        ByteBuffer buffer = BufferUtils.createByteBuffer(w*h*4);

        for (int y = 0;y<w;y++)
        {
            for (int x = 0;x<h;x++)
            {
                int i = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((i >> 16) & 0xFF));
                buffer.put((byte) ((i >> 8) & 0xFF));
                buffer.put((byte) ((i ) & 0xFF));
                buffer.put((byte) ((i >> 24) & 0xFF));
            }
        }

        buffer.flip();


        int id = glGenTextures();


        glBindTexture(GL_TEXTURE_2D,id);
        glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,w,h,0,GL_RGBA,GL_UNSIGNED_BYTE,buffer);
        Logger.errGL("Error while loading texture");

        if (mode == TextureUtils.MIPMAP_MODE) {
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            Logger.errGL("Error while generating mipmap");
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);

        } else if (mode == TextureUtils.MIPMAP_ANISOTROPIC_MODE) {
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
            if (GL.getCapabilities().GL_EXT_texture_filter_anisotropic) {
                float amount = Math.min(4f, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0f);
                GL11.glTexParameterf(GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
                Logger.errGL("Error while setting anisotropic filtering");
            } else {
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 3f);
                Logger.warn("Anisotropic filtering is not supported by your graphic card");
            }
        } else if (mode == TextureUtils.NEAREST_MODE) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        } else if (mode == TextureUtils.LINEAR_MODE){
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        }else{
            glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
        }
        if (isClampEdge) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        } else {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        }
        Logger.errGL("Error while applying parameter texture");

        glBindTexture(GL_TEXTURE_2D,0);

        return new TextureLoader(id,w);
    }

//    public static ByteBuffer loadImage(BufferedImage image) {
//        int w = image.getWidth();
//        int h = image.getHeight();
//
//        int[] pixels = new int[w*h];
//        image.getRGB(0,0,w,h,pixels,0,w);
//
//        ByteBuffer buffer = BufferUtils.createByteBuffer(w*h*4);
//
//        for (int y = 0;y<w;y++)
//        {
//            for (int x = 0;x<h;x++)
//            {
//                int i = pixels[x+y*w];
//                buffer.put((byte) ((i >> 16) & 0xFF));
//                buffer.put((byte) ((i >> 8) & 0xFF));
//                buffer.put((byte) ((i ) & 0xFF));
//                buffer.put((byte) ((i >> 24) & 0xFF));
//            }
//        }
//
//        buffer.flip();
//        return buffer;
//    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return this.width;
    }
}
