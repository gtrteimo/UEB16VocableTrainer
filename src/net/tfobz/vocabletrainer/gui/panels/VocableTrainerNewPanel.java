package net.tfobz.vocabletrainer.gui.panels;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.*;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
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
		
		update();
		
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
		
		newSet.addActionListener(e -> newSet());
		
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
	    for (Lernkartei set : sets) {
	        comboBox.addItem(set);
	    }
	    updateLabels();
	}
	
	private void updateLabels () {
		Lernkartei temp = (Lernkartei)comboBox.getSelectedItem();
		label1.setText(temp.getWortEinsBeschreibung());
		label2.setText(temp.getWortZweiBeschreibung());
	}
	
	private void newSet () {
		NewSetDialog nameDialog = new NewSetDialog (vtf);
		nameDialog.setVisible(true);

		//SQL injection potential: 
		// ', '', '', true, false); DROP SCHEMA PUBLIC CASCADE; --
		
        String input = nameDialog.getInput();
        if (!input.isEmpty()) {
            Lernkartei newSet = new Lernkartei(input, "Description 1", "Description 2", true, false);
            int result = VokabeltrainerDB.hinzufuegenLernkartei(newSet);
            if (result == 0) {
                sets.add(newSet);
                comboBox.addItem(newSet);
            } else {
                JOptionPane.showMessageDialog(this, "Seem to me like an SQL Injection. Well not my problem!", "Statement", JOptionPane.ERROR_MESSAGE);
            }
        }
        update();
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
    private class NewSetDialog extends JDialog {

    	private JLabel text;
        private JTextField inputField;
        private JButton confirmButton;
        private JButton cancelButton;

        public NewSetDialog(JFrame parent) {
            super(parent, "New Set", true);

            setResizable(false);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            getContentPane().setBackground(C_powderBlue);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    check();
                }
            });
            
            setLayout(null);

            int width = parent.getWidth() / 2;
            int height = parent.getHeight() / 2;
            setSize(width, height);
            setLocation(parent.getX() + parent.getWidth() / 2 - width / 2, parent.getY() + parent.getHeight() / 2 - height / 2);

            text = new JLabel("Enter set name:");
            text.setBounds(13, height/8 - 16, width - 32, height/4);
            text.setFont(new Font ("Arial", Font.PLAIN, text.getHeight()/2 + 1));
            text.setForeground(C_nigth);
            text.setHorizontalAlignment(SwingConstants.CENTER);
            
            inputField = new JTextField();
            inputField.setBounds(13, height/3 - 8, width - 32, height/6);
            inputField.setFont(new Font ("Arial", Font.PLAIN, inputField.getHeight()/2 + 1));
            inputField.setBackground(C_platinum);
            inputField.setForeground(C_nigth);
            inputField.setBorder(null);
            
            inputField.setText("', '', '', true, false); DROP SCHEMA PUBLIC CASCADE; --");

            confirmButton = new JButton("Confirm");
            confirmButton.setBounds(width + 4 -  width/2, height - height/5 - 53, width/2 - 22, height / 5 );
            confirmButton.setFont(new Font ("Arial", Font.PLAIN, confirmButton.getHeight()/2 + 1));
            confirmButton.setForeground(C_nigth);
            confirmButton.setBackground(C_platinum);
            confirmButton.setFocusPainted(false);
            confirmButton.setBorderPainted(false);
            confirmButton.addActionListener(e -> check());

            cancelButton = new JButton("Cancel");
            cancelButton.setBounds(13 , height - height/5 - 53, width/2 - 22, height / 5 );
            cancelButton.setFont(new Font ("Arial", Font.PLAIN, cancelButton.getHeight()/2 + 1));
            cancelButton.setForeground(C_nigth);
            cancelButton.setBackground(C_platinum);
            cancelButton.setFocusPainted(false);
            cancelButton.setBorderPainted(false);
            cancelButton.addActionListener(e -> closeDialog());
            
            add(text);
            add(inputField);
            add(confirmButton);
            add(cancelButton);
        }

        private void check() {
            if (!inputField.getText().trim().isEmpty()) {
                setVisible(false);
            } else {
                closeDialog();
            }
        }

        public String getInput() {
            return inputField.getText().trim();
        }

        public void closeDialog() {
            setVisible(false);
            dispose();
        }
    }
}
