package fr.ostix.game.entity.component.collision;

//import com.flowpowered.react.collision.shape.*;
//import com.flowpowered.react.collision.shape.CollisionShape.CollisionShapeType;
//import fr.ostix.game.entity.BoundingModel;

import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.ComponentCreator;

public class CollisionCreator implements ComponentCreator {


    @Override
    public Component createComponent(Entity entity) {
        return null;
    }

    @Override
    public Component loadComponent(String component, Entity entity) {
        String[] lines = component.split("\n");
        if (!lines[0].equalsIgnoreCase("Collision Component")) {
            return null;
        }
        CollisionProperty prop = new CollisionProperty();
        try {
            prop.setControllerType(lines[1]);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new CollisionComponent(entity, prop);
    }

}
