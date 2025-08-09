package io.github.some_example_name.model.user;

import io.github.some_example_name.model.user.inventory.Inventory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String username;
    private String hashedPassword;
    private String nickname;
    private String email;
    private String gender;
    private String securityQuestion;
    private String hashedSecurityAnswer;
    private int score = 0;
    private int highestScore = 0;
    private int gameCount = 0;
    private int energy;
    private Inventory inventory;
    private Wallet wallet;
    private EnergySystem energySystem = new EnergySystem();

    public User(String username, String password, String nickname,
                String email, String gender, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.hashedPassword = hashSHA256(password);
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.securityQuestion = securityQuestion;
        this.hashedSecurityAnswer = hashSHA256(securityAnswer);
        this.energy = 200;
        this.wallet = new Wallet();
    }

    public User(String username, String passwordOrHash, String nickname,
                String email, String gender, String securityQuestion, String answer, boolean isHashed) {

        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.securityQuestion = securityQuestion;
        this.energy = 200;
        this.wallet = new Wallet();

        if (isHashed) {
            this.hashedPassword = passwordOrHash;
            this.hashedSecurityAnswer = answer;
        } else {
            this.hashedPassword = hashSHA256(passwordOrHash);
            this.hashedSecurityAnswer = hashSHA256(answer);
        }
    }



    private String hashSHA256(String input) {
        if (input == null) return null;
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
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    public boolean checkPassword(String inputPassword) {
        return hashSHA256(inputPassword).equals(this.hashedPassword);
    }

    public void changePassword(String newPassword) {
        this.hashedPassword = hashSHA256(newPassword);
    }

    public void changeNickname(String newNick) {
        this.nickname = newNick;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getHashedSecurityAnswer() {
        return hashedSecurityAnswer;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public int getScore() {
        return score;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void increaseScore(int amount) {
        this.score += amount;
    }

    public void incrementGameCount() {
        this.gameCount++;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public static User createFromDatabase(String username, String hashedPassword, String nickname,
                                          String email, String gender, String securityQuestion, String hashedSecurityAnswer) {
        return new User(username, hashedPassword, nickname, email, gender, securityQuestion, hashedSecurityAnswer, true);
    }

}
