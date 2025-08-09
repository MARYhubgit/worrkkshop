package io.github.some_example_name.model.cook;


import java.util.*;

public enum FoodRecipe {
    FRIED_EGG("Fried_egg", mapOf(Ingredient.EGG, 1), 50, null, "Starter", "35g"),
    BAKED_FISH("Baked_Fish", mapOf(Ingredient.SARDINE, 1, Ingredient.SALMON, 1, Ingredient.WHEAT, 1), 75, null, "Starter", "100g"),
    SALAD("Salad", mapOf(Ingredient.LEEK, 1, Ingredient.DANDELION, 1), 113, null, "Starter", "110g"),
    OMELET("Omelet", mapOf(Ingredient.EGG, 1, Ingredient.MILK, 1), 100, null, "Stardrop Saloon", "125g"),
    PUMPKIN_PIE("Pumpkin_pie", mapOf(Ingredient.PUMPKIN, 1, Ingredient.WHEAT, 1, Ingredient.MILK, 1, Ingredient.SUGAR, 1), 225, null, "Stardrop Saloon", "385g"),
    SPAGHETTI("Spaghetti", mapOf(Ingredient.WHEAT, 1, Ingredient.TOMATO, 1), 75, null, "Stardrop Saloon", "120g"),
    PIZZA("Pizza", mapOf(Ingredient.WHEAT, 1, Ingredient.TOMATO, 1, Ingredient.CHEESE, 1), 150, null, "Stardrop Saloon", "300g"),
    TORTILLA("Tortilla", mapOf(Ingredient.CORN, 1), 50, null, "Stardrop Saloon", "50g"),
    MAKI_ROLL("Maki_Roll", mapOf(Ingredient.SARDINE, 1, Ingredient.RICE, 1, Ingredient.FIBER, 1), 100, null, "Stardrop Saloon", "220g"),
    TRIPLE_SHOT_ESPRESSO("Triple_Shot_Espresso", mapOf(Ingredient.COFFEE, 3), 200, "Max Energy + 100 (5 hours)", "Stardrop Saloon", "450g"),
    COOKIE("Cookie", mapOf(Ingredient.WHEAT, 1, Ingredient.SUGAR, 1, Ingredient.EGG, 1), 90, null, "Stardrop Saloon", "140g"),
    HASH_BROWNS("Hash_Browns", mapOf(Ingredient.POTATO, 1, Ingredient.OIL, 1), 90, "Farming (5 hours)", "Stardrop Saloon", "120g"),
    PANCAKES("Pancakes", mapOf(Ingredient.WHEAT, 1, Ingredient.EGG, 1), 90, "Foraging (11 hours)", "Stardrop Saloon", "80g"),
    FRUIT_SALAD("Fruit_Salad", mapOf(Ingredient.BLUEBERRY, 1, Ingredient.MELON, 1, Ingredient.APRICOT, 1), 263, null, "Stardrop Saloon", "450g"),
    RED_PLATE("Red_Plate", mapOf(Ingredient.CABBAGE, 1, Ingredient.RADISH, 1), 240, "Max Energy +50 (3 hours)", "Stardrop Saloon", "400g"),
    BREAD("Bread", mapOf(Ingredient.WHEAT, 1), 50, null, "Stardrop Saloon", "60g"),
    SALMON_DINNER("Salmon_Dinner", mapOf(Ingredient.SALMON, 1, Ingredient.AMARANTH, 1, Ingredient.KALE, 1), 125, null, "Leah reward", "300g"),
    VEGETABLE_MEDLEY("Vegetable_Medley", mapOf(Ingredient.TOMATO, 1, Ingredient.BEET, 1), 165, null, "Foraging Level 2", "120g"),
    LUCKY_LUNCH("Lucky_Lunch", mapOf(Ingredient.EGG, 1, Ingredient.MILK, 1, Ingredient.PARSNIP, 1), 200, "Farming (5 hours)", "Farming level 1", "150g"),
    SURVIVAL_BURGER("Survival_Burger", mapOf(Ingredient.WHEAT, 1, Ingredient.CARROT, 1, Ingredient.EGGPLANT, 1), 125, "Foraging (5 hours)", "Foraging level 3", "180g"),
    SASHIMI("Sashimi", mapOf(Ingredient.SARDINE, 2, Ingredient.POTATO, 1, Ingredient.OIL, 1), 150, "Fishing (5 hours)", "Fishing level 2", "220g"),
    SEAFOAM_PUDDING("Seafoam_Pudding", mapOf(Ingredient.FLOUNDER, 1, Ingredient.MIDNIGHT_CARP, 1), 175, "Fishing (10 hours)", "Fishing level 3", "300g"),
    MAPLE_BAR("Maple_Bar", mapOf(Ingredient.CARROT, 2, Ingredient.SUGAR, 1, Ingredient.MILK, 1), 125, "Mining (5 hours)", "Mining level 1", "200g");



    private final String name;
    private final Map<Ingredient, Integer> ingredients;
    private final int energyCost;
    private final String buff;
    private final String source;
    private final String weight;

    FoodRecipe(String name,
               Map<Ingredient, Integer> ingredients,
               int energyCost,
               String buff,
               String source,
               String weight) {
        this.name = name;
        this.ingredients = ingredients;
        this.energyCost = energyCost;
        this.buff = buff;
        this.source = source;
        this.weight = weight;
    }

    public String getName() { return name; }
    public Map<Ingredient, Integer> getIngredients() { return ingredients; }
    public int getEnergyCost() { return energyCost; }
    public String getBuff() { return buff; }
    public String getSource() { return source; }
    public String getWeight() { return weight; }

    @SafeVarargs
    private static <V> Map<Ingredient, V> mapOf(Ingredient firstKey, V firstValue, Object... others) {
        EnumMap<Ingredient, V> map = new EnumMap<>(Ingredient.class);
        map.put(firstKey, firstValue);
        for (int i = 0; i < others.length; i += 2) {
            @SuppressWarnings("unchecked")
            Ingredient key = (Ingredient) others[i];
            @SuppressWarnings("unchecked")
            V value = (V) others[i + 1];
            map.put(key, value);
        }
        return Collections.unmodifiableMap(map);
    }

    public static FoodRecipe fromName(String name) {
        for (FoodRecipe recipe : values()) {
            if (recipe.getName().equalsIgnoreCase(name)) {
                return recipe;
            }
        }
        return null;
    }

}
