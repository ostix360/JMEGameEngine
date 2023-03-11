package fr.ostix.game.core.events.listener.player;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.entity.EntityCloseEvent;
import fr.ostix.game.core.events.player.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.world.*;

import java.util.*;

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
//            if (entity.equals(e.getPlayer())) return; TODO font bugged
            float distance = (float) Math.sqrt(Math.pow(entity.getPosition().x() - x, 2) + Math.pow(entity.getPosition().z() - z, 2));
            if (distance < 5) {
                EventManager.getInstance().callEvent(new EntityCloseEvent(entity, world, 2));
                world.getEntitiesNear().add(entity);
            }
        });
    }
}
