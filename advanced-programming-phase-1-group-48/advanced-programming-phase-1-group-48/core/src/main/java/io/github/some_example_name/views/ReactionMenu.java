package io.github.some_example_name.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Reaction.Reaction;

public class ReactionMenu extends Window {

    private static final int[] defaultEmojiIndices = {0, 1, 2, 3, 4}; // قابل تغییر در آینده
    private TextField messageField;
    Skin otherSkin = new Skin(Gdx.files.internal("skin/plain-james-ui.json"),
        new TextureAtlas(Gdx.files.internal("skin/plain-james-ui.atlas")));

    public ReactionMenu(Skin skin, Player player) {
        super("React", skin);

        defaults().pad(5);
        setSize(300, 400);
        setPosition(100, 100);

        Table emojiTable = new Table();
        for (int emojiIndex : defaultEmojiIndices) {
            String path = String.format("Emoji/Emojis%03d.png", emojiIndex);
            Texture emojiTexture = new Texture(Gdx.files.internal(path));
            Image emojiImage = new Image(new TextureRegion(emojiTexture));

            TextButton button = new TextButton("", otherSkin);
            button.setSize(48, 48);
            button.add(emojiImage).size(32, 32).pad(5);
            button.align(Align.center);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    String msg = messageField.getText().trim();
                    if (!msg.isEmpty()) {

                        TextureRegion textureRegion = new TextureRegion(emojiTexture);
                        player.setReaction(new Reaction(textureRegion, msg));
                    } else {
                        TextureRegion textureRegion = new TextureRegion(emojiTexture);
                        player.setReaction(new Reaction(textureRegion, null));
                    }
                    setVisible(false);
                }
            });


            emojiTable.add(button).pad(5);
            if (emojiTable.getChildren().size % 2 == 0) emojiTable.row();
        }

        ScrollPane scrollPane = new ScrollPane(emojiTable, otherSkin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setScrollbarsOnTop(true);

        add(new Label("Choose Emoji:", otherSkin)).left().row();
        add(scrollPane).height(150).width(260).row();


        messageField = new TextField("", otherSkin);
        messageField.setMessageText("Say something...");
        messageField.setMaxLength(10);

        add(new Label("Or Type Message (max 10):", otherSkin)).left().row();
        add(messageField).width(250).row();


        TextButton sendMessage = new TextButton("Send Text", otherSkin);
        sendMessage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String msg = messageField.getText().trim();
                if (!msg.isEmpty()) {
                    Reaction textReaction = new Reaction(null, msg); // 👈 ساخت یک واکنش فقط متنی
                    player.setReaction(textReaction);                // 👈 ثبت آن برای نمایش بالای سر
                    setVisible(false);
                }
            }
        });

        add(sendMessage).padTop(10).row();


        TextButton close = new TextButton("Close", skin);
        close.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setVisible(false);
            }
        });
        add(close).padTop(5).row();

        pack();
    }
}
