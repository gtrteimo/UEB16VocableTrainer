package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.panels.VocableTrainerPanel;

/**
 * Represents an input dialog for the Vocable Trainer application.
 * This dialog allows users to input text with options to confirm or cancel the action.
 * It extends the VocableTrainerInfoDialog to inherit common dialog functionalities.
 */
public class VocableTrainerInputDialog extends VocableTrainerInfoDialog {
    
    private static final long serialVersionUID = 1L;

    /** Label to display the prompt text */
    protected JLabel label;
    
    /** Text field for user input */
    protected JTextField input;
    
    /** Button to confirm the input */
    protected JButton confirmButton;
    
    /** Button to cancel the input */
    protected JButton cancelButton;
    
    /**
     * Constructs a new VocableTrainerInputDialog.
     *
     * @param parent The parent JFrame of this dialog
     * @param title The title of the dialog window
     * @param text The prompt text to display to the user
     * @param defaultText The default text to display in the input field
     */
    public VocableTrainerInputDialog(JFrame parent, String title, String text, String defaultText) {
        super(parent, title);
        
        // Set dialog size to one-third of the parent size
        setSize(getWidth() / 3, getHeight() / 3);
        
        // Center the dialog relative to the parent frame
        setLocation(
            (parent.getWidth() - 6) / 2 - (parent.getWidth() / 3 - 6) / 2,
            (parent.getHeight() - 6) / 2 - (parent.getHeight() / 3 - 6) / 2
        );
        
        // Configure the component panel size and position
        componentPanel.setBounds(0, 0, (parent.getWidth() / 3 - 6), (parent.getHeight() / 3 - 40));
        
        int panelWidth = componentPanel.getWidth();
        int panelHeight = componentPanel.getHeight();
        
        // Initialize and configure the label
        label = new JLabel();
        label.setBounds(8, 8, panelWidth - 16, panelHeight - 16);
        label.setForeground(VocableTrainerPanel.textColor2);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        
        // Dynamically adjust font size to fit the label within the panel
        Font font = new Font("Arial", Font.PLAIN, label.getHeight() / 10 + 1);
        int maxWidth = panelWidth - 16;
        int maxHeight = panelHeight - 16;
        FontMetrics metrics;
        
        while (true) {
            label.setFont(font);
            metrics = label.getFontMetrics(font);
            StringBuilder formattedText = new StringBuilder();
            int lineHeight = metrics.getHeight();
            int totalHeight = 0;
            boolean fits = true;
            StringBuilder line = new StringBuilder();
            
            // Split the prompt text into words and format into lines
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
        
        // Initialize and configure the input text field
        input = new JTextField();
        input.setText(defaultText);
        input.setBounds(16, (int) (panelHeight * (1 / 3.0)), panelWidth - 32, panelHeight / 6);
        input.setFont(new Font("Arial", Font.PLAIN, input.getHeight() / 2));
        input.setForeground(VocableTrainerPanel.textColor2);
        input.setBackground(VocableTrainerPanel.textColor1);
        input.setBorder(null);
        input.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Initialize and configure the confirm button
        confirmButton = new JButton();
        confirmButton.setText(VocableTrainerLocalization.DIALOG_INPUT_CONFIRM);
        confirmButton.setBounds(panelWidth / 2 + 16, panelHeight - panelHeight / 6 - 16, panelWidth / 2 - 32, panelHeight / 6);
        confirmButton.setFont(new Font("Arial", Font.PLAIN, confirmButton.getHeight() / 2));
        confirmButton.setForeground(VocableTrainerPanel.textColor1);
        confirmButton.setBackground(VocableTrainerPanel.buttonBackgroundColor);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.addActionListener(e -> {
            setVisible(false);
        });

        // Initialize and configure the cancel button
        cancelButton = new JButton();
        cancelButton.setText(VocableTrainerLocalization.DIALOG_INPUT_CANCEL);
        cancelButton.setBounds(16, panelHeight - panelHeight / 6 - 16, panelWidth / 2 - 32, panelHeight / 6);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, cancelButton.getHeight() / 2));
        cancelButton.setForeground(VocableTrainerPanel.textColor1);
        cancelButton.setBackground(VocableTrainerPanel.buttonBackgroundColor);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.addActionListener(e -> closeDialog());
        
        // Add components to the panel
        componentPanel.add(label);
        componentPanel.add(input);
        componentPanel.add(confirmButton);
        componentPanel.add(cancelButton);
        
        // Add the component panel to the dialog
        add(componentPanel);
    }
    
    /**
     * Retrieves the text entered by the user in the input field.
     *
     * @return The input text as a String
     */
    public String getInput() {
        return input.getText();
    }
    
    /**
     * Closes the dialog by clearing the input field and disposing of the dialog window.
     */
    public void closeDialog() {
        input.setText("");
        setVisible(false);
        dispose();
    }
}
