package io.github.some_example_name.controllers;



import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Tools.Tool;
import io.github.some_example_name.model.items.Item;
import io.github.some_example_name.model.Player.inventory.Inventory;

import java.util.Map;
import java.util.Set;

public class ToolController {
    public static String handleEquip(String toolName, Player player) {
        Inventory inv = player.getInventory();
        if (!inv.hasItem(toolName)) {
            return (toolName + " not in Backpack");
        }
        Item item = Inventory.itemInstances.get(toolName.toLowerCase());
        if (item == null || !(item instanceof Tool)) {
            return ("Tool instance not found or is not a Tool.");
        }
        Tool tool = (Tool) item;
        player.setEquippedTool(tool);
        return (toolName + " equipped visually and logically.");
    }

    public static void handleUse(int x, int y,Player player) {
        Tool currentTool = (Tool) player.getEquippedItem();
        currentTool.use(player, x, y);
    }

    public static String showCurrentTool(Player player) {
        Item equipped = player.getEquippedItem();
        if (equipped == null || !(equipped instanceof Tool)) {
            return "You have no tool equipped!";
        } else {
            Tool currentTool = (Tool) equipped;
            return "Current tool: " + currentTool.getName();
        }
    }
    public static String showAvailableTools(Player player) {
        Inventory inventory = player.getInventory();
        Set<String> itemNames = inventory.getItemNames();

        String[] toolKeywords = {"axe", "pickaxe", "hoe", "watering", "fishing", "scythe",
            "milk", "shear", "trash"};

        StringBuilder sb = new StringBuilder();
        boolean foundTool = false;

        for (String name : itemNames) {
            for (String keyword : toolKeywords) {
                if (name.toLowerCase().contains(keyword)) {
                    sb.append("- ").append(name).append("\n");
                    foundTool = true;
                    break;
                }
            }
        }

        if (!foundTool) {
            return "No tools found in your inventory.";
        }

        return sb.toString().trim();
    }
    public static String showInventory(Player player) {
        Inventory inventory = player.getInventory();
        Map<String, Integer> items = inventory.getItems();
        if (items.isEmpty()) {
            return ("Your inventory is empty.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Your capacity : "+ inventory.getCapacity()+"\n");
        sb.append("Your inventory contains:\n");

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            sb.append("- ").append(itemName).append(": ").append(quantity).append("\n");
        }

        return sb.toString();
    }
    public static String trashItem(Player player, String itemName, Integer optionalCount) {
        Inventory inventory = player.getInventory();

        if (!inventory.hasItem(itemName)) {
            return "Item not found in inventory: " + itemName;
        }

        Item prototype = Inventory.itemInstances.get(itemName.toLowerCase());
        if (prototype == null) {
            return "Unknown item: " + itemName;
        }

        int quantityToRemove = optionalCount != null
            ? Math.min(optionalCount, inventory.getItemInstance(itemName))
            : inventory.getItemInstance(itemName);

        return inventory.removeItemWithTrash(itemName, quantityToRemove, prototype,player);
    }
}
