package fr.ostix.game.entity.entities.npc;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.states.*;
import fr.ostix.game.entity.entities.*;
import fr.ostix.game.entity.entities.npc.gui.*;
import fr.ostix.game.world.*;

import java.util.*;

public class NPCGod extends NPC {
    private static final NPCGod INSTANCE = new NPCGod();

    private NPCGod() {
        super(0,null,null,null,0, "God");
        this.gui = new NPCGodGui();
        this.initBeforeSpawn();
    }


    public static NPCGod getInstance() {
        return INSTANCE;
    }

    public void talke(List<String> dialogs,int line, World world) {
        this.gui.showDialogs(dialogs,line,world);
        EventManager.getInstance().callEvent(new StateOverWorldEvent(this.getName(),gui, 1));
//        EventManager.getInstance().unRegister(this.NPCDefaultsListener);
    }

}
