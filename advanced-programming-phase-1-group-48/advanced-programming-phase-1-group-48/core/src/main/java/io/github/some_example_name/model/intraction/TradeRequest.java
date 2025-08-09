package io.github.some_example_name.model.intraction;



import io.github.some_example_name.model.items.Item;

import java.util.List;

public class TradeRequest {
    private String id;
    private String senderUsername;
    private String receiverUsername;
    private List<Item> offeredItems;
    private List<Item> requestedItems;
    private double offeredMoney;
    private double requestedMoney;
    private String status;


    public TradeRequest(String id, String senderUsername, String receiverUsername,
                        List<Item> offeredItems, List<Item> requestedItems,
                        double offeredMoney, double requestedMoney) {
        this.id = id;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.offeredItems = offeredItems;
        this.requestedItems = requestedItems;
        this.offeredMoney = offeredMoney;
        this.requestedMoney = requestedMoney;
        this.status = null;
    }

    public String getId() {
        return id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public List<Item> getOfferedItems() {
        return offeredItems;
    }

    public List<Item> getRequestedItems() {
        return requestedItems;
    }

    public double getOfferedMoney() {
        return offeredMoney;
    }

    public double getRequestedMoney() {
        return requestedMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAccepted() {
        return "accept".equals(status);
    }

    public boolean isRejected() {
        return "reject".equals(status);
    }

    public void addOfferedItem(Item item) {offeredItems.add(item);}

    public void removeOfferedItem(Item item) {
        offeredItems.remove(item);
    }

    public void addRequestedItem(Item item) {
        requestedItems.add(item);
    }

    public void removeRequestedItem(Item item) {
        requestedItems.remove(item);
    }
}
