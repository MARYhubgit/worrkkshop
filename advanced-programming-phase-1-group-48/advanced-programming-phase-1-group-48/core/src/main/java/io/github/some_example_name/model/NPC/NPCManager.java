package io.github.some_example_name.model.NPC;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class NPCManager {
    private static final Map<String, NPC> npcs = new HashMap<>();

    public static void registerNPC(NPC npc) {
        npcs.put(npc.getName().toLowerCase(), npc);
    }

    public static NPC get(String name) {
        return npcs.get(name.toLowerCase());
    }

    public static Collection<NPC> getAll() {
        return npcs.values();
    }

//    public static void resetAllForDay(String username) {
//        for (NPC npc : npcs.values()) {
//            npc.resetDaily(username);
//        }
//    }
}
