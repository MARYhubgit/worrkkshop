package io.github.some_example_name.model.Tools;

import io.github.some_example_name.model.Player.Player;

public class MilkPail extends Tool {

    public MilkPail(String milkPail,char p) {
        super(milkPail,p,0 ,4);
    }

    @Override
    public boolean use(Player player, int x, int y) {
        int energyCost = 4;
        if (player.getEnergy() < energyCost) return false;
        player.decreaseEnergy(energyCost);
        return true;
    }

    @Override
    public boolean canUseOn(int x, int  y) {
        return false;
    }

    @Override
    public Tool upgrade() {
        return null;
    }

    @Override
    public void interact() {

    }

    @Override
    public int getSellPrice() {
        return 500;
    }

    @Override
    public boolean isEdible() {
        return false;
    }
}
