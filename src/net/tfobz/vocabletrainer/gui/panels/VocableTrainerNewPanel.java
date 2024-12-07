package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

@SuppressWarnings("serial")
public class VocableTrainerNewPanel extends VocableTrainerPanel {
	
	private JLabel newSet;
	
	public VocableTrainerNewPanel (VocableTrainerFrame vtf) {
		super(vtf);
		
		barPane.setTitle("New");
		
		newSet = new JLabel("New Set");
		newSet.setForeground(C_nigth);
		
		this.add(barPane);
		this.add(panel);
	}
	
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		newSet.setBounds(panel.getHeight()/16 + 24, 16, panel.getHeight()/16, panel.getWidth() - panel.getHeight()/16);
    }
}
