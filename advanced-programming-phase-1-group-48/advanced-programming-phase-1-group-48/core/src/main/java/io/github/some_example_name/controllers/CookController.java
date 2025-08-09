package io.github.some_example_name.controllers;

import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Player.inventory.Inventory;
import io.github.some_example_name.model.Player.inventory.Refrigerator;
import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.cook.Buff;
import io.github.some_example_name.model.cook.Food;
import io.github.some_example_name.model.cook.FoodRecipe;
import io.github.some_example_name.model.cook.Ingredient;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.items.Item;
import io.github.some_example_name.views.Graphic.GameHUD;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CookController {
    public static String handleRefrigeratorCommand(Player player, String action, String itemName) {
        Game game = GameManager.getCurrentGame();
        Inventory inventory = player.getInventory();
        Refrigerator refrigerator = player.getRefrigerator();

        if (action.equalsIgnoreCase("put")) {
            if (!inventory.hasItem(itemName)) {
                return message("You don't have " + itemName + " in your inventory.");
            }
            Item item = Inventory.itemInstances.get(itemName.toLowerCase());
            if (item == null || !item.isEdible()) {
                return message(itemName + " is not edible and cannot be put in the refrigerator.");
            }
            refrigerator.addItem(item);
            inventory.removeItem(itemName, 1);
            return message(itemName + " has been placed in the refrigerator.");
        } else if (action.equalsIgnoreCase("pick")) {
            if (!refrigerator.hasItem(itemName)) {
                return message("No such item in the refrigerator.");
            }
            Item item = Inventory.itemInstances.get(itemName.toLowerCase());
            Result result = inventory.addItem(item);
            if (!result.success()) {
                return message("Backpack is full. Cannot pick up item.");
            }
            refrigerator.removeItem(itemName, 1);
            return message("Picked " + itemName + " from the refrigerator.");
        } else {
            return message("Invalid action. Use 'put' or 'pick'.");
        }
    }

    public static String prepareFood(Player player, String recipeName) {
        FoodRecipe recipe;
        try {
            recipe = FoodRecipe.valueOf(recipeName.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            return message("No such recipe exists.");
        }

        if (!player.knowsRecipe(recipe)) {
            return message("You haven't learned this recipe yet.");
        }

        Map<Ingredient, Integer> ingredients = recipe.getIngredients();
        Map<Ingredient, Integer> fromInventory = new HashMap<>();
        Map<Ingredient, Integer> fromFridge = new HashMap<>();

        for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
            Ingredient ing = entry.getKey();
            int required = entry.getValue();
            int inInv = player.getInventory().getItemInstance(ing.name());
            int inFridge = player.getRefrigerator().getItemCount(ing.name());

            if (inInv + inFridge < required) {
                return message("Not enough ingredients to cook " + recipe.getName());
            }

            int fromInv = Math.min(required, inInv);
            int fromRef = required - fromInv;

            if (fromInv > 0) fromInventory.put(ing, fromInv);
            if (fromRef > 0) fromFridge.put(ing, fromRef);
        }

        int energy = player.getEnergy();
        if (energy < 3) {
            return message("Not enough energy to cook.");
        }

        player.decreaseEnergy(3);

        Food food = new Food(recipe);
        Result result = player.getInventory().addItem(food);
        if (!result.success()) {
            return message("Inventory is full.");
        }

        fromInventory.forEach((i, qty) -> player.getInventory().removeItem(i.name(), qty));
        fromFridge.forEach((i, qty) -> player.getRefrigerator().removeItem(i.name(), qty));

        return message("Cooked " + recipe.getName() + " successfully!");
    }

    public static String eat(Player player, String foodName) {
        Game game = GameManager.getCurrentGame();
        long currentHour = game.getCurrentHour();
        String key = foodName.toLowerCase();
        Inventory inv = player.getInventory();

        if (!inv.hasItem(key)) return message("You don't have " + foodName + " in your inventory.");
        Item item = Inventory.itemInstances.get(key);
        if (!(item instanceof Food)) return message("This item is not edible.");

        Food food = (Food) item;
        inv.removeItem(key, 1);

        player.increaseEnergy(food.getEnergyRestoration());
        String newBuffStr = food.getBuff();
        if (newBuffStr != null) {
            Buff newBuff = Buff.parseBuff(newBuffStr, currentHour);
            player.setActiveBuff(newBuff);
            if (newBuff.getType() == Buff.Type.ENERGY_BOOST) {
                player.setEnergy(player.getMaxEnergy() + newBuff.getAmount());
            }
            return message("You gained a buff: " + newBuff.getType() + " for " + newBuff.getDurationInHours() + " hours.");
        } else {
            player.setActiveBuff(null);
            return message("You ate " + food.getName() + ". Energy restored: " + food.getEnergyRestoration());
        }
    }

    public static String showLearnedRecipes(Player player) {
        if (player.getLearnedRecipes().isEmpty()) {
            return message("You haven't learned any recipes yet.");
        }
        String list = player.getLearnedRecipes().stream()
            .map(FoodRecipe::getName)
            .collect(Collectors.joining(", "));
        return message("Recipes: " + list);
    }

    private static String message(String msg) {
        if (GameHUD.showMessage != null) {
            GameHUD.showMessage.accept(msg);
        }
        return msg;
    }

    public static String eatFromFridge(Player player, String itemName) {
        Game game = GameManager.getCurrentGame();
        long currentHour = game.getCurrentHour();
        String key = itemName.toLowerCase();

        if (!player.getRefrigerator().hasItem(key)) {
            return message("This item is not in the refrigerator.");
        }

        FoodRecipe recipe = FoodRecipe.fromName(itemName);
        if (recipe == null) {
            return message("This item is not edible.");
        }

        player.getRefrigerator().removeItem(key, 1);

        player.increaseEnergy(recipe.getEnergyCost());

        String buffStr = recipe.getBuff();
        if (buffStr != null) {
            Buff newBuff = new Food(recipe).getParsedBuff(currentHour);
            player.setActiveBuff(newBuff);

            if (newBuff.getType() == Buff.Type.ENERGY_BOOST) {
                player.setEnergy(player.getMaxEnergy() + newBuff.getAmount());
            }

            return message("You gained a buff: " + newBuff.getType() + " for " + newBuff.getDurationInHours() + " hours.");
        } else {
            player.setActiveBuff(null);
            return message("You ate " + recipe.getName() + ". Energy restored: " + recipe.getEnergyCost());
        }
    }



}
