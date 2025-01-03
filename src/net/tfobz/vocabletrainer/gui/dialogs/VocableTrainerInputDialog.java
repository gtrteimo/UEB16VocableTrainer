package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
		label.setText(text);
		label.setBounds(16, 16 , w - 32, h / 6 );
		label.setFont(new Font ("Arial", Font.PLAIN, label.getHeight()/2));
		label.setForeground(VocableTrainerPanel.C_nigth);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		input = new JTextField();
		input.setText(defaultText);
		input.setBounds(16, (int)(h * (1/3.0)), w - 32, h / 6 );
		input.setFont(new Font ("Arial", Font.PLAIN, input.getHeight()/2));
		input.setForeground(VocableTrainerPanel.C_nigth);
		input.setBackground(VocableTrainerPanel.C_platinum);
		input.setBorder(null);
		input.setHorizontalAlignment(SwingConstants.CENTER);
		
		confirmButton = new JButton("Confirm");
        confirmButton.setBounds(w/2 + 16, h - h/6 - 16, w/2 - 32, h / 6 );
        confirmButton.setFont(new Font ("Arial", Font.PLAIN, confirmButton.getHeight()/2));
        confirmButton.setForeground(VocableTrainerPanel.C_platinum);
        confirmButton.setBackground(VocableTrainerPanel.C_slateGray);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.addActionListener(e -> {
        	setVisible(false);
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(16 , h - h/6 - 16, w/2 - 32, h / 6 );
        cancelButton.setFont(new Font ("Arial", Font.PLAIN, cancelButton.getHeight()/2));
        cancelButton.setForeground(VocableTrainerPanel.C_platinum);
        cancelButton.setBackground(VocableTrainerPanel.C_slateGray);
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
