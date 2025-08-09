package io.github.some_example_name.model.enums;

import java.util.regex.Pattern;

public enum MenuCommands {
    REGISTER("register\\s+-u\\s+(?<username>.*)\\s+-p\\s+(?<password>.*)\\s+(?<passwordConfirm>.*)\\s+-n\\s+(?<nickname>.*)\\s+-e\\s+" +
            "(?<email>.*)\\s+-g\\s+(?<gender>.*)"),
    PICK_QUESTION("pick\\s+question\\s+-q\\s+(?<questionNumber>.*)\\s+-a\\s+(?<answer>.*)\\s+-c\\s+(?<answerConfirm>.*)"),
    LOGIN("login\\s+-u\\s+(?<username>\"[^\"]+\"|\\S+)\\s+-p\\s+(?<password>\"[^\"]*\"|\\S*)(?:\\s+(?<stay>--stay-logged-in))?"),
    FORGET_PASSWORD("forget\\s+password\\s+-u\\s+(?<username>.*)"),
    ANSWER("answer\\s+-a\\s+(?<answer>.*)"),
    CHANGE_USERNAME("change\\s+username\\s+-u\\s+(?<username>.*)"),
    CHANGE_NICKNAME("change\\s+nickname\\s+-u\\s+(?<nickname>.*)"),
    CHANGE_EMAIL("change\\s+email\\s+(?<email>.*)"),
    LOGOUT("user\\s+logout"),
    SHOW_CURRENT_MENU("show\\s+current\\s+menu"),
    MENU_ENTRANCE("menu\\s+enter\\s+(?<menuName>.*)"),
    START_NEW_GAME("game\\s+new\\s+-u\\s+(?<users>.+)"),
    CHANGE_PASSWORD("change\\s+password\\s+-p\\s+(?<newPassword>.*)\\s+-o\\s+(?<oldPassword>.*)"),
    PRINT("print\\s+map\\s+-l\\s+(?<x>\\d),(?<y>\\d)\\s*-s\\s*(?<size>.*)"),
    WALK("walk\\s+-l\\s+(?<x>\\d+),(?<y>\\d+)"),
    EXIT("exit\\s+game"),
    LOAD("load\\s+game"),
    ENTER_HOME("enter\\s+home"),
    TIME("time"),
    DATE("date"),
    TIME_AND_DATE("datetime"),
    DAY_OF_WEEK("day\\s+of\\s+the\\s+week"),
    CHEAT_TIME("cheat\\s+advance\\s+time\\s+(?<X>.*)h"),
    CHEAT_DAY("cheat\\s+advance\\s+date\\s+(?<X>.*)d"),
    SEASON("season"),
    WEATHER("weather"),
    WEATHER_FORECAST("weather\\s+forecast"),
    CHEAT_FORECAST("cheat\\s+weather\\s+set\\s+(?<Type>.*)"),
    SHOW_ENERGY("energy\\s+show"),
    SET_ENERGY("energy\\s+set\\s+-v\\s+(?<value>.*)"),
    SET_UNLIMITED("energy\\s+unlimited"),
    CRAFTING_SHOW_RECIPES("crafting\\s+show\\s+recipes"),
    CRAFTING_CRAFT("crafting\\s+craft\\s+(?<itemName>.*)"),
    //PLACE_ITEM("place item -n (?<name>\\w+) -d (?<dir>[A-Z]{1,2})"),
    CHEAT_ADD_ITEM("cheat add item -n (?<name>\\w+) -c (?<count>\\d+)"),
    HELP_READ("help\\s+reading\\s+map"),
    THOR("cheat\\s+Thor\\s+-l\\s=(?<x>.*),(?<y>.*)"),
    ARTISAN_USE("artisan\\s+use\\s+(?<artisan>\\S+)\\s*(?<items>.+)"),
    ARTISAN_GET("artisan\\s+get\\s+(?<artisan>\\S+)"),
    PLACE_ITEM("place\\s+item\\s+-n\\s+(?<name>.*)\\s+-d\\s+(?<dir>up|down|left|right|upleft|upright|downleft|downright)"),
    MEET_NPC("meet\\s+NPC\\s+(?<npcName>.*)"),
    GIFT_NPC("gift\\s+NPC\\s+(?<npcName>.*)\\s+-i\\s+(?<item>.*)"),
    LIST_FRIENDSHIP("friendship\\s+NPC\\s+list"),
    QUESTS_LIST("quests\\s+list"),
    FINISH_QUEST("quests\\s+finish\\s+-i\\s+(?<index>.*)"),
    NEXT_TURN("next\\s+turn");


    private final Pattern pattern;

    MenuCommands(String regex) {
        this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public Pattern getPattern() {
        return pattern;
    }
}
