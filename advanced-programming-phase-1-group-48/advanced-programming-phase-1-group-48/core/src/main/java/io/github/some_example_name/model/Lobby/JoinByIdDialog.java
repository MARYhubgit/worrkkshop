package io.github.some_example_name.model.Lobby;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class JoinByIdDialog extends Dialog {
    private final Skin skin;
    private final Stage stage;
    private final String username;

    private final TextField idField;
    private final TextField passwordField;

    public JoinByIdDialog(Stage stage, Skin skin, String username) {
        super("Join by Lobby ID", skin);
        this.skin = skin;
        this.stage = stage;
        this.username = username;

        idField = new TextField("", skin);
        idField.setMessageText("Lobby ID");

        passwordField = new TextField("", skin);
        passwordField.setMessageText("Password (if needed)");
        passwordField.setPasswordMode(true);

        getContentTable().add("Lobby ID:").left().pad(5);
        getContentTable().add(idField).width(200).pad(5).row();
        getContentTable().add("Password:").left().pad(5);
        getContentTable().add(passwordField).width(200).pad(5).row();

        button("Join", true);
        button("Cancel", false);

        show(stage);
    }

    @Override
    protected void result(Object obj) {
        boolean confirmed = (Boolean) obj;
        if (confirmed) {
            String id = idField.getText().trim();
            String password = passwordField.getText();
            LobbyInfo lobby = LobbyManager.joinById(id, password, username);

            if (lobby != null) {
                com.badlogic.gdx.Gdx.app.postRunnable(() ->
                    io.github.some_example_name.Main.getMain().setScreen(
                        new io.github.some_example_name.screens.InsideLobbyScreen(lobby)
                    ));
            } else {
                passwordField.setText("");
                idField.setMessageText("Not Found / Wrong Password");
            }
        }
    }
}
