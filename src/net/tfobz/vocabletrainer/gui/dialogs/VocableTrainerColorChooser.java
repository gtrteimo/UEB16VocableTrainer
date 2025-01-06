package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.tfobz.vocabletrainer.gui.panels.VocableTrainerPanel;

@SuppressWarnings("serial")
public class VocableTrainerColorChooser extends JFrame {
		
	private JLabel[] labels;
	private JButton[] buttons;
	private Color[] colors;
	private JButton apply;
	
	public Color[] getColors() {
		return colors;
	}

	private JButton close;
	private VocableTrainerPanel reference;

	public VocableTrainerColorChooser(VocableTrainerPanel reference) {
		super("Theme customiser");
		setBounds(100,100,reference.getWidth()/2,reference.getHeight()/2);
	    setResizable(true);
        this.setMinimumSize(new Dimension(600, 300));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.reference = reference;
		
		labels = new JLabel[5];
		buttons = new JButton[5];
		colors = new Color[5];
		
		labels[0] = new JLabel("Menu Bar Colour");
		labels[1] = new JLabel("Main Background Colour");
		labels[2] = new JLabel("Text Colour 1");
		labels[3] = new JLabel("Text Colour 2");
		labels[4] = new JLabel("Button Background Colour");
		colors[0] = VocableTrainerPanel.C_spaceCadet;
		colors[1] = VocableTrainerPanel.C_powderBlue;
		colors[2] = VocableTrainerPanel.C_platinum;
		colors[3] = VocableTrainerPanel.C_nigth;
		colors[4] = VocableTrainerPanel.C_slateGray;
		
		for (int i = 0; i < buttons.length; i++) {
		    buttons[i] = new JButton();
		    buttons[i].setBackground(colors[i]);
		    int index = i;
		    buttons[i].addActionListener(e -> {
		        Color chosenColor = JColorChooser.showDialog(null, "Choose a Color", buttons[index].getBackground());
		        if (chosenColor != null) {
		            buttons[index].setBackground(chosenColor);
		            colors[index] = chosenColor;
		        }
		    });
		}
		apply = new JButton("Apply");
		apply.addActionListener(e->{
			VocableTrainerColorChooser.this.reference.changeColour(colors[0], colors[1], colors[2], colors[3], colors[4]);
			});
		close = new JButton("Close");
		close.addActionListener(e->{
				setVisible(false);
				dispose();
			});
		
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        
		        int height = this.getHeight();
		        int width = this.getWidth();
		        
		        apply.setBounds(10, getHeight()-height/6, width/2-15, height/6-10);
				apply.setFont(new Font ("Arial", Font.PLAIN, apply.getHeight()/2));
				close.setBounds(10+width/2, getHeight()-height/6, width/2-15, height/6-10);
				close.setFont(new Font ("Arial", Font.PLAIN, close.getHeight()/2));
				
				for (int i = 0; i < 5; i++) {
					labels[i].setBounds(10, 10+height/6*i, width/2-15, height/6-10);
					labels[i].setFont(new Font ("Arial", Font.PLAIN, labels[i].getHeight()/2));
					buttons[i].setBounds(10+width/2, 10+height/6*i, width/2-15, height/6-10);
					buttons[i].setFont(new Font ("Arial", Font.PLAIN, buttons[i].getHeight()/2));
				}
		    }
		};
		
		panel.add(apply);
		panel.add(close);
		for (JLabel label : labels) {
			panel.add(label);
		}
		for (JButton button : buttons) {
			panel.add(button);
		}
		this.getContentPane().add(panel);
	}
	
}
