package io.github.some_example_name.model.building;


import io.github.some_example_name.model.game.Position;

public class BuildingFactory {
    public static Buildings create(String name, Position position) {
        name = name.toUpperCase();

        try {
            BarnType barnType = BarnType.valueOf(name);
            //return new Barn(barnType, position);
        } catch (IllegalArgumentException e) {
        }

        try {
            CoopType coopType = CoopType.valueOf(name);
//            return new Coop(coopType, position);
        } catch (IllegalArgumentException e) {
        }

        throw new IllegalArgumentException("Unknown building name: " + name);
    }
}
