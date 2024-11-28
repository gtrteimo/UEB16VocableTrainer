package net.tfobz.vocabletrainer.gui;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JFrame;

import net.tfobz.vocabletrainer.gui.panels.*;

@SuppressWarnings("serial")
public class VocableTrainerFrame extends JFrame {
	
	private int width;
	private int height;
	
	private Container contentPane;
	
	VocableTrainerStartPane start;
	VocableTrainerMenuPane menu;
	
	public VocableTrainerFrame () {
		this(1200, 900);
	}
	public VocableTrainerFrame (int width, int height) {
		this.width = width;
		this.height = height;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		System.out.println(" N: "+getInsets().top+" O: "+getInsets().right+" S: "+getInsets().bottom+" W: "+getInsets().left);
		setBounds(25, 25, width, height);
				
		contentPane = getContentPane();
		contentPane.setLayout(new CardLayout());
		
		generatePanels();
		
		contentPane.add(start);	
	}
	
	private void generatePanels () {
		start = new VocableTrainerStartPane(this);
		menu = new VocableTrainerMenuPane(this);

	}
}
