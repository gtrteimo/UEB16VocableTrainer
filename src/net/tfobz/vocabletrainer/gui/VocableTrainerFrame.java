package net.tfobz.vocabletrainer.gui;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import net.tfobz.vocabletrainer.gui.panels.*;

@SuppressWarnings("serial")
public class VocableTrainerFrame extends JFrame {
	
	private Container contentPane;
	
	private ArrayList<Integer> history = new ArrayList<Integer>();
	private VocableTrainerPanel[] panels = new VocableTrainerPanel[8];
//	private VocableTrainerInterface db;
	
	public VocableTrainerFrame () {
		this(1080, 720);
	}
	public VocableTrainerFrame (int width, int height) {
		super();
		setSize(width, height);
		setMinimumSize(new Dimension(480, 360));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(25, 25, width, height);
		
//		db = new VocableTrainerInterface();
		
		contentPane = getContentPane();
		contentPane.setLayout(new CardLayout());
		
		generatePanels();
		
		contentPane.add(panels[1]);
		history.add(1);
	}
	
	private void generatePanels () {
		panels[0] = new VocableTrainerMenuPanel(this);
		panels[1] = new VocableTrainerHomePanel(this);
		panels[2] = new VocableTrainerEditPanel(this);
		panels[3] = new VocableTrainerNewPanel(this);
		panels[4] = new VocableTrainerInfoPanel(this);
		panels[5] = new VocableTrainerStartPanel(this);
		panels[6] = new VocableTrainerCreditsPanel(this);
		panels[7] = new VocableTrainerSettingsPanel(this);
	}
	public void changePanel (int panelIndex) throws Exception {
		if (panelIndex > 0) {
			if (history.size() > 0) {
				if (history.get(history.size()-1) != panelIndex) {
					contentPane.add(panels[panelIndex]);
					contentPane.remove(panels[history.get(history.size()-1)]);
					history.add(panelIndex);
				}
			}
		} else if (panelIndex == 0) {
			contentPane.remove(panels[history.get(history.size()-1)]);
			contentPane.add(panels[panelIndex]);
			contentPane.add(panels[history.get(history.size()-1)]);
		} else if (panelIndex == -1) {
			if (history.size() > 1) {
				contentPane.add(panels[history.get(history.size()-2)]);
				contentPane.remove(panels[history.get(history.size()-1)]);
				history.remove(history.size()-1);
			}
		} else if (panelIndex == -2) {
			contentPane.remove(panels[0]);
		}
	}
	
	public void close () {
		setVisible(false);
		dispose();
		System.exit(0);
	}
}
