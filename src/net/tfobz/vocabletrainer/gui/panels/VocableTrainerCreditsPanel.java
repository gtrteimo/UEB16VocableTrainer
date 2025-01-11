package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

/**
 * VocableTrainerCreditsPanel is a GUI panel that displays the credits for the Vocable Trainer application.
 * It lists the creators and helpers involved in the development of the application.
 * 
 * <p>This panel is part of the GUI and extends the VocableTrainerPanel to inherit common functionalities.
 * It utilizes JLabels to display the names of creators and helpers, and manages their layout and styling.
 * </p>
 * 
 * @see VocableTrainerPanel
 */
@SuppressWarnings("serial")
public class VocableTrainerCreditsPanel extends VocableTrainerPanel {
		
    // Label for the creators section
    private JLabel labelCreators;
    
    // List of labels displaying the creators' names
    private ArrayList<JLabel> creators;
    
    // Label for the helpers section
    private JLabel labelHelpers;
    
    // List of labels displaying the helpers' names
    private ArrayList<JLabel> helpers;
	
    /**
     * Constructs a VocableTrainerCreditsPanel with the specified frame.
     * Initializes the layout, creates labels for creators and helpers, and adds them to the panel.
     * 
     * @param vtf the VocableTrainerFrame that this panel is part of
     */
    public VocableTrainerCreditsPanel(VocableTrainerFrame vtf) {
        super(vtf);
        panel.setLayout(null);
                
        // Initialize and configure the creators label
        labelCreators = new JLabel();
        labelCreators.setForeground(textColor2);
        creators = new ArrayList<JLabel>();
        creators.add(createLabel(new JLabel("Nathan C. (21chinat)")));
        creators.add(createLabel(new JLabel("Gerhard T. R. (gtrteimo)")));
        
        // Initialize and configure the helpers label
        labelHelpers = new JLabel();
        labelHelpers.setForeground(textColor2);
        helpers = new ArrayList<JLabel>();
        helpers.add(createLabel(new JLabel("Nick L.")));
        helpers.add(createLabel(new JLabel("Manuel P.")));
        helpers.add(createLabel(new JLabel("Johannes W.")));
        helpers.add(createLabel(new JLabel("Niklas P.")));
        helpers.add(createLabel(new JLabel("Ivan S.")));
        
        // Set localized texts for labels
        setLocalisation();
        
        // Add creators and helpers labels to the panel
        panel.add(labelCreators);
        panel.add(labelHelpers);
        
        // Add each creator label to the panel
        for (JLabel creator : creators) {
            panel.add(creator);
        }
        
        // Add each helper label to the panel
        for (JLabel helper : helpers) {
            panel.add(helper);
        }
        
        // Add the navigation bar and the main panel to this container
        this.add(barPane);
        this.add(panel);
    }
	
    /**
     * Creates and configures a JLabel with the specified properties.
     * Sets the foreground and background colors.
     * 
     * @param label the JLabel to configure
     * @return the configured JLabel
     */
    private JLabel createLabel(JLabel label) {
        label.setForeground(textColor2);
        label.setBackground(mainBackgroundColor);
        return label;
    }
	
    /**
     * Sets the colors for the labels in the panel.
     * Overrides the setColors method from the superclass to apply specific color settings.
     */
    @Override
    public void setColors() {
        super.setColors();
        labelCreators.setForeground(textColor2);
        labelHelpers.setForeground(textColor2);
        
        // Apply color settings to all creator labels
        for (JLabel creator : creators) {
            creator.setForeground(textColor2);
            creator.setBackground(mainBackgroundColor);
        }
        
        // Apply color settings to all helper labels
        for (JLabel helper : helpers) {
            helper.setForeground(textColor2);
            helper.setBackground(mainBackgroundColor);
        }
    }
	
    /**
     * Sets the localized text for the labels in the credits panel.
     * Overrides the setLocalisation method from the superclass to apply specific localization.
     */
    @Override
    public void setLocalisation() {		
        barPane.setTitle(VocableTrainerLocalization.CREDITS_NAME);
        
        labelCreators.setText(VocableTrainerLocalization.CREDITS_CREATORS);
        labelHelpers.setText(VocableTrainerLocalization.CREDITS_HELPERS);
    }
	
    /**
     * Paints the component by setting the bounds and fonts of the labels.
     * Overrides the paintComponent method to customize the layout dynamically based on panel size.
     * 
     * @param g the Graphics context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Position and style the creators label
        labelCreators.setBounds(panel.getWidth() / 12, panel.getHeight() / 8, panel.getWidth() / 2, panel.getHeight() / 12);
        labelCreators.setFont(new Font("Arial", Font.BOLD, labelCreators.getHeight() / 2 + 5));
        
        // Position and style each creator label
        for (int i = 0; i < creators.size(); i++) {
            JLabel temp = creators.get(i);
            temp.setBounds(panel.getWidth() / 6, panel.getHeight() / (16 / (4 + i)), panel.getWidth() / 2, panel.getHeight() / 18);
            temp.setFont(new Font("Arial", Font.PLAIN, temp.getHeight() / 2 + 5));
        }
        
        // Position and style the helpers label
        labelHelpers.setBounds(panel.getWidth() / 12, panel.getHeight() / (16 / 7), panel.getWidth() / 2, panel.getHeight() / 12);
        labelHelpers.setFont(new Font("Arial", Font.BOLD, labelHelpers.getHeight() / 2 + 5));
        
        // Position and style each helper label
        for (int i = 0; i < helpers.size(); i++) {
            JLabel temp = helpers.get(i);
            temp.setBounds(panel.getWidth() / 6, (int) (panel.getHeight() / (16.0 / (10 + i))), panel.getWidth() / 2, panel.getHeight() / 18);
            temp.setFont(new Font("Arial", Font.PLAIN, temp.getHeight() / 2 + 5));
        }
    }
}
