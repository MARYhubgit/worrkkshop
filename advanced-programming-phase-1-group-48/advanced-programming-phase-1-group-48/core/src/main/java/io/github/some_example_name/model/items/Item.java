package io.github.some_example_name.model.items;

public abstract class Item {
    private String name;
    private char displayChar;

    public Item(String name, char displayChar) {
        this.name = name;
        this.displayChar = displayChar;
    }

    public String getName() {
        return name;
    }

    public char getDisplayChar() {
        return displayChar;
    }

    public abstract void interact();

    public abstract int getSellPrice();

    public abstract boolean isEdible();



}
