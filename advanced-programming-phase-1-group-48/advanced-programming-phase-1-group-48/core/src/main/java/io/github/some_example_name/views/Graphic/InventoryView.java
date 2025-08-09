package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import io.github.some_example_name.controllers.CookController;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Player.inventory.Inventory;
import io.github.some_example_name.model.items.Item;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Map;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

public class InventoryView extends Group {
    private final Player player;
    private final Inventory inventory;
    private final Texture backgroundTexture;
    private final Table gridTable;
    private final Skin skin;
    private final Label goldLabel;
    private final ScrollPane scrollPane;
    private final Image trashCanImage;
    private final DragAndDrop dragAndDrop;
    private BitmapFont font;
    private final Label.LabelStyle customLabelStyle;
    private final Table mainTable;
    private final Table bottomTable;
    private FridgeView fridgeView;
    private ShippingBinView shippingBinView;


    public InventoryView(Player player, Skin skin) {
        this.font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(1.2f);
        this.customLabelStyle = new Label.LabelStyle(font, Color.BLACK);
        this.player = player;
        this.inventory = player.getInventory();
        this.skin = skin;
        this.backgroundTexture = new Texture("inventory/inventory_background.png");

        Image backgroundImage = new Image(backgroundTexture);
        dragAndDrop = new DragAndDrop();

        gridTable = new Table(skin);
        gridTable.align(Align.topLeft);
        gridTable.padTop(15);
        updateGrid();

        Stack stack = new Stack();
        stack.add(backgroundImage);
        stack.add(gridTable);

        int visibleHeight = backgroundTexture.getHeight();
        scrollPane = new ScrollPane(stack, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setScrollbarsOnTop(true);

        goldLabel = new Label("Gold: " + player.getMoney(), customLabelStyle);

        Texture trashTexture = new Texture("item/Trash_Can_Steel.png");
        trashCanImage = new Image(trashTexture);
        trashCanImage.setSize(64, 64);
        trashCanImage.setAlign(Align.center);

        // 🎯 هدف سطل آشغال
        dragAndDrop.addTarget(new DragAndDrop.Target(trashCanImage) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                trashCanImage.setColor(1, 0, 0, 1);
                return true;
            }

            @Override
            public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
                trashCanImage.setColor(1, 1, 1, 1);
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Object obj = payload.getObject();
                if (obj instanceof String itemName) {
                    Item item = Inventory.itemInstances.get(itemName);
                    if (item != null) {
                        String result = inventory.removeItemWithTrash(itemName, 1, item, player);
                        System.out.println(result);
                        refresh();
                    }
                }
                trashCanImage.setColor(1, 1, 1, 1);
            }
        });

        mainTable = new Table(skin);
        mainTable.setFillParent(true);
        mainTable.align(Align.top);
        mainTable.add(scrollPane).width(backgroundTexture.getWidth()).height(visibleHeight).row();

        bottomTable = new Table(skin);
        bottomTable.add(goldLabel).expandX().fillX().left().padLeft(10);
        bottomTable.add(trashCanImage).size(64, 64).expandX().right().padRight(100);
        mainTable.add(bottomTable).padTop(10);

        this.setSize(backgroundTexture.getWidth(), backgroundTexture.getHeight());
        this.addActor(mainTable);
    }

    private void updateGrid() {
        gridTable.clear();
        int col = 0;
        int maxCols = 11;

        for (Map.Entry<String, Integer> entry : inventory.getItems().entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            Item item = Inventory.itemInstances.get(itemName);

            Texture itemTexture = new Texture("item/" + item.getName() + ".png");
            Image itemImage = new Image(new TextureRegionDrawable(new TextureRegion(itemTexture)));
            itemImage.setScaling(Scaling.fit);
            itemImage.setSize(64, 64);
            itemImage.setAlign(Align.center);

            Label qtyLabel = new Label(String.valueOf(quantity), customLabelStyle);
            qtyLabel.setAlignment(Align.bottomRight);

            Stack itemStack = new Stack();
            itemStack.add(itemImage);

            Table qtyTable = new Table();
            qtyTable.add(qtyLabel).expand().bottom().right().pad(2);
            itemStack.add(qtyTable);

            final String draggedItemName = itemName;

            itemStack.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    Item item = Inventory.itemInstances.get(itemName);
//                    if (item != null && item.isEdible()) {
//                        String result = CookController.handleRefrigeratorCommand(player, "put", itemName);
//                        System.out.println("🍎 Transferred to fridge: " + result);
//
//                        // Refresh both views
//                        refresh();
//                        if (fridgeView != null) fridgeView.update();
//                    } else {
//                        GameHUD.showMessage.accept("❌ This item is not edible.");
//                    }
//                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showTargetChoiceDialog(itemName);
                }

            });


            // 🎯 منبع کشیدن
            dragAndDrop.addSource(new DragAndDrop.Source(itemStack) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject(draggedItemName);
                    payload.setDragActor(itemStack);
                    return payload;
                }
            });

            gridTable.add(itemStack).size(60, 60).pad(4);
            col++;
            if (col % maxCols == 0) gridTable.row();
        }
    }

    public void refresh() {
        updateGrid();
        goldLabel.setText("Gold: " + player.getMoney());
        bottomTable.clearChildren();
        bottomTable.add(goldLabel).expandX().fillX().left().padLeft(10);
        bottomTable.add(trashCanImage).size(64, 64).expandX().right().padRight(100);
    }

    public void addFridgeDropTarget(final FridgeView fridgeView) {
        dragAndDrop.addTarget(new DragAndDrop.Target(fridgeView) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                String itemName = (String) payload.getObject();
                Item item = Inventory.itemInstances.get(itemName.toLowerCase());
                GameHUD.showMessage.accept("Keys: " + Inventory.itemInstances.keySet());

                return item != null && item.isEdible();
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                String itemName = (String) payload.getObject();
                Item item = Inventory.itemInstances.get(itemName.toLowerCase());

                GameHUD.showMessage.accept("Droppp" + itemName + item.isEdible());
                if (item != null && item.isEdible()) {
                    String result = CookController.handleRefrigeratorCommand(player, "put", itemName);
                    System.out.println(result);
                    refresh();
                    fridgeView.update();
                }
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void setFridgeView(FridgeView fridgeView) {
        this.fridgeView = fridgeView;
    }

    private void showTargetChoiceDialog(String itemName) {
        Dialog dialog = new Dialog(" ", skin) {
            @Override
            protected void result(Object object) {
                boolean sendToFridge = (Boolean) object;

                if (sendToFridge) {
                    String resultStr = CookController.handleRefrigeratorCommand(player, "put", itemName);
                    System.out.println(resultStr);
                    if (fridgeView != null) fridgeView.update();
                } else {
                    Item item = Inventory.itemInstances.get(itemName);
                    if (item != null) {
                        System.out.println(itemName);
                        if (item.getName().equals("Basic_Hoe") || item.getName().equals("Basic_Pickaxe") || item.getName().equals("Basic_Axe") ||  item.getName().equals("Basic_Watering_Can") || item.getName().equals("scythe") ) {
                            GameHUD.showMessage.accept("this item is unsellable");
                            return;
                        }
                        player.getInventory().removeItem(itemName, 1);
                        player.getShippingBin().sellItem(player, itemName, 1);

                        if (shippingBinView != null)
                            shippingBinView.updateGrid();
                    }
                }

                refresh();
            }
        };

        dialog.button("🧊 Fridge", true);
        dialog.button("📦 Sell", false);
        dialog.show(getStage());
    }



}
