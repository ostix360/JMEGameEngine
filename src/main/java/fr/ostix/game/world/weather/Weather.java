package fr.ostix.game.world.weather;

import fr.ostix.game.core.camera.ICamera;
import fr.ostix.game.toolBox.Color;
import org.joml.Math;
import org.joml.Vector3f;

public class Weather {

    private final Sky sky;
    private WeatherFrame actualFrame;
    private float density;
    private float gradient;

    private final WeatherFrame[] weatherFrames = {
            new WeatherFrame(0.0F, 0.0023F, 5.0F, new Color(0.0F, 0.0F, 0.1F), new Color(0.0F, 0.0F, 0.1F), SkyTexture.NIGHT),
            new WeatherFrame(3500.0F, 0.0023F, 5.0F, new Color(0.01F, 0.0F, 0.15F), new Color(0.0F, 0.0F, 0.1F), SkyTexture.NIGHT),
            new WeatherFrame(5000.0F, 0.004F, 1.0F, new Color(0.8F, 0.2F, 0.2F), new Color(1.2F, 0.1F, 0.2F), SkyTexture.SUNSET),
            new WeatherFrame(8000.0F, 0.0023F, 5.0F, new Color(0.5444f, 0.62f, 0.69f), new Color(1.0F, 1.0F, 1.0F), SkyTexture.DAY),
            new WeatherFrame(12000.0F, 0.0023F, 5.0F, new Color(0.5444f, 0.62f, 0.69f), new Color(1.0F, 1.0F, 1.0F), SkyTexture.DAY),
            new WeatherFrame(16500.0F, 0.0023F, 5.0F, new Color(0.5444f, 0.62f, 0.69f), new Color(1.0F, 1.0F, 1.0F), SkyTexture.DAY),
            new WeatherFrame(20000.0F, 0.004F, 1.0F, new Color(200.0F, 61.0F, 20.0F, true), new Color(240.0F, 61.0F, 0.0F, true), SkyTexture.SUNSET),
            new WeatherFrame(23500.0F, 0.0023F, 5.0F, new Color(0.01F, 0.01F, 0.08F), new Color(0.0F, 0.0F, 0.1F), SkyTexture.NIGHT),
            new WeatherFrame(24001.0F, 0.0023F, 5.0F, new Color(0.0F, 0.0F, 0.1F), new Color(0.0F, 0.0F, 0.1F), SkyTexture.NIGHT)};

    public Weather(ICamera cam) {
        this.sky = new Sky(cam);
        updateSkyBox(weatherFrames[0], weatherFrames[1]);
    }

    public void update(float time) {
        calculateSunPosition(time);
        interpolateWeather(time);
    }

    private void interpolateWeather(float time) {
        WeatherFrame frame1 = this.weatherFrames[0];
        WeatherFrame frame2;
        if (actualFrame == null){
            actualFrame = frame1;
        }
        int pointer = 1;
        for (; ; ) {
            frame2 = this.weatherFrames[(pointer++)];
            if (time < frame2.getTime()) {
                break;
            }
            frame1 = frame2;
        }
        float timeFactor = WeatherFrame.getInterpolatedTime(frame1, frame2, time);
       // if (!actualFrame.equals(frame1)) {
            updateSkyBox(frame1, frame2);
        //}
        updateFogVariables(timeFactor,frame1, frame2);
        sky.getSkyBox().setBlendFactor(timeFactor);
        sky.update(1 / 60f);
        actualFrame = frame1;
    }

    private void updateSkyBox(WeatherFrame frame1, WeatherFrame frame2) {
        this.sky.getSkyBox().setTextures(frame1.getSkyTexture(), frame2.getSkyTexture());
    }

    private void calculateSunPosition(float time) {
        double radianTime = time * 3.141592653589793D / 12000.0D - 1.5707963267948966D;
        float x = (float) (1000000.0D * Math.cos(radianTime));
        float y = (float) (600000.0D * Math.sin(radianTime)) + 450000.0F;
        float z = (float) (399999.96875D * Math.sin(radianTime));
        this.sky.getSun().setPosition(new Vector3f(x, y, z));
    }

    private void updateFogVariables(float timeFactor, WeatherFrame frame1, WeatherFrame frame2) {
        this.density = WeatherFrame.getInterpolatedFogDensity(frame1, frame2, timeFactor);
        this.gradient = WeatherFrame.getInterpolatedFogGradients(frame1, frame2, timeFactor);
        Color fogColor = Color.getInterpolatedColor(frame1.getFogColor(), frame2.getFogColor(),timeFactor);
        this.sky.setSkyColour(fogColor);
    }

    public float getDensity() {
        return density;
    }

    public float getGradient() {
        return gradient;
    }

    public Sky getSky() {
        return this.sky;
    }
}
