package io.github.some_example_name.model.crafting;

import java.util.*;

public class CraftingManager {

    private static final Map<String, CraftingRecipe> recipes = new HashMap<>();

    public static void initializeRecipes() {
        addRecipe("Cherry_Bomb", Map.of("copper_ore", 4, "coal", 1), "Mining Level 1", 2, 50);
        addRecipe("Bomb", Map.of("iron_ore", 4, "coal", 1), "Mining Level 2", 2, 50);
        addRecipe("Mega_Bomb", Map.of("gold_ore", 4, "coal", 1), "Mining Level 3", 2, 50);
        addRecipe("Sprinkler", Map.of("copper_bar", 1, "iron_bar", 1), "Farming Level 1", 2, 0);
        addRecipe("Quality_Sprinkler", Map.of("iron_bar", 1, "gold_bar", 1), "Farming Level 2", 2, 0);
        addRecipe("Iridium_Sprinkler", Map.of("gold_bar", 1, "iridium_bar", 1), "Farming Level 3", 2, 0);
        addRecipe("Charcoal_Kiln", Map.of("wood", 20, "copper_bar", 2), "Foraging Level 1", 2, 0);
        addRecipe("Furnace", Map.of("copper_ore", 20, "stone", 25), "-", 2, 0);
        addRecipe("Scarecrow", Map.of("wood", 50, "coal", 1, "fiber", 20), "-", 2, 0);
        addRecipe("Deluxe_Scarecrow", Map.of("wood", 50, "coal", 1, "fiber", 20, "iridium ore", 1), "Farming Level 2", 2, 0);
        addRecipe("Bee_House", Map.of("wood", 40, "coal", 8, "iron bar", 1), "Farming Level 1", 2, 0);
        addRecipe("Cheese_Press", Map.of("wood", 45, "stone", 45, "copper bar", 1), "Farming Level 2", 2, 0);
        addRecipe("Keg", Map.of("wood", 30, "copper_bar", 1, "iron_bar", 1), "Farming Level 3", 2, 0);
        addRecipe("Loom", Map.of("wood", 60, "fiber", 30), "Farming Level 3", 2, 0);
        addRecipe("Mayonnaise_Machine", Map.of("wood", 15, "stone", 15, "copper bar", 1), "-", 2, 0);
        addRecipe("Oil_Maker", Map.of("wood", 100, "gold_bar", 1, "iron_bar", 1), "Farming Level 3", 2, 0);
        addRecipe("Preserves_Jar", Map.of("wood", 50, "stone", 40, "coal", 8), "Farming Level 2", 2, 0);
        addRecipe("Dehydrator", Map.of("wood", 30, "stone", 20, "fiber", 30), "Pierre's General Store", 2, 0);
//        addRecipe("Grass_Starter", Map.of("wood", 1, "fiber", 1), "Pierre's General Store", 2, 0);
        addRecipe("Fish_Smoker", Map.of("wood", 50, "iron_bar", 3, "coal", 10), "Fish Shop", 2, 0);
//        addRecipe("Mystic_Tree_Seed", Map.of("acorn", 5, "maple_seed", 5, "pine_cone", 5, "mahogany_seed", 5), "Foraging Level 4", 2, 100);
    }

    private static void addRecipe(String itemName, Map<String, Integer> ingredients, String source, int energy, int sellPrice) {
        recipes.put(itemName.toLowerCase(), new CraftingRecipe(itemName, ingredients, source, energy, sellPrice));
    }

    public static CraftingRecipe getRecipe(String itemName) {
        return recipes.get(itemName.toLowerCase());
    }

    public static Collection<CraftingRecipe> getAllRecipes() {
        return recipes.values();
    }

    public static boolean recipeExists(String itemName) {
        return recipes.containsKey(itemName.toLowerCase());
    }
}
