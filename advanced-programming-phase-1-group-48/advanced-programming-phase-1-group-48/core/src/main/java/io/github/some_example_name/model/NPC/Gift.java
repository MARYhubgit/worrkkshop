package io.github.some_example_name.model.NPC;


import io.github.some_example_name.model.Playerr;

public class Gift {
    private String itemName;
    private int amount;
    private int rating;
    private Playerr sender;
    private Playerr receiver;

    public Gift(String itemName, int amount) {
        this.itemName = itemName;
        this.amount = amount;
        this.rating = 0;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Playerr getSender() {
        return sender;
    }

    public void setSender(Playerr sender) {
        this.sender = sender;
    }

    public Playerr getReceiver() {
        return receiver;
    }

    public void setReceiver(Playerr receiver) {
        this.receiver = receiver;
    }

    public void rateGift(int rating) {
        this.rating = rating;
        adjustFriendshipXp();
    }

    private void adjustFriendshipXp() {
        int xpChange = 15 + 30 * (3 - rating);
    }
}
