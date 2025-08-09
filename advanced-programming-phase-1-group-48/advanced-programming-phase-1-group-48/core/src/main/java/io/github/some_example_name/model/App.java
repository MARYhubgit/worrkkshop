package io.github.some_example_name.model;




import io.github.some_example_name.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class App {
    //private ArrayList<Player> currentPlayers;
    private static List<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }

}
