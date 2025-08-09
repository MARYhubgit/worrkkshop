package io.github.some_example_name.model.items;


public class ForagingItem extends Item {
    public ForagingItem() {
        super("ForagingItem", 'F');
    }

    @Override
    public void interact() {
        // TODO
    }

    @Override
    public int getSellPrice() {
        return 0;
    }

    @Override
    public boolean isEdible() {
        return false;
    }
}
