package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import io.github.some_example_name.controllers.CookController;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Player.inventory.Inventory;
import io.github.some_example_name.model.items.Item;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.some_example_name.views.GameView;

import java.util.Map;

public class FridgeView extends Group {
    private final Player player;
    private final Skin skin;
    private final Table itemTable;
    private final ScrollPane scrollPane;
    private Runnable onBackToCookingMenu;
    private final DragAndDrop dragAndDrop;
    private final Label.LabelStyle customLabelStyle;
    private GameView gameView;
    Skin otherSkin = new Skin(Gdx.files.internal("skin/plain-james-ui.json"),
        new TextureAtlas(Gdx.files.internal("skin/plain-james-ui.atlas")));

    public FridgeView(Player player, Skin skin) {
        this.player = player;
        this.skin = skin;

        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.2f);
        font.setColor(Color.BLACK);
        customLabelStyle = new Label.LabelStyle(font, Color.BLACK);

        this.setSize(1000, 500);
        this.dragAndDrop = new DragAndDrop();

        itemTable = new Table(otherSkin);
        itemTable.align(Align.topLeft);

        Texture backgroundTexture = new Texture("inventory/inventory_background.png");
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        backgroundImage.setSize(1000, 100);
        backgroundImage.setFillParent(true);

        Texture iconTex = new Texture("Cook/refrigerator.jpg");
        Image fridgeIcon = new Image(new TextureRegionDrawable(new TextureRegion(iconTex)));
        fridgeIcon.setSize(48, 48);
        fridgeIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FridgeView.this.setVisible(false);
                if (onBackToCookingMenu != null) onBackToCookingMenu.run();
            }
        });

        Table topBar = new Table(otherSkin);
        topBar.add(fridgeIcon).size(48, 48).left().padRight(10);
        topBar.add(new Label("Refrigerator", customLabelStyle)).left().padTop(10);
        topBar.align(Align.left);

        Stack stack = new Stack();
        stack.add(backgroundImage);
        stack.add(itemTable);

        scrollPane = new ScrollPane(stack, otherSkin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        Table mainTable = new Table(otherSkin);
        mainTable.setFillParent(true);
        mainTable.align(Align.top);
        mainTable.add(topBar).pad(10).left().row();
        mainTable.add(scrollPane).width(1000).height(100).padTop(5).row();

        this.addActor(mainTable);

        update();
    }

    public void update() {
        itemTable.clear();
        dragAndDrop.clear();
        itemTable.align(Align.topLeft);
        itemTable.padTop(10).padLeft(10);

        Map<String, Integer> items = player.getRefrigerator().getItems();
        int count = 0;

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();

            Item item = Inventory.itemInstances.get(itemName);
            if (item == null) continue;

            TextureRegion itemRegion = new TextureRegion(new Texture("item/" + item.getName() + ".png"));
            Image itemImage = new Image(new TextureRegionDrawable(itemRegion));
            itemImage.setScaling(Scaling.fit);
            itemImage.setSize(48, 48);

            Label qtyLabel = new Label("x" + quantity, customLabelStyle);
            Table cell = new Table();
            cell.add(itemImage).size(48, 48).row();
            cell.add(qtyLabel).center();

            cell.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Dialog dialog = new Dialog("Select Action", otherSkin) {
                        protected void result(Object obj) {
                            if ("eat".equals(obj)) {
                                String result = CookController.eatFromFridge(player, item.getName());
                                if (gameView != null) {
                                    gameView.triggerEatingEffect(item.getName());
                                }
                                update();
                            }
                            else if ("back".equals(obj)) {
                                CookController.handleRefrigeratorCommand(player, "pick", item.getName());
                                update();
                            }
                        }
                    };
                    dialog.text("What do you want to do?");
                    dialog.button("Eat", "eat");
                    dialog.button("Return to Inventory", "back");
                    dialog.show(getStage());
                }
            });

            // Drag & Drop
            dragAndDrop.addSource(new DragAndDrop.Source(itemImage) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject(item);
                    Image dragActor = new Image(new TextureRegionDrawable(itemRegion));
                    dragActor.setSize(48, 48);
                    payload.setDragActor(dragActor);
                    return payload;
                }
            });

            itemTable.add(cell).pad(5);
            count++;
            if (count % 11 == 0) itemTable.row();
        }

        dragAndDrop.addTarget(new DragAndDrop.Target(this) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return payload.getObject() instanceof Item;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Item draggedItem = (Item) payload.getObject();
                CookController.handleRefrigeratorCommand(player, "put", draggedItem.getName());
                update();
            }
        });
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void setOnBackToCookingMenu(Runnable r) {
        this.onBackToCookingMenu = r;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
