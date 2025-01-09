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

/**
 * VocableTrainerBarPanel is a GUI component that represents the menu bar
 * in the Vocable Trainer application. It displays the main title and an optional
 * subtitle, along with a decorative bar image.
 */
@SuppressWarnings("serial")
public class VocableTrainerBarPanel extends VocableTrainerPanel {

    // Reference to the source panel from which this bar panel is created
    private VocableTrainerPanel sourcePanel;

    // Path to the bar image resource
    private static final String BAR_PATH = "/net/tfobz/vocabletrainer/main/resources/icons/bars.png";
    private static Image barsImage;

    // Label to display the title text
    private JLabel title;
    // Optional subtitle text
    private String text;

    /**
     * Constructs a new VocableTrainerBarPanel with the specified source panel.
     *
     * @param sourcePanel the source VocableTrainerPanel from which this bar panel is created
     */
    public VocableTrainerBarPanel(VocableTrainerPanel sourcePanel) {
        super(sourcePanel);
        this.sourcePanel = sourcePanel;
        setLayout(null);
        setBackground(menuBarColor);

        title = new JLabel();

        loadImage();
        setLocalisation();
        title.setForeground(textColor1);
        title.setHorizontalAlignment(SwingConstants.LEFT);
        add(title);
    }

    /**
     * Sets the title text of the bar panel. If the provided text is non-empty,
     * it appends it to the main title separated by a hyphen.
     *
     * @param text the subtitle text to display alongside the main title
     */
    public void setTitle(String text) {
        this.text = text;
        if (text != null && !text.trim().replaceAll("\n", "").isEmpty()) {
            title.setText(VocableTrainerLocalization.MAIN_TITLE + " - " + text);
        } else {
            title.setText(VocableTrainerLocalization.MAIN_TITLE);
        }
    }

    /**
     * Updates the color scheme of the bar panel elements.
     * Sets the background color and the foreground color of the title label.
     */
    @Override
    public void setColors() {
        setBackground(menuBarColor);
        title.setForeground(textColor1);
    }

    /**
     * Applies localization settings to the bar panel.
     * Updates the title text based on the current localization.
     */
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

    /**
     * Loads the bar image from the specified resource path.
     * This method ensures that the image is loaded only once to optimize performance.
     */
    @Override
    protected void loadImage() {
        if (barsImage == null) {
            try (InputStream imgStream = getClass().getResourceAsStream(BAR_PATH)) {
                if (imgStream != null) {
                    barsImage = ImageIO.read(imgStream);
                }
                // If the image is not found, the barsImage remains null
            } catch (IOException e) {
                // Exception handling can be implemented here if needed
            }
        }
    }

    /**
     * Paints the component, including the bar image and the title label.
     * Adjusts the positioning and sizing of the title based on the panel's dimensions.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the bar image if it has been successfully loaded
        if (barsImage != null) {
            g.drawImage(barsImage, 9, 9, getHeight() - 18, getHeight() - 18, null);
        }

        // Position and size the title label dynamically based on the panel's dimensions
        title.setBounds(getHeight(), 9, getWidth() - getHeight() - 9, getHeight() - 18);
        title.setFont(new Font("Arial", Font.BOLD, (getHeight() - 18) / 2 + 7));
    }
}
