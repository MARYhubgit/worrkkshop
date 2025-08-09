package io.github.some_example_name.views.Graphic;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.some_example_name.controllers.FriendshipController;
import io.github.some_example_name.model.Player.Player;



public class SocialScreen extends Table {
    private final Skin skin;
    private final Player player;

    private TextField tfUsername, tfMessage, tfItemName, tfAmount, tfGiftId, tfRating, tfRingName;
    private Label outputLabel;

    Skin otherSkin = new Skin(Gdx.files.internal("skin/plain-james-ui.json"),
        new TextureAtlas(Gdx.files.internal("skin/plain-james-ui.atlas")));

    public SocialScreen(Player player, Skin skin) {
        super(skin);
        this.player = player;
        this.skin = otherSkin;

        this.pad(10).top().left();
        this.defaults().pad(5).left();

        buildUI();
        this.setFillParent(false);     // چون داخل ScrollPane هست
        this.pack();                   // ابعاد خود را محاسبه کن
        this.invalidateHierarchy();
        this.setSize(1000, 600); // یا اندازه‌ای مناسب برای ارتفاع کل صفحه

    }

    private void buildUI() {
        tfUsername = new TextField("", skin);
        tfMessage = new TextField("", skin);
        tfItemName = new TextField("", skin);
        tfAmount = new TextField("", skin);
        tfGiftId = new TextField("", skin);
        tfRating = new TextField("", skin);
        tfRingName = new TextField("", skin);
        outputLabel = new Label("", skin);
        outputLabel.setWrap(true);

        add(new Label("Friend Username:", skin)); add(tfUsername).row();

        // Button: Show Friendships
        TextButton btnShowFriends = new TextButton("View Friendships", skin);
        btnShowFriends.addListener(click(() -> outputLabel.setText(FriendshipController.showFriendships(player))));
        add(btnShowFriends).colspan(2).row();

        add(new Label("Message:", skin)); add(tfMessage).row();
        add(button("Talk", () -> outputLabel.setText(FriendshipController.talk(player, tfUsername.getText(), tfMessage.getText())))).colspan(2).row();

        add(new Label("Gift Item:", skin)); add(tfItemName).row();
        add(new Label("Amount:", skin)); add(tfAmount).row();
        add(button("Send Gift", () -> {
            try {
                int amt = Integer.parseInt(tfAmount.getText());
                outputLabel.setText(FriendshipController.sendGift(player, tfUsername.getText(), tfItemName.getText(), amt));
            } catch (NumberFormatException e) {
                outputLabel.setText("Invalid amount.");
            }
        })).colspan(2).row();

        add(button("Gift History", () -> outputLabel.setText(FriendshipController.giftHistory(player, tfUsername.getText())))).colspan(2).row();
        add(button("List My Gifts", () -> outputLabel.setText(FriendshipController.listGifts(player)))).colspan(2).row();

        add(new Label("Gift ID:", skin)); add(tfGiftId).row();
        add(new Label("Rating (1-5):", skin)); add(tfRating).row();
        add(button("Rate Gift", () -> {
            try {
                int id = Integer.parseInt(tfGiftId.getText());
                int rating = Integer.parseInt(tfRating.getText());
                outputLabel.setText(FriendshipController.rateGift(player, id, rating));
            } catch (NumberFormatException e) {
                outputLabel.setText("Invalid input for ID or rating.");
            }
        })).colspan(2).row();

        add(button("Send Flower", () -> outputLabel.setText(FriendshipController.sendFlower(player, tfUsername.getText())))).colspan(2).row();
        add(button("Hug", () -> outputLabel.setText(FriendshipController.hugPlayer(player, tfUsername.getText())))).colspan(2).row();

        add(new Label("Ring Name:", skin)); add(tfRingName).row();
        add(button("Propose Marriage", () ->
            outputLabel.setText(FriendshipController.askMarriage(player, tfUsername.getText(), tfRingName.getText()))
        )).colspan(2).row();

        ScrollPane scroll = new ScrollPane(outputLabel, skin);
        scroll.setFadeScrollBars(false);
        scroll.setScrollingDisabled(true, false);
        add(scroll).height(120).width(400).colspan(2).row();
    }

    private TextButton button(String text, Runnable action) {
        TextButton btn = new TextButton(text, skin);
        btn.addListener(click(action));
        return btn;
    }

    private ClickListener click(Runnable action) {
        return new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        };
    }
}
