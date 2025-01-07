package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.panels.VocableTrainerPanel;

public class VocableTrainerInputDialog extends VocableTrainerInfoDialog {
	
	protected JLabel label;
	protected JTextField input;
	protected JButton confirmButton;
	protected JButton cancelButton;
	
	public VocableTrainerInputDialog (JFrame parent, String title, String text, String defaultText) {
		super(parent, title);
		
		setSize(getWidth()/3, getHeight()/3);
		setLocation((parent.getWidth() - 6)/2 - (parent.getWidth()/3 - 6)/2, (parent.getHeight() - 6)/2 - (parent.getHeight()/3 - 6)/2 );
        componentPanel.setBounds(0, 0, (parent.getWidth()/3 - 6), (parent.getHeight()/3 - 40));
		
		int w = componentPanel.getWidth();
        int h = componentPanel.getHeight();
		
        label = new JLabel();
		label.setBounds(8, 8, componentPanel.getWidth() - 16, componentPanel.getHeight() - 16);
		label.setForeground(VocableTrainerPanel.textColor2);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.TOP);
        
        Font font = new Font("Arial", Font.PLAIN, label.getHeight()/10+1);
		int maxWidth = componentPanel.getWidth() - 16;
		int maxHeight = componentPanel.getHeight() - 16;
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
		
		input = new JTextField();
		input.setText(defaultText);
		input.setBounds(16, (int)(h * (1/3.0)), w - 32, h / 6 );
		input.setFont(new Font ("Arial", Font.PLAIN, input.getHeight()/2));
		input.setForeground(VocableTrainerPanel.textColor2);
		input.setBackground(VocableTrainerPanel.textColor1);
		input.setBorder(null);
		input.setHorizontalAlignment(SwingConstants.CENTER);
		
		confirmButton = new JButton();
		confirmButton.setText(VocableTrainerLocalization.DIALOG_INPUT_CONFIRM);
        confirmButton.setBounds(w/2 + 16, h - h/6 - 16, w/2 - 32, h / 6 );
        confirmButton.setFont(new Font ("Arial", Font.PLAIN, confirmButton.getHeight()/2));
        confirmButton.setForeground(VocableTrainerPanel.textColor1);
        confirmButton.setBackground(VocableTrainerPanel.buttonBackgroundColor);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.addActionListener(e -> {
        	setVisible(false);
        });

        cancelButton = new JButton();
        cancelButton.setText(VocableTrainerLocalization.DIALOG_INPUT_CANCEL);
        cancelButton.setBounds(16 , h - h/6 - 16, w/2 - 32, h / 6 );
        cancelButton.setFont(new Font ("Arial", Font.PLAIN, cancelButton.getHeight()/2));
        cancelButton.setForeground(VocableTrainerPanel.textColor1);
        cancelButton.setBackground(VocableTrainerPanel.buttonBackgroundColor);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.addActionListener(e -> closeDialog());
        
        componentPanel.add(label);
        componentPanel.add(input);
        componentPanel.add(confirmButton);
        componentPanel.add(cancelButton);
        
        add(componentPanel);
	}
	
	public String getInput() {
		return input.getText();
	}
	public void closeDialog() {
		input.setText("");
		setVisible(false);
		dispose();
	}
}
