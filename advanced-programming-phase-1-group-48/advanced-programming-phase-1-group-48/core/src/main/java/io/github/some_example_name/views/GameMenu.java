package io.github.some_example_name.views;

import io.github.some_example_name.controllers.GameMenuController;
import io.github.some_example_name.model.enums.MenuCommands;

import java.util.Scanner;

public class GameMenu implements AppMenu {
    public void handleInput(String command, Scanner scanner) {
        if (command.startsWith("game new")) {
            GameMenuController.handleNewGame(command, scanner);
        } else if (command.matches(MenuCommands.PRINT.getPattern().pattern())) {
            GameMenuController.printMap(command, scanner);
        } else if (command.matches(MenuCommands.WALK.getPattern().pattern())) {
            GameMenuController.handleWalkCommand(command);
        }else if (command.matches(MenuCommands.NEXT_TURN.getPattern().pattern())) {
            GameMenuController.handleNextTurn();
        } else if (command.matches(MenuCommands.EXIT.getPattern().pattern())) {
            GameMenuController.handleExitGame();
        } else if (command.matches(MenuCommands.LOAD.getPattern().pattern())) {
            GameMenuController.handleLoadGameCommand();
        } else if (command.matches(MenuCommands.TIME.getPattern().pattern())) {
            GameMenuController.showTime();
        } else if (command.matches(MenuCommands.DATE.getPattern().pattern())) {
            GameMenuController.showDate();
        } else if (command.matches(MenuCommands.TIME_AND_DATE.getPattern().pattern())) {
            GameMenuController.showDateAndTime();
        } else if (command.matches(MenuCommands.DAY_OF_WEEK.getPattern().pattern())) {
            GameMenuController.showDayOfTheWeek();
        } else if (command.matches(MenuCommands.CHEAT_DAY.getPattern().pattern())) {
            GameMenuController.cheatDays(command);
        } else if (command.matches(MenuCommands.CHEAT_TIME.getPattern().pattern())) {
           // System.out.println("here?");
            GameMenuController.cheatTime(command);
        } else if (command.matches(MenuCommands.SEASON.getPattern().pattern())) {
            GameMenuController.showSeason();
        }else if (command.matches(MenuCommands.WEATHER.getPattern().pattern())) {
            GameMenuController.showWeather();
        } else if (command.matches(MenuCommands.WEATHER_FORECAST.getPattern().pattern())) {
            GameMenuController.getTomorrowWeather();
        } else if (command.matches(MenuCommands.CHEAT_FORECAST.getPattern().pattern())) {
            GameMenuController.cheatWeather(command);
        } else if (command.matches(MenuCommands.SHOW_ENERGY.getPattern().pattern())) {
            GameMenuController.showEnergy();
        } else if (command.matches(MenuCommands.SET_ENERGY.getPattern().pattern())) {
            GameMenuController.setEnergy(command);
        }  else if (command.matches(MenuCommands.SET_UNLIMITED.getPattern().pattern())) {
            GameMenuController.setUnlimited();
        } else if (command.matches(MenuCommands.ENTER_HOME.getPattern().pattern())) {
            GameMenuController.enterHomeMenu(scanner);
        } else if (command.matches(MenuCommands.CRAFTING_SHOW_RECIPES.getPattern().pattern())) {
            GameMenuController.craftingShowRecipes();
        } else if (command.matches(MenuCommands.CRAFTING_CRAFT.getPattern().pattern())) {
            GameMenuController.handleCraftCommand(command);
        } else if (command.matches(MenuCommands.CHEAT_ADD_ITEM.getPattern().pattern())) {
            GameMenuController.handleCheatAddItemCommand(command);
        } else if (command.matches(MenuCommands.HELP_READ.getPattern().pattern())) {
           GameMenuController.printMapHelp();
        } else if (command.matches(MenuCommands.THOR.getPattern().pattern())) {
            GameMenuController.handleCheatThor(command);
        }else if (command.matches(MenuCommands.ARTISAN_USE.getPattern().pattern())) {
            GameMenuController.handleArtisanUseCommand(command);
        } else if (command.matches(MenuCommands.ARTISAN_GET.getPattern().pattern())) {
            GameMenuController.handleArtisanGetCommand(command);
        } else if (command.matches(MenuCommands.PLACE_ITEM.getPattern().pattern())) {
            GameMenuController.handlePlaceCommand(command);
        } else if (command.matches(MenuCommands.MEET_NPC.getPattern().pattern())) {
            GameMenuController.handleMeetNPC(command);
        } else if (command.matches(MenuCommands.GIFT_NPC.getPattern().pattern())) {
            GameMenuController.handleGiftCommand(command);
        } else if (command.matches(MenuCommands.LIST_FRIENDSHIP.getPattern().pattern())) {
            GameMenuController.handleList();
        } else if (command.matches(MenuCommands.QUESTS_LIST.getPattern().pattern())) {
            GameMenuController.handleQuestList();
        }  else if (command.matches(MenuCommands.FINISH_QUEST.getPattern().pattern())) {
            GameMenuController.handleQuestFinish(command);
        }else if (command.matches(MenuCommands.LIST_FRIENDSHIP.getPattern().pattern())) {
            GameMenuController.handleList();
        }else if (command.startsWith("show") || command.startsWith("purchase") || command.startsWith("energy") ||
                command.startsWith("talk")||command.startsWith("friendships")||command.startsWith("gift")||
                command.startsWith("respond")||command.startsWith("hug")||command.startsWith("flower")||
                command.startsWith("ask")||command.startsWith("start")||command.startsWith("trade")|| command.startsWith("craftinfo")
                ||command.startsWith("tools")||command.startsWith("inventory")|| command.startsWith("plant")||command.startsWith("howmuch")||
                command.startsWith("build")||command.startsWith("cheat")||command.startsWith("buy")||command.startsWith("collect")||
        command.startsWith("bond")||command.startsWith("showgrowthplant")||command.startsWith("cut")||command.startsWith("fertilize")||
        command.startsWith("showplant")||command.startsWith("plant")||command.startsWith("plant")||command.startsWith("eat")||
        command.startsWith("add")||command.startsWith("fishing")){
            GameMenuController.command(command);
        }
    }
}
