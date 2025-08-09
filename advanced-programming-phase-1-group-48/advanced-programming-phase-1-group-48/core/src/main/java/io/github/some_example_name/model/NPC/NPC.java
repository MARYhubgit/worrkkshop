package io.github.some_example_name.model.NPC;

import io.github.some_example_name.model.enums.WeatherType;
import io.github.some_example_name.model.enums.Season;
import java.util.*;

public class NPC {
    private final String name;
    private final String job;
    private final String homeName;
    private final Map<Season, Map<WeatherType, List<String>>> dialogues;

    public NPC(String name, String job, String homeName) {
        this.name = name;
        this.job = job;
        this.homeName = homeName;
        this.dialogues = new EnumMap<>(Season.class);

        for (Season season : Season.values()) {
            dialogues.put(season, new EnumMap<>(WeatherType.class));
        }
        //System.out.println("here");
        initDialogues();
    }

    private void initDialogues() {
        for (Season season : Season.values()) {
            Map<WeatherType, List<String>> seasonalDialogues = dialogues.get(season);

            switch (name.toLowerCase()) {
                case "sebastian" -> {
                    seasonalDialogues.put(WeatherType.SUNNY, List.of("Sun’s too bright... I prefer the shade.", "Nice weather, I guess."));
                    seasonalDialogues.put(WeatherType.RAINY, List.of("Rain’s nice. Keeps people away.", "I like walking in the rain."));
                    seasonalDialogues.put(WeatherType.STORM, List.of("Thunder’s loud… but I don’t mind it.", "Good day to stay inside."));
                    seasonalDialogues.put(WeatherType.SNOW, List.of("Snow’s peaceful. I like that.", "Want to build a snowman? Just kidding."));
                }
                case "abigail" -> {
                    seasonalDialogues.put(WeatherType.SUNNY, List.of("Let’s go on an adventure!", "The sun fuels my energy today."));
                    seasonalDialogues.put(WeatherType.RAINY, List.of("Rain makes everything feel more dramatic.", "Perfect day to stay in and play games."));
                    seasonalDialogues.put(WeatherType.STORM, List.of("Wow, that lightning was intense!", "Storms are kind of exciting, don’t you think?"));
                    seasonalDialogues.put(WeatherType.SNOW, List.of("Let’s go crunch some snow!", "I love how quiet everything gets when it snows."));
                }
                case "harvey" -> {
                    seasonalDialogues.put(WeatherType.SUNNY, List.of("Great day for a check-up!", "Don’t forget to stay hydrated."));
                    seasonalDialogues.put(WeatherType.RAINY, List.of("Careful not to catch a cold!", "Stay dry and warm."));
                    seasonalDialogues.put(WeatherType.STORM, List.of("Storms can be dangerous—stay safe.", "Make sure your first-aid kit is stocked."));
                    seasonalDialogues.put(WeatherType.SNOW, List.of("Let me know if you slip on the ice!", "Snowy days are cozy inside."));
                }
                case "robin" -> {
                    seasonalDialogues.put(WeatherType.SUNNY, List.of("Perfect day for building!", "Sun’s shining—let’s get to work."));
                    seasonalDialogues.put(WeatherType.RAINY, List.of("Rain slows construction down…", "I need to check the barn roof again."));
                    seasonalDialogues.put(WeatherType.STORM, List.of("Storms keep me out of the workshop.", "Stay indoors, alright?"));
                    seasonalDialogues.put(WeatherType.SNOW, List.of("Can’t build much in the snow.", "I love the way the trees look now."));
                }
                case "lia", "leah" -> {
                    seasonalDialogues.put(WeatherType.SUNNY, List.of("Nature is so inspiring today.", "The light is just right for painting."));
                    seasonalDialogues.put(WeatherType.RAINY, List.of("Rain is the perfect backdrop for sculpting.", "I love how rain smells."));
                    seasonalDialogues.put(WeatherType.STORM, List.of("Thunder shakes the cabin sometimes!", "Maybe I’ll sketch the storm."));
                    seasonalDialogues.put(WeatherType.SNOW, List.of("Snow makes me want to sculpt something serene.", "I can’t stop watching it fall."));
                }
                default -> {
                    seasonalDialogues.put(WeatherType.SUNNY, List.of("It’s a beautiful day!", "The sun feels great."));
                    seasonalDialogues.put(WeatherType.RAINY, List.of("Rainy days make me sleepy.", "I like hearing the raindrops."));
                    seasonalDialogues.put(WeatherType.STORM, List.of("Better stay inside today!", "That thunder was close!"));
                    seasonalDialogues.put(WeatherType.SNOW, List.of("It’s snowing again!", "Looks like a snowball fight day."));
                }
            }
            dialogues.put(season, seasonalDialogues);
        }
        // System.out.println(dialogues);
    }


    public String getRandomDialogue(Season season, WeatherType weather) {
        List<String> options = dialogues.get(season).get(weather);
        // System.out.println(options);
        if (options == null || options.isEmpty()) return "...";
        return options.get(new Random().nextInt(options.size()));
    }

    public String getName() {
        return name;
    }

    public String getHomeName() {
        return homeName;
    }

    public String getJob() {
        return job;
    }
    private final Set<String> favoriteItems = new HashSet<>();


    public void addFavoriteItem(String itemName) {
        favoriteItems.add(itemName.toLowerCase());
    }

    public boolean likes(String itemName) {
        return favoriteItems.contains(itemName.toLowerCase());
    }

    private final List<String> giftPool = new ArrayList<>();

    public void addGiftOption(String itemName) {
        giftPool.add(itemName.toLowerCase());
    }

    public String getRandomGift() {
        if (giftPool.isEmpty()) return null;
        return giftPool.get(new Random().nextInt(giftPool.size()));
    }


}


