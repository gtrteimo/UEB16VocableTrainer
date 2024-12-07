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

    final static String pfad = "/icons/bars.png";
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
        setBackground(new Color(50, 50, 75, 255));

        loadImage();

        title = new JLabel("Vocable Trainer");
        title.setForeground(new Color(225, 225, 225));
        title.setHorizontalAlignment(SwingConstants.LEFT);
        add(title);
    }

    private void loadImage() {
        try {
            InputStream imgStream = getClass().getResourceAsStream(pfad);
            if (imgStream != null) {
                barsImage = ImageIO.read(imgStream);
            } else {
                System.err.println("Image not found at " + pfad);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
