package net.tfobz.vocabletrainer.gui;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;

import net.tfobz.vocabletrainer.gui.panels.*;

@SuppressWarnings("serial")
public class VocableTrainerFrame extends JFrame {
	
	private int width;
	private int height;
	
	private Container contentPane;
	
	VocableTrainerStartPane start;
	VocableTrainerMenuPane menu;
	VocableTrainerPanel credits;
	VocableTrainerPanel settings;

	
	public VocableTrainerFrame () {
		this(1200, 900);
	}
	public VocableTrainerFrame (int width, int height) {
		super("Vokabeltrainer");
		this.setMinimumSize(new Dimension(600, 400));
		this.width = width;
		this.height = height;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		System.out.println(" N: "+getInsets().top+" O: "+getInsets().right+" S: "+getInsets().bottom+" W: "+getInsets().left);
		setBounds(25, 25, width, height);
				
		contentPane = getContentPane();
		contentPane.setLayout(new CardLayout());
		
		generatePanels();
		
		contentPane.add(settings);	
	}
	
	private void generatePanels () {
		start = new VocableTrainerStartPane(this);
		menu = new VocableTrainerMenuPane(this);
		credits = new VocableTrainerCreditsPane(this);
		settings = new VocableTrainerSettingsPane(this);
	}
}
