package io.github.some_example_name.model.shop;


import io.github.some_example_name.model.enums.Quality;
import io.github.some_example_name.model.items.Item;

public class InventoryItem {
    private Item item;
    private int count;
    private Quality quality;

    public InventoryItem(Item item, int count, Quality quality) {
        this.item = item;
        this.count = count;
        this.quality = quality;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void increase(int amount) {
        this.count += amount;
    }

    public void decrease(int amount) {
        if (amount > count) throw new IllegalArgumentException("not enough");
        this.count -= amount;
    }

    public Quality getQuality() {
        return quality;
    }

    public int getEffectiveSellPrice() {
        return 0;
    }

    @Override
    public String toString() {
        return item.getName() + " ×" + count + " (" + quality.name() + ")";
    }
}
