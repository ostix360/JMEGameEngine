package fr.ostix.game.menu;

import fr.ostix.game.core.Game;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.listener.menu.QuestHandlerMenuListener;
import fr.ostix.game.core.quest.QuestCategory;
import fr.ostix.game.core.quest.QuestManager;
import fr.ostix.game.core.quest.QuestStatus;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.menu.component.QuestElement;
import org.joml.Vector2f;

public class QuestHandlerMenu extends Screen {
    public final QuestManager questManager;

    private GuiTexture background;
    private GUIText mainTitle;
    private GuiTexture selectedQuest;
    private QuestHandlerMenuListener listener;

    private boolean alreadyInit = false;

    private static boolean opened = false;

    public QuestHandlerMenu() {
        super("Quest Handler Menu");
        this.questManager = QuestManager.INSTANCE;
    }

    @Override
    public void init() {
        if (!alreadyInit) {
            background = new GuiTexture(ResourcePack.getTextureByName("questHandlerBG").getID(), new Vector2f(10, 10), new Vector2f(1900, 1060));
            mainTitle = new GUIText("Quests", 1, Game.gameFont, new Vector2f(920, 20), 700f, false);
            selectedQuest = new GuiTexture(ResourcePack.getTextureByName("point").getID(), new Vector2f(100, 100), new Vector2f(10, 10));
            this.listener = new QuestHandlerMenuListener(this);
            alreadyInit = true;
        }
        super.init();
    }

    private void addQuested() {
        int i = 0;
        for (QuestCategory quest : questManager.getQuests().values()) {
            addComponent(new QuestElement(100, 100 + i * 155, 500, 150, quest));
            if (quest.getStatus().equals(QuestStatus.QUESTING)) {
                selectedQuest.setPosition(new Vector2f(105, 175 + i * 155));
            }
            i++;

        }
    }


    public void notifyQuestSelected(int questID) {
        int i = 0;
        for (QuestCategory quest : questManager.getQuests().values()) {
            if (quest.getId() == questID) {
                selectedQuest.setPosition(new Vector2f(105, 175 + i * 155));
                return;
            }
            i++;
        }
    }

    public void open() {
        if (isOpened()) {
            return;
        }
        MasterGui.addGui(background);
        MasterFont.add(mainTitle);
        addQuested();
        MasterGui.addGui(selectedQuest);
        EventManager.getInstance().register(listener);
        opened = true;
    }

    public void close() {
        MasterGui.removeGui(background, selectedQuest);
        MasterFont.remove(mainTitle);
        removeQuested();
        EventManager.getInstance().unRegister(listener);
        opened = false;

    }

    public static boolean isOpened() {
        return opened;
    }

    private void removeQuested() {
        this.cleanUp();
    }
}
