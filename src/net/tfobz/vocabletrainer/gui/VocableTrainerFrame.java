package net.tfobz.vocabletrainer.gui;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import net.tfobz.vocabletrainer.gui.panels.*;

@SuppressWarnings("serial")
public class VocableTrainerFrame extends JFrame {
	
	private int width;
	private int height;
	
	private Container contentPane;
	
	public VocableTrainerPanel[] panels = new VocableTrainerPanel[8];
	
	public VocableTrainerFrame () {
		this(1080, 720);
	}
	public VocableTrainerFrame (int width, int height) {
		this.width = width;
		this.height = height;
		setMinimumSize(new Dimension(480, 360));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(25, 25, width, height);
				
		contentPane = getContentPane();
		contentPane.setLayout(new CardLayout());
		
		generatePanels();
		
		contentPane.add(panels[5]);	
	}
	
	private void generatePanels () {
//		panels[0] = new VocableTrainerHomePanel(this);
//		panels[1] = new VocableTrainerMenuPanel(this);
//		panels[2] = new VocableTrainerEditPanel(this);
//		panels[3] = new VocableTrainerNewPanel(this);
//		panels[4] = new VocableTrainerNewSetPanel(this);
		panels[5] = new VocableTrainerInfoPanel(this);
//		panels[6] = new VocableTrainerStartPanel(this);
//		panels[7] = new VocableTrainerPanel(this);
//		panels[8] = new VocableTrainerCreditsPanel(this);
//		panels[8] = new VocableTrainerSettingsPanel(this);




	}
}
