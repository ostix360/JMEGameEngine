package fr.ostix.game.menu;

import fr.ostix.game.menu.component.CreateNewWorld;
import fr.ostix.game.menu.component.HorizontalButton;
import fr.ostix.game.menu.component.WorldSave;
import fr.ostix.game.toolBox.ToolDirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorldChooserMenu extends Screen {

    private final List<HorizontalButton> saves = new ArrayList<>();


    public WorldChooserMenu(String title) {
        super(title);
    }

    @Override
    public void init() {
        super.init();
        for (int i = 1; i < 3; i++) {
            saves.add(new CreateNewWorld(200, 200 * i + 50, 600, 200));
        }
        this.findSaves();
    }

    private void findSaves() {
        File saveFolder = new File(ToolDirectory.RES_FOLDER + "/saves");
        File[] savesFolders = saveFolder.listFiles();
        if (Objects.requireNonNull(savesFolders).length == 0) {
            return;
        }
        for (int i = 1; i <= savesFolders.length; i++) {
            File file = savesFolders[i];
            if (file.isDirectory()) {
                saves.add(new WorldSave(200, 200 * i + 50, 600, 200, file.getName()));
            }
        }
    }
}
