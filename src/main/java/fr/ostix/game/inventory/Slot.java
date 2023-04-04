package fr.ostix.game.inventory;

import fr.ostix.game.core.Game;
import fr.ostix.game.core.Input;
import fr.ostix.game.core.logics.Callback;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.entity.entities.shop.Product;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.gui.GuiLayer;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.items.ItemStack;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.OpenGL.DisplayManager;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class Slot {

    private static final GuiLayer layer =
            new GuiLayer(new Vector2f(0, 0), new Vector2f(1, 1),
                    new Color(0.3f, 0.3f, 0.3f, 0.3f));
    private final float x, y;
    private final float size;
    private final GuiTexture texture;
    private static final GuiTexture itemDescriptionMenu =
            new GuiTexture(ResourcePack.getTextureByName("itemDescription").getID(),
                new Vector2f(25, 230), new Vector2f(400, 500));;
    private boolean isEmpty;
    private ItemStack stack;
    private boolean isIn;

    private GUIText count;

    private Callback<Product> onClick;

    public Slot(float x, float y, float size) {
        this(x, y, size, null);
    }

    public Slot(float x, float y, float size, Callback<Product> onClick) {
        this.x = x;
        this.y = y;
        this.size = size;
        texture = new GuiTexture(ResourcePack.getTextureByName("slot").getID(),
                new Vector2f(x, y), new Vector2f(size * 1.23f, size));
        texture.setLayer(new Color(0.45f, 0.45f, 0.5f, 0.85f));

        this.stack = new ItemStack(null, 0);
        this.onClick = onClick;
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
        if (isIn && Input.keysMouse[GLFW.GLFW_MOUSE_BUTTON_1]) {
            if (onClick != null) {
                onClick.call(new Product(stack, stack.getItem().getPrice()));
                //TODO add confirmation popup to avoid miss click
            }
        }
    }

    public void render() {
        if (isIn && !isEmpty()) {
            MasterGui.addTempGui(itemDescriptionMenu);
            MasterFont.addTempFont(stack.getItem().getItemDescription());
            MasterFont.addTempFont(stack.getItem().getPriceText());
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
