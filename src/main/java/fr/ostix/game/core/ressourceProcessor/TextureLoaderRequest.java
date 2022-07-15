package fr.ostix.game.core.ressourceProcessor;


import fr.ostix.game.core.loader.*;
import fr.ostix.game.graphics.textures.*;

import java.io.*;

public class TextureLoaderRequest extends GLRequest {
    private TextureProperties prop;
    private InputStream file;
    private String Sfile;
    private TextureLoader texture;
    private Texture tex;
    private final boolean isForEntity;


    public TextureLoaderRequest(String file, TextureProperties prop) {
        this.prop = prop;
        this.Sfile = file;
        isForEntity = true;
    }


    public Texture getTex() {
        return tex;
    }

    public TextureLoaderRequest(String file) {
        this.Sfile = file;
        this.isForEntity = false;
    }

    public TextureLoaderRequest(InputStream file) {
        this.file = file;
        isForEntity = false;
    }

    public TextureLoader getTexture() {
        return texture;
    }

    @Override
    public void execute() {

        texture = Loader.INSTANCE.loadTexture(Sfile);

        if (isForEntity) {
            tex = new Texture(texture, prop);

        }
        super.execute();
    }

    @Override
    public String toString() {
        return "TextureLoaderRequest{" +
                "isExecuted=" + isExecuted +
                ", Properties=" + prop +
                ", InputStream File =" + file +
                ", String file='" + Sfile + '\'' +
                ", Texture =" + tex +
                ", isForEntity=" + isForEntity +
                '}';
    }
}
