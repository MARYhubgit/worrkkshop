package io.github.some_example_name.model.Player;

public class Wallet {
    private int gold;

    public boolean hasEnough(int amount) {
        return gold >= amount;
    }

    public void deduct(int amount) {
        gold -= amount;
    }

    public void add(int amount) {
        gold += amount;
    }

    public int getGold() {
        return gold;
    }
}
