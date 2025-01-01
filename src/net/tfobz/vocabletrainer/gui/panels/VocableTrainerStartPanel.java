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
	private JComboBox<Lernkartei> setComboBox;
	private JComboBox<Fach> boxComboBoxes;
	private JComboBox<TimeUnit>[] optionComboBoxesTime = new JComboBox[2];
	private JButton start;
	
	public VocableTrainerStartPanel (VocableTrainerFrame vtf) {
		super(vtf);
		
		panel.loadImage();
		
		barPane.setTitle("Start");
		
		panel.setLayout(null);
				
		options[0] = new JCheckBox("Time per card");
		options[0].setMnemonic('T');
		options[0].setSelected(true);
		options[1] = new JCheckBox("Total time");
		options[1].setMnemonic('O');
		options[1].setSelected(false);
		options[2] = new JCheckBox("Case sensitive");
		options[2].setMnemonic('C');
		options[2].setSelected(true);
		options[3] = new JCheckBox("Amount of cards");
		options[3].setMnemonic('A');
		options[3].setSelected(false);
		options[4] = new JCheckBox("Practice Run");
		options[4].setMnemonic('P');
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
			optionSpinners[i].setBorder(null);
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
			setComboBox = new JComboBox<Lernkartei>(sets.toArray(new Lernkartei[0]));
		} else {
			setComboBox = new JComboBox<Lernkartei>();
		}
		setComboBox = new JComboBox<Lernkartei>(sets.toArray(new Lernkartei[0]));
		setComboBox.setForeground(C_nigth);
		setComboBox.setBackground(C_platinum);
		setComboBox.setBorder(null);
		
		setComboBox.addActionListener(e -> retriveBoxes());
		
		boxes = VokabeltrainerDB.getFaecher(setComboBox.getSelectedIndex());
		if (boxes != null) {
			boxComboBoxes = new JComboBox<Fach>(boxes.toArray(new Fach[0]));
		} else {
			boxComboBoxes = new JComboBox<Fach>();
		}
		boxComboBoxes.setForeground(C_nigth);
		boxComboBoxes.setBackground(C_platinum);
		

		
		for (int i = 0; i < optionComboBoxesTime.length; i++) {			
			optionComboBoxesTime[i] = new JComboBox<TimeUnit>(TimeUnit.values());
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
		start.setMnemonic('S');
		
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
		
		panel.add(setComboBox);
		panel.add(boxComboBoxes);
		
		panel.add(optionComboBoxesTime[0]);
		panel.add(optionComboBoxesTime[1]);
		
		panel.add(start);
		
		add(barPane);
		add(panel);
		
	}
	
	private void startRun() {
		//TODO this is not working properly! i marked the test constructor for the data structure as deprecated because i noticed you were using it (don`t do that). i think the setters works so idk where the problem is except that the total time limit is somehow set
		//Answer: just comment out the shit -> as written "//Debug and Testing" so not for release
		VocableTrainerRunSettings settings = null;
		try {
			 settings = new VocableTrainerRunSettings((Fach) boxComboBoxes.getSelectedItem(), (Lernkartei) setComboBox.getSelectedItem());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Please selecet a Set and Box", "Error", JOptionPane.ERROR_MESSAGE);
			//Debug and Testing
//			settings = new VocableTrainerRunSettings();
		}
		if (settings != null) {
			if (options[0].isSelected()) {
				settings.setCardTimeLimit(((Integer)(optionSpinners[0].getValue())).intValue(), (TimeUnit)optionComboBoxesTime[0].getSelectedItem());
			} 
			if (options[1].isSelected()) {
				settings.setCardTimeLimit(((Integer)(optionSpinners[1].getValue())).intValue(), (TimeUnit)optionComboBoxesTime[1].getSelectedItem());
			}
			
			if (options[3].isSelected()) {
				settings.setCardLimit(((Integer)(optionSpinners[2].getValue())).intValue());
			}
			
			settings.setParcticeRun(options[4].isSelected());
			
			
			try {
				vtf.changePanel(-3);
				VocableTrainerRunPanel run = new VocableTrainerRunPanel(vtf, settings);
				vtf.add(run);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void retrive() {
		sets = VokabeltrainerDB.getLernkarteien();
		setComboBox.removeAllItems();
	    if (sets != null) {
		    for (Lernkartei set : sets) {
		    	setComboBox.addItem(set);
		    }
		    retriveBoxes();
	    } else {
	    	//TODO how about if the program was used for the first time. change this message
            JOptionPane.showMessageDialog(this, "Looks like the Sets Database was droped", "Statement", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	protected void retriveBoxes() {
		Lernkartei l = (Lernkartei) setComboBox.getSelectedItem();
		if (l!=null) {
			boxes = VokabeltrainerDB.getFaecher(l.getNummer());
			boxComboBoxes.removeAllItems();
		    if (boxes != null) {
			    for (Fach box : boxes) {
			    	boxComboBoxes.addItem(box);
			    }
		    } else {
		    	//TODO how about if it was just created and is still empty. change this message
	            JOptionPane.showMessageDialog(this, "Looks like the Sets Database was droped", "Statement", JOptionPane.ERROR_MESSAGE);
		    }
		}
		
		boxComboBoxes.setRenderer(new DefaultListCellRenderer() {
		    @Override
		    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		        if (value instanceof Fach) {
		            label.setText(((Fach) value).getBeschreibung());
		        }
		        return label;
		    }
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
				
		setComboBox.setBounds((int)(panel.getWidth()/(32/3.0)), panel.getHeight()/50, (int)(panel.getWidth()/(1000/607.0)), panel.getHeight()/12);
		setComboBox.setFont(new Font ("Arial", Font.BOLD, setComboBox.getHeight()/2 + 5));
		
		boxComboBoxes.setBounds((int)(panel.getWidth()/(12/9.0)), panel.getHeight()/50, panel.getWidth()/5, panel.getHeight()/12);
		boxComboBoxes.setFont(new Font ("Arial", Font.BOLD, boxComboBoxes.getHeight()/2 + 5));
		
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
			
			
		}
		
	}
}

				