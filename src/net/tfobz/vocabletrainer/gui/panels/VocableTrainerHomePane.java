package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.gui.*;

import java.awt.Color;

import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class VocableTrainerHomePane extends VocableTrainerPanel {
	
	private VocableTrainerFrame vtf;
	
	private VocableTrainerBarPane barPane;
	private VocableTrainerPanel panel;
	
	public VocableTrainerHomePane (VocableTrainerFrame vtf) {
		this.vtf=vtf;
		 
		setBackground(new Color(225, 225, 225, 255));
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		barPane = new VocableTrainerBarPane(this);
		panel = new VocableTrainerPanel(this);
		
		this.add(barPane);
		this.add(panel);
	}
}
