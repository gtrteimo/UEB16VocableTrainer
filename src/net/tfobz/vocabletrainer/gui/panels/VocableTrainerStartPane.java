package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.gui.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class VocableTrainerStartPane extends VocableTrainerPanel {
	
	private VocableTrainerFrame vtf;
	
	private VocableTrainerBarPane barPane;
	private VocableTrainerPanel panel;
	
	public VocableTrainerStartPane (VocableTrainerFrame vtf) {
		this.vtf=vtf;
		 
		setBackground(new Color(225, 225, 225, 255));
		
		barPane = new VocableTrainerBarPane(this);
		panel = new VocableTrainerPanel(this);
	}
}
