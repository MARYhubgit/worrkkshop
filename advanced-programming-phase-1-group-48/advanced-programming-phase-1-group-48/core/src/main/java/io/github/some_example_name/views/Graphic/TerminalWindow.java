package io.github.some_example_name.views.Graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import io.github.some_example_name.controllers.GameController;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Weather.DateAndTime;
import io.github.some_example_name.model.cook.FoodRecipe;
import io.github.some_example_name.model.crafting.CraftingManager;
import io.github.some_example_name.model.crafting.CraftingRecipe;
import io.github.some_example_name.model.items.ItemFactory;

import java.util.Arrays;

public class TerminalWindow extends Group {
    private final Skin skin;
    private final Table container;
    private final TextField commandField;
    private final Label outputLabel;
    private final Stage stage;
    private final Player player;
    private final BitmapFont bitmapFont;
    private boolean open = false; // وضعیت پنجره

    public TerminalWindow(Skin skin, Stage stage, GameController controller) {
        this.skin = skin;
        this.stage = stage;
        this.player = controller.getPlayerController().getPlayer();

        bitmapFont = new BitmapFont();
        Label.LabelStyle titleStyle = new Label.LabelStyle(bitmapFont, Color.WHITE);
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = bitmapFont;
        textFieldStyle.fontColor = skin.getColor("white");

        // container که خودش داخل این Group قرار می‌گیرد
        container = new Table(skin);
        container.setBackground("window");

        // اندازه / موقعیت: ابتدا کاملاً بیرون صفحه (پایین) قرار می‌دهیم
        container.setSize(600, 380);
        float startX = (Gdx.graphics.getWidth() - container.getWidth()) / 2f;
        float hiddenY = -container.getHeight();
        container.setPosition(startX, hiddenY);

        // عنوان و دکمه Close
        Label titleLabel = new Label("Terminal", titleStyle);
        TextButton closeButton = new TextButton("X", skin);
        closeButton.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });

        Table titleTable = new Table();
        titleTable.add(titleLabel).left().expandX().pad(8);
        titleTable.add(closeButton).right().pad(8);

        // فیلد فرمان و خروجی
        commandField = new TextField("", textFieldStyle);
        commandField.setMessageText("Enter command...");
        commandField.setTextFieldListener((field, c) -> {
            if (c == '\r' || c == '\n') {
                handleCommand(field.getText());
                field.setText("");
            }
        });

        outputLabel = new Label("", titleStyle);
        outputLabel.setWrap(true);

        // چیدمان درون کانتینر
        container.add(titleTable).expandX().fillX().row();
        container.add(commandField).width(580).pad(8).row();
        ScrollPane scroll = new ScrollPane(outputLabel, skin);
        scroll.setFadeScrollBars(false);
        scroll.setScrollingDisabled(true, false);
        container.add(scroll).width(580).height(240).pad(8).row();

        container.setTouchable(Touchable.disabled); // تا زمانی که باز نشده، غیرقابل لمس باشد

        // اضافه کردن کانتینر به این Group
        this.addActor(container);

        // مخفی اولیه
        this.setVisible(false);
    }

    public boolean isOpen() {
        return open;
    }

    /** باز کردن پنجره (با انیمیشن و فوکس) */
    public void open() {
        if (open) return;
        open = true;

        // آماده‌سازی: پاک کردن هر اکشن قبلی
        container.clearActions();

        // نشان دادن و فعال کردن تاچ بعد از ظاهر شدن
        this.setVisible(true);
        container.setTouchable(Touchable.enabled);

        // انیمیشن باز شدن و بعد از آن فوکس را بده
        float targetY = 20f;
        container.addAction(Actions.sequence(
            Actions.moveTo(container.getX(), targetY, 0.22f),
            Actions.run(() -> {
                // در پایان انیمیشن فوکس را به فیلد بده
                stage.setKeyboardFocus(commandField);
            })
        ));
    }

    /** بستن پنجره (فوراً فوکس را پاک می‌کنیم، سپس انیمیشن پنهان شدن) */
    public void close() {
        if (!open) return;
        open = false;

        // فوراً فوکس را پاک کن تا stage/field دیگر رودی را مصرف نکند
        stage.setKeyboardFocus(null);

        // پاک کردن اکشن‌های قبلی و انیمیشن پنهان شدن، سپس غیرقابل لمس و مخفی شدن
        container.clearActions();
        float hideY = -container.getHeight();
        container.addAction(Actions.sequence(
            Actions.moveTo(container.getX(), hideY, 0.22f),
            Actions.run(() -> {
                container.setTouchable(Touchable.disabled);
                this.setVisible(false);
            })
        ));
    }

    public void toggle() {
        if (open) close(); else open();
    }

    private void handleCommand(String text) {
        if (text == null || text.trim().isEmpty()) return;

        String[] parts = text.trim().split("\\s+");
        String command = parts[0].toLowerCase();

        switch (command) {
            case "help":
                outputLabel.setText("Available commands: help, clear, gold, add <itemName>");
                break;

            case "clear":
                outputLabel.setText("");
                break;

            case "gold":
                outputLabel.setText("Gold: 99999 (cheat not applied yet)");
                break;

            case "add":
                if (parts.length < 2) {
                    outputLabel.setText("Usage: add <itemName>");
                } else {
                    String itemName = parts[1].toLowerCase();

                    ItemFactory.createItem(itemName, player.getInventory());

                    outputLabel.setText("Added item: " + itemName);
                }
                break;
            case "recipe":
                if (parts.length < 2) {
                    outputLabel.setText("Please specify the recipe name.");
                } else {
                    String foodName = String.join("_", Arrays.copyOfRange(parts, 1, parts.length)).toUpperCase();
                    try {
                        FoodRecipe recipe = FoodRecipe.valueOf(foodName);
                        player.learnRecipe(recipe);
                        outputLabel.setText("Learned Recipe: " + recipe.getName());
                    } catch (IllegalArgumentException e) {
                        outputLabel.setText("No such recipe: " + foodName.replace("_", " "));
                    }
                }
                break;
            case "crafting_recipe":
                if (parts.length < 2) {
                    outputLabel.setText("Please specify the recipe name.");
                } else {
                    String craftingName = String.join("_", Arrays.copyOfRange(parts, 1, parts.length)).toLowerCase();
                    CraftingRecipe matched = null;
                    for (CraftingRecipe r : CraftingManager.getAllRecipes()) {
                        if (r.getItemName().equalsIgnoreCase(craftingName)) {
                            matched = r;
                            break;
                        }
                    }

                    if (matched != null) {
                        player.learnCraftingRecipe(matched.getItemName());
                        outputLabel.setText("Learned Crafting Recipe: " + matched.getItemName());
                    } else {
                        outputLabel.setText("No such crafting recipe: " + craftingName);
                    }
                }
                break;
            case "add_time" :
                if (parts.length < 2) {
                    outputLabel.setText("Please specify the time");
                }else {
                    try {
                        int hoursToAdd = Integer.parseInt(parts[1]);
                        DateAndTime.cheatHours(hoursToAdd);
                        outputLabel.setText("Added time: " + hoursToAdd);
                    }catch (NumberFormatException e) {
                        outputLabel.setText("Please specify a valid time");
                    }
                }
                break;
            case "add_money":
                if (parts.length < 2) {
                    outputLabel.setText("Please specify the money");
                }else {
                    try{
                        int moneyToAdd = Integer.parseInt(parts[1]);
                        player.addMoney(moneyToAdd);
                        outputLabel.setText("Added money: " + moneyToAdd);
                    }catch (NumberFormatException e) {
                        outputLabel.setText("Please specify a valid money");
                    }
                }
                break;
            default:
                outputLabel.setText("Unknown command: " + text);
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
