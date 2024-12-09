package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.gui.*;

import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class VocableTrainerStartPane extends VocableTrainerPanel {
	
	private VocableTrainerFrame vtf;
	
	private VocableTrainerBarPane barPane;
	private VocableTrainerPanel panel;
	
	public VocableTrainerStartPane (VocableTrainerFrame vtf) {
		this.vtf=vtf;
		 
		setBackground(new Color(225, 225, 225, 255));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JCheckBox checkbox1 = new JCheckBox("Option 1");
        JPanel checkboxPanel1 = new JPanel();
        checkboxPanel1.setBackground(new Color(225, 225, 225, 255));
        checkboxPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel1.add(checkbox1);
        JPanel spinnerPanel1 = new JPanel();
        spinnerPanel1.setLayout(new BoxLayout(spinnerPanel1, BoxLayout.X_AXIS));
        JSpinner spinner1A = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner spinner1B = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        spinnerPanel1.add(spinner1A);
        spinnerPanel1.add(Box.createRigidArea(new Dimension(10, 0)));
        spinnerPanel1.add(spinner1B);
        checkboxPanel1.add(spinnerPanel1);
        add(checkboxPanel1);

        JCheckBox checkbox2 = new JCheckBox("Option 2");
        JPanel checkboxPanel2 = new JPanel();
        checkboxPanel2.setBackground(new Color(225, 225, 225, 255));
        checkboxPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel2.add(checkbox2);
        JPanel spinnerPanel2 = new JPanel();
        spinnerPanel2.setLayout(new BoxLayout(spinnerPanel2, BoxLayout.X_AXIS));
        JSpinner spinner2A = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner spinner2B = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        spinnerPanel2.add(spinner2A);
        spinnerPanel2.add(Box.createRigidArea(new Dimension(10, 0)));
        spinnerPanel2.add(spinner2B);
        checkboxPanel2.add(spinnerPanel2);
        add(checkboxPanel2);

        JCheckBox checkbox3 = new JCheckBox("Option 3");
        JPanel checkboxPanel3 = new JPanel();
        checkboxPanel3.setBackground(new Color(225, 225, 225, 255));
        checkboxPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel3.add(checkbox3);
        JPanel spinnerPanel3 = new JPanel();
        spinnerPanel3.setLayout(new BoxLayout(spinnerPanel3, BoxLayout.X_AXIS));
        JSpinner spinner3A = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner spinner3B = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        spinnerPanel3.add(spinner3A);
        spinnerPanel3.add(Box.createRigidArea(new Dimension(10, 0)));
        spinnerPanel3.add(spinner3B);
        checkboxPanel3.add(spinnerPanel3);
        add(checkboxPanel3);

        JCheckBox checkbox4 = new JCheckBox("Option 4");
        JPanel checkboxPanel4 = new JPanel();
        checkboxPanel4.setBackground(new Color(225, 225, 225, 255));
        checkboxPanel4.setLayout(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel4.add(checkbox4);
        JPanel spinnerPanel4 = new JPanel();
        checkboxPanel4.add(spinnerPanel4);
        add(checkboxPanel4);

        JCheckBox checkbox5 = new JCheckBox("Option 5");
        JPanel checkboxPanel5 = new JPanel();
        checkboxPanel5.setBackground(new Color(225, 225, 225, 255));
        checkboxPanel5.setLayout(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel5.add(checkbox5);
        JPanel spinnerPanel5 = new JPanel();
        checkboxPanel5.add(spinnerPanel5);
        add(checkboxPanel5);

        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Start");
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(button);
        button.setPreferredSize(new Dimension(0, 50));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        add(buttonPanel);
		
		barPane = new VocableTrainerBarPane(this);
		panel = new VocableTrainerPanel(this);
	}
}
