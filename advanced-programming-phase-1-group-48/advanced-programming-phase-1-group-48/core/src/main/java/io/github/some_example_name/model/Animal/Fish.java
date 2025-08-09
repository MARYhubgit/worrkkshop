package io.github.some_example_name.model.Animal;


import io.github.some_example_name.model.enums.Quality;

public class Fish{
    private final String name;
    private final Quality quality;
    private final int price;

    public Fish(String name, Quality quality, int price) {
        this.name = name;
        this.quality = quality;
        this.price = price;
    }
    public String getName()    { return name; }
    public Quality getQuality(){ return quality; }
    public int getPrice()      { return price; }


}
