package fr.ostix.game.core.events.listener.player;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.player.*;
import fr.ostix.game.entity.*;

public class PlayerListener implements Listener {


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        switch (p.getMovement()) {
            case FORWARD:
                float dx = (float) (Math.sin(Math.toRadians(p.getRotation().y())));
                float dz = (float) (Math.cos(Math.toRadians(p.getRotation().y())));
                break;
            case BACK:
                dx = -(float) (Math.sin(Math.toRadians(p.getRotation().y())));
                dz = -(float) (Math.cos(Math.toRadians(p.getRotation().y())));
                break;
            default:
                break;

        }
    }
}
