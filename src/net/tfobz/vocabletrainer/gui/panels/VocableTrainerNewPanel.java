package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

@SuppressWarnings("serial")
public class VocableTrainerNewPanel extends VocableTrainerPanel {
	
	private JButton newSet;
	
	public VocableTrainerNewPanel (VocableTrainerFrame vtf) {
		super(vtf);
		
		panel.setLayout(null);
		
		barPane.setTitle("New");
		
		newSet = new JButton("New Set");
		newSet.setForeground(C_nigth);
		newSet.setBackground(C_powderBlue);
		newSet.setBorderPainted(false);
		newSet.setFocusPainted(false);
		newSet.setHorizontalAlignment(SwingConstants.LEFT);
		
		panel.add(newSet);
		
		this.add(barPane);
		this.add(panel);
	}
	
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = panel.getWidth();
        int h = panel.getHeight();
        
		newSet.setBounds(w / 8, 16, w / 4 , h / 16);
		newSet.setFont(new Font ("Arial", Font.BOLD, newSet.getHeight()/2+5));
    }
}
