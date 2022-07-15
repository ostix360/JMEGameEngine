package fr.ostix.game.world.weather;

import fr.ostix.game.toolBox.*;

public class WeatherFrame {
    private final float time;
    private final float fogDensity;
    private final float fogGradient;
    private final Color fogColor;
    private final Color sunLightColor;
    private final SkyTexture skyTexture;


    public WeatherFrame(float time, float fogDensity, float fogGradient, Color fogColor, Color sunLightColor, SkyTexture skyTexture) {
        this.time = time;
        this.fogDensity = fogDensity;
        this.fogGradient = fogGradient;
        this.fogColor = fogColor;
        this.sunLightColor = sunLightColor;
        this.skyTexture = skyTexture;
    }

    public SkyTexture getSkyTexture() {
        return skyTexture;
    }

    public float getTime() {
        return time;
    }

    public static float getInterpolatedTime(WeatherFrame frame1, WeatherFrame frame2, float currentTime){
        float interval = frame2.time - frame1.time;
        float progress = currentTime - frame1.time;
        return progress/interval;
    }

    public static float getInterpolatedFogDensity(WeatherFrame frame1, WeatherFrame frame2,float blendFactor){
        float part1 = frame1.fogDensity * (1.0F - blendFactor);
        float part2 = frame2.fogDensity * blendFactor;
        return part1 + part2;
    }

    public static float getInterpolatedFogGradients(WeatherFrame frame1, WeatherFrame frame2,float blendFactor){
        float part1 = frame1.fogGradient * (1.0F - blendFactor);
        float part2 = frame2.fogGradient * blendFactor;
        return part1 + part2;
    }

    public Color getFogColor() {
        return fogColor;
    }
}
