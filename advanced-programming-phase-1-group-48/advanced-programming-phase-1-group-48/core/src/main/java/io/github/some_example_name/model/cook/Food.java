package io.github.some_example_name.model.cook;

import io.github.some_example_name.model.items.Item;

public class Food extends Item {
    private final FoodRecipe recipe;

    public Food(FoodRecipe recipe) {
        super(recipe.getName(), 'F');
        this.recipe = recipe;
    }

    @Override
    public void interact() {

    }

    @Override
    public int getSellPrice() {
        try {
            return Integer.parseInt(recipe.getWeight().replace("g", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public boolean isEdible() {
        return true;
    }

    public int getEnergyRestoration() {
        return recipe.getEnergyCost();
    }

    public String getBuff() {
        return recipe.getBuff();
    }

    public FoodRecipe getRecipe() {
        return recipe;
    }

    public Buff getParsedBuff(long currentHour) {
        String buffStr = recipe.getBuff();
        if (buffStr == null) return null;

        try {
            if (buffStr.startsWith("Max Energy +")) {
                // مثال: "Max Energy + 100 (5 hours)"
                String[] parts = buffStr.split("\\+|\\(|\\)");
                int amount = Integer.parseInt(parts[1].trim());
                int hours = Integer.parseInt(parts[2].replaceAll("[^0-9]", "").trim());
                return new Buff(Buff.Type.ENERGY_BOOST, amount, hours, currentHour);
            } else {
                // مثال: "Farming (5 hours)"
                String[] parts = buffStr.split("\\(|\\)");
                String skillName = parts[0].trim().toUpperCase(); // FARMING
                int hours = Integer.parseInt(parts[1].replaceAll("[^0-9]", "").trim());

                // نوع را SKILL_ENERGY_REDUCTION قرار می‌دهیم و مقدار را مثلاً پیش‌فرض 2
                return new Buff(Buff.Type.SKILL_ENERGY_REDUCTION, 2, hours, currentHour, skillName);
            }
        } catch (Exception e) {
            System.err.println("Failed to parse buff: " + buffStr);
            return null;
        }
}}
