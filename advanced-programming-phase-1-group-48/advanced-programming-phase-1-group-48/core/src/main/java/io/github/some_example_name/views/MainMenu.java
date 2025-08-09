package io.github.some_example_name.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.controllers.MainMenuController;
import io.github.some_example_name.controllers.MenuController;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.enums.MenuCommands;

import java.util.Scanner;

public class MainMenu implements AppMenu, Screen {
    private Stage stage;
    private final TextButton playButton;
    private final Label gameTitle;
    private final TextField field;
    public Table table;
    private final MainMenuController controller;

    public MainMenu(MainMenuController controller, Skin skin) {
        this.controller = controller;
        this.playButton = new TextButton("play", skin);
        this.gameTitle = new Label("This is a title", skin);
        this.field = new TextField("this is a field", skin);
        this.table = new Table();

        controller.setView(this);
    }

    public MainMenu() {
        this(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin());
    }

    public void handleInput(String command, Scanner scanner) {
        if (command.equals("Yes")) {
            MainMenuController.start(scanner);
        } else if (command.equals("No")) {
            System.exit(0);
        } else if(command.matches(MenuCommands.LOGOUT.getPattern().pattern())){
            MainMenuController.logout(scanner);
        } else if(command.matches(MenuCommands.SHOW_CURRENT_MENU.getPattern().pattern())){
            MenuController.showCurrentMenu();
        } else if(command.matches(MenuCommands.MENU_ENTRANCE.getPattern().pattern())){
            MenuController.menuEntrance(command);
        }
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();
        table.add(gameTitle);
        table.row().pad(10, 0 , 10 , 0);
        table.add(field).width(600);
        table.row().pad(10, 0 , 10 , 0);
        table.add(playButton);

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleMainMenuButtons();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    public TextButton getPlayButton() {
        return playButton;
    }

    public TextField getField() {
        return field;
    }
}
