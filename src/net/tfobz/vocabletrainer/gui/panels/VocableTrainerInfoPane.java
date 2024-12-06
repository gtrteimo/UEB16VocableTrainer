package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;

public class VocableTrainerInfoPane extends VocableTrainerPanel {
	public VocableTrainerInfoPane (VocableTrainerFrame vtf) {
		super(VocableTrainerPanel.NORMAL_PANEL);
		this.add(panel);
		revalidate();
	}
}
