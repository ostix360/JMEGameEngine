package fr.ostix.game.entity.entities;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.entity.npc.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.player.*;
import fr.ostix.game.core.events.states.*;
import fr.ostix.game.core.logics.Callback;
import fr.ostix.game.entity.*;
import fr.ostix.game.entity.entities.npc.gui.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.world.*;
import org.joml.*;

import java.lang.reflect.Array;
import java.util.*;

public class NPC extends Entity implements Interact {
    private final String name;
    protected NPCGui gui;

    private String defaultMessage = null;

    private String theDefaultMessage = "debug message";

    protected final Listener NPCDefaultsListener;


    public NPC(int id, Model model, Vector3f position, Vector3f rotation, float scale, String name) {
        super(id, model, position, rotation, scale);
        this.name = name;

        gui = new NPCGui("Talking to", this);
        NPCDefaultsListener = new NPCTalkListener(this);
//        if (this.getId() == 0)  ;

    }

    @Override
    public void initBeforeSpawn() {
        this.registerDefaultDialog();
        super.initBeforeSpawn();
    }

    public void talke(List<String> dialogs,int line, World world) {
        gui.showDialogs(dialogs,line, world);
        EventManager.getInstance().callEvent(new StateOverWorldEvent(name, gui, 1));
//        EventManager.getInstance().unRegister(this.NPCDefaultsListener);
    }

    public void talke(World w, Callback<Boolean> callback, Object... args) {
        int line = 0;
        int i = 0;
        List<String> dialogs = new ArrayList<>();
        if (args[i] instanceof Integer){
            line = (int) args[i];
            i++;
        }
        for (; i < args.length; i++) {
            if ((args[i] instanceof String)) {
                dialogs.add((String) args[i]);
                continue;
            }else if (args[i] instanceof Object[]){
                for (Object o : (Object[]) args[i]) {
                    if (o instanceof String){
                        dialogs.add((String) o);
                    }else {
                        throw new IllegalArgumentException("args[" + i + "] is not a String");
                    }
                }
                break;
            }
            throw new IllegalArgumentException("args[" + i + "] is not a String");
        }

        gui.showDialogs(dialogs, line, w,callback);
        EventManager.getInstance().callEvent(new StateOverWorldEvent(name, gui, 1));
    }


    public void talke(String dialog, World world) {
        if (dialog == null) {
            return;
        }
        gui.showDialog(dialog, world);

        EventManager.getInstance().callEvent(new StateOverWorldEvent(name, gui, 1));
//        EventManager.getInstance().unRegister(this.NPCDefaultsListener);
//        List<String> dialogs = new ArrayList<String>();
//        dialogs.add("Hello");
//        dialogs.add("I'm your father");
//        dialogs.add("Bie");
//        gui.showDialogs(dialogs,world);
    }

    @Override
    public void interact(World world) {

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
        EventManager.getInstance().unRegister(this.NPCDefaultsListener);
        defaultMessage = null;
    }

    public void registerDefaultDialog() {
        if (this.getId() == 0) return;
        EventManager.getInstance().register(this.NPCDefaultsListener);
        defaultMessage = theDefaultMessage;
    }

    @Override
    public void cleanUp() {
        EventManager.getInstance().unRegister(this.NPCDefaultsListener);
        super.cleanUp();
    }
}
