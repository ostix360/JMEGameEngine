package fr.ostix.game.entity.entities.npc.gui;

import fr.ostix.game.entity.*;
import fr.ostix.game.entity.entities.npc.*;

public class NPCGodGui extends NPCGui{

    public NPCGodGui() {
        super("God is talking to you...", NPCGod.getInstance());
    }

    @Override
    protected void moveToNPC() {
        Transform t = (Transform) (world.getPlayer().getTransform().clone());
//        t.getPosition().add(0,10,0);
        this.world.getCamera().goTo(t);
    }
}