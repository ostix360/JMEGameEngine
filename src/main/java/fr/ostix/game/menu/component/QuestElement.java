package fr.ostix.game.menu.component;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.menu.QuestSelectedEvent;
import fr.ostix.game.core.quest.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.graphics.font.meshCreator.*;
import fr.ostix.game.graphics.font.rendering.*;
import fr.ostix.game.gui.*;
import org.joml.*;
import org.lwjgl.glfw.*;

public class QuestElement extends Component {

    private final QuestCategory quest;
    private static final int TEXTURE = ResourcePack.getTextureByName("questElement").getID();

    private final GUIText questTitle;
    private final GUIText questDescription;

    public QuestElement(float x, float y, float width, float height, QuestCategory quest) {
        super(x, y, width, height, TEXTURE);
        this.quest = quest;
        if (this.quest.getStatus().equals(QuestStatus.AVAILABLE) || this.quest.getStatus().equals(QuestStatus.QUESTING)) {
            this.questTitle = new GUIText(quest.getName(), 1f, Game.gameFont, new Vector2f(x + 50, y + 10), 700, false);
            this.questDescription = new GUIText(quest.getQuestingQuest().getDescription(), 0.7f, Game.gameFont, new Vector2f(x + 50, y + 50), 920f, false);
        }else if (this.quest.getStatus().equals(QuestStatus.DONE)) {
            this.questTitle = new GUIText(quest.getName(), 1f, Game.gameFont, new Vector2f(x + 50, y + 10), 700, false);
            this.questDescription = new GUIText("Deja fait", 0.7f, Game.gameFont, new Vector2f(x + 50, y + 50), 920f, false);
        }else {
            this.questTitle = new GUIText("???", 1.0f, Game.gameFont, new Vector2f(x + 50, y + 10), 920f, false);
            this.questDescription = new GUIText("La vie vous a prepare quelque chose \n" +
                    "Ne vous inquitez pas ces deja determine", 0.7f, Game.gameFont, new Vector2f(x + 50, y + 50), 920f, false);
        }

        this.texture = new GuiTexture(TEXTURE, new Vector2f(x, y), new Vector2f(width, height));
    }


    @Override
    public void render() {

    }

    @Override
    public void init() {
        super.init();
        MasterFont.add(questTitle);
        MasterFont.add(questDescription);
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        MasterFont.remove(questTitle);
        MasterFont.remove(questDescription);
    }

    @Override
    public void update() {
        if (isIn() && Input.keysMouse[GLFW.GLFW_MOUSE_BUTTON_1]) {
//             TODO Burk
            EventManager.getInstance().callEvent(new QuestSelectedEvent(this.quest.getId(),1));
        }
    }
}
