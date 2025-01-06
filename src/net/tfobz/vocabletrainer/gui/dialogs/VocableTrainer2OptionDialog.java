package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.tfobz.vocabletrainer.gui.panels.VocableTrainerPanel;

public class VocableTrainer2OptionDialog extends VocableTrainerInfoDialog {

	protected boolean answer;
	
	protected JLabel label;
	protected JButton confirmButton;
	protected JButton cancelButton;
	
	public VocableTrainer2OptionDialog(JFrame parent, String title, String text, String option1, String option2) {
		super(parent, title);
		
		setSize(getWidth()/3, getHeight()/3);
		setLocation((parent.getWidth() - 6)/2 - (parent.getWidth()/3 - 6)/2, (parent.getHeight() - 6)/2 - (parent.getHeight()/3 - 6)/2 );
        componentPanel.setBounds(0, 0, (parent.getWidth()/3 - 6), (parent.getHeight()/3 - 40));
		
		label = new JLabel(text);
		label.setBounds(8,  componentPanel.getHeight()/3, componentPanel.getWidth() - 16, componentPanel.getHeight()/8);
		label.setFont(new Font ("Arial", Font.PLAIN, label.getHeight()*7/10 + 1));
		label.setForeground(VocableTrainerPanel.textColor2);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		confirmButton = new JButton(option1);
        confirmButton.setBounds(componentPanel.getWidth()/2 + 16, componentPanel.getHeight() - componentPanel.getHeight()/6 - 16, componentPanel.getWidth()/2 - 32, componentPanel.getHeight() / 6 );
        confirmButton.setFont(new Font ("Arial", Font.PLAIN, confirmButton.getHeight()/2));
        confirmButton.setForeground(VocableTrainerPanel.textColor1);
        confirmButton.setBackground(VocableTrainerPanel.buttonBackgroundColor);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.addActionListener(e -> {
        	setVisible(false);
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
        	answer = false;
        });
		
		componentPanel.add(label);
		componentPanel.add(cancelButton);
		componentPanel.add(confirmButton);
		
		add(componentPanel);
	}
	
	public boolean getAnswer() {
		return answer;
	}

}
