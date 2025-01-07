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
	
	public static String MAIN_TITLE;
	
	public static String DATA_SECONDS;
	public static String DATA_MINUTES;
	public static String DATA_HOURS;
	
	public static String EXPORT_EXPORT;
	public static String EXPORT_QUESTION;
	
    public static String DIALOG_INFO_CLOSE;
    
    public static String DIALOG_TWO_OPTION_YES;
    public static String DIALOG_TWO_OPTION_NO;
    public static String DIALOG_TWO_OPTION_DELETE_TITLE;
    public static String DIALOG_TWO_OPTION_DELETE_QUESTION;
    public static String DIALOG_TWO_OPTION_DELETE_CONFIRM;
    public static String DIALOG_TWO_OPTION_DELETE_CANCEL;
    
    public static String DIALOG_NEW_SET_TITLE;
    public static String DIALOG_NEW_SET_ENTER_SET_NAME;
    public static String DIALOG_NEW_SET_DESCRIPTION_1;
    public static String DIALOG_NEW_SET_DESCRIPTION_2;
    public static String DIALOG_NEW_SET_SAVE;
    public static String DIALOG_NEW_SET_CANCEL;
    
    public static String DIALOG_INPUT_RENAME;
    public static String DIALOG_INPUT_QUESTION;
    public static String DIALOG_INPUT_CONFIRM;
    public static String DIALOG_INPUT_CANCEL;
    
    public static String DIALOG_COLOUR_CHOOSER_TITLE;
    public static String DIALOG_COLOUR_CHOOSER_TITLE2;
    public static String DIALOG_COLOUR_CHOOSER_CHOOSE_COLOR;
    public static String DIALOG_COLOUR_CHOOSER_COLOUR_MENU_BAR;
    public static String DIALOG_COLOUR_CHOOSER_COLOUR_MAIN_BACKGROUND;
    public static String DIALOG_COLOUR_CHOOSER_COLOUR_TEXT_1;
    public static String DIALOG_COLOUR_CHOOSER_COLOUR_TEXT_2;
    public static String DIALOG_COLOUR_CHOOSER_COLOUR_BUTTON_BACKGROUND;
    public static String DIALOG_COLOUR_CHOOSER_APPLY;
    public static String DIALOG_COLOUR_CHOOSER_CLOSE;
    
    public static String ERROR;
    public static String ERROR_DATABASE_DROPPED;
    public static String ERROR_START_SELECT_SET;
    public static String ERROR_START_SELECT_BOX;
    public static String ERROR_START_NO_CARDS_IN_BOX;
    public static String ERROR_START_TOO_MANY_CARDS;
    public static String ERROR_NEW_ENTER_DATA;
    public static String ERROR_NEW_SET_SET_CREATION_FAIL;
    public static String ERROR_NEW_BOX_CREATION_FAIL;
    public static String ERROR_NEW_CARD_CREATION_FAIL;
    public static String ERROR_IMPORT_DUPLICATE_CARD;
    public static String ERROR_SETTINGS_NO_INTERNET;
    public static String ERROR_SETTINGS_NOT_SUPPORTED;
    
    public static String MENU_HOME;
    public static String MENU_START;
    public static String MENU_INFO;
    public static String MENU_NEW;
    public static String MENU_CREDITS;
    public static String MENU_SETTINGS;
    public static String MENU_HELP;
    
    public static String HOME_NAME;
    public static String HOME_START;
    public static String HOME_NEW;
    public static String HOME_INFO;
    public static String HOME_EXIT;
    
    public static String NEW_NAME;
    public static String NEW_NEW_SET;
    public static String NEW_IMPORT;
    public static String NEW_EXPORT;
    public static String NEW_SAVE;
    public static String NEW_RENAME;
    public static String NEW_DELETE;
     
    public static String INFO_NAME;
    public static String INFO_TOPIC;
    public static String INFO_RENAME;
    public static String INFO_SAVE;
    public static String INFO_DELETE;
    public static String INFO_DATE_MODIFIED;
    public static String INFO_LAST_ASKED;
    public static String INFO_BOX;
    
    public static String START_NAME;
    public static String START_BOX_BOX;
    public static String START_BOX_ALL;
    public static String START_BOX_DUE;
    public static String START_TIME_PER_CARD;
    public static String START_TOTAL_TIME;
    public static String START_CASE_SENSITIVE;
    public static String START_AMOUNT_OF_CARDS;
    public static String START_PRACTISE_RUN;
    public static String START_DIRECTION_DIRECTION;
    public static String START_DIRECTION_OPTION_1;
    public static String START_DIRECTION_OPTION_2;
    public static String START_DIRECTION_OPTION_3;
    public static String START_START;
    
    public static String CREDITS_NAME;
    public static String CREDITS_CREATORS;
    public static String CREDITS_HELPERS;

    public static String SETTINGS_NAME;
    public static String SETTINGS_LANGUAGE;
    public static String SETTINGS_THEME;
    public static String SETTINGS_SIMPLIFIED_VIEW;
    public static String SETTINGS_ALLOW_PREMIUM;
    public static String SETTINGS_SUGGEST_FEATURE;

    public static String RUN_NAME;
    public static String RUN_TIME_TOTAL_TIME;
    public static String RUN_TIME_TIME_PER_CARD;
    public static String RUN_STOP;
    public static String RUN_SKIP;
    public static String RUN_CHECK;
    public static String RUN_NEXT;
    public static String RUN_END;
    public static String RUN_ANSWER;

    public static String RUN_STATS_TOTAL_TIME;
    public static String RUN_STATS_MAX_CARD_TIME;
    public static String RUN_STATS_MIN_CARD_TIME;
    public static String RUN_STATS_AVG_CARD_TIME;
    public static String RUN_STATS_TOTAL_CARDS;
    public static String RUN_STATS_SKIPPED_CARDS;
    public static String RUN_STATS_WRONG_ANSWERS;
    public static String RUN_STATS_CORRECT_ANSWERS;
    public static String RUN_STATS_MAX_STREAK;
    public static String RUN_STATS_ACCURACY;
    
    private static final String PATH = "src/net/tfobz/vocabletrainer/main/resources/localisation/_.yml";

    public static void loadLocalization(localisation l) {
    	    	
    	String path = PATH.replaceAll("_", l+"");
    	
    	System.out.println(path);
    	
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

	private static int countLeadingSpaces(String s) {
	     int count = 0;
	     while (count < s.length() && s.charAt(count) == ' ') {
	         count++;
	     }
	     return count;
	}
	
	private static void mapValues(Map<String, String> values, localisation l) {
		 MAIN_TITLE = values.getOrDefault(l+".main.title", "MAIN_TITLE");
		
		 DATA_SECONDS = values.getOrDefault(l+".data.seconds", "DATA_SECONDS");
		 DATA_MINUTES = values.getOrDefault(l+".data.minutes", "DATA_MINUTES");
		 DATA_HOURS = values.getOrDefault(l+".data.hours", "DATA_HOURS");
		
		 EXPORT_EXPORT = values.getOrDefault(l+".export.export", "EXPORT_EXPORT");
		 EXPORT_QUESTION = values.getOrDefault(l+".export.question", "EXPORT_QUESTION");
		
	     DIALOG_INFO_CLOSE = values.getOrDefault(l+".dialog.info.close", "DIALOG_INFO_CLOSE");
	    
	     DIALOG_TWO_OPTION_YES = values.getOrDefault(l+".dialog.two_options.yes", "DIALOG_TWO_OPTION_YES");
	     DIALOG_TWO_OPTION_NO = values.getOrDefault(l+".dialog.two_options.no", "DIALOG_TWO_OPTION_NO");
	     DIALOG_TWO_OPTION_DELETE_TITLE = values.getOrDefault(l+".dialog.two_options.delete.title", "DIALOG_TWO_OPTION_DELETE_TITLE");
	     DIALOG_TWO_OPTION_DELETE_QUESTION = values.getOrDefault(l+".dialog.two_options.delete.question", "DIALOG_TWO_OPTION_DELETE_QUESTION");
	     DIALOG_TWO_OPTION_DELETE_CONFIRM = values.getOrDefault(l+".dialog.two_options.delete.confirm", "DIALOG_TWO_OPTION_DELETE_CONFIRM");
	     DIALOG_TWO_OPTION_DELETE_CANCEL = values.getOrDefault(l+".dialog.two_options.delete.cancel", "DIALOG_TWO_OPTION_DELETE_CANCEL");
	    
	     DIALOG_NEW_SET_TITLE = values.getOrDefault(l+".dialog.new_set.title", "DIALOG_NEW_SET_TITLE");
	     DIALOG_NEW_SET_ENTER_SET_NAME = values.getOrDefault(l+".dialog.new_set.enter_set_name", "DIALOG_NEW_SET_ENTER_SET_NAME");
	     DIALOG_NEW_SET_DESCRIPTION_1 = values.getOrDefault(l+".dialog.new_set.description1", "DIALOG_NEW_SET_DESCRIPTION_1");
	     DIALOG_NEW_SET_DESCRIPTION_2 = values.getOrDefault(l+".dialog.new_set.description2", "DIALOG_NEW_SET_DESCRIPTION_2");
	     DIALOG_NEW_SET_SAVE = values.getOrDefault(l+".dialog.new_set.save", "DIALOG_NEW_SET_SAVE");
	     DIALOG_NEW_SET_CANCEL = values.getOrDefault(l+".dialog.new_set.cancel", "DIALOG_NEW_SET_CANCEL");
	    
	     DIALOG_INPUT_RENAME = values.getOrDefault(l+".dialog.input.rename", "DIALOG_INPUT_RENAME");
	     DIALOG_INPUT_QUESTION = values.getOrDefault(l+".dialog.input.question", "DIALOG_INPUT_QUESTION");
	     DIALOG_INPUT_CONFIRM = values.getOrDefault(l+".dialog.input.confirm", "DIALOG_INPUT_CONFIRM");
	     DIALOG_INPUT_CANCEL = values.getOrDefault(l+".dialog.input.cancel", "DIALOG_INPUT_CANCEL");
	    
	     DIALOG_COLOUR_CHOOSER_TITLE = values.getOrDefault(l+".dialog.colour_chooser.title", "DIALOG_COLOUR_CHOOSER_TITLE");
	     DIALOG_COLOUR_CHOOSER_TITLE2 = values.getOrDefault(l+".dialog.colour_chooser.choose_color", "DIALOG_COLOUR_CHOOSER_TITLE2");
	     DIALOG_COLOUR_CHOOSER_COLOUR_MENU_BAR = values.getOrDefault(l+".dialog.colour_chooser.colour.menu_bar", "DIALOG_COLOUR_CHOOSER_COLOUR_MENU_BAR");
	     DIALOG_COLOUR_CHOOSER_COLOUR_MAIN_BACKGROUND = values.getOrDefault(l+".dialog.colour_chooser.colour.main_background", "DIALOG_COLOUR_CHOOSER_COLOUR_MAIN_BACKGROUND");
	     DIALOG_COLOUR_CHOOSER_COLOUR_TEXT_1 = values.getOrDefault(l+".dialog.colour_chooser.colour.text_1", "DIALOG_COLOUR_CHOOSER_COLOUR_TEXT_1");
	     DIALOG_COLOUR_CHOOSER_COLOUR_TEXT_2 = values.getOrDefault(l+".dialog.colour_chooser.colour.text_2", "DIALOG_COLOUR_CHOOSER_COLOUR_TEXT_2");
	     DIALOG_COLOUR_CHOOSER_COLOUR_BUTTON_BACKGROUND = values.getOrDefault(l+".dialog.colour_chooser.colour.button_background", "DIALOG_COLOUR_CHOOSER_COLOUR_BUTTON_BACKGROUND");
	     DIALOG_COLOUR_CHOOSER_APPLY = values.getOrDefault(l+".dialog.colour_chooser.apply", "DIALOG_COLOUR_CHOOSER_APPLY");
	     DIALOG_COLOUR_CHOOSER_CLOSE = values.getOrDefault(l+".dialog.colour_chooser.close", "DIALOG_COLOUR_CHOOSER_CLOSE");
	     
	     ERROR = values.getOrDefault(l+".error.error", "ERROR");
	     ERROR_DATABASE_DROPPED = values.getOrDefault(l+".error.database_dropped", "ERROR_DATABASE_DROPPED");
	     ERROR_START_SELECT_SET = values.getOrDefault(l+".error.start.select_set", "ERROR_START_SELECT_SET");
	     ERROR_START_SELECT_BOX = values.getOrDefault(l+".error.start.select_box", "ERROR_START_SELECT_BOX");
	     ERROR_START_NO_CARDS_IN_BOX = values.getOrDefault(l+".error.start.no_cards_in_box", "ERROR_START_NO_CARDS_IN_BOX");
	     ERROR_START_TOO_MANY_CARDS = values.getOrDefault(l+".error.start.too_many_cards", "ERROR_START_TOO_MANY_CARDS");
	     ERROR_NEW_ENTER_DATA = values.getOrDefault(l+".error.new.enter_data", "ERROR_NEW_ENTER_DATA");
	     ERROR_NEW_SET_SET_CREATION_FAIL = values.getOrDefault(l+".error.new.set_creation_fail", "ERROR_NEW_SET_SET_CREATION_FAIL");
	     ERROR_NEW_BOX_CREATION_FAIL = values.getOrDefault(l+".error.new.box_creation_fail", "ERROR_NEW_BOX_CREATION_FAIL");
	     ERROR_NEW_CARD_CREATION_FAIL = values.getOrDefault(l+".error.new.card_creation_fail", "ERROR_NEW_CARD_CREATION_FAIL");
	     ERROR_IMPORT_DUPLICATE_CARD = values.getOrDefault(l+".error.import.duplicate_card", "ERROR_IMPORT_DUPLICATE_CARD");
	     ERROR_SETTINGS_NO_INTERNET = values.getOrDefault(l+".error.settings.no_internet", "ERROR_SETTINGS_NO_INTERNET");
	     ERROR_SETTINGS_NOT_SUPPORTED = values.getOrDefault(l+".error.settings.not_supported", "ERROR_SETTINGS_NOT_SUPPORTED");
	    
	     MENU_HOME = values.getOrDefault(l+".menu.home", "MENU_HOME");
	     MENU_START = values.getOrDefault(l+".menu.start", "MENU_START");
	     MENU_INFO = values.getOrDefault(l+".menu.info", "MENU_INFO");
	     MENU_NEW = values.getOrDefault(l+".menu.new", "MENU_NEW");
	     MENU_CREDITS = values.getOrDefault(l+".menu.credits", "MENU_CREDITS");
	     MENU_SETTINGS = values.getOrDefault(l+".menu.settings", "MENU_SETTINGS");
	     MENU_HELP = values.getOrDefault(l+".menu.help", "MENU_HELP");
	    
	     HOME_NAME = values.getOrDefault(l+".home.name", "HOME_NAME");
	     HOME_START = values.getOrDefault(l+".home.start", "HOME_START");
	     HOME_NEW = values.getOrDefault(l+".home.new", "HOME_NEW");
	     HOME_INFO = values.getOrDefault(l+".home.info", "HOME_INFO");
	     HOME_EXIT = values.getOrDefault(l+".home.exit", "HOME_EXIT");
	    
	     NEW_NAME = values.getOrDefault(l+".new.name", "NEW_NAME");
	     NEW_NEW_SET = values.getOrDefault(l+".new.new_set", "NEW_NEW_SET");
	     NEW_IMPORT = values.getOrDefault(l+".new.import", "NEW_IMPORT");
	     NEW_EXPORT = values.getOrDefault(l+".new.export", "NEW_EXPORT");
	     NEW_SAVE = values.getOrDefault(l+".new.save", "NEW_SAVE");
	     NEW_RENAME = values.getOrDefault(l+".new.rename", "NEW_RENAME");
	     NEW_DELETE = values.getOrDefault(l+".new.delete", "NEW_DELETE");
	     
	     INFO_NAME = values.getOrDefault(l+".info.name", "INFO_NAME");
	     INFO_TOPIC = values.getOrDefault(l+".info.topic", "INFO_TOPIC");
	     INFO_RENAME = values.getOrDefault(l+".info.rename", "INFO_RENAME");
	     INFO_SAVE = values.getOrDefault(l+".info.save", "INFO_SAVE");
	     INFO_DELETE = values.getOrDefault(l+".info.delete", "INFO_DELETE");
	     INFO_DATE_MODIFIED = values.getOrDefault(l+".info.date_modified", "INFO_DATE_MODIFIED");
	     INFO_LAST_ASKED = values.getOrDefault(l+".info.last_asked", "INFO_LAST_ASKED");
	     INFO_BOX = values.getOrDefault(l+".info.box", "INFO_BOX");
	    
	     START_NAME = values.getOrDefault(l+".start.name", "START_NAME");
	     START_BOX_BOX = values.getOrDefault(l+".start.box.box", "START_BOX_BOX");
	     START_BOX_ALL = values.getOrDefault(l+".start.box.all", "START_BOX_ALL");
	     START_BOX_DUE = values.getOrDefault(l+".start.box.due", "START_BOX_DUE");
	     START_TIME_PER_CARD = values.getOrDefault(l+".start.time_per_card", "START_TIME_PER_CARD");
	     START_TOTAL_TIME = values.getOrDefault(l+".start.total_time", "START_TOTAL_TIME");
	     START_CASE_SENSITIVE = values.getOrDefault(l+".start.case_sensitive", "START_CASE_SENSITIVE");
	     START_AMOUNT_OF_CARDS = values.getOrDefault(l+".start.amount_of_cards", "START_AMOUNT_OF_CARDS");
	     START_PRACTISE_RUN = values.getOrDefault(l+".start.practise_run", "START_PRACTISE_RUN");
	     START_DIRECTION_DIRECTION = values.getOrDefault(l+".start.direction.direction", "START_DIRECTION_DIRECTION");
	     START_DIRECTION_OPTION_1 = values.getOrDefault(l+".start.direction.option_1", "START_DIRECTION_OPTION_1");
	     START_DIRECTION_OPTION_2 = values.getOrDefault(l+".start.direction.option_2", "START_DIRECTION_OPTION_2");
	     START_DIRECTION_OPTION_3 = values.getOrDefault(l+".start.direction.option_3", "START_DIRECTION_OPTION_3");
	     START_START = values.getOrDefault(l+".start.start", "START_START");
	    
	     CREDITS_NAME = values.getOrDefault(l+".credits.name", "CREDITS_NAME");
	     CREDITS_CREATORS = values.getOrDefault(l+".credits.creators", "CREDITS_CREATORS");
	     CREDITS_HELPERS = values.getOrDefault(l+".credits.helpers", "CREDITS_HELPERS");

	     SETTINGS_NAME = values.getOrDefault(l+".settings.name", "SETTINGS_NAME");
	     SETTINGS_LANGUAGE = values.getOrDefault(l+".settings.language", "SETTINGS_LANGUAGE");
	     SETTINGS_THEME = values.getOrDefault(l+".settings.theme", "SETTINGS_THEME");
	     SETTINGS_SIMPLIFIED_VIEW = values.getOrDefault(l+".settings.simplified_view", "SETTINGS_SIMPLIFIED_VIEW");
	     SETTINGS_ALLOW_PREMIUM = values.getOrDefault(l+".settings.allow_premium", "SETTINGS_ALLOW_PREMIUM");
	     SETTINGS_SUGGEST_FEATURE = values.getOrDefault(l+".settings.suggest_feature", "SETTINGS_SUGGEST_FEATURE");
	     
	      RUN_NAME = values.getOrDefault(l+".run.name", "RUN_NAME");
	      RUN_TIME_TOTAL_TIME = values.getOrDefault(l+".run.time.total_time", "RUN_TIME_TOTAL_TIME");
	      RUN_TIME_TIME_PER_CARD = values.getOrDefault(l+".run.time.time_per_card", "RUN_TIME_TIME_PER_CARD");
	      RUN_STOP = values.getOrDefault(l+".run.stop", "RUN_STOP");
	      RUN_SKIP = values.getOrDefault(l+".run.skip", "RUN_SKIP");
	      RUN_CHECK = values.getOrDefault(l+".run.check", "RUN_CHECK");
	      RUN_NEXT = values.getOrDefault(l+".run.next", "RUN_NEXT");
	      RUN_END = values.getOrDefault(l+".run.end", "RUN_END");
	      RUN_ANSWER = values.getOrDefault(l+".run.answer", "RUN_ANSWER");
	      
	      RUN_STATS_TOTAL_TIME = values.getOrDefault(l+".run.stats.total_time", "RUN_STATS_TOTAL_TIME");
	      RUN_STATS_MAX_CARD_TIME = values.getOrDefault(l+".run.stats.max_card_time", "RUN_STATS_MAX_CARD_TIME");
	      RUN_STATS_MIN_CARD_TIME = values.getOrDefault(l+".run.stats.min_card_time", "RUN_STATS_MIN_CARD_TIME");
	      RUN_STATS_AVG_CARD_TIME = values.getOrDefault(l+".run.stats.avg_card_time", "RUN_STATS_AVG_CARD_TIME");
	      RUN_STATS_TOTAL_CARDS = values.getOrDefault(l+".run.stats.total_cards", "RUN_STATS_TOTAL_CARDS");
	      RUN_STATS_SKIPPED_CARDS = values.getOrDefault(l+".run.stats.skipped_cards", "RUN_STATS_SKIPPED_CARDS");
	      RUN_STATS_WRONG_ANSWERS = values.getOrDefault(l+".run.stats.wrong_ansers", "RUN_STATS_WRONG_ANSWERS");
	      RUN_STATS_CORRECT_ANSWERS = values.getOrDefault(l+".run.stats.correct_answers", "RUN_STATS_CORRECT_ANSWERS");
	      RUN_STATS_MAX_STREAK = values.getOrDefault(l+".run.stats.max_streak", "RUN_STATS_MAX_STREAK");
	      RUN_STATS_ACCURACY = values.getOrDefault(l+".run.stats.accuracy", "RUN_STATS_ACCURACY");
	      
    }
}
