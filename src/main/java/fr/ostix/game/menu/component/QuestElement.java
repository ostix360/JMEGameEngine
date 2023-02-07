package fr.ostix.game.menu.component;

import fr.ostix.game.core.*;
import fr.ostix.game.core.quest.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.graphics.font.meshCreator.*;
import fr.ostix.game.graphics.font.rendering.*;
import fr.ostix.game.gui.*;
import org.joml.*;
import org.lwjgl.glfw.*;

public class QuestElement extends Component {

    private final QuestCategory quest;
    private static final int TEXTURE = ResourcePack.getTextureByName("quest_element").getID();

    private final GUIText questTitle;
    private final GUIText questDescription;

    public QuestElement(float x, float y, float width, float height, QuestCategory quest) {
        super(x, y, width, height, TEXTURE);
        this.quest = quest;
        if (this.quest.getStatus().equals(QuestStatus.AVAILABLE)) {
            this.questTitle = new GUIText(quest.getName(), 1.3f, Game.gameFont, new Vector2f(x + 20, y + 20), 1f, true);
            this.questDescription = new GUIText(quest.getQuestingQuest().getDescription(), 1, Game.gameFont, new Vector2f(x + 50, y + 50), 0.5f, true);
        } else {
            this.questTitle = new GUIText("????????????", 1.3f, Game.gameFont, new Vector2f(x + 20, y + 20), 1f, true);
            this.questDescription = new GUIText("La vie vous a prepare quelque chose \n" +
                    "Ne vous inquitez pas ces deja determine", 1, Game.gameFont, new Vector2f(x + 50, y + 50), 0.5f, true);
        }

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
            QuestManager.INSTANCE.addToQuesting(this.quest.getId());
        }
    }
}
