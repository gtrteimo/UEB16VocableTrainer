package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;


@SuppressWarnings("serial")
public class VocableTrainerSettingsPanel extends VocableTrainerPanel {
	
	private JLabel language;
	private JComboBox<String> languageBox;
	private JLabel theme;
	private JComboBox<String> themeBox;
	private JCheckBox simplifiedBox;
	private JCheckBox allwoPremiumBox;
	private JButton button;
	
	public VocableTrainerSettingsPanel (VocableTrainerFrame vtf) {
		super(vtf);
		 		
		panel.setLayout(null);
        
        language = new JLabel("Language");
        language.setEnabled(false);
        language.setForeground(C_nigth);
        
        theme = new JLabel("Theme");
        theme.setEnabled(false);
        theme.setForeground(C_nigth);
        
        languageBox = new JComboBox<String>();
        languageBox.setEnabled(false);
        languageBox.addItem("English");
        
        themeBox = new JComboBox<String>();
        themeBox.setEnabled(false);
        themeBox.addItem("Acqua");
        
        simplifiedBox = new JCheckBox("Simplified View");
        simplifiedBox.setEnabled(false);
        simplifiedBox.setSelected(true);
        
        allwoPremiumBox = new JCheckBox("Allow purchase of Premium");
        allwoPremiumBox.setEnabled(false);
        allwoPremiumBox.setSelected(false);
        
        button = new JButton("Buy Premium to unlock Settings");
        button.setEnabled(false);
		
        panel.add(language);
        panel.add(theme);
        panel.add(languageBox);
        panel.add(themeBox);
        panel.add(simplifiedBox);
        panel.add(allwoPremiumBox);
        panel.add(button);
        
		add(barPane);
		add(panel);
	}
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int h = panel.getHeight();
        int w = panel.getWidth();
        
        language.setBounds(25, 25, w/5, h/7);
//        theme.setBounds(10, 10+height/7, width/2-10, height/7-10);
//        languageBox.setBounds(10+width/2, 10, width/2-10, height/7-10);
//        themeBox.setBounds(10+width/2, 10+height/7, width/2-10, height/7-10);
//        simplifiedBox.setBounds(10, 10+2*height/7, width, height/7-10);
//        allwoPremiumBox.setBounds(10, 10+3*height/7, width, height/7-10);
//        button.setBounds(10, 10+4*height/7, width, height/7*2-10);
    }
}