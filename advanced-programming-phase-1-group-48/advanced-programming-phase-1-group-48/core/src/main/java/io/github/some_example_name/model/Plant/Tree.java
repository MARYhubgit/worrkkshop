package io.github.some_example_name.model.Plant;


import io.github.some_example_name.model.Plant.Enums.TreeType;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.Weather.DateAndTime;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.items.Item;
import io.github.some_example_name.model.items.ItemFactory;

import java.util.Random;

public class Tree implements PlantInstance {
    private final TreeType type;
    private int age;
    private int DayofBirth;
    private int daysSinceLastFruit;
    private boolean isBurned;

    public Tree(TreeType type) {
        this.type = type;
        this.age = 0;
        this.DayofBirth = DateAndTime.getTotalDays();
        this.daysSinceLastFruit = 0;
        this.isBurned = false;
    }


    public void growOneDay() {
        if (age < type.getTotalTime()) {
            age++;
        } else if (!isBurned) {
            daysSinceLastFruit++;
        }
    }

    public void resetWaterFlag() {
        // no-op برای درخت
    }

    /** آب دادن (معمولاً روی درخت اعمال نمی‌شود) */
    @Override
    public void water() {
        // no-op برای درخت
    }

    /** آیا بلوغ اولیه کامل شده و فاصلهٔ برداشت دوره‌ای سپری شده؟ */

    public boolean isReadyToHarvest() {
        return !isBurned
                && age >= type.getTotalTime()
                && daysSinceLastFruit >= type.getFruitCycle();
    }

    @Override
    public Item harvest() {
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        if (isBurned) {
            Item coal = ItemFactory.createItem("coal", player.getInventory());
            return coal;
        }
        if (isReadyToHarvest()) {
            daysSinceLastFruit = 0;
            Item fruit = ItemFactory.createItem(type.getFruit(), player.getInventory());
//            player.getInventory().add(fruit);
            return fruit;
        }
        return null;
    }

    public int strikeByLightning() {
        double chance = Math.random();
        if (chance < 0.5) {
            isBurned = true;
            return 1;
        } else {
            isBurned = false;
            return 0;
        }
    }

    public void cutDown() {
        int count = new Random().nextInt(2) + 1;
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        for (int i = 0; i < count; i++) {
            ItemFactory.createItem(type.getName(), player.getInventory());
        }
    }

    public boolean isMature() {
        return age >= type.getTotalTime();
    }

    public TreeType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "type=" + type +
                ", age=" + age +
                ", DayofBirth=" + DayofBirth +
                ", daysSinceLastFruit=" + daysSinceLastFruit +
                ", isBurned=" + isBurned +
                '}';
    }
}
