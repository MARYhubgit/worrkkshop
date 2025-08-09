package io.github.some_example_name.model.Tools;


import io.github.some_example_name.model.Player.Player;

public class Shear extends Tool {

    public Shear(String Shear , char p) {

        super(Shear, p, 0, 1000);
    }

    @Override
    public boolean use(Player player, int x , int y) {
        int energyCost = 4;
        if (player.getEnergy() < energyCost) return false;
        player.decreaseEnergy(energyCost);
        return true;
    }

    @Override
    public boolean canUseOn(int x, int y) {
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

