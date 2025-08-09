package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controllers.LoginController;
import io.github.some_example_name.model.Result;

public class ForgotPasswordScreen extends ScreenAdapter {
    private final Main game;
    private Stage stage;
    private Skin skin;

    public ForgotPasswordScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin  = new Skin(Gdx.files.internal("skin/plain-james-ui.json"));

        Table table = new Table(skin);
        table.setFillParent(true);
        table.pad(40);
        stage.addActor(table);

        // ۱) ورودی نام‌کاربری
        final TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");
        final Label usernameError = new Label("", skin);
        usernameError.setColor(1,0,0,1);

        // ۲) دکمهٔ دریافت سؤال
        TextButton getQuestionBtn = new TextButton("Get Security Question", skin);
        final Label questionLabel = new Label("", skin);
        questionLabel.setWrap(true);

        // ۳) ورودی پاسخ
        final TextField answerField = new TextField("", skin);
        answerField.setMessageText("Answer");
        final Label answerError = new Label("", skin);
        answerError.setColor(1,0,0,1);

        // ۴) دکمهٔ ارسال پاسخ
        TextButton submitBtn = new TextButton("Submit Answer", skin);
        final Label resultLabel = new Label("", skin);

        // ۵) دکمهٔ بازگشت
        TextButton backBtn = new TextButton("Back to Login", skin);

        // چیدمان
        table.add(new Label("Forgot Password", skin)).colspan(2).padBottom(20).row();

        table.add(usernameField).width(300).colspan(2).row();
        table.add(usernameError).colspan(2).padBottom(10).row();

        table.add(getQuestionBtn).colspan(2).padBottom(15).row();
        table.add(questionLabel).width(300).colspan(2).padBottom(15).row();

        table.add(answerField).width(300).colspan(2).row();
        table.add(answerError).colspan(2).padBottom(10).row();

        table.add(submitBtn).colspan(2).padBottom(15).row();
        table.add(resultLabel).colspan(2).padBottom(15).row();

        table.add(backBtn).colspan(2).row();

        // Listenerها

        // دریافت سؤال امنیتی
        getQuestionBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                usernameError.setText("");
                questionLabel.setText("");
                resultLabel.setText("");
                String user = usernameField.getText().trim();
                if (user.isEmpty()) {
                    usernameError.setText("Username cannot be empty");
                    return;
                }
                // فرض می‌کنیم متدی در LoginController داریم که سؤال را برمی‌گرداند:
                Result res = LoginController.fetchSecurityQuestion(user);
                if (res.success()) {
                    questionLabel.setText("Your question: " + res.message());
                } else {
                    usernameError.setText(res.message());
                }
            }
        });

        // ارسال پاسخ
        submitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                answerError.setText("");
                resultLabel.setText("");
                String user = usernameField.getText().trim();
                String ans  = answerField.getText().trim();
                if (ans.isEmpty()) {
                    answerError.setText("Answer cannot be empty");
                    return;
                }
                // فرض می‌کنیم متد زیر را هم اضافه کرده‌ایم:
                Result res = LoginController.resetPasswordWithAnswer(user, ans);
                if (res.success()) {
                    resultLabel.setText(res.message());
                } else {
                    answerError.setText(res.message());
                }
            }
        });

        // بازگشت به صفحهٔ لاگین
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LoginScreen(game));
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
