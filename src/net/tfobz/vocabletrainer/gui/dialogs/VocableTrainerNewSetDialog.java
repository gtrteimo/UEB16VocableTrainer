package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.tfobz.vocabletrainer.gui.panels.VocableTrainerPanel;
import net.tfobz.vokabeltrainer.model.Lernkartei;

@SuppressWarnings("serial")
public class VocableTrainerNewSetDialog extends VocableTrainerInfoDialog {	
	protected JLabel text;
	protected JTextField inputField;
	protected JLabel dLabel1;
	protected JLabel dLabel2;
	protected JTextField dInputField1;
	protected JTextField dInputField2;
	protected JButton confirmButton;
	protected JButton cancelButton;

    public VocableTrainerNewSetDialog(JFrame parent) {
        super(parent, "New Set");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeDialog();
            }
        });    
        
        int w = componentPanel.getWidth();
        int h = componentPanel.getHeight();
        
        text = new JLabel("Enter set name:");
        text.setBounds(16, 16, w - 32, h/8);
        text.setFont(new Font ("Arial", Font.PLAIN, text.getHeight()/2 + 1));
        text.setForeground(VocableTrainerPanel.C_nigth);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        
        inputField = new JTextField();
        inputField.setBounds(16, 16 + h/8, w - 32, h/8);
        inputField.setFont(new Font ("Arial", Font.PLAIN, inputField.getHeight()/2 + 1));
        inputField.setBackground(VocableTrainerPanel.C_platinum);
        inputField.setForeground(VocableTrainerPanel.C_nigth);
        inputField.setBorder(null);
        
        inputField.setText("Example Name");
        //"', '', '', true, false); DROP SCHEMA PUBLIC CASCADE; --"
//SQL injection potential: 
        
        dLabel1 = new JLabel("Description 1");         	
        dLabel1.setBounds(16, 16 + h/3, w/2 - 32, h/8);
        dLabel1.setFont(new Font ("Arial", Font.PLAIN, dLabel1.getHeight()/2 + 1));
        dLabel1.setBackground(VocableTrainerPanel.C_platinum);
        dLabel1.setForeground(VocableTrainerPanel.C_nigth);
        dLabel1.setBorder(null);
        dLabel1.setDisplayedMnemonic('1');
        dLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        dLabel2 = new JLabel("Description 2");         	
        dLabel2.setBounds(w/2 + 16, 16 + h/3, w/2 - 32, h/8);
        dLabel2.setFont(new Font ("Arial", Font.PLAIN, dLabel2.getHeight()/2 + 1));
        dLabel2.setBackground(VocableTrainerPanel.C_platinum);
        dLabel2.setForeground(VocableTrainerPanel.C_nigth);
        dLabel2.setBorder(null);
        dLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        dLabel2.setDisplayedMnemonic('2');
        
        dInputField1 = new JTextField();
        dInputField1.setBounds(16, 16 + h/2, w/2 - 32, h/8);
        dInputField1.setFont(new Font ("Arial", Font.PLAIN, dInputField1.getHeight()/2 + 1));
        dInputField1.setBackground(VocableTrainerPanel.C_platinum);
        dInputField1.setForeground(VocableTrainerPanel.C_nigth);
        dInputField1.setBorder(null);
        dInputField1.setHorizontalAlignment(SwingConstants.CENTER);
        dLabel1.setLabelFor(dInputField1);

        
        dInputField1.setText("Deutsch");
        
        dInputField2 = new JTextField();
        dInputField2.setBounds(w/2 + 16, 16 + h/2, w/2 - 32, h/8);
        dInputField2.setFont(new Font ("Arial", Font.PLAIN, dInputField2.getHeight()/2 + 1));
        dInputField2.setBackground(VocableTrainerPanel.C_platinum);
        dInputField2.setForeground(VocableTrainerPanel.C_nigth);
        dInputField2.setBorder(null);
        dInputField2.setHorizontalAlignment(SwingConstants.CENTER);
        dLabel2.setLabelFor(dInputField2);

        dInputField2.setText("English");
        
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(w/2 + 16, h - h/6 - 16, w/2 - 32, h / 6 );
        confirmButton.setFont(new Font ("Arial", Font.PLAIN, confirmButton.getHeight()/2));
        confirmButton.setForeground(VocableTrainerPanel.C_platinum);
        confirmButton.setBackground(VocableTrainerPanel.C_slateGray);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.addActionListener(e -> check());

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(16 , h - h/6 - 16, w/2 - 32, h / 6 );
        cancelButton.setFont(new Font ("Arial", Font.PLAIN, cancelButton.getHeight()/2));
        cancelButton.setForeground(VocableTrainerPanel.C_platinum);
        cancelButton.setBackground(VocableTrainerPanel.C_slateGray);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.addActionListener(e -> closeDialog());
        
        inputField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {}
				
			@Override
			public void focusGained(FocusEvent e) {
				inputField.selectAll();
			}
		});
        dInputField1.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {}
			
			@Override
			public void focusGained(FocusEvent e) {
				dInputField1.selectAll();
			}
		});
        dInputField2.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {}
			
			@Override
			public void focusGained(FocusEvent e) {
				dInputField2.selectAll();
			}
		});
        
        componentPanel.add(text);
        componentPanel.add(inputField);
        componentPanel.add(dLabel1);
        componentPanel.add(dLabel2);
        componentPanel.add(dInputField1);
        componentPanel.add(dInputField2);
        componentPanel.add(confirmButton);
        componentPanel.add(cancelButton);
        
        add(componentPanel);
    }

    private void check() {
        if (!inputField.getText().trim().isEmpty()) {
            setVisible(false);
        } else {
            closeDialog();
        }
    }
    
    public static boolean sqlInjection(Lernkartei l) {
    	l.getWortEinsBeschreibung();
    	if (l.getBeschreibung().contains("'") || l.getBeschreibung().contains(";") || l.getBeschreibung().contains("--")) {
    		return true;
    	}
    	if (l.getWortEinsBeschreibung().contains("'") || l.getWortEinsBeschreibung().contains(";") || l.getWortEinsBeschreibung().contains("--")) {
    		return true;
    	}
    	if (l.getWortZweiBeschreibung().contains("'") || l.getWortZweiBeschreibung().contains(";") || l.getWortZweiBeschreibung().contains("--")) {
    		return true;
    	}
    	return false;
    }

    public Lernkartei getInput() {
    	Lernkartei ret = new Lernkartei();
    	ret.setBeschreibung(inputField.getText().trim());
    	ret.setWortEinsBeschreibung(dInputField1.getText().trim());
    	ret.setWortZweiBeschreibung(dInputField2.getText().trim());
        return ret;
    }

    @Override
    public void closeDialog() {
    	inputField.setText("");
    	dInputField1.setText("");
    	dInputField2.setText("");
        setVisible(false);
        dispose();
    }
}
