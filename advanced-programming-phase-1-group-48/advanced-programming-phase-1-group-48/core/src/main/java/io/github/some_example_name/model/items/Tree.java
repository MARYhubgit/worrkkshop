package io.github.some_example_name.model.items;

public class Tree extends Item {
    public Tree() {
        super("Tree", 'T');
    }

    @Override
    public void interact() {
        //todo
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

