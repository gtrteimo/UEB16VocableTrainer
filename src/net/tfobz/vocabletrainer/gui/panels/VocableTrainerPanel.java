package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
	
	public final static Color C_nigth = new Color(11, 9, 10); 
	public final static Color C_spaceCadet = new Color(50, 50, 75); 
	public final static Color C_slateGray = new Color(111, 116, 146); 
	public final static Color C_powderBlue = new Color(171, 181, 216); 
	public final static Color C_platinum = new Color(225, 225, 225);
	
	protected VocableTrainerFrame vtf;
	protected VocableTrainerPanel vtp;
	
	protected boolean state = false;
			
	final static String backPath = "/icons/back.png";
    private static Image backImage;
	
	protected VocableTrainerBarPanel barPane;
	protected VocableTrainerPanel panel;
	
	public VocableTrainerPanel() {}
	
	public VocableTrainerPanel (VocableTrainerFrame vtf, boolean state) {
		super();
		this.vtf = vtf;
		this.state = state;
		
		if (barPane == null) {
			barPane = new VocableTrainerBarPanel(this);
		}
		int height = vtf.getHeight()-vtf.getInsets().top-vtf.getInsets().bottom;
		barPane.addMouseListener(new ImageClick(9, 9, height/12-18, height/12-18, -2));

	}
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
	public VocableTrainerPanel (VocableTrainerPanel vtp, boolean state) {
		super();
		this.vtp = vtp;
		this.state = state;
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
	
	public void retrive () {
		System.out.println("Hello World");
	}
	
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (state) {
        	if (vtf!=null) {
	    		barPane.setSize(getWidth(), getHeight() / 12);
	    		panel.setBounds(0, getHeight()/12, getWidth(), getHeight() - getHeight() / 12);
	        }
        } else {
	        if (vtf!=null) {
	    		barPane.setSize(getWidth(), getHeight() / 12);
	    		panel.setBounds(16, getHeight()/12, getWidth() - 32, getHeight() / 12 * 11 - 16);
	        } else if (vtp!=null) {
		        if (backImage != null) {
					g.drawImage(backImage, 16, 16, getHeight()/16, getHeight()/16, null);
				}
	        } else {
	        	
	        }
        }
    }
    
    public void paintComponentRun (Graphics g) {
		panel.setSize(getWidth()-32, getHeight()-32);
		super.paintComponent(g);
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
