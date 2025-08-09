package io.github.some_example_name.model.user;

public class Wallet {
    private int gold;

    public boolean hasEnough(int amount) {
        return gold >= amount;
    }

    public void deduct(int amount) {
    }

    public void add(int amount) {
        gold += amount;
    }

    public int getGold() {
        return gold;
    }
}
