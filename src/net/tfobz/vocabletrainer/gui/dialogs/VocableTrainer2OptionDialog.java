package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import net.tfobz.vocabletrainer.gui.panels.VocableTrainerPanel;

/**
 * A dialog with two options that can be used to get a binary decision (Yes/No, Accept/Decline, etc.) from the user.
 */
public class VocableTrainer2OptionDialog extends VocableTrainerInfoDialog {

    /**
     * Indicates whether the dialog was answered.
     */
    protected boolean answered;

    /**
     * Stores the user's answer: true for confirm, false for cancel.
     */
    protected boolean answer;

    /**
     * Label for displaying the dialog text.
     */
    protected JLabel label;

    /**
     * Button for the confirm action.
     */
    protected JButton confirmButton;

    /**
     * Button for the cancel action.
     */
    protected JButton cancelButton;

    /**
     * Constructs a dialog with two options.
     *
     * @param parent the parent frame for this dialog
     * @param title the title of the dialog
     * @param text the text to be displayed in the dialog
     * @param option1 the label for the confirm button
     * @param option2 the label for the cancel button
     */
    public VocableTrainer2OptionDialog(JFrame parent, String title, String text, String option1, String option2) {
        super(parent, title);

        setSize(getWidth() * 2 / 3, getHeight() * 2 / 3);
        setLocation((parent.getWidth() - 6) / 2 - (parent.getWidth() / 3 - 6) / 2, (parent.getHeight() - 6) / 2 - (parent.getHeight() / 3 - 6) / 2);
        componentPanel.setBounds(0, 0, (parent.getWidth() / 3 - 6), (parent.getHeight() / 3 - 40));

        label = new JLabel();
        label.setBounds(8, 8, componentPanel.getWidth() - 16, componentPanel.getHeight() - 16);
        label.setForeground(VocableTrainerPanel.textColor2);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);

        adjustFontToFit(label, text, componentPanel.getWidth() - 16, componentPanel.getHeight() - 16);

        confirmButton = createButton(option1, componentPanel.getWidth() / 2 + 16, componentPanel.getHeight() - componentPanel.getHeight() / 6 - 16, componentPanel.getWidth() / 2 - 32, componentPanel.getHeight() / 6);
        confirmButton.addActionListener(e -> {
            setVisible(false);
            answered = true;
            answer = true;
        });

        cancelButton = createButton(option2, 16, componentPanel.getHeight() - componentPanel.getHeight() / 6 - 16, componentPanel.getWidth() / 2 - 32, componentPanel.getHeight() / 6);
        cancelButton.addActionListener(e -> {
            setVisible(false);
            answered = true;
            answer = false;
        });

        componentPanel.add(label);
        componentPanel.add(cancelButton);
        componentPanel.add(confirmButton);

        add(componentPanel);
    }

    /**
     * Adjusts the font size of the label to fit within the specified dimensions.
     *
     * @param label the label to adjust
     * @param text the text to display in the label
     * @param maxWidth the maximum width for the text
     * @param maxHeight the maximum height for the text
     */
    private void adjustFontToFit(JLabel label, String text, int maxWidth, int maxHeight) {
        Font font = new Font("Arial", Font.PLAIN, label.getHeight() / 10 + 1);
        FontMetrics metrics;

        while (true) {
            label.setFont(font);
            metrics = label.getFontMetrics(font);
            StringBuilder formattedText = new StringBuilder();
            int lineHeight = metrics.getHeight();
            int totalHeight = 0;
            boolean fits = true;
            StringBuilder line = new StringBuilder();

            for (String word : text.split(" ")) {
                if (metrics.stringWidth(line.toString() + word + " ") > maxWidth) {
                    if (line.length() > 0) {
                        formattedText.append(line).append("<br>");
                        totalHeight += lineHeight;
                        line.setLength(0);
                    }
                    if (totalHeight + lineHeight > maxHeight) {
                        fits = false;
                        break;
                    }
                    line.append(word).append(" ");
                } else {
                    line.append(word).append(" ");
                }
            }

            if (line.length() > 0) {
                formattedText.append(line);
                totalHeight += lineHeight;
            }

            if (fits && totalHeight <= maxHeight) {
                label.setText("<html><div style='text-align:center;'>" + formattedText.toString().trim() + "</div></html>");
                break;
            }

            font = new Font("Arial", Font.PLAIN, font.getSize() - 1);
        }
    }

    /**
     * Creates a button with specified properties.
     *
     * @param text the text for the button
     * @param x the x-coordinate of the button
     * @param y the y-coordinate of the button
     * @param width the width of the button
     * @param height the height of the button
     * @return the created button
     */
    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.PLAIN, height / 2));
        button.setForeground(VocableTrainerPanel.textColor1);
        button.setBackground(VocableTrainerPanel.buttonBackgroundColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    /**
     * Gets whether the dialog was answered.
     *
     * @return true if the dialog was answered, false otherwise
     */
    public boolean getAnswered() {
        return answered;
    }

    /**
     * Gets the user's answer.
     *
     * @return true for confirm, false for cancel
     */
    public boolean getAnswer() {
        return answer;
    }
}
