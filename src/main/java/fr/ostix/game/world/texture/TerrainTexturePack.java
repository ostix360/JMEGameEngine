package fr.ostix.game.world.texture;

public class TerrainTexturePack {
    private final TerrainTexture backgroundTexture;
    private final TerrainTexture rTexture;
    private final TerrainTexture gTexture;
    private final TerrainTexture bTexture;

    public TerrainTexturePack(TerrainTexture backgroundTexture, TerrainTexture rTexture,
                              TerrainTexture gTexture, TerrainTexture bTexture) {
        this.backgroundTexture = backgroundTexture;
        this.rTexture = rTexture;
        this.gTexture = gTexture;
        this.bTexture = bTexture;
    }

    public static TerrainTexturePack load(String line) {
        String[] values = line.split(";");
        TerrainTexture back = TerrainTexture.load(values[0],false);
        TerrainTexture r = TerrainTexture.load(values[1],false);
        TerrainTexture g = TerrainTexture.load(values[2],false);
        TerrainTexture b = TerrainTexture.load(values[3],false);
        return new TerrainTexturePack(back,r,g,b);
    }

    public TerrainTexture getBackgroundTexture() {
        return backgroundTexture;
    }

    public TerrainTexture getrTexture() {
        return rTexture;
    }

    public TerrainTexture getgTexture() {
        return gTexture;
    }

    public TerrainTexture getbTexture() {
        return bTexture;
    }
}
