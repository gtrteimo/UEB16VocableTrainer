package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.data.VocableTrainerLocalization.localisation;
import net.tfobz.vocabletrainer.data.VocableTrainerRunSettings.TimeUnit;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerColorChooser;


@SuppressWarnings("serial")
public class VocableTrainerSettingsPanel extends VocableTrainerPanel {
	
	private JLabel language;
	private JComboBox<localisation> languageBox;
	private JLabel theme;
	private JComboBox<String> themeBox;
	private JCheckBox simplifiedBox;
	private JCheckBox allwoPremiumBox;
	private JButton button;
	
	public VocableTrainerSettingsPanel (VocableTrainerFrame vtf) {
		super(vtf);
		 		
		panel.setLayout(null);
        
        language = new JLabel("   " + VocableTrainerLocalization.SETTINGS_LANGUAGE);
        language.setForeground(C_nigth);
        
        theme = new JLabel("   " + VocableTrainerLocalization.SETTINGS_THEME);
        theme.setForeground(C_nigth);
        
        languageBox = new JComboBox<VocableTrainerLocalization.localisation>(VocableTrainerLocalization.localisation.values());
        languageBox.setForeground(C_nigth);
        languageBox.setBackground(C_platinum);
        
        themeBox = new JComboBox<String>();
        themeBox.setForeground(C_nigth);
        themeBox.setBackground(C_platinum);
        themeBox.addItem("Acqua");
        themeBox.addItem("Monochrom light");
        themeBox.addItem("Dark Blue");
        themeBox.addItem("Custom");
        
        simplifiedBox = new JCheckBox(VocableTrainerLocalization.SETTINGS_SIMPLIFIED_VIEW);
        simplifiedBox.setBackground(C_powderBlue);
        simplifiedBox.setForeground(C_nigth);
        simplifiedBox.setEnabled(false);
        simplifiedBox.setSelected(true);
        
        allwoPremiumBox = new JCheckBox(VocableTrainerLocalization.SETTINGS_ALLOW_PREMIUM);
        allwoPremiumBox.setBackground(C_powderBlue);
        allwoPremiumBox.setForeground(C_nigth);

        allwoPremiumBox.setEnabled(false);
        allwoPremiumBox.setSelected(false);
        
        button = new JButton(VocableTrainerLocalization.SETTINGS_SUGGEST_FEATURE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(C_slateGray);
        button.setForeground(C_platinum);
        
        themeBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (themeBox.getSelectedIndex()) {
				case 0:
					changeColour(new Color(50, 50, 75), new Color(171, 181, 216), new Color(225, 225, 225), new Color(11, 9, 10), new Color(111, 116, 146));
					break;
				case 1:
					changeColour(new Color(25, 25, 25), Color.WHITE, Color.WHITE, Color.BLACK, new Color(75, 75, 75));
					break;
				case 2:
					changeColour(new Color(0, 136, 255), new Color(0, 12, 33), new Color(255, 255, 255), new Color(255, 255, 255), new Color(0, 136, 255));
					break;
				default:
					JFrame colorChooser = new VocableTrainerColorChooser(VocableTrainerSettingsPanel.this);
					colorChooser.setVisible(true);
					break;
				}
			}
		});
		
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