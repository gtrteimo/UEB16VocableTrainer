package net.tfobz.vocabletrainer.gui.panels;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("serial")
public class VocableTrainerBarPanel extends VocableTrainerPanel {

    VocableTrainerPanel sourcePanel;

    final static String barPath = "/icons/bars.png";
    private static Image barsImage;

    private JLabel title;

    public void setTitle(String text) {
        if (text.trim() != null && !text.trim().equals("")) {
            title.setText("Vocable Trainer - " + text);
        } else {
            title.setText("Vocable Trainer");
        }
    }

    public VocableTrainerBarPanel(VocableTrainerPanel sourcePanel) {
        super(sourcePanel);
        setLayout(null);
        setBackground(C_spaceCadet);

        loadImage();

        title = new JLabel("Vocable Trainer");
        title.setForeground(C_platinum);
        title.setHorizontalAlignment(SwingConstants.LEFT);
        add(title);
    }
    
    @Override
    public void setColours() {
    	setBackground(C_spaceCadet);
    	title.setForeground(C_platinum);
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
