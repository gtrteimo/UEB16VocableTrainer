package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Dimension;

import javax.swing.JPanel;

import net.tfobz.vocabletrainer.gui.*;

/**
 * Menu panel that will be displayed on top of the source panel that called it.
 * Gives someone a selection of all panels that they can navigate to.
 * 
 * @author gtrteimo
 */
@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements VocableTrainerPanelInterface{
	//TODO -> all
	private VocableTrainerFrame vtf;
	
	public MenuPanel (VocableTrainerFrame vtf) {
		this.vtf = vtf;
		setPreferredSize(new Dimension(vtf.getWidth(), vtf.getHeight()));
	}

	@Override
	public void updateSize(int width, int heigth) {
//		setPreferredSize(new Dimension(width, width));
//		vtf.pack();
//		TODO -> fix
	}
}
