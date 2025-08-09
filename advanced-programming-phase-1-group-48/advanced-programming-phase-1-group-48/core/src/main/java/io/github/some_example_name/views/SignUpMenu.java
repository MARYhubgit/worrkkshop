package io.github.some_example_name.views;


import io.github.some_example_name.controllers.MenuController;
import io.github.some_example_name.controllers.SignUpMenuController;
import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.enums.MenuCommands;

import java.util.Scanner;

public class SignUpMenu implements AppMenu{
    public void handleInput(String command, Scanner scanner) {
        if(command.matches(MenuCommands.REGISTER.getPattern().pattern())){
            Result result = SignUpMenuController.signup(command, scanner);
            AppView.printMessage(result.message());
        } else if(command.matches(MenuCommands.SHOW_CURRENT_MENU.getPattern().pattern())){
            MenuController.showCurrentMenu();
        } else if(command.matches(MenuCommands.MENU_ENTRANCE.getPattern().pattern())){
            MenuController.menuEntrance(command);
        } else {
            System.out.println("Invalid command");
        }
    }
}
