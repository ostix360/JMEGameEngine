package fr.ostix.game.entity.entities.npc.gui;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.listener.keyListeners.*;
import fr.ostix.game.core.events.states.*;
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

    public NPCGui(String title, NPC npc) {
        super(title);
        this.npc = npc;
        init();
    }

    @Override
    public void init() {
        background = new GuiTexture(ResourcePack.getTextureByName("dialogs").getID(),new Vector2f(600,500),
                new Vector2f(600,550));
        this.read = new NPCReaderListener(this);
        super.init();
    }

    public void showDialog(String dialog, World world) {
        this.dialogs.clear();
        this.dialogs.add(dialog);

        this.dialog = new GUIText(dialog,1f, Game.gameFont,new Vector2f(600,700),600,true);
        this.dialog.setColour(Color.GRAY);
        this.world = world;
        moveToNPC();
        MasterGui.addGui(background);
        MasterFont.add(this.dialog);
        EventManager.getInstance().register(this.read);
    }

    public void showDialogs(List<String> dialogs, World world) {
        this.dialogs.clear();
        this.dialogs.addAll(dialogs);
        this.world = world;
        EventManager.getInstance().callEvent(new StateOverWorldEvent(this.title,this,2));

        this.dialog = new GUIText(dialogs.get(0),1f, Game.gameFont,new Vector2f(600,700),600,true);
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
        if (indexDialog <= dialogs.size()-1){
            MasterFont.remove(this.dialog);
            this.dialog.setText(dialogs.get(indexDialog));
            MasterFont.add(dialog);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else {
            EventManager.getInstance().unRegister(this.read);
            MasterGui.removeGui(background);
            MasterFont.remove(this.dialog);
            indexDialog = 0;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            EventManager.getInstance().callEvent(new StateOverWorldEvent(States.WORLD.getName(),null,2));
        }
    }
}
