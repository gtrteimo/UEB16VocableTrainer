package net.tfobz.vocabletrainer.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VocableTrainerLocalization {
	
	public static enum localisation {
		English, Deutsch, Italiano
	}
	
	public static String TITLE;
	public static String SECONDS;
	public static String MINUTES;
	public static String HOURS;
    public static String INFO_DIALOG_CLOSE;
    public static String TWO_OPTION_YES;
    public static String TWO_OPTION_NO;
    public static String NEW_SET_TITLE;
    public static String NEW_SET_ENTER_SET_NAME;
    public static String NEW_SET_DESCRIPTION_1;
    public static String NEW_SET_DESCRIPTION_2;
    public static String RENAME_SET;
    public static String RENAME_SET_QUESTION;
    public static String CONFIRM_DELETE;
    public static String CONFIRM_DELETE_QUESTION;
    public static String BUTTON_CONFIRM;
    public static String BUTTON_CANCEL;
    public static String ERROR;
    public static String ERROR_SELECT_SET;
    public static String ERROR_SELECT_BOX;
    public static String ERROR_NO_CARDS_IN_BOX;
    public static String ERROR_TOO_MANY_CARDS;
    public static String ERROR_SET_DROPPED;
    public static String ERROR_BOX_CREATION_FAIL;
    public static String ERROR_CARD_CREATION_FAIL;
    public static String ERROR_DUPLICATE_CARD;
    public static String SETTINGS_LANGUAGE;
    public static String SETTINGS_THEME;
    public static String SETTINGS_SIMPLIFIED_VIEW;
    public static String SETTINGS_ALLOW_PREMIUM;
    public static String SETTINGS_SUGGEST_FEATURE;
    public static String START_BOX;
    public static String START_BOX_ALL;
    public static String START_BOX_DUE;
    public static String START_TIME_PER_CARD;
    public static String START_TOTAL_TIME;
    public static String START_CASE_SENSITIVE;
    public static String START_AMOUNT_OF_CARDS;
    public static String START_PRACTISE_RUN;
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
    public static String BUTTON_START;
    public static String BUTTON_SAVE;
    public static String BUTTON_STOP;
    public static String BUTTON_SKIP;
    public static String BUTTON_NEXT;
    public static String BUTTON_CHECK;
    public static String BUTTON_END;
    public static String BUTTON_CLOSE;
    public static String BUTTON_IMPORT;
    public static String BUTTON_EXPORT;
    public static String BUTTON_RENAME;
    public static String BUTTON_DELETE;
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

    private static String path = "resources/localisation/german.yml";

    /**
     * Loads localization data from the specified YAML file based on the selected language.
     *
     * @param l the selected localization enum (English, Deutsch, Italiano)
     */
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
             // Stack to keep track of the current key hierarchy
             Deque<String> keyStack = new ArrayDeque<>();
             // Number of spaces per indentation level (assuming 2)
             final int indentSize = 2;

             while ((line = reader.readLine()) != null) {
                 // Trim trailing spaces but keep leading spaces for indentation
                 String trimmedLine = line.replaceAll("\\s+$", "");

                 if (trimmedLine.trim().isEmpty() || trimmedLine.trim().startsWith("#")) {
                     continue; // Skip empty lines and comments
                 }

                 // Determine current indentation level (number of leading spaces)
                 int currentIndent = countLeadingSpaces(trimmedLine);
                 // Calculate depth based on indentation
                 int depth = currentIndent / indentSize;

                 // Extract the actual content without leading spaces
                 String content = trimmedLine.trim();

                 // Adjust the stack based on the current depth
                 while (keyStack.size() > depth) {
                     keyStack.pop();
                 }

                 if (content.endsWith(":")) {
                     // It's a key that has nested keys
                     String key = content.substring(0, content.length() - 1).trim();
                     keyStack.push(key);
                 } else if (content.contains(":")) {
                     // It's a key-value pair
                     String[] parts = content.split(":", 2);
                     String keyPart = parts[0].trim();
                     String valuePart = parts[1].trim().replaceAll("^\"|\"$", ""); // Remove surrounding quotes

                     // Build the full key by joining the stack (reversed) and the current key
                     List<String> keys = new ArrayList<>(keyStack);
                     Collections.reverse(keys);
                     keys.add(keyPart);
                     String fullKey = String.join(".", keys);

                     values.put(fullKey, valuePart);
                 }
                 // Lines without ":" are ignored in this implementation
             }

         } catch (IOException e) {
             throw new RuntimeException("Failed to load localization: " + e.getMessage(), e);
         }

         mapValues(values, l);
     }

	/**
     * Counts the number of leading spaces in a string.
     *
     * @param s the string to evaluate
     * @return the number of leading spaces
     */
	private static int countLeadingSpaces(String s) {
	     int count = 0;
	     while (count < s.length() && s.charAt(count) == ' ') {
	         count++;
	     }
	     return count;
	}

	/**
     * Assigns the parsed localization values to the static fields.
     *
     * @param values the map containing the localization key-value pairs
     */
	private static void mapValues(Map<String, String> values, localisation l) {
		TITLE = values.getOrDefault(l+".title.title", "");
		SECONDS = values.getOrDefault(l+".time.seconds", "");
		MINUTES = values.getOrDefault(l+".time.minutes", "");
		HOURS = values.getOrDefault(l+".time.hours", "");
        INFO_DIALOG_CLOSE = values.getOrDefault(l+".dialogs.info.close", "");
        TWO_OPTION_YES = values.getOrDefault(l+".dialogs.two_option.yes", "");
        TWO_OPTION_NO = values.getOrDefault(l+".dialogs.two_option.no", "");
        NEW_SET_TITLE = values.getOrDefault(l+".dialogs.new_set.title", "");
        NEW_SET_ENTER_SET_NAME = values.getOrDefault(l+".dialogs.new_set.enter_set_name", "");
        NEW_SET_DESCRIPTION_1 = values.getOrDefault(l+".dialogs.new_set.description_1", "");
        NEW_SET_DESCRIPTION_2 = values.getOrDefault(l+".dialogs.new_set.description_2", "");
        RENAME_SET = values.getOrDefault(l+".dialogs.input.rename_title", "");
        RENAME_SET_QUESTION = values.getOrDefault(l+".dialogs.input.rename_description", "");
        CONFIRM_DELETE = values.getOrDefault(l+".dialogs.two_option.confirm_delete", "");
        CONFIRM_DELETE_QUESTION = values.getOrDefault(l+".dialogs.two_option.confirm_delete_question", "");
        BUTTON_CONFIRM = values.getOrDefault(l+".dialogs.input.confirm", "");
        BUTTON_CANCEL = values.getOrDefault(l+".dialogs.input.cancel", "");
        ERROR = values.getOrDefault(l+".dialogs.errors.error", "");
        ERROR_SELECT_SET = values.getOrDefault(l+".dialogs.errors.select_set", "");
        ERROR_SELECT_BOX = values.getOrDefault(l+".dialogs.errors.select_box", "");
        ERROR_NO_CARDS_IN_BOX = values.getOrDefault(l+".dialogs.errors.no_cards_in_box", "");
        ERROR_TOO_MANY_CARDS = values.getOrDefault(l+".dialogs.errors.too_many_cards", "");
        ERROR_SET_DROPPED = values.getOrDefault(l+".dialogs.errors.set_dropped", "");
        ERROR_BOX_CREATION_FAIL = values.getOrDefault(l+".dialogs.errors.box_creation_fail", "");
        ERROR_CARD_CREATION_FAIL = values.getOrDefault(l+".dialogs.errors.card_creation_fail", "");
        ERROR_DUPLICATE_CARD = values.getOrDefault(l+".dialogs.errors.dupilcate_card", "");
        SETTINGS_LANGUAGE = values.getOrDefault(l+".settings.language", "");
        SETTINGS_THEME = values.getOrDefault(l+".settings.theme", "");
        SETTINGS_SIMPLIFIED_VIEW = values.getOrDefault(l+".settings.simplified_view", "");
        SETTINGS_ALLOW_PREMIUM = values.getOrDefault(l+".settings.allow_premium", "");
        SETTINGS_SUGGEST_FEATURE = values.getOrDefault(l+".settings.suggest_feature", "");
        START_BOX = values.getOrDefault(l+".start.box", "");
        START_BOX_ALL = values.getOrDefault(l+".start.box_all", "");
        START_BOX_DUE = values.getOrDefault(l+".start.box_due", "");
        START_TIME_PER_CARD = values.getOrDefault(l+".start.time_per_card", "");
        START_TOTAL_TIME = values.getOrDefault(l+".start.total_time", "");
        START_CASE_SENSITIVE = values.getOrDefault(l+".start.case_sensitive", "");
        START_AMOUNT_OF_CARDS = values.getOrDefault(l+".start.amount_of_cards", "");
        START_PRACTISE_RUN = values.getOrDefault(l+".start.practise_run", "");
        MENU_HOME = values.getOrDefault(l+".menu.home", "");
        MENU_START = values.getOrDefault(l+".menu.start", "");
        MENU_INFO = values.getOrDefault(l+".menu.info", "");
        MENU_NEW = values.getOrDefault(l+".menu.new", "");
        MENU_CREDITS = values.getOrDefault(l+".menu.credits", "");
        MENU_SETTINGS = values.getOrDefault(l+".menu.settings", "");
        MENU_EXIT = values.getOrDefault(l+".menu.exit", "");
        CREDITS_CREATORS = values.getOrDefault(l+".credits.creators", "");
        CREDITS_HELPERS = values.getOrDefault(l+".credits.helpers", "");
        HOME_START = values.getOrDefault(l+".home.start", "");
        HOME_NEW = values.getOrDefault(l+".home.new", "");
        HOME_INFO = values.getOrDefault(l+".home.info", "");
        HOME_EXIT = values.getOrDefault(l+".home.exit", "");
        BUTTON_START = values.getOrDefault(l+".buttons.start", "");
        BUTTON_SAVE = values.getOrDefault(l+".buttons.save", "");
        BUTTON_STOP = values.getOrDefault(l+".buttons.stop", "");
        BUTTON_SKIP = values.getOrDefault(l+".buttons.skip", "");
        BUTTON_NEXT = values.getOrDefault(l+".buttons.next", "");
        BUTTON_CHECK = values.getOrDefault(l+".buttons.check", "");
        BUTTON_END = values.getOrDefault(l+".buttons.end", "");
        BUTTON_CLOSE = values.getOrDefault(l+".buttons.close", "");
        BUTTON_IMPORT = values.getOrDefault(l+".buttons.import", "");
        BUTTON_EXPORT = values.getOrDefault(l+".buttons.export", "");
        BUTTON_RENAME = values.getOrDefault(l+".buttons.rename", "");
        BUTTON_DELETE = values.getOrDefault(l+".buttons.delete", "");
        STATISTICS_TOTAL_TIME = values.getOrDefault(l+".statistics.total_time", "");
        STATISTICS_MAX_CARD_TIME = values.getOrDefault(l+".statistics.max_card_time", "");
        STATISTICS_MIN_CARD_TIME = values.getOrDefault(l+".statistics.min_card_time", "");
        STATISTICS_AVG_CARD_TIME = values.getOrDefault(l+".statistics.avg_card_time", "");
        STATISTICS_TOTAL_CARDS = values.getOrDefault(l+".statistics.total_cards", "");
        STATISTICS_SKIPPED_CARDS = values.getOrDefault(l+".statistics.skipped_cards", "");
        STATISTICS_WRONG_ANSWERS = values.getOrDefault(l+".statistics.wrong_answers", "");
        STATISTICS_CORRECT_ANSWERS = values.getOrDefault(l+".statistics.correct_answers", "");
        STATISTICS_MAX_STREAK = values.getOrDefault(l+".statistics.max_streak", "");
        STATISTICS_ACCURACY = values.getOrDefault(l+".statistics.accuracy", "");
        PLACEHOLDER_INPUT_WORD = values.getOrDefault(l+".placeholders.input_word", "");
    }
}
