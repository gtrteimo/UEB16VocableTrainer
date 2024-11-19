package net.tfobz.vocabletrainer.gui;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;

import net.tfobz.vocabletrainer.gui.panels.*;

/**
 * Class that creates the frame for the GUI of the vocable trainer to be displayed
 * 
 * Mainly focuses on switching the differant panels and sorting out what to paint on the frame.
 * The rest is done by the differant panels
 * 
 * @author gtrteimo
 */
@SuppressWarnings("serial")
public class VocableTrainerFrame extends JFrame {
	
	private int width, height;
	
	private Container contentPane;
	
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Container getContentPane() {
		return contentPane;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Default size of the panel contained in the frame
	 * width = 1200px
	 * heigth = 900px
	 */
	public VocableTrainerFrame() {
		this(1200, 900);
	}
	
	/**
	 * Initiales the vocable trainer
	 * @param width The width that the panel inside the frame has. 
	 * 				The frame is a bit bigger because of the insets the frame has. 
	 * @param height The height that the panel inside the frame has. 
	 * 				 The frame is a bit bigger because of the insets the frame has.
	 */
	public VocableTrainerFrame(int width, int height) {
		this.width = width;
		this.height = height;
		
		setTitle("Vocable Trainer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(25, 25);
		
		//Using ContentPane for better Panel switching
		contentPane = super.getContentPane();
		
		//Start Panel
		HomePanel homePanel = new HomePanel(this);
		contentPane.add(homePanel);
		
		//Size depends on the Components. Like that there is no need to worry about Insets
		pack();
		
		
	}
//	TODO -> Tell each component (panel) when to paint.
//	public void paint (Graphics g) { 
//						
//	} 
	
}
