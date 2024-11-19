package net.tfobz.vocabletrainer.gui.listeners;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import net.tfobz.vocabletrainer.gui.VocableTrainerPanelInterface;

/**
 * 
 * 
 * @author gtrteimo
 */
public class ResizeAdapter implements ComponentListener {

	@Override
	public void componentResized(ComponentEvent e) {
		Component c = e.getComponent();
		if (c instanceof javax.swing.JPanel && c instanceof VocableTrainerPanelInterface) {
			VocableTrainerPanelInterface vtp = (VocableTrainerPanelInterface) c;
			int width = c.getWidth();
			int heigth = c.getHeight();
			vtp.updateSize(width, heigth);
		} else {
			System.err.println("ERROR in ResizeAdapter Interface in the componentResized Method"
					+ "A Component that recieved the resize event isen't dosen't extend from JPanel or dosen't implement VocableTrainerPanel");
//			throw new Exception("A Component that recieved the resize event isen't dosen't extend from JPanel or dosen't implement VocableTrainerPanel");
			//TODO -> Better Error Handling
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}

}
