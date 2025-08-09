package io.github.some_example_name.views;


import io.github.some_example_name.controllers.MenuController;
import io.github.some_example_name.controllers.ProfileMenuController;
import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.enums.MenuCommands;

import java.util.Scanner;

public class ProfileMenu implements AppMenu{
    public void handleInput(String command, Scanner scanner) {
        if (command.startsWith("user info")) {
            ProfileMenuController.handleUserInfo();
        } else if(command.matches(MenuCommands.CHANGE_USERNAME.getPattern().pattern())){
            Result result = ProfileMenuController.changeUsername(command);
            AppView.printMessage(result.message());
        } else if(command.matches(MenuCommands.CHANGE_EMAIL.getPattern().pattern())){
            Result result = ProfileMenuController.changeEmail(command);
            AppView.printMessage(result.message());
        } else if(command.matches(MenuCommands.CHANGE_NICKNAME.getPattern().pattern())){
            Result result = ProfileMenuController.changeNickname(command);
            AppView.printMessage(result.message());
        }  else if(command.matches(MenuCommands.CHANGE_PASSWORD.getPattern().pattern())){
            Result result = ProfileMenuController.changePassword(command);
            AppView.printMessage(result.message());
        } else if(command.matches(MenuCommands.SHOW_CURRENT_MENU.getPattern().pattern())){
            MenuController.showCurrentMenu();
        } else if(command.matches(MenuCommands.MENU_ENTRANCE.getPattern().pattern())){
            MenuController.menuEntrance(command);
        }else {
            System.out.println("Invalid command");
        }
    }
}
