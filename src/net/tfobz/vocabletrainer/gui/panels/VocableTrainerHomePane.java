package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.gui.*;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

@SuppressWarnings("serial")
public class VocableTrainerHomePane extends VocableTrainerPanel {
	
	private VocableTrainerFrame vtf;
	
	private VocableTrainerBarPane barPane;
	private VocableTrainerPanel panel;
	
	public VocableTrainerHomePane (VocableTrainerFrame vtf) {
		this.vtf=vtf;
		 
		setBackground(new Color(225, 225, 225, 255));
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		barPane = new VocableTrainerBarPane(this);
		this.add(barPane);

		panel = new VocableTrainerPanel(this);
		
//		 GroupLayout layout = new GroupLayout(this);
//		panel.setLayout(layout);
//		
//		 layout.setAutoCreateGaps(true);
//	     layout.setAutoCreateContainerGaps(true);
//		
//		JButton B_start = new JButton ("Start");
//		JButton B_new = new JButton ("New");
//		JButton B_stats = new JButton ("Stats");
//		JButton B_edit = new JButton ("Edit");
//		
////		layout.setHorizontalGroup(
//	            layout.createSequentialGroup()
//	                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
//	                    .addComponent(B_start)
//	                    .addComponent(B_new)
//	                    .addComponent(B_stats)
//	                    .addComponent(B_edit))
//	        );
//
//	        // Vertical group: Stack buttons with gaps between them
//	        layout.setVerticalGroup(
//	            layout.createSequentialGroup()
//	                .addComponent(B_start)
//	                .addComponent(B_new)
//	                .addComponent(B_stats)
//	                .addComponent(B_edit)
//	        );

	        // Set preferred sizes or customize button appearance if needed
//	        for (JButton button : new JButton[]{button1, button2, button3, button4}) {
//	            button.setPreferredSize(new Dimension(150, 30)); // Example size
//	        }
		
		this.add(panel);
	}
	public void doLayout() {
	        super.doLayout();

	        int totalHeight = getHeight();
	        int barPaneHeight = totalHeight / 12; 
	        int panelHeight = totalHeight - barPaneHeight;

	        barPane.setPreferredSize(new Dimension(getWidth(), barPaneHeight));
	        barPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, barPaneHeight));
	        barPane.setMinimumSize(new Dimension(getWidth(), barPaneHeight));

	        panel.setPreferredSize(new Dimension(getWidth()-32, panelHeight-16));
	        panel.setMaximumSize(new Dimension(getWidth()-32, panelHeight-16));
	        panel.setMinimumSize(new Dimension(getWidth()-32, panelHeight-16));

	        revalidate();
    }
}
