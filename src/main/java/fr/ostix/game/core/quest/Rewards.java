package fr.ostix.game.core.quest;

import com.google.gson.annotations.Expose;
import fr.ostix.game.entity.Player;
import fr.ostix.game.items.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Rewards {
    @Expose
    private List<ItemStack> rewardsItems = new ArrayList<>();
    @Expose
    private int moneyAmount = 0;

    public Rewards() {
    }

    public Rewards(List<ItemStack> rewardsItems, int moneyAmount) {
        this.rewardsItems = rewardsItems;
        this.moneyAmount = moneyAmount;
    }

    public List<ItemStack> getRewardsItems() {
        return rewardsItems;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void reward(Player p) {
        p.getInventory().addItems(rewardsItems);
        //TODO add Money
    }
}
