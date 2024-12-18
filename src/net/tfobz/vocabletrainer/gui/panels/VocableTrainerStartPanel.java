package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.data.VocableTrainerRunSettingsData;
import net.tfobz.vocabletrainer.data.VocableTrainerRunSettingsData.TimeUnit;
import net.tfobz.vocabletrainer.gui.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class VocableTrainerStartPanel extends VocableTrainerPanel {
	
	private JCheckBox[] options = new JCheckBox[5];
	private JSpinner[] optionSpinners = new JSpinner[3];
	private JComboBox<String>[] optionComboBoxes = new JComboBox[2];
	private JComboBox<TimeUnit>[] optionComboBoxesTime = new JComboBox[2];
	private JButton start;
	
	public VocableTrainerStartPanel (VocableTrainerFrame vtf) {
		super(vtf);
		
		panel.loadImage();
		
		barPane.setTitle("Start");
		
		panel.setLayout(null);
				
		options[0] = new JCheckBox("Time per card");
		options[0].setSelected(true);
		options[1] = new JCheckBox("Total time");
		options[1].setSelected(false);
		options[2] = new JCheckBox("Case sensitive");
		options[2].setSelected(true);
		options[3] = new JCheckBox("Amount of cards");
		options[3].setSelected(true);
		options[4] = new JCheckBox("Practice Run");
		options[4].setSelected(false);

		for (int i = 0; i < options.length; i++) { 
			options[i].setForeground(C_nigth);
			options[i].setBackground(C_powderBlue);
		}
		SpinnerListener spinnerListener = new SpinnerListener();
		for (int i = 0; i < optionSpinners.length; i++) {
			optionSpinners[i] = new JSpinner();
			optionSpinners[i].setForeground(C_nigth);
			optionSpinners[i].setBackground(C_powderBlue);
			optionSpinners[i].addChangeListener(spinnerListener);
			JComponent editor = optionSpinners[i].getEditor();
			if (editor instanceof JSpinner.DefaultEditor) {
				JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
				defaultEditor.getTextField().addKeyListener(spinnerListener);
			}
		}
		optionSpinners[0].setEnabled(true);
		optionSpinners[1].setEnabled(false);
		optionSpinners[2].setEnabled(true);

		
		optionSpinners[0].setValue(30);
		optionSpinners[1].setValue(5);
		optionSpinners[2].setValue(10);
		
		for (int i = 0; i < optionComboBoxes.length; i++) {
			optionComboBoxes[i] = new JComboBox<String>();
			optionComboBoxesTime[i] = new JComboBox<TimeUnit>(TimeUnit.values());
			optionComboBoxes[i].setForeground(C_nigth);
			optionComboBoxes[i].setBackground(C_platinum);
			optionComboBoxesTime[i].setForeground(C_nigth);
			optionComboBoxesTime[i].setBackground(C_platinum);
		}
		
		optionComboBoxesTime[0].setEnabled(true);
		optionComboBoxesTime[1].setEnabled(false);
		        
		optionComboBoxesTime[0].setSelectedItem(TimeUnit.Seconds);
		optionComboBoxesTime[1].setSelectedItem(TimeUnit.Minutes);
		
		start = new JButton("Start");
		start.setForeground(C_nigth);
		start.setBackground(C_platinum);
		start.setFocusPainted(false);
		start.setBorderPainted(false);
		
		options[0].addActionListener(e -> {
				if (options[0].isSelected()) {
					options[1].setEnabled(false);
					optionSpinners[0].setEnabled(true);
					optionComboBoxesTime[0].setEnabled(true);
				} else {
					options[1].setEnabled(true);
					optionSpinners[0].setEnabled(false);
					optionComboBoxesTime[0].setEnabled(false);
				}
		});
		options[1].addActionListener(e -> {
			if (options[1].isSelected()) {
				options[0].setEnabled(false);
				optionSpinners[1].setEnabled(true);
				optionComboBoxesTime[1].setEnabled(true);
			} else {
				options[0].setEnabled(true);
				optionSpinners[1].setEnabled(false);
				optionComboBoxesTime[1].setEnabled(false);
			}
		});
		options[3].addActionListener(e -> {
			if (options[3].isSelected()) {
				optionSpinners[2].setEnabled(true);
			} else {
				optionSpinners[2].setEnabled(false);
			}
		});
		
		start.addActionListener(e -> startRun());
		
		for (int i = 0; i < options.length; i++) { 
			panel.add(options[i]);
		}
		
		for (int i = 0; i < optionSpinners.length; i++) {
			panel.add(optionSpinners[i]);
		}
		for (int i = 0; i < optionComboBoxes.length; i++) {
			panel.add(optionComboBoxes[i]);
			panel.add(optionComboBoxesTime[i]);
		}
		
		panel.add(start);
		
		add(barPane);
		add(panel);
		
	}
	
	private void startRun() {
		VocableTrainerRunSettingsData settings = new VocableTrainerRunSettingsData();
		settings.timePerCardState = options[0].isSelected();
		settings.timeForCardsState = options[1].isSelected();
		settings.caseSensitiveState = options[2].isSelected();
		settings.cardLimitState = options[3].isSelected();
		settings.practiceRunState = options[4].isSelected();
		
		settings.timePerCard = ((Integer)(optionSpinners[0].getValue())).intValue();
		settings.timeForCards = ((Integer)(optionSpinners[1].getValue())).intValue();
		settings.amountOfCards = ((Integer)(optionSpinners[2].getValue())).intValue();
		
		settings.timeUnit1 = (TimeUnit)optionComboBoxesTime[0].getSelectedItem();
		settings.timeUnit2 = (TimeUnit)optionComboBoxesTime[1].getSelectedItem();
		
		//TODO call run
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < options.length; i++) {
			options[i].setBounds((int)(panel.getWidth()/(32/3.0)), (int)(panel.getHeight()/(64/3.0)*(i*(5/2.0)+3)), panel.getHeight()/2, panel.getHeight()/16);
			options[i].setFont(new Font ("Arial", Font.PLAIN, options[i].getHeight()/2 + 5));
		}
		
		optionSpinners[0].setBounds((int)(panel.getWidth()/(12/6.0)), (int)(panel.getHeight()/(64/3.0)*(0*(5/2.0)+3)), panel.getWidth()/5, panel.getHeight()/16);
		optionSpinners[1].setBounds((int)(panel.getWidth()/(12/6.0)), (int)(panel.getHeight()/(64/3.0)*(1*(5/2.0)+3)), panel.getWidth()/5, panel.getHeight()/16);
		optionSpinners[2].setBounds((int)(panel.getWidth()/(12/9.0)), (int)(panel.getHeight()/(64/3.0)*(3*(5/2.0)+3)), panel.getWidth()/5, panel.getHeight()/16);
		
		optionSpinners[0].setFont(new Font ("Arial", Font.PLAIN, optionSpinners[0].getHeight()/2));
		optionSpinners[1].setFont(new Font ("Arial", Font.PLAIN, optionSpinners[1].getHeight()/2));
		optionSpinners[2].setFont(new Font ("Arial", Font.PLAIN, optionSpinners[2].getHeight()/2));
		
		optionComboBoxesTime[0].setBounds((int)(panel.getWidth()/(12/9.0)), (int)(panel.getHeight()/(64/3.0)*(0*(5/2.0)+3)), panel.getWidth()/5, panel.getHeight()/16);
		optionComboBoxesTime[1].setBounds((int)(panel.getWidth()/(12/9.0)), (int)(panel.getHeight()/(64/3.0)*(1*(5/2.0)+3)), panel.getWidth()/5, panel.getHeight()/16);
		
		optionComboBoxesTime[0].setFont(new Font ("Arial", Font.PLAIN, optionComboBoxesTime[0].getHeight()/2));
		optionComboBoxesTime[1].setFont(new Font ("Arial", Font.PLAIN, optionComboBoxesTime[1].getHeight()/2));
		
		start.setBounds(16, panel.getHeight()/4*3, panel.getWidth()-32, panel.getHeight()/4-16);
		start.setFont(new Font ("Arial", Font.BOLD, start.getHeight()/2));
	}
	private class SpinnerListener implements KeyListener, ChangeListener {
		
		 @Override
		    public void stateChanged(ChangeEvent e) {
		        JSpinner spinner = (JSpinner) e.getSource();
		        int value = (int) spinner.getValue();

		        if (value < 0) {
		            spinner.setValue(0);
		        }
		    }
		
	    @Override
	    public void keyReleased(KeyEvent e) {
	        JTextField textField = (JTextField) e.getSource();
	        String text = textField.getText();

	        try {
	            int intValue = Integer.parseInt(text);

	            if (intValue < 1) {
	                intValue = Math.abs(intValue);
	                textField.setText(String.valueOf(intValue));
	            }
	        } catch (NumberFormatException ex) {
	            String sanitized = text.replaceAll("[^0-9]", "");
	            textField.setText(sanitized);
	        }
	    }

	    @Override
	    public void keyTyped(KeyEvent e) {}

	    @Override
	    public void keyPressed(KeyEvent e) {}
	}
}
