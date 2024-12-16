package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

import javax.imageio.ImageIO;

import net.tfobz.vocabletrainer.access.*;
import net.tfobz.vocabletrainer.db.*;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

@SuppressWarnings("serial")
public class VocableTrainerEditPanel extends VocableTrainerPanel {
	
	final static String emptySetPath = "/icons/emptySet.png";
    private static Image emptySetImage;
    
    final static String setPath = "/icons/set.png";
    private static Image setImage;
	
	private ArrayList<Lernkartei> sets;
	
	public VocableTrainerEditPanel (VocableTrainerFrame vtf) {
		super(vtf);
		
		barPane.setTitle("Edit");
		
		sets = VocableTrainerInterface.getSets();
		
		loadImage();
		
		this.add(barPane);
		this.add(panel);
	}
	
	@Override
	protected void loadImage() {
		super.loadImage();
		if (emptySetImage == null) {
	        try {
	            InputStream imgStream = getClass().getResourceAsStream(emptySetPath);
	            if (imgStream != null) {
	            	emptySetImage = ImageIO.read(imgStream);
	            } else {
	                System.err.println("Image not found at " + emptySetPath);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    	}
		if (setImage == null) {
	        try {
	            InputStream imgStream = getClass().getResourceAsStream(setPath);
	            if (imgStream != null) {
	            	setImage = ImageIO.read(imgStream);
	            } else {
	                System.err.println("Image not found at " + setPath);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    	}
	}
	
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (setImage != null) {
        	System.out.println("help");
			g.drawImage(setImage, 64, 64, getHeight()/16, getHeight()/16, null);
		}
    }
}
