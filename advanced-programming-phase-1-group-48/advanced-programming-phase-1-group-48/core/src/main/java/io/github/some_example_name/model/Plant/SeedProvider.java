package io.github.some_example_name.model.Plant;


import io.github.some_example_name.model.enums.Season;

import java.util.List;
import java.util.Random;

public interface SeedProvider <T extends PlantType>{
    String getName();

    /** فصلی که می‌شود این دانه را کاشت */
    Season getSeason();

    /**
     * لیست تمام گیاهانی که این دانه (به‌صورت احتمالی
     * یا مخلوط) می‌تواند تولید کند.
     */
    List<T> getPossiblePlants();

    /**
     * انتخاب رندوم یک گیاه از لیست بالا
     * (برای MixedSeed وقتی کاربر می‌کارد).
     */
    default T pickRandomPlant() {
        List<T> list = getPossiblePlants();
        return list.get(new Random().nextInt(list.size()));
    }
}
