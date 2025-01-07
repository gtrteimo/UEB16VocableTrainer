package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;

@SuppressWarnings("serial")
public class VocableTrainerBarPanel extends VocableTrainerPanel {

    VocableTrainerPanel sourcePanel;

    final static String barPath = "/net/tfobz/vocabletrainer/main/resources/icons/bars.png";
    private static Image barsImage;

    private JLabel title;
    private String text;

    public void setTitle(String text) {
       this.text = text;
    }

    public VocableTrainerBarPanel(VocableTrainerPanel sourcePanel) {
        super(sourcePanel);
        setLayout(null);
        setBackground(menuBarColor);

        title = new JLabel();
        
        loadImage();
        setLocalisation();
        title.setForeground(textColor1);
        title.setHorizontalAlignment(SwingConstants.LEFT);
        add(title);
    }
    
    @Override
    public void setColors() {
    	setBackground(menuBarColor);
    	title.setForeground(textColor1);
	}
    
    @Override
	public void setLocalisation() {
    	if (title != null) {
	    	 if (text != null && !text.trim().replaceAll("\n", "").isEmpty()) {
	             title.setText(VocableTrainerLocalization.MAIN_TITLE + " - " + text);
	         } else {
	             title.setText(VocableTrainerLocalization.MAIN_TITLE);
	         }
    	}
	}
    
    @Override
    protected void loadImage() {
    	if (barsImage == null) {
	        try {
	            InputStream imgStream = getClass().getResourceAsStream(barPath);
	            if (imgStream != null) {
	                barsImage = ImageIO.read(imgStream);
	            } else {
	                System.err.println("Image not found at " + barPath);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    	}
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (barsImage != null) {
            g.drawImage(barsImage, 9, 9, getHeight() - 18, getHeight() - 18, null);
        }

        title.setBounds(getHeight(), 9, getWidth()-getHeight()-9, getHeight()-18);
        title.setFont(new Font("Arial", Font.BOLD, (getHeight() - 18) / 2 + 7));
    }
}
