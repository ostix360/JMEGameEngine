package fr.ostix.game.world.texture;

import fr.ostix.game.core.ressourceProcessor.*;

public class TerrainTexture {

    private int textureID;
    private final TextureLoaderRequest tlr;

    private TerrainTexture(TextureLoaderRequest tlr) {
        this.tlr = tlr;
    }

    public static TerrainTexture load(String name,boolean isBlendMap) {
        String str = "";
        if (isBlendMap) {
            str = "blendMap/";
        }else{
            str = "pack/";
        }
        TextureLoaderRequest tlr = new TextureLoaderRequest("terrain/"+ str + name);
        GLRequestProcessor.sendRequest(tlr);

        return new TerrainTexture(tlr);
    }


    public int getTextureID() {
        if (textureID == 0){
            if (tlr.isExecuted()){
                textureID = tlr.getTexture().getId();
            }
        }
        return textureID;
    }
}
