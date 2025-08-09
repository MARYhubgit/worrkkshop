package io.github.some_example_name.model.Plant;


import io.github.some_example_name.model.items.Item;

public interface PlantInstance {
    /** آب دادن: ست کردن پرچم wateredToday */
    void water();

    /** برداشت: تولید آیتم یا null */
    Item harvest();
}
