package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

@SuppressWarnings("serial")
public class VocableTrainerCreditsPanel extends VocableTrainerPanel {
		
	private JLabel labelhelpers;
	private ArrayList<JLabel> helpers;
	private JLabel labelcreators;
	private ArrayList<JLabel> creators;
	
	public VocableTrainerCreditsPanel (VocableTrainerFrame vtf) {
		super(vtf);
		panel.setLayout(null);
		
		barPane.setTitle(VocableTrainerLocalization.MENU_CREDITS);
		
		labelcreators = new JLabel(VocableTrainerLocalization.CREDITS_CREATORS);
		labelcreators.setForeground(textColor2);
		creators = new ArrayList<JLabel>();
		creators.add(createLabel(new JLabel("Nathan C. (21chinat)")));
		creators.add(createLabel(new JLabel("Gerhard T. R. (gtrteimo)")));
		
		labelhelpers = new JLabel(VocableTrainerLocalization.CREDITS_HELPERS);
		labelhelpers.setForeground(textColor2);
		helpers = new ArrayList<JLabel>();
		helpers.add(createLabel(new JLabel("Nick L.")));
		helpers.add(createLabel(new JLabel("Manuel P.")));
		helpers.add(createLabel(new JLabel("Johannes W.")));
		
		panel.add(labelcreators);
		panel.add(labelhelpers);
		
		for (int i = 0; i < creators.size(); i++) {
			panel.add(creators.get(i));
		}
		for (int i = 0; i < helpers.size(); i++) {
			panel.add(helpers.get(i));
		}
		
		this.add(barPane);
		this.add(panel);
	}
	
	private JLabel createLabel (JLabel label) {
		label.setForeground(textColor2);
		label.setBackground(mainBackgroundColor);
		return label;
	}
	
	@Override
	public void setColors() {
		super.setColors();
		labelcreators.setForeground(textColor2);
		labelhelpers.setForeground(textColor2);
		
		for (int i = 0; i < creators.size(); i++) {
			creators.get(i).setForeground(textColor2);
			creators.get(i).setBackground(mainBackgroundColor);
		}
		for (int i = 0; i < helpers.size(); i++) {
			helpers.get(i).setForeground(textColor2);
			helpers.get(i).setBackground(mainBackgroundColor);
		}
	}
	
	@Override
	public void setLocalisation() {
		super.setLocalisation();
		
		barPane.setTitle(VocableTrainerLocalization.MENU_CREDITS);
		
		labelcreators.setText(VocableTrainerLocalization.CREDITS_CREATORS);
		labelhelpers.setText(VocableTrainerLocalization.CREDITS_HELPERS);
	}
	
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        labelcreators.setBounds(panel.getWidth()/(12/1), panel.getHeight()/(16/2), panel.getWidth()/2, panel.getHeight()/12);
        labelcreators.setFont(new Font ("Arial", Font.BOLD, labelcreators.getHeight()/2 +5));
        
		for (int i = 0; i < creators.size(); i++) {
			JLabel temp = creators.get(i);
			temp.setBounds(panel.getWidth()/(12/2), panel.getHeight()/(16/(4+i)), panel.getWidth()/2, panel.getHeight()/18);
			temp.setFont(new Font ("Arial", Font.PLAIN, temp.getHeight()/2 +5));
		}
		
		labelhelpers.setBounds(panel.getWidth()/(12/1), panel.getHeight()/(16/7), panel.getWidth()/2, panel.getHeight()/12);
		labelhelpers.setFont(new Font ("Arial", Font.BOLD, labelhelpers.getHeight()/2 +5));
        
		for (int i = 0; i < helpers.size(); i++) {
			JLabel temp = helpers.get(i);
			temp.setBounds(panel.getWidth()/(12/2),(int)(panel.getHeight()/(16.0/(10+i))), panel.getWidth()/2, panel.getHeight()/18);
			temp.setFont(new Font ("Arial", Font.PLAIN, temp.getHeight()/2 +5));
		}
    }
}
