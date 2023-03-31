package fr.ostix.game.entity.entities.npc;

import fr.ostix.game.entity.entities.NPC;
import fr.ostix.game.entity.entities.npc.gui.NPCGodGui;
import fr.ostix.game.world.World;

public class NPCGod extends NPC {
    private static final NPCGod INSTANCE = new NPCGod();

    private NPCGod() {
        super(0,null,null,null,0, "God");
        this.gui = new NPCGodGui();
    }


    public static NPCGod getInstance() {
        return INSTANCE;
    }



    @Override
    public void talke(String dialog, World world) {
        super.talke(dialog, world);
    }
}
