package io.github.some_example_name.model.Weather;


import io.github.some_example_name.model.enums.WeatherType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Weather {
    private static Weather instance;
    private static WeatherType today;
    private static WeatherType tomorrow;

    private Weather() {
        generateTomorrowWeather(DateAndTime.getInstance().getCurrentSeason().name());
        today = tomorrow;
        generateTomorrowWeather(DateAndTime.getInstance().getCurrentSeason().name());

    }

    public static Weather getInstance() {
        if (instance == null)
            instance = new Weather();
        return instance;
    }

    public static WeatherType getToday() {
        return today;
    }
    public static WeatherType setToday(WeatherType today) {
        Weather.today = today;
        return today;
    }
    public static WeatherType getTomorrow() {
        return tomorrow;
    }

    public static void generateTomorrowWeather(String currentSeason) {
        Random rand = new Random();
        WeatherType[] options;
        switch (currentSeason) {
            case "Spring":
            case "Summer":
            case "Fall":
                options = new WeatherType[]{WeatherType.SUNNY, WeatherType.RAINY, WeatherType.STORM};
                break;
            case "Winter":
                options = new WeatherType[]{WeatherType.SUNNY, WeatherType.SNOW};
                break;
            default:
                options = new WeatherType[]{WeatherType.SUNNY};
        }
        tomorrow = options[rand.nextInt(options.length)];
    }

    public void displayTodayWeather() {
        System.out.printf("Today's weather: %s%n", today);
    }

    public void displayTomorrowForecast() {
        System.out.printf("Tomorrow's forecast: %s%n", tomorrow);
    }

    public static void cheatSetTomorrowWeather(WeatherType type) {
        tomorrow = type;
        System.out.printf("Cheat: Tomorrow's weather set to %s%n", type);
    }

    public void advanceDayAndWeather() {
        today = tomorrow;
        Effect.apply(DateAndTime.getInstance().getCurrentSeason().name());
        generateTomorrowWeather(DateAndTime.getInstance().getCurrentSeason().name());
    }

    public Map<String,Object> saveState() {
        Map<String,Object> s = new HashMap<>();
        s.put("today", today);
        s.put("tomorrow", tomorrow);
        return s;
    }

    public void loadState(Map<String,Object> s) {
        today = (WeatherType) s.get("today");
        tomorrow = (WeatherType) s.get("tomorrow");
    }
}

