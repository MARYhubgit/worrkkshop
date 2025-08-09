package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import io.github.some_example_name.model.cook.FoodRecipe;
import io.github.some_example_name.model.cook.Ingredient;
import io.github.some_example_name.model.crafting.CraftingRecipe;

import java.util.Map;

public class TooltipListener extends InputListener {
    private final Tooltip tooltip;
    private FoodRecipe recipe;
    private CraftingRecipe craftingRecipe;

    public TooltipListener(Tooltip tooltip, FoodRecipe recipe) {
        this.tooltip = tooltip;
        this.recipe = recipe;
    }

    public TooltipListener(Tooltip tooltip, CraftingRecipe recipe) {
        this.tooltip = tooltip;
        craftingRecipe = recipe;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        StringBuilder sb = new StringBuilder("Ingredients:\n");
        if (recipe != null) {
            for (Map.Entry<Ingredient, Integer> e : recipe.getIngredients().entrySet()) {
                sb.append(e.getKey().name()).append(": ").append(e.getValue()).append("\n");
            }
            tooltip.show(sb.toString(), Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        }else {
            for (Map.Entry<String, Integer> e : craftingRecipe.getIngredients().entrySet()) {
                sb.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
            }
            tooltip.show(sb.toString(), Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        }

    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        tooltip.hide();
    }
}

