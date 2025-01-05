package net.tfobz.vocabletrainer.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VocableTrainerLocalization {
	
	public static enum localisation {
		English, Deutsch, Italiano
	}
	
    public static String INFO_DIALOG_CLOSE;
    public static String TWO_OPTION_YES;
    public static String TWO_OPTION_NO;
    public static String NEW_SET_TITLE;
    public static String NEW_SET_ENTER_SET_NAME;
    public static String NEW_SET_DESCRIPTION_1;
    public static String NEW_SET_DESCRIPTION_2;
    public static String BUTTON_CONFIRM;
    public static String BUTTON_CANCEL;
    public static String ERROR_SELECT_SET_AND_BOX;
    public static String ERROR_NO_CARDS_IN_BOX;
    public static String ERROR_TOO_MANY_CARDS;
    public static String ERROR_SET_DROPPED;
    public static String SETTINGS_LANGUAGE;
    public static String SETTINGS_THEME;
    public static String SETTINGS_SIMPLIFIED_VIEW;
    public static String SETTINGS_ALLOW_PREMIUM;
    public static String SETTINGS_BUY_PREMIUM;
    public static String MENU_HOME;
    public static String MENU_START;
    public static String MENU_INFO;
    public static String MENU_NEW;
    public static String MENU_CREDITS;
    public static String MENU_SETTINGS;
    public static String MENU_EXIT;
    public static String CREDITS_CREATORS;
    public static String CREDITS_HELPERS;
    public static String HOME_START;
    public static String HOME_NEW;
    public static String HOME_INFO;
    public static String HOME_EXIT;
    public static String BUTTON_SAVE;
    public static String BUTTON_STOP;
    public static String BUTTON_SKIP;
    public static String BUTTON_NEXT;
    public static String BUTTON_CHECK;
    public static String BUTTON_END;
    public static String STATISTICS_TOTAL_TIME;
    public static String STATISTICS_MAX_CARD_TIME;
    public static String STATISTICS_MIN_CARD_TIME;
    public static String STATISTICS_AVG_CARD_TIME;
    public static String STATISTICS_TOTAL_CARDS;
    public static String STATISTICS_SKIPPED_CARDS;
    public static String STATISTICS_WRONG_ANSWERS;
    public static String STATISTICS_CORRECT_ANSWERS;
    public static String STATISTICS_MAX_STREAK;
    public static String STATISTICS_ACCURACY;
    public static String PLACEHOLDER_INPUT_WORD;

    private static String path = "resources/localisation/english.yml";

    public static void loadLocalization(localisation l) {
    	
    	switch (l) {
    	case English:
    		path = "resources/localisation/english.yml";
    		break;
    	case Deutsch:
    		path = "resources/localisation/german.yml";
    		break;
    	case Italiano:
    		path = "resources/localisation/italian.yml";
    		break;
	}
    	
        Map<String, String> values = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            String currentKey = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (line.endsWith(":")) {
                    currentKey = currentKey.isEmpty() ? line.substring(0, line.length() - 1) : currentKey + "." + line.substring(0, line.length() - 1);
                } else if (line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    String key = currentKey + "." + parts[0].trim();
                    String value = parts[1].trim().replaceAll("^\"|\"$", "");
                    values.put(key, value);
                } else {
                    while (currentKey.contains(".")) {
                        currentKey = currentKey.substring(0, currentKey.lastIndexOf('.'));
                        if (!line.endsWith(":")) {
                            break;
                        }
                    }
                }
            }

            mapValues(values);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load localization: " + e.getMessage(), e);
        }
    }

    private static void mapValues(Map<String, String> values) {
        INFO_DIALOG_CLOSE = debugValue("INFO_DIALOG_CLOSE", values.getOrDefault("en.dialogs.info.close", ""));
        TWO_OPTION_YES = debugValue("TWO_OPTION_YES", values.getOrDefault("en.dialogs.two_option.yes", ""));
        TWO_OPTION_NO = debugValue("TWO_OPTION_NO", values.getOrDefault("en.dialogs.two_option.no", ""));
        NEW_SET_TITLE = debugValue("NEW_SET_TITLE", values.getOrDefault("en.dialogs.new_set.title", ""));
        NEW_SET_ENTER_SET_NAME = debugValue("NEW_SET_ENTER_SET_NAME", values.getOrDefault("en.dialogs.new_set.enter_set_name", ""));
        NEW_SET_DESCRIPTION_1 = debugValue("NEW_SET_DESCRIPTION_1", values.getOrDefault("en.dialogs.new_set.description_1", ""));
        NEW_SET_DESCRIPTION_2 = debugValue("NEW_SET_DESCRIPTION_2", values.getOrDefault("en.dialogs.new_set.description_2", ""));
        BUTTON_CONFIRM = debugValue("BUTTON_CONFIRM", values.getOrDefault("en.dialogs.input.confirm", ""));
        BUTTON_CANCEL = debugValue("BUTTON_CANCEL", values.getOrDefault("en.dialogs.input.cancel", ""));
        ERROR_SELECT_SET_AND_BOX = debugValue("ERROR_SELECT_SET_AND_BOX", values.getOrDefault("en.dialogs.errors.select_set_and_box", ""));
        ERROR_NO_CARDS_IN_BOX = debugValue("ERROR_NO_CARDS_IN_BOX", values.getOrDefault("en.dialogs.errors.no_cards_in_box", ""));
        ERROR_TOO_MANY_CARDS = debugValue("ERROR_TOO_MANY_CARDS", values.getOrDefault("en.dialogs.errors.too_many_cards", ""));
        ERROR_SET_DROPPED = debugValue("ERROR_SET_DROPPED", values.getOrDefault("en.dialogs.errors.set_dropped", ""));
        SETTINGS_LANGUAGE = debugValue("SETTINGS_LANGUAGE", values.getOrDefault("en.settings.language", ""));
        SETTINGS_THEME = debugValue("SETTINGS_THEME", values.getOrDefault("en.settings.theme", ""));
        SETTINGS_SIMPLIFIED_VIEW = debugValue("SETTINGS_SIMPLIFIED_VIEW", values.getOrDefault("en.settings.simplified_view", ""));
        SETTINGS_ALLOW_PREMIUM = debugValue("SETTINGS_ALLOW_PREMIUM", values.getOrDefault("en.settings.allow_premium", ""));
        SETTINGS_BUY_PREMIUM = debugValue("SETTINGS_BUY_PREMIUM", values.getOrDefault("en.settings.buy_premium", ""));
        MENU_HOME = debugValue("MENU_HOME", values.getOrDefault("en.menu.home", ""));
        MENU_START = debugValue("MENU_START", values.getOrDefault("en.menu.start", ""));
        MENU_INFO = debugValue("MENU_INFO", values.getOrDefault("en.menu.info", ""));
        MENU_NEW = debugValue("MENU_NEW", values.getOrDefault("en.menu.new", ""));
        MENU_CREDITS = debugValue("MENU_CREDITS", values.getOrDefault("en.menu.credits", ""));
        MENU_SETTINGS = debugValue("MENU_SETTINGS", values.getOrDefault("en.menu.settings", ""));
        MENU_EXIT = debugValue("MENU_EXIT", values.getOrDefault("en.menu.exit", ""));
        CREDITS_CREATORS = debugValue("CREDITS_CREATORS", values.getOrDefault("en.credits.creators", ""));
        CREDITS_HELPERS = debugValue("CREDITS_HELPERS", values.getOrDefault("en.credits.helpers", ""));
        HOME_START = debugValue("HOME_START", values.getOrDefault("en.home.start", ""));
        HOME_NEW = debugValue("HOME_NEW", values.getOrDefault("en.home.new", ""));
        HOME_INFO = debugValue("HOME_INFO", values.getOrDefault("en.home.info", ""));
        HOME_EXIT = debugValue("HOME_EXIT", values.getOrDefault("en.home.exit", ""));
        BUTTON_SAVE = debugValue("BUTTON_SAVE", values.getOrDefault("en.buttons.save", ""));
        BUTTON_STOP = debugValue("BUTTON_STOP", values.getOrDefault("en.buttons.stop", ""));
        BUTTON_SKIP = debugValue("BUTTON_SKIP", values.getOrDefault("en.buttons.skip", ""));
        BUTTON_NEXT = debugValue("BUTTON_NEXT", values.getOrDefault("en.buttons.next", ""));
        BUTTON_CHECK = debugValue("BUTTON_CHECK", values.getOrDefault("en.buttons.check", ""));
        BUTTON_END = debugValue("BUTTON_END", values.getOrDefault("en.buttons.end", ""));
        STATISTICS_TOTAL_TIME = debugValue("STATISTICS_TOTAL_TIME", values.getOrDefault("en.statistics.total_time", ""));
        STATISTICS_MAX_CARD_TIME = debugValue("STATISTICS_MAX_CARD_TIME", values.getOrDefault("en.statistics.max_card_time", ""));
        STATISTICS_MIN_CARD_TIME = debugValue("STATISTICS_MIN_CARD_TIME", values.getOrDefault("en.statistics.min_card_time", ""));
        STATISTICS_AVG_CARD_TIME = debugValue("STATISTICS_AVG_CARD_TIME", values.getOrDefault("en.statistics.avg_card_time", ""));
        STATISTICS_TOTAL_CARDS = debugValue("STATISTICS_TOTAL_CARDS", values.getOrDefault("en.statistics.total_cards", ""));
        STATISTICS_SKIPPED_CARDS = debugValue("STATISTICS_SKIPPED_CARDS", values.getOrDefault("en.statistics.skipped_cards", ""));
        STATISTICS_WRONG_ANSWERS = debugValue("STATISTICS_WRONG_ANSWERS", values.getOrDefault("en.statistics.wrong_answers", ""));
        STATISTICS_CORRECT_ANSWERS = debugValue("STATISTICS_CORRECT_ANSWERS", values.getOrDefault("en.statistics.correct_answers", ""));
        STATISTICS_MAX_STREAK = debugValue("STATISTICS_MAX_STREAK", values.getOrDefault("en.statistics.max_streak", ""));
        STATISTICS_ACCURACY = debugValue("STATISTICS_ACCURACY", values.getOrDefault("en.statistics.accuracy", ""));
        PLACEHOLDER_INPUT_WORD = debugValue("PLACEHOLDER_INPUT_WORD", values.getOrDefault("en.placeholders.input_word", ""));
    }

    private static String debugValue(String name, String value) {
        if (value.isEmpty()) {
            System.out.println("Missing key: " + name);
        } else {
            System.out.println(name + " = " + value);
        }
        return value;
    }
}
