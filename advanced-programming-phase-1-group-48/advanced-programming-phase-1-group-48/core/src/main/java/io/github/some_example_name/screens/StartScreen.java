package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controllers.PreGameMenuController;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.views.PreGameMenuView;

public class StartScreen extends ScreenAdapter {
    private final Main game;
    private Stage stage;
    private Skin  skin;

    public StartScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        // 1) Stage + Input
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // 2) Skin پیش‌فرض (assets/skin/uiskin.json)
        skin = new Skin(Gdx.files.internal("skin/plain-james-ui.json"));

        // 3) چیدمان Table
        Table table = new Table();
        table.setFillParent(true);
        table.pad(20);

        // 4) بک‌گراند (اختیاری)
//        Drawable bg = skin.getDrawable("default-round"); // فقط برای پروتوتایپ
//        table.setBackground(bg);

        // 5) المان‌ها
        Label title = new Label("Stardew Clone", skin);
        title.setFontScale(2f);

        TextButton btnLogin   = new TextButton("Login", skin);
        TextButton btnSignup  = new TextButton("Sign Up", skin);
        TextButton btnPlay    = new TextButton("Play (Guest)", skin);
        TextButton btnExit    = new TextButton("Exit", skin);

        // 6) اضافه کردن به جدول
        table.add(title).colspan(2).padBottom(40).row();
        table.add(btnLogin).width(200).pad(10);
        table.add(btnSignup).width(200).pad(10).row();
        table.add(btnPlay).colspan(2).width(200).pad(10).row();
        table.add(btnExit).colspan(2).width(200).pad(10);

        stage.addActor(table);

        // 7) Listenerها
        btnLogin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new io.github.some_example_name.screens.LoginScreen(game));
            }
        });
        btnSignup.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new io.github.some_example_name.screens.SignupScreen(game));
            }
        });
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        // پاک‌سازی صفحه
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
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
