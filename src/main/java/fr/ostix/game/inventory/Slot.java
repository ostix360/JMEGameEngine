package fr.ostix.game.inventory;

import fr.ostix.game.core.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.graphics.font.meshCreator.*;
import fr.ostix.game.graphics.font.rendering.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.items.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.toolBox.OpenGL.*;
import org.joml.*;

public class Slot {
    private final float x, y;
    private final float size;
    private final GuiTexture texture;
    private final GuiTexture itemDescriptionMenu;
    private boolean isEmpty;
    private ItemStack stack;
    private boolean isIn;

    private GUIText count;

    public Slot(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
        texture = new GuiTexture(ResourcePack.getTextureByName("slot").getID(),
                new Vector2f(x, y), new Vector2f(size * 1.23f, size));
        texture.setLayer(new Color(0.3f, 0.3f, 0.3f, 0.3f));
        itemDescriptionMenu = new GuiTexture(ResourcePack.getTextureByName("itemDescription").getID(),
                new Vector2f(25, 230), new Vector2f(400, 500));
        this.stack = new ItemStack(null, 0);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }


    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public void update() {
        float mX = (float) Input.getMouseX() / DisplayManager.getWidth() * 1920;
        float mY = (float) Input.getMouseY() / DisplayManager.getHeight() * 1080;

        isIn = mX >= this.x && mY >= this.y &&
                mX < (this.x + this.size) && mY < (this.y + this.size);
        texture.hasLayer(isIn);
    }

    public void render() {
        if (isIn && !isEmpty()) {
            MasterGui.addTempGui(itemDescriptionMenu);
            MasterFont.addTempFont(stack.getItem().getItemDescription());
        }
    }

    public void startRendering() {
        if (!this.getStack().isEmpty()) {
            stack.getItem().startRendering(this.x + 5, this.y + 5);
            count = new GUIText(String.valueOf(stack.getCount()),
                    1f, Game.gameFont,
                    new Vector2f(this.x + this.size, this.y + this.size -40f),
                    3000f, false);
            count.setColour(Color.RED);
            MasterFont.add(count);
        }
        MasterGui.addGui(texture);
    }

    public void stopRendering() {
        if (!this.getStack().isEmpty()) {
            stack.getItem().stopRendering();
            MasterFont.remove(count);
        }
        MasterGui.removeGui(texture);
    }
}
