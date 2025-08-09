package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import io.github.some_example_name.controllers.CookController;
import io.github.some_example_name.controllers.CraftingController;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.cook.FoodRecipe;
import io.github.some_example_name.model.crafting.CraftingManager;
import io.github.some_example_name.model.crafting.CraftingRecipe;

public class CraftingView extends Group {
    private final Player player;
    private final Skin skin;
    private final Table recipeTable;
    private final ScrollPane scrollPane;
    private final Tooltip tooltip;

    public CraftingView(Player player, Skin skin) {
        this.player = player;
        this.skin = skin;


        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.setSize(screenWidth, screenHeight);

        // Background
        Texture bg = new Texture("Craftable_item/background.jpg");
        float bgWidth = 500;
        float bgHeight = 500;

        Image background = new Image(new TextureRegionDrawable(new TextureRegion(bg)));
        background.setSize(bgWidth, bgHeight);
        background.setPosition((Gdx.graphics.getWidth() - bgWidth) / 2f,
            (Gdx.graphics.getHeight() - bgHeight) / 2f);


        recipeTable = new Table();
        recipeTable.defaults().pad(4).size(100, 100);
        recipeTable.setSize(bgWidth, bgHeight);
        recipeTable.setPosition(background.getX(), background.getY());
        this.addActor(recipeTable);

        // Tooltip عمومی
        tooltip = new Tooltip(skin);
        this.addActor(tooltip);

        Stack stack = new Stack();
        stack.setSize(bgWidth, bgHeight);
        stack.add(background);
        stack.add(recipeTable);

        scrollPane = new ScrollPane(stack, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.align(Align.top);
        mainTable.add(scrollPane).width(1000).height(500).pad(20);

        this.addActor(mainTable);

        updateRecipes();
    }

    public void updateRecipes() {
        recipeTable.clear();
        int count = 0;

        for (CraftingRecipe recipe : CraftingManager.getAllRecipes()) {
            boolean unlocked = recipe.isUnlockedFor(player);
            Image recipeImage = createRecipeImage(recipe, unlocked);
            recipeTable.add(recipeImage);

            count++;
            if (count % 11 == 0) {
                recipeTable.row(); // ردیف جدید
            }
        }

    }

    private Image createRecipeImage( CraftingRecipe recipe, boolean learned) {
        Texture tex = new Texture("Craftable_item/" + recipe.getItemName() + ".png");
        Image image = new Image(new TextureRegionDrawable(tex));

        // شفافیت بر اساس آموخته بودن
        image.setColor(1, 1, 1, learned ? 1f : 0.5f);

        image.addListener(new TooltipListener(tooltip, recipe));

        if (learned) {
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    CraftingController.attemptCraft(player, recipe);
                }
            });
        }


        return image;
    }

}
