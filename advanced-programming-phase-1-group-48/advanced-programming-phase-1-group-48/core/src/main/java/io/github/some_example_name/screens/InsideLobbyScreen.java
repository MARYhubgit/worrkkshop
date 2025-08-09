package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.Lobby.LobbyInfo;
import io.github.some_example_name.model.Lobby.LobbyManager;
import io.github.some_example_name.model.Session;

public class InsideLobbyScreen extends ScreenAdapter {
    private final LobbyInfo lobby;
    private Stage stage;
    private Skin skin;
    private Table playerTable;
    private TextButton startButton;
    private int lastPlayerListHash = -1;
    private boolean dialogShown = false;

    public InsideLobbyScreen(LobbyInfo lobby) {
        this.lobby = lobby;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/plain-james-ui.json"));
        Gdx.input.setInputProcessor(stage);

        Table root = new Table(skin);
        root.setFillParent(true);
        stage.addActor(root);

        Table topBar = new Table(skin);
        TextButton leaveButton = new TextButton("Leave", skin);
        startButton = new TextButton("Start Game", skin);
        Label title = new Label("Lobby: " + lobby.getName(), skin);
        Label idLabel = new Label("Lobby ID: " + lobby.getId(), skin);
        TextButton backToMenuBtn = new TextButton("Back to Menu", skin);

        title.setFontScale(1.2f);
        leaveButton.getLabel().setFontScale(0.9f);
        startButton.getLabel().setFontScale(0.9f);
        backToMenuBtn.getLabel().setFontScale(0.9f);

        topBar.add(backToMenuBtn).left().pad(10);
        topBar.add(leaveButton).left().pad(10);
        topBar.add(title).expandX().center();
        root.add(idLabel).colspan(2).padBottom(20).row();
        topBar.add(startButton).right().pad(10);

        root.add(topBar).expandX().fillX().padBottom(10).row();

        playerTable = new Table(skin);
        ScrollPane scrollPane = new ScrollPane(playerTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setScrollbarsOnTop(true);

        root.add(scrollPane).expand().fill().pad(10).row();

        leaveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = Session.getCurrentUser().getUsername();
                lobby.removePlayer(username);
                if (lobby.getPlayers().isEmpty()) {
                    LobbyManager.removeLobby(lobby.getId());
                }
                Gdx.app.postRunnable(() -> Main.getMain().setScreen(new LobbyScreen()));
            }
        });

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (canStartGame()) {
                    lobby.startGame();
                    // در آینده به صفحه بازی برو
                    // Gdx.app.postRunnable(() -> Main.getMain().setScreen(new GameScreen(lobby)));
                }
            }
        });
        backToMenuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.postRunnable(() -> Main.getMain().setScreen(new LobbyScreen()));
            }
        });


        updatePlayerList();
    }

    private void updatePlayerList() {
        playerTable.clear();
        String currentAdmin = lobby.getAdminUsername();
        for (String username : lobby.getPlayers()) {
            Label label = new Label(username, skin);
            if (username.equals(currentAdmin)) {
                label.setText(username + " (Admin)");
            }
            label.setFontScale(1.0f);
            playerTable.add(label).left().pad(5).row();
        }
    }

    private void updatePlayerListIfChanged() {
        int currentHash = lobby.getPlayers().hashCode();
        if (currentHash != lastPlayerListHash) {
            lastPlayerListHash = currentHash;
            updatePlayerList();
        }
    }

    private boolean canStartGame() {
        return lobby.getAdminUsername().equals(Session.getCurrentUser().getUsername())
            && lobby.getPlayers().size() >= 2;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        startButton.setDisabled(!canStartGame());
        updatePlayerListIfChanged();

        LobbyManager.cleanEmptyLobbies();

        if (!LobbyManager.lobbyExists(lobby.getId())) {
            showLobbyDeletedDialog();
        }
    }


    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }


    private void showLobbyDeletedDialog() {
        if (dialogShown) return;
        dialogShown = true;

        Dialog dialog = new Dialog("Lobby Expired", skin) {
            @Override
            protected void result(Object object) {
                Gdx.app.postRunnable(() -> Main.getMain().setScreen(new LobbyScreen()));
            }
        };
        dialog.text("this lobby is unavailable");
        dialog.button("ok", skin);
        dialog.show(stage);
    }
}
