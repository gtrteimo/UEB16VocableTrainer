package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.tfobz.vocabletrainer.gui.panels.VocableTrainerPanel;

public class VocableTrainer2OptionDialog extends VocableTrainerInfoDialog {

	protected boolean answered;
	protected boolean answer;
	
	protected JLabel label;
	protected JButton confirmButton;
	protected JButton cancelButton;
	
	public VocableTrainer2OptionDialog(JFrame parent, String title, String text, String option1, String option2) {
		super(parent, title);
		
		setSize(getWidth()/3, getHeight()/3);
		setLocation((parent.getWidth() - 6)/2 - (parent.getWidth()/3 - 6)/2, (parent.getHeight() - 6)/2 - (parent.getHeight()/3 - 6)/2 );
        componentPanel.setBounds(0, 0, (parent.getWidth()/3 - 6), (parent.getHeight()/3 - 40));
		
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
		
		confirmButton = new JButton(option1);
        confirmButton.setBounds(componentPanel.getWidth()/2 + 16, componentPanel.getHeight() - componentPanel.getHeight()/6 - 16, componentPanel.getWidth()/2 - 32, componentPanel.getHeight() / 6 );
        confirmButton.setFont(new Font ("Arial", Font.PLAIN, confirmButton.getHeight()/2));
        confirmButton.setForeground(VocableTrainerPanel.textColor1);
        confirmButton.setBackground(VocableTrainerPanel.buttonBackgroundColor);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.addActionListener(e -> {
        	setVisible(false);
        	answered = true;
        	answer = true;
        });

        cancelButton = new JButton(option2);
        cancelButton.setBounds(16 , componentPanel.getHeight() - componentPanel.getHeight()/6 - 16, componentPanel.getWidth()/2 - 32, componentPanel.getHeight() / 6 );
        cancelButton.setFont(new Font ("Arial", Font.PLAIN, cancelButton.getHeight()/2));
        cancelButton.setForeground(VocableTrainerPanel.textColor1);
        cancelButton.setBackground(VocableTrainerPanel.buttonBackgroundColor);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
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
	public boolean getAnswered() {
		return answered;
	}
	public boolean getAnswer() {
		return answer;
	}

}
