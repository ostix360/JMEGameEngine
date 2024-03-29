package fr.ostix.game.entity.component.collision;

import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.control.RigidBodyControl;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.ComponentType;

public class CollisionComponent extends Component {
    private PhysicsControl physic;

    private final CollisionProperty prop;

    public CollisionComponent(Entity e, CollisionProperty prop) {
        super(ComponentType.COLLISION_COMPONENT, e);
        this.prop = prop;
        if (prop.getControllerType().equals("Character")) {
            physic = new CharacterControl(new MeshCollisionShape(e.getModel().getMeshModel()),4f);
        }else{
            physic = new RigidBodyControl(0f);
        }

    }

    @Override
    public void update() {
        physic.update(1/60f);
    }

    public PhysicsControl getPhysic() {
        return physic;
    }

    public CollisionProperty getProp() {
        return prop;
    }
}
