package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.*;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.panels.VocableTrainerPanel;

/**
 * A dialog displaying information to the user with formatted text and a close button.
 */
@SuppressWarnings("serial")
public class VocableTrainerInfoDialog extends JDialog {
	
	/** Panel to hold all components of the dialog */
	protected JPanel componentPanel;
	
	/** Label to display the formatted informational text */
	private JLabel label;
	
	/**
	 * Constructs an information dialog with the specified title and text.
	 * Automatically formats the text to fit within the dialog bounds.
	 * 
	 * @param parent the parent frame of the dialog
	 * @param title the title of the dialog
	 * @param text the informational text to be displayed
	 */
	public VocableTrainerInfoDialog(JFrame parent, String title, String text) {
		super(parent, title, true);
		
		initializeDialog(parent);
		
		label = new JLabel();
		label.setBounds(8, 8, componentPanel.getWidth() - 16, componentPanel.getHeight() - 16);
		label.setForeground(VocableTrainerPanel.textColor2);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.TOP);
		
		setFormattedText(label, text, componentPanel.getWidth() - 16, componentPanel.getHeight() - 16);
		
		JButton close = createCloseButton();
		componentPanel.add(label);
		componentPanel.add(close);
		
		add(componentPanel);
	}

	/**
	 * Initializes the dialog properties and the main component panel.
	 * 
	 * @param parent the parent frame of the dialog
	 */
	private void initializeDialog(JFrame parent) {
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(parent.getWidth()/2, parent.getHeight()/2);
//		setLocation(parent.getX() + parent.getWidth()/2 - getWidth()/2, parent.getY());
		setLocationRelativeTo(parent);
		
		componentPanel = new JPanel();
		componentPanel.setLayout(null);
		componentPanel.setBackground(VocableTrainerPanel.mainBackgroundColor);
		componentPanel.setBounds(0, 0, parent.getWidth()/2 - 6, parent.getHeight()/2 - 40);
	}

	/**
	 * Creates and returns a close button for the dialog.
	 * 
	 * @return a JButton configured as a close button
	 */
	private JButton createCloseButton() {
		JButton close = new JButton(VocableTrainerLocalization.DIALOG_INFO_CLOSE);
		close.setBounds(16, componentPanel.getHeight() - componentPanel.getHeight() / 6 - 16, componentPanel.getWidth() - 32, componentPanel.getHeight() / 6);
		close.setFont(new Font("Arial", Font.PLAIN, close.getHeight() / 2));
		close.setForeground(VocableTrainerPanel.textColor1);
		close.setBackground(VocableTrainerPanel.buttonBackgroundColor);
		close.setFocusPainted(false);
		close.setBorderPainted(false);
		close.addActionListener(e -> closeDialog());
		return close;
	}

	/**
	 * Sets the text of the label, formatting it to fit within the specified bounds.
	 * 
	 * @param label the label to set the text for
	 * @param text the text to be formatted
	 * @param maxWidth the maximum width of the text
	 * @param maxHeight the maximum height of the text
	 */
	private void setFormattedText(JLabel label, String text, int maxWidth, int maxHeight) {
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
	 * Closes the dialog and releases resources.
	 */
	public void closeDialog() {
		setVisible(false);
		dispose();
	}

	/**
	 * Constructs an information dialog with the specified title.
	 * 
	 * @param parent the parent frame of the dialog
	 * @param title the title of the dialog
	 */
	public VocableTrainerInfoDialog(JFrame parent, String title) {
		super(parent, title, true);
		initializeDialog(parent);
	}
}
