package fr.ostix.game.entity.entities.npc.gui;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.listener.keyListeners.*;
import fr.ostix.game.core.events.states.*;
import fr.ostix.game.core.logics.Callback;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.entity.entities.*;
import fr.ostix.game.graphics.font.meshCreator.*;
import fr.ostix.game.graphics.font.rendering.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.menu.*;
import fr.ostix.game.menu.stat.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.world.*;
import org.joml.*;

import java.util.*;

public class NPCGui extends Screen {

    protected final NPC npc;
    private int indexDialog = 0;

    protected GuiTexture background;

    protected GUIText dialog;

    protected final List<String> dialogs = new ArrayList<>();

    protected Listener read;

    protected World world;

    protected int startLine = 0;
    private Callback callback;

    public NPCGui(String title, NPC npc) {
        super(title);
        this.npc = npc;
        init();
    }

    @Override
    public void init() {
        background = new GuiTexture(ResourcePack.getTextureByName("dialogs").getID(), new Vector2f(600, 500),
                new Vector2f(600, 550));
        this.read = new NPCReaderListener(this);
        super.init();
    }

    public void showDialog(String dialog, World world) {
        this.dialogs.clear();
        this.dialogs.add(dialog);
        this.world = world;
    }

    public void showDialogs(List<String> dialogs, int line, World w, Callback callback) {
        this.dialogs.clear();
        this.dialogs.addAll(dialogs);
        this.world = w;
        this.startLine = line;
        this.callback = callback;
    }

    public void showDialogs(List<String> dialogs, int line, World world) {
        this.dialogs.clear();
        this.dialogs.addAll(dialogs);
        this.world = world;
        this.startLine = line;
    }

    @Override
    public void open() {

        if (dialogs.size() <= startLine) {
            System.err.println("No dialog to show");
            return;
        }

        this.dialog = new GUIText(dialogs.get(startLine), 1f, Game.gameFont, new Vector2f(600, 700), 600, true);
        this.dialog.setColour(Color.GRAY);

        moveToNPC();
        MasterGui.addGui(background);
        MasterFont.add(this.dialog);


        EventManager.getInstance().register(this.read);
    }

    protected void moveToNPC() {
        Transform t = (Transform) npc.getTransform().clone();
//        t.getPosition().add(10,0,0);
        this.world.getCamera().goTo(t);
    }

    public void next() {
        indexDialog++;
        if (indexDialog <= dialogs.size() - 1) {
            MasterFont.remove(this.dialog);
            this.dialog.setText(dialogs.get(indexDialog));
            MasterFont.add(dialog);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            EventManager.getInstance().unRegister(this.read);
            indexDialog = 0;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            EventManager.getInstance().callEvent(new StateOverWorldEvent(States.WORLD.getName(), null, 2));
            if (callback == null) {
                return;
            }
            this.callback.call();
        }
    }

    public void cleanUp() {
        MasterGui.removeGui(background);
        MasterFont.remove(this.dialog);
        super.cleanUp();
    }


}
