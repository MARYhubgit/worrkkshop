package io.github.some_example_name.controllers;


import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.Session;
import io.github.some_example_name.model.enums.MenuCommands;
import io.github.some_example_name.model.user.User;
import io.github.some_example_name.model.user.UserManager;
import io.github.some_example_name.views.MainMenu;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginController {
    public static Result login(String command, Scanner scanner) {
        Matcher matcher = MenuCommands.LOGIN.getPattern().matcher(command);
        if (!matcher.matches()) {
            return Result.failure("invalid command!");
        }
        String username = removeQuotes(matcher.group("username"));
        String password = removeQuotes(matcher.group("password"));
        boolean stayLoggedIn = matcher.group("stay") != null;
        User user = UserManager.findByUsername(username);

        if (user == null) {
            return Result.failure("invalid username!");
        }
        if (!checkPassword(password, user)) {
            return Result.failure("invalid password!");
        }
        Session.setCurrentUser(user);
        if (stayLoggedIn) UserManager.saveSession(user);
        String mainMenu = "main menu";
        MenuController.setMenu(new MainMenu(), mainMenu);
        UserManager.setCurrentUser(user);
        return Result.success("Login successful! You are now in main menu!");
    }

    public static Result handleForgotPassword(String command, Scanner scanner) {
        Matcher matcher = MenuCommands.FORGET_PASSWORD.getPattern().matcher(command);
        if (!matcher.matches()) {
            return Result.failure("invalid command!");
        }
        String username = matcher.group("username");


        User user = UserManager.findByUsername(username);
        if (user == null) {
            return Result.failure("invalid username!");
        }


        System.out.println(" Your security question is : " + user.getSecurityQuestion());
        System.out.print("Please enter your password in this form: answer -a <answer>");
        String answerCommand = scanner.nextLine();
        Matcher matcher2 = MenuCommands.ANSWER.getPattern().matcher(answerCommand);
        if (!matcher2.matches()) {
            return Result.failure("invalid answer!");
        }
        String answer = matcher2.group("answer");

        if (verifySecurityAnswer(answer, user)) {
            String message = processPasswordReset(user, scanner);
            return Result.success(message);
        } else {
            return Result.failure("Your answer is incorrect! Now you are redirecting to the login menu!");
        }
    }

    private static String processPasswordReset(User user, Scanner scanner) {
        System.out.println("Here is your choice for your password reset");
        System.out.println("1. generate new random password");
        System.out.println("2. enter new password");
        System.out.print("enter 1 or 2 : ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String newPassword;
        if (choice == 1) {
            newPassword = SignUpMenuController.generateStrongPassword();
            return ("You new password is : " + newPassword);
        } else {
            System.out.print("Enter your new password: ");
            newPassword = scanner.nextLine();
        }
        newPassword = hashSHA256(newPassword);


        user.setHashedPassword(newPassword);
        UserManager.updateUser(user);
        return ("password changed successfully!");
    }

    private static String removeQuotes(String value) {
        return value.startsWith("\"") && value.endsWith("\"")
            ? value.substring(1, value.length() - 1)
            : value;
    }


    public static Result changePasswordDirect(String oldPassword, String newPassword) {
        User user = UserManager.getCurrentUser();
        if (user == null) {
            return Result.failure("No user is currently logged in!");
        }

        // ۱) مطمئن شو رمز قبلی درست است
        if (!checkPassword(oldPassword, user)) {
            return Result.failure("Old password is incorrect");
        }

        // ۲) مطمئن شو رمز جدید با رمز قبلی فرق دارد
        if (oldPassword.equals(newPassword)) {
            return Result.failure("New password must differ from the old one");
        }

        // ۳) اعتبارسنجی ساختار رمز جدید
        String pwdMsg = SignUpMenuController.validatePassword(newPassword);
        if (!pwdMsg.equals("password is true")) {
            return Result.failure(pwdMsg);
        }

        // ۴) تغییر رمز
        user.setHashedPassword(hashSHA256(newPassword));
        UserManager.updateUser(user);
        return Result.success("Password changed successfully!");
    }



    public static boolean checkPassword(String inputPassword, User user) {
        String inputHashed = hashSHA256(inputPassword);
        return inputHashed.equals(user.getHashedPassword());
    }

    public static String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            System.out.println("Exseption:"+e.getMessage());
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    public static boolean verifySecurityAnswer(String inputAnswer, User user) {
        String inputHashed = hashSHA256(inputAnswer);
        return inputHashed.equals(user.getHashedSecurityAnswer());
    }

    /**
     * متد ساده برای لاگین در UI بدون Scanner و command parsing
     */
    public static Result login2(String username, String password) {
        if (username == null || username.isBlank()) {
            return Result.failure("Username cannot be empty");
        }
        if (password == null || password.isBlank()) {
            return Result.failure("Password cannot be empty");
        }
        User user = UserManager.findByUsername(username);
        if (user == null) {
            return Result.failure("Invalid username!");
        }
        if (!checkPassword(password, user)) {
            return Result.failure("Invalid password!");
        }
        Session.setCurrentUser(user);
        return Result.success("Login successful!");
    }

    public static Result login3(String username, String password, boolean stayLoggedIn) {
        if (username == null || username.isBlank())
            return Result.failure("Username cannot be empty");
        if (password == null || password.isBlank())
            return Result.failure("Password cannot be empty");

        User user = UserManager.findByUsername(username);
        if (user == null) return Result.failure("Invalid username!");

        if (!checkPassword(password, user))
            return Result.failure("Invalid password!");

        Session.setCurrentUser(user);
        if (stayLoggedIn) {
            UserManager.saveSession(user);
        }
        return Result.success("Login successful!");
    }

    /**
     * برمی‌گرداند سؤال امنیتی کاربر (یا خطا).
     */
    public static Result fetchSecurityQuestion(String username) {
        if (username == null || username.isBlank()) {
            return Result.failure("Username cannot be empty");
        }
        User user = UserManager.findByUsername(username);
        if (user == null) {
            return Result.failure("Invalid username!");
        }
        String q = user.getSecurityQuestion();
        if (q == null) {
            return Result.failure("No security question set for this user");
        }
        return Result.success(q);
    }

    /**
     * بررسی پاسخ و تنظیم رمز جدید (رندم) در صورت درست بودن.
     */
    public static Result resetPasswordWithAnswer(String username, String answer) {
        User user = UserManager.findByUsername(username);
        if (user == null) {
            return Result.failure("Invalid username!");
        }
        if (!verifySecurityAnswer(answer, user)) {
            return Result.failure("Incorrect answer!");
        }
        // تولید رمز جدید و هش‌شده
        String newPwd = SignUpMenuController.generateStrongPassword();
        String hashed = hashSHA256(newPwd);
        user.setHashedPassword(hashed);
        UserManager.updateUser(user);
        return Result.success("Your new password is: " + newPwd);
    }


}
