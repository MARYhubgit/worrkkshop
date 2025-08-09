package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.controllers.ProfileMenuController;
import io.github.some_example_name.model.Result;
import io.github.some_example_name.Main;

public class ProfileScreen extends ScreenAdapter {
    private final Main game;
    private Stage stage;
    private Skin  skin;

    public ProfileScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/plain-james-ui.json"));

        Table table = new Table(skin);
        table.setFillParent(true);
        table.pad(20);
        stage.addActor(table);

        // نمایش اطلاعات فعلی
        Label lblUser  = new Label("Username: "  + ProfileMenuController.getCurrentUser().getUsername(), skin);
        Label lblNick  = new Label("Nickname: "  + ProfileMenuController.getCurrentUser().getNickname(), skin);
        Label lblEmail = new Label("Email: "     + ProfileMenuController.getCurrentUser().getEmail(), skin);

        // فیلدها برای تغییر
        final TextField tfUsername = new TextField("", skin);
        tfUsername.setMessageText("New Username");
        final TextField tfEmail    = new TextField("", skin);
        tfEmail.setMessageText("New Email");
        final TextField tfNick     = new TextField("", skin);
        tfNick.setMessageText("New Nickname");
        final TextField tfOldPass  = new TextField("", skin);
        tfOldPass.setPasswordMode(true);
        tfOldPass.setPasswordCharacter('*');
        tfOldPass.setMessageText("Old Password");
        final TextField tfNewPass  = new TextField("", skin);
        tfNewPass.setPasswordMode(true);
        tfNewPass.setPasswordCharacter('*');
        tfNewPass.setMessageText("New Password");

        // دکمه‌ها و پیام
        TextButton btnChangeUser = new TextButton("Change Username", skin);
        TextButton btnChangeEmail= new TextButton("Change Email", skin);
        TextButton btnChangeNick = new TextButton("Change Nickname", skin);
        TextButton btnChangePass = new TextButton("Change Password", skin);
        TextButton btnBack       = new TextButton("Back to Main Menu", skin);
        final Label msgLabel     = new Label("", skin);

        // چینش در جدول
        table.add(lblUser).colspan(2).padBottom(10).row();
        table.add(lblNick).colspan(2).padBottom(10).row();
        table.add(lblEmail).colspan(2).padBottom(20).row();

        table.add(tfUsername).width(300).pad(5);
        table.add(btnChangeUser).pad(5).row();

        table.add(tfEmail).width(300).pad(5);
        table.add(btnChangeEmail).pad(5).row();

        table.add(tfNick).width(300).pad(5);
        table.add(btnChangeNick).pad(5).row();

        table.add(tfOldPass).width(300).pad(5);
        table.add(tfNewPass).width(300).pad(5).row();
        table.add().colspan(2).pad(5).row(); // فاصله
        table.add(btnChangePass).colspan(2).pad(5).row();

        table.add(btnBack).colspan(2).padTop(20).row();
        table.add(msgLabel).colspan(2).padTop(10);

        // Listenerها
        btnChangeUser.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Result res = ProfileMenuController.changeUsernameDirect(tfUsername.getText().trim());
                msgLabel.setText(res.message());
            }
        });
        btnChangeEmail.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Result res = ProfileMenuController.changeEmailDirect(tfEmail.getText().trim());
                msgLabel.setText(res.message());
            }
        });
        btnChangeNick.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Result res = ProfileMenuController.changeNicknameDirect(tfNick.getText().trim());
                msgLabel.setText(res.message());
            }
        });
        btnChangePass.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String oldP = tfOldPass.getText().trim();
                String newP = tfNewPass.getText().trim();
                Result res = ProfileMenuController.changePasswordDirect(oldP, newP);
                msgLabel.setText(res.message());
            }
        });
        btnBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f,0.1f,0.1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
