package io.github.some_example_name.controllers;



import io.github.some_example_name.model.Animal.Animal;
import io.github.some_example_name.model.Animal.AnimalKind;
import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;
import io.github.some_example_name.model.Animal.AnimalProduct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimallController {
    public static void caress(AnimalKind kind) {
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();

        Map<String , Animal> brought = player.getBroughtAnimal();
        Animal animal = brought.get(kind);

        if (animal == null) {
            System.out.println("No animal of type " + kind.name() + " was found!");
            return;
        }

        try {
            animal.pet();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addFriendship(int friendship, Animal a) {
        a.setFriendship(a.getFriendship() + friendship);
    }


    public static void showAnimals(Player player) {
        if (player.getBroughtAnimal().isEmpty()) {
            System.out.println("You have not brought any animals.");
            return;
        }

        System.out.println("Animals:");
        for ( Animal a : player.getBroughtAnimal().values()) {
            System.out.printf("- %s → Friendship: %d%n", a.getName(), a.getFriendship());
        }
    }

    public static void showCollectedProducts(Player player) {
        Map<String, Animal> animals = player.getBroughtAnimal();

        boolean hasAny = false;
        for (Animal animal : animals.values()) {
            AnimalProduct product = Animal.getTodaysProduct();
//            if (product != null && animal.getKind().getProductPrices().containsKey(product) && !animal.isHasProducedToday() ) {
//                System.out.printf("%s → %s with quality %.2f%n",
//                        animal.getName(),
//                        product.name().toLowerCase().replace('_', ' '),
//                        animal.getTodaysProductQuality());
//                hasAny = true;
//            }
        }

        if (!hasAny) {
            System.out.println("No collected products to show.");
        }


    }



    public static void showbarnAnimalCategories(Player player) {
        Map<String, Animal> animals = player.getBroughtAnimal();

        if (animals.isEmpty()) {
            System.out.println("no animals to show.");
            return;
        }

        Map<String, List<Animal>> categories = new HashMap<>();

        for (Animal animal : animals.values()) {
            String type = String.valueOf(animal.getKind()); // مثل "Cow", "Chicken", ...
            categories.putIfAbsent(type, new ArrayList<>());
            categories.get(type).add(animal);
        }

        System.out.println("barn animals:");
        for (String type : categories.keySet()) {
            System.out.println("- " + type + ":");
            for (Animal a : categories.get(type)) {
                System.out.println("   • " + a.getName());
            }
        }
    }


    public static void showCagedAnimalCategories(Player player) {
        Map<String, Animal> animals = player.getBroughtAnimal();

        if (animals.isEmpty()) {
            System.out.println("no animals to show.");
            return;
        }

        Map<String, List<Animal>> categories = new HashMap<>();

        for (Animal animal : animals.values()) {
            String type = String.valueOf(animal.getKind()); // مثل "Rabbit", "Parrot", ...
            categories.putIfAbsent(type, new ArrayList<>());
            categories.get(type).add(animal);
        }

        System.out.println("coop animals:");
        for (String type : categories.keySet()) {
            System.out.println("- " + type + ":");
            for (Animal a : categories.get(type)) {
                System.out.println("   • " + a.getName());
            }
        }
    }

}





