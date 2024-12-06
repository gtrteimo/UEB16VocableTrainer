package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")

public class VocableTrainerPanel extends JPanel {
	
	final static int NORMAL_PANEL = 0, MENU_PANEL = 1;
	
	protected VocableTrainerBarPane barPane;
	protected VocableTrainerPanel panel;
	
	public VocableTrainerPanel () {}
	public VocableTrainerPanel (int createPanelOption) {
		super();
		setBackground(new Color(225, 225, 225, 255));
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		barPane = new VocableTrainerBarPane(this);
		barPane.setPreferredSize(new Dimension(getWidth(), getHeight() / 12));
		this.add(barPane);

		panel = new VocableTrainerPanel(this);
		panel.setPreferredSize(new Dimension(getWidth() - 32, getHeight() / 12 * 11 - 16));
	
	}
	public VocableTrainerPanel (VocableTrainerPanel sourcePanel) {
		this(sourcePanel, new Color(171, 181, 216));
	}
	public VocableTrainerPanel (VocableTrainerPanel sourcePanel, Color colour) {
		super();
		super.setBackground(colour);
	}
	public VocableTrainerPanel (VocableTrainerPanel sourcePanel, int width, int height) {
		this(sourcePanel, 0, 0, width, height, new Color(171, 181, 216));
	}
	public VocableTrainerPanel (VocableTrainerPanel sourcePanel, int width, int height, Color colour) {
		this(sourcePanel, 0, 0, width, height, colour);
	}
	public VocableTrainerPanel (VocableTrainerPanel sourcePanel, int x, int y, int width, int height) {
		this(sourcePanel, x, y, width, height, new Color(171, 181, 216));
	}
	public VocableTrainerPanel (VocableTrainerPanel sourcePanel, int x, int y, int width, int height, Color colour) {
		super();
		super.setBackground(colour);
		super.setBounds(x, y, width, height);
	}
	public void doLayout() {
        super.doLayout();
        
        int totalHeight = getHeight();
        
        int barPaneHeight = totalHeight / 12; 
        int panelHeight = totalHeight - barPaneHeight;
        
        for (Component c: getComponents()) {
        	if (c instanceof VocableTrainerPanel) {
        		if (c instanceof VocableTrainerBarPane) {
        			VocableTrainerBarPane barPane = (VocableTrainerBarPane) c;
        			
        	        barPane.setMaximumSize(new Dimension(getWidth(), barPaneHeight));
        	        barPane.setMinimumSize(new Dimension(getWidth(), barPaneHeight));
        	        this.barPane = barPane;
        		} else {
        			VocableTrainerPanel panel = (VocableTrainerPanel) c;
        			
                	panel.setMaximumSize(new Dimension(getWidth()-32, panelHeight-16));
                	panel.setMinimumSize(new Dimension(getWidth()-32, panelHeight-16));
        		}
        	}
        }       
        revalidate();
	}
}
