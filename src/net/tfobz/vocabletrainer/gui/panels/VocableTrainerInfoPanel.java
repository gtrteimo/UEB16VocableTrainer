package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Graphics;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vokabeltrainer.model.Lernkartei;

@SuppressWarnings("serial")
public class VocableTrainerInfoPanel extends VocableTrainerPanel {
		
	private List<Lernkartei> sets;
	
	private JLabel topic;
	private JComboBox<String> comboBox;
	
	public VocableTrainerInfoPanel (VocableTrainerFrame vtf) {
		super(vtf);
		
		barPane.setTitle("Info");
		
		panel.setLayout(null);
		
		this.add(barPane);
		this.add(panel);
	}
	
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
