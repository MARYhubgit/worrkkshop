package io.github.some_example_name.model.Player;

public class Skill {
    private String name;
    private int level;
    private int xp;

    public Skill(String name) {
        this.name = name;
        this.level = 0;
        this.xp = 0;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public String gainXp(int amount) {
        xp += amount;
        while (level < 4 && xp >= requiredXpForNextLevel(level + 1)) {
            level++;
            return ("Skill " + name + " leveled up to level " + level + "!");
        }
        return ("Skill " + name + " not enough or its full level!");
    }

    private int requiredXpForNextLevel(int nextLevel) {
        return 50 + (nextLevel * 100);
    }
}
