package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.data.*;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vokabeltrainer.model.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

@SuppressWarnings("serial")
public class VocableTrainerRunPanel extends VocableTrainerPanel {
	
	private VocableTrainerRunSettings settings;
	
	private int[] times;
	private int[] results;
	private int cardNum;
	private ArrayList<Karte> cards;
	private Karte currentCard;
	
	
	private JLabel clock1;
	private JLabel clock2;
	private Timer timer1;
	private Timer timer2;
	private int time1;
	private int time2;
	
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
		
        cards = VokabeltrainerDB.getKarten(settings.getBox().getNummer());
        times = settings.isCardLimit()?new int[settings.getCardLimit()]: new int[cards.size()];
        cardNum = 0;

		currentCard = cards.get((int)(Math.random()*cards.size()));
        cards.remove(currentCard);
        
		clock1 = new JLabel("Total Time: 0");
        clock2 = new JLabel("Card Time:  0");
        originalWord = new JLabel("Original Word");
        answer = new JLabel();
        input = new JTextField();
        
        
        timer1 = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time1++;
				clock1.setText("Card Time:  "+time1);
				if(settings.isTotalTimeLimit()&&time1>=settings.getTotalTimeLimit()) {
					checkCard();
					endRun();
				}
			}
		});
        timer2 = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time2++;
				clock2.setText("Total Time: "+time2);
				if(settings.isCardTimeLimit()&&time2>=settings.getCardTimeLimit()) {
					checkCard();
					nextCard();
				}
			}
		});
        
        panel.add(clock1);
        panel.add(clock2);
        panel.add(originalWord);
        panel.add(answer);
        panel.add(input);
        add(panel);
	}
	
	public void endRun() {
		timer1.stop();
		System.out.println("Hello World");
		//TODO stats screen and update erinnerung
	}
	
	public void nextCard() {
		if(cards.isEmpty()) {
			endRun();
		}else {
			currentCard = cards.get((int)(Math.random()*cards.size()));
	        cards.remove(currentCard);
		}
	}
	
	public void checkCard() {
		timer2.stop();
		times[cardNum]=time2;
		if(!input.getText().isEmpty()) {
			boolean correct;
			if(settings.isCaseSensitiv()) {
				correct = input.getText().equals(currentCard.getWortZwei());
			} else {
				correct = input.getText().equalsIgnoreCase(currentCard.getWortZwei());
			}
			results[cardNum]= correct?1:-1;
			input.setBackground(correct?Color.GREEN:Color.RED);
		    answer.setText("<html>Correct answer <span style='color:green;'>"+currentCard.getWortZwei()+"</span></html>");
		}
	}
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		panel.setSize(getWidth()-32, getHeight()-32);
		
		clock1.setBounds(10, 10, 200, 30);
        clock1.setFont(new Font("Arial", Font.PLAIN, clock1.getHeight()/2));
        
        clock2.setBounds(10, 40, 200, 30);
        clock2.setFont(new Font("Arial", Font.PLAIN, clock2.getHeight()/2));
        
        originalWord.setBounds(10, 70, panel.getWidth(), 20);
        originalWord.setFont(new Font("Arial", Font.PLAIN, clock2.getHeight()/2));
        
        answer.setBounds(10, 100, panel.getWidth(), 20);
        answer.setFont(new Font("Arial", Font.PLAIN, clock2.getHeight()/2));
        
        input.setBounds(10, 130, panel.getWidth(), 20);
        input.setFont(new Font("Arial", Font.PLAIN, clock2.getHeight()/2));
        
        
	}
}