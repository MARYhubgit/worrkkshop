package io.github.some_example_name.model.Player.inventory;


import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.items.Item;
import io.github.some_example_name.views.Graphic.InventoryView;

import java.util.*;

public class Inventory {
    private Map<String,Integer> items = new HashMap<>();
    public static Map<String, Item> itemInstances = new HashMap<>();
    private Backpack backpack;
    private TrashCan trashCan;

    public Inventory(Backpack backpack, TrashCan trashCan) {
        this.backpack = backpack;
        this.trashCan = trashCan;
    }
    public int getCapacity() {
        return backpack.getCapacity();
    }
    public Result addItem(Item item) {
        String name = item.getName().toLowerCase();
        if (!items.containsKey(name) && items.size() >= backpack.getCapacity()) {
            return Result.failure("Backpack is full! Cannot add new item type: " + item.getName());
        }
        items.put(name, items.getOrDefault(name, 0) + 1);
        itemInstances.put(name, item);
        return Result.success(item.getName() + " added successfully. Quantity: " + items.get(name));
    }

    public boolean hasItem(String name) {
        return items.containsKey(name.toLowerCase());
    }


    public boolean hasItem(String name, int quantity) {
        return items.containsKey(name.toLowerCase()) && quantity <= items.getOrDefault(name.toLowerCase(),0);
    }

    public int  getItemInstance(String name) {
        return items.getOrDefault(name.toLowerCase(), 0);
    }

    public void removeItem(String name, int quantity) {
        String key = name.toLowerCase();
        if (!items.containsKey(key)) return;

        int currentQuantity = items.get(key);
        if (quantity >= currentQuantity) {
            items.remove(key);
        } else {
            items.put(key, currentQuantity - quantity);
        }
    }

    public String removeItemWithTrash(String name, int qty, Item prototype,Player player) {
        String key = name.toLowerCase();
        Integer current = items.get(key);
        if (current == null) {
            return ("Item not found in inventory: " + name);
        }
        int removeQty = Math.min(qty, current);
        double refundRate = trashCan.getType().getRefundRate();
        int refund = (int) Math.round(prototype.getSellPrice() * refundRate) * removeQty;
        int remaining = current - removeQty;
        if (remaining > 0) {
            items.put(key, remaining);
        } else {
            items.remove(key);
        }
        player.addMoney(refund);
        return ("Removed " + removeQty + " x " + name + ". You got " + refund + " coins back.");
    }


    public Map<String, Integer> getItems() {
        return new HashMap<>(items);
    }

    public int usedSlots() {
        return items.size();
    }

    public int getRemainingCapacity() {
        return backpack.getCapacity() - usedSlots();
    }

    public Set<String> getItemNames() {
        return items.keySet();
    }
    public boolean canAcceptNewItem(String itemName) {
        itemName = itemName.toLowerCase();
        return items.containsKey(itemName) || items.size() < backpack.getCapacity();
    }


}
