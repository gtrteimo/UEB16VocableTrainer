package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.data.*;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vokabeltrainer.model.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	private Timer timer;
	private int time1;
	private int time2;
	
	private JLabel originalWord;
	private JLabel answer;
	private JTextField input;
	
	private JButton stop;
	private JButton skip;
	private JButton next;
	
	private boolean running=true;
	private ArrayList<JLabel> stat;
	private ArrayList<JLabel> value;

	
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
        originalWord.setForeground(C_nigth);
        answer = new JLabel();
        answer.setForeground(C_nigth);
        
        input = new JTextField();
        input.setForeground(C_nigth);
        input.setBackground(C_platinum);
        input.setBorder(null);
        
        stop = new JButton("Stop");
        stop.setForeground(C_nigth);
        stop.setBackground(C_platinum);
        stop.setFocusPainted(false);
        stop.setBorderPainted(false);
        stop.setMnemonic('S');
        skip = new JButton("Skip");
        skip.setForeground(C_nigth);
        skip.setBackground(C_platinum);
        skip.setFocusPainted(false);
        skip.setBorderPainted(false);
        skip.setMnemonic('K');
        next = new JButton("Check");
        next.setForeground(C_nigth);
        next.setBackground(C_platinum);
        next.setFocusPainted(false);
        next.setBorderPainted(false);
        next.setMnemonic('C');
        
        originalWord.setVerticalAlignment(SwingConstants.TOP);
        originalWord.setHorizontalAlignment(SwingConstants.CENTER);
        answer.setVerticalAlignment(SwingConstants.TOP);
        answer.setHorizontalAlignment(SwingConstants.CENTER);
        input.setHorizontalAlignment(SwingConstants.CENTER);
        
        stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(timer.isRunning()) {
					timer.stop();
				}
				vtf.changePanel(1);
				vtf.remove(VocableTrainerRunPanel.this);
			}
		});
        
        skip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				input.setText("");
				checkCard();
			}
		});
        
        next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(timer.isRunning()) {
					checkCard();
				} else {
					nextCard();
				}
				
			}
		});
        
        timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time1++;
				clock1.setText("Total Time: "+time1);
				if(settings.isTotalTimeLimit()&&time1>=settings.getTotalTimeLimit()) {
					checkCard();
					endRun();
				}
				time2++;
				clock2.setText("Card Time: "+time2);
				if(settings.isCardTimeLimit()&&time2>=settings.getCardTimeLimit()) {
					checkCard();
				}
			}
		});
        
        nextCard();
        timer.start();
        
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
		if(timer.isRunning())
			timer.stop();
		loadStats();
		//TODO update erinnerung
	}
	
	public void nextCard() {
		if(cardNum>=times.length-1) {
			endRun();
		}else {
			cardNum++;
			currentCard = cards.get((int)(Math.random()*cards.size()));
	        cards.remove(currentCard);
	        originalWord.setText(currentCard.getWortEins());
	        skip.setVisible(true);
			next.setText("Check");
			next.setMnemonic('C');
			input.setBackground(C_platinum);
			input.setText("");
			answer.setText("");
	        time2=0;
	        timer.start();
		}
	}
	
	public void checkCard() {
		timer.stop();
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
		next.setMnemonic('X');
	}
	
	public void loadStats() {
		running = false;
		panel.removeAll();
		stat = new ArrayList<JLabel>();
		value = new ArrayList<JLabel>();
		
		int minTime=0, maxTime=0;
		double avgTime=0;
		int correctCards=0, wrongCards=0, skippedCards=0;
		
		for (int i = 0; i < times.length; i++) {
			minTime = times[i]<minTime?times[i]:minTime;
			maxTime = times[i]>maxTime?times[i]:maxTime;
			avgTime += times[i];
			correctCards += results[i]==1?1:0;
			wrongCards += results[i]==-1?1:0;
			skippedCards += results[i]==0?1:0;
		}
		avgTime = avgTime/times.length;
		
		stat.add(new JLabel("Total Time: "));
		value.add(new JLabel(Integer.toString(time1)));
		
		stat.add(new JLabel("Total Cards: "));
		value.add(new JLabel(Integer.toString(results.length)));
		
		stat.add(new JLabel("Avg Card Time: "));
		value.add(new JLabel(Double.toString(avgTime)));
		
		stat.add(new JLabel("Max Card Time: "));
		value.add(new JLabel(Integer.toString(maxTime)));

		stat.add(new JLabel("Min Card Time: "));
		value.add(new JLabel(Integer.toString(minTime)));

		stat.add(new JLabel("Skipped Cards: "));
		value.add(new JLabel(Integer.toString(skippedCards)));

		stat.add(new JLabel("Correct Answers: "));
		value.add(new JLabel(Integer.toString(correctCards)));

		stat.add(new JLabel("Wrong Answers: "));
		value.add(new JLabel(Integer.toString(wrongCards)));

		for (JLabel label : stat) {
			panel.add(label);
		}
		for (JLabel label : value) {
			panel.add(label);
		}
		//TODO a button to exit the stats and go back to the rest of the program
		repaint();
	}
	
	@Override
	public void paintComponent (Graphics g) {		
		
		super.paintComponentRun(g);
		
		int width = panel.getWidth();
		int height = panel.getHeight();
		if(running) {
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
		}else {
			for (int i = 0; i < stat.size(); i++) {
				stat.get(i).setBounds(10, 10+i*((height-20)/stat.size()), (width-30)/2, (height-20)/stat.size()-10);
			}
			for (int i = 0; i < value.size(); i++) {
				value.get(i).setBounds(width/2+5, 10+i*((height-20)/value.size()), (width-30)/2, (height-20)/value.size()-10);
			}
		}
    }
}