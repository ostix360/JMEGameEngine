package fr.ostix.game.menu.ingame;

import fr.ostix.game.core.Game;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.menu.Screen;
import fr.ostix.game.menu.component.Button;
import fr.ostix.game.menu.component.HorizontalButton;
import org.joml.Vector2f;

public class CloseChoicePopup extends Screen {

    private final Button yes;
    private final Button no;

    private final GuiTexture background;
    private final GUIText question;

    public CloseChoicePopup(String title, String question, Button.IPressable yesCallback, Button.IPressable noCallback) {
        super(title);
        yes = new HorizontalButton(1100,800,200,120,
                ResourcePack.getTextureByName("yes").getID(), yesCallback);
        no = new HorizontalButton(460,800,200,120,
                ResourcePack.getTextureByName("no").getID(), noCallback);
        background = new GuiTexture(ResourcePack.getTextureByName("closeChoiceBG").getID(),
                new Vector2f(540, 250),new Vector2f(700, 550));
        this.question = new GUIText(question, 1, Game.gameFont, new Vector2f(700, 500),
                600, false);
    }

    @Override
    public void init() {
        super.init();
        MasterGui.addGui(background);
        MasterFont.add(question);
    }

    @Override
    public void open() {
        this.addComponent(yes);
        this.addComponent(no);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        MasterGui.removeGui(background);
        MasterFont.remove(question);
    }
}
