package io.github.some_example_name.model.Player.inventory;

import io.github.some_example_name.model.items.Item;

import java.util.HashMap;
import java.util.Map;

public class Refrigerator {
    private Map<String, Integer> foodItems = new HashMap<>();

    public boolean addItem(Item item) {
        if (!item.isEdible()) return false;
        String name = item.getName().toLowerCase();
        foodItems.put(name, foodItems.getOrDefault(name, 0) + 1);
        return true;
    }

    public boolean removeItem(String name, int quantity) {
        name = name.toLowerCase();
        if (!foodItems.containsKey(name)) return false;
        int qty = foodItems.get(name);
        if (qty <= 1) {
            foodItems.remove(name);
        } else {
            foodItems.put(name, qty - 1);
        }
        return true;
    }

    public int getItemCount(String name) {
        return foodItems.getOrDefault(name.toLowerCase(), 0);
    }

    public boolean hasItem(String name) {
        return foodItems.containsKey(name.toLowerCase());
    }

    public Map<String, Integer> getItems() {
        return new HashMap<>(foodItems);
    }
}
