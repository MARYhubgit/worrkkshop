package io.github.some_example_name.controllers;


import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.Session;
import io.github.some_example_name.model.user.User;
import io.github.some_example_name.model.user.UserManager;
import io.github.some_example_name.screens.GameScreen;
import io.github.some_example_name.screens.StartScreen;
import io.github.some_example_name.views.*;

import java.util.Scanner;

public class MainMenuController {
    private MainMenu view;

    public void setView(MainMenu view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view != null) {
            if (view.getPlayButton().isChecked() && view.getField().getText().equals("kiarash")) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        }
    }

    public static void start(Scanner scanner) {
        User rememberedUser = UserManager.loadLastSession();
        if (rememberedUser != null) {
            Session.setCurrentUser(rememberedUser);
            showAutoLoginMenu(rememberedUser, scanner);
        } else {
            showSignupOrLogin(scanner);
        }
    }

    private static void showAutoLoginMenu(User user, Scanner scanner) {
        System.out.println("Auto-logged in as " + user.getUsername());
        System.out.println("1. Continue with this account");
        System.out.println("2. Logout and use different account");
        System.out.print("Enter your choice: ");
        int choice;
        choice = SignUpMenuController.getUserChoice(scanner);
        String gameMenu = "game menu";
        if (choice == 1) {
            UserManager.setCurrentUser(user);
            MenuController.setMenu(new GameMenu(), gameMenu);
            return;
        } else {
            Session.logout();
            UserManager.clearSession();
            showSignupOrLogin(scanner);
            return;
        }
    }

    private static void showSignupOrLogin(Scanner scanner) {
        while (!Session.isLoggedIn()) {
            System.out.println("You need to sign up or login first because there is no saved auto logged in");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.print("Enter your choice: ");

            int choice;
            choice = SignUpMenuController.getUserChoice(scanner);

            if (choice == 1) {
                String signupMenu = "signup menu";
                System.out.println("You are in signup menu now!");
                System.out.println("You can signup with this structure :\n" +
                        " register -u <username> -p <password> <password_confirm> -n <nickname> -e\n" +
                        "<email> -g <gender>");
                MenuController.setMenu(new SignUpMenu(), signupMenu);
                return;
            } else if (choice == 2) {
                String loginMenu = "login menu";
                System.out.println("You are in login menu now!");
                MenuController.setMenu(new LoginMenu(),loginMenu);
                return;
            } else {
                System.out.println("invalid input.");
            }
        }
    }

    public static void logout(Scanner scanner) {
        UserManager.setCurrentUser(null);
        Session.logout();
        System.out.println("User logged out , now you must choose one of the following options:");
        System.out.println("1. Sign Up");
        System.out.println("2. Login");
        System.out.print("Enter your choice: ");

        int choice;
        choice = SignUpMenuController.getUserChoice(scanner);

        if (choice == 1) {
            String signupMenu = "signup menu";
            MenuController.setMenu(new SignUpMenu(), signupMenu);
            return;
        } else if (choice == 2) {
            String loginMenu = "login menu";
            MenuController.setMenu(new LoginMenu(),loginMenu);
            return;
        } else {
            System.out.println("invalid input.");
        }
    }

    /** راه‌اندازی بازی (مثلاً رفتن به صفحهٔ بازی) */
    public void startGame(Main game) {
        System.out.println("Starting game...");
        if (!Session.isLoggedIn()) {
            System.out.println("session: ");
            game.setScreen(new StartScreen(game));
        } else {
            System.out.println("session is already logged in.");
            game.setScreen(new GameScreen(game));
        }
    }


    /** خروج از حساب کاربری */
    public void logout() {
        Session.logout();
        UserManager.clearSession();
    }
}
