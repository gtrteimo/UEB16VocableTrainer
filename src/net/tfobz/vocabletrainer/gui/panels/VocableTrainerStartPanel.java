
package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.data.VocableTrainerRunSettings;
import net.tfobz.vocabletrainer.data.VocableTrainerRunSettings.TimeUnit;
import net.tfobz.vocabletrainer.gui.*;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInfoDialog;
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
		
		barPane.setTitle(VocableTrainerLocalization.MENU_START);
		
		panel.setLayout(null);
		options[0] = new JCheckBox(VocableTrainerLocalization.START_TIME_PER_CARD);
		options[0].setMnemonic('T');
		options[0].setSelected(true);
		options[1] = new JCheckBox(VocableTrainerLocalization.START_TOTAL_TIME);
		options[1].setMnemonic('O');
		options[1].setSelected(false);
		options[2] = new JCheckBox(VocableTrainerLocalization.START_CASE_SENSITIVE);
		options[2].setMnemonic('C');
		options[2].setSelected(true);
		options[3] = new JCheckBox(VocableTrainerLocalization.START_AMOUNT_OF_CARDS);
		options[3].setMnemonic('A');
		options[3].setSelected(false);
		options[4] = new JCheckBox(VocableTrainerLocalization.START_PRACTISE_RUN);
		options[4].setMnemonic('P');
		options[4].setSelected(false);

		for (int i = 0; i < options.length; i++) { 
			options[i].setForeground(textColor2);
			options[i].setBackground(mainBackgroundColor);
			options[i].setBorderPainted(false);
			options[i].setFocusPainted(false);
		}
		SpinnerListener spinnerListener = new SpinnerListener();
		for (int i = 0; i < optionSpinners.length; i++) {
			optionSpinners[i] = new JSpinner();
			optionSpinners[i].setForeground(textColor2);
			optionSpinners[i].setBackground(mainBackgroundColor);
			optionSpinners[i].setBorder(null);
			optionSpinners[i].addChangeListener(spinnerListener);
			JComponent editor = optionSpinners[i].getEditor();
			if (editor instanceof JSpinner.DefaultEditor) {
				JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
				defaultEditor.getTextField().addKeyListener(spinnerListener);
				defaultEditor.getTextField().setBackground(textColor1);
				defaultEditor.getTextField().setForeground(textColor2);
			}
			for (Component component : optionSpinners[i].getComponents()) {
                component.setBackground(textColor1); 
                component.setForeground(textColor2);
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
		setComboBox.setForeground(textColor2);
		setComboBox.setBackground(textColor1);
		setComboBox.setBorder(null);
		
		setComboBox.addActionListener(e -> retriveBoxes());
		
		boxes = VokabeltrainerDB.getFaecher(setComboBox.getSelectedIndex());
		if (boxes != null) {
			boxComboBoxes = new JComboBox<Fach>(boxes.toArray(new Fach[0]));
		} else {
			boxComboBoxes = new JComboBox<Fach>();
		}
		boxComboBoxes.setForeground(textColor2);
		boxComboBoxes.setBackground(textColor1);
		

		
		for (int i = 0; i < optionComboBoxesTime.length; i++) {			
			optionComboBoxesTime[i] = new JComboBox<TimeUnit>(TimeUnit.values());
			optionComboBoxesTime[i].setForeground(textColor2);
			optionComboBoxesTime[i].setBackground(textColor1);
			optionComboBoxesTime[i].setRenderer(new ListCellRenderer<TimeUnit>() {
				@Override
				public Component getListCellRendererComponent(JList<? extends TimeUnit> list, TimeUnit value, int index,
						boolean isSelected, boolean cellHasFocus) {
					if (value != null) {
						JLabel label = new JLabel();
						switch (value) {
							case Seconds:
								label.setText(VocableTrainerLocalization.SECONDS);
								break;
							case Minutes:
								label.setText(VocableTrainerLocalization.MINUTES);
								break;
							case Hours:
								label.setText(VocableTrainerLocalization.HOURS);
								break;
							default:
								label.setText(value.toString());
						}
						if (isSelected) {
							label.setBackground(list.getSelectionBackground());
							label.setForeground(list.getSelectionForeground());
						} else {
							label.setBackground(list.getBackground());
							label.setForeground(list.getForeground());
						}
						label.setOpaque(true);
						return label;
					}
					return null;
				}
			});

		}		
		
		optionComboBoxesTime[0].setEnabled(true);
		optionComboBoxesTime[1].setEnabled(false);
		        
		optionComboBoxesTime[0].setSelectedIndex(0);
		optionComboBoxesTime[1].setSelectedIndex(1);
		
		start = new JButton(VocableTrainerLocalization.BUTTON_START);
		start.setForeground(textColor1);
		start.setBackground(buttonBackgroundColor);
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
		VocableTrainerRunSettings settings = null;
		try {
			 settings = new VocableTrainerRunSettings((Fach) boxComboBoxes.getSelectedItem(), (Lernkartei) setComboBox.getSelectedItem());
		} catch (NullPointerException e) {
			if ((Lernkartei) setComboBox.getSelectedItem() == null) {
				new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_SELECT_SET).setVisible(true);
			}
			if ((Fach) boxComboBoxes.getSelectedItem() == null) {
				new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_SELECT_BOX).setVisible(true);
			}
			return;
		}
		
		List<Karte> k = VokabeltrainerDB.getKarten(((Fach) boxComboBoxes.getSelectedItem()).getNummer());
		System.out.println(settings.getBox().getNummer());
		if (settings.getBox().getNummer() == -11 || settings.getBox().getNummer() == -22 || (k != null && k.size() > 0 && (options[3].isSelected()?k.size() >= ((Integer)(optionSpinners[2].getValue())).intValue():true))) {
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
					VocableTrainerRunPanel run = new VocableTrainerRunPanel(vtf, settings);
					vtf.changePanel(-3);
					vtf.add(run);
				} catch (IllegalArgumentException e0) {
					if (options[3].isSelected()) {
						new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, "You are trying to start with "+((Integer)(optionSpinners[2].getValue())).intValue() + "crads while there are only "+k.size()+ " Cards in the Box").setVisible(true);;
					} else {
						new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, "The Box you are trying to start has 0 Cards in it. Either add some Cards or chose another Box").setVisible(true);;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else if (options[3].isSelected()) {
			String t = VocableTrainerLocalization.ERROR_TOO_MANY_CARDS;
			String t2 = t.substring(t.indexOf('}')+1);
			new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, t.substring(0, t.indexOf('{'))+((Integer)(optionSpinners[2].getValue())).intValue() + t.substring(t.indexOf('}') + 1, t2.indexOf('{')) + k.size() + t.substring(t2.indexOf('}') + 1) );
		} else {
			new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_NO_CARDS_IN_BOX).setVisible(true);;
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
            new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_SET_DROPPED).setVisible(true);
	    }
	}
	
	protected void retriveBoxes() {
	    Lernkartei l = (Lernkartei) setComboBox.getSelectedItem();
	    if (l != null) {
	        boxes = VokabeltrainerDB.getFaecher(l.getNummer());
	        boxComboBoxes.removeAllItems();
	        
	        boxComboBoxes.addItem(new Fach(-22, ""));  
	        boxComboBoxes.addItem(new Fach(-11, "")); 

	        if (boxes != null) {
	            for (Fach box : boxes) {
	                boxComboBoxes.addItem(box);
	            }
	        } else {
	            new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_SET_DROPPED).setVisible(true);
	        }
	    }

	    boxComboBoxes.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	            if (value instanceof Fach) {
	                Fach fach = (Fach) value;
	                if (fach.getNummer() == -11) {
	                    label.setText(VocableTrainerLocalization.START_BOX_DUE);
	                } else if (fach.getNummer() == -22) {
	                    label.setText(VocableTrainerLocalization.START_BOX_ALL);
	                } else {
	                    label.setText(fach.getBeschreibung() + ". "+VocableTrainerLocalization.START_BOX);
	                }
	            }
	            return label;
	        }
	    });
	}

	
	@Override
	public void setColors() {
		super.setColors();
		
		for (int i = 0; i < options.length; i++) { 
			options[i].setForeground(textColor2);
			options[i].setBackground(mainBackgroundColor);
		}
		
		for (int i = 0; i < optionSpinners.length; i++) {
			optionSpinners[i].setForeground(textColor2);
			optionSpinners[i].setBackground(mainBackgroundColor);
			JComponent editor = optionSpinners[i].getEditor();
			if (editor instanceof JSpinner.DefaultEditor) {
				JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
				defaultEditor.getTextField().setBackground(textColor1);
				defaultEditor.getTextField().setForeground(textColor2);
			}
			for (Component component : optionSpinners[i].getComponents()) {
                component.setBackground(textColor1); 
                component.setForeground(textColor2);
            }
		}
		
		setComboBox.setForeground(textColor2);
		setComboBox.setBackground(textColor1);
		
		boxComboBoxes.setForeground(textColor2);
		boxComboBoxes.setBackground(textColor1);
		
		for (int i = 0; i < optionComboBoxesTime.length; i++) {			
			optionComboBoxesTime[i].setForeground(textColor2);
			optionComboBoxesTime[i].setBackground(textColor1);
		}		
		
		start.setForeground(textColor1);
		start.setBackground(buttonBackgroundColor);
		
	}
	
	@Override
	public void setLocalisation() {
		super.setLocalisation();
		
		barPane.setTitle(VocableTrainerLocalization.MENU_START);
		
		options[0].setText(VocableTrainerLocalization.START_TIME_PER_CARD);
		options[1].setText(VocableTrainerLocalization.START_TOTAL_TIME);
		options[2].setText(VocableTrainerLocalization.START_CASE_SENSITIVE);
		options[3].setText(VocableTrainerLocalization.START_AMOUNT_OF_CARDS);
		options[4].setText(VocableTrainerLocalization.START_PRACTISE_RUN);
		
		start.setText(VocableTrainerLocalization.BUTTON_START);
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

		        if (value <= 0) {
		            spinner.setValue(1);
		        }
		    }
		
	    @Override
	    public void keyReleased(KeyEvent e) {
	        JTextField textField = (JTextField) e.getSource();
	        String text = textField.getText();
	        if (text.trim().replaceAll("\n", "").isEmpty()) {
                textField.setText(String.valueOf(1));
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

				