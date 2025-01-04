package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
		panel.setBackground(C_slateGray);
		
		barPane.setTitle("");
		
		panel.setLayout(null);
		
		options[0] = new JButton("Home");
		options[1] = new JButton("Start");
		options[2] = new JButton("Info");
		options[3] = new JButton("New");
		options[4] = new JButton("Credits");
		options[5] = new JButton("Settings");
		options[6] = new JButton("Exit");

		for (int i = 0; i < options.length; i++) {
			options[i].setForeground(C_platinum);
			options[i].setBackground(C_slateGray);
			options[i].setFocusPainted(false);
			options[i].setBorderPainted(false);
			
			panel.add(options[i]);
		}
		
		options[0].addActionListener(new PanelsChanger(1));
		options[1].addActionListener(new PanelsChanger(4));
		options[2].addActionListener(new PanelsChanger(3));
		options[3].addActionListener(new PanelsChanger(2));
		options[4].addActionListener(new PanelsChanger(5));
		options[5].addActionListener(new PanelsChanger(6));
		options[6].addActionListener(new PanelsChanger(-1));

		add(barPane);
		add(panel);
	
	}
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int w = panel.getWidth();
        int h = panel.getHeight();
        
        for (int i = 0; i < options.length; i++) {
			options[i].setBounds(w/64, h/7*i+10, w/16*14/5, h/16);
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
