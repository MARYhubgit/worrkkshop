package io.github.some_example_name.model.NPC;


import io.github.some_example_name.model.Player.inventory.Inventory;

import java.util.Map;

public class InventoryUtils {
    public static boolean hasItems(Inventory inventory, Map<String, Integer> requiredItems) {
        for (Map.Entry<String, Integer> entry : requiredItems.entrySet()) {
            if (inventory.getItemInstance(entry.getKey())< entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public static void removeItems(Inventory inventory, Map<String, Integer> itemsToRemove) {
        for (Map.Entry<String, Integer> entry : itemsToRemove.entrySet()) {
            inventory.removeItem(entry.getKey(), entry.getValue());
        }
    }
}
