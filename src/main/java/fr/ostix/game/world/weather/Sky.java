package fr.ostix.game.world.weather;

import fr.ostix.game.entity.camera.*;
import fr.ostix.game.entity.component.light.*;
import fr.ostix.game.toolBox.*;
import org.joml.*;

public class Sky {
    private final SkyBox skybox;
    private Color skyColor = new Color(0.5444f, 0.62f, 0.69f);
    private final Light sun;

    protected Sky(ICamera camera) {
        this.skybox = new SkyBox(camera);
        this.sun = new Light(new Vector3f(100000, 100000, -100000), Color.SUN, 0.5f, null);
    }

    public void update(float deltaTime) {
        skybox.update(deltaTime);
    }

    public Light getSun() {
        return sun;
    }

    public SkyBox getSkyBox() {
        return this.skybox;
    }

    public Color getSkyColour() {
        return this.skyColor;
    }

    public void setSkyColour(Color skyColor) {
        this.skyColor = skyColor;
    }

}
