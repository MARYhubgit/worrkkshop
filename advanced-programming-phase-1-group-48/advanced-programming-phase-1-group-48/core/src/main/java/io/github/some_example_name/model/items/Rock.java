package io.github.some_example_name.model.items;


public class Rock extends Item {
    public Rock() {
        super("Rock", 'R');
    }

    @Override
    public void interact() {
        // Todo
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
