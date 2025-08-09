package io.github.some_example_name.model.shop;


import io.github.some_example_name.model.enums.Quality;
import io.github.some_example_name.model.items.Item;

public class SellItemRequest {
    private final Item item;
    private final int count;
    private final Quality quality;

    public SellItemRequest(Item item, int count, Quality quality) {
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

    public Quality getQuality() {
        return quality;
    }
}
