package io.github.some_example_name.model.artisan;

import java.util.*;

public class ArtisanRecipes {

    public static class Recipe {
        public final List<String> inputs;
        public final String output;
        public final int durationInHours;
        public final int energy;

        public Recipe(List<String> inputs, String output, int durationInHours, int energy) {
            this.inputs = inputs;
            this.output = output;
            this.durationInHours = durationInHours;
            this.energy = energy;
        }
    }

    private static final Map<String, List<Recipe>> RECIPES = new HashMap<>();

    static {
        RECIPES.put("bee house", List.of(
                new Recipe(List.of(), "Honey", 96, 75) // 4 روز
        ));

        RECIPES.put("cheese press", List.of(
                new Recipe(List.of("milk"), "Cheese", 3, 100),
                new Recipe(List.of("large milk"), "Cheese", 3, 100),
                new Recipe(List.of("goat milk"), "Goat Cheese", 3, 100),
                new Recipe(List.of("large goat milk"), "Goat Cheese", 3, 100)
        ));

        RECIPES.put("keg", List.of(
                new Recipe(List.of("wheat"), "Beer", 24, 50),
                new Recipe(List.of("rice"), "Vinegar", 10, 13),
                new Recipe(List.of("coffee bean", "coffee bean", "coffee bean", "coffee bean", "coffee bean"), "Coffee", 2, 75),
                new Recipe(List.of("honey"), "Mead", 10, 100),
                new Recipe(List.of("hops"), "Pale Ale", 72, 50)
                // Juice, Wine از طریق شرط any vegetable / fruit قابل پیاده‌سازی جدا هستند
        ));

        RECIPES.put("dehydrator", List.of(
                new Recipe(List.of("mushroom", "mushroom", "mushroom", "mushroom", "mushroom"), "Dried Mushrooms", 24, 50),
                new Recipe(List.of("grapes", "grapes", "grapes", "grapes", "grapes"), "Raisins", 24, 125),
                new Recipe(List.of("apple", "apple", "apple", "apple", "apple"), "Dried Fruit", 24, 75)
        ));

        RECIPES.put("charcoal klin", List.of(
                new Recipe(List.of("wood", "wood", "wood", "wood", "wood", "wood", "wood", "wood", "wood", "wood"), "Coal", 1, 0)
        ));

        RECIPES.put("loom", List.of(
                new Recipe(List.of("wool"), "Cloth", 4, 0)
        ));

        RECIPES.put("mayonnaise machine", List.of(
                new Recipe(List.of("egg"), "Mayonnaise", 3, 50),
                new Recipe(List.of("large egg"), "Mayonnaise", 3, 50),
                new Recipe(List.of("duck egg"), "Duck Mayonnaise", 3, 75),
                new Recipe(List.of("dinosaur egg"), "Dinosaur Mayonnaise", 3, 125)
        ));

        RECIPES.put("oil maker", List.of(
                new Recipe(List.of("truffle"), "Truffle Oil", 6, 38),
                new Recipe(List.of("corn"), "Oil", 6, 13),
                new Recipe(List.of("sunflower"), "Oil", 24, 13),
                new Recipe(List.of("sunflower seeds"), "Oil", 1, 13)
        ));

        RECIPES.put("preserves jar", List.of(
                new Recipe(List.of("cucumber"), "Pickles", 6, 10),
                new Recipe(List.of("apple"), "Jelly", 72, 15)
        ));

        RECIPES.put("fish smoker", List.of(
                new Recipe(List.of("salmon", "coal"), "Smoked Fish", 1, 20)
        ));

        RECIPES.put("furnace", List.of(
                new Recipe(List.of("copper ore", "copper ore", "copper ore", "copper ore", "copper ore", "coal"), "Copper Bar", 4, 0),
                new Recipe(List.of("iron ore", "iron ore", "iron ore", "iron ore", "iron ore", "coal"), "Iron Bar", 4, 0),
                new Recipe(List.of("gold ore", "gold ore", "gold ore", "gold ore", "gold ore", "coal"), "Gold Bar", 4, 0),
                new Recipe(List.of("iridium ore", "iridium ore", "iridium ore", "iridium ore", "iridium ore", "coal"), "Iridium Bar", 4, 0)
        ));
    }

    public static Recipe findRecipe(String artisan, List<String> inputs) {
        List<Recipe> recipes = RECIPES.get(artisan.toLowerCase());
        if (recipes == null) return null;
        List<String> sortedInputs = new ArrayList<>(inputs);
        Collections.sort(sortedInputs);
        for (Recipe recipe : recipes) {
            List<String> required = new ArrayList<>(recipe.inputs);
            Collections.sort(required);
            if (sortedInputs.equals(required)) return recipe;
        }
        return null;
    }

    public static boolean isArtisanValid(String artisan) {
        return RECIPES.containsKey(artisan.toLowerCase());
    }
}
