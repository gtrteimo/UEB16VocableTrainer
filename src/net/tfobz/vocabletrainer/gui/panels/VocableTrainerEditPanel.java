package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Graphics;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

@SuppressWarnings("serial")
public class VocableTrainerEditPanel extends VocableTrainerPanel {
		
	public VocableTrainerEditPanel (VocableTrainerFrame vtf) {
		super(vtf);
		
		barPane.setTitle("Edit");
		
		this.add(barPane);
		this.add(panel);
	}
	
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
