package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controllers.LoginController;

import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.Session;
import io.github.some_example_name.model.game.Game;

import java.util.Scanner;

public class LoginScreen extends ScreenAdapter {
    private final Main game;          // کلاس اصلیِ Game
    private Stage stage;
    private Skin skin;
    private Table table;

    public LoginScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin  = new Skin(Gdx.files.internal("skin/plain-james-ui.json"));
        table = new Table(skin);
        table.setFillParent(true);
        table.pad(40);
        stage.addActor(table);

        // — TextFields —
        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");
        Label usernameError = new Label("", skin);
        usernameError.setColor(Color.RED);

        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Password");
        Label passwordError = new Label("", skin);
        passwordError.setColor(Color.RED);

        // — چک‌باکس Stay logged in —
        final CheckBox stayCheckbox = new CheckBox("Remember me", skin);

        // — دکمه‌ها —
        TextButton loginBtn    = new TextButton("Login", skin);
        TextButton signupBtn   = new TextButton("Sign Up", skin);
        TextButton forgotBtn   = new TextButton("Forgot Password?", skin);
        Label    msgLabel      = new Label("", skin);

        // — چیدمان جدول —
        table.add(new Label("Login", skin)).colspan(2).padBottom(20).row();

        table.add(usernameField).width(300).colspan(2).row();
        table.add(usernameError).colspan(2).padBottom(5).row();

        table.add(passwordField).width(300).colspan(2).row();
        table.add(passwordError).colspan(2).padBottom(5).row();

        table.add(stayCheckbox).colspan(2).padTop(5).row();

        // ردیف دکمه‌ها: Login و Sign Up
        Table btnRow = new Table(skin);
        btnRow.add(loginBtn).padRight(10);
        btnRow.add(signupBtn);
        table.add(btnRow).colspan(2).padTop(20).row();

        // لینک فراموشی رمز
        table.add(forgotBtn).colspan(2).padTop(10).row();

        // پیام کلی (مثلاً موفقیت ورود)
        table.add(msgLabel).colspan(2).padTop(15).row();

        // — Listenerها —

        // ۱) Login
        loginBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean valid = true;
                usernameError.setText("");
                passwordError.setText("");
                msgLabel.setText("");

                String user = usernameField.getText().trim();
                String pass = passwordField.getText().trim();
                boolean stay = stayCheckbox.isChecked();

                // Field-level validation
                if (user.isEmpty()) {
                    usernameError.setText("Username cannot be empty");
                    valid = false;
                }
                if (pass.isEmpty()) {
                    passwordError.setText("Password cannot be empty");
                    valid = false;
                }
                if (!valid) return;

                // فراخوانی کنترلر (اینجا یک متد login3 فرض می‌کنیم)
                Result result = LoginController.login3(user, pass, stay);
                if (result.success()) {
                    msgLabel.setText("Welcome back, " + user + "!");
                    game.setScreen(new MainMenuScreen(game));
                } else {
                    // پیام خطای کلی یا اختصاص به field
                    String m = result.message();
                    if (m.toLowerCase().contains("username")) {
                        usernameError.setText(m);
                    } else if (m.toLowerCase().contains("password")) {
                        passwordError.setText(m);
                    } else {
                        msgLabel.setText(m);
                    }
                }
            }
        });

        // ۲) Sign Up → بازگشت به منوی ثبت‌نام
        signupBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SignupScreen(game));
            }
        });

        // ۳) Forgot Password → نمایش صفحه یا دیالوگ فراموشی
        forgotBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new ForgotPasswordScreen(game));
                // یا: new SecurityQuestionDialog(...).show(stage);
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
