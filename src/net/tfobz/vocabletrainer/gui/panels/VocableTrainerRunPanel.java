package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.gui.*;

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
	
	public VocableTrainerRunPanel (VocableTrainerFrame vtf) {
		this.vtf=vtf;
		setBackground(new Color(225, 225, 225, 255));
		
		
		
		setLayout(null);

		ClockLabel clock1 = new ClockLabel("Total Time: ");
        clock1.setBounds(10, 10, 200, 30);
        clock1.setFont(new Font("Arial", Font.PLAIN, 30));
        this.add(clock1);
        ClockLabel clock2 = new ClockLabel("Card Time: ");
        clock2.setBounds(10, 40, 200, 30);
        clock2.setFont(new Font("Arial", Font.PLAIN, 30));
        this.add(clock2);
        
        
        clock1.start();
        clock2.start();
		
		barPane = new VocableTrainerBarPane(this);
		panel = new VocableTrainerPanel(this);
	}
	
	private class ClockLabel extends JLabel {
		
		private final int pos;
		private int time;
		ScheduledExecutorService executor=null;
		
		public ClockLabel(String fixtext) {
			super(fixtext);
			pos = fixtext.length();
		}
		
		public void reset() {
			time = 0;
			stop();
			start();
		}
		
		public void stop() {
			executor.shutdown();
			executor = null;
		}
		
		public void start() {
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
