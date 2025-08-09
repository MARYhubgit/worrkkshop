package io.github.some_example_name.model.Animal;



import io.github.some_example_name.model.Player.Player;
import io.github.some_example_name.model.building.Barn;
import io.github.some_example_name.model.building.Buildings;
import io.github.some_example_name.model.building.Coop;
import io.github.some_example_name.model.game.Game;
import io.github.some_example_name.model.game.GameManager;

import java.util.Map;


public class AnimalCreator {
    public static Animal createAnimal(String kindName, String animalName) {
        AnimalKind kind;
        try {
            kind = AnimalKind.valueOf(kindName.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Animal type '" + kindName + "' is not available.", ex);
        }
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        String homeType = kind.getAnimalKind();
        boolean placed = false;
        Barn targetBarn = null;
        Coop targetCoop = null;

        if ("barn".equalsIgnoreCase(homeType)) {
            for (Map.Entry<Buildings, Integer> entry : player.getMyBuilding().entrySet()) {
                Buildings bld = entry.getKey();
                //if (!(bld instanceof Barn)) continue;
                //Barn barn = (Barn) bld;
                int capacityLeft = entry.getValue();

//                for (String unlocked : barn.getBarnType().getUnlocks()) {
//                    if (unlocked.equalsIgnoreCase(kind.name()) && capacityLeft > 0) {
//                        targetBarn = barn;
//                        placed = true;
//                        break;
//                    }
//                }
                if (placed) break;
            }
            if (!placed) {
                throw new IllegalStateException(
                        "No barn available with space or proper unlock for " + kind);
            }

        } else if ("coop".equalsIgnoreCase(homeType)) {
            for (Map.Entry<Buildings, Integer> entry : player.getMyBuildings().entrySet()) {
                Buildings bld = entry.getKey();
//                if (!(bld instanceof Coop)) continue;
//                Coop coop = (Coop) bld;
                int capacityLeft = entry.getValue();

//                for (String unlocked : coop.getCoopType().getUnlocks()) {
//                    if (unlocked.equalsIgnoreCase(kind.name()) && capacityLeft > 0) {
//                        targetCoop = coop;
//                        placed = true;
//                        break;
//                    }
//                }
                if (placed) break;
            }
            if (!placed) {
                throw new IllegalStateException(
                        "No coop available with space or proper unlock for " + kind);
            }

        } else {
            throw new IllegalStateException("Unknown housing type for " + kind);
        }

        Animal animal = new Animal(animalName, kind);

        if (targetBarn != null) {
            targetBarn.addAnimal(animal);
           // player.getMyBuildings().merge(targetBarn, -1, Integer::sum);
        }else if(targetCoop !=null){
            targetCoop.addAnimal(animal);
//            player.getMyBuildings().merge(targetCoop, -1, Integer::sum);
        }
        player.getBroughtAnimal().put(animalName, animal);

        return animal;
    }

    public static void showUncollectedAnimalProducts() {
        Game game = GameManager.getCurrentGame();
        Player player = game.getCurrentPlayerForPlay();
        boolean any = false;

        System.out.println("=== Animals with uncollected products ===");
        for (Animal a : player.getBroughtAnimal().values()) {
            if (a.getTodaysProduct() != null) {
                System.out.printf(
                        "- %s: %s (Quality: %d)%n",
                        a.getName(),
                        a.getTodaysProduct(),
                        a.getTodaysProductQuality()
                );
                any = true;
            }
        }

        if (!any) {
            System.out.println("No uncollected products at the moment.");
        }
    }
}
