package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.controllers.MainMenuController;
import io.github.some_example_name.Main;
import io.github.some_example_name.controllers.PreGameMenuController;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.Session;
import io.github.some_example_name.views.PreGameMenuView;

public class MainMenuScreen extends ScreenAdapter {
    private final Main game;
    private Stage stage;
    private Skin  skin;
    private MainMenuController controller;

    public MainMenuScreen(Main game) {
        this.game = game;
        this.controller = new MainMenuController();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/plain-james-ui.json"));

        Table table = new Table(skin);
        table.setFillParent(true);
        table.center();
        table.pad(20);

        Label title = new Label("Stardew Clone", skin);
        title.setFontScale(2f);

        TextButton playBtn    = new TextButton("Play", skin);
        TextButton profileBtn = new TextButton("Profile", skin);
        TextButton logoutBtn  = new TextButton("Logout", skin);
        TextButton exitBtn    = new TextButton("Exit", skin);
        TextButton lobbyBtn    = new TextButton("Lobby", skin);

        table.add(title).colspan(2).padBottom(40).row();
        table.add(playBtn).width(200).pad(10);
        table.add(profileBtn).width(200).pad(10).row();
        table.add(logoutBtn).width(200).pad(10);
        table.add(exitBtn).width(200).pad(10).row();
        table.add(lobbyBtn).width(200).pad(10);

        stage.addActor(table);

        // لیسنرها
        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    System.out.println("Play clicked! Session.isLoggedIn=" + Session.isLoggedIn());
                    Main.getMain().getScreen().dispose();
                    System.out.println("here");
                    Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                    System.out.println("here2");
                    // اگر startGame خودش GameScreen نمی‌سازد، این خط را اینجا بگذار:
                    // game.setScreen(new GameScreen(game));
                }catch (Exception e){
                    System.out.println("message: " + e.getMessage());}
            }
        });
        profileBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new ProfileScreen(game));
            }
        });
        logoutBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.logout();
                game.setScreen(new StartScreen(game));
            }
        });
        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        lobbyBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    System.out.println("Play clicked! Session.isLoggedIn=" + Session.isLoggedIn());
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new LobbyScreen());
                } catch (Exception e) {
                    System.out.println("message: " + e.getMessage());
                }
            }
        });

    }

    @Override
    public void render(float delta) {
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
