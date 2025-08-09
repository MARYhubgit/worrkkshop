package io.github.some_example_name.model.building;


import io.github.some_example_name.model.Animal.Animal;
import io.github.some_example_name.model.game.Position;

import java.util.ArrayList;
import java.util.List;

public class Coop{

    private final CoopType coopType;
    private final List<Animal> animals;
    private static int capacity;

    public Coop(CoopType coopType) {
//        super(coopType.name(), topLeft, coopType.getWidth(), coopType.getHeight());
        this.coopType = coopType;
        this.animals = new ArrayList<>();
        Coop.capacity = coopType.getCapacity();
    }

    public CoopType getCoopType() {
        return coopType;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void addAnimal(Animal animal) {
        capacity--;
        animals.add(animal);
    }

    public boolean removeAnimal(Animal animal) {
        return animals.remove(animal);
    }


    @Override
    public String toString() {
        return coopType.toString();
    }
}
