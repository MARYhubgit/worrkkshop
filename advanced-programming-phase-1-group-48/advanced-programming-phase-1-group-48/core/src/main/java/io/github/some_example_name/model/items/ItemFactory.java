package io.github.some_example_name.model.items;


import io.github.some_example_name.model.Tools.*;
import io.github.some_example_name.model.cook.Food;
import io.github.some_example_name.model.cook.FoodRecipe;
import io.github.some_example_name.model.cook.Ingredient;
import io.github.some_example_name.model.enums.*;
import io.github.some_example_name.model.Player.inventory.Inventory;

import java.util.*;

public class ItemFactory {

    public static Item createItem(String name, Inventory inventory) {
        if (inventory.getRemainingCapacity() == 0) return null;

        name = name.trim().toLowerCase();

        // Hoe
        for (HoeType hoeType : HoeType.values()) {
            if (hoeType.getName().equalsIgnoreCase(name)) {
                Hoe hoe = new Hoe(hoeType);
                inventory.addItem(hoe);
                return hoe;
            }
        }

        // Pickaxe
        for (PickaxeType pickaxeType : PickaxeType.values()) {
            if (pickaxeType.getName().equalsIgnoreCase(name)) {
                Pickaxe pickaxe = new Pickaxe(pickaxeType);
                inventory.addItem(pickaxe);
                return pickaxe;
            }
        }

        // Axe
        for (AxeType axeType : AxeType.values()) {
            if (axeType.getName().equalsIgnoreCase(name)) {
                Axe axe = new Axe(axeType);
                inventory.addItem(axe);
                return axe;
            }
        }

        // Watering Can
        for (WateringCanType canType : WateringCanType.values()) {
            if (canType.getName().equalsIgnoreCase(name)) {
                WateringCan can = new WateringCan(canType);
                inventory.addItem(can);
                return can;
            }
        }

        // Scythe
        if (name.equals("scythe")) {
            Scythe scythe = new Scythe("scythe", 'S', 0, 2);
            inventory.addItem(scythe);
            return scythe;
        }

        // Fishing Pole
        for (FishingPoleType type : FishingPoleType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                FishingPole pole = new FishingPole(type);
                inventory.addItem(pole);
                return pole;
            }
        }

        // غذاها
        for (FoodRecipe recipe : FoodRecipe.values()) {
            if (recipe.getName().equalsIgnoreCase(name)) {
                Food food = new Food(recipe);
                inventory.addItem(food);
                return food;
            }
        }

        // منابع
        switch (name) {
            case "wood":
                return addAndReturn(new Resource("Wood", 'W', 10), inventory);
            case "copper_ore":
                return addAndReturn(new Resource("Copper Ore", 'C', 75), inventory);
            case "iron_ore":
                return addAndReturn(new Resource("Iron Ore", 'I', 150), inventory);
            case "gold_ore":
                return addAndReturn(new Resource("Gold Ore", 'G', 400), inventory);
            case "coal":
                return addAndReturn(new Resource("Coal", 'L', 150), inventory);
            case "rock":
                return addAndReturn(new Resource("Rock", 'R', 150), inventory);
        }

        for (SimpleItemInfo info : SimpleItemInfo.values()) {
            if (info.getName().equalsIgnoreCase(name)) {
                SimpleItem item = new SimpleItem(
                    info.getName(),
                    info.getDisplayChar(),
                    info.getSellPrice(),
                    info.getEnergy(),
                    info.getType()
                );
                Inventory.itemInstances.put(item.getName().toLowerCase(), item); // 🟢 اضافه کن
                return addAndReturn(item, inventory);
            }
        }


        switch (name) {
            case "milk_pail":
                return addAndReturn(new MilkPail("Milk_Pail", 'P'), inventory);
            case "shear":
                return addAndReturn(new Shear("Shear", 'S'), inventory);
        }

        throw new IllegalArgumentException("Unknown item: " + name);
    }

    private static Item addAndReturn(Item item, Inventory inventory) {
        inventory.addItem(item);
        return item;
    }
}
