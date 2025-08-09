package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Player.inventory.Inventory;
import io.github.some_example_name.model.Tools.Tool;
import io.github.some_example_name.model.items.Item;
import io.github.some_example_name.controllers.ToolController;

import java.util.Set;

public class ToolInventoryView extends Group {
    private final Player player;
    private final Inventory inventory;
    private final Skin skin;
    private final Table toolTable;

    public ToolInventoryView(Player player, Skin skin) {
        this.player = player;
        this.inventory = player.getInventory();
        this.skin = skin;

        toolTable = new Table(skin);
        toolTable.align(Align.bottomLeft);
        updateToolTable();

        ScrollPane scrollPane = new ScrollPane(toolTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(false, true);

        this.addActor(scrollPane);
        this.setSize(500, 80);
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float viewWidth = this.getWidth();
        float viewHeight = this.getHeight();

        float x = (screenWidth - viewWidth) / 2f  + 150;

        float y = 100f;

        this.setPosition(x, y);
    }

    public void updateToolTable() {
        toolTable.clear();

        Set<String> itemNames = inventory.getItemNames();
        String[] toolKeywords = {"axe", "pickaxe", "hoe", "watering", "fishing", "scythe", "milk", "shear", "trash","rod"};

        for (String name : itemNames) {
            boolean isTool = false;
            for (String keyword : toolKeywords) {
                if (name.toLowerCase().contains(keyword)) {
                    isTool = true;
                    break;
                }
            }
            if (!isTool) continue;

            Item item = Inventory.itemInstances.get(name.toLowerCase());
            if (!(item instanceof Tool)) continue;

            Texture texture = new Texture("item/" + item.getName() + ".png");
            Image image = new Image(new TextureRegionDrawable(texture));
            image.setSize(64, 64);

            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ToolController.handleEquip(item.getName(), player);
                    System.out.println(item.getName() + " equipped!");
                }
            });

            toolTable.add(image).size(64, 64).pad(5);
        }
    }
}
