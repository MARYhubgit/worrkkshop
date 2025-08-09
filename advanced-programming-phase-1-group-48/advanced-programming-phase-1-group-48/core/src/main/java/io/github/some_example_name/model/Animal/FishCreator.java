package io.github.some_example_name.model.Animal;


import io.github.some_example_name.model.enums.Quality;
import io.github.some_example_name.model.enums.Season;
import io.github.some_example_name.model.enums.WeatherType;

import java.util.*;
import java.util.stream.Collectors;

public class FishCreator {
    private static final Map<Season, List<FishType>> fishBySeason = new EnumMap<>(Season.class);
    static {
        fishBySeason.put(Season.FALL,   Arrays.asList(FishType.SALMON, FishType.SARDINE, FishType.SHAD, FishType.BLUE_DISCUS, FishType.ANGLER));
        fishBySeason.put(Season.WINTER, Arrays.asList(FishType.MIDNIGHT_CARP, FishType.SQUID, FishType.TUNA, FishType.PERCH, FishType.GLACIERFISH));
        fishBySeason.put(Season.SPRING, Arrays.asList(FishType.FLOUNDER, FishType.LIONFISH, FishType.HERRING, FishType.GHOSTFISH, FishType.LEGEND));
        fishBySeason.put(Season.SUMMER, Arrays.asList(FishType.TILAPIA, FishType.DORADO, FishType.SUNFISH, FishType.RAINBOW_TROUT, FishType.CRIMSONFISH));
    }

    public static List<Fish> catchFishes(Season season, double skill, double maxSkill, WeatherType weather) {
        if (weather == null) {
            weather = WeatherType.SUNNY;
        }
        double M;
        switch (weather) {
            case SUNNY:  M = 0.5;  break;
            case RAINY:  M = 1.2;  break;
            case STORM: M = 1.5;  break;
            default:     M = 1.0;  break;
        }

        double R = Math.random();
        int count = (int) Math.ceil(2 + skill * (M * R));
        count = Math.min(count, 6);

        List<FishType> candidates = fishBySeason.getOrDefault(season, Collections.emptyList())
                .stream()
                .filter(ft -> ft.isLegendary() ? skill >= maxSkill : true)
                .collect(Collectors.toList());

        Random rnd = new Random();
        List<Fish> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            FishType type = candidates.get(rnd.nextInt(candidates.size()));

            double qRand = Math.random();
            Quality quality;
            if      (qRand <= 0.5) quality = Quality.NORMAL;
            else if (qRand <= 0.7) quality = Quality.SILVER;
            else if (qRand <= 0.9) quality = Quality.GOLD;
            else                   quality = Quality.IRIDIUM;

            int price = (int) Math.round(type.getBasePrice() * quality.getMultiplier());
            result.add(new Fish(type.getDisplayName(), quality, price));
        }

        return result;
    }
}
