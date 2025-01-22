package fr.ostix.game.entity.entities.npc.gui;

import fr.ostix.game.core.logics.Callback;
import fr.ostix.game.entity.Transform;
import fr.ostix.game.entity.entities.npc.NPCGod;
import fr.ostix.game.world.World;

import java.util.List;

public class NPCGodGui extends NPCGui{

    public NPCGodGui() {
        super("God is talking to you...", NPCGod.INSTANCE);
    }

    @Override
    protected void moveToNPC() {
        this.npc = NPCGod.INSTANCE;
        Transform t = (Transform) (world.getPlayer().getTransform().clone());
        this.world.getCamera().goTo(t);
    }

    @Override
    public void showDialogs(List<String> dialogs, int line, World w, Callback<Boolean> callback) {
        super.showDialogs(dialogs, line, w, callback);
    }
}
