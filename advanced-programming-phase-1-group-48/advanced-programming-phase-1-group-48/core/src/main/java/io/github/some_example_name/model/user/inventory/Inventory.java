package io.github.some_example_name.model.user.inventory;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Integer> items = new HashMap<>();
    private Backpack backpack;
    private TrashCan trashCan;

    public Inventory(Backpack backpack, TrashCan trashCan) {
        this.backpack = backpack;
        this.trashCan = trashCan;
    }
    public void addItem(String name, int count) {
        items.put(name, items.getOrDefault(name, 0) + count);
    }

    public boolean hasItem(String name, int count) {
        return items.getOrDefault(name, 0) >= count;
    }

    public void removeItem(String name, int count) {
    }

    public Map<String, Integer> getItems() {
        return items;
    }
}

