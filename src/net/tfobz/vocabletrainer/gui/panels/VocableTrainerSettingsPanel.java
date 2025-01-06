package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.data.VocableTrainerLocalization.localisation;
import net.tfobz.vocabletrainer.data.VocableTrainerSettingsIO;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerColorChooser;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInfoDialog;


@SuppressWarnings("serial")
public class VocableTrainerSettingsPanel extends VocableTrainerPanel {
	
	public final static Color colours[][] = {
			{new Color(50, 50, 75), new Color(171, 181, 216), new Color(225, 225, 225), new Color(11, 9, 10), new Color(111, 116, 146)},
			{new Color(25, 25, 25), Color.WHITE, Color.BLACK	, Color.BLACK, new Color(75, 75, 75)},
			{new Color(0, 136, 255), new Color(0, 12, 33), new Color(0, 0, 0), new Color(255, 255, 255), new Color(0, 136, 255)}
	};
	
	private JLabel language;
	private JComboBox<localisation> languageBox;
	private JLabel theme;
	private JComboBox<String> themeBox;
	private JCheckBox simplifiedBox;
	private JCheckBox allwoPremiumBox;
	private JButton button;
	private VocableTrainerColorChooser colorChooser;
	
	public VocableTrainerSettingsPanel (VocableTrainerFrame vtf) {
		super(vtf);
		 		
		panel.setLayout(null);
        
        languageBox = new JComboBox<VocableTrainerLocalization.localisation>(VocableTrainerLocalization.localisation.values());
        themeBox = new JComboBox<String>();
        themeBox.addItem("Acqua");
        themeBox.addItem("Monochrom light");
        themeBox.addItem("Dark Blue");
        themeBox.addItem("Custom");
        
        simplifiedBox = new JCheckBox();

        allwoPremiumBox = new JCheckBox();
		
        languageBox.setSelectedItem(VocableTrainerSettingsIO.localisation);
        simplifiedBox.setSelected(VocableTrainerSettingsIO.simplified);
        allwoPremiumBox.setSelected(VocableTrainerSettingsIO.premium);
        
        Color[] t = VocableTrainerSettingsIO.colours;
        
        if (t[0].equals(colours[0][0]) && t[1].equals(colours[0][1]) && t[2].equals(colours[0][2]) && t[3].equals(colours[0][3]) && t[4].equals(colours[0][4])) {
        	themeBox.setSelectedIndex(0);
        } else if (t[0].equals(colours[1][0]) && t[1].equals(colours[1][1]) && t[2].equals(colours[1][2]) && t[3].equals(colours[1][3]) && t[4].equals(colours[1][4])) {
        	themeBox.setSelectedIndex(1);
        } else if (t[0].equals(colours[2][0]) && t[1].equals(colours[2][1]) && t[2].equals(colours[2][2]) && t[3].equals(colours[2][3]) && t[4].equals(colours[2][4])) {
        	themeBox.setSelectedIndex(2);
        } else {
        	themeBox.setSelectedItem("Custom");
        }
        
		
        language = new JLabel();
        language.setForeground(textColor2);
        
        theme = new JLabel();
        theme.setForeground(textColor2);
        
        languageBox.setForeground(textColor2);
        languageBox.setBackground(textColor1);
        
        
        themeBox.setForeground(textColor2);
        themeBox.setBackground(textColor1);
        
        simplifiedBox.setBackground(mainBackgroundColor);
        simplifiedBox.setForeground(textColor2);
        simplifiedBox.setEnabled(false);
        simplifiedBox.setSelected(true);
       
        allwoPremiumBox.setBackground(mainBackgroundColor);
        allwoPremiumBox.setForeground(textColor2);

        allwoPremiumBox.setEnabled(false);
        allwoPremiumBox.setSelected(false);
        
        button = new JButton();
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(buttonBackgroundColor);
        button.setForeground(textColor1);
        
        languageBox.addActionListener(e -> {
        	super.changeLocalisation((localisation) languageBox.getSelectedItem());
        	VocableTrainerSettingsIO.saveSettings((localisation) languageBox.getSelectedItem());
        });
                
        themeBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(colorChooser!=null) {
					colorChooser.setVisible(false);
					colorChooser.dispose();
					colorChooser = null;
				}
				switch (themeBox.getSelectedIndex()) {
				case 0:
					changeColor(colours[0][0], colours[0][1], colours[0][2], colours[0][3], colours[0][4]);
					new Thread(() -> VocableTrainerSettingsIO.saveSettings(colours[0])).start();
					break;
				case 1:
					changeColor(colours[1][0], colours[1][1], colours[1][2], colours[1][3], colours[1][4]);
					new Thread(() -> VocableTrainerSettingsIO.saveSettings(colours[1])).start();
					break;
				case 2:
					changeColor(colours[2][0], colours[2][1], colours[2][2], colours[2][3], colours[2][4]);
					new Thread(() -> VocableTrainerSettingsIO.saveSettings(colours[2])).start();
		        	break;
				default:
					colorChooser = new VocableTrainerColorChooser(VocableTrainerSettingsPanel.this);
					colorChooser.setVisible(true);
					new Thread(() -> VocableTrainerSettingsIO.saveSettings(colorChooser.getColors())).start();
					break;
				}
			}
		});

        button.addActionListener(e -> openWebpage("https://github.com/gtrteimo/UEB16VocableTrainer"));
        		
        simplifiedBox.addActionListener(e -> {
        	new Thread(() -> VocableTrainerSettingsIO.saveSettings(simplifiedBox.isEnabled(), allwoPremiumBox.isSelected())).start();;
        });
        allwoPremiumBox.addActionListener(e -> {
        	new Thread(() -> VocableTrainerSettingsIO.saveSettings(simplifiedBox.isEnabled(), allwoPremiumBox.isSelected())).start();;
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
	
	private void openWebpage(String urlString) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                URI uri = new URI(urlString);
                desktop.browse(uri);
            } catch (Exception e) {
                new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_SETTINGS_NO_INTERNET);
            }
        } else {
            new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_SETTINGS_NOT_SUPPORTED+"https://github.com/gtrteimo/UEB16VocableTrainer");
        }
    }	
	
	@Override
	public void setColors() {
		super.setColors();
		
        language.setForeground(textColor2);
        
        theme.setForeground(textColor2);
        
        languageBox.setForeground(textColor2);
        languageBox.setBackground(textColor1);
        
        themeBox.setForeground(textColor2);
        themeBox.setBackground(textColor1);
        
        simplifiedBox.setBackground(mainBackgroundColor);
        simplifiedBox.setForeground(textColor2);
        
        allwoPremiumBox.setBackground(mainBackgroundColor);
        allwoPremiumBox.setForeground(textColor2);
        
        button.setBackground(buttonBackgroundColor);
        button.setForeground(textColor1);
	}
	
	@Override
	public void setLocalisation() {
		super.setLocalisation();
		
		barPane.setTitle(VocableTrainerLocalization.SETTINGS_NAME);
        language.setText("   " + VocableTrainerLocalization.SETTINGS_LANGUAGE);
        theme.setText("   " + VocableTrainerLocalization.SETTINGS_THEME);
        simplifiedBox.setText(VocableTrainerLocalization.SETTINGS_SIMPLIFIED_VIEW);
        allwoPremiumBox.setText(VocableTrainerLocalization.SETTINGS_ALLOW_PREMIUM);
        button.setText(VocableTrainerLocalization.SETTINGS_SUGGEST_FEATURE);
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