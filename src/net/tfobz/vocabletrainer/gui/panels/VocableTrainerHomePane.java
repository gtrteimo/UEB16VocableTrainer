package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.gui.*;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class VocableTrainerHomePane extends VocableTrainerPanel {
	
	private VocableTrainerFrame vtf;
	
	public VocableTrainerHomePane (VocableTrainerFrame vtf) {
		super(VocableTrainerPanel.NORMAL_PANEL);
		this.vtf=vtf;
		 
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		
		 layout.setAutoCreateGaps(true);
	     layout.setAutoCreateContainerGaps(true);
		
		JButton B_start = new JButton ("Start");
		JButton B_new = new JButton ("New");
		JButton B_stats = new JButton ("Stats");
		JButton B_edit = new JButton ("Edit");
		
		// Horizontal group: Center buttons in two columns
		layout.setHorizontalGroup(
		    layout.createSequentialGroup()
		        .addGap(0, 0, Short.MAX_VALUE) // Dynamic gap on left
		        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
		            .addGroup(layout.createSequentialGroup()
		                .addComponent(B_start)
		                .addGap(20) // Fixed gap between buttons
		                .addComponent(B_new))
		            .addGroup(layout.createSequentialGroup()
		                .addComponent(B_stats)
		                .addGap(20) // Fixed gap between buttons
		                .addComponent(B_edit)))
		        .addGap(0, 0, Short.MAX_VALUE) // Dynamic gap on right
		);

		// Vertical group: Center buttons with rows stacked
		layout.setVerticalGroup(
		    layout.createSequentialGroup()
		        .addGap(0, 0, Short.MAX_VALUE) // Dynamic gap on top
		        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
		            .addComponent(B_start)
		            .addComponent(B_new))
		        .addGap(20) // Fixed gap between rows
		        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
		            .addComponent(B_stats)
		            .addComponent(B_edit))
		        .addGap(0, 0, Short.MAX_VALUE) // Dynamic gap on bottom
		);


//	         Set preferred sizes or customize button appearance if needed
	        for (JButton button : new JButton[]{B_start, B_new, B_stats, B_edit}) {
	            button.setPreferredSize(new Dimension(150, 30)); // Example size
	        }
		
		this.add(panel);
	}
	public void doLayout() {
	        super.doLayout();

	        int totalHeight = getHeight();
	        int totalWidth = getWidth();

	        int barPaneHeight = totalHeight / 12; 
	        int panelHeight = totalHeight - barPaneHeight;

	        int buttonWidth = totalWidth / 4;
	        int buttonHeight = panelHeight / 9 * 2; // Equivalent to 9/2 of panel height

	        for (Component component : panel.getComponents()) {
	            if (component instanceof JButton) {
	            	JButton button = (JButton) component;
	                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
	                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
	                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
	                
	                int fontSize = buttonHeight / 5; // Example: font size is 1/5 of the button height
	                button.setFont(new Font("Arial", Font.PLAIN, fontSize));  // Use your desired font style
	            }
	        }
	        
	        revalidate();
    }
}
