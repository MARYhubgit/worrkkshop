// LobbyScreen.java
package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.Lobby.*;
import io.github.some_example_name.model.Session;

public class LobbyScreen extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    private Table lobbyListTable;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/plain-james-ui.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("Lobbies", skin);
        table.add(title).colspan(2).padBottom(20).row();

        TextButton createBtn = new TextButton("Create Lobby", skin);
        TextButton refreshBtn = new TextButton("Refresh", skin);
        TextButton joinByIdBtn = new TextButton("Join by ID", skin);

        table.add(createBtn).pad(10);
        table.add(refreshBtn).pad(10).row();
        table.add(joinByIdBtn).colspan(2).pad(10).row();

        lobbyListTable = new Table(skin);
        ScrollPane lobbyListPane = new ScrollPane(lobbyListTable, skin);
        lobbyListPane.setFadeScrollBars(false);
        table.add(lobbyListPane).colspan(2).expand().fill().row();

        updateLobbyList();

        refreshBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                updateLobbyList();
            }
        });

        createBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                CreateLobbyDialog dialog = new CreateLobbyDialog(stage, skin);
                dialog.show(stage);
            }
        });

        joinByIdBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new JoinByIdDialog(stage, skin, Session.getCurrentUser().getUsername());
            }
        });

    }

    private void updateLobbyList() {
        lobbyListTable.clear();
        for (LobbyInfo lobby : LobbyManager.getVisibleLobbies()) {
            String label = lobby.getName() + " | Players: " + lobby.getPlayers().size() + " | ID: " + lobby.getId();
            TextButton joinBtn = new TextButton("Join", skin);
            Label lobbyLabel = new Label(label, skin);

            Table row = new Table(skin);
            row.add(lobbyLabel).expandX().left();
            row.add(joinBtn).right();
            lobbyListTable.add(row).fillX().pad(5).row();

            final LobbyInfo currentLobby = lobby; // این خیلی مهمه که final باشه
            joinBtn.addListener(new ChangeListener() {
                public void changed(ChangeEvent event, Actor actor) {
                    String username = Session.getCurrentUser().getUsername();

                    if (currentLobby.isPrivate()) {
                        // لابی رمز دارد → رمز را بپرس
                        new PasswordDialog(stage, skin, currentLobby, username, () -> {
                            Gdx.app.postRunnable(() ->
                                Main.getMain().setScreen(new InsideLobbyScreen(currentLobby))
                            );
                        });
                    } else {
                        LobbyInfo joined = LobbyManager.joinById(currentLobby.getId(), "", username);
                        if (joined != null) {
                            Gdx.app.postRunnable(() ->
                                Main.getMain().setScreen(new InsideLobbyScreen(joined))
                            );
                        } else {
                            System.out.println("Join failed!");
                        }
                    }
                }
            });


        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        LobbyManager.cleanEmptyLobbies();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
