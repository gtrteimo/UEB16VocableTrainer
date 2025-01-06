package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private VocableTrainerPanel panel;

	public VocableTrainerColorChooser(VocableTrainerPanel panel) {
		super("Theme customiser");
		setBounds(100,100,panel.getWidth()/2,panel.getHeight()/2);
	    setResizable(true);
        this.setBackground(VocableTrainerPanel.C_powderBlue);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.panel = panel;
		
		labels = new JLabel[5];
		buttons = new JButton[5];
		colors = new Color[5];
		
		labels[0] = new JLabel("Menu Bar Colour");
		labels[1] = new JLabel("Main Background Colour");
		labels[2] = new JLabel("Text Colour 1");
		labels[3] = new JLabel("Text Colour 2");
		labels[4] = new JLabel("Button Background Colour");
		
		for (int i = 0; i < buttons.length; i++) {
		    buttons[i] = new JButton();
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
			VocableTrainerColorChooser.this.panel.changeColour(colors[0], colors[1], colors[2], colors[3], colors[4]);
			});
		close = new JButton("Close");
		close.addActionListener(e->{
				setVisible(false);
				dispose();
			});
		
		Insets insets = getInsets();
		int width = getWidth()-insets.left-insets.right-20;
		int height = getHeight()-insets.top-insets.bottom-10;
		
		apply.setBounds(insets.left+10, getHeight()-insets.bottom-height/6, width/2-5, height/6-10);
		apply.setFont(new Font ("Arial", Font.PLAIN, apply.getHeight()/2 +5));
		close.setBounds(insets.left+width/2+20, getHeight()-insets.bottom-height/6, width/2-5, height/6-10);
		close.setFont(new Font ("Arial", Font.PLAIN, close.getHeight()/2 +5));
		
		for (int i = 0; i < 5; i++) {
			labels[i].setBounds(insets.left+20, insets.top+10+height/6*i, width/2-5, height/6-10);
			labels[i].setFont(new Font ("Arial", Font.PLAIN, labels[i].getHeight()/2 +5));
			buttons[i].setBounds(insets.left+width/2+20, insets.top+10+height/6*i, width/2-5, height/6-10);
			buttons[i].setFont(new Font ("Arial", Font.PLAIN, buttons[i].getHeight()/2 +5));
		}
		
		Container contentPane = this.getContentPane();
		contentPane.setLayout(null);
		contentPane.add(apply);
		contentPane.add(close);
		for (JLabel label : labels) {
			contentPane.add(label);
		}
		for (JButton button : buttons) {
			contentPane.add(button);
		}
	}
	
	
}
