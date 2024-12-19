package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.gui.*;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;
import java.awt.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

@SuppressWarnings("serial")
public class VocableTrainerRunPanel extends VocableTrainerPanel {
	
	private VocableTrainerFrame vtf;
	private VocableTrainerBarPane barPane;
	private VocableTrainerPanel panel;
	
	private VocableTrainerRunSettings settings;
	private int[] times;
	
	public VocableTrainerRunPanel (VocableTrainerFrame vtf, VocableTrainerRunSettings settings) {
		this.vtf=vtf;
		this.settings = settings;
		
		setBackground(new Color(225, 225, 225, 255));
		
		
		setLayout(null);

		ClockLabel clock1 = new ClockLabel("Total Time: ", settings.isTotalTimeLimit()?settings.getTotalTimeLimit():0);
        clock1.setBounds(10, 10, 200, 30);
        clock1.setFont(new Font("Arial", Font.PLAIN, 30));
        this.add(clock1);
        ClockLabel clock2 = new ClockLabel("Card Time: ", settings.isCardTimeLimit()?settings.getCardTimeLimit():0);
        clock2.setBounds(10, 40, 200, 30);
        clock2.setFont(new Font("Arial", Font.PLAIN, 30));
        this.add(clock2);
        
        times = settings.isCardLimit()?new int[settings.getCardLimit()]: new int[VokabeltrainerDB.getKarten(settings.getFach().getNummer()).size()];
        
        clock1.start();
        clock2.start();
		
		barPane = new VocableTrainerBarPane(this);
		panel = new VocableTrainerPanel(this);
	}
	
	public void endRun() {
		
	}
	
	private class ClockLabel extends JLabel {
		
		private final int pos;
		private int time;
		private int limit = 0;
		private boolean limited = false;
		ScheduledExecutorService executor=null;
		
		public ClockLabel(String fixtext) {
			super(fixtext);
			pos = fixtext.length();
		}
		
		public ClockLabel(String fixtext, int limit) {
			super(fixtext);
			pos = fixtext.length();
			this.limited = limit>0;
			this.limit = limit;
		}
		
		public void stop() {
			executor.shutdown();
			executor = null;
		}
		
		public void start() {
			time=0;
			if(executor==null) {
				executor = Executors.newSingleThreadScheduledExecutor();
				executor.scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						time++;
						setText(getText().substring(0, pos)+time);
					}
				}, 0, 1, TimeUnit.SECONDS);
			}
		}
		
	}
}
