package io.github.some_example_name.model.Lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.some_example_name.model.Session;
import io.github.some_example_name.model.database.DatabaseLobbyManager;

public class CreateLobbyDialog extends Dialog {
    public CreateLobbyDialog(Stage stage, Skin skin) {
        super("Create Lobby", skin);

        TextField nameField = new TextField("", skin);
        CheckBox privateBox = new CheckBox("Private", skin);
        CheckBox visibleBox = new CheckBox("Visible", skin);
        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setMessageText("Password");

        getContentTable().add("Lobby Name").left().row();
        getContentTable().add(nameField).width(250).row();
        getContentTable().add(privateBox).left().row();
        getContentTable().add(visibleBox).left().row();
        getContentTable().add(passwordField).width(250).row();

        getButtonTable().add(new TextButton("Cancel", skin));
        TextButton ok = new TextButton("Create", skin);
        getButtonTable().add(ok);

        ok.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                String name = nameField.getText();
                boolean isPrivate = privateBox.isChecked();
                boolean isVisible = visibleBox.isChecked();
                String password = isPrivate ? passwordField.getText() : null;
                String username = Session.getCurrentUser().getUsername();

                LobbyInfo lobby = LobbyManager.createLobby(name, isPrivate, isVisible, password, username);

                // ذخیره فقط در صورتی که قبلاً ثبت نشده باشد
                if (!DatabaseLobbyManager.lobbyExists(lobby.getId())) {
                    DatabaseLobbyManager.saveLobby(lobby);
                }

                Gdx.app.postRunnable(() -> {
                    hide();
                    io.github.some_example_name.Main.getMain().setScreen(new io.github.some_example_name.screens.InsideLobbyScreen(lobby));
                });
            }
        });
    }
}
