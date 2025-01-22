package fr.ostix.game.entity.entities;

import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.listener.Listener;
import fr.ostix.game.core.events.listener.NPCTalkListener;
import fr.ostix.game.core.events.states.StateOverWorldEvent;
import fr.ostix.game.core.logics.Callback;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.entities.npc.gui.NPCGui;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.world.World;
import org.joml.Vector3f;

import java.util.Arrays;

public class NPC extends Entity implements Interact {
    private final String name;
    protected NPCGui gui;

    private String defaultMessage = null;

    private String theDefaultMessage = "Hello my friend!!";

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

    public void talke(World w, int line, Callback<Boolean> callback, String... dialogs) {
        gui.showDialogs(Arrays.asList(dialogs), line, w, callback);

        EventManager.getInstance().callEvent(new StateOverWorldEvent(name, gui, 1));
    }

    public void talke(World world, Callback<Boolean> callback, String... dialogs) {
        this.talke(world, 0, callback, dialogs);
    }

    public void talke(World world, String... dialogs) {
        this.talke(world, null, dialogs);
    }


    public void talke(String dialog, World world) {
        if (dialog == null) {
            return;
        }
        EventManager.getInstance().callEvent(new StateOverWorldEvent(name, gui, 1));
        gui.showDialog(dialog, world);

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
