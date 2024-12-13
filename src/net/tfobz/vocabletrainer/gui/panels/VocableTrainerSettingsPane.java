package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
public class VocableTrainerSettingsPane extends VocableTrainerPanel {

	private VocableTrainerFrame vtf;
	
	private VocableTrainerBarPane barPane;
	private VocableTrainerPanel panel;

	private JComboBox<String> languageBox;
	private JComboBox<String> themeBox;
	private JCheckBox simplifiedBox;
	private JCheckBox allwoPremiumBox;
	
	public VocableTrainerSettingsPane (VocableTrainerFrame vtf) {
		this.vtf=vtf;
		 
		setBackground(new Color(225, 225, 225, 255));
		
		this.setLayout(null);

        int height = /*getHeight()*/400-20;
        int width = /*getWidth()*/600-20;
        
        JLabel temp = new JLabel("Language");
        temp.setEnabled(false);
        temp.setBounds(10, 10, width/2-10, height/7-10);
        add(temp);
        languageBox = new JComboBox<String>();
        languageBox.setEnabled(false);
        languageBox.addItem("English");
        languageBox.setBounds(10+width/2, 10, width/2-10, height/7-10);
        add(languageBox);
        
        temp = new JLabel("Theme");
        temp.setEnabled(false);
        temp.setBounds(10, 10+height/7, width/2-10, height/7-10);
        add(temp);
        themeBox = new JComboBox<String>();
        themeBox.setEnabled(false);
        themeBox.addItem("Acqua");
        themeBox.setBounds(10+width/2, 10+height/7, width/2-10, height/7-10);
        add(themeBox);
        
        simplifiedBox = new JCheckBox("Simplified View");
        simplifiedBox.setEnabled(false);
        simplifiedBox.setSelected(true);
        simplifiedBox.setBounds(10, 10+2*height/7, width, height/7-10);
        add(simplifiedBox);
        
        allwoPremiumBox = new JCheckBox("Allow purchase of Premium");
        allwoPremiumBox.setEnabled(false);
        allwoPremiumBox.setSelected(false);
        allwoPremiumBox.setBounds(10, 10+3*height/7, width, height/7-10);
        add(allwoPremiumBox);
        
        JButton button = new JButton("Buy Premium to unlock Settings");
        button.setEnabled(false);
        button.setBounds(10, 10+4*height/7, width, height/7*2-10);
        add(button);
		
		barPane = new VocableTrainerBarPane(this);
		panel = new VocableTrainerPanel(this);
	}
}
