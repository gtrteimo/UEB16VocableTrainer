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
	
	VocableTrainerPanel[] panels = new VocableTrainerPanel[8];
	
	public VocableTrainerFrame () {
		this(1200, 900);
	}
	public VocableTrainerFrame (int width, int height) {
		this.width = width;
		this.height = height;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(25, 25, width, height);
				
		contentPane = getContentPane();
		contentPane.setLayout(new CardLayout());
		
		generatePanels();
		
		contentPane.add(panels[0]);	
	}
	
	private void generatePanels () {
		panels[0] = new VocableTrainerHomePane(this);
		panels[1] = new VocableTrainerMenuPane(this);
//		panels[2] = new VocableTrainerEditPane(this);
//		panels[3] = new VocableTrainerNewPane(this);
//		panels[4] = new VocableTrainerNewSetPane(this);
		panels[5] = new VocableTrainerInfoPane(this);
//		panels[6] = new VocableTrainerStartPane(this);
//		panels[7] = new VocableTrainerPane(this);
//		panels[8] = new VocableTrainerCreditsPane(this);
//		panels[8] = new VocableTrainerSettingsPane(this);




	}
}
