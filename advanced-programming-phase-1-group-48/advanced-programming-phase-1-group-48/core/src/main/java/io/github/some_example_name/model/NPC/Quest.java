package io.github.some_example_name.model.NPC;


import java.util.Map;

public class Quest {
    private final String npcName;
    private final String description;
    private final Map<String, Integer> requiredItems;
    private final String reward;
    private final int requiredFriendshipLevel;
    private final int activateAfterDays;
    private boolean completed = false;

    public Quest(String npcName, String description, Map<String, Integer> requiredItems,
                 String reward, int requiredFriendshipLevel, int activateAfterDays) {
        this.npcName = npcName;
        this.description = description;
        this.requiredItems = requiredItems;
        this.reward = reward;
        this.requiredFriendshipLevel = requiredFriendshipLevel;
        this.activateAfterDays = activateAfterDays;
    }

    public boolean canBeActivated(int playerFriendLevel, int currentDay) {
//        System.out.println(playerFriendLevel);
//        System.out.println();
        return playerFriendLevel >= requiredFriendshipLevel ;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }

    public String getNpcName() {
        return npcName;
    }

    public Map<String, Integer> getRequiredItems() {
        return requiredItems;
    }

    public String getDescription() {
        return description;
    }

    public String getReward() {
        return reward;
    }

    public int getRequiredFriendshipLevel() {
        return requiredFriendshipLevel;
    }

    @Override
    public String toString() {
        return String.format("From %s: %s (Needs: %s, Reward: %s)", npcName, description, requiredItems, reward);
    }
}
