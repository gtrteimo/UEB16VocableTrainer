package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.*;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
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
		
		label = new JLabel();
		label.setBounds(8, 8, componentPanel.getWidth() - 16, componentPanel.getHeight() - 16);
		label.setForeground(VocableTrainerPanel.textColor2);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.TOP);
		
		Font font = new Font("Arial", Font.PLAIN, label.getHeight()/10+1);
		int maxWidth = componentPanel.getWidth() - 16;
		int maxHeight = componentPanel.getHeight() - 16;
		FontMetrics metrics;
		
		while (true) {
			label.setFont(font);
			metrics = label.getFontMetrics(font);
			StringBuilder formattedText = new StringBuilder();
			int lineHeight = metrics.getHeight();
			int totalHeight = 0;
			boolean fits = true;
			StringBuilder line = new StringBuilder();
			for (String word : text.split(" ")) {
				if (metrics.stringWidth(line.toString() + word + " ") > maxWidth) {
					if (line.length() > 0) {
						formattedText.append(line).append("<br>");
						totalHeight += lineHeight;
						line.setLength(0);
					}
					if (totalHeight + lineHeight > maxHeight) {
						fits = false;
						break;
					}
					line.append(word).append(" ");
				} else {
					line.append(word).append(" ");
				}
			}
			if (line.length() > 0) {
				formattedText.append(line);
				totalHeight += lineHeight;
			}
			if (fits && totalHeight <= maxHeight) {
				label.setText("<html><div style='text-align:center;'>" + formattedText.toString().trim() + "</div></html>");
				break;
			}
			font = new Font("Arial", Font.PLAIN, font.getSize() - 1);
		}
		
		JButton close = new JButton(VocableTrainerLocalization.BUTTON_CLOSE);
		close.setBounds( 16, componentPanel.getHeight() - componentPanel.getHeight()/6 - 16, componentPanel.getWidth() - 32, componentPanel.getHeight() / 6 );
		close.setFont(new Font ("Arial", Font.PLAIN, close.getHeight()/2));
		close.setForeground(VocableTrainerPanel.textColor1);
		close.setBackground(VocableTrainerPanel.buttonBackgroundColor);
		close.setFocusPainted(false);
		close.setBorderPainted(false);
		close.addActionListener(e -> closeDialog());
		
		componentPanel.add(label);
		componentPanel.add(close);
		
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
        componentPanel.setBackground(VocableTrainerPanel.mainBackgroundColor);
        componentPanel.setBounds(0, 0 , (parent.getWidth() - 6), (parent.getHeight() - 40));
	}
	 public void closeDialog() {
	        setVisible(false);
	        dispose();
	    }
}
