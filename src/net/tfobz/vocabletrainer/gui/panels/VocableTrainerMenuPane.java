package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.gui.*;

import java.awt.Color;

import javax.swing.*;

@SuppressWarnings("serial")
public class VocableTrainerMenuPane extends VocableTrainerPanel {
	
	private VocableTrainerFrame vtf;
	private JButton startButton;
	private JButton newButton;
	private JButton statsButton;
	private JButton editButton;
	
	public VocableTrainerMenuPane (VocableTrainerFrame vtf) {
		this.vtf=vtf;
		setBackground(new Color(225, 225, 225, 255));
		
		this.startButton=new JButton("Start");
		this.newButton=new JButton("New");
		this.statsButton=new JButton("Stats");
		this.editButton=new JButton("Edit");
		
		this.add(startButton);
		this.add(newButton);
		this.add(statsButton);
		this.add(editButton);
	}
}
