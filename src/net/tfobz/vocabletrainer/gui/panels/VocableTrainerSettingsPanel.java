package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.data.VocableTrainerLocalization.localisation;
import net.tfobz.vocabletrainer.data.VocableTrainerSettingsIO;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerColorChooser;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInfoDialog;

/**
 * VocableTrainerSettingsPanel is a GUI panel that allows users to configure
 * settings for the Vocable Trainer application. It provides options to select
 * language, theme, and toggle premium features.
 * <p>
 * This panel includes components such as JComboBox for language and theme selection,
 * JCheckBox for simplified view and premium feature toggling, and a JButton
 * to suggest features. It also handles the persistence of settings and updates
 * the UI based on user interactions.
 * </p>
 * 
 * @see VocableTrainerPanel
 */
@SuppressWarnings("serial")
public class VocableTrainerSettingsPanel extends VocableTrainerPanel {
    
    /**
     * Predefined color themes for the settings panel. Each sub-array represents
     * a different theme with specific color configurations.
     */
    public final static Color colours[][] = {
        {
            new Color(50, 50, 75),
            new Color(171, 181, 216),
            new Color(225, 225, 225),
            new Color(11, 9, 10),
            new Color(111, 116, 146)
        },
        {
            new Color(25, 25, 25),
            Color.WHITE,
            Color.WHITE,
            Color.BLACK,
            new Color(75, 75, 75)
        },
        {
            new Color(0, 136, 255),
            new Color(0, 12, 33),
            new Color(0, 0, 0),
            new Color(255, 255, 255),
            new Color(0, 136, 255)
        }
    };
    
    private JLabel language;
    private JComboBox<localisation> languageBox;
    private JLabel theme;
    private JComboBox<String> themeBox;
    private JCheckBox simplifiedBox;
    private JCheckBox allwoPremiumBox;
    private JButton button;
    private VocableTrainerColorChooser colorChooser;
    
    /**
     * Constructs a new VocableTrainerSettingsPanel with the specified parent frame.
     * Initializes all UI components, sets up layout, and adds action listeners
     * for user interactions.
     * 
     * @param vtf the parent VocableTrainerFrame
     */
    public VocableTrainerSettingsPanel (VocableTrainerFrame vtf) {
        super(vtf);
                
        panel.setLayout(null);
        
        // Initialize language selection combo box
        languageBox = new JComboBox<VocableTrainerLocalization.localisation>(VocableTrainerLocalization.localisation.values());
        
        // Initialize theme selection combo box with predefined themes
        themeBox = new JComboBox<String>();
        themeBox.addItem("Custom");
        themeBox.addItem("Acqua");
        themeBox.addItem("Monochrom light");
        themeBox.addItem("Dark Blue");
        
        // Initialize checkboxes for simplified view and premium features
        simplifiedBox = new JCheckBox();
        allwoPremiumBox = new JCheckBox();
        
        // Set initial selections based on saved settings
        languageBox.setSelectedItem(VocableTrainerSettingsIO.localisation);
        simplifiedBox.setSelected(VocableTrainerSettingsIO.simplified);
        allwoPremiumBox.setSelected(VocableTrainerSettingsIO.premium);
        
        // Determine and set the current theme based on saved color settings
        Color[] t = VocableTrainerSettingsIO.colours;
        if (t[0].equals(colours[0][0]) && t[1].equals(colours[0][1]) && t[2].equals(colours[0][2]) 
            && t[3].equals(colours[0][3]) && t[4].equals(colours[0][4])) {
            themeBox.setSelectedIndex(1);
        } else if (t[0].equals(colours[1][0]) && t[1].equals(colours[1][1]) && t[2].equals(colours[1][2]) 
                   && t[3].equals(colours[1][3]) && t[4].equals(colours[1][4])) {
            themeBox.setSelectedIndex(2);
        } else if (t[0].equals(colours[2][0]) && t[1].equals(colours[2][1]) && t[2].equals(colours[2][2]) 
                   && t[3].equals(colours[2][3]) && t[4].equals(colours[2][4])) {
            themeBox.setSelectedIndex(3);
        } else {
            themeBox.setSelectedIndex(0);
        }
        
        // Initialize labels for language and theme
        language = new JLabel();
        language.setForeground(textColor2);
        
        theme = new JLabel();
        theme.setForeground(textColor2);
        
        // Configure language combo box appearance
        languageBox.setForeground(textColor2);
        languageBox.setBackground(textColor1);
        
        // Configure theme combo box appearance
        themeBox.setForeground(textColor2);
        themeBox.setBackground(textColor1);
        
        // Configure simplified view checkbox appearance and state
        simplifiedBox.setBackground(mainBackgroundColor);
        simplifiedBox.setForeground(textColor2);
        simplifiedBox.setEnabled(false);
        simplifiedBox.setSelected(true);
       
        // Configure premium feature checkbox appearance and state
        allwoPremiumBox.setBackground(mainBackgroundColor);
        allwoPremiumBox.setForeground(textColor2);
        allwoPremiumBox.setEnabled(false);
        allwoPremiumBox.setSelected(false);
        
        // Initialize the suggest feature button
        button = new JButton();
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(buttonBackgroundColor);
        button.setForeground(textColor1);
        
        // Add action listener for language selection changes
        languageBox.addActionListener(e -> {
            super.changeLocalisation((localisation) languageBox.getSelectedItem());
            repaint();
            new Thread(() -> VocableTrainerSettingsIO.saveSettings((localisation) languageBox.getSelectedItem())).start();
        });
                
        // Add action listener for theme selection changes
        themeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(colorChooser != null) {
                    colorChooser.setVisible(false);
                    colorChooser.dispose();
                    colorChooser = null;
                }
                switch (themeBox.getSelectedIndex()) {
                    case 1:
                        applyTheme(colours[0]);
                        break;
                    case 2:
                        applyTheme(colours[1]);
                        break;
                    case 3:
                        applyTheme(colours[2]);
                        break;
                    default:
                        openCustomColorChooser();
                        break;
                }
            }
        });

        // Add action listener to the suggest feature button
        button.addActionListener(e -> openWebpage(vtf, "https://github.com/gtrteimo/UEB16VocableTrainer"));
                
        // Add action listeners for checkboxes to save settings upon state changes
        simplifiedBox.addActionListener(e -> {
            new Thread(() -> VocableTrainerSettingsIO.saveSettings(simplifiedBox.isEnabled(), allwoPremiumBox.isSelected())).start();
        });
        allwoPremiumBox.addActionListener(e -> {
            new Thread(() -> VocableTrainerSettingsIO.saveSettings(simplifiedBox.isEnabled(), allwoPremiumBox.isSelected())).start();
        });

        // Add all components to the panel
        panel.add(language);
        panel.add(theme);
        panel.add(languageBox);
        panel.add(themeBox);
        panel.add(simplifiedBox);
        panel.add(allwoPremiumBox);
        panel.add(button);
        
        // Add panel and bar pane to the main container
        add(barPane);
        add(panel);
    }
    
    /**
     * Applies the specified color theme to the settings panel and saves the
     * selected theme.
     * 
     * @param themeColors an array of Colors representing the selected theme
     */
    private void applyTheme(Color[] themeColors) {
        changeColor(themeColors[0], themeColors[1], themeColors[2], themeColors[3], themeColors[4]);
        new Thread(() -> VocableTrainerSettingsIO.saveSettings(themeColors)).start();
    }
    
    /**
     * Opens the custom color chooser dialog to allow users to select a custom
     * color theme. Saves the selected colors upon confirmation.
     */
    private void openCustomColorChooser() {
        colorChooser = new VocableTrainerColorChooser(VocableTrainerSettingsPanel.this);
        colorChooser.setVisible(true);
        new Thread(() -> VocableTrainerSettingsIO.saveSettings(colorChooser.getColors())).start();
    }
    
    /**
     * Opens the specified webpage in the default browser. If the desktop
     * environment does not support browsing or an error occurs, an informational
     * dialog is displayed to the user.
     * 
     * @param parent    the parent JFrame for dialog positioning
     * @param urlString the URL to be opened
     */
    public static void openWebpage(JFrame parent, String urlString) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                URI uri = new URI(urlString);
                desktop.browse(uri);
            } catch (Exception e) {
                new VocableTrainerInfoDialog(parent, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_SETTINGS_NO_INTERNET);
            }
        } else {
            new VocableTrainerInfoDialog(parent, VocableTrainerLocalization.ERROR, 
                VocableTrainerLocalization.ERROR_SETTINGS_NOT_SUPPORTED + "https://github.com/gtrteimo/UEB16VocableTrainer");
        }
    }   
    
    /**
     * Sets the colors of the UI components based on the current theme. This method
     * overrides the parent method to apply specific color settings to each component.
     */
    @Override
    public void setColors() {
        super.setColors();
        
        language.setForeground(textColor2);
        theme.setForeground(textColor2);
        
        languageBox.setForeground(textColor2);
        languageBox.setBackground(textColor1);
        
        themeBox.setForeground(textColor2);
        themeBox.setBackground(textColor1);
        
        simplifiedBox.setBackground(mainBackgroundColor);
        simplifiedBox.setForeground(textColor2);
        
        allwoPremiumBox.setBackground(mainBackgroundColor);
        allwoPremiumBox.setForeground(textColor2);
        
        button.setBackground(buttonBackgroundColor);
        button.setForeground(textColor1);
    }
    
    /**
     * Sets the localization for all UI components based on the current language
     * selection. This method overrides the parent method to update texts accordingly.
     */
    @Override
    public void setLocalisation() {
        barPane.setTitle(VocableTrainerLocalization.SETTINGS_NAME);
        language.setText("   " + VocableTrainerLocalization.SETTINGS_LANGUAGE);
        theme.setText("   " + VocableTrainerLocalization.SETTINGS_THEME);
        simplifiedBox.setText(VocableTrainerLocalization.SETTINGS_SIMPLIFIED_VIEW);
        allwoPremiumBox.setText(VocableTrainerLocalization.SETTINGS_ALLOW_PREMIUM);
        button.setText(VocableTrainerLocalization.SETTINGS_SUGGEST_FEATURE);
    }
    
    /**
     * Paints the component and arranges the layout of all UI elements. Sets the
     * bounds and fonts of labels, combo boxes, checkboxes, and buttons based on
     * the current size of the panel.
     * 
     * @param g the Graphics context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int h = panel.getHeight();
        int w = panel.getWidth();
                
        // Position and style the language label
        language.setBounds(w / 29, h / 17, w / 3, h / 8);
        language.setFont(new Font("Arial", Font.PLAIN, language.getHeight() / 2 + 5));
        
        // Position and style the language combo box
        languageBox.setBounds(w - w / 2 - 16, h / 17, w / 2, h / 8);
        languageBox.setFont(new Font("Arial", Font.PLAIN, languageBox.getHeight() / 2 + 5));
        
        // Position and style the theme label
        theme.setBounds(w / 29, h / 17 * 4, w / 4, h / 8);
        theme.setFont(new Font("Arial", Font.PLAIN, theme.getHeight() / 2 + 5));
        
        // Position and style the theme combo box
        themeBox.setBounds(w - w / 2 - 16, h / 17 * 4, w / 2, h / 8);
        themeBox.setFont(new Font("Arial", Font.PLAIN, themeBox.getHeight() / 2 + 5));
        
        // Position and style the simplified view checkbox
        simplifiedBox.setBounds(w / 29, h / 17 * 7, w / 2, h / 8);
        simplifiedBox.setFont(new Font("Arial", Font.PLAIN, simplifiedBox.getHeight() / 2 + 5));
        
        // Position and style the premium feature checkbox
        allwoPremiumBox.setBounds(w / 29, h / 17 * 10, w - 20, h / 8);
        allwoPremiumBox.setFont(new Font("Arial", Font.PLAIN, allwoPremiumBox.getHeight() / 2 + 5));
        
        // Position and style the suggest feature button
        button.setBounds(16, panel.getHeight() / 4 * 3, panel.getWidth() - 32, panel.getHeight() / 4 - 16);
        button.setFont(new Font("Arial", Font.BOLD, button.getHeight() / 2 - 10));
    }
}
