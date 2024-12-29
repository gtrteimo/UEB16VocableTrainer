package net.tfobz.vocabletrainer.gui.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vocabletrainer.gui.dialogs.InfoDialog;
import net.tfobz.vocabletrainer.gui.dialogs.NewSetDialog;
import net.tfobz.vokabeltrainer.model.*;

@SuppressWarnings("serial")
public class VocableTrainerNewPanel extends VocableTrainerPanel {
	
	List<Lernkartei> sets;
	
	private JButton newSet;
    private JComboBox<Lernkartei> comboBox;
	private JButton save;
	private JLabel label1, label2;
	private JTextField text1, text2;
	
	public VocableTrainerNewPanel (VocableTrainerFrame vtf) {
		super(vtf);
		
		panel.setLayout(null);
		
		barPane.setTitle("New");
		
		newSet = new JButton("New Set");
		newSet.setForeground(C_nigth);
		newSet.setBackground(C_powderBlue);
		newSet.setBorderPainted(false);
		newSet.setFocusPainted(false);
		newSet.setHorizontalAlignment(SwingConstants.LEFT);
		newSet.setMnemonic('N');
		
		comboBox = new JComboBox<>();
		comboBox.setForeground(C_nigth);
		comboBox.setBackground(C_platinum);
		comboBox.setBorder(null);
		
		
		label1 = new JLabel("Hello");
		label1.setForeground(C_nigth);
		label1.setHorizontalAlignment(SwingConstants.LEFT);

		
		label2 = new JLabel("World!");
		label2.setForeground(C_nigth);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		
		text1 = new JTextField();
		text1.setForeground(C_nigth);
		text1.setBackground(C_platinum);
		text1.setBorder(null);
		text1.setHorizontalAlignment(SwingConstants.LEFT);
		
		text2 = new JTextField();
		text2.setForeground(C_nigth);
		text2.setBackground(C_platinum);
		text2.setBorder(null);
		text2.setHorizontalAlignment(SwingConstants.RIGHT);
		
		save = new JButton("Save");
		save.setForeground(C_nigth);
		save.setBackground(C_platinum);
		save.setFocusPainted(false);
		save.setBorderPainted(false);
		save.setMnemonic('S');
		
		update();
		
		comboBox.addActionListener(e -> updateLabels());
		
		newSet.addActionListener(e -> newSet());
		save.addActionListener(e -> newCard());
		
		if ((Lernkartei)comboBox.getSelectedItem() == null) {
			label1.setEnabled(false);
			label2.setEnabled(false);
			text1.setEnabled(false);
			text2.setEnabled(false);
			save.setEnabled(false);
		}
		
		panel.add(newSet);
		panel.add(comboBox);
		panel.add(label1);
		panel.add(label2);
		panel.add(text1);
		panel.add(text2);
		panel.add(save);
		
		this.add(barPane);
		this.add(panel);
	}
	
	private void update () {
		sets = VokabeltrainerDB.getLernkarteien();
	    comboBox.removeAllItems();
	    if (sets != null) {
		    for (Lernkartei set : sets) {
		        comboBox.addItem(set);
		    }
		    updateLabels();
	    } else {
            JOptionPane.showMessageDialog(this, "Looks like the Sets Database was droped", "Statement", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void updateLabels () {
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
	
	private void newSet () {
		NewSetDialog nameDialog = new NewSetDialog (vtf);
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
        	if (NewSetDialog.sqlInjection(input)) {
        		System.err.println("HELP, SQL Injection");
        	}
        }
        nameDialog.closeDialog();
        update();
	}
	
	private void newCard () {
		Lernkartei s = (Lernkartei) comboBox.getSelectedItem();
		
		if (s != null) {
			if (!text1.getText().trim().replace("\n", "").isEmpty() && !text2.getText().trim().replace("\n", "").isEmpty()) {
				Karte c = new Karte(-1, text1.getText(), text2.getText(),
						VokabeltrainerDB.getLernkartei(s.getNummer()).getRichtung(),
						VokabeltrainerDB.getLernkartei(s.getNummer()).getGrossKleinschreibung());
				
				VokabeltrainerDB.hinzufuegenKarte(s.getNummer(), c);
				
				text1.setText("");
				text2.setText("");
				
				vtf.changePanel(-1);
			} else {
				System.out.println("Here");
				new InfoDialog(vtf, "Info", "\"" + s.getWortEinsBeschreibung() + "\" or \"" + s.getWortZweiBeschreibung() + "\" can't be empty!").setVisible(true);
			}
		} else {
			System.out.println("Help");
			new InfoDialog(vtf, "Info", "Select or create a Set").setVisible(true);;
		}
	}
	
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = panel.getWidth();
        int h = panel.getHeight();
        
        
		newSet.setBounds(w/16+32, h / 500 + 8, w / 3 , h / 16 + 16);
		newSet.setFont(new Font ("Arial", Font.BOLD, (int)(newSet.getHeight()/1.5)+1));
		
		comboBox.setBounds(16, h / 8 + 16, w - 32, h / 8);
	    comboBox.setFont(new Font("Arial", Font.PLAIN, comboBox.getHeight() / 2 + 1));
	    
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
