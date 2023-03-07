package fr.ostix.game.menu;

import fr.ostix.game.core.*;
import fr.ostix.game.core.quest.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.graphics.font.meshCreator.*;
import fr.ostix.game.graphics.font.rendering.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.menu.component.*;
import org.joml.*;

public class QuestHandlerMenu extends Screen {
    public final QuestManager questManager;

    private GuiTexture background;
    private GUIText mainTitle;
    private GuiTexture selectedQuest;

    private boolean alreadyInit = false;

    private static boolean opened = false;

    public QuestHandlerMenu() {
        super("Quest Handler Menu");
        this.questManager = QuestManager.INSTANCE;
    }

    @Override
    public void init() {
        if (!alreadyInit) {
            background = new GuiTexture(ResourcePack.getTextureByName("questHandlerBG").getID(), new Vector2f(0, 0), new Vector2f(100, 1));
            mainTitle = new GUIText("Quests", 1, Game.gameFont, new Vector2f(400, 20), 700f, true);
            selectedQuest = new GuiTexture(ResourcePack.getTextureByName("point").getID(), new Vector2f(100, 100), new Vector2f(100, 100));
            alreadyInit = true;
        }
        super.init();
    }

    private void addQuested() {
        int i = 0;
        for (QuestCategory quest : questManager.getQuests().values()) {
            addComponent(new QuestElement(100, 100 + i * 100, 500, 100, quest));
            if (quest.getStatus().equals(QuestStatus.QUESTING)) {
                selectedQuest.setPosition(new Vector2f(100, 400 + i * 100));
            }
            i++;

        }
    }


    public void notifyQuestSelected(int questID) {
        int i = 0;
        for (QuestCategory quest : questManager.getQuests().values()) {
            if (quest.getId() == questID) {
                selectedQuest.setPosition(new Vector2f(100, 400 + i * 100));
                return;
            }
            i++;
        }
    }

    public void open() {
        if (isOpened()) {
            return;
        }
        MasterGui.addGui(background, selectedQuest);
        MasterFont.add(mainTitle);
        addQuested();
        opened = true;
    }

    public void close() {
        MasterGui.removeGui(background, selectedQuest);
        MasterFont.remove(mainTitle);
        removeQuested();
        opened = false;

    }

    public static boolean isOpened() {
        return opened;
    }

    private void removeQuested() {
        this.cleanUp();
    }
}
