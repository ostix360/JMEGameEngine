package fr.ostix.game.core.events.listener.player;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.entity.EntityCloseEvent;
import fr.ostix.game.core.events.player.PlayerMoveEvent;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.entities.Interact;
import fr.ostix.game.world.World;

import java.util.List;

public class PlayerInteractListener extends PlayerListener {

    private final World world;
    private final List<Entity> entities;

    public PlayerInteractListener(World world, List<Entity> entities) {
        this.world = world;
        this.entities = entities;
    }

    @Override
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        float x = e.getPlayer().getPosition().x();
        float z = e.getPlayer().getPosition().z();
        world.getEntitiesNear().clear();
        entities.forEach(entity -> {
            if (entity.equals(e.getPlayer())) return;
            float distance = (float) Math.sqrt(Math.pow(entity.getPosition().x() - x, 2) + Math.pow(entity.getPosition().z() - z, 2));
            if (distance < 5) {
                world.getEntitiesNear().add(entity);
            }
        });
        if (world.getEntitiesNear().size() > 0 && world.getEntitiesNear().get(0) instanceof Interact) {         // TODO: if get(0) isn't an instance of Interact, check the next one and so on
            EventManager.getInstance().callEvent(new EntityCloseEvent(world.getEntitiesNear().get(0), world, 2));
        }else{
            EventManager.getInstance().callEvent(new EntityCloseEvent(null, world, 2));
        }
    }
}
