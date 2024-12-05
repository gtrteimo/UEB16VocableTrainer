package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;

import javax.swing.JPanel;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

@SuppressWarnings("serial")
public class VocableTrainerBarPane extends VocableTrainerPanel {
	
	VocableTrainerPanel sourcePanel;
	
	public VocableTrainerBarPane (VocableTrainerPanel sourcePanel) {
		this.sourcePanel = sourcePanel;
		setBackground(new Color(50, 50, 75, 255));
	}
}
