package io.github.some_example_name.model.items;

public class SimpleItem extends Item {
    private final int sellPrice;
    private final int energy;
    private final String type;
    private boolean isEdible; // دیگه final نیست


    public SimpleItem(String name, char displayChar, int sellPrice,int energy , String type) {
        super(name, displayChar);
        this.sellPrice = sellPrice;
        this.energy = energy;
        this.type = type;

        this.isEdible = isFoodType(type);
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
        return isEdible;
    }

    private boolean isFoodType(String type) {
        return switch (type.toLowerCase()) {
            case "ingredient", "crop", "vegetable", "fruit", "fungus", "dairy", "forage", "drink", "sap" -> true;
            default -> false;
        };
    }

}
