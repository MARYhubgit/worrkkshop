package io.github.some_example_name.model.NPC;

import io.github.some_example_name.model.Player.Player;

public class RewardSystem {
    public static void give(Player player, String reward) {
        if (reward.endsWith("g")) {
            int gold = Integer.parseInt(reward.replace("g", ""));
            // player.addGold(gold);
            System.out.println("You received " + gold + " gold!");
        } else if (reward.equalsIgnoreCase("Friendship +1")) {
            //player.increaseFriendshipLevel(1);
            System.out.println("Your friendship level increased!");
        } else {
            //  player.getInventory().addItem(reward, 1);
            System.out.println("You received: " + reward);
        }
    }
}
