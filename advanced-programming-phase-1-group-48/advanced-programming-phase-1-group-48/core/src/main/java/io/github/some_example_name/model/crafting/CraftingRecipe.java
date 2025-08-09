package io.github.some_example_name.model.crafting;



import io.github.some_example_name.model.Player.Player;

import java.util.Map;

public class CraftingRecipe {
    private final String itemName;
    private final Map<String, Integer> ingredients;
    private final String source;
    private final int energyCost;
    private final int sellPrice;

    private boolean unlockedViaShop = false;

    public CraftingRecipe(String itemName, Map<String, Integer> ingredients, String source, int energyCost, int sellPrice) {
        this.itemName = itemName;
        this.ingredients = ingredients;
        this.source = source;
        this.energyCost = energyCost;
        this.sellPrice = sellPrice;
    }

    public boolean isUnlockedFor(Player player) {
        if (source.equals("-")) return true;
        if (unlockedViaShop) return true;
        if (player.hasLearnedCraftingRecipe(itemName)) return true;

        if (source.startsWith("Farming Level")) {
            int requiredLevel = Integer.parseInt(source.replace("Farming Level ", ""));
            return player.getSkill("Farming").getLevel() >= requiredLevel;
        }
        if (source.startsWith("Mining Level")) {
            int requiredLevel = Integer.parseInt(source.replace("Mining Level ", ""));
            return player.getSkill("Mining").getLevel() >= requiredLevel;
        }
        if (source.startsWith("Foraging Level")) {
            int requiredLevel = Integer.parseInt(source.replace("Foraging Level ", ""));
            return player.getSkill("Foraging").getLevel() >= requiredLevel;
        }

        return false;
    }

    public void unlockFromShop() {
        this.unlockedViaShop = true;
    }

    public boolean isUnlockedFromShop() {
        return unlockedViaShop;
    }

    public String getItemName() {
        return itemName;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public String getSource() {
        return source;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    @Override
    public String toString() {
        return itemName + " (From: " + source + ") - Needs: " + ingredients;
    }
}
