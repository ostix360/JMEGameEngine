package fr.ostix.game.world.weather;

import fr.ostix.game.core.loader.Loader;

public enum SkyTexture {

    DAY(new String[]{"day/right", "day/left", "day/top", "day/bottom", "day/back", "day/front"}),
    NIGHT(new String[]{"night/right", "night/left", "night/top", "night/bottom", "night/back", "night/front"}),
    CLOUDY_NIGHT(new String[]{"night/cloudy/right", "night/cloudy/left", "night/cloudy/top",
            "night/cloudy/bottom", "night/cloudy/back", "night/cloudy/front"}),
    SUNSET(new String[]{"sunset/right", "sunset/left", "sunset/top", "sunset/bottom", "sunset/back", "sunset/front"});


    private final int textureId;

    SkyTexture(String[] name){
        this.textureId = Loader.INSTANCE.loadCubMap(name);
    }

    public int getTextureId() {
        return textureId;
    }
}
