package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import net.tfobz.vocabletrainer.gui.*;
import net.tfobz.vocabletrainer.gui.listeners.*;
/**
 * Starting panel that you will see when you start the programm.
 * Boast some panels that the user can navigate to.
 * 
 * @author gtrteimo
 */
@SuppressWarnings("serial")
public class HomePanel extends JPanel implements VocableTrainerPanelInterface{
	//TODO -> all
	private VocableTrainerFrame vtf;
	private BarPanel bar;
	
	public HomePanel (VocableTrainerFrame vtf) {
		this.vtf = vtf;
		setPreferredSize(new Dimension(vtf.getWidth(), vtf.getHeight()));
//		setSize(new Dimension(vtf.getWidth(), vtf.getHeight()));
		this.setBackground(new Color(225, 225, 225, 255));
		this.setLayout(null);
		
		bar = new BarPanel(vtf.getWidth(), vtf.getHeight());
		
		this.add(bar);
		
		super.addComponentListener(new ResizeAdapter());
	}
	
	public void updateSize(int width, int heigth) {
//		setPreferredSize(new Dimension(width, width));
//		vtf.pack();
//		repaint();
//		System.out.println("Help");
//		TODO -> fix
	}
}
