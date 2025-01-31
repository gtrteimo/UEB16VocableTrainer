package net.tfobz.vocabletrainer.data;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

/**
 * Class responsible for handling the settings of the Vocable Trainer application,
 * including loading, saving, and managing default settings.
 */
public class VocableTrainerSettingsIO {

    public static VocableTrainerLocalization.localisation localisation;
    public static Color[] colours;
    public static boolean simplified;
    public static boolean premium;

    /**
     * Loads settings from a file. If the file does not exist, default settings are created.
     */
    public static void loadSettings() {
        File settingsFile = new File("settings.txt");

        // Check if settings file exists, create default settings if not
        if (!settingsFile.exists()) {
            createDefaultSettings(settingsFile);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(settingsFile))) {
            String line;
            int lineCounter = 0;

            // Read the settings file line by line
            while ((line = reader.readLine()) != null) {
                lineCounter++;
                // Handle localisation settings (line containing language)
                if (line.equals("Deutsch") || line.equals("English") || line.equals("Italiano")) {
                    localisation = VocableTrainerLocalization.localisation.valueOf(line);
                // Handle colour settings (line starting with [r=)
                } else if (line.startsWith("[r=")) {
                    String[] colorStrings = line.split("; ");
                    if (colorStrings.length == 5) {
                        colours = new Color[5];

                        // Parse each color from the string
                        for (int i = 0; i < 5; i++) {
                            String[] rgb = colorStrings[i]
                                .replace("[r=", "")
                                .replace("]", "")
                                .split(",g=|,b=");
                            int r = Integer.parseInt(rgb[0]);
                            int g = Integer.parseInt(rgb[1]);
                            int b = Integer.parseInt(rgb[2]);
                            colours[i] = new Color(r, g, b);
                        }
                    } else {
                        System.err.println("Error: Expected 5 colors, found " + colorStrings.length);
                    }
                // Handle simplified setting (third line)
                } else if (lineCounter == 3) {
                    simplified = Boolean.parseBoolean(line);
                // Handle premium setting (fourth line)
                } else if (lineCounter == 4) {
                    premium = Boolean.parseBoolean(line);
                } else {
                    System.err.println("Unrecognized line format: " + line);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a default settings file with predefined values.
     * 
     * @param settingsFile the file to write the default settings to
     */
    private static void createDefaultSettings(File settingsFile) {
        try (FileWriter writer = new FileWriter(settingsFile)) {
            // Write default localisation setting
            writer.write("English\n");
            // Write default colors
            writer.write("[r=50,g=50,b=75]; [r=171,g=181,b=216]; [r=255,g=255,b=255]; [r=11,g=9,b=10]; [r=111,g=116,b=146]\n");
            // Write default simplified and premium settings
            writer.write("true\n");
            writer.write("false\n");
        } catch (IOException e) {
            System.err.println("Error creating default settings file: " + e.getMessage());
        }
    }

    /**
     * Saves the current settings to a file.
     */
    public static void saveSettings() {
        File settingsFile = new File("settings.txt");
        try (FileWriter writer = new FileWriter(settingsFile)) {
            // Save localisation setting
            writer.write(localisation.name() + "\n");

            // Save colors in formatted strings
            StringBuilder colorsBuilder = new StringBuilder();
            for (int i = 0; i < colours.length; i++) {
                Color color = colours[i];
                colorsBuilder.append(String.format("[r=%d,g=%d,b=%d]", color.getRed(), color.getGreen(), color.getBlue()));
                if (i < colours.length - 1) {
                    colorsBuilder.append("; ");
                }
            }
            writer.write(colorsBuilder.toString() + "\n");

            // Save simplified and premium settings
            writer.write(simplified + "\n");
            writer.write(premium + "\n");

        } catch (IOException e) {
            System.err.println("Error saving settings file: " + e.getMessage());
        }
    }

    /**
     * Saves the localisation setting and updates the settings file.
     * 
     * @param localisation the localisation setting to be saved
     */
    public static void saveSettings(VocableTrainerLocalization.localisation localisation) {
        VocableTrainerSettingsIO.localisation = localisation;
        saveSettings();
    }

    /**
     * Saves the color settings and updates the settings file.
     * 
     * @param colours the array of colors to be saved
     */
    public static void saveSettings(Color[] colours) {
        VocableTrainerSettingsIO.colours = colours;
        saveSettings();
    }

    /**
     * Saves the simplified and premium settings and updates the settings file.
     * 
     * @param simplified the simplified mode setting
     * @param premium the premium mode setting
     */
    public static void saveSettings(boolean simplified, boolean premium) {
        VocableTrainerSettingsIO.simplified = simplified;
        VocableTrainerSettingsIO.premium = premium;
        saveSettings();
    }
}
