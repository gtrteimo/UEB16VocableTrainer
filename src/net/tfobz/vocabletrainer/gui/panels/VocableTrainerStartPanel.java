package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.data.VocableTrainerRunSettings;
import net.tfobz.vocabletrainer.data.VocableTrainerRunSettings.TimeUnit;
import net.tfobz.vocabletrainer.gui.*;
import net.tfobz.vokabeltrainer.model.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class VocableTrainerStartPanel extends VocableTrainerPanel {
	
	private List<Lernkartei> sets;
	private List<Fach> boxes;
	
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
		options[3].setSelected(false);
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
		optionSpinners[2].setEnabled(false);

		
		optionSpinners[0].setValue(30);
		optionSpinners[1].setValue(5);
		optionSpinners[2].setValue(10);
		
		sets = VokabeltrainerDB.getLernkarteien();
		if (sets != null) {
			List<String> stringsSets = sets.stream()
	                .map(Lernkartei::toString) // Extract the string using the getter
	                .collect(Collectors.toList());
			
			optionComboBoxes[0] = new JComboBox<String>(stringsSets.toArray(new String[0]));
		} else {
			optionComboBoxes[0] = new JComboBox<String>();
		}
		
		boxes = VokabeltrainerDB.getFaecher(optionComboBoxes[0].getSelectedIndex());
		if (boxes != null) {
			List<String> stringsBox = boxes.stream()
					.map(Fach::getBeschreibung)
	                .collect(Collectors.toList());	
			optionComboBoxes[1] = new JComboBox<String>(stringsBox.toArray(new String[0]));
		} else {
			optionComboBoxes[1] = new JComboBox<String>();
		}
                
		

		
		for (int i = 0; i < optionComboBoxes.length; i++) {			
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
					optionSpinners[0].setEnabled(true);
					optionComboBoxesTime[0].setEnabled(true);
				} else {
					optionSpinners[0].setEnabled(false);
					optionComboBoxesTime[0].setEnabled(false);
				}
		});
		options[1].addActionListener(e -> {
			if (options[1].isSelected()) {
				optionSpinners[1].setEnabled(true);
				optionComboBoxesTime[1].setEnabled(true);
			} else {
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
		VocableTrainerRunSettings settings;
		try {
			 settings = new VocableTrainerRunSettings((Fach) optionComboBoxesTime[1].getSelectedItem(), (Lernkartei) optionComboBoxesTime[0].getSelectedItem());
		} catch (Exception e) {
//			JOptionPane.showMessageDialog(this, "Please selecet a Set and Box", "Error", JOptionPane.ERROR_MESSAGE);
			//Debug and Testing
			settings = new VocableTrainerRunSettings();
		}
		
		if (options[0].isSelected()) {
			settings.setCardTimeLimit(((Integer)(optionSpinners[0].getValue())).intValue(), (TimeUnit)optionComboBoxesTime[0].getSelectedItem());
		} else if (options[1].isSelected()) {
			settings.setCardTimeLimit(((Integer)(optionSpinners[1].getValue())).intValue(), (TimeUnit)optionComboBoxesTime[1].getSelectedItem());
		}
		
		if (options[3].isSelected()) {
			settings.setCardLimit(((Integer)(optionSpinners[2].getValue())).intValue());
		}
		
		settings.setParcticeRun(options[4].isSelected());
		
		Thread t = new Thread(new StartThread(settings));
		
		try {
			vtf.changePanel(-3);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			t.join();
		} catch (InterruptedException e) {}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
				
		optionComboBoxes[0].setBounds((int)(panel.getWidth()/(32/3.0)), panel.getHeight()/50, (int)(panel.getWidth()/(1000/607.0)), panel.getHeight()/12);
		optionComboBoxes[0].setFont(new Font ("Arial", Font.BOLD, optionComboBoxes[0].getHeight()/2 + 5));
		
		optionComboBoxes[1].setBounds((int)(panel.getWidth()/(12/9.0)), panel.getHeight()/50, panel.getWidth()/5, panel.getHeight()/12);
		optionComboBoxes[1].setFont(new Font ("Arial", Font.BOLD, optionComboBoxes[1].getHeight()/2 + 5));
		
		for (int i = 0; i < options.length; i++) {
			options[i].setBounds((int)(panel.getWidth()/(32/3.0)), (int)(panel.getHeight()/(64/3.0)*(i*(5/2.0)+3)), panel.getHeight()/2, panel.getHeight()/16);
			options[i].setFont(new Font ("Arial", Font.PLAIN, options[i].getHeight()/2 + 5));
		}
		
		optionSpinners[0].setBounds((int)(panel.getWidth()/(12/6.0)), (int)(panel.getHeight()/(64/3.0)*(0*(5/2.0)+3)), panel.getWidth()/5, panel.getHeight()/16);
		optionSpinners[1].setBounds((int)(panel.getWidth()/(12/6.0)), (int)(panel.getHeight()/(64/3.0)*(1*(5/2.0)+3)), panel.getWidth()/5, panel.getHeight()/16);
		optionSpinners[2].setBounds((int)(panel.getWidth()/(12/9.0)), (int)(panel.getHeight()/(64/3.0)*(3*(5/2.0)+3)), panel.getWidth()/5, panel.getHeight()/16);
		
		for (int i = 0; i < optionSpinners.length; i++) {
			JComponent editor = optionSpinners[i].getEditor();
			if (editor instanceof JSpinner.DefaultEditor) {
				JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
				defaultEditor.getTextField().setFont(new Font ("Arial", Font.PLAIN, optionSpinners[i].getHeight()/2));
			}
		}
		
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
	        if (text.trim().replaceAll("\n", "").isEmpty()) {
                textField.setText(String.valueOf(0));
	        } else {
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
	    }

	    @Override
	    public void keyTyped(KeyEvent e) {}

	    @Override
	    public void keyPressed(KeyEvent e) {}
	}
	private class StartThread implements Runnable {

		private VocableTrainerRunSettings settings;
		
		public StartThread(VocableTrainerRunSettings settings) {
			this.settings = settings;
		}
		
		@Override
		public void run() {
			VocableTrainerRunPanel run = new VocableTrainerRunPanel(vtf, settings);
		}
		
	}
}
