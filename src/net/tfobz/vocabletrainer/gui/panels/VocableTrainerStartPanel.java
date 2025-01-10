package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.data.VocableTrainerRunSettings;
import net.tfobz.vocabletrainer.data.VocableTrainerRunSettings.TimeUnit;
import net.tfobz.vocabletrainer.gui.*;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInfoDialog;
import net.tfobz.vokabeltrainer.model.*;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The {@code VocableTrainerStartPanel} class represents the starting panel of the Vocable Trainer application.
 * It allows users to configure settings before starting a training session, such as selecting sets, boxes,
 * time limits, and other options.
 * 
 * <p>This panel includes various UI components like checkboxes, spinners, combo boxes, and a start button.
 * Users can customize their training experience by adjusting these settings.</p>
 * 
 * <p>It handles the retrieval of available learning sets and boxes from the database and manages user interactions
 * to start a training session based on the selected configurations.</p>
 * 
 * @see VocableTrainerPanel
 * @see VocableTrainerRunSettings
 * @see VocableTrainerRunPanel
 */
@SuppressWarnings("serial")
public class VocableTrainerStartPanel extends VocableTrainerPanel {
    
    private List<Lernkartei> sets;
    private List<Fach> boxes;
    
    private JCheckBox[] options = new JCheckBox[6];
    private JSpinner[] optionSpinners = new JSpinner[3];
    private JComboBox<Lernkartei> setComboBox;
    private JComboBox<Fach> boxComboBoxes;
    private JComboBox<TimeUnit>[] optionComboBoxesTime = new JComboBox[2];
    private JComboBox<String> directionComboBox;
    private JButton start;
    
    /**
     * Constructs a new {@code VocableTrainerStartPanel} with the specified frame.
     * 
     * @param vtf the parent {@code VocableTrainerFrame}
     */
    public VocableTrainerStartPanel (VocableTrainerFrame vtf) {
        super(vtf);
        
        panel.loadImage();
        panel.setLayout(null);
        
        initializeOptions();
        initializeSpinners();
        initializeSetComboBox();
        initializeBoxComboBoxes();
        initializeTimeOptionComboBoxes();
        initializeStartButton();
        initializeDirectionComboBox();
        addComponentsToPanel();
        addPanelToFrame();
    }
    
    /**
     * Initializes the option checkboxes with mnemonics and default selections.
     */
    private void initializeOptions() {
        options[0] = new JCheckBox();
        options[0].setMnemonic('T');
        options[0].setSelected(true);
        
        options[1] = new JCheckBox();
        options[1].setMnemonic('O');
        options[1].setSelected(false);
        
        options[2] = new JCheckBox();
        options[2].setMnemonic('C');
        options[2].setSelected(true);
        
        options[3] = new JCheckBox();
        options[3].setMnemonic('A');
        options[3].setSelected(false);
        
        options[4] = new JCheckBox();
        options[4].setMnemonic('P');
        options[4].setSelected(false);
        
        options[5] = new JCheckBox();
        options[5].setMnemonic('D');
        options[5].setSelected(false);
        
        for (JCheckBox option : options) { 
            option.setForeground(textColor2);
            option.setBackground(mainBackgroundColor);
            option.setBorderPainted(false);
            option.setFocusPainted(false);
        }
    }
    
    /**
     * Initializes the option spinners with default values and listeners.
     */
    private void initializeSpinners() {
        SpinnerListener spinnerListener = new SpinnerListener();
        for (int i = 0; i < optionSpinners.length; i++) {
            optionSpinners[i] = new JSpinner();
            optionSpinners[i].setForeground(textColor2);
            optionSpinners[i].setBackground(mainBackgroundColor);
            optionSpinners[i].setBorder(null);
            optionSpinners[i].addChangeListener(spinnerListener);
            
            JComponent editor = optionSpinners[i].getEditor();
            if (editor instanceof JSpinner.DefaultEditor) {
                JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
                defaultEditor.getTextField().addKeyListener(spinnerListener);
                defaultEditor.getTextField().setBackground(textColor1);
                defaultEditor.getTextField().setForeground(textColor2);
            }
            
            for (Component component : optionSpinners[i].getComponents()) {
                component.setBackground(textColor1); 
                component.setForeground(textColor2);
            }
        }
        optionSpinners[0].setEnabled(true);
        optionSpinners[1].setEnabled(false);
        optionSpinners[2].setEnabled(false);
        
        optionSpinners[0].setValue(30);
        optionSpinners[1].setValue(5);
        optionSpinners[2].setValue(10);
    }
    
    /**
     * Initializes the set combo box with available learning sets.
     */
    private void initializeSetComboBox() {
        sets = VokabeltrainerDB.getLernkarteien();
        if (sets != null) {
            setComboBox = new JComboBox<Lernkartei>(sets.toArray(new Lernkartei[0]));
        } else {
            setComboBox = new JComboBox<Lernkartei>();
        }
        setComboBox.setForeground(textColor2);
        setComboBox.setBackground(textColor1);
        setComboBox.setBorder(null);
        
        setComboBox.addActionListener(e -> retriveBoxes());
    }
    
    /**
     * Initializes the box combo boxes with available boxes based on the selected set.
     */
    private void initializeBoxComboBoxes() {
        boxes = VokabeltrainerDB.getFaecher(setComboBox.getSelectedIndex());
        if (boxes != null) {
            boxComboBoxes = new JComboBox<Fach>(boxes.toArray(new Fach[0]));
        } else {
            boxComboBoxes = new JComboBox<Fach>();
        }
        boxComboBoxes.setForeground(textColor2);
        boxComboBoxes.setBackground(textColor1);
    }
    
    /**
     * Initializes the time unit combo boxes with available time units.
     */
    private void initializeTimeOptionComboBoxes() {
        for (int i = 0; i < optionComboBoxesTime.length; i++) {			
            optionComboBoxesTime[i] = new JComboBox<TimeUnit>(TimeUnit.values());
            optionComboBoxesTime[i].setForeground(textColor2);
            optionComboBoxesTime[i].setBackground(textColor1);
            optionComboBoxesTime[i].setRenderer(new TimeUnitListCellRenderer());
        }
        
        optionComboBoxesTime[0].setEnabled(true);
        optionComboBoxesTime[1].setEnabled(false);
        
        optionComboBoxesTime[0].setSelectedIndex(0);
        optionComboBoxesTime[1].setSelectedIndex(1);
    }
    
    /**
     * Initializes the start button with listeners and styles.
     */
    private void initializeStartButton() {
        start = new JButton();
        start.setForeground(textColor1);
        start.setBackground(buttonBackgroundColor);
        start.setFocusPainted(false);
        start.setBorderPainted(false);
        start.setMnemonic('S');
        start.addActionListener(e -> startRun());
    }
    
    /**
     * Initializes the direction combo box with direction options.
     */
    private void initializeDirectionComboBox() {
        directionComboBox = new JComboBox<String>();
        directionComboBox.setForeground(textColor2);
        directionComboBox.setBackground(textColor1);
        directionComboBox.setBorder(null);
        directionComboBox.setEnabled(false);
        
        options[0].addActionListener(e -> toggleOptionSpinners(0));
        options[1].addActionListener(e -> toggleOptionSpinners(1));
        options[3].addActionListener(e -> toggleOptionSpinners(3));
        options[5].addActionListener(e -> toggleDirectionComboBox());
    }
    
    /**
     * Toggles the enabled state of option spinners based on the selected option.
     * 
     * @param optionIndex the index of the option to toggle
     */
    private void toggleOptionSpinners(int optionIndex) {
        switch(optionIndex) {
            case 0:
                if (options[0].isSelected()) {
                    optionSpinners[0].setEnabled(true);
                    optionComboBoxesTime[0].setEnabled(true);
                } else {
                    optionSpinners[0].setEnabled(false);
                    optionComboBoxesTime[0].setEnabled(false);
                }
                break;
            case 1:
                if (options[1].isSelected()) {
                    optionSpinners[1].setEnabled(true);
                    optionComboBoxesTime[1].setEnabled(true);
                } else {
                    optionSpinners[1].setEnabled(false);
                    optionComboBoxesTime[1].setEnabled(false);
                }
                break;
            case 3:
                if (options[3].isSelected()) {
                    optionSpinners[2].setEnabled(true);
                } else {
                    optionSpinners[2].setEnabled(false);
                }
                break;
            default:
                break;
        }
    }
    
    /**
     * Toggles the enabled state of the direction combo box based on the selected option.
     */
    private void toggleDirectionComboBox() {
        if (options[5].isSelected()) {
            directionComboBox.setEnabled(true);
        } else {
            directionComboBox.setEnabled(false);
            directionComboBox.setSelectedItem(VocableTrainerLocalization.START_DIRECTION_OPTION_1);
        }
    }
    
    /**
     * Adds all initialized components to the panel.
     */
    private void addComponentsToPanel() {
        for (JCheckBox option : options) { 
            panel.add(option);
        }
        
        for (JSpinner spinner : optionSpinners) {
            panel.add(spinner);
        }
        
        panel.add(setComboBox);
        panel.add(boxComboBoxes);
        
        for (JComboBox<TimeUnit> comboBox : optionComboBoxesTime) {			
            panel.add(comboBox);
        }
        
        panel.add(directionComboBox);
        panel.add(start);
    }
    
    /**
     * Adds the bar pane and panel to the main frame.
     */
    private void addPanelToFrame() {
        add(barPane);
        add(panel);
    }
    
    /**
     * Starts the training run based on the current settings.
     * Validates user selections and initializes the run panel if valid.
     * Displays error dialogs if necessary.
     */
    private void startRun() {
        VocableTrainerRunSettings settings = null;
        try {
            settings = new VocableTrainerRunSettings(
                (Fach) boxComboBoxes.getSelectedItem(), 
                (Lernkartei) setComboBox.getSelectedItem()
            );
        } catch (NullPointerException e) {
            if ((Lernkartei) setComboBox.getSelectedItem() == null) {
                new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_START_SELECT_SET).setVisible(true);
            }
            if ((Fach) boxComboBoxes.getSelectedItem() == null) {
                new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_START_SELECT_BOX).setVisible(true);
            }
            return;
        }
        
        List<Karte> k = VokabeltrainerDB.getKarten(((Fach) boxComboBoxes.getSelectedItem()).getNummer());
        
        if (isValidRunSettings(settings, k)) {
            applySettingsToRunSettings(settings);
            try {
                VocableTrainerRunPanel run = new VocableTrainerRunPanel(vtf, settings);
                vtf.changePanel(-3);
                vtf.add(run);
            } catch (IllegalArgumentException e0) {
            	handleRunPanelException(e0, k);
            } catch (Exception e1) {
                // Exception handling can be enhanced as needed
            }
        } else if (options[3].isSelected()) {
            displayTooManyCardsError(k);
        } else {
            new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_START_NO_CARDS_IN_BOX).setVisible(true);
        }
    }
    
    /**
     * Validates if the run settings are appropriate based on the selected box and card limits.
     * 
     * @param settings the current run settings
     * @param k the list of cards in the selected box
     * @return {@code true} if the settings are valid, {@code false} otherwise
     */
    private boolean isValidRunSettings(VocableTrainerRunSettings settings, List<Karte> k) {
        return settings.getBox().getNummer() == -11 || 
               settings.getBox().getNummer() == -22 || 
               (k != null && k.size() > 0 && 
                (options[3].isSelected() ? k.size() >= ((Integer)(optionSpinners[2].getValue())).intValue() : true));
    }
    
    /**
     * Applies the selected options and spinners to the run settings.
     * 
     * @param settings the run settings to apply the configurations to
     */
    private void applySettingsToRunSettings(VocableTrainerRunSettings settings) {
        if (options[0].isSelected()) {
            settings.setCardTimeLimit(((Integer)(optionSpinners[0].getValue())).intValue(), (TimeUnit)optionComboBoxesTime[0].getSelectedItem());
        } 
        if (options[1].isSelected()) {
            settings.setTotalTimeLimit(((Integer)(optionSpinners[1].getValue())).intValue(), (TimeUnit)optionComboBoxesTime[1].getSelectedItem());
        }
        
        if (options[3].isSelected()) {
            settings.setCardLimit(((Integer)(optionSpinners[2].getValue())).intValue());
        }
        
        settings.setParcticeRun(options[4].isSelected());
        
        switch (directionComboBox.getSelectedIndex()) {
            case 0:
                settings.setDirection(0);
                break;
            case 1:
                settings.setDirection(1);
                break;
            case 2:
                settings.setDirection(-1);
                break;
        }
    }
    
    /**
     * Handles exceptions thrown when initializing the run panel.
     * 
     * @param e0 the {@code IllegalArgumentException} thrown
     * @param k the list of cards in the selected box
     */
    private void handleRunPanelException(IllegalArgumentException e0, List<Karte> k) {
        if (options[3].isSelected()) {
            String t = VocableTrainerLocalization.ERROR_START_TOO_MANY_CARDS;
            new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, 
                t.substring(0, t.indexOf('{')) + 
                ((Integer)(optionSpinners[2].getValue())).intValue() + 
                t.substring(t.indexOf('}') + 1, t.lastIndexOf('{')) + 
                (k!=null? "" + k.size():"0") +
                t.substring(t.lastIndexOf('}') + 1)
            ).setVisible(true);
        } else {
            new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_START_NO_CARDS_IN_BOX).setVisible(true);
        }
    }
    
    /**
     * Displays an error dialog indicating too many cards selected.
     * 
     * @param k the list of cards in the selected box
     */
    private void displayTooManyCardsError(List<Karte> k) {
        String t = VocableTrainerLocalization.ERROR_START_TOO_MANY_CARDS;
        new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, 
            t.substring(0, t.indexOf('{')) + 
            ((Integer)(optionSpinners[2].getValue())).intValue() + 
            t.substring(t.indexOf('}') + 1, t.lastIndexOf('{')) + 
            (k!=null? "" + k.size():"0") +
            t.substring(t.lastIndexOf('}') + 1)
        ).setVisible(true);
    }
    
    /**
     * Retrieves available learning sets from the database and updates the set combo box.
     * Displays an error dialog if the database retrieval fails.
     */
    @Override
    public void retrive() {
        sets = VokabeltrainerDB.getLernkarteien();
        setComboBox.removeAllItems();
        if (sets != null) {
            for (Lernkartei set : sets) {
                setComboBox.addItem(set);
            }
            retriveBoxes();
        } else {
            new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_DATABASE_DROPPED).setVisible(true);
        }
    }
    
    /**
     * Retrieves available boxes based on the selected learning set and updates the box combo boxes.
     * Sets custom renderers for better display of box descriptions.
     * Displays an error dialog if the database retrieval fails.
     */
    protected void retriveBoxes() {
        Lernkartei l = (Lernkartei) setComboBox.getSelectedItem();
        if (l != null) {
            boxes = VokabeltrainerDB.getFaecher(l.getNummer());
            boxComboBoxes.removeAllItems();
            
            boxComboBoxes.addItem(new Fach(-22, ""));  
            boxComboBoxes.addItem(new Fach(-11, "")); 

            if (boxes != null) {
                for (Fach box : boxes) {
                    boxComboBoxes.addItem(box);
                }
            } else {
                new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, VocableTrainerLocalization.ERROR_DATABASE_DROPPED).setVisible(true);
            }
        }

        boxComboBoxes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Fach) {
                    Fach fach = (Fach) value;
                    if (fach.getNummer() == -11) {
                        label.setText(VocableTrainerLocalization.START_BOX_DUE);
                    } else if (fach.getNummer() == -22) {
                        label.setText(VocableTrainerLocalization.START_BOX_ALL);
                    } else {
                        label.setText(fach.getBeschreibung() + ". " + VocableTrainerLocalization.START_BOX_BOX);
                    }
                }
                return label;
            }
        });
    }

    /**
     * Sets the colors of all UI components based on the current theme.
     */
    @Override
    public void setColors() {
        super.setColors();
        
        for (JCheckBox option : options) { 
            option.setForeground(textColor2);
            option.setBackground(mainBackgroundColor);
        }
        
        for (JSpinner spinner : optionSpinners) {
            spinner.setForeground(textColor2);
            spinner.setBackground(mainBackgroundColor);
            JComponent editor = spinner.getEditor();
            if (editor instanceof JSpinner.DefaultEditor) {
                JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
                defaultEditor.getTextField().setBackground(textColor1);
                defaultEditor.getTextField().setForeground(textColor2);
            }
            for (Component component : spinner.getComponents()) {
                component.setBackground(textColor1); 
                component.setForeground(textColor2);
            }
        }
        
        setComboBox.setForeground(textColor2);
        setComboBox.setBackground(textColor1);
        
        boxComboBoxes.setForeground(textColor2);
        boxComboBoxes.setBackground(textColor1);
        
        for (JComboBox<TimeUnit> comboBox : optionComboBoxesTime) {			
            comboBox.setForeground(textColor2);
            comboBox.setBackground(textColor1);
        }		
		
        directionComboBox.setBackground(textColor2);
        directionComboBox.setForeground(textColor1);
        
        start.setForeground(textColor1);
        start.setBackground(buttonBackgroundColor);
    }
    
    /**
     * Sets the localization texts for all UI components based on the current language settings.
     */
    @Override
    public void setLocalisation() {		
        barPane.setTitle(VocableTrainerLocalization.START_NAME);
        
        options[0].setText(VocableTrainerLocalization.START_TIME_PER_CARD);
        options[1].setText(VocableTrainerLocalization.START_TOTAL_TIME);
        options[2].setText(VocableTrainerLocalization.START_CASE_SENSITIVE);
        options[3].setText(VocableTrainerLocalization.START_AMOUNT_OF_CARDS);
        options[4].setText(VocableTrainerLocalization.START_PRACTISE_RUN);
        options[5].setText(VocableTrainerLocalization.START_DIRECTION_DIRECTION);
        
        directionComboBox.removeAllItems();
        
        directionComboBox.addItem(VocableTrainerLocalization.START_DIRECTION_OPTION_1);
        directionComboBox.addItem(VocableTrainerLocalization.START_DIRECTION_OPTION_2);
        directionComboBox.addItem(VocableTrainerLocalization.START_DIRECTION_OPTION_3);
        
        start.setText(VocableTrainerLocalization.START_START);
    }
    
    /**
     * Paints the component and sets the bounds and fonts of all UI elements.
     * 
     * @param g the {@code Graphics} object used for painting
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        setComboBox.setBounds((int)(panel.getWidth()/(32/3.0)), panel.getHeight()/50, (int)(panel.getWidth()/(1000/607.0)), panel.getHeight()/12);
        setComboBox.setFont(new Font ("Arial", Font.BOLD, setComboBox.getHeight()/2 + 5));
        
        boxComboBoxes.setBounds((int)(panel.getWidth()/(12/9.0)), panel.getHeight()/50, panel.getWidth()/5, panel.getHeight()/12);
        boxComboBoxes.setFont(new Font ("Arial", Font.BOLD, boxComboBoxes.getHeight()/2));
        
        for (int i = 0; i < options.length; i++) {
            options[i].setBounds(
                (int)(panel.getWidth()/(32/3.0)), 
                (int)(panel.getHeight()/(64/3.0)*(i*2 + 3)), 
                panel.getHeight()/2, 
                panel.getHeight()/16
            );
            options[i].setFont(new Font ("Arial", Font.PLAIN, options[i].getHeight()/2 + 5));
        }
        
        optionSpinners[0].setBounds(
            (int)(panel.getWidth()/(12/6.0)), 
            (int)(panel.getHeight()/(64/3.0)*(0*2 + 3)), 
            panel.getWidth()/5, 
            panel.getHeight()/16
        );
        optionSpinners[1].setBounds(
            (int)(panel.getWidth()/(12/6.0)), 
            (int)(panel.getHeight()/(64/3.0)*(1*2 + 3)), 
            panel.getWidth()/5, 
            panel.getHeight()/16
        );
        optionSpinners[2].setBounds(
            (int)(panel.getWidth()/(12/9.0)), 
            (int)(panel.getHeight()/(64/3.0)*(3*2 + 3)), 
            panel.getWidth()/5, 
            panel.getHeight()/16
        );
        
        for (JSpinner spinner : optionSpinners) {
            JComponent editor = spinner.getEditor();
            if (editor instanceof JSpinner.DefaultEditor) {
                JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
                defaultEditor.getTextField().setFont(new Font ("Arial", Font.PLAIN, spinner.getHeight()/2));
            }
        }
        
        optionComboBoxesTime[0].setBounds(
            (int)(panel.getWidth()/(12/9.0)), 
            (int)(panel.getHeight()/(64/3.0)*(0*2 + 3)), 
            panel.getWidth()/5, 
            panel.getHeight()/16
        );
        optionComboBoxesTime[1].setBounds(
            (int)(panel.getWidth()/(12/9.0)), 
            (int)(panel.getHeight()/(64/3.0)*(1*2 + 3)), 
            panel.getWidth()/5, 
            panel.getHeight()/16
        );
        
        optionComboBoxesTime[0].setFont(new Font ("Arial", Font.PLAIN, optionComboBoxesTime[0].getHeight()/2));
        optionComboBoxesTime[1].setFont(new Font ("Arial", Font.PLAIN, optionComboBoxesTime[1].getHeight()/2));
        
        directionComboBox.setBounds(
            (int)(panel.getWidth()/(12/9.0)), 
            (int)(panel.getHeight()/(64/3.0)*(5*2 + 3)), 
            panel.getWidth()/5, 
            panel.getHeight()/16
        );
        directionComboBox.setFont(new Font ("Arial", Font.PLAIN, directionComboBox.getHeight()/2));

        start.setBounds(16, panel.getHeight()/4*3, panel.getWidth()-32, panel.getHeight()/4-16);
        start.setFont(new Font ("Arial", Font.BOLD, start.getHeight()/2));
    }
    
    /**
     * The {@code SpinnerListener} class listens to changes and key events on the spinners.
     * It ensures that spinner values remain within valid ranges.
     */
    private class SpinnerListener implements KeyListener, ChangeListener {
        
        /**
         * Handles changes in spinner values, enforcing minimum and maximum limits.
         * 
         * @param e the {@code ChangeEvent} triggered by a spinner
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner) e.getSource();
            int value = (int) spinner.getValue();

            if (value == 0) {
                spinner.setValue(1);
            }
            if (value > 100000) {
                spinner.setValue(100000);
            }
            if (value < 0) {
                spinner.setValue(Math.abs(value));
            }
        }
        
        /**
         * Handles key releases in spinner text fields to ensure only numeric input.
         * 
         * @param e the {@code KeyEvent} triggered by a key release
         */
        @Override
        public void keyReleased(KeyEvent e) {
            JTextField textField = (JTextField) e.getSource();
            String text = textField.getText();
            try {
                int intValue = Integer.parseInt(text);

                if (intValue < 1) {
                    intValue = Math.abs(intValue);
                    textField.setText(String.valueOf(intValue));
                }
            } catch (NumberFormatException ex) {
                String sanitized = text.replaceAll("[^0-9]", "");
                textField.setText(sanitized);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // No action needed on keyTyped
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // No action needed on keyPressed
        }
    }
    
    /**
     * The {@code TimeUnitListCellRenderer} class provides a custom renderer for the TimeUnit combo boxes.
     * It displays localized names for each time unit.
     */
    private class TimeUnitListCellRenderer extends JLabel implements ListCellRenderer<TimeUnit> {
        
        /**
         * Constructs a new {@code TimeUnitListCellRenderer}.
         */
        public TimeUnitListCellRenderer() {
            setOpaque(true);
            setFont(new Font("Arial", Font.PLAIN, options[0].getHeight() / 2));
        }
        
        @Override
        public Component getListCellRendererComponent(
                JList<? extends TimeUnit> list, 
                TimeUnit value, 
                int index,
                boolean isSelected, 
                boolean cellHasFocus) {
            if (value != null) {
                switch (value) {
                    case Seconds:
                        setText(VocableTrainerLocalization.DATA_SECONDS);
                        break;
                    case Minutes:
                        setText(VocableTrainerLocalization.DATA_MINUTES);
                        break;
                    case Hours:
                        setText(VocableTrainerLocalization.DATA_HOURS);
                        break;
                    default:
                        setText(value.toString());
                }
                if (isSelected) {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
            }
            return this;
        }
    }
}
