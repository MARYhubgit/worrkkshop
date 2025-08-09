package io.github.some_example_name.model.Weather;


import io.github.some_example_name.model.enums.Season;
import io.github.some_example_name.model.enums.WeatherType;

import static io.github.some_example_name.model.enums.Season.*;

public class Effect {


    public static void apply(String currentSeasonName) {
        Season season = Season.valueOf(currentSeasonName);
        WeatherType weather = Weather.getInstance().getToday();

        switch (weather) {
            case SUNNY:
                applySunny(season);
                break;
            case RAINY:
                applyRain(season);
                break;
            case STORM:
                applyStorm(season);
                break;
            case SNOW:
                applySnow(season);
                break;
        }

        System.out.printf("Applied weather effects: season=%s, weather=%s%n", season, weather);
    }

    private static void applySunny(Season season) {
        /// ////////////
    }

    private static void applyRain(Season season) {
        if (season == SPRING || season == SUMMER || season == FALL) {
            //IrrigationSystem.autoIrrigate();
        }
        /// /////////
        //Thunderbolt.getInstance().strikeRain();
    }

    private static void applyStorm(Season season) {
        applyRain(season);
        Thunderbolt.getInstance().strikeStorm();
    }

    private static void applySnow(Season season) {
        if (season == WINTER) {
            /// //////////////
        } else {
            /// //////////////////////
        }
    }

}
