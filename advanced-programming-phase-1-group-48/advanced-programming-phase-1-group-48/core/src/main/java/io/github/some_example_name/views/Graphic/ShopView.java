package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.shop.Product;
import io.github.some_example_name.model.shop.Shop;
import io.github.some_example_name.model.shop.ShopManager;
import io.github.some_example_name.model.shop.ShopType;
import io.github.some_example_name.model.enums.Season;

import java.util.List;

public class ShopView extends Group {
    private final Player player;
    private final Skin skin;
    private final Shop shop;
    private final Table productTable;
    private final ScrollPane scrollPane;
    private final SelectBox<String> filterSelect;
    private Runnable onClose;
    private final Label goldLabel;
    private BitmapFont font;
    private final Label.LabelStyle customLabelStyle;

    public ShopView(Player player, Skin skin, ShopType shopType) {
        this.font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(1.2f);
        this.customLabelStyle = new Label.LabelStyle(font, Color.BLACK);
        this.player = player;
        this.skin = skin;
        this.shop = new ShopManager().getShop(shopType);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.setSize(screenWidth, screenHeight);

        Texture bgTex = new Texture("shop/shop_background.png");
        Image background = new Image(new TextureRegionDrawable(new TextureRegion(bgTex)));
        background.setSize(1000, 600);
        background.setPosition((screenWidth - background.getWidth()) / 2f,
            (screenHeight - background.getHeight()) / 2f);

        productTable = new Table(skin);
        productTable.align(Align.topLeft);
        productTable.defaults().pad(5);

        scrollPane = new ScrollPane(productTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setSize(900, 400);

        goldLabel = new Label("Gold: " + player.getMoney(), customLabelStyle);

        filterSelect = new SelectBox<>(skin);
        filterSelect.setItems("All Products", "Available Only");
        filterSelect.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateProducts();
            }
        });

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (onClose != null) onClose.run();
            }
        });

        Table topBar = new Table(skin);
        topBar.add(new Label(shopType.name() + " Shop", customLabelStyle)).expandX().left().pad(100);
        topBar.add(goldLabel).right().padRight(20);
        topBar.row();
        topBar.add(filterSelect).left().pad(10);
        topBar.add(closeButton).right().pad(10);

        Table mainTable = new Table(skin);
        mainTable.setFillParent(true);
        mainTable.align(Align.top);
        mainTable.add(topBar).fillX().row();
        mainTable.add(scrollPane).width(900).height(400).center().pad(20);

        this.addActor(background);
        this.addActor(mainTable);

        updateProducts();
    }

    public void updateProducts() {
        productTable.clear();
        List<Product> productsToShow = filterSelect.getSelected().equals("Available Only")
            ? shop.getAvailableProducts()
            : shop.getAllProducts();

        for (Product p : productsToShow) {
            Texture itemTex = new Texture("item/" + p.getName() + ".png");
            Image icon = new Image(new TextureRegionDrawable(new TextureRegion(itemTex)));
            icon.setSize(48, 48);
            Label nameLabel = new Label(p.getName(), customLabelStyle);
            Label priceLabel = new Label(p.getPrice() + " g", customLabelStyle);

            if (!p.isAvailable() || !p.isAvailableInSeason()) {
                icon.setColor(0.5f, 0.5f, 0.5f, 1f);
                nameLabel.setColor(0.5f, 0.5f, 0.5f, 1f);
                priceLabel.setColor(0.5f, 0.5f, 0.5f, 1f);
            }

            TextButton buyButton = new TextButton("Buy", skin);
            buyButton.setDisabled(!p.isAvailable() || !p.isAvailableInSeason());
            buyButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ShopBuyPopup popup = new ShopBuyPopup(player, skin,p,onClose);
                    popup.setOnClose(() -> {
                        updateProducts();
                        goldLabel.setText("Gold: " + player.getMoney());
                        ShopView.this.removeActor(popup);
                    });
                    ShopView.this.addActor(popup);
                }
            });

            Table row = new Table(skin);
            row.add(icon).size(48).padRight(10);
            row.add(nameLabel).left().padRight(20);
            row.add(priceLabel).padRight(20);
            row.add(buyButton).width(80);
            productTable.add(row).left().row();
        }
    }

    public void setOnClose(Runnable r) {
        this.onClose = r;
    }


}
