package io.github.some_example_name.controllers;


import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.intraction.Friendship;
import io.github.some_example_name.model.intraction.GiftRecord;
import io.github.some_example_name.model.items.Item;
import io.github.some_example_name.model.items.ItemFactory;

import java.util.Map;

public class
FriendshipController {
    public static void increacrLevel(int amount ,String senderName,String receiverName) {
        Game game = GameManager.getCurrentGame();
        Player receiver = game.getPlayerByUsername(receiverName);
        Player sender = game.getPlayerByUsername(senderName);

        Friendship friendship = game.getFriendship(sender, receiver);
        friendship.setLevel(amount);
        friendship.setXp(amount*100 + 80);
    }
    public static String showFriendships(Player player) {
        StringBuilder result = new StringBuilder("Friendships:\n");
        String playerName = player.getName();
        Game game = GameManager.getCurrentGame();

        for (Map.Entry<String, Friendship> entry : game.friendships.entrySet()) {
            String key = entry.getKey();
            Friendship friendship = entry.getValue();


            String[] parts = key.split("#", 2);
            String name1 = parts[0];
            String name2 = parts[1];

            if (name1.equals(playerName) || name2.equals(playerName)) {

                Player other = name1.equals(playerName)
                    ? friendship.getPlayer2()
                    : friendship.getPlayer1();
                int level = friendship.getLevel();
                int xp    = friendship.getXp();

                result.append("Friend: ").append(other.getName())
                    .append(" | Level: ").append(level)
                    .append(" | XP: ").append(xp)
                    .append("\n");
            }
        }


        return result.toString();
    }

    public static String talk(Player sender, String receiverUsername, String message) {
        Game game = GameManager.getCurrentGame();
        Player receiver = game.getPlayerByUsername(receiverUsername);
        if (receiver == null) {
            return ("No player found with that name.");
        }
        Friendship friendship = game.getFriendship(sender, receiver);
        if (friendship == null) {
            game.addFriendship(sender, receiver);
            friendship = game.getFriendship(sender, receiver);
        }

        friendship.addXp(20, game.getCurrentDay());
        friendship.getMessages().add(sender.getName() + ": " + message);
        String notification =("Notification! "+ receiver.getName() + " received a new message from " +sender.getName()+ ": " + message);
        receiver.addNotification(notification);
        return "You have received a new message from " + receiver.getName() + ": " + message;

    }

    public static String showTalkHistory(Player viewer, String otherUsername) {
        Game game = GameManager.getCurrentGame();
        Player other = game.getPlayerByUsername(otherUsername);
        if (other == null) {
            return ("No player found with that name.");
        }

        Friendship friendship = game.getFriendship(viewer, other);
        if (friendship.getMessages().isEmpty()) {
            return ("No messages exchanged yet.");
        }

        for (String msg : friendship.getMessages()) {
            return (msg);
        }
        return ("No messages exchanged yet.");
    }

    public static String sendGift(Player sender, String receiverName, String itemName, int amount) {
        Game game = GameManager.getCurrentGame();
        Player receiver = game.getPlayerByUsername(receiverName);
        Friendship friendship = game.getFriendship(sender, receiver);
        if (receiver == null) {
            return ("No player found with name: " + receiverName);

        }
        if (!friendship.canSendGift()) {
            return ("Your friendship level is too low to send gifts.");

        }
        if (!sender.getInventory().hasItem(itemName, amount)) {
            return ("You do not have " + amount + " of " + itemName + " to send.");

        }

        sender.getInventory().removeItem(itemName, amount);

        for (int i = 0; i < amount; i++) {
            Item item = ItemFactory.createItem(itemName, receiver.getInventory());
            if (item == null) {
                return ("Receiver's inventory is full! Only " + i + " out of " + amount + " items were added.");
            }
        }

        GiftRecord record = new GiftRecord(sender, receiver, itemName, amount, Game.getCurrentDay());
        friendship = game.getFriendship(sender, receiver);
        friendship.addGiftRecord(record);

        String massage=  "Notification! " + receiver.getName() + " received a gift from "
            + sender.getName() + ": " + amount + "x " + itemName + "Please rate to your gift.";

        receiver.addNotification(massage);
        return ("You sent " + amount + "x " + itemName + " to " + receiver.getName());
    }

    public static String listGifts(Player viewer) {
        Friendship f;
        Game game = GameManager.getCurrentGame();
        boolean any = false;
        for (Friendship friendship : game.getAllFriendshipsOf(viewer)) {
            for (GiftRecord r : friendship.getGiftHistory()) {
                if (r.getReceiver().equals(viewer)) {
                    any = true;
                    return ("[" + r.getDaySent() + "]"+r.getId()
                        + " from " + r.getSender().getName()
                        + ": " + r.getAmount() + "x " + r.getItemName()
                        + (r.getRating() != null ? " (rated " + r.getRating() + ")" : ""));
                }
            }
        }
        if (!any) {
            return("You have not received any gifts yet.");
        }
        return "";
    }

    public static String rateGift(Player viewer, int giftHash, int rating) {
        Game game = GameManager.getCurrentGame();
        for (Friendship friendship : game.getAllFriendshipsOf(viewer)) {
            for (GiftRecord r : friendship.getGiftHistory()) {
                if (r.getId() == giftHash && r.getReceiver().equals(viewer)) {
                    if (rating < 1 || rating > 5) {
                        return ("Invalid rating. Must be between 1 and 5.");

                    }
                    friendship.rateGift(r, rating);
                    return ("You rated gift #" + giftHash + " with " + rating);
                }
            }
        }
        return ("No gift found with id: " + giftHash);
    }

    public static String giftHistory(Player viewer, String otherName) {
        Game game = GameManager.getCurrentGame();
        Player other = game.getPlayerByUsername(otherName);
        if (other == null) {
            return ("No player named " + otherName);

        }
        Friendship f = game.getFriendship(viewer, other);
        if (f.getGiftHistory().isEmpty()) {
            return ("No gifts exchanged with " + otherName);
        }
        for (GiftRecord r : f.getGiftHistory()) {
            return ("[" + r.getDaySent() + "] "
                + r.getSender().getName() + " → " + r.getReceiver().getName()
                + ": " + r.getAmount() + "x " + r.getItemName()
                + (r.getRating() != null ? " (rated " + r.getRating() + ")" : ""));
        }
        return ("No gifts exchanged with " + otherName);
    }

    public static String hugPlayer(Player sender, String receiverUsername) {
        Game game = GameManager.getCurrentGame();
        Player receiver =game.getPlayerByUsername(receiverUsername);
        if (receiver == null) {
            return "Player not found!";
        }


        Friendship friendship = game.getFriendship(sender, receiver);
        if (friendship == null) {
            return "You are not friends yet!";
        }

        if (!friendship.canHug()) {
            return "You need at least friendship level 2 to hug!";
        }

        friendship.addXp(60, game.getCurrentDay());
        return "You hugged " + receiverUsername + "! (+60 friendship XP)";
    }

    public static String sendFlower(Player sender, String receiverUsername) {
        Game game = GameManager.getCurrentGame();
        Player receiver = game.getPlayerByUsername(receiverUsername);
        if (receiver == null) return "Player not found.";


        Friendship friendship = game.getFriendship(sender, receiver);
        if (friendship == null) return "You are not friends with this player.";

//        if (friendship.getLevel() != 2) return "You must be at level 2 friendship to send a flower.";
        if (friendship.getXp() < 100 * 3) return "You must fill XP of level 2 before sending flower.";

        if (!sender.getInventory().hasItem("flower", 1)) {
            return "You don't have a flower in your inventory.";
        }

        sender.getInventory().removeItem("flower",1);
        ItemFactory.createItem("flower",receiver.getInventory());

        friendship.setLevel(3);


        return "You have sent a flower to " + receiverUsername + ". Friendship level increased to 3.";
    }

    public static String askMarriage(Player proposer, String targetUsername, String ringName) {
        Game game = GameManager.getCurrentGame();
        Player target = game.getPlayerByUsername(targetUsername);
//        if (!proposer.getGender().equalsIgnoreCase("male")) {
//            return "Only male players can initiate a marriage proposal.";
//        }
        if (target == null) return "Target player not found.";


        Friendship friendship = game.getFriendship(proposer, target);
        if (friendship == null) {
            return "You must build a friendship before proposing marriage.";
        }
        if (!friendship.canMarry()) {
            return "Friendship level must be at least 3 to propose marriage.";
        }

//        if (proposer.getGender() == target.getGender()) {
//            return "Marriage is only allowed between opposite genders.";
//        }

        if (proposer.isMarried() || target.isMarried()) {
            return "One of the players is already married.";
        }

        if (!proposer.getInventory().hasItem(ringName, 1)) {
            return "You don't have the specified ring in your inventory.";
        }

        Game.saveMarriageRequest(proposer.getName(), target.getName(), ringName);

        String message = proposer.getName() + " has proposed to you! Use 'respond --accept -u " + proposer.getName() +
            "' to accept or 'respond --reject -u " + proposer.getName() + "' to reject.";
        target.addNotification(message);

        return "Marriage proposal sent to " + target.getName() + ".";
    }

    public static String respondMarriage(Player responder, String proposerUsername, boolean accept, boolean reject) {
        Game game = GameManager.getCurrentGame();

        Player proposer = game.getPlayerByUsername(proposerUsername);
        if (proposer == null)
            return "Proposing player not found.";

        Friendship friendship = game.getFriendship(proposer, responder);
        if (friendship == null || !friendship.canMarry())
            return "You are not at the correct friendship level to respond.";

        if (reject) {
            friendship.setLevel(0);
            responder.decreaseEnergy(responder.getEnergy()/2);
            System.out.println("You have rejected your marriage.");
            System.out.println("responder energy : "+responder.getEnergy()+"\n");
            System.out.println("friendship Level : "+friendship.getLevel());

            return "";
//            proposer.setLowEnergyUntil(game.getCurrentDay() + 7);
//            game.removeMarriageRequest(responder.getUsername());
//            return "You have rejected the proposal. Friendship reset. "
//                    + proposerUsername + " will have reduced energy for 7 days.";
        }
//
//        for (ItemStack stack : new ArrayList<>(proposer.getInventory().getAllItems())) {
//            proposer.getInventory().removeItem(stack.getItemName(), stack.getAmount());
//            responder.getInventory().addItem(stack.getItemName(), stack.getAmount());
//        }
//
//
//        int totalMoney = proposer.getBalance() + responder.getBalance();
//        proposer.setBalance(totalMoney);
//        responder.setBalance(totalMoney);

        if (accept) {
            friendship.setLevel(4);
            proposer.increaseEnergy(50);
            responder.increaseEnergy(50);
            System.out.println("\"You have accepted the proposal! You are now married to \" + proposerUsername + \".\";");
            System.out.println("friendship Level : "+friendship.getLevel()+"\n");
            System.out.println("proposer energy : "+proposer.getEnergy()+"\n");
            System.out.println("responder energy : "+responder.getEnergy()+"\n");
            return "";
        }
        return "";
    }



}
