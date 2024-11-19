package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;

import javax.swing.JPanel;

import net.tfobz.vocabletrainer.gui.VocableTrainerPanelInterface;

@SuppressWarnings("serial")
public class BarPanel extends JPanel implements VocableTrainerPanelInterface{
	
	
	String text;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public BarPanel(int width, int heigth) {
		this(width, heigth, "Vocable Trainer");
	}
	public BarPanel(int width, int heigth, String text) {
		setBounds(0, 0, width, heigth/900*75);
		setBackground(new Color(50, 50, 75, 255));
	}
	
	public void update (int width, int height) {
		
	}

	public void updateSize(int width, int heigth) {
		setSize(width, heigth);
	}
	
}
