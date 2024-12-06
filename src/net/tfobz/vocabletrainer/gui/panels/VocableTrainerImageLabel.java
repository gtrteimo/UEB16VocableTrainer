package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class VocableTrainerImageLabel extends JPanel {
	private Image image;
	private int width, heigth;

	public VocableTrainerImageLabel(String imageURL) {
		image = new ImageIcon(getClass().getResource("/icons/bars.png")).getImage();
    }
	public VocableTrainerImageLabel(ImageIcon image) {
		this.image = image.getImage();
    }
    public VocableTrainerImageLabel(Image image) {
        this.image = image;
    }

    
    public void draw () {
    	repaint();
    }
    public void draw (int width, int heigth) {
    	this.width = width;
    	this.heigth = heigth;
    	repaint();
	}
    
    @Override
    protected void paintComponent(Graphics g) {
    	System.out.println("Hello");
        super.paintComponent(g);
        g.drawImage(image, 20, 20, width, heigth ,null);
    }

}
