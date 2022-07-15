package fr.ostix.game.entity.entities.npc;

import fr.ostix.game.entity.entities.*;
import fr.ostix.game.menu.*;

public class NPCGui extends Screen {

    private final NPC npc;

    public NPCGui(String title, NPC npc) {
        super(title);
        this.npc = npc;
    }
}
