package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

@SuppressWarnings("serial")

public class VocableTrainerPanel extends JPanel {
	
	final static Color C_nigth = new Color(11, 9, 10); 
	final static Color C_spaceCadet = new Color(50, 50, 75); 
	final static Color C_slateGray = new Color(111, 116, 146); 
	final static Color C_powderBlue = new Color(171, 181, 216); 
	final static Color C_platinum = new Color(225, 225, 225);
	
	protected VocableTrainerFrame vtf;
	protected VocableTrainerPanel vtp;
	
	final static String backPath = "/icons/back.png";
    private static Image backImage;
	
	protected VocableTrainerBarPanel barPane;
	protected VocableTrainerPanel panel;
	
	public VocableTrainerPanel (VocableTrainerFrame vtf) {
		super();
		this.vtf = vtf;
		setBackground(C_platinum);
		
		setLayout(null);
		
		if (barPane == null) {
			barPane = new VocableTrainerBarPanel(this);
		}
		int height = vtf.getHeight()-vtf.getInsets().top-vtf.getInsets().bottom;
		
		barPane.setBounds(0, 0, getWidth(), getHeight() / 12);
		barPane.addMouseListener(new ImageClick(9, 9, height/12-18, height/12-18, 0));
		
		panel = new VocableTrainerPanel(this);
		panel.setBounds(8, getHeight()/12, getWidth() - 32, getHeight() / 12 * 11 - 16);
		panel.setBackground(C_powderBlue);
		panel.addMouseListener(new ImageClick(16, 16, (height-32)/16, (height-32)/16, -1));
	}
	public VocableTrainerPanel (VocableTrainerPanel vtp) {
		super();
		this.vtp = vtp;
	}
	
	protected void loadImage() {
    	if (backImage == null) {
	        try {
	            InputStream imgStream = getClass().getResourceAsStream(backPath);
	            if (imgStream != null) {
	            	backImage = ImageIO.read(imgStream);
	            } else {
	                System.err.println("Image not found at " + backPath);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    	}
    }
	
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (vtf!=null) {
    		barPane.setSize(getWidth(), getHeight() / 12);
    		panel.setBounds(16, getHeight()/12, getWidth() - 32, getHeight() / 12 * 11 - 16);
        } else if (vtp!=null) {
	        if (backImage != null) {
				g.drawImage(backImage, 16, 16, getHeight()/16, getHeight()/16, null);
			}
        }
    }
    private class ImageClick implements MouseListener {

    	private int x, y, width, height, panelIndex;
    	
    	public ImageClick (int x, int y, int width, int height, int panelIndex) {
    		this.x = x;
    		this.y = y;
    		this.width = width;
    		this.height = height;
    		this.panelIndex = panelIndex;
    	}
    	@Override
		public void mouseReleased(MouseEvent e) {
			int mouseX = e.getX();
    		int mouseY = e.getY();
    		if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
				try {
    				vtf.changePanel(panelIndex);
    			} catch (Exception e1) {
        			System.err.println("Failed to change to Panel: "+panelIndex);
				}
    		}
		}    	
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
    }
}
