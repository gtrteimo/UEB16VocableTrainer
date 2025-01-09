package net.tfobz.vocabletrainer.gui.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

/**
 * The VocableTrainerHomePanel class represents the home screen panel of the Vocable Trainer application.
 * It provides navigation buttons for starting the training, creating new entries, viewing information,
 * and exiting the application.
 */
@SuppressWarnings("serial")
public class VocableTrainerHomePanel extends VocableTrainerPanel {
    
    private JButton B_start;
    private JButton B_new;
    private JButton B_info;
    private JButton B_exit;
    
    /**
     * Constructs the VocableTrainerHomePanel with the specified VocableTrainerFrame.
     *
     * @param vtf the main frame of the Vocable Trainer application
     */
    public VocableTrainerHomePanel(VocableTrainerFrame vtf) {
        super(vtf);
        
        // Load background image for the panel
        panel.loadImage();
                
        // Set layout manager to null for absolute positioning
        panel.setLayout(null);
        
        // Initialize buttons
        B_start = new JButton();
        B_new = new JButton();
        B_info = new JButton();
        B_exit = new JButton();
        
        // Set button background colors
        B_start.setBackground(buttonBackgroundColor);
        B_new.setBackground(buttonBackgroundColor);
        B_info.setBackground(buttonBackgroundColor);
        B_exit.setBackground(buttonBackgroundColor);
        
        // Set button foreground (text) colors
        B_start.setForeground(textColor1);
        B_new.setForeground(textColor1);
        B_info.setForeground(textColor1);
        B_exit.setForeground(textColor1);

        // Remove focus painting from buttons
        B_start.setFocusPainted(false);
        B_new.setFocusPainted(false);
        B_info.setFocusPainted(false);
        B_exit.setFocusPainted(false);

        // Remove border painting from buttons
        B_start.setBorderPainted(false);
        B_new.setBorderPainted(false);
        B_info.setBorderPainted(false);
        B_exit.setBorderPainted(false);
        
        // Set keyboard mnemonics for accessibility
        B_start.setMnemonic('S');
        B_new.setMnemonic('N');
        B_info.setMnemonic('I');
        B_exit.setMnemonic('E');
        
        // Add action listeners to buttons
        B_start.addActionListener(new ButtonListener(5));
        B_new.addActionListener(new ButtonListener(3));
        B_info.addActionListener(new ButtonListener(4));
        B_exit.addActionListener(new ButtonListener(-1));
        
        // Apply localized text to buttons and title bar
        setLocalisation();
        
        // Add buttons to the panel
        panel.add(B_start);
        panel.add(B_new);
        panel.add(B_info);
        panel.add(B_exit);
        
        // Add the navigation bar and main panel to the HomePanel
        this.add(barPane);
        this.add(panel);
    }
    
    /**
     * Sets the color scheme for the HomePanel components.
     * Overrides the setColors method from the superclass to apply specific colors to buttons.
     */
    @Override
    public void setColors() {
        super.setColors();
        B_start.setBackground(buttonBackgroundColor);
        B_new.setBackground(buttonBackgroundColor);
        B_info.setBackground(buttonBackgroundColor);
        B_exit.setBackground(buttonBackgroundColor);
        
        B_start.setForeground(textColor1);
        B_new.setForeground(textColor1);
        B_info.setForeground(textColor1);
        B_exit.setForeground(textColor1);
    }
    
    /**
     * Applies localized text to the buttons and title bar.
     * Overrides the setLocalisation method from the superclass to set specific text values.
     */
    @Override
    public void setLocalisation() {        
        barPane.setTitle(VocableTrainerLocalization.HOME_NAME);
        
        B_start.setText(VocableTrainerLocalization.HOME_START);
        B_new.setText(VocableTrainerLocalization.HOME_NEW);
        B_info.setText(VocableTrainerLocalization.HOME_INFO);
        B_exit.setText(VocableTrainerLocalization.HOME_EXIT);
    }
    
    /**
     * Paints the component, setting the layout and font of buttons based on the panel size.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {        
        super.paintComponent(g);
        
        // Calculate center positions
        int middleX = panel.getWidth() / 2;
        int middleY = panel.getHeight() / 2; 
                
        // Define button size based on panel dimensions
        int width = middleX / 2;
        int height = middleY / 2;
        
        // Position buttons on the panel
        B_start.setBounds(middleX - width - 8, middleY - height - 8, width, height);
        B_new.setBounds(middleX + 8, middleY - height - 8, width, height);
        B_info.setBounds(middleX - width - 8, middleY + 8, width, height);
        B_exit.setBounds(middleX + 8, middleY + 8, width, height);
        
        // Set font size relative to button height
        Font buttonFont = new Font("Arial", Font.PLAIN, height / 2);
        B_start.setFont(buttonFont);
        B_new.setFont(buttonFont);
        B_info.setFont(buttonFont);
        B_exit.setFont(buttonFont);
    }
    
    /**
     * The ButtonListener class handles action events for the buttons on the HomePanel.
     * It changes the current panel based on the associated panel index or exits the application.
     */
    private class ButtonListener implements ActionListener {

        private int panelIndex;
        
        /**
         * Constructs a ButtonListener with the specified panel index.
         *
         * @param panelIndex the index of the panel to switch to, or -1 to exit
         */
        public ButtonListener(int panelIndex) {
            this.panelIndex = panelIndex;
        }
        
        /**
         * Invoked when an action occurs. Changes the panel or exits the application based on panelIndex.
         *
         * @param e the event triggered by the button press
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (panelIndex == -1) {
                vtf.close();
            } else {
                try {
                    vtf.changePanel(panelIndex);
                } catch (Exception ex) {
                    // Log the exception or handle it appropriately
                }
            }
        }
    }
}
