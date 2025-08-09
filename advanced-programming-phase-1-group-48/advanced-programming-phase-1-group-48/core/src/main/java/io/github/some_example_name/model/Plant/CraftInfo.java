package io.github.some_example_name.model.Plant;

import io.github.some_example_name.model.Plant.Enums.*;

import java.util.Optional;



public class CraftInfo {
    public static void craftInfo(String name) {
        Optional<? extends PlantType> result;


        result = CropType.fromName(name);
        if (!result.isPresent()) {
            result = TreeType.fromName(name);
        }
        if (!result.isPresent()) {
            result = ForCropType.fromName(name);
        }
        if (!result.isPresent()) {
            result = ForTreeType.fromName(name);
        }
        if (!result.isPresent()) {
            result = SeedType.fromName(name);
        }

        result.ifPresentOrElse(
                info -> System.out.println(info),
                ()   -> System.err.println("Error: no such item: " + name)
        );

    }
}
