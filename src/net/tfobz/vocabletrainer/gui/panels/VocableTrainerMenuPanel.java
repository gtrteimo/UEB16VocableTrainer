package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.*;

@SuppressWarnings("serial")
public class VocableTrainerMenuPanel extends VocableTrainerPanel {
		
	private JButton options[] = new JButton[7];
	
	public VocableTrainerMenuPanel (VocableTrainerFrame vtf) {
		super(vtf, true);
				
		setLayout(null);
		
		barPane.setBounds(0, 0, getWidth(), getHeight() / 12);
		
		panel = new VocableTrainerPanel(this, true);
		panel.setBounds(0, getHeight()/12, getWidth()/2 - 16, getHeight() / 12 * 11 - 16);
		panel.setBackground(buttonBackgroundColor);
		
		barPane.setTitle("");
		
		panel.setLayout(null);
		
		options[0] = new JButton();
		options[1] = new JButton();
		options[2] = new JButton();
		options[3] = new JButton();
		options[4] = new JButton();
		options[5] = new JButton();
		options[6] = new JButton();

		for (int i = 0; i < options.length; i++) {
			options[i].setForeground(textColor1);
			options[i].setBackground(buttonBackgroundColor);
			options[i].setFocusPainted(false);
			options[i].setBorderPainted(false);
			
			panel.add(options[i]);
		}
		
		options[0].addActionListener(new PanelsChanger(1));
		options[1].addActionListener(new PanelsChanger(5));
		options[2].addActionListener(new PanelsChanger(4));
		options[3].addActionListener(new PanelsChanger(3));
		options[4].addActionListener(new PanelsChanger(6));
		options[5].addActionListener(new PanelsChanger(7));
		options[6].addActionListener(new PanelsChanger(-1));

		add(barPane);
		add(panel);
	
	}
	
	@Override
	public void setColors() {
		super.setColors();
		panel.setBackground(buttonBackgroundColor);
		
		for (int i = 0; i < options.length; i++) {
			options[i].setForeground(textColor1);
			options[i].setBackground(buttonBackgroundColor);
		}
	}
	
	@Override
	public void setLocalisation() {
		options[0].setText(VocableTrainerLocalization.MENU_HOME);
		options[1].setText(VocableTrainerLocalization.MENU_START);
		options[2].setText(VocableTrainerLocalization.MENU_INFO);
		options[3].setText(VocableTrainerLocalization.MENU_NEW);
		options[4].setText(VocableTrainerLocalization.MENU_CREDITS);
		options[5].setText(VocableTrainerLocalization.MENU_SETTINGS);
		options[6].setText(VocableTrainerLocalization.MENU_HELP);
	}
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int w = panel.getWidth();
        int h = panel.getHeight();
        
        for (int i = 0; i < options.length; i++) {
			options[i].setBounds(w/64, h/7*i+10, w/4, h/16);
			options[i].setFont(new Font ("Arial", Font.BOLD, options[i].getHeight()/2 + 10));
			options[i].setHorizontalAlignment(SwingConstants.LEFT);
        }
    }
	private class PanelsChanger implements ActionListener {

		private int panelIndex;
		
		public PanelsChanger(int panelIndex) {
			this.panelIndex = panelIndex;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (panelIndex < 0) {
				vtf.close();
			} else {
				try {
					vtf.changePanel(-2);
					vtf.changePanel(panelIndex);
				} catch (Exception e1) {
	    			System.err.println("Failed to change to Panel: "+panelIndex);
				}
			}
		}
	}
}
