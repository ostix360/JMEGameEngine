package fr.ostix.game.entity.component.collision;

import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.ComponentType;

public class CollisionComponent extends Component {

    public CollisionComponent(Entity e) {
        super(ComponentType.COLLISION_COMPONENT, e);
    }

    @Override
    public void update() {
        // TODO Update collision
    }
}
