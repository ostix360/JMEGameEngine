package fr.ostix.game.items;

import com.google.gson.annotations.Expose;
import fr.ostix.game.core.Game;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.gui.GuiTexture;
import fr.ostix.game.gui.MasterGui;
import fr.ostix.game.toolBox.Color;
import org.joml.Vector2f;

import java.util.Objects;

public class Item {

    @Expose
    private final int id;
    private final String name;
    private final String description;
    private final int texture;
    private GuiTexture gui;
    private final GUIText itemDescription;

    private final ItemType type;
    private final int price;
    private final GUIText priceText;



    public Item(int id, String name, String description, int price, String textureName, ItemType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.texture = ResourcePack.getTextureByName(textureName).getID();
        this.itemDescription = new GUIText(description, 1f, Game.gameFont,
                new Vector2f(95, 290f), 280f, false);
        this.type = type;
        this.itemDescription.setColour(Color.MAGENTA);
        this.priceText = new GUIText("Price: " + price, 1f, Game.gameFont,
                new Vector2f(95, 630f), 280f, false);
        this.priceText.setColour(Color.YELLOW);
    }

    public void onItemUse() {
    }

    public GUIText getItemDescription() {
        return itemDescription;
    }

     public GUIText getPriceText() {
        return priceText;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }



    public int getTexture() {
        return texture;
    }

    public void startRendering(float x, float y) {
        gui = new GuiTexture(texture, new Vector2f(x, y), new Vector2f(130 * 1.23f, 130));
        MasterGui.addGui(gui);

    }

    public ItemType getType() {
        return type;
    }

    public void stopRendering() {
        MasterGui.removeGui(gui);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getId() == item.getId() && getType() == item.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType());
    }

    public int getPrice() {
        return price;
    }
}
