package io.github.some_example_name.controllers;

import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.crafting.CraftingManager;
import io.github.some_example_name.model.crafting.CraftingRecipe;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.game.WorldMap;

import java.util.Scanner;

public class HomeMenu {
    public static void show(Player player, Scanner scanner) {
        Game game = GameManager.getCurrentGame();
        WorldMap worldMap = game.getWorldMap();
        while (true) {
            System.out.println(" Here is home of " + player.getName() + " choose one of the following options:");
            System.out.println("1: Show available options ");
            System.out.println("2: Making items");
            System.out.println("3:Exit");
            System.out.println("Enter your choice: ");

            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> {
                    showAvailableRecipes(player);
                    return;
                }
               // case "2" -> CraftingSystem.startCrafting(player); // (todo: متد صنعتگری)
                case "2" -> {
                    System.out.println("Now you can make items in this way : crafting craft <item_name>");
                    return;
                }
                case "3" -> {
                    System.out.println("Exiting the menu");
                    return;
                }
                default -> System.out.println("invalid input. please try again");
            }
        }
    }

//    private static void showHomeInventory(Player player) {
//        System.out.println("📦 Available items:");
//        //player.getHomeInventory().getItems().forEach((name, count) ->
//                //System.out.println("- " + name + " x" + count));
//    }

    public static void showAvailableRecipes(Player player) {
        System.out.println("Here is a list of all recipes , You just learned some of them which are unlocked.");
        for (CraftingRecipe recipe : CraftingManager.getAllRecipes()) {
            if (recipe.isUnlockedFor(player)) {
                System.out.println("✔ " + recipe.getItemName() + " - Needs: " + recipe.getIngredients()
                        + " | Energy: " + recipe.getEnergyCost());
            } else {
                System.out.println("✘ " + recipe.getItemName() + " (Locked) - Source: " + recipe.getSource());
            }
        }
    }

}

