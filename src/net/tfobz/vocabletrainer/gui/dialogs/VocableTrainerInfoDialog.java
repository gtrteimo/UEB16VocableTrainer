package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Font;

import javax.swing.*;

import net.tfobz.vocabletrainer.gui.panels.VocableTrainerPanel;

@SuppressWarnings("serial")
public class VocableTrainerInfoDialog extends JDialog {
	
	protected JPanel componentPanel;
	
	private JLabel label;
	
	public VocableTrainerInfoDialog(JFrame parent, String title, String text) {
		this(parent, title);
		
		setSize(getWidth()/3, getHeight()/3);
		setLocation((parent.getWidth() - 6)/2 - (parent.getWidth()/3 - 6)/2, (parent.getHeight() - 6)/2 - (parent.getHeight()/3 - 6)/2 );
        componentPanel.setBounds(0, 0, (parent.getWidth()/3 - 6), (parent.getHeight()/3 - 40));
		
		label = new JLabel(text);
		label.setBounds(8,  componentPanel.getHeight()/3, componentPanel.getWidth() - 16, componentPanel.getHeight()/8);
		label.setFont(new Font ("Arial", Font.PLAIN, label.getHeight()*7/10 + 1));
		label.setForeground(VocableTrainerPanel.C_nigth);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		componentPanel.add(label);
		
		add(componentPanel);
	}
	public VocableTrainerInfoDialog(JFrame parent, String title) {
	    super(parent, title, true);
	    
	    setResizable(false);
	    
	    setLayout(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    
	    setSize(parent.getWidth(), parent.getHeight());
        setLocation(parent.getX(), parent.getY());
        
        componentPanel = new JPanel();
        componentPanel.setLayout(null);
        componentPanel.setBackground(VocableTrainerPanel.C_powderBlue);
        componentPanel.setBounds(0, 0 , (parent.getWidth() - 6), (parent.getHeight() - 40));
	}
	 public void closeDialog() {
	        setVisible(false);
	        dispose();
	    }
}
