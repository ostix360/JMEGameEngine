package fr.ostix.game.core.events.listener.player;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.player.*;
import fr.ostix.game.entity.*;
import org.joml.*;
import org.joml.Math;


public class PlayerListener implements Listener {


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        float dx = 0, dy = 0, dz = 0;
        switch (p.getMovement()) {
            case FORWARD:
                dx = (Math.sin(Math.toRadians(p.getRotation().y())));
                dz = (Math.cos(Math.toRadians(p.getRotation().y())));
                break;
            case BACK:
                dx = -(Math.sin(Math.toRadians(p.getRotation().y())));
                dz = -(Math.cos(Math.toRadians(p.getRotation().y())));
                break;
//            case JUMP:
//                e.getPlayer().getControl().jump();
////                dy = 0.5f;
//                break;
            default:
                break;

        }
        e.getPlayer().getControl().setWalkDirection(new Vector3f(dx, 0, dz).mul(30));
//        e.getPlayer().increasePosition(new Vector3f(dx,dy,dz));

    }
}
