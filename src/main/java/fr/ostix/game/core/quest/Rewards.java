package fr.ostix.game.core.quest;

import fr.ostix.game.items.*;

import java.util.*;

public class Rewards {
    private List<ItemStack> rewardsItems = new ArrayList<>();
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
}
