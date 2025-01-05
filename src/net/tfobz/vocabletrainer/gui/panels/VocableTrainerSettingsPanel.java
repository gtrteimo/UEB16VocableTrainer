package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
        
        language = new JLabel("   Language");
        language.setEnabled(false);
        language.setForeground(C_nigth);
        
        theme = new JLabel("   Theme");
        theme.setEnabled(false);
        theme.setForeground(C_nigth);
        
        languageBox = new JComboBox<String>();
        languageBox.setEnabled(false);
        languageBox.setForeground(C_nigth);
        languageBox.setBackground(C_platinum);
        languageBox.addItem("English");
        
        themeBox = new JComboBox<String>();
        themeBox.setEnabled(false);
        themeBox.setForeground(C_nigth);
        themeBox.setBackground(C_platinum);
        themeBox.addItem("Acqua");
        
        simplifiedBox = new JCheckBox(" Simplified View");
        simplifiedBox.setBackground(C_powderBlue);
        simplifiedBox.setForeground(C_nigth);
        simplifiedBox.setEnabled(false);
        simplifiedBox.setSelected(true);
        
        allwoPremiumBox = new JCheckBox(" Allow purchase of Premium");
        allwoPremiumBox.setBackground(C_powderBlue);
        allwoPremiumBox.setForeground(C_nigth);

        allwoPremiumBox.setEnabled(false);
        allwoPremiumBox.setSelected(false);
        
        button = new JButton("Buy Premium to unlock Settings");
        button.setEnabled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(C_slateGray);
        button.setForeground(C_platinum);
		
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
	
	@Override
	public void setColours() {
		super.setColours();
		
        language.setForeground(C_nigth);
        
        theme.setForeground(C_nigth);
        
        languageBox.setForeground(C_nigth);
        languageBox.setBackground(C_platinum);
        
        themeBox.setForeground(C_nigth);
        themeBox.setBackground(C_platinum);
        
        simplifiedBox.setBackground(C_powderBlue);
        simplifiedBox.setForeground(C_nigth);
        
        allwoPremiumBox.setBackground(C_powderBlue);
        allwoPremiumBox.setForeground(C_nigth);
        
        button.setBackground(C_slateGray);
        button.setForeground(C_platinum);
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int h = panel.getHeight();
        int w = panel.getWidth();
                
        language.setBounds(w/29, h/17, w/3, h/8);
        language.setFont(new Font ("Arial", Font.PLAIN, language.getHeight()/2 +5));
        
        languageBox.setBounds(w-w/2-16, h/17, w/2, h/8);
        languageBox.setFont(new Font ("Arial", Font.PLAIN, languageBox.getHeight()/2 +5));
        
        theme.setBounds(w/29, h/17*4, w/4, h/8);
        theme.setFont(new Font ("Arial", Font.PLAIN, theme.getHeight()/2 +5));
        
        themeBox.setBounds(w-w/2-16, h/17*4, w/2, h/8);
        themeBox.setFont(new Font ("Arial", Font.PLAIN, themeBox.getHeight()/2 +5));
        
        simplifiedBox.setBounds(w/29, h/17*7, w/2,  h/8);
        simplifiedBox.setFont(new Font ("Arial", Font.PLAIN, simplifiedBox.getHeight()/2 + 5));
        
        allwoPremiumBox.setBounds(w/29, h/17*10, w-20,  h/8);
        allwoPremiumBox.setFont(new Font ("Arial", Font.PLAIN, allwoPremiumBox.getHeight()/2 + 5));
        
        button.setBounds(16, panel.getHeight()/4*3, panel.getWidth()-32, panel.getHeight()/4-16);
        button.setFont(new Font ("Arial", Font.BOLD, button.getHeight()/2-10));
    }
}