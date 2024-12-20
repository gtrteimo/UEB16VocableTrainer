package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.data.*;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vokabeltrainer.model.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

@SuppressWarnings("serial")
public class VocableTrainerRunPanel extends VocableTrainerPanel {
	
	private VocableTrainerRunSettings settings;
	
	
	private int[] times;
	private int result;
	private int cardNum;
	private ArrayList<Karte> cards;
	private Karte currentCard;
	
	
	private ClockLabel clock1;
	private ClockLabel clock2;
	
	private JLabel originalWord;
	private JLabel answer;

	private JTextField input;
	
	private JButton stop;
	private JButton skip;
	private JButton next;

	
	public VocableTrainerRunPanel (VocableTrainerFrame vtf, VocableTrainerRunSettings settings) {
		super();
		this.vtf = vtf;
		this.settings = settings;
		
		setBackground(C_platinum);
		setLayout(null);
		
		panel = new VocableTrainerPanel(this);
		panel.setLocation(16, 16);
		panel.setBackground(C_powderBlue);

        times = settings.isCardLimit()?new int[settings.getCardLimit()]: new int[VokabeltrainerDB.getKarten(settings.getBox().getNummer()).size()];
        cardNum = 0;
        
        cards = VokabeltrainerDB.getKarten(settings.getBox().getNummer());
        
		clock1 = new ClockLabel("Total Time: ", settings.isTotalTimeLimit()?settings.getTotalTimeLimit():0, ()->endRun());
        clock2 = new ClockLabel("Card Time: ", settings.isCardTimeLimit()?settings.getCardTimeLimit():0,()->nextCard());
        
        clock1.start();
        clock2.start();
        
		currentCard = cards.get((int)(Math.random()*cards.size()));
        cards.remove(currentCard);
        
        panel.add(clock1);
        panel.add(clock2);
        
        add(panel);
	}
	
	public void endRun() {
		System.out.println("Hello World");
	}
	
	public void nextCard() {
		if(cards.isEmpty()) {
			endRun();
		}else {
			currentCard = cards.get((int)(Math.random()*cards.size()));
	        cards.remove(currentCard);
	        clock2.start();
		}
	}
	
	public void checkCard() {
		times[cardNum] = clock2.getTime();
		//TODO
	}
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		panel.setSize(getWidth()-32, getHeight()-32);
		
		clock1.setBounds(10, 10, 200, 30);
        clock1.setFont(new Font("Arial", Font.PLAIN, clock1.getHeight()/2));
        
        clock2.setBounds(10, 40, 200, 30);
        clock2.setFont(new Font("Arial", Font.PLAIN, clock2.getHeight()/2));
	}
	
	private class ClockLabel extends JLabel {
		
		private final int pos;
		private Runnable action;
		private int time;
		private int limit = 0;
		private boolean limited = false;
		ScheduledExecutorService executor=null;
		
		public ClockLabel(String fixtext, int limit, Runnable action) {
			super(fixtext);
			pos = fixtext.length();
			this.limited = limit>0;
			this.limit = limit;
			this.action = action;
		}
		
		public void stop() {
			executor.shutdown();
			executor = null;
		}
		
		public int getTime() {
			return time;
		}
		
		public void start() {
			time=0;
			if(executor==null) {
				executor = Executors.newSingleThreadScheduledExecutor();
				executor.scheduleAtFixedRate(()-> {
						time++;
						setText(getText().substring(0, pos)+time);
						if(limited&&time>=limit) {
							action.run();
						}
					}, 0, 1, TimeUnit.SECONDS);
			}
		}
		
	}
}