package net.tfobz.vocabletrainer.gui.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import net.tfobz.vocabletrainer.data.VocableTrainerIO;
import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInfoDialog;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInputDialog;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerNewSetDialog;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainer2OptionDialog;
import net.tfobz.vokabeltrainer.model.*;

@SuppressWarnings("serial")
public class VocableTrainerNewPanel extends VocableTrainerPanel {
	
	List<Lernkartei> sets;
	
	private JButton newSet;
	private JButton impo;
	private JButton expo;
    private JComboBox<Lernkartei> comboBox;
    private JButton renameButton;
    private JButton deleteButton;
	private JButton save;
	private JLabel label1, label2;
	private JTextField text1, text2;
	
	public VocableTrainerNewPanel (VocableTrainerFrame vtf) {
		super(vtf);
		
		panel.setLayout(null);
		
		barPane.setTitle(VocableTrainerLocalization.MENU_NEW);
		
		newSet = new JButton();
		newSet.setForeground(textColor1);
		newSet.setBackground(buttonBackgroundColor);
		newSet.setBorderPainted(false);
		newSet.setFocusPainted(false);
		newSet.setHorizontalAlignment(SwingConstants.CENTER);
		newSet.setMnemonic('N');
		
		impo = new JButton();
		impo.setForeground(textColor1);
		impo.setBackground(buttonBackgroundColor);
		impo.setBorderPainted(false);
		impo.setFocusPainted(false);
		impo.setHorizontalAlignment(SwingConstants.CENTER);
		impo.setMnemonic('I');
		
		expo = new JButton();
		expo.setForeground(textColor1);
		expo.setBackground(buttonBackgroundColor);
		expo.setBorderPainted(false);
		expo.setFocusPainted(false);
		expo.setHorizontalAlignment(SwingConstants.CENTER);
		expo.setMnemonic('E');
		
		comboBox = new JComboBox<>();
		comboBox.setForeground(textColor2);
		comboBox.setBackground(textColor1);
		comboBox.setBorder(null);
	
		renameButton = new JButton();
        renameButton.setFocusPainted(false);
        renameButton.setBorderPainted(false);
        renameButton.setForeground(textColor1);
        renameButton.setBackground(buttonBackgroundColor);
        renameButton.setMnemonic('R');
        
        deleteButton = new JButton();
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setForeground(textColor1);
        deleteButton.setBackground(buttonBackgroundColor);
        deleteButton.setMnemonic('D');
        renameButton.addActionListener(e -> {
            Lernkartei s = (Lernkartei) comboBox.getSelectedItem();
            if (s != null) {
                VocableTrainerInputDialog d = new VocableTrainerInputDialog(vtf, VocableTrainerLocalization.DIALOG_INPUT_RENAME,VocableTrainerLocalization.DIALOG_INPUT_QUESTION, s.getBeschreibung());
                d.setVisible(true);
                if (d.getInput() != null && !d.getInput().trim().isEmpty()) {
                    s.setBeschreibung(d.getInput());
                    VokabeltrainerDB.aendernLernkartei(s);
                    retrive();
                }
                d.closeDialog();
            }
        });
        deleteButton.addActionListener(e -> {
	        Lernkartei s = (Lernkartei) comboBox.getSelectedItem();
	        if (s != null) {
	            VocableTrainer2OptionDialog d = new VocableTrainer2OptionDialog(vtf, VocableTrainerLocalization.DIALOG_TWO_OPTION_DELETE_TITLE, VocableTrainerLocalization.DIALOG_TWO_OPTION_DELETE_QUESTION, VocableTrainerLocalization.DIALOG_TWO_OPTION_DELETE_CONFIRM, VocableTrainerLocalization.DIALOG_TWO_OPTION_DELETE_CANCEL);
	            d.setVisible(true);
	            if (d.getAnswer()) {
	                VokabeltrainerDB.loeschenLernkartei(s.getNummer());
	                retrive();
	                repaint();
	            }
	            d.closeDialog();
	        }
        });
		
		label1 = new JLabel();
		label1.setForeground(textColor2);
		label1.setHorizontalAlignment(SwingConstants.CENTER);

		
		label2 = new JLabel();
		label2.setForeground(textColor2);
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		
		text1 = new JTextField();
		text1.setForeground(textColor2);
		text1.setBackground(textColor1);
		text1.setBorder(null);
		text1.setHorizontalAlignment(SwingConstants.CENTER);
		
		text2 = new JTextField();
		text2.setForeground(textColor2);
		text2.setBackground(textColor1);
		text2.setBorder(null);
		text2.setHorizontalAlignment(SwingConstants.CENTER);
		
		save = new JButton();
		save.setForeground(textColor1);
		save.setBackground(buttonBackgroundColor);
		save.setFocusPainted(false);
		save.setBorderPainted(false);
		save.setMnemonic('S');
		
		retrive();
		
		comboBox.addActionListener(e -> retriveLabels());
		
		newSet.addActionListener(e -> newSet());
		
		impo.addActionListener(e -> VocableTrainerIO.Import(vtf, (Lernkartei)comboBox.getSelectedItem()));
		expo.addActionListener(e -> VocableTrainerIO.Export(vtf, (Lernkartei)comboBox.getSelectedItem()));
		
		save.addActionListener(e -> newCard());
		
		if ((Lernkartei)comboBox.getSelectedItem() == null) {
			label1.setEnabled(false);
			label2.setEnabled(false);
			text1.setEnabled(false);
			text2.setEnabled(false);
			save.setEnabled(false);
		}
		
		panel.add(newSet);
		panel.add(impo);
		panel.add(expo);
		panel.add(comboBox);
		panel.add(renameButton);
		panel.add(deleteButton);
		panel.add(label1);
		panel.add(label2);
		panel.add(text1);
		panel.add(text2);
		panel.add(save);
		
		this.add(barPane);
		this.add(panel);
	}
	
	
	
	private void newSet () {
		VocableTrainerNewSetDialog nameDialog = new VocableTrainerNewSetDialog (vtf);
		nameDialog.setVisible(true);
		
        Lernkartei input = nameDialog.getInput();
        if (input != null && input.getBeschreibung() != null &&!input.getBeschreibung().isEmpty() && !input.getWortEinsBeschreibung().isEmpty() && !input.getWortZweiBeschreibung().isEmpty() &&!nameDialog.sqlInjection(input)) {
            Lernkartei newSet = input;
            int result = VokabeltrainerDB.hinzufuegenLernkartei(newSet);
            if (result == 0) {
                sets.add(newSet);
                comboBox.addItem(newSet);
            } else {
                JOptionPane.showMessageDialog(this, "There was an Error while writing the Set to the Database!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
        	if (VocableTrainerNewSetDialog.sqlInjection(input)) {
        		System.err.println("HELP, SQL Injection");
        	}
        }
        nameDialog.closeDialog();
        retrive();
	}
	
	private void newCard() {
	    Lernkartei s = (Lernkartei) comboBox.getSelectedItem();

	    if (s != null) {
	        String input1 = text1.getText().trim();
	        String input2 = text2.getText().trim();

	        if (!input1.isEmpty() && !input2.isEmpty()) {
	            // Check for existing Fächer in the selected Lernkartei
	            List<Fach> faecher = VokabeltrainerDB.getFaecher(s.getNummer());

	            if (faecher == null) {
	                new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_DATABASE_DROPPED).setVisible(true);
	                return;
	            }

	            if (faecher.isEmpty()) {
	                // Create a new Fach if none exist
	                Fach newFach = new Fach();
	                newFach.setBeschreibung("1");
	                int result = VokabeltrainerDB.hinzufuegenFach(s.getNummer(), newFach);
	                if (result != 0) {
	                    new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_NEW_BOX_CREATION_FAIL).setVisible(true);
	                    return;
	                }
	                faecher = VokabeltrainerDB.getFaecher(s.getNummer());
	            }

	            // Add the new card to the first Fach
	            Fach firstFach = faecher.get(0);
	            Karte c = new Karte(-1, input1, input2, s.getRichtung(), s.getGrossKleinschreibung());
	            c.setFnummer(firstFach.getNummer());

	            int result = VokabeltrainerDB.hinzufuegenKarte(s.getNummer(), c);

	            switch (result) {
	                case 0:
	                    // Success: Reset the input fields and update the UI
	                    text1.setText("");
	                    text2.setText("");
	                    vtf.changePanel(-1);
	                    break;
	                case -5:
	                    new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_IMPORT_DUPLICATE_CARD).setVisible(true);
	                    break;
	                default:
	                    new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_NEW_CARD_CREATION_FAIL).setVisible(true);
	                    break;
	            }
	        } else {
	        	String e = VocableTrainerLocalization.ERROR_NEW_ENTER_DATA;
	        	String e1 = e.substring(e.indexOf('}')+1, e.indexOf('{'));
	        	e.replaceFirst("}", "");
	            new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR,  s.getWortEinsBeschreibung() + e1 + s.getWortZweiBeschreibung() + e.substring(e.indexOf('}'))).setVisible(true);
	        }
	    } else {
	        new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_START_SELECT_SET).setVisible(true);
	    }
	}

	@Override
	public void retrive () {
		sets = VokabeltrainerDB.getLernkarteien();
	    comboBox.removeAllItems();
	    if (sets != null) {
		    for (Lernkartei set : sets) {
		        comboBox.addItem(set);
		    }
		    retriveLabels();
	    } else {
            new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_DATABASE_DROPPED).setVisible(true);
	    }
	}
	
	protected void retriveLabels () {
		Lernkartei temp = (Lernkartei)comboBox.getSelectedItem();
		if (temp != null) {
			label1.setText(temp.getWortEinsBeschreibung());
			label2.setText(temp.getWortZweiBeschreibung());
			
			label1.setEnabled(true);
			label2.setEnabled(true);
			text1.setEnabled(true);
			text2.setEnabled(true);
			save.setEnabled(true);
		}
	}
	
	@Override
	public void setColors() {
		super.setColors();
		
		newSet.setForeground(textColor1);
		newSet.setBackground(buttonBackgroundColor);
		
		impo.setForeground(textColor1);
		impo.setBackground(buttonBackgroundColor);
		
		expo.setForeground(textColor1);
		expo.setBackground(buttonBackgroundColor);
		
		comboBox.setForeground(textColor2);
		comboBox.setBackground(textColor1);
		
        renameButton.setForeground(textColor1);
        renameButton.setBackground(buttonBackgroundColor);
        
        deleteButton.setForeground(textColor1);
        deleteButton.setBackground(buttonBackgroundColor);
        
		label1.setForeground(textColor2);
		
		label2.setForeground(textColor2);
		
		text1.setForeground(textColor2);
		text1.setBackground(textColor1);
		
		text2.setForeground(textColor2);
		text2.setBackground(textColor1);
		
		save.setForeground(textColor1);
		save.setBackground(buttonBackgroundColor);
	}
	
	@Override
	public void setLocalisation() {
		barPane.setTitle(VocableTrainerLocalization.NEW_NAME);
		newSet.setText(VocableTrainerLocalization.NEW_NEW_SET);
		impo.setText(VocableTrainerLocalization.NEW_IMPORT);
		expo.setText(VocableTrainerLocalization.NEW_EXPORT);
		renameButton.setText(VocableTrainerLocalization.NEW_RENAME);
        deleteButton.setText(VocableTrainerLocalization.NEW_DELETE);
        save.setText(VocableTrainerLocalization.NEW_SAVE);
	}
	
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = panel.getWidth();
        int h = panel.getHeight();
        
        
		newSet.setBounds(w/16+32, h / 500 + 8, w / 6 + 45 , h / 16 + 16);
		newSet.setFont(new Font ("Arial", Font.BOLD, (int)(newSet.getHeight()/1.5)+1));
		
		impo.setBounds(w - 32 - 2*w / 5, h / 500 + 8, w / 5 , h / 16 + 16);
		impo.setFont(new Font ("Arial", Font.BOLD, (int)(impo.getHeight()/1.5)+1));
		
		expo.setBounds(w - 16 - w / 5, h / 500 + 8, w / 5 , h / 16 + 16);
		expo.setFont(new Font ("Arial", Font.BOLD, (int)(expo.getHeight()/1.5)+1));
		
		comboBox.setBounds(16, h / 8 + 16, w*7/10, h / 8);
	    comboBox.setFont(new Font("Arial", Font.PLAIN, comboBox.getHeight() / 2 + 1));
	    	    
        renameButton.setBounds(w*7/10 + 48, h / 8 + 16, w - w*7/10 - 64, h / 16 - 2);
        renameButton.setFont(new Font("Arial", Font.PLAIN, renameButton.getHeight() / 2 + 1));
        
        deleteButton.setBounds(w*7/10 + 48, h / 8 + 16 + h / 16 + 2, w - w*7/10 - 64, h / 16 - 2);
        deleteButton.setFont(new Font("Arial", Font.PLAIN, deleteButton.getHeight() / 2 + 1));
	    
	    label1.setBounds(16, (int)(h/8.0*3) + 16, w/2 -32, h / 8);
	    label1.setFont(new Font("Arial", Font.PLAIN, label1.getHeight() / 2 + 1));
	    
	    label2.setBounds(w/2+16, (int)(h/8.0*3) + 16, w/2 -32, h / 8);
	    label2.setFont(new Font("Arial", Font.PLAIN, label2.getHeight() / 2 + 1));
	    
	    text1.setBounds(16, h/2 + 16, w/2 -32, h / 8);
	    text1.setFont(new Font("Arial", Font.PLAIN, text1.getHeight() / 2 + 1));
	    
	    text2.setBounds(w/2+16, h/2 + 16, w/2 -32, h / 8);
	    text2.setFont(new Font("Arial", Font.PLAIN, text2.getHeight() / 2 + 1));
	    
	    save.setBounds(16, panel.getHeight()/4*3, panel.getWidth()-32, panel.getHeight()/4-16);
		save.setFont(new Font ("Arial", Font.BOLD, save.getHeight()/2 + 1));
    }    	
    	
}
