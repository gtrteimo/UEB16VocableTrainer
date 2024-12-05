package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")

public class VocableTrainerPanel extends JPanel {
	
	public VocableTrainerPanel () {
		this(null);
	}
	public VocableTrainerPanel (VocableTrainerPanel sourcePanel) {
		this(sourcePanel, new Color(171, 181, 216));
	}
	public VocableTrainerPanel (VocableTrainerPanel sourcePanel, Color colour) {
		super();
		super.setBackground(colour);
	}
	public VocableTrainerPanel (VocableTrainerPanel sourcePanel, int width, int height) {
		this(sourcePanel, 0, 0, width, height, new Color(171, 181, 216));
	}
	public VocableTrainerPanel (VocableTrainerPanel sourcePanel, int width, int height, Color colour) {
		this(sourcePanel, 0, 0, width, height, colour);
	}
	public VocableTrainerPanel (VocableTrainerPanel sourcePanel, int x, int y, int width, int height) {
		this(sourcePanel, x, y, width, height, new Color(171, 181, 216));
	}
	public VocableTrainerPanel (VocableTrainerPanel sourcePanel, int x, int y, int width, int height, Color colour) {
		super();
		super.setBackground(colour);
		super.setBounds(x, y, width, height);
	}
}
