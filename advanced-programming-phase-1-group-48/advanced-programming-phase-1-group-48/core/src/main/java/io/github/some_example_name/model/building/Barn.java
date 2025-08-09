package io.github.some_example_name.model.building;



import io.github.some_example_name.model.Animal.Animal;
import io.github.some_example_name.model.game.Position;

import java.util.ArrayList;
import java.util.List;

public class Barn {

    private final BarnType barnType;
    private final List<Animal> animals;
    private static int capacity;


    public Barn(BarnType barnType) {
//        super(barnType.name(), topLeft, barnType.getWidth(), barnType.getHeight());
        this.barnType = barnType;
        this.animals = new ArrayList<>();
        this.capacity = barnType.getCapacity();
    }

    public BarnType getBarnType() {
        return barnType;
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

    public static void getType(){

    }

//    @Override
//    public void interact() {
//    }

    @Override
    public String toString() {
        return barnType.toString();
    }

    public static int getCapacity() {
        return capacity;
    }
}
