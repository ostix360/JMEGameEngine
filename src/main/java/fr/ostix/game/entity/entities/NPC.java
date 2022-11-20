package fr.ostix.game.entity.entities;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.entity.npc.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.entity.entities.npc.gui.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.world.*;
import org.joml.*;

import java.util.*;

public class NPC extends Entity implements Interact{
    private final String name;
    protected NPCGui gui;

    private String defaultMessage = null;

    private String theDefaultMessage = "debug message";

    protected final Listener NPCDefaultsListener;


    public NPC(int id, Model model, Vector3f position, Vector3f rotation, float scale,String name) {
        super(id, model, position, rotation, scale);
        this.name = name;
        this.registerDefaultDialog();
        gui = new NPCGui("Talking to", this);
        NPCDefaultsListener = new NPCTalkListener(this);
        if (this.getId() == 0) return;
        EventManager.getInstance().register(this.NPCDefaultsListener);
    }

    public void talke(List<String> dialogs,World world) {
        gui.showDialogs(dialogs,world);
//        EventManager.getInstance().unRegister(this.NPCDefaultsListener);
    }

    public void talke(String dialog,World world) {
        if (dialog != null) {
            gui.showDialog(dialog,world);
        }
//        EventManager.getInstance().unRegister(this.NPCDefaultsListener);
//        List<String> dialogs = new ArrayList<String>();
//        dialogs.add("Hello");
//        dialogs.add("I'm your father");
//        dialogs.add("Bie");
//        gui.showDialogs(dialogs,world);
    }

    @Override
    public void interact(World world) {
        EventManager.getInstance().callEvent(new NPCTalkEvent(world, 1, this));
    }

    public String getName() {
        return name;
    }

    public Listener getNPCDefaultsListener() {
        return NPCDefaultsListener;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void unRegisterDefaultDialog() {
        defaultMessage = null;
    }

    public void registerDefaultDialog() {
            defaultMessage = theDefaultMessage;
    }
}
