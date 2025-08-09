package io.github.some_example_name.model.intraction;


import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;

import java.util.ArrayList;
import java.util.List;

public class Friendship {
    private Player player1;
    private Player player2;
    private int level;
    private int xp;
    private List<String> messages;
    private List<GiftRecord> giftHistory = new ArrayList<>();
    private boolean isMarried;
    private boolean bouquetGiven = false;
    private int lastInteractedDay = -1;
    private boolean xpIncreasedToday = false;

    public Friendship(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.level = 0;
        this.xp = 0;
        this.messages = new ArrayList<>();
        this.giftHistory = new ArrayList<>();
        this.isMarried = false;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public void addXp(int amount,  int currentDay) {
        if (lastInteractedDay != currentDay) {
            lastInteractedDay = currentDay;
            xpIncreasedToday = false;
        }
        if (!xpIncreasedToday) {
            this.xp += amount;
            xpIncreasedToday = true;
            if (this.xp >= getXpThresholdForNextLevel()) {
                levelUp();
            }
        }
    }

    public void decayDailyXp() {
        this.xp = Math.max(0, this.xp - 10);
    }

    private void levelUp() {
        this.level++;
    }

    private int getXpThresholdForNextLevel() {
        return 100 * (1 + level);
    }

    public boolean canSendGift() {
        return level > 0;
    }

    public boolean canHug() {
        return level >= 2;
    }

    public boolean canMarry() {
        return level >= 3;
    }

    public void dailyXpDecay(int currentDay) {
        if (lastInteractedDay == currentDay) {
            return;
        }

        int currentLevelThreshold = 100 * level;

        if (xp > currentLevelThreshold) {
            xp = Math.max(0 , xp - 10);
        } else if (level > 0) {
            level--;
            xp = getXpThresholdForNextLevel() - 10;
        }
    }

    public void addGiftRecord(GiftRecord record) {
        giftHistory.add(record);
    }
    public List<GiftRecord> getGiftHistory() {
        return giftHistory;
    }
    public void rateGift(GiftRecord record, int rating) {
        if (record.getRating() != null) {
            throw new IllegalStateException("This gift has already been rated.");
        }
        record.setRating(rating);
        int delta = 15 + 30 * (3 - rating);
        Game game = GameManager.getCurrentGame();
        this.addXp(delta, game.getCurrentDay());
    }
}
