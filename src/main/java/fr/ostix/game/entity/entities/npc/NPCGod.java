package fr.ostix.game.entity.entities.npc;

import fr.ostix.game.entity.entities.*;
import fr.ostix.game.entity.entities.npc.gui.*;
import fr.ostix.game.world.*;

import java.util.*;

public class NPCGod extends NPC {
    private static final NPCGod INSTANCE = new NPCGod();

    private NPCGod() {
        super(0,null,null,null,0, "God");
        this.gui = new NPCGodGui();
    }


    public static NPCGod getInstance() {
        return INSTANCE;
    }

    public void talke(List<String> dialogs, World world) {
        this.gui.showDialogs(dialogs,world);
//        EventManager.getInstance().unRegister(this.NPCDefaultsListener);
    }

}
