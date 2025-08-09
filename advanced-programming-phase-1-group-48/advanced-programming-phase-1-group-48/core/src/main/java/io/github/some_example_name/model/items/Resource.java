package io.github.some_example_name.model.items;

public class Resource extends Item {
    private int sellPrice;
    public Resource(String name, char displayChar, int sellPrice) {
        super(name, displayChar);
        this.sellPrice = sellPrice;
    }

    @Override
    public void interact() {
    }

    @Override
    public int getSellPrice() {
        return sellPrice;
    }

    @Override
    public boolean isEdible() {
        return false;
    }
}
