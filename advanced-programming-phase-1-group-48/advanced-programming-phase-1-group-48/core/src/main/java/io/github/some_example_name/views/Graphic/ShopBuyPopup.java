package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.items.ItemFactory;
import io.github.some_example_name.model.shop.Product;
import io.github.some_example_name.model.shop.Shop;
import io.github.some_example_name.views.Graphic.GameHUD;

public class ShopBuyPopup extends Group {
    private final Player player;
    private final Product product;
    private final Skin skin;
    private int quantity = 1;
    private BitmapFont font;
    private final Label.LabelStyle customLabelStyle;

    private final Label qtyLabel;
    private final Label priceLabel;
    private final Runnable onClose;

    public ShopBuyPopup(Player player, Skin skin, Product product, Runnable onClose) {
        this.font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(1.2f);
        this.customLabelStyle = new Label.LabelStyle(font, Color.BLACK);
        this.player = player;
        this.product = product;
        this.skin = skin;
        this.onClose = onClose;

        this.setSize(400, 300);
        this.setPosition(
            (float)(com.badlogic.gdx.Gdx.graphics.getWidth() - getWidth()) / 2,
            (float)(com.badlogic.gdx.Gdx.graphics.getHeight() - getHeight()) / 2
        );

        // پس زمینه
        Texture bgTexture = new Texture("shop/shop_background.png");
        Image bgImage = new Image(new TextureRegionDrawable(new TextureRegion(bgTexture)));
        bgImage.setSize(400, 300);
        this.addActor(bgImage);

        Table content = new Table(skin);
        content.setFillParent(true);
        this.addActor(content);

        Label titleLabel = new Label("Buy: " + product.getName(), customLabelStyle);
        titleLabel.setFontScale(1.2f);

        Texture itemTexture = new Texture("item/" + product.getName() + ".png");
        Image itemImage = new Image(new TextureRegionDrawable(new TextureRegion(itemTexture)));
        itemImage.setSize(64, 64);

        // قیمت و تعداد
        qtyLabel = new Label("Qty: 1", customLabelStyle);
        priceLabel = new Label("Total: " + product.getPrice() + "g", customLabelStyle);

        TextButton plusBtn = new TextButton("+", skin);
        TextButton minusBtn = new TextButton("-", skin);
        TextButton buyBtn = new TextButton("Buy", skin);
        TextButton cancelBtn = new TextButton("Cancel", skin);

        plusBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
//                if (quantity < product.getRemainingToday()) {
                    quantity++;
                    updateLabels();
//                }
            }
        });

        minusBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (quantity >= 1) {
                    quantity--;
                    updateLabels();
                }
            }
        });

        buyBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                int totalCost = quantity * product.getPrice();
                if (!product.isAvailableInSeason()) {
                    GameHUD.showMessage.accept("❌ This product is not available this season.");
                    return;
                }
                if (product.getRemainingToday() < quantity) {
                    GameHUD.showMessage.accept("❌ Not enough stock available.");
                    return;
                }
                if (player.getMoney() < totalCost) {
                    GameHUD.showMessage.accept("❌ Not enough gold.");
                    return;
                }
                if (product.getRequiredFishingLevel() > player.getSkill("Fishing").getLevel()) {
                    GameHUD.showMessage.accept("❌ Fishing level too low.");
                    return;
                }

                player.decreaseMoney(totalCost);
                ItemFactory.createItem(product.getName(),player.getInventory());
                product.purchase(quantity);
                GameHUD.showMessage.accept("Purchased " + quantity + " x " + product.getName());
                onClose.run();
                ShopBuyPopup.this.remove();
            }
        });

        cancelBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                onClose.run();
                ShopBuyPopup.this.remove();
            }
        });

        content.add(titleLabel).colspan(4).pad(10).row();
        content.add(itemImage).colspan(4).pad(10).row();
        content.add(minusBtn).size(40).pad(5);
        content.add(qtyLabel).pad(5);
        content.add(plusBtn).size(40).pad(5);
        content.add(priceLabel).pad(5).row();
        content.add(buyBtn).colspan(2).fillX().pad(10);
        content.add(cancelBtn).colspan(2).fillX().pad(10);
    }

    private void updateLabels() {
        qtyLabel.setText("Qty: " + quantity);
        priceLabel.setText("Total: " + (quantity * product.getPrice()) + "g");
        qtyLabel.invalidateHierarchy();
        priceLabel.invalidateHierarchy();
    }


    public void setOnClose(Runnable onClose) {

    }
}
