package io.github.some_example_name.model.intraction;


import io.github.some_example_name.model.Player.Player;

public class GiftRecord {
    private static int nextId = 1;
    private final int id;
    private Player sender;
    private Player receiver;
    private String itemName;
    private int amount;
    private int daySent;
    private Integer rating;

    public GiftRecord(Player sender, Player receiver, String itemName, int amount, int daySent) {
        this.sender = sender;
        this.receiver = receiver;
        this.itemName = itemName;
        this.amount = amount;
        this.daySent = daySent;
        this.rating = null;
        this.id = nextId++;
    }
    public int getId() {
        return id;
    }

    public void setRating(int rating) { this.rating = rating; }

    public Integer getRating() { return rating; }

    public Player getReceiver() {
        return receiver;
    }

    public int  getDaySent() {
        return daySent;
    }

    public Player getSender() {
        return sender;
    }

    public int getAmount() {
        return amount;
    }

    public String getItemName() {
        return itemName;
    }
}
