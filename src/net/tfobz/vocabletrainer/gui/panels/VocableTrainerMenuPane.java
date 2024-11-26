package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.gui.*;

import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class VocableTrainerMenuPane extends JPanel {
	
	private VocableTrainerFrame vtf;
	
	public VocableTrainerMenuPane (VocableTrainerFrame vtf) {
		this.vtf=vtf;
		setBackground(new Color(225, 225, 225, 255));
	}
}
