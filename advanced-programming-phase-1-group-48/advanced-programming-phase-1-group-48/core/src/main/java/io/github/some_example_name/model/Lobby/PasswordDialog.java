package io.github.some_example_name.model.Lobby;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class PasswordDialog extends Dialog {
    private final LobbyInfo lobby;
    private final String username;
    private final Runnable onSuccess;

    private final TextField passwordField;

    public PasswordDialog(Stage stage, Skin skin, LobbyInfo lobby, String username, Runnable onSuccess) {
        super("Enter Password", skin);
        this.lobby = lobby;
        this.username = username;
        this.onSuccess = onSuccess;

        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setMessageText("Password");

        getContentTable().add(passwordField).width(250).pad(10);

        button("Join", true);
        button("Cancel", false);

        show(stage);
    }

    @Override
    protected void result(Object obj) {
        boolean joined = (Boolean) obj;
        if (joined) {
            String input = passwordField.getText();
            if (lobby.checkPassword(input)) {
                lobby.addPlayer(username);
                onSuccess.run();
            } else {
                passwordField.setText("");
                passwordField.setMessageText("Wrong password!");
            }
        }
    }
}
