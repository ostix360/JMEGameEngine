package fr.ostix.game.entity.component.light;


import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.ComponentCreator;
import fr.ostix.game.toolBox.Color;
import org.joml.Vector3f;


public class LightCreator implements ComponentCreator {

    @Override
    public Component createComponent(Entity entity) {
        return new Light(new Vector3f(), Color.SUN, entity);
    }

    @Override
    public Component loadComponent(String component, Entity entity) {
        String[] lines = component.split("\n");
        Vector3f pos;
        Color color;
        Vector3f att;
        float power;
        String[] values = lines[0].split(";");
        pos = new Vector3f(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));
        values = lines[1].split(";");
        color = new Color(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));
        values = lines[2].split(";");
        att = new Vector3f(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));
        power = Float.parseFloat(lines[3]);
        pos.add(entity.getPosition());
        return new Light(pos, color, power, att, entity);
    }


}
