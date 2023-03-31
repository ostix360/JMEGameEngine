package fr.ostix.game.core.events.listener;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.entity.EntityInteractEvent;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.entities.Interact;

public class EntityListener implements Listener {
    // private final List<Entity> entities = new ArrayList<>();
    // public static EntityListener instance = new EntityListener();

    protected final Entity e;

    public EntityListener(Entity e) {
        this.e = e;
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent e) {
        if (this.e.equals(e.getEntity())) {
            if (this.e instanceof Interact) {
                ((Interact) this.e).interact(e.getWorld());
            }
        }
    }


//    public static EntityListener getInstance() {
//        return instance;
//    }
}
