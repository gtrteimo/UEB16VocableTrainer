package net.tfobz.vocabletrainer.data;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class VocableTrainerSettingsIO {

	public static VocableTrainerLocalization.localisation localisation;
	public static Color[] colours;
	public static boolean simplified;
	public static boolean premium;
	
    public static void loadSettings() {
        File settingsFile = new File("src/net/tfobz/vocabletrainer/main/resources/settings.txt");

        if (!settingsFile.exists()) {
            createDefaultSettings(settingsFile);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(settingsFile))) {
            String line;
            int lineCounter = 0;

            while ((line = reader.readLine()) != null) {
                lineCounter++;
                if (line.equals("Deutsch") || line.equals("English") || line.equals("Italiano")) {
                    localisation = VocableTrainerLocalization.localisation.valueOf(line);
                    System.out.println("Localisation: " + localisation);
                } else if (line.startsWith("[r=")) {
                    String[] colorStrings = line.split("; ");
                    if (colorStrings.length == 5) {
                        colours = new Color[5];

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
                        System.out.println("Colors: " + java.util.Arrays.toString(colours));
                    } else {
                        System.err.println("Error: Expected 5 colors, found " + colorStrings.length);
                    }
                } else if (lineCounter == 3) {
                    simplified = Boolean.parseBoolean(line);
                    System.out.println("Simplified: " + simplified);
                } else if (lineCounter == 4) {
                    premium = Boolean.parseBoolean(line);
                    System.out.println("Premium: " + premium);
                } else {
                    System.err.println("Unrecognized line format: " + line);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private static void createDefaultSettings(File settingsFile) {
        try (FileWriter writer = new FileWriter(settingsFile)) {
            writer.write("English\n");
            writer.write("[r=50,g=50,b=75]; [r=171,g=181,b=216]; [r=255,g=255,b=255]; [r=11,g=9,b=10]; [r=111,g=116,b=146]\n");
            writer.write("true\n");
            writer.write("false\n");
            System.out.println("Default settings file created.");
        } catch (IOException e) {
            System.err.println("Error creating default settings file: " + e.getMessage());
        }
    }
    
    public static void saveSettings() {
    	File settingsFile = new File("src/net/tfobz/vocabletrainer/main/settings.txt");
        try (FileWriter writer = new FileWriter(settingsFile)) {
            writer.write(localisation.name() + "\n");

            StringBuilder colorsBuilder = new StringBuilder();
            for (int i = 0; i < colours.length; i++) {
                Color color = colours[i];
                colorsBuilder.append(String.format("[r=%d,g=%d,b=%d]", color.getRed(), color.getGreen(), color.getBlue()));
                if (i < colours.length - 1) {
                    colorsBuilder.append("; ");
                }
            }
            writer.write(colorsBuilder.toString() + "\n");

            writer.write(simplified + "\n");
            writer.write(premium + "\n");

            System.out.println("Settings saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving settings file: " + e.getMessage());
        }
    }
    
    public static void saveSettings(VocableTrainerLocalization.localisation localisation) {
    	VocableTrainerSettingsIO.localisation = localisation;
    	
    	saveSettings();
    }
    public static void saveSettings(Color[] colours) {
    	VocableTrainerSettingsIO.colours = colours;
    	
    	saveSettings();
    }
    public static void saveSettings(boolean simplified, boolean premium) {
    	VocableTrainerSettingsIO.simplified = simplified;
    	VocableTrainerSettingsIO.premium = premium;
    	
    	saveSettings();
    }
}
