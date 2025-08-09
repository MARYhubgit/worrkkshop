package io.github.some_example_name.model.NPC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestManager {
    private static final Map<String, List<Quest>> npcQuests = new HashMap<>();

    public static void init() {
        npcQuests.put("Sebastian", List.of(
            new Quest("Sebastian", "Deliver 50 Iron Ore", Map.of("iron ore", 50), "2 Diamonds", 0, 0),
            new Quest("Sebastian", "Deliver 1 Gold Bar", Map.of("gold bar", 1), "Friendship +1", 1, 0),
            new Quest("Sebastian", "Deliver 12 Pumpkin Pie", Map.of("pumpkin pie", 12), "5000g", 2, 28)
        ));

        npcQuests.put("Harvey", List.of(
            new Quest("Harvey", "Bring 10 Herbs", Map.of("herb", 10), "Health Potion", 0, 0),
            new Quest("Harvey", "Deliver 5 Bandages", Map.of("bandage", 5), "Friendship +1", 1, 0),
            new Quest("Harvey", "Collect 3 Rare Mushrooms", Map.of("rare mushroom", 3), "7000g", 2, 30)
        ));

        npcQuests.put("Abigail", List.of(
            new Quest("Abigail", "Find 3 Amethysts", Map.of("amethyst", 3), "Magic Ring", 0, 0),
            new Quest("Abigail", "Deliver 1 Dragon Scale", Map.of("dragon scale", 1), "Friendship +1", 1, 0),
            new Quest("Abigail", "Bring 5 Ghost Essences", Map.of("ghost essence", 5), "8000g", 2, 32)
        ));

        npcQuests.put("Lia", List.of(
            new Quest("Lia", "Gather 20 Wildflowers", Map.of("wildflower", 20), "Floral Crown", 0, 0),
            new Quest("Lia", "Deliver 10 Honey Jars", Map.of("honey jar", 10), "Friendship +1", 1, 0),
            new Quest("Lia", "Collect 5 Butterfly Wings", Map.of("butterfly wing", 5), "6000g", 2, 29)
        ));

        npcQuests.put("Robin", List.of(
            new Quest("Robin", "Provide 30 Wood Logs", Map.of("wood log", 30), "Tool Upgrade", 0, 0),
            new Quest("Robin", "Deliver 15 Nails", Map.of("nail", 15), "Friendship +1", 1, 0),
            new Quest("Robin", "Bring 10 Stone Bricks", Map.of("stone brick", 10), "9000g", 2, 31)
        ));
    }

    public static List<Quest> getQuestsFor(String npcName) {
        return npcQuests.getOrDefault(npcName, List.of());
    }
}
