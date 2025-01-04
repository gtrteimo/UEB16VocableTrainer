package net.tfobz.vocabletrainer.gui.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

@SuppressWarnings("serial")
public class VocableTrainerHomePanel extends VocableTrainerPanel {
		
	private JButton B_start;
	private JButton B_new;
	private JButton B_info;
	private JButton B_exit;
	
	public VocableTrainerHomePanel (VocableTrainerFrame vtf) {
		super(vtf);
		
		panel.loadImage();
		
		barPane.setTitle("Home");
		
		panel.setLayout(null);
		
		B_start = new JButton ("Start");
		B_new = new JButton ("New");
		B_info = new JButton ("Info");
		B_exit = new JButton ("Exit");
		
		B_start.setBackground(C_slateGray);
		B_new.setBackground(C_slateGray);
		B_info.setBackground(C_slateGray);
		B_exit.setBackground(C_slateGray);
		
		B_start.setForeground(C_platinum);
		B_new.setForeground(C_platinum);
		B_info.setForeground(C_platinum);
		B_exit.setForeground(C_platinum);

		B_start.setFocusPainted(false);
		B_new.setFocusPainted(false);
		B_info.setFocusPainted(false);
		B_exit.setFocusPainted(false);

		B_start.setBorderPainted(false);
		B_new.setBorderPainted(false);
		B_info.setBorderPainted(false);
		B_exit.setBorderPainted(false);
		
		B_start.setMnemonic('S');
		B_new.setMnemonic('N');
		B_info.setMnemonic('I');
		B_exit.setMnemonic('E');
		
		B_start.addActionListener(new ButtonListener(4));
		B_new.addActionListener(new ButtonListener(2));
		B_info.addActionListener(new ButtonListener(3));
		B_exit.addActionListener(new ButtonListener(-1));
		
		panel.add(B_start);
		panel.add(B_new);
		panel.add(B_info);
		panel.add(B_exit);
		
		
		this.add(barPane);
		this.add(panel);
	}
	
	@Override
	public void paintComponent(Graphics g) {        
        super.paintComponent(g);
        
        int middleX = panel.getWidth()/2;
		int middleY = panel.getHeight()/2; 
				
		int width = middleX/2;
		int height = middleY/2;
        
        B_start.setBounds(middleX - width - 8, middleY - height - 8, width, height);
		B_new.setBounds(middleX + 8, middleY - height - 8, width, height);
		B_info.setBounds(middleX - width - 8, middleY + 8, width, height);
		B_exit.setBounds(middleX + 8, middleY + 8, width, height);
		
		B_start.setFont(new Font ("Arial", Font.PLAIN, height/2));
		B_new.setFont(new Font ("Arial", Font.PLAIN, height/2));
		B_info.setFont(new Font ("Arial", Font.PLAIN, height/2));
		B_exit.setFont(new Font ("Arial", Font.PLAIN, height/2));
		
    }
	private class ButtonListener implements ActionListener {

		private int panelIndex;
		
		public ButtonListener (int panelIndex) {
			super();
			this.panelIndex = panelIndex;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (panelIndex == -1) {
				vtf.close();
			} else {
				try {
					vtf.changePanel(panelIndex);
				} catch (Exception e1) {
					System.err.println("Failed to change to Panel: "+panelIndex);
					e1.printStackTrace();
				}
			}
		}
	}
}
