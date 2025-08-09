package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.some_example_name.controllers.CookController;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.cook.FoodRecipe;

public class CookingMenu extends Group {
    private final Player player;
    private final Skin skin;
    private final Tooltip tooltip;
    private final Texture backgroundTexture;
    private final Image backgroundImage;
    private final Table recipeTable;
    private final Image fridgeIcon;
    private Runnable onFridgeIconClicked;

    public CookingMenu(Player player, Skin skin) {
        this.player = player;
        this.skin = skin;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.setSize(screenWidth, screenHeight);

        // پس‌زمینه منو آشپزی
        backgroundTexture = new Texture("Cook/background.jpg");
        backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        backgroundImage.setSize(800, 200); // عرض و ارتفاع پیش‌فرض
        backgroundImage.setPosition((screenWidth - backgroundImage.getWidth()) / 2f,
            (screenHeight - backgroundImage.getHeight()) / 2f);
        this.addActor(backgroundImage);

        // جدول نمایش غذاها
        recipeTable = new Table();
        recipeTable.defaults().pad(4).size(60, 60);
        recipeTable.setPosition(backgroundImage.getX(), backgroundImage.getY());
        recipeTable.setSize(backgroundImage.getWidth(), backgroundImage.getHeight());
        this.addActor(recipeTable);

        // Tooltip عمومی
        tooltip = new Tooltip(skin);
        this.addActor(tooltip);

        // آیکون یخچال
        Texture fridgeTexture = new Texture("Cook/refrigerator.jpg");
        fridgeIcon = new Image(new TextureRegionDrawable(new TextureRegion(fridgeTexture)));
        fridgeIcon.setSize(64, 64);
        fridgeIcon.setPosition(getWidth()/2 - 450, getHeight()/2 + 100);
        this.addActor(fridgeIcon);

        fridgeIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (onFridgeIconClicked != null) {
                    setVisible(false);
                    onFridgeIconClicked.run(); // اجرای منوی یخچال
                }
            }
        });

        updateRecipes();
    }

    public void updateRecipes() {
        recipeTable.clear();
        int count = 0;

        for (FoodRecipe recipe : FoodRecipe.values()) {
            boolean learned = player.knowsRecipe(recipe);
            Image recipeImage = createRecipeImage(recipe, learned);
            recipeTable.add(recipeImage);

            count++;
            if (count % 11 == 0) {
                recipeTable.row(); // ردیف جدید
            }
        }
    }

    private Image createRecipeImage(FoodRecipe recipe, boolean learned) {
        Texture tex = new Texture("Cook/Recipe/" + recipe.getName() + ".png");
        Image image = new Image(new TextureRegionDrawable(tex));

        // شفافیت بر اساس آموخته بودن
        image.setColor(1, 1, 1, learned ? 1f : 0.5f);

        image.addListener(new TooltipListener(tooltip, recipe));

        if (learned) {
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    String result = CookController.prepareFood(player, recipe.getName());
                    System.out.println(result);
                }
            });
        }

        return image;
    }

    public void setOnFridgeIconClicked(Runnable onFridgeIconClicked) {
        this.onFridgeIconClicked = onFridgeIconClicked;
    }
}
