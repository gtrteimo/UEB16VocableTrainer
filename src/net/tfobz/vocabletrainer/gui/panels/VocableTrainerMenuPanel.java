package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.*;

/**
 * The {@code VocableTrainerMenuPanel} class represents the main menu panel
 * within the Vocable Trainer application. It extends {@link VocableTrainerPanel}
 * and provides navigation options for users to access different sections of the application.
 * 
 * <p>This panel includes a set of buttons that allow users to navigate to Home, Start,
 * Information, New Entries, Credits, Settings, and Help sections.</p>
 * 
 * @author
 * @version 1.0
 */
@SuppressWarnings("serial")
public class VocableTrainerMenuPanel extends VocableTrainerPanel {
    
    /**
     * Array of buttons representing different menu options.
     */
    private JButton options[] = new JButton[7];
    
    /**
     * Constructs a new {@code VocableTrainerMenuPanel} with the specified frame.
     * 
     * @param vtf the {@link VocableTrainerFrame} that contains this panel
     */
    public VocableTrainerMenuPanel (VocableTrainerFrame vtf) {
        super(vtf, true);
                
        // Set the layout to null for absolute positioning
        setLayout(null);
        
        // Configure the navigation bar pane
        barPane.setBounds(0, 0, getWidth(), getHeight() / 12);
        barPane.setTitle("");
        
        // Initialize the main panel
        panel = new VocableTrainerPanel(this, true);
        panel.setBounds(0, getHeight() / 12, getWidth() / 2 - 16, getHeight() / 12 * 11 - 16);
        panel.setBackground(buttonBackgroundColor);
        panel.setLayout(null);
        
        // Initialize and configure buttons
        for (int i = 0; i < options.length; i++) {
            options[i] = new JButton();
            options[i].setForeground(textColor1);
            options[i].setBackground(buttonBackgroundColor);
            options[i].setFocusPainted(false);
            options[i].setBorderPainted(false);
            panel.add(options[i]);
        }
        
        // Assign action listeners to buttons for navigation
        options[0].addActionListener(new PanelsChanger(1));   // Home
        options[1].addActionListener(new PanelsChanger(5));   // Start
        options[2].addActionListener(new PanelsChanger(4));   // Info
        options[3].addActionListener(new PanelsChanger(3));   // New Entries
        options[4].addActionListener(new PanelsChanger(6));   // Credits
        options[5].addActionListener(new PanelsChanger(7));   // Settings
        options[6].addActionListener(new PanelsChanger(-1));  // Help
        
        // Add components to the main panel
        add(barPane);
        add(panel);
    
    }
    
    /**
     * Sets the color scheme for the panel and its components.
     * Overrides the method from {@link VocableTrainerPanel}.
     */
    @Override
    public void setColors() {
        super.setColors();
        panel.setBackground(buttonBackgroundColor);
        
        // Update colors for all buttons
        for (int i = 0; i < options.length; i++) {
            options[i].setForeground(textColor1);
            options[i].setBackground(buttonBackgroundColor);
        }
    }
    
    /**
     * Sets the localization text for the menu options.
     * Overrides the method from {@link VocableTrainerPanel}.
     */
    @Override
    public void setLocalisation() {
        barPane.setTitle(null);
        
        // Assign localized text to each button
        options[0].setText(VocableTrainerLocalization.MENU_HOME);
        options[1].setText(VocableTrainerLocalization.MENU_START);
        options[2].setText(VocableTrainerLocalization.MENU_INFO);
        options[3].setText(VocableTrainerLocalization.MENU_NEW);
        options[4].setText(VocableTrainerLocalization.MENU_CREDITS);
        options[5].setText(VocableTrainerLocalization.MENU_SETTINGS);
        options[6].setText(VocableTrainerLocalization.MENU_HELP);
    }
    
    /**
     * Paints the component, including positioning and styling of buttons.
     * Overrides the method from {@link VocableTrainerPanel}.
     * 
     * @param g the {@link Graphics} context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int w = panel.getWidth();
        int h = panel.getHeight();
        
        // Position and style each button within the panel
        for (int i = 0; i < options.length; i++) {
            options[i].setBounds(w / 64, h / 7 * i + 10, w / 4, h / 16);
            options[i].setFont(new Font("Arial", Font.BOLD, options[i].getHeight() / 2 + 10));
            options[i].setHorizontalAlignment(SwingConstants.LEFT);
        }
    }
    
    /**
     * The {@code PanelsChanger} class handles action events for menu buttons,
     * facilitating navigation between different panels within the application.
     */
    private class PanelsChanger implements ActionListener {

        /**
         * Index of the panel to switch to.
         */
        private int panelIndex;
        
        /**
         * Constructs a new {@code PanelsChanger} with the specified panel index.
         * 
         * @param panelIndex the index of the panel to switch to
         */
        public PanelsChanger(int panelIndex) {
            this.panelIndex = panelIndex;
        }
        
        /**
         * Handles the action event by changing the current panel based on the panel index.
         * If the panel index is negative, it opens the help webpage.
         * 
         * @param e the {@link ActionEvent} that triggered the action
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (panelIndex < 0) {
                // Open the help webpage
                VocableTrainerSettingsPanel.openWebpage(vtf, 
                    "https://github.com/gtrteimo/UEB16VocableTrainer/blob/main/help.txt");
            } else {
                try {
                    // Change to the specified panel
                    vtf.changePanel(-2);
                    vtf.changePanel(panelIndex);
                } catch (Exception e1) {
                    // Handle any exceptions during panel switching
                    // Consider logging the exception using a logging framework
                }
            }
        }
    }
}
