package fr.ostix.game.core.events.listener.player;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.player.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.world.*;

import java.util.*;

public class PlayerInteractListener extends PlayerListener{

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
        entities.forEach(entity ->{
            if (entity.getPosition().x() < x + 10 && entity.getPosition().x() > x - 10){
                if (entity.getPosition().z() < z + 10 && entity.getPosition().z() > z - 10){
                    world.getEntitiesNear().add(entity);
                }
            }
        });
    }
}
