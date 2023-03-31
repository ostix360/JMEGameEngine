package fr.ostix.game.menu;

import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.listener.menu.InGameMenuListener;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.entity.Player;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class InGameMenu extends Screen {
    private Player player;
    private int heartTexture;
    private GuiTexture enduranceBar;
    private int enduranceTexture;

    public List<GuiTexture> toRender = new ArrayList<>();
    public List<GUIText> toRenderText = new ArrayList<>();

    private InGameMenuListener listener;

    public InGameMenu() {
        super("My World");

    }

    public void init(Player player) {
        super.init();
        this.player = player;
        heartTexture = ResourcePack.getTextureByName("food").getID();
        enduranceBar = new GuiTexture(ResourcePack.getTextureByName("enduranceBar").getID(), new Vector2f(975, 955), new Vector2f(590, 135));
        enduranceTexture = ResourcePack.getTextureByName("endurance").getID();
        listener = new InGameMenuListener(this);
        EventManager.getInstance().register(listener);
    }


    @Override
    public void update() {
        super.update();
    }

    public void render() {
        for (int i = 0; i < player.getHealth(); i++) {
            MasterGui.addTempGui(new GuiTexture(heartTexture, new Vector2f(250 + i * 60, 970), new Vector2f(100f)));
        }
        float percent = (player.getSprintTime()
                / (60)) * 450;
        MasterGui.addTempGui(new GuiTexture(enduranceTexture, new Vector2f(1110, 985), new Vector2f(percent, 60)));
        MasterGui.addTempGui(enduranceBar);

        MasterGui.addTempGui(toRender.toArray(new GuiTexture[0]));
        toRenderText.forEach(MasterFont::addTempFont);

        toRender.clear();
        toRenderText.clear();
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        EventManager.getInstance().unRegister(listener);
    }
}
