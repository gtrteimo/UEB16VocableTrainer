	package net.tfobz.vocabletrainer.gui.panels;

	import java.awt.Color;

	@SuppressWarnings("serial")
	public class VocableTrainerBarPane extends VocableTrainerPanel {
		
		VocableTrainerPanel sourcePanel;
		
		final static String pfad = "\\resources\\icon\\bars.png";	
		private static VocableTrainerImageLabel barsImage;
		
		public VocableTrainerBarPane (VocableTrainerPanel sourcePanel) {
			super(sourcePanel);
			setLayout(null);
			setBackground(new Color(50, 50, 75, 255));
			if (barsImage == null) {
				barsImage = new VocableTrainerImageLabel(pfad);
			}
			add(barsImage);
		}
	}

