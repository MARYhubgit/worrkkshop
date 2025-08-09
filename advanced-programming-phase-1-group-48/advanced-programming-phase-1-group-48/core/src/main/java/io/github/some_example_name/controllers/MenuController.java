package io.github.some_example_name.controllers;

import io.github.some_example_name.model.enums.MenuCommands;
import io.github.some_example_name.views.*;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MenuController {
    private static AppMenu currentMenu;
    private static String currentMenuName;

    public static void setMenu(AppMenu menu, String menuName) {
        currentMenu = menu;
        currentMenuName = menuName;
    }

    public static void showCurrentMenu() {
        System.out.println(currentMenuName);
    }

    public static void handleCommand(String command, Scanner scanner) {
        if (currentMenu != null) {
            currentMenu.handleInput(command, scanner);
        }
    }

    public static void menuEntrance(String command) {

    }
}
