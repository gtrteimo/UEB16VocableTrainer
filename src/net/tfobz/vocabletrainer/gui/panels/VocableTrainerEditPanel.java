package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Graphics;
import java.util.ArrayList;

import net.tfobz.vocabletrainer.access.*;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

import net.tfobz.vokabeltrainer.model.Fach;

@SuppressWarnings("serial")
public class VocableTrainerEditPanel extends VocableTrainerPanel {
		
	private ArrayList<Fach> sets;
	
	public VocableTrainerEditPanel (VocableTrainerFrame vtf) {
		super(vtf);
		
		barPane.setTitle("Edit");
		
//		VocableTrainerInterface.
		
		this.add(barPane);
		this.add(panel);
	}
	
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
