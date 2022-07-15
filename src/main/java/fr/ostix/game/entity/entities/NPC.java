package fr.ostix.game.entity.entities;

import fr.ostix.game.entity.*;
import fr.ostix.game.entity.entities.npc.*;
import fr.ostix.game.graphics.model.*;
import org.joml.*;

import java.util.*;

public class NPC extends Entity {
    private String name;
    private final NPCGui gui;


    public NPC(int id, Model model, Vector3f position, Vector3f rotation, float scale) {
        super(id, model, position, rotation, scale);
        gui = new NPCGui("Talking to " + name, this);
    }

    public void talke(List<String> dialogs) {

    }

    public void talke(String dialog) {

    }
}
