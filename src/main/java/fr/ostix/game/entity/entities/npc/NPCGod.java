package fr.ostix.game.entity.entities.npc;

import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.entity.EntityCloseEvent;
import fr.ostix.game.entity.entities.NPC;
import fr.ostix.game.entity.entities.npc.gui.NPCGodGui;
import fr.ostix.game.entity.entities.npc.gui.NPCGui;
import fr.ostix.game.world.World;



public class NPCGod extends NPC {
    public static final NPCGod INSTANCE = new NPCGod();

    private NPCGod() {
        super(0,null,null,null,0, "God");
        this.gui = new NPCGodGui();
    }


    public static NPCGod getInstance() {
        return INSTANCE;
    }



    @Override
    public void talke(String dialog, World world) {
        EventManager.getInstance().callEvent(new EntityCloseEvent(null, world, 2));
        super.talke(dialog, world);
    }
}
