package fr.ostix.game.entity.component.particle;

import fr.ostix.game.core.loader.json.JsonUtils;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.ComponentCreator;
import fr.ostix.game.graphics.particles.ParticleSystem;
import fr.ostix.game.graphics.particles.ParticleTarget;
import fr.ostix.game.graphics.particles.ParticleTargetProperties;
import fr.ostix.game.graphics.particles.ParticleTexture;
import fr.ostix.game.graphics.particles.particleSpawn.*;
import fr.ostix.game.graphics.textures.Texture;
import fr.ostix.game.toolBox.Logger;
import org.joml.Vector3f;

import java.util.Objects;

public class ParticleCreator implements ComponentCreator {
    @Override
    public Component createComponent(Entity entity) {
        return null;
    }

    @Override
    public Component loadComponent(String component, Entity entity) {
        ParticleSystem system = null;
        String[] lines = component.split("\n");
        Texture tex = ResourcePack.getTextureByName(lines[lines.length - 1]);
        ParticleTexture texture = new ParticleTexture(tex.getID(), tex.getNumbersOfRows(), tex.isAdditive());
        String[] values;
        try {
            values = lines[0].split(";");
            system = new ParticleSystem(texture, Float.parseFloat(values[0]), Float.parseFloat(values[1]),
                    Float.parseFloat(values[2]), Float.parseFloat(values[3]),
                    Float.parseFloat(values[4]));
            values = lines[1].split(";");
            setError(system, values);
            values = lines[2].split(";");
            Vector3f offset = new Vector3f(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));
            system.setPositionOffset(offset);
            values = lines[3].split(";");
            setDirection(system, values);
            values = lines[4].split(";");
            int id = Integer.parseInt(values[0]);
            ParticleSpawn spawn;
            switch (id) {
                case 0:
                    spawn = new Circle();
                    break;
                case 1:
                    spawn = new Line();
                    break;
                case 3:
                    spawn = new Sphere();
                    break;
                default:
                    spawn = new Point();
            }
            spawn.load(values);
            String line = lines[5];
            if (!Objects.equals(line, "")) {
                ParticleTargetProperties prop = JsonUtils.gsonInstance().fromJson(line, ParticleTargetProperties.class);
                system.setTarget(new ParticleTarget(prop, entity));
            }
        } catch (Exception e) {
            Logger.err("Failed to load Particle Component ");
            e.printStackTrace();
        }
        return new ParticleComponent(system, entity);
    }

    private void setError(ParticleSystem system, String[] values) {
        if (Boolean.parseBoolean(values[0])) system.randomizeRotation();
        system.setLifeError(Float.parseFloat(values[1]));
        system.setScaleError(Float.parseFloat(values[2]));
        system.setSpeedError(Float.parseFloat(values[3]));
    }

    private void setDirection(ParticleSystem system, String[] values) {
        Vector3f direction = new Vector3f(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));
        system.setDirection(direction, Float.parseFloat(values[3]));
    }
}
