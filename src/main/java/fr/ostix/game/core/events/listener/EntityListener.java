package fr.ostix.game.core.events.listener;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.entity.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.entity.entities.*;

public class EntityListener implements Listener {
    // private final List<Entity> entities = new ArrayList<>();
    // public static EntityListener instance = new EntityListener();

    private final Entity e;

    public EntityListener(Entity e) {
        this.e = e;
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent e) {
        if (this.e.equals(e.getEntity())) {
            if (this.e instanceof Shop) {

            }
        }
    }


//    public static EntityListener getInstance() {
//        return instance;
//    }
}
