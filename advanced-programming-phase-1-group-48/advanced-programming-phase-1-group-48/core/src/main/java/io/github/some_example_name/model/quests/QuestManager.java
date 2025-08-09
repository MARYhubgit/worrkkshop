//package io.github.some_example_name.model.quests;
//
//
//
//import io.github.some_example_name.model.NPC.Quest;
//import io.github.some_example_name.model.user.User;
//
//import java.util.*;
//
//public class QuestManager {
//    private static QuestManager instance;
//    private Map<User, List<Quest>> activeQuests = new HashMap<>();
//
//    public static QuestManager getInstance() {
//        if (instance == null)
//            instance = new QuestManager();
//        return instance;
//    }
//
//    public List<Quest> getActiveQuests(User user) {
//        return activeQuests.getOrDefault(user, new ArrayList<>());
//    }
//
//    public void assignQuest(User user, Quest quest) {
//        activeQuests.putIfAbsent(user, new ArrayList<>());
//        activeQuests.get(user).add(quest);
//    }
////
////    public boolean finishQuest(User user, int index) {
////        List<Quest> quests = activeQuests.get(user);
////        if (index >= quests.size()) return false;
////        Quest quest = quests.get(index);
////        if (quest.complete(user)) {
////            // remove or mark as done
////            return true;
////        }
////        return false;
////    }
//}
