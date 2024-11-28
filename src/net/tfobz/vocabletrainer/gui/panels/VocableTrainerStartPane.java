package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.gui.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class VocableTrainerStartPane extends JPanel {
	
	private VocableTrainerFrame vtf;
	
	public VocableTrainerStartPane (VocableTrainerFrame vtf) {
		this.vtf=vtf;
		
		setLayout(new BoxLayout());
		setBackground(new Color(225, 225, 225, 255));
		
		
		GridBagConstraints gbc = new GridBagConstraints();
        
		VocableTrainerBarPane barPane = new VocableTrainerBarPane(vtf);
//        barPane.setPreferredSize(new Dimension(0, 25)); // Fixed height of 75 pixels
		
        gbc.gridx = 0; // Column position
        gbc.gridy = 0; // Row position
        gbc.weightx = 1.0; // Take all horizontal space
        gbc.weighty = 0.12; // Don't grow vertically
        gbc.fill = GridBagConstraints.BOTH; // Stretch horizontally
        
        
        GridBagConstraints gbcRest = new GridBagConstraints();
        gbcRest.gridx = 0; // First column
        gbcRest.gridy = 1; // Second row
        gbcRest.weightx = 1.0; // Full width
        gbcRest.weighty = 0.88; // 90% of total height (adjustable proportion)
        gbcRest.fill = GridBagConstraints.BOTH; // Stretch both horizontally and vertically

        // Add an empty JPanel to take up the remaining space (or replace with another component)
        add(new JPanel(), gbcRest);
        
        add(barPane, gbc);
	}
}
