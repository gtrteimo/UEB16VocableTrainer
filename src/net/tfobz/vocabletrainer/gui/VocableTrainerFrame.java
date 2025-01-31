package net.tfobz.vocabletrainer.gui;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.data.VocableTrainerSettingsIO;
import net.tfobz.vocabletrainer.gui.panels.*;

/**
 * Main frame of the Vocable Trainer application. This class manages the
 * primary GUI structure, including the switching between different panels
 * and maintaining the navigation history.
 */
@SuppressWarnings("serial")
public class VocableTrainerFrame extends JFrame {

    private static final String ICON_PATH = "/net/tfobz/vocabletrainer/main/resources/icons/icon.png";

    private Image icon;
    private Container contentPane;
    private ArrayList<Integer> history = new ArrayList<>();
    private VocableTrainerPanel[] panels = new VocableTrainerPanel[8];

    /**
     * Returns the array of all panels used in the application.
     * 
     * @return an array of VocableTrainerPanel objects
     */
    public VocableTrainerPanel[] getPanels() {
        return panels;
    }

    /**
     * Constructs a new VocableTrainerFrame with default dimensions.
     */
    public VocableTrainerFrame() {
        this(1080, 720);
    }

    /**
     * Constructs a new VocableTrainerFrame with specified dimensions.
     * 
     * @param width  the width of the frame
     * @param height the height of the frame
     */
    public VocableTrainerFrame(int width, int height) {
        super();
        setSize(width, height);
        setMinimumSize(new Dimension(720, 480));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(25, 25, width, height);
        //setLocationRelativeTo(null);
        loadImage();
        setIconImage(icon);

        // Load settings and apply them to the application
        VocableTrainerSettingsIO.loadSettings();
        VocableTrainerLocalization.loadLocalization(VocableTrainerSettingsIO.localisation);
        VocableTrainerPanel.changeColorStatic(
            VocableTrainerSettingsIO.colours[0], 
            VocableTrainerSettingsIO.colours[1], 
            VocableTrainerSettingsIO.colours[2], 
            VocableTrainerSettingsIO.colours[3], 
            VocableTrainerSettingsIO.colours[4]
        );

        contentPane = getContentPane();
        contentPane.setLayout(new CardLayout());

        // Generate all the panels used in the application
        generatePanels();

        contentPane.add(panels[1]);
        history.add(1);
    }

    /**
     * Generates and initializes all panels used in the application.
     */
    private void generatePanels() {
        panels[0] = new VocableTrainerMenuPanel(this);
        panels[1] = new VocableTrainerHomePanel(this);
        panels[3] = new VocableTrainerNewPanel(this);
        panels[4] = new VocableTrainerInfoPanel(this);
        panels[5] = new VocableTrainerStartPanel(this);
        panels[6] = new VocableTrainerCreditsPanel(this);
        panels[7] = new VocableTrainerSettingsPanel(this);
        panels[0].changeLocalisation(VocableTrainerSettingsIO.localisation);
    }

    /**
     * Changes the currently visible panel in the application.
     * 
     * @param panelIndex the index of the panel to switch to
     * @throws RuntimeException if the panel index is invalid
     */
    public void changePanel(int panelIndex) throws RuntimeException {
        if (panelIndex > 0) {
            if (history.size() > 0) {
                if (history.get(history.size() - 1) != panelIndex) {
                    panels[panelIndex].retrive();
                    contentPane.add(panels[panelIndex]);
                    contentPane.remove(panels[history.get(history.size() - 1)]);
                    history.add(panelIndex);
                }
            }
        } else if (panelIndex == 0) {
            contentPane.remove(panels[history.get(history.size() - 1)]);
            contentPane.add(panels[panelIndex]);
            contentPane.add(panels[history.get(history.size() - 1)]);
        } else if (panelIndex == -1) {
            if (history.size() > 1) {
                panels[history.get(history.size() - 2)].retrive();
                contentPane.add(panels[history.get(history.size() - 2)]);
                contentPane.remove(panels[history.get(history.size() - 1)]);
                history.remove(history.size() - 1);
            }
        } else if (panelIndex == -2) {
            contentPane.remove(panels[0]);
        } else if (panelIndex == -3) {
            contentPane.remove(panels[history.get(history.size() - 1)]);
        }
    }
    /**
     * Loads the bar image from the specified resource path.
     * This method ensures that the image is loaded only once to optimize performance.
     */
    protected void loadImage() {
        if (icon == null) {
            try (InputStream imgStream = getClass().getResourceAsStream(ICON_PATH)) {
                if (imgStream != null) {
                	icon = ImageIO.read(imgStream);
                }
                // If the image is not found, the barsImage remains null
            } catch (IOException e) {
                // Exception handling can be implemented here if needed
            }
        }
    }

    /**
     * Closes the application and releases all resources.
     */
    public void close() {
        setVisible(false);
        dispose();
        System.exit(0);
    }
}
