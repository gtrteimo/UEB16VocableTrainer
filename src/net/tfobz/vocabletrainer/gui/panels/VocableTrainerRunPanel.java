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

/**
 * VocableTrainerRunPanel is responsible for managing the vocabulary training session.
 * It handles the display of words, user input, timing, and tracking of results.
 * 
 * <p>This panel interacts with the VocableTrainerFrame to switch between different panels
 * and updates the database with the user's progress.</p>
 * 
 * @see VocableTrainerFrame
 * @see VocableTrainerRunSettings
 * @see VokabeltrainerDB
 */
@SuppressWarnings("serial")
public class VocableTrainerRunPanel extends VocableTrainerPanel {
    
    // Lock object for synchronizing database operations
    private static final Object DB_LOCK = new Object();
    
    // Configuration settings for the current training run
    private VocableTrainerRunSettings settings;
    
    // Arrays to store time taken and results for each card
    private int[] times;
    private int[] results;
    private int cardNum;
    private ArrayList<Karte> cards;
    private Karte currentCard;
    
    // UI components for displaying timers and words
    private JLabel clock1;
    private JLabel clock2;
    private Timer timer;
    private int time1;
    private int time2;
    
    private JLabel originalWord;
    private JLabel answer;
    private JTextField input;
    
    // Control buttons
    private JButton stop;
    private JButton skip;
    private JButton next;
    
    // Statistics components
    private boolean running = true;
    private ArrayList<JLabel> stat;
    private ArrayList<JLabel> value;
    private JButton end;
    
    // Flag to determine which word to display first
    private boolean cardOne = true;
    
    /**
     * Constructs a new VocableTrainerRunPanel with the specified frame and settings.
     * Initializes the UI components and starts the training session.
     * 
     * @param vtf      the main frame of the Vocable Trainer application
     * @param settings the settings for the current training run
     * @throws IllegalArgumentException if no cards are available for the selected settings
     */
    public VocableTrainerRunPanel(VocableTrainerFrame vtf, VocableTrainerRunSettings settings) throws IllegalArgumentException {
        super();
        this.vtf = vtf;
        this.settings = settings;
        
        setBackground(textColor1);
        setLayout(null);
        
        panel = new VocableTrainerPanel();
        panel.setLocation(16, 16);
        panel.setBackground(mainBackgroundColor);
        panel.setLayout(null);
        
        initializeCards();
        initializeTimers();
        initializeUIComponents();
        initializeEventListeners();
        
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
    
    /**
     * Initializes the list of cards based on the current settings.
     * Throws an exception if no cards are found.
     * 
     * @throws IllegalArgumentException if the card list is empty
     */
    private void initializeCards() throws IllegalArgumentException {
        if (settings.getBox().getNummer() == -11) {
            cards = new ArrayList<Karte>();
            List<Fach> faecher = VokabeltrainerDB.getFaecherErinnerung(settings.getSet().getNummer());
            for (Fach fach : faecher) {
                if (fach != null) {
                    cards.addAll(VokabeltrainerDB.getKarten(fach.getNummer()));
                }
            }
            if (cards.isEmpty()) {
                endRunDueToNoCards();
            }
        } else if (settings.getBox().getNummer() == -22) {
            cards = (ArrayList<Karte>) VokabeltrainerDB.getKartenUndBoxenVonLernkartei(settings.getSet().getNummer());
            if (cards.isEmpty()) {
                endRunDueToNoCards();
            }
        } else {
            cards = VokabeltrainerDB.getKarten(settings.getBox().getNummer());
        }
        times = settings.isCardLimit() 
                ? new int[Math.min(settings.getCardLimit(), cards.size())] 
                : new int[cards.size()];
        results = new int[times.length];
        cardNum = -1;
    }
    
    /**
     * Ends the run if no cards are available and switches to the appropriate panel.
     */
    private void endRunDueToNoCards() {
        vtf.changePanel(5);
        vtf.remove(this);
        throw new IllegalArgumentException("No cards available for the selected settings.");
    }
    
    /**
     * Initializes the timer components for tracking total and per-card time.
     */
    private void initializeTimers() {
        clock1 = new JLabel(VocableTrainerLocalization.RUN_TIME_TOTAL_TIME + "0");
        clock2 = new JLabel(VocableTrainerLocalization.RUN_TIME_TIME_PER_CARD + "0");
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimers();
            }
        });
    }
    
    /**
     * Updates the total and per-card timers, and checks for time limits.
     */
    private void updateTimers() {
        time1++;
        time2++;
        clock1.setText(VocableTrainerLocalization.RUN_TIME_TOTAL_TIME + String.format("%.2f", time1 / 100.0));
        
        if (settings.isTotalTimeLimit() && time1 / 100 >= settings.getTotalTimeLimit()) {
            checkCard();
            endRun();
        }
        
        clock2.setText(VocableTrainerLocalization.RUN_TIME_TIME_PER_CARD + String.format("%.2f", time2 / 100.0));
        
        if (settings.isCardTimeLimit() && time2 / 100 >= settings.getCardTimeLimit()) {
            checkCard();
        }
    }
    
    /**
     * Initializes the UI components such as labels, text fields, and buttons.
     */
    private void initializeUIComponents() {
        originalWord = new JLabel();
        originalWord.setForeground(textColor2);
        answer = new JLabel();
        answer.setForeground(textColor2);
        
        input = new JTextField();
        input.setForeground(textColor2);
        input.setBackground(textColor1);
        input.setBorder(null);
        
        stop = createButton(VocableTrainerLocalization.RUN_STOP, 'S');
        skip = createButton(VocableTrainerLocalization.RUN_SKIP, 'K');
        next = createButton(VocableTrainerLocalization.RUN_NEXT, 'C');
        
        originalWord.setVerticalAlignment(SwingConstants.TOP);
        originalWord.setHorizontalAlignment(SwingConstants.CENTER);
        answer.setVerticalAlignment(SwingConstants.TOP);
        answer.setHorizontalAlignment(SwingConstants.CENTER);
        input.setHorizontalAlignment(SwingConstants.CENTER);
        
        answer.setText(VocableTrainerLocalization.RUN_ANSWER);
        input.setText("");
    }
    
    /**
     * Creates a JButton with the specified text and mnemonic.
     * 
     * @param text     the text to display on the button
     * @param mnemonic the mnemonic key for the button
     * @return the configured JButton
     */
    private JButton createButton(String text, char mnemonic) {
        JButton button = new JButton(text);
        button.setForeground(textColor1);
        button.setBackground(buttonBackgroundColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMnemonic(mnemonic);
        return button;
    }
    
    /**
     * Initializes event listeners for input fields and buttons.
     */
    private void initializeEventListeners() {
        input.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                // No action needed on focus lost
            }
            
            @Override
            public void focusGained(FocusEvent e) {
                input.selectAll();                
            }
        });
        
        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleEnterKey(e);
            }
        });
        
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStopAction();
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
                handleNextAction();
            }
        });
    }
    
    /**
     * Handles the action when the Enter key is pressed in the input field.
     * 
     * @param e the KeyEvent triggered by the key press
     */
    private void handleEnterKey(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (timer.isRunning()) {
                checkCard();
            } else {
                nextCard();
            }
        }
    }
    
    /**
     * Handles the action when the Stop button is pressed.
     * Stops the timer and switches to the main panel.
     */
    private void handleStopAction() {
        if (timer.isRunning()) {
            timer.stop();
        }
        vtf.changePanel(1);
        vtf.remove(this);
    }
    
    /**
     * Handles the action when the Next button is pressed.
     * Checks the current card or proceeds to the next card based on the timer state.
     */
    private void handleNextAction() {
        if (timer.isRunning()) {
            checkCard();
        } else {
            nextCard();
        }
    }
    
    /**
     * Ends the current training run, updates the database, and loads statistics.
     */
    public void endRun() {
        if (timer.isRunning()) {
            timer.stop();
        }
        updateDatabaseAfterRun();
        loadStats();
    }
    
    /**
     * Updates the database with the learning progress after the run has ended.
     */
    private void updateDatabaseAfterRun() {
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
                        } catch (InterruptedException e1) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }).start();
    }
    
    /**
     * Loads and displays the statistics of the training run.
     * Updates the UI to show various performance metrics.
     */
    public void loadStats() {
        running = false;
        panel.removeAll();
        stat = new ArrayList<JLabel>();
        value = new ArrayList<JLabel>();
        end = createEndButton();
        
        calculateStatistics();
        displayStatistics();
        
        panel.add(end);
        repaint();
    }
    
    /**
     * Creates the End button used to finalize the training run.
     * 
     * @return the configured End JButton
     */
    private JButton createEndButton() {
        JButton endButton = new JButton(VocableTrainerLocalization.RUN_END);
        endButton.setForeground(textColor1);
        endButton.setBackground(buttonBackgroundColor);
        endButton.setFocusPainted(false);
        endButton.setBorderPainted(false);
        endButton.setMnemonic('n');
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vtf.changePanel(1);
                vtf.remove(VocableTrainerRunPanel.this);
            }
        });
        return endButton;
    }
    
    /**
     * Calculates various statistics based on the training run.
     */
    private void calculateStatistics() {
        int minTime = Integer.MAX_VALUE, maxTime = 0;
        double avgTime = 0;
        int correctCards = 0, wrongCards = 0, skippedCards = 0, maxCardStreak = 0, cardStreak = 0;
        
        for (int i = 0; i < times.length; i++) {
            if (results[i] == 1) {
                minTime = Math.min(minTime, times[i]);
                correctCards++;
                cardStreak++;
                maxCardStreak = Math.max(maxCardStreak, cardStreak);
            } else {
                if (results[i] == -1) {
                    wrongCards++;
                } else if (results[i] == 0) {
                    skippedCards++;
                }
                cardStreak = 0;
            }
            maxTime = Math.max(maxTime, times[i]);
            avgTime += times[i];
        }
        avgTime /= times.length;
        
        stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_TOTAL_TIME + ": "));
        value.add(new JLabel(String.format("%.2f", time1 / 100.0) + " " + VocableTrainerLocalization.DATA_SECONDS));
        
        stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_MAX_CARD_TIME + ": "));
        value.add(new JLabel(String.format("%.2f", maxTime / 100.0) + " " + VocableTrainerLocalization.DATA_SECONDS));
        
        stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_MIN_CARD_TIME + ": "));
        value.add(new JLabel(correctCards != 0 ? String.format("%.2f", minTime / 100.0) + " " + VocableTrainerLocalization.DATA_SECONDS : "No correct answer"));
        
        stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_AVG_CARD_TIME + ": "));
        value.add(new JLabel(String.format("%.2f", avgTime / 100) + " " + VocableTrainerLocalization.DATA_SECONDS));
        
        stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_TOTAL_CARDS + ": "));
        value.add(new JLabel(Integer.toString(results.length)));
        
        stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_SKIPPED_CARDS + ": "));
        value.add(new JLabel(Integer.toString(skippedCards)));
        
        stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_WRONG_ANSWERS + ": "));
        value.add(new JLabel(Integer.toString(wrongCards)));
        
        stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_CORRECT_ANSWERS + ": "));
        value.add(new JLabel(Integer.toString(correctCards)));
        
        stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_MAX_STREAK + ": "));
        value.add(new JLabel(Integer.toString(maxCardStreak)));
        
        stat.add(new JLabel(VocableTrainerLocalization.RUN_STATS_ACCURACY + ": "));
        value.add(new JLabel(correctCards + wrongCards != 0 
            ? Integer.toString(100 * correctCards / (correctCards + wrongCards)) + "%" 
            : "N/A"));
    }
    
    /**
     * Displays the calculated statistics on the panel.
     */
    private void displayStatistics() {
        for (JLabel label : stat) {
            panel.add(label);
        }
        for (JLabel label : value) {
            panel.add(label);
        }
    }
    
    /**
     * Moves to the next card in the training session.
     * If no more cards are available, ends the run.
     */
    public void nextCard() {
        cardNum++;
        if (cardNum < times.length) {
            selectNextCard();
        } else {
            endRun();
        } 
    }
    
    /**
     * Selects the next card randomly and updates the UI accordingly.
     */
    private void selectNextCard() {
        currentCard = cards.get((int)(Math.random() * cards.size()));
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
        time2 = 0;
        clock2.setText(VocableTrainerLocalization.RUN_TIME_TIME_PER_CARD + "0");
        timer.start();
    }
    
    /**
     * Checks the user's answer for the current card and updates the result.
     */
    public void checkCard() {
        timer.stop();
        times[cardNum] = time2;
        boolean correct = isAnswerCorrect();
        
        results[cardNum] = correct ? 1 : -1;
        input.setBackground(correct ? Color.GREEN : Color.RED);
        
        
        
        if (!settings.isParcticeRun()) {
            updateDatabaseWithResult(correct);
        }
        
        displayCorrectAnswer();
        skip.setVisible(false);
        next.setText(VocableTrainerLocalization.RUN_NEXT);
    }
    
    /**
     * Determines if the user's input is correct based on the current card and settings.
     * 
     * @return true if the answer is correct, false otherwise
     */
    private boolean isAnswerCorrect() {
        String userInput = input.getText();
        if (settings.isCaseSensitiv()) {
            return cardOne ? userInput.equals(currentCard.getWortZwei())
                           : userInput.equals(currentCard.getWortEins());
        } else {
            return cardOne ? userInput.equalsIgnoreCase(currentCard.getWortZwei())
                           : userInput.equalsIgnoreCase(currentCard.getWortEins());
        }
    }
    
    /**
     * Updates the database with the result of the current card.
     * 
     * @param correct true if the answer was correct, false otherwise
     */
    private void updateDatabaseWithResult(boolean correct) {
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
    
    /**
     * Displays the correct answer after the user has submitted their response.
     */
    private void displayCorrectAnswer() {
        if (cardOne) {
            answer.setText("<html>Correct answer: <span style='color:green;'>" + currentCard.getWortZwei() + "</span></html>");
        } else {
            answer.setText("<html>Correct answer: <span style='color:green;'>" + currentCard.getWortEins() + "</span></html>");
        }
    }
    
    /**
     * Skips the current card and moves to the next one.
     */
    public void skipCard() {
        timer.stop();
        times[cardNum] = time2;
        nextCard();
    }
    
    /**
     * Overrides the paintComponent method to layout UI components based on the current state.
     * 
     * @param g the Graphics object used for drawing
     */
    @Override
    public void paintComponent(Graphics g) {        
        super.paintComponentRun(g);
        
        int width = panel.getWidth();
        int height = panel.getHeight();
        
        if (running) {
            layoutTrainingComponents(width, height);
        } else {
            layoutStatisticsComponents(width, height);
        }
    }
    
    /**
     * Lays out the UI components during the training session.
     * 
     * @param width  the width of the panel
     * @param height the height of the panel
     */
    private void layoutTrainingComponents(int width, int height) {
        // Position and set font for total time label
        clock1.setBounds(10, 10, width - 20, height / 9 - 10);
        clock1.setFont(new Font("Arial", Font.PLAIN, clock1.getHeight() / 2));
        
        // Position and set font for per-card time label
        clock2.setBounds(10, 10 + height / 9, width - 20, height / 9 - 10);
        clock2.setFont(new Font("Arial", Font.PLAIN, clock2.getHeight() / 2));
        
        // Position and set font for original word label
        originalWord.setBounds(10, 10 + height / 9 * 2, width - 20, height / 9 * 2 - 10);
        originalWord.setFont(new Font("Arial", Font.PLAIN, originalWord.getHeight() / 2));
        
        // Position and set font for answer label
        answer.setBounds(10, 10 + height / 9 * 4, width - 20, height / 9 - 10);
        answer.setFont(new Font("Arial", Font.PLAIN, answer.getHeight() / 3 * 2));
        
        // Position and set font for input field
        input.setBounds(10, 10 + height / 9 * 5, width - 20, height / 9 * 2 - 10);
        input.setFont(new Font("Arial", Font.PLAIN, input.getHeight() / 2));
        
        // Position and set font for Stop button
        stop.setBounds(10, 10 + height / 9 * 7, (width - 40) / 3, height / 9 * 2 - 20);
        stop.setFont(new Font("Arial", Font.PLAIN, stop.getHeight() / 2));
        
        // Position and set font for Skip button
        skip.setBounds(20 + (width - 40) / 3, 10 + height / 9 * 7, (width - 40) / 3, height / 9 * 2 - 20);
        skip.setFont(new Font("Arial", Font.PLAIN, skip.getHeight() / 2));
        
        // Position and set font for Next button
        next.setBounds(30 + (width - 40) / 3 * 2, 10 + height / 9 * 7, (width - 40) / 3, height / 9 * 2 - 20);
        next.setFont(new Font("Arial", Font.PLAIN, next.getHeight() / 2));
    }
    
    /**
     * Lays out the UI components to display the training statistics.
     * 
     * @param width  the width of the panel
     * @param height the height of the panel
     */
    private void layoutStatisticsComponents(int width, int height) {
        // Position and set font for End button
        end.setBounds(10, height - height / 10 * 2, width - 20, height / 10 * 2 - 10);
        end.setFont(new Font("Arial", Font.PLAIN, end.getHeight() / 2));
        
        // Adjust height for statistics display
        height -= height / 10 * 2;
        
        // Position and set font for statistic labels
        for (int i = 0; i < stat.size(); i++) {
            stat.get(i).setBounds(10, 10 + i * ((height - 20) / stat.size()), (width - 30) / 2, (height - 20) / stat.size() - 10);
            stat.get(i).setFont(new Font("Arial", Font.PLAIN, stat.get(i).getHeight() / 2));
        }
        
        // Position and set font for statistic values
        for (int i = 0; i < value.size(); i++) {
            value.get(i).setBounds(width / 2 + 5, 10 + i * ((height - 20) / value.size()), (width - 30) / 2, (height - 20) / value.size() - 10);
            value.get(i).setFont(new Font("Arial", Font.PLAIN, value.get(i).getHeight() / 2));
        }
    }
}
