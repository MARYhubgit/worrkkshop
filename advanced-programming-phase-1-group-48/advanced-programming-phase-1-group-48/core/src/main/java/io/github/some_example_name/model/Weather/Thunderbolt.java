package io.github.some_example_name.model.Weather;

import java.util.Random;

public class Thunderbolt {
    private static Thunderbolt instance;

    private Thunderbolt() {}

    public static synchronized Thunderbolt getInstance() {
        if (instance == null) {
            instance = new Thunderbolt();
        }
        return instance;
    }




    public void strikeStorm() {
//        Random rand = new Random();
//        GameMap map = GameMap.getInstance();
//        int width = map.getWidth();
//        int height = map.getHeight();
//        for (int i = 0; i < 3; i++) {
//            int x = rand.nextInt(width);
//            int y = rand.nextInt(height);
//            strikeAt(x, y);
//        }

        //TODO
    }


}
