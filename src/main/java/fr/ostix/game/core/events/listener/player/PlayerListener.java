package fr.ostix.game.core.events.listener.player;

import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.listener.Listener;
import fr.ostix.game.core.events.player.PlayerMoveEvent;
import fr.ostix.game.entity.Player;
import org.joml.Math;
import org.joml.Vector3f;


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
        e.getPlayer().getControl().setWalkDirection(new Vector3f(dx, 0, dz).mul(15));
//        e.getPlayer().increasePosition(new Vector3f(dx,dy,dz));

    }
}
