package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.data.*;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vokabeltrainer.model.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("serial")
public class VocableTrainerRunPanel extends VocableTrainerPanel {
	
	private static final Object DB_LOCK = new Object();
	
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
	private JButton end;
	
	private boolean cardOne = true;
	
	public VocableTrainerRunPanel (VocableTrainerFrame vtf, VocableTrainerRunSettings settings) throws IllegalArgumentException{
		super();
		this.vtf = vtf;
		this.settings = settings;
		
		setBackground(textColor1);
		setLayout(null);
		
		
		panel = new VocableTrainerPanel();
		panel.setLocation(16, 16);
		panel.setBackground(mainBackgroundColor);
		
		panel.setLayout(null);
		
		if (settings.getBox().getNummer() == -11) {
			cards = new ArrayList<Karte>();
			List<Fach> faecher = VokabeltrainerDB.getFaecherErinnerung(settings.getSet().getNummer());
			for (Fach fach: faecher) {
				if (fach != null) {
					cards.addAll(
							VokabeltrainerDB.getKarten(fach.getNummer()));
				}
			}
			if (cards.size() <= 0) {
				vtf.changePanel(5);
				vtf.remove(VocableTrainerRunPanel.this);
				throw new IllegalArgumentException("");
			}
		} else if (settings.getBox().getNummer() == -22) {
			cards = (ArrayList<Karte>) VokabeltrainerDB.getKartenUndBoxenVonLernkartei(settings.getSet().getNummer());
			if (cards.size() <= 0) {
				vtf.changePanel(5);
				vtf.remove(VocableTrainerRunPanel.this);
				throw new IllegalArgumentException("");
			}
		} else {
			cards = VokabeltrainerDB.getKarten(settings.getBox().getNummer());
		}
        times = settings.isCardLimit()?new int[settings.getCardLimit()<cards.size()?settings.getCardLimit():cards.size()]: new int[cards.size()];
        results = new int[times.length];
        cardNum = -1;

        clock1 = new JLabel(VocableTrainerLocalization.RUN_TIME_TOTAL_TIME+"0");
        clock2 = new JLabel(VocableTrainerLocalization.RUN_TIME_TIME_PER_CARD+"0");
        
        originalWord = new JLabel();
        originalWord.setForeground(textColor2);
        answer = new JLabel();
        answer.setForeground(textColor2);
        
        input = new JTextField();
        input.setForeground(textColor2);
        input.setBackground(textColor1);
        input.setBorder(null);
        
        stop = new JButton(VocableTrainerLocalization.RUN_STOP);
        stop.setForeground(textColor1);
        stop.setBackground(buttonBackgroundColor);
        stop.setFocusPainted(false);
        stop.setBorderPainted(false);
        stop.setMnemonic('S');
        skip = new JButton(VocableTrainerLocalization.RUN_SKIP);
        skip.setForeground(textColor1);
        skip.setBackground(buttonBackgroundColor);
        skip.setFocusPainted(false);
        skip.setBorderPainted(false);
        skip.setMnemonic('K');
        next = new JButton(VocableTrainerLocalization.RUN_NEXT);
        next.setForeground(textColor1);
        next.setBackground(buttonBackgroundColor);
        next.setFocusPainted(false);
        next.setBorderPainted(false);
        next.setMnemonic('C');
        
        originalWord.setVerticalAlignment(SwingConstants.TOP);
        originalWord.setHorizontalAlignment(SwingConstants.CENTER);
        answer.setVerticalAlignment(SwingConstants.TOP);
        answer.setHorizontalAlignment(SwingConstants.CENTER);
        input.setHorizontalAlignment(SwingConstants.CENTER);
        
        answer.setText(VocableTrainerLocalization.RUN_ANSWER);
        input.setText("");
        input.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {}
			
			@Override
			public void focusGained(FocusEvent e) {
				input.selectAll();				
			}
		});
        
        input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (timer.isRunning()) {
                        checkCard();
                    } else {
                        nextCard();
                    }
                }
			}
		});
        
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
				skipCard();
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
        
        timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time1++;
				time2++;
				clock1.setText(VocableTrainerLocalization.RUN_TIME_TOTAL_TIME+String.format("%.2f", time1/100.0));
				if(settings.isTotalTimeLimit()&&time1/100>=settings.getTotalTimeLimit()) {
					checkCard();
					endRun();
				}
				clock2.setText(VocableTrainerLocalization.RUN_TIME_TIME_PER_CARD+String.format("%.2f", time2/100.0));
				if(settings.isCardTimeLimit()&&time2/100>=settings.getCardTimeLimit()) {
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
	    if (timer.isRunning()) {
	        timer.stop();
	    }
	    new Thread(() -> {
	        synchronized (DB_LOCK) {
	            boolean repeat = true;
	            while (repeat) {
	                try {
	                    if (settings.getBox().getNummer() == -11) {
	                        for (Fach f : VokabeltrainerDB.getFaecherErinnerung(settings.getSet().getNummer())) {
	                            f.setGelerntAm(new Date());
	                        }
	                    } else if (settings.getBox().getNummer() == -22) {
	                        for (Fach f : VokabeltrainerDB.getFaecher(settings.getSet().getNummer())) {
	                            f.setGelerntAm(new Date());
	                        }
	                    } else {
	                        settings.getBox().setGelerntAm(new Date());
	                    }
	                    repeat = false;
	                } catch (Exception e) {
	                    try {
	                        Thread.sleep(500);
	                    } catch (InterruptedException e1) {}
	                }
	            }
	        }
	    }).start();
	    loadStats();
	}


	public void nextCard() {
		cardNum++;
		if(cardNum<times.length){
			currentCard = cards.get((int)(Math.random()*cards.size()));
	        cards.remove(currentCard);
	        if (settings.getDirection() == 1 || (settings.getDirection() == 0 && Math.round(Math.random()) == 1)) {
		        originalWord.setText(currentCard.getWortEins());
		        cardOne = true;
	        } else {
		        originalWord.setText(currentCard.getWortZwei());
		        cardOne = false;
	        }
	        skip.setVisible(true);
			next.setText(VocableTrainerLocalization.RUN_NEXT);
			next.setMnemonic('e');
			input.setBackground(textColor1);
			input.setText("");
			input.selectAll();
			answer.setText(VocableTrainerLocalization.RUN_ANSWER);
	        time2=0;
	        clock2.setText(VocableTrainerLocalization.RUN_TIME_TIME_PER_CARD+"0");
	        timer.start();
		} else {
			endRun();
		} 
	}
	
	public void checkCard() {
	    timer.stop();
	    times[cardNum] = time2;
	    boolean correct;

	    if (settings.isCaseSensitiv()) {
	        correct = cardOne ? input.getText().equals(currentCard.getWortZwei())
	                          : input.getText().equals(currentCard.getWortEins());
	    } else {
	        correct = cardOne ? input.getText().equalsIgnoreCase(currentCard.getWortZwei())
	                          : input.getText().equalsIgnoreCase(currentCard.getWortEins());
	    }

	    results[cardNum] = correct ? 1 : -1;
	    input.setBackground(correct ? Color.GREEN : Color.RED);

	    if (!settings.isParcticeRun()) {
	        new Thread(() -> {
	            synchronized (DB_LOCK) {
	                boolean repeat = true;
	                while (repeat) {
	                    try {
	                        if (correct) {
	                            if (VokabeltrainerDB.setKarteRichtig(currentCard) == -2) {
	                                Fach fach = new Fach();
	                                fach.setBeschreibung("SetReminder");

	                                VokabeltrainerDB.hinzufuegenFach(settings.getSet().getNummer(), fach);

	                                VokabeltrainerDB.setKarteRichtig(currentCard);
	                            }
	                        } else {
	                            VokabeltrainerDB.setKarteFalsch(currentCard);
	                        }
	                        repeat = false;
	                    } catch (Exception e) {
	                        try {
	                            Thread.sleep(500);
	                        } catch (InterruptedException e1) {
	                            Thread.currentThread().interrupt();
	                        }
	                    }
	                }
	            }
	        }).start();
	    }

	    if (cardOne) {
	        answer.setText("<html>Correct answer: <span style='color:green;'>" + currentCard.getWortZwei() + "</span></html>");
	    } else {
	        answer.setText("<html>Correct answer: <span style='color:green;'>" + currentCard.getWortEins() + "</span></html>");
	    }

	    skip.setVisible(false);
	    next.setText(VocableTrainerLocalization.RUN_NEXT);
	}

	
	public void skipCard() {
		timer.stop();
		times[cardNum]=time2;
		nextCard();
	}
	
	public void loadStats() {
		running = false;
		panel.removeAll();
		stat = new ArrayList<JLabel>();
		value = new ArrayList<JLabel>();
		end = new JButton(VocableTrainerLocalization.RUN_END);
		end.setForeground(textColor1);
		end.setBackground(buttonBackgroundColor);
		end.setFocusPainted(false);
		end.setBorderPainted(false);
		end.setMnemonic('n');
		end.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vtf.changePanel(1);
				vtf.remove(VocableTrainerRunPanel.this);
			}
		});
		
		int minTime=Integer.MAX_VALUE, maxTime=0;
		double avgTime=0;
		int correctCards=0, wrongCards=0, skippedCards=0, maxCardStreak=0, cardStreak=0;
		
		for (int i = 0; i < times.length; i++) {
			minTime = times[i]<minTime&&results[i]==1?times[i]:minTime;
			maxTime = times[i]>maxTime?times[i]:maxTime;
			avgTime += times[i];
			correctCards += results[i]==1?1:0;
			wrongCards += results[i]==-1?1:0;
			skippedCards += results[i]==0?1:0;
			
			cardStreak = results[i]==1?cardStreak+1:0;
			maxCardStreak = cardStreak>maxCardStreak?cardStreak:maxCardStreak;
		}
		avgTime = avgTime/times.length;
		
		stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_TOTAL_TIME+": "));
		value.add(new JLabel(String.format("%.2f", time1/100.0)+" seconds"));
		
		stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_MAX_CARD_TIME+": "));
		value.add(new JLabel(String.format("%.2f", maxTime/100.0)+" seconds"));

		stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_MIN_CARD_TIME+": "));
		value.add(new JLabel(correctCards!=0?String.format("%.2f", minTime/100.0)+" seconds":"No correct answer"));

		stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_AVG_CARD_TIME+": "));
		value.add(new JLabel(String.format("%.2f", avgTime/100)+" seconds"));
		
		stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_TOTAL_CARDS+": "));
		value.add(new JLabel(Integer.toString(results.length)));

		stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_SKIPPED_CARDS+": "));
		value.add(new JLabel(Integer.toString(skippedCards)));

		stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_WRONG_ANSWERS+": "));
		value.add(new JLabel(Integer.toString(wrongCards)));
		
		stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_CORRECT_ANSWERS+": "));
		value.add(new JLabel(Integer.toString(correctCards)));

		stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_MAX_STREAK+": "));
		value.add(new JLabel(Integer.toString(maxCardStreak)));
		
		stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_ACCURACY+": "));
		value.add(new JLabel(Integer.toString(100*correctCards/(correctCards+wrongCards))+"%"));

		for (JLabel label : stat) {
			panel.add(label);
		}
		for (JLabel label : value) {
			panel.add(label);
		}
		panel.add(end);
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
	        originalWord.setFont(new Font("Arial", Font.PLAIN, originalWord.getHeight()/2));
	        
	        answer.setBounds(10, 10+height/9*4, width-20, height/9-10);
	        answer.setFont(new Font("Arial", Font.PLAIN, answer.getHeight()/3*2));
	        
	        input.setBounds(10, 10+height/9*5, width-20, height/9*2-10);
	        input.setFont(new Font("Arial", Font.PLAIN, input.getHeight()/2));
	        
	        stop.setBounds(10, 10+height/9*7, (width-40)/3, height/9*2-20);
	        stop.setFont(new Font("Arial", Font.PLAIN, stop.getHeight()/2));
	        
	        skip.setBounds(20+(width-40)/3, 10+height/9*7, (width-40)/3, height/9*2-20);
	        skip.setFont(new Font("Arial", Font.PLAIN, skip.getHeight()/2));
	        
	        next.setBounds(30+(width-40)/3*2, 10+height/9*7, (width-40)/3, height/9*2-20);
	        next.setFont(new Font("Arial", Font.PLAIN, next.getHeight()/2));
		}else {
			end.setBounds(10, height-height/10*2, width-20, height/10*2-10);
			end.setFont(new Font("Arial", Font.PLAIN, end.getHeight()/2));
			height -= height/10*2;
			for (int i = 0; i < stat.size(); i++) {
				stat.get(i).setBounds(10, 10+i*((height-20)/stat.size()), (width-30)/2, (height-20)/stat.size()-10);
				stat.get(i).setFont(new Font("Arial", Font.PLAIN, stat.get(i).getHeight()/2));
			}
			for (int i = 0; i < value.size(); i++) {
				value.get(i).setBounds(width/2+5, 10+i*((height-20)/value.size()), (width-30)/2, (height-20)/value.size()-10);
				value.get(i).setFont(new Font("Arial", Font.PLAIN, value.get(i).getHeight()/2));
			}
		}
    }
}