package io.github.some_example_name.model.Plant;


import io.github.some_example_name.model.Plant.Enums.CropType;
import io.github.some_example_name.model.Plant.Enums.TreeType;

public class PlantFactory {
    public static PlantInstance createPlant(CropType type) {
        return new Crop(type);
    }

    public static PlantInstance creatTree (TreeType type){
        return  new Tree (type);
    }
}
