package io.github.some_example_name.model.Plant;

public class Seed {
    private String name;
    private String season;

    Seed(String name, String season) {
        this.name = name;
        this.season = season;
    }
    //TODO
    public String getName() {
        return name;
    }
    public String getSeason() {
        return season;
    }
}
