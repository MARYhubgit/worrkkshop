package io.github.some_example_name.controllers;


import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.enums.MenuCommands;
import io.github.some_example_name.model.user.User;
import io.github.some_example_name.model.user.UserManager;
import io.github.some_example_name.views.LoginMenu;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

public class SignUpMenuController {
    private static final int MIN_PASSWORD_LENGTH = 8;
    public static final List<String> SECURITY_QUESTIONS = Arrays.asList(
            "What city were you born in?",
            "What was your first best friend name?",
            "What was your last best friend name?",
            "What was the name of your first school?",
            "What is your favorite color?",
            "What is your favorite food?",
            "What was your childhood nickname?"
    );
    private static final int MAX_PASSWORD_ATTEMPTS = 3;
    private static String selectedQuestion;
    private static String securityAnswer;

    public static Result signup(String command, Scanner scanner) {
        Matcher matcher = MenuCommands.REGISTER.getPattern().matcher(command);
        if (!matcher.matches()) {
            return Result.failure("invalid command!");
        }
        String username = matcher.group("username");
        String password = matcher.group("password");
        String passwordConfirm = matcher.group("passwordConfirm");
        String nickname = matcher.group("nickname");
        String email = matcher.group("email");
        String gender = matcher.group("gender");
        if (username.startsWith("\"") && username.endsWith("\"")) {
            username = username.substring(1, username.length() - 1);
        }

        if (!isUsernameValid(username)) {
            return Result.failure("invalid username!");
        }
        String newUserName;
        if (UserManager.userExists(username)) {
            System.out.println("Username already exists! Now a new username will be shown!");

            newUserName = generateNewUsername(username, scanner);
            if (newUserName.equals("Generating new username...")) {
                newUserName = generateNewUsername(username, scanner);
            }
            username = newUserName;
        }

        String message = validateEmail(email);
        if (!message.equals("email is true")) {
            return Result.failure(message);
        }

        if (password.equalsIgnoreCase("random") || passwordConfirm.equalsIgnoreCase("random")) {
            String handledPassword;
            handledPassword = handleRandomPassword(scanner);
            if (handledPassword.equals("Returning to signup menu")) {
                return Result.failure("Returning to signup menu");
            } else if (handledPassword.equals("Generate new random password")) {
                handledPassword = handleRandomPassword(scanner);
            }

            password = handledPassword;
            passwordConfirm = handledPassword;

        }

        String confirmPasswordMessage;
        if (!password.equals(passwordConfirm)) {
            confirmPasswordMessage = handlePasswordEntry(scanner, password);
            if (!confirmPasswordMessage.startsWith("newPassword")) {
                return Result.failure(confirmPasswordMessage);
            } else {
                String[] parts = confirmPasswordMessage.split(" : ");
                String newPassword = parts[1];
                password = newPassword;
                passwordConfirm = newPassword;
            }
        }
        String passwordMessage = validatePassword(password);
        if (!passwordMessage.equals("password is true")) {
            passwordMessage = validatePassword(password);
            return Result.failure(passwordMessage);
        }
        String SecurityQuestionMessage;
        SecurityQuestionMessage = processSecurityQuestion(username, password, nickname, email, gender, scanner);
        return Result.success(SecurityQuestionMessage);
    }

    private static String processSecurityQuestion(String username, String password, String nickname, String email,
                                                  String gender, Scanner scanner) {
        displaySecurityQuestions();

        while (true) {
            System.out.print("\nEnter security question selection : ");
            String input = scanner.nextLine().trim();
            Matcher matcher = MenuCommands.PICK_QUESTION.getPattern().matcher(input);

            if (!matcher.matches()) {
                System.out.println("Invalid format. Enter your answer in right format");
                continue;
            }
            try {
                int qNum = Integer.parseInt(matcher.group("questionNumber"));
                String answer = matcher.group("answer").trim();
                String confirm = matcher.group("answerConfirm").trim();

                if (qNum < 1 || qNum > SECURITY_QUESTIONS.size()) {
                    System.out.println("Question number must be between 1 and " + SECURITY_QUESTIONS.size());
                    continue;
                }

                if (!answer.equals(confirm)) {
                    System.out.println("Answer and confirmation don't match!");
                    continue;
                }

                selectedQuestion = SECURITY_QUESTIONS.get(qNum - 1);
                securityAnswer = answer;
                User newUser = new User(username, password, nickname, email, gender, selectedQuestion, securityAnswer);
                UserManager.addUser(newUser);
                String loginMenu = "login menu";
                MenuController.setMenu(new LoginMenu(),loginMenu);
                return ("Registration completed successfully , You are now in login menu!");
            } catch (NumberFormatException e) {
                System.out.println("Question number must be a valid integer");
            }
        }
    }

    private static void displaySecurityQuestions() {
        System.out.println("\nPlease select a security question:");
        for (int i = 0; i < SECURITY_QUESTIONS.size(); i++) {
            System.out.println((i + 1) + ". " + SECURITY_QUESTIONS.get(i));
        }
        System.out.println("\nUse command: pick question -q <question_number> -a <answer> -c <answer_confirm>");
    }


//    public static String validatePassword(String password) {
//        String allowedSpecialChars = "?><,\"';;:/\\|][}{+=)(*&^%$#!";
//        boolean hasLower = false;
//        boolean hasUpper = false;
//        boolean hasDigit = false;
//        boolean hasSpecial = false;
//
//        for (char c : password.toCharArray()) {
//            if (Character.isLowerCase(c)) {
//                hasLower = true;
//            } else if (Character.isUpperCase(c)) {
//                hasUpper = true;
//            } else if (Character.isDigit(c)) {
//                hasDigit = true;
//            } else if (allowedSpecialChars.indexOf(c) != -1) {
//                hasSpecial = true;
//            } else {
//                return ("Password contains invalid character: " + c);
//            }
//        }
//
//        StringBuilder errors = new StringBuilder();
//
//        if (password.length() < 8) {
//            errors.append("Password must be at least 8 characters. ");
//        }
//        if (!hasLower) {
//            errors.append("Password must contain at least one lowercase letter. ");
//        }
//        if (!hasUpper) {
//            errors.append("Password must contain at least one uppercase letter. ");
//        }
//        if (!hasDigit) {
//            errors.append("Password must contain at least one number. ");
//        }
//        if (!hasSpecial) {
//            errors.append("Password must contain at least one special character. ");
//        }
//
//        if (errors.length() > 0) {
//            return (errors.toString());
//        }
//
//        return "password is true";
//    }

//    public static String validateEmail(String email) {
//        int atIndex = email.indexOf('@');
//        if (atIndex == -1) {
//            return ("Email must contain exactly one @ symbol");
//        }
//        if (email.indexOf('@', atIndex + 1) != -1) {
//            return ("Email must contain exactly one @ symbol");
//        }
//
//        String username = email.substring(0, atIndex);
//
//        if (username.startsWith("\"") && username.endsWith("\"")) {
//            username = username.substring(1, username.length() - 1);
//        }
//        String domain = email.substring(atIndex + 1);
//
//        if (username.isEmpty()) {
//            return ("Username part cannot be empty");
//        }
//
//        if (!isAlphanumeric(username.charAt(0)) || !isAlphanumeric(username.charAt(username.length() - 1))) {
//            System.out.println(username);
//            return ("Username must start and end with letter or number");
//        }
//
//        boolean hasDot = false;
//        for (int i = 0; i < username.length(); i++) {
//            char c = username.charAt(i);
//            if (!isValidUsernameChar(c)) {
//                return ("Username contains invalid character: " + c);
//            }
//            if (c == '.' && i > 0 && username.charAt(i - 1) == '.') {
//                return ("Username cannot have consecutive dots");
//            }
//        }
//
//        if (domain.isEmpty()) {
//            return ("Domain part cannot be empty");
//        }
//
//        int lastDot = domain.lastIndexOf('.');
//        if (lastDot == -1) {
//            return ("Domain must contain at least one dot");
//        }
//
//        String extension = domain.substring(lastDot + 1);
//        if (extension.length() < 2) {
//            return ("Domain extension must be at least 2 characters");
//        }
//
//        for (int i = 0; i < domain.length(); i++) {
//            char c = domain.charAt(i);
//            if (!isValidDomainChar(c)) {
//                return ("Domain contains invalid character: " + c);
//            }
//        }
//
//        String forbiddenChars = "?><,\"';;:/\\|][}{+=)(*&^%$#!";
//        for (char c : email.toCharArray()) {
//            if (forbiddenChars.indexOf(c) != -1) {
//                return ("Email contains forbidden character: " + c);
//            }
//        }
//        return "email is true";
//    }

    private static String handlePasswordEntry(Scanner scanner, String password) {
        int attempts = 0;
        System.out.println("Your password and confirmation do not match. Please try again.");

        while (attempts < MAX_PASSWORD_ATTEMPTS) {
            System.out.print("Enter password: ");
            String newPassword = scanner.nextLine().trim();

            System.out.print("Confirm password: ");
            String passwordConfirm = scanner.nextLine().trim();

            if (!newPassword.equals(passwordConfirm)) {
                attempts++;
                System.out.println("Password and confirmation do not match");
                System.out.println("Remaining attempts: " + (MAX_PASSWORD_ATTEMPTS - attempts));

                if (attempts < MAX_PASSWORD_ATTEMPTS) {
                    System.out.println("1. Try again");
                    System.out.println("2. Return to signup menu");
                    System.out.print("Your choice: ");

                    int choice;
                    choice = getUserChoice(scanner);
                    if (choice == 2) {
                        return ("Returned to signup menu");
                    }
                }
            } else {
                String validation = validatePassword(newPassword);
                if (!validation.equals("password is true")) {
                    System.out.println(validation);
                    attempts++;
                } else {
                    return ("newPassword is true and it is : " + newPassword);
                }
            }
        }
        return ("Maximum attempts reached. Returning to main menu");
    }

    private static String handleRandomPassword(Scanner scanner) {
        String randomPassword = generateStrongPassword();
        System.out.println("Your generated password is: " + randomPassword);
        System.out.println("Do you accept this password? (yes/no)");

        boolean userAccepts = getUserAcceptance(scanner);
        if (userAccepts) {
            //System.out.println("its here");
            return (randomPassword);
        } else {
            System.out.println("Choose an option:");
            System.out.println("1. Generate new random password");
            System.out.println("2. Return to signup menu");

            int choice = getUserChoice(scanner);
            if (choice == 1) {
                return "Generate new random password";
            } else {
                return ("Returning to signup menu");
            }
        }
    }

    public static String generateStrongPassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "?><,\"';;:/\\|][}{+=)(*&^%$#!";
        String all = upper + lower + digits + special;

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(special.charAt(random.nextInt(special.length())));

        for (int i = 4; i < 12; i++) {
            password.append(all.charAt(random.nextInt(all.length())));
        }
        // Shuffle the characters
        char[] chars = password.toString().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int j = random.nextInt(chars.length);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        return new String(chars);
    }

    private static String generateNewUsername(String username, Scanner scanner) {
        String newUserName = generateAlternateUsername(username);
        System.out.println("Your generated username is: " + newUserName);
        System.out.println("Do you accept this username? (yes/no)");

        boolean userAccepts = getUserAcceptance(scanner);
        if (userAccepts) {
            return (newUserName);
        } else {
            return ("Generating new username...");
        }
    }

    public static String generateAlternateUsername(String baseUsername) {
        Random random = new Random();
        String newUserName = baseUsername + "-" + (random.nextInt(999) + 1);
        if (UserManager.userExists(newUserName)) {
            generateAlternateUsername(baseUsername);
        }
        return newUserName;
    }

    public static boolean getUserAcceptance(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            }
            System.out.println("Please enter 'yes' or 'no':");
        }
    }

    public static int getUserChoice(Scanner scanner) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice == 1 || choice == 2) {
                    return choice;
                }
                System.out.println("Please enter 1 or 2:");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (1 or 2):");
            }
        }
    }


    private static boolean isAlphanumeric(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
    }

    public static boolean isValidUsernameChar(char c) {
        return isAlphanumeric(c) || c == '.' || c == '-' || c == '_';
    }

    private static boolean isValidDomainChar(char c) {
        return isAlphanumeric(c) || c == '.' || c == '-';
    }

    private static boolean isUsernameValid(String username) {
        if(!(username.matches("^[a-zA-Z0-9-]+$"))){
            System.out.println("username Not matched  :::" +username);
        }
        return username.matches("^[a-zA-Z0-9-]+$");
    }
        /**
         * متد ساده برای محیط گرافیکی:
         * فقط اعتبارسنجی می‌کند و در صورت موفقیت، کاربر را می‌سازد.
         */
        public static Result signup2(
            String username,
            String password,
            String passwordConfirm,
            String nickname,
            String email,
            String gender
        ) {
            // ۱) نام‌کاربری
            if (username == null || username.isBlank()) {
                return Result.failure("Username cannot be empty");
            }
            if (!username.matches("^[a-zA-Z0-9_-]+$")) {
                return Result.failure("Username can only contain letters, numbers, underscore or hyphen");
            }
            if (UserManager.userExists(username)) {
                return Result.failure("Username already exists");
            }

            // ۲) ایمیل
            String emailMsg = validateEmail(email);
            if (!emailMsg.equals("email is true")) {
                return Result.failure(emailMsg);
            }

            // ۳) رمز عبور
            if (!password.equals(passwordConfirm)) {
                return Result.failure("Passwords do not match");
            }
            String pwdMsg = validatePassword(password);
            if (!pwdMsg.equals("password is true")) {
                return Result.failure(pwdMsg);
            }

            // ۴) نیک‌نیم
            if (nickname == null || nickname.isBlank()) {
                return Result.failure("Nickname cannot be empty");
            }

            // ۵) جنسیت (اختیاری)
            if (gender == null || gender.isBlank()) {
                return Result.failure("Please select a gender");
            }

            // ۶) ساخت و ذخیره کاربر
            User newUser = new User(username, password, nickname, email, gender, null, null);
            UserManager.addUser(newUser);

            return Result.success("Signup successful! Please login.");
        }

        public static String validatePassword(String password) {
            if (password.length() < MIN_PASSWORD_LENGTH) {
                return "Password must be at least " + MIN_PASSWORD_LENGTH + " characters";
            }
            boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
            boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
            boolean hasDigit = password.chars().anyMatch(Character::isDigit);
            boolean hasSpecial = password.chars().anyMatch(c -> "?><,\"';;:/\\|][}{+=)(*&^%$#!".indexOf(c) != -1);

            System.out.println("Password: " + password);
            if (!hasLower) return "Password must contain a lowercase letter";
            if (!hasUpper) return "Password must contain an uppercase letter";
            if (!hasDigit) return "Password must contain a digit";
            if (!hasSpecial) return "Password must contain a special character";

            return "password is true";
        }

        public static String validateEmail(String email) {
            if (email == null || email.isBlank()) {
                return "Email cannot be empty";
            }
            // ساده: یک @ و یک . پس از آن
            int at = email.indexOf('@'), dot = email.lastIndexOf('.');
            if (at < 1 || dot < at + 2 || dot == email.length() - 1) {
                return "Invalid email format";
            }
            return "email is true";
        }

        // اگر نیاز به هش کردن داری (مثل LoginController)
        private static String hashSHA256(String input) {
            // ... کد همانی که در LoginController است ...
            return LoginController.hashSHA256(input);
        }
    }

