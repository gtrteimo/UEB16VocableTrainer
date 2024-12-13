package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;


@SuppressWarnings("serial")
public class VocableTrainerCreditsPane extends VocableTrainerPanel {

	private VocableTrainerFrame vtf;
	
	private VocableTrainerBarPane barPane;
	private VocableTrainerPanel panel;
	
	private static final String SPACER = "  +  ";
	
	private JLabel labelhelpers;
	private ArrayList<JLabel> helpers;
	private JLabel labelcreators;
	private ArrayList<JLabel> creators;
	
	public VocableTrainerCreditsPane (VocableTrainerFrame vtf) {
		this.vtf=vtf;
		 
		setBackground(new Color(225, 225, 225, 255));
		
		this.setLayout(null);

		labelcreators = new JLabel("Creators");
		creators = new ArrayList<JLabel>();
		creators.add(new JLabel(SPACER+"21chinat"));
		creators.add(new JLabel(SPACER+"gtrteimo"));
		
		labelhelpers = new JLabel("Special thanks to our slaves:");
		helpers = new ArrayList<JLabel>();
		helpers.add(new JLabel(SPACER+"Luggin Nick"));
		helpers.add(new JLabel(SPACER+"Prader Manuel"));
		helpers.add(new JLabel(SPACER+"Winkler Johannes"));
		
		int componentHeight = this.getHeight()/(2+creators.size()+helpers.size());
		
		int n=0;
		labelcreators.setBounds(10,10+n*componentHeight,200,30);
		this.add(labelcreators);
		n++;
		for(JLabel a : creators) {
			a.setBounds(10, 10+n*componentHeight, 200, 30);
			this.add(a);
			n++;
		}
		labelhelpers.setBounds(10, 10+n*componentHeight, 200, 30);
		n++;
		this.add(labelhelpers);
		for(JLabel a : helpers) {
			a.setBounds(10, 10+n*componentHeight, 200, 30);
			this.add(a);
			n++;
		}
		
		
		barPane = new VocableTrainerBarPane(this);
		panel = new VocableTrainerPanel(this);
	}
}
