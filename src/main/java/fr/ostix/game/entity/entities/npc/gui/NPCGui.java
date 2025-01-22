package fr.ostix.game.entity.entities.npc.gui;

import fr.ostix.game.core.Game;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.listener.Listener;
import fr.ostix.game.core.events.listener.keyListeners.NPCReaderListener;
import fr.ostix.game.core.events.states.StateOverWorldEvent;
import fr.ostix.game.core.logics.Callback;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.entity.Transform;
import fr.ostix.game.entity.entities.NPC;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.menu.Screen;
import fr.ostix.game.menu.state.States;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.world.World;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class NPCGui extends Screen {

    protected final NPC npc;
    private int indexDialog = 0;

    protected GuiTexture background;
    private GUIText next_dialog;

    protected GUIText dialog;

    protected final List<String> dialogs = new ArrayList<>();

    protected Listener read;

    protected World world;

    protected int startLine = 0;
    private Callback<Boolean> callback;

    public NPCGui(String title, NPC npc) {
        super(title);
        this.npc = npc;
        init();
    }

    @Override
    public void init() {
        background = new GuiTexture(ResourcePack.getTextureByName("dialogs").getID(), new Vector2f(600, 500),
                new Vector2f(600, 550));
        this.next_dialog = new GUIText("Press ENTER to continue", 0.5f, Game.gameFont, new Vector2f(1020, 870), 600, false);
        this.read = new NPCReaderListener(this);
        super.init();
    }

    public void showDialog(String dialog, World world) {
        this.dialogs.clear();
        this.dialogs.add(dialog);
        this.world = world;
    }

    public void showDialogs(List<String> dialogs, int line, World w, Callback<Boolean> callback) {
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
        MasterFont.add(this.next_dialog);


        EventManager.getInstance().register(this.read);
    }

    protected void moveToNPC() {
        Transform t = (Transform) npc.getTransform().clone();
//        t.getPosition().add(10,0,0);
        this.world.getCamera().goTo(t);
    }

    public void next() {
        indexDialog++;

        MasterFont.remove(this.dialog);
        if (indexDialog <= dialogs.size() - 1) {
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
            this.callback.call(true);
            this.cleanUp();
        }
    }

    public void cleanUp() {
        MasterGui.removeGui(background);
        MasterFont.remove(this.dialog);
        MasterFont.remove(this.next_dialog);
        EventManager.getInstance().unRegister(this.read);
        indexDialog = 0;
        super.cleanUp();
    }
}
