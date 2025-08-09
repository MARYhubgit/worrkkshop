package io.github.some_example_name.controllers;



import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.enums.MenuCommands;
import io.github.some_example_name.model.user.User;
import io.github.some_example_name.model.user.UserManager;

import java.util.regex.Matcher;

public class ProfileMenuController {
    private static User currentUser = UserManager.getCurrentUser();

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void handleUserInfo() {
        System.out.printf("""
                        Username: %s
                        Nickname: %s
                        Highest Score: %d
                        Games Played: %d
                        """,
                currentUser.getUsername(),
                currentUser.getNickname(),
                currentUser.getHighestScore(),
                currentUser.getGameCount()
        );
    }

    public static Result changePassword(String command) {
        Matcher matcher = MenuCommands.CHANGE_PASSWORD.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("invalid command!");
        }
        String newPassword = matcher.group("newPassword");
        String oldPassword = matcher.group("oldPassword");

        String newHashedPassword ;
        newHashedPassword = LoginController.hashSHA256(newPassword);
        if (!currentUser.getHashedPassword().equals(newHashedPassword)) {
             return Result.failure("Your input password doesn't match your current password!");
        }

        if (newPassword.equals(oldPassword)) {
            return Result.failure("New password must not match your current password!");
        }

        String passwordValidation = SignUpMenuController.validatePassword(newPassword);

        if (!passwordValidation.equals("password is true")){
            return Result.failure(passwordValidation);
        }


        currentUser.setHashedPassword(newHashedPassword);
        UserManager.updateUser(currentUser);
        return Result.success("Password changed successfully!");
    }

    public static Result changeNickname(String command) {
        Matcher matcher = MenuCommands.CHANGE_NICKNAME.getPattern().matcher(command);
        if (!matcher.matches()) {
            System.out.println("invalid command!");
        }
        String newNickname = matcher.group("nickname");
        if (newNickname.equals(currentUser.getNickname())) {
            return Result.failure("new nickname must be different from current nickname!");
        }

        currentUser.setNickname(newNickname);
        UserManager.updateUser(currentUser);
        return Result.success("Nickname changed successfully!");
    }

    public static Result changeEmail(String command) {
        Matcher matcher = MenuCommands.CHANGE_EMAIL.getPattern().matcher(command);
        if (!matcher.matches()) {
            return Result.failure("invalid command!");
        }
        String newEmail = matcher.group("email");

        if (newEmail.equals(currentUser.getEmail())) {
            return Result.failure("new email must be different from current email!");
        }

        String emailValidation = SignUpMenuController.validateEmail(newEmail);

        if (!emailValidation.equals("email is true")){
            return Result.failure(emailValidation);
        }

        currentUser.setEmail(newEmail);
        UserManager.updateUser(currentUser);
        return Result.success("Email changed successfully!");
    }

    public static Result changeUsername(String command) {
        Matcher matcher = MenuCommands.CHANGE_USERNAME.getPattern().matcher(command);
        if (!matcher.matches()) {
            return Result.failure("invalid command!");
        }
        String newUsername = matcher.group("username");

        if (newUsername.equals(currentUser.getUsername())) {
            return  Result.failure("new username equals current username");
        }

        if (UserManager.userExists(newUsername)) {
            return Result.success("This username is already in use!");
        }

        UserManager.updateUsername(currentUser.getUsername(), newUsername);
        currentUser.setUsername(newUsername);
        return Result.success("Username changed successfully!");
    }

    // در کلاس ProfileMenuController، پس از متدهای فعلی:

    /** تغییر یوزرنیم بدون نیاز به کامندلاین */
    public static Result changeUsernameDirect(String newUsername) {
        // فرض می‌کنیم الگوی کامند: change username -u "<username>"
        String cmd = String.format("change username -u \"%s\"", newUsername);
        return changeUsername(cmd);
    }

    /** تغییر ایمیل */
    public static Result changeEmailDirect(String newEmail) {
        String cmd = String.format("change email -e \"%s\"", newEmail);
        return changeEmail(cmd);
    }

//    /** تغییر نیک‌نیم */
    public static Result changeNicknameDirect(String newNickname) {
        String cmd = String.format("change nickname -n \"%s\"", newNickname);
        return changeNickname(cmd);
    }

    public static Result changePasswordDirect(String oldPassword, String newPassword) {
        return LoginController.changePasswordDirect(oldPassword,newPassword);
    }

}
