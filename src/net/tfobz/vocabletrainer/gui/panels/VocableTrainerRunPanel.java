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
		
		
		panel = new VocableTrainerPanel();
		panel.setLocation(16, 16);
		panel.setBackground(C_powderBlue);
		
		panel.setLayout(null);
		
        cards = VokabeltrainerDB.getKarten(settings.getBox().getNummer());
        times = settings.isCardLimit()?new int[settings.getCardLimit()]: new int[cards.size()];
        results = new int[times.length];
        cardNum = -1;

        clock1 = new JLabel("Total Time: 0");
        clock2 = new JLabel("Card Time:  0");
        originalWord = new JLabel();
        answer = new JLabel();
        input = new JTextField();
        stop = new JButton("Stop");
        skip = new JButton("Skip");
        next = new JButton("Check");
        
        originalWord.setVerticalAlignment(SwingConstants.TOP);
        originalWord.setHorizontalAlignment(SwingConstants.CENTER);
        answer.setVerticalAlignment(SwingConstants.TOP);
        answer.setHorizontalAlignment(SwingConstants.CENTER);
        input.setHorizontalAlignment(SwingConstants.CENTER);
        
        stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO back to start menu
				
			}
		});
        
        skip.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				input.setText("");
				checkCard();
				nextCard();
			}
		});
        
        next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(timer2.isRunning()) {
					checkCard();
				} else {
					nextCard();
				}
				
			}
		});
        
        timer1 = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time1++;
				clock1.setText("Total Time: "+time1);
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
				clock2.setText("Card Time: "+time2);
				if(settings.isCardTimeLimit()&&time2>=settings.getCardTimeLimit()) {
					checkCard();
				}
			}
		});
        
        nextCard();
        timer1.start();
        timer2.start();
        
        panel.add(clock1);
        panel.add(clock2);
        panel.add(originalWord);
        panel.add(answer);
        panel.add(input);
        panel.add(stop);
        panel.add(skip);
        panel.add(next);
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
			cardNum++;
			currentCard = cards.get((int)(Math.random()*cards.size()));
	        cards.remove(currentCard);
	        originalWord.setText(currentCard.getWortEins());
	        skip.setVisible(true);
			next.setText("Check");
			input.setBackground(Color.WHITE);//TODO correct Color
			input.setText("");
			answer.setText("");
	        time2=0;
	        timer2.start();
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
		}
	    answer.setText("<html>Correct answer: <span style='color:green;'>"+currentCard.getWortZwei()+"</span></html>");
		skip.setVisible(false);
		next.setText("Next");
	}
	
	@Override
	public void paintComponent (Graphics g) {		
		
		super.paintComponentRun(g);
		
		int width = panel.getWidth();
		int height = panel.getHeight();
		
		clock1.setBounds(10, 10, width-20, height/9-10);
        clock1.setFont(new Font("Arial", Font.PLAIN, clock1.getHeight()/2));
        
        clock2.setBounds(10, 10+height/9, width-20, height/9-10);
        clock2.setFont(new Font("Arial", Font.PLAIN, clock2.getHeight()/2));
        
        originalWord.setBounds(10, 10+height/9*2, width-20, height/9*2-10);
        originalWord.setFont(new Font("Arial", Font.PLAIN, originalWord.getHeight()/4));
        
        answer.setBounds(10, 10+height/9*4, width-20, height/9-10);
        answer.setFont(new Font("Arial", Font.PLAIN, answer.getHeight()/2));
        
        input.setBounds(10, 10+height/9*5, width-20, height/9*2-10);
        input.setFont(new Font("Arial", Font.PLAIN, input.getHeight()/4));
        
        stop.setBounds(10, 10+height/9*7, (width-40)/3, height/9*2-20);
        stop.setFont(new Font("Arial", Font.PLAIN, stop.getHeight()/2));
        
        skip.setBounds(20+(width-40)/3, 10+height/9*7, (width-40)/3, height/9*2-20);
        skip.setFont(new Font("Arial", Font.PLAIN, skip.getHeight()/2));
        
        next.setBounds(30+(width-40)/3*2, 10+height/9*7, (width-40)/3, height/9*2-20);
        next.setFont(new Font("Arial", Font.PLAIN, next.getHeight()/2));
    }
}