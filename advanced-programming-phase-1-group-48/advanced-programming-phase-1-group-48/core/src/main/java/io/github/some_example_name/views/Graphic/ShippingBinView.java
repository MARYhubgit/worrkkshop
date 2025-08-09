package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.github.some_example_name.model.Player.Player;

import java.util.Map;

public class ShippingBinView extends Group {
    private final Player player;
    private final Table gridTable;
    private final Skin skin;
    private final Texture backgroundTexture;

    public ShippingBinView(Player player, Skin skin) {
        this.player = player;
        this.skin = skin;
        this.backgroundTexture = new Texture("inventory/inventory_background.png");

        Image background = new Image(backgroundTexture);
//        background.setSize(400, 300); // یا اندازه‌ی دقیق تصویرت
        this.addActor(background);

        gridTable = new Table(skin);
        gridTable.align(Align.topLeft);
        gridTable.setPosition(7, 80); // یا مقدار مناسب دیگر برای جابه‌جا شدن به بالا
        this.addActor(gridTable);


        updateGrid();
    }

    public void updateGrid() {
        gridTable.clear();

        Map<String, Integer> items = player.getShippingBin().getItems();

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            Texture texture = new Texture("item/" + itemName + ".png");

            Image itemImage = new Image(texture);
            itemImage.setSize(65, 65);

            Label qtyLabel = new Label(String.valueOf(quantity), skin);
            Stack stack = new Stack();
            stack.add(itemImage);
            stack.add(qtyLabel);

            gridTable.add(stack).size(64, 64).pad(4);
        }
    }
}
