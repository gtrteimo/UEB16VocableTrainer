package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

/**
 * Represents the main panel of the Vocable Trainer application.
 * Manages the layout, colors, and localization settings.
 */
@SuppressWarnings("serial")
public class VocableTrainerPanel extends JPanel {
    
    // Static color definitions for consistent UI theming
    public static Color textColor2 = new Color(11, 9, 10); 
    public static Color menuBarColor = new Color(50, 50, 75); 
    public static Color buttonBackgroundColor = new Color(111, 116, 146); 
    public static Color mainBackgroundColor = new Color(171, 181, 216); 
    public static Color textColor1 = new Color(225, 225, 225);
    
    protected VocableTrainerFrame vtf;
    protected VocableTrainerPanel vtp;
    
    protected boolean state = false;
            
    private static final String backPath =  "/net/tfobz/vocabletrainer/main/resources/icons/back.png";
    private static Image backImage;
    
    protected VocableTrainerBarPanel barPane;
    protected VocableTrainerPanel panel;
    
    /**
     * Default constructor.
     */
    public VocableTrainerPanel() {}
    
    /**
     * Constructs a VocableTrainerPanel with a reference to the main frame and initial state.
     * 
     * @param vtf the main VocableTrainerFrame
     * @param state the initial state of the panel
     */
    public VocableTrainerPanel(VocableTrainerFrame vtf, boolean state) {
        super();
        this.vtf = vtf;
        this.state = state;
        
        if (barPane == null) {
            barPane = new VocableTrainerBarPanel(this);
        }
        int height = vtf.getHeight() - vtf.getInsets().top - vtf.getInsets().bottom;
        barPane.addMouseListener(new ImageClick(9, 9, height / 12 - 18, height / 12 - 18, -2));
    }
    
    /**
     * Constructs a VocableTrainerPanel with a reference to the main frame.
     * Initializes the layout and adds sub-panels.
     * 
     * @param vtf the main VocableTrainerFrame
     */
    public VocableTrainerPanel(VocableTrainerFrame vtf) {
        super();
        this.vtf = vtf;
        setBackground(textColor1);
        
        setLayout(null);
        
        if (barPane == null) {
            barPane = new VocableTrainerBarPanel(this);
        }
        int height = vtf.getHeight() - vtf.getInsets().top - vtf.getInsets().bottom;
        
        barPane.setBounds(0, 0, getWidth(), getHeight() / 12);
        barPane.addMouseListener(new ImageClick(9, 9, height / 12 - 18, height / 12 - 18, 0));
        
        panel = new VocableTrainerPanel(this);
        panel.setBounds(8, getHeight() / 12, getWidth() - 32, getHeight() / 12 * 11 - 16);
        panel.setBackground(mainBackgroundColor);
        panel.addMouseListener(new ImageClick(16, 16, (height - 32) / 16, (height - 32) / 16, -1));
    }
    
    /**
     * Constructs a VocableTrainerPanel with a reference to another VocableTrainerPanel.
     * 
     * @param vtp the parent VocableTrainerPanel
     */
    public VocableTrainerPanel(VocableTrainerPanel vtp) {
        super();
        this.vtp = vtp;
    }
    
    /**
     * Constructs a VocableTrainerPanel with a reference to another VocableTrainerPanel and initial state.
     * 
     * @param vtp the parent VocableTrainerPanel
     * @param state the initial state of the panel
     */
    public VocableTrainerPanel(VocableTrainerPanel vtp, boolean state) {
        super();
        this.vtp = vtp;
        this.state = state;
    }
    
    /**
     * Sets the colors for the panel and its sub-components.
     */
    public void setColors() {
        setBackground(textColor1);
        barPane.setColors();
        panel.setBackground(mainBackgroundColor);
    }
    
    /**
     * Sets the localization for the panel and its sub-components.
     */
    public void setLocalisation() {
        // Localization setting can be implemented here
    }
    
    /**
     * Changes the static color scheme of the application.
     * 
     * @param newMenuBarColor the new color for the menu bar
     * @param newMainBackgroundColor the new color for the main background
     * @param newTextColor1 the new primary text color
     * @param newTextColor2 the new secondary text color
     * @param newButtonBackgroundColor the new background color for buttons
     */
    public static void changeColorStatic(Color newMenuBarColor, Color newMainBackgroundColor, Color newTextColor1, Color newTextColor2, Color newButtonBackgroundColor) {
        menuBarColor = newMenuBarColor;
        mainBackgroundColor = newMainBackgroundColor;
        textColor1 = newTextColor1;
        textColor2 = newTextColor2;
        buttonBackgroundColor = newButtonBackgroundColor;
    }
    
    /**
     * Changes the color scheme of the application and updates all panels.
     * 
     * @param newMenuBarColor the new color for the menu bar
     * @param newMainBackgroundColor the new color for the main background
     * @param newTextColor1 the new primary text color
     * @param newTextColor2 the new secondary text color
     * @param newButtonBackgroundColor the new background color for buttons
     */
    public void changeColor(Color newMenuBarColor, Color newMainBackgroundColor, Color newTextColor1, Color newTextColor2, Color newButtonBackgroundColor) {
        menuBarColor = newMenuBarColor;
        mainBackgroundColor = newMainBackgroundColor;
        textColor1 = newTextColor1;
        textColor2 = newTextColor2;
        buttonBackgroundColor = newButtonBackgroundColor;
        
        for (VocableTrainerPanel panel : vtf.getPanels()) {
            if (panel != null) {
                panel.setColors();
            }
        }
    }
    
    /**
     * Changes the static localization of the application.
     * 
     * @param l the new localization setting
     */
    public static void changeLocalisationStatic(VocableTrainerLocalization.localisation l) {
        VocableTrainerLocalization.loadLocalization(l);
    }
    
    /**
     * Changes the localization of the application and updates all panels.
     * 
     * @param l the new localization setting
     */
    public void changeLocalisation(VocableTrainerLocalization.localisation l) {
        VocableTrainerLocalization.loadLocalization(l);
        
        for (VocableTrainerPanel panel : vtf.getPanels()) {
            if (panel != null) {
                panel.setLocalisation();
            }
        }
    }
    
    /**
     * Loads the background image if it hasn't been loaded yet.
     */
    protected void loadImage() {
        if (backImage == null) {
            try (InputStream imgStream = getClass().getResourceAsStream(backPath)) {
                if (imgStream != null) {
                    backImage = ImageIO.read(imgStream);
                }
            } catch (IOException e) {
                // Handle image loading failure if necessary
            }
        }
    }
    
    /**
     * Retrieves necessary data or state. (Method implementation pending)
     */
    public void retrive() {}
    
    /**
     * Paints the component, adjusting layout based on state.
     * 
     * @param g the Graphics context
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (state) {
            if (vtf != null) {
                barPane.setSize(getWidth(), getHeight() / 12);
                panel.setBounds(0, getHeight() / 12, getWidth(), getHeight() - getHeight() / 12);
            }
        } else {
            if (vtf != null) {
                barPane.setSize(getWidth(), getHeight() / 12);
                panel.setBounds(16, getHeight() / 12, getWidth() - 32, getHeight() / 12 * 11 - 16);
            } else if (vtp != null) {
                if (backImage != null) {
                    g.drawImage(backImage, 16, 16, getHeight() / 16, getHeight() / 16, null);
                }
            }
        }
    }
    
    /**
     * Paints the component during runtime, adjusting the panel size.
     * 
     * @param g the Graphics context
     */
    public void paintComponentRun(Graphics g) {
        panel.setSize(getWidth() - 32, getHeight() - 32);
        super.paintComponent(g);
    }
    
    /**
     * Inner class to handle mouse click events on images.
     */
    private class ImageClick implements MouseListener {

        private int x, y, width, height, panelIndex;
        
        /**
         * Constructs an ImageClick listener with specified bounds and target panel index.
         * 
         * @param x the x-coordinate of the clickable area
         * @param y the y-coordinate of the clickable area
         * @param width the width of the clickable area
         * @param height the height of the clickable area
         * @param panelIndex the index of the panel to switch to upon click
         */
        public ImageClick(int x, int y, int width, int height, int panelIndex) {
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
                    // Handle exception if panel change fails
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
