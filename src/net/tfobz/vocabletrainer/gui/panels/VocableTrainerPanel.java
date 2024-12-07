package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

@SuppressWarnings("serial")

public class VocableTrainerPanel extends JPanel {
	
	private VocableTrainerFrame vtf;
	private VocableTrainerPanel vtp;
	
	protected VocableTrainerBarPanel barPane;
	protected VocableTrainerPanel panel;
	
	public VocableTrainerPanel (VocableTrainerFrame vtf) {
		super();
		this.vtf = vtf;
		setBackground(new Color(225, 225, 225, 255));
		
		setLayout(null);
		
		if (barPane == null) {
			barPane = new VocableTrainerBarPanel(this);
		}
		barPane.setBounds(0, 0, getWidth(), getHeight() / 12);

		panel = new VocableTrainerPanel(this);
		panel.setBounds(8, getHeight()/12, getWidth() - 32, getHeight() / 12 * 11 - 16);
		panel.setBackground(new Color (171, 181, 216));
		
		addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		int x = e.getX();
        		int y = e.getY();

        		if (x >= 9 && x <= 9 + getHeight() - 18 && y >= 9 && y <= 9 + getHeight() - 18) {
        			System.out.println("Open Menu Pane");
//        			vtf.add(vtf.panels[1]);
        		}
        	}
    	});
	}
	public VocableTrainerPanel (VocableTrainerPanel vtp) {
		super();
		this.vtp = vtp;
	}
	
    @Override
	public void paintComponent(Graphics g) {
        if (vtf!=null) {
    		barPane.setSize(getWidth(), getHeight() / 12);
    		panel.setBounds(16, getHeight()/12, getWidth() - 32, getHeight() / 12 * 11 - 16);
        }
        
        super.paintComponent(g);
    }
}
