package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;

import javax.swing.JPanel;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

public class VocableTrainerBarPane extends JPanel {
	
	VocableTrainerFrame vtf;
	
	public VocableTrainerBarPane (VocableTrainerFrame vtf) {
		this.vtf = vtf;
		setBackground(new Color(255, 0, 0, 255));
	}
}
