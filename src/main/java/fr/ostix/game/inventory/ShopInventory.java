package fr.ostix.game.inventory;

import fr.ostix.game.core.Game;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.states.StateOverWorldEvent;
import fr.ostix.game.entity.Player;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.items.ItemStack;
import fr.ostix.game.items.ItemType;
import fr.ostix.game.items.Items;
import fr.ostix.game.menu.ingame.CloseChoicePopup;
import fr.ostix.game.toolBox.Color;
import org.joml.Vector2f;

public class ShopInventory extends Inventory {

    private final ItemTab shopTab;
    private Player player;
    private GUIText moneyText;
    private int coin;
    private CloseChoicePopup confirmPopup;


    public ShopInventory() {
        super("Shop");

        shopTab = ItemTab.newEmptyTab("Shop", 35, ItemType.ALL, (p) -> {
            if (p.getPrice() <= 0) return;
            if (p.getPrice() > this.player.getMoney()) return;
            confirmPopup = new CloseChoicePopup("Confirm payment",
                    " Are you sure you want to buy "+ p.getProductStack().getItem().getName() +" ?",
                    (b) -> {
                        this.player.pay(p.getPrice());
                        this.player.getInventory().addItems(p.getProductStack());
                        EventManager.getInstance().callEvent(new StateOverWorldEvent("World", null, 2));
                    },
                    (b) -> EventManager.getInstance().callEvent(new StateOverWorldEvent("World", null, 2)));
            EventManager.getInstance().callEvent(new StateOverWorldEvent("Confirm payment", confirmPopup, 2));
        });
        setItems();
    }

    @Override
    public void init() {
        super.init();
        //coin = ResourcePack.getTexture("coin").getTextureID();
        moneyText = new GUIText(String.valueOf(0), 1, Game.gameFont, new Vector2f(1920 - 100, 25),
                30, false);
        moneyText.setColour(Color.YELLOW);
    }

    private void setItems() {
        this.addItems(new ItemStack(Items.getItem(0), 10));
    }

    @Override
    public void open() {
        super.open();
        shopTab.setItems(itemStackByItem);
        shopTab.startRendering();
    }

    @Override
    public void update() {
        super.update();
        if (isOpen()) {
            shopTab.update();
            moneyText.setText(String.valueOf(player.getMoney()));
        }
    }

    @Override
    public void render() {
        shopTab.render();
        MasterFont.addTempFont(moneyText);
    }

    @Override
    public void close() {
        super.close();
        shopTab.stopRendering();
    }

    @Override
    public void cleanUp() {
        this.close();
        super.cleanUp();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
