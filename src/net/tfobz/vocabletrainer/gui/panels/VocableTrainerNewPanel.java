package net.tfobz.vocabletrainer.gui.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import net.tfobz.vocabletrainer.data.VocableTrainerIO;
import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInfoDialog;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInputDialog;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerNewSetDialog;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainer2OptionDialog;
import net.tfobz.vokabeltrainer.model.*;

/**
 * VocableTrainerNewPanel is a GUI panel that allows users to create new vocabulary sets,
 * import/export sets, rename or delete existing sets, and add new vocabulary cards.
 * It extends the VocableTrainerPanel and integrates with the main VocableTrainerFrame.
 */
@SuppressWarnings("serial")
public class VocableTrainerNewPanel extends VocableTrainerPanel {
    
    // List of vocabulary sets (Lernkartei)
    private List<Lernkartei> sets;
    
    // GUI Components
    private JButton newSet;
    private JButton impo;
    private JButton expo;
    private JComboBox<Lernkartei> comboBox;
    private JButton renameButton;
    private JButton deleteButton;
    private JButton save;
    private JLabel label1, label2;
    private JTextField text1, text2;
    
    /**
     * Constructs a new VocableTrainerNewPanel with the specified VocableTrainerFrame.
     *
     * @param vtf the main VocableTrainerFrame
     */
    public VocableTrainerNewPanel(VocableTrainerFrame vtf) {
        super(vtf);
        
        // Initialize panel layout
        panel.setLayout(null);
        
        // Set title for the bar pane
        barPane.setTitle(VocableTrainerLocalization.MENU_NEW);
        
        // Initialize buttons with consistent styling
        initializeButtons();
        
        // Initialize combo box for selecting vocabulary sets
        initializeComboBox();
        
        // Initialize labels and text fields
        initializeLabelsAndTextFields();
        
        // Retrieve and display existing vocabulary sets
        retrive();
        
        // Add action listeners for interactive components
        addActionListeners();
        
        // Disable input fields if no set is selected
        toggleInputFields((Lernkartei)comboBox.getSelectedItem() == null);
        
        // Add all components to the panel
        addComponentsToPanel();
        
        // Add barPane and panel to the main container
        this.add(barPane);
        this.add(panel);
    }
    
    /**
     * Initializes the buttons with consistent styling and properties.
     */
    private void initializeButtons() {
        newSet = createButton('N');
        impo = createButton('I');
        expo = createButton('E');
        renameButton = createButton('R');
        deleteButton = createButton('D');
        save = createButton('S');
        save.setMnemonic('S');
    }
    
    /**
     * Creates a JButton with predefined styles and a mnemonic.
     *
     * @param mnemonic the mnemonic key for the button
     * @return the styled JButton
     */
    private JButton createButton(char mnemonic) {
        JButton button = new JButton();
        button.setForeground(textColor1);
        button.setBackground(buttonBackgroundColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setMnemonic(mnemonic);
        return button;
    }
    
    /**
     * Initializes the combo box for selecting vocabulary sets.
     */
    private void initializeComboBox() {
        comboBox = new JComboBox<>();
        comboBox.setForeground(textColor2);
        comboBox.setBackground(textColor1);
        comboBox.setBorder(null);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12)); // Default font size
    }
    
    /**
     * Initializes labels and text fields used for input.
     */
    private void initializeLabelsAndTextFields() {
        label1 = createLabel();
        label2 = createLabel();
        
        text1 = createTextField();
        text2 = createTextField();
    }
    
    /**
     * Creates a JLabel with predefined styles.
     *
     * @return the styled JLabel
     */
    private JLabel createLabel() {
        JLabel label = new JLabel();
        label.setForeground(textColor2);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 12)); // Default font size
        return label;
    }
    
    /**
     * Creates a JTextField with predefined styles.
     *
     * @return the styled JTextField
     */
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setForeground(textColor2);
        textField.setBackground(textColor1);
        textField.setBorder(null);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setFont(new Font("Arial", Font.PLAIN, 12)); // Default font size
        return textField;
    }
    
    /**
     * Adds action listeners to buttons and combo box.
     */
    private void addActionListeners() {
        comboBox.addActionListener(e -> retriveLabels());
        
        newSet.addActionListener(e -> newSet());
        
        impo.addActionListener(e -> VocableTrainerIO.Import(vtf, (Lernkartei)comboBox.getSelectedItem()));
        expo.addActionListener(e -> VocableTrainerIO.Export(vtf, (Lernkartei)comboBox.getSelectedItem()));
        
        save.addActionListener(e -> newCard());
        
        renameButton.addActionListener(e -> renameSet());
        deleteButton.addActionListener(e -> deleteSet());
    }
    
    /**
     * Enables or disables input fields based on the provided condition.
     *
     * @param disable true to disable input fields, false to enable
     */
    private void toggleInputFields(boolean disable) {
        label1.setEnabled(!disable);
        label2.setEnabled(!disable);
        text1.setEnabled(!disable);
        text2.setEnabled(!disable);
        save.setEnabled(!disable);
    }
    
    /**
     * Adds all GUI components to the main panel.
     */
    private void addComponentsToPanel() {
        panel.add(newSet);
        panel.add(impo);
        panel.add(expo);
        panel.add(comboBox);
        panel.add(renameButton);
        panel.add(deleteButton);
        panel.add(label1);
        panel.add(label2);
        panel.add(text1);
        panel.add(text2);
        panel.add(save);
    }
    
    /**
     * Handles the creation of a new vocabulary set.
     */
    private void newSet() {
        VocableTrainerNewSetDialog nameDialog = new VocableTrainerNewSetDialog(vtf);
        nameDialog.setVisible(true);
        
        Lernkartei input = nameDialog.getInput();
        if (isValidNewSet(input, nameDialog)) {
            Lernkartei newSet = input;
            int result = VokabeltrainerDB.hinzufuegenLernkartei(newSet);
            if (result == 0) {
                sets.add(newSet);
                comboBox.addItem(newSet);
            } else {
                JOptionPane.showMessageDialog(this, "There was an Error while writing the Set to the Database!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        nameDialog.closeDialog();
        retrive();
    }
    
    /**
     * Validates the new set input and checks for SQL injection.
     *
     * @param input the input Lernkartei
     * @param dialog the input dialog
     * @return true if the input is valid, false otherwise
     */
    private boolean isValidNewSet(Lernkartei input, VocableTrainerNewSetDialog dialog) {
        if (input != null 
            && input.getBeschreibung() != null 
            && !input.getBeschreibung().isEmpty() 
            && !input.getWortEinsBeschreibung().isEmpty() 
            && !input.getWortZweiBeschreibung().isEmpty() 
            && !dialog.sqlInjection(input)) {
            return true;
        } else {
            if (VocableTrainerNewSetDialog.sqlInjection(input)) {
                // Handle potential SQL injection attempt
                // Logging or additional security measures can be added here
            }
            return false;
        }
    }
    
    /**
     * Handles the addition of a new vocabulary card to the selected set.
     */
    private void newCard() {
        Lernkartei selectedSet = (Lernkartei) comboBox.getSelectedItem();

        if (selectedSet != null) {
            String input1 = text1.getText().trim();
            String input2 = text2.getText().trim();

            if (!input1.isEmpty() && !input2.isEmpty()) {
                List<Fach> faecher = VokabeltrainerDB.getFaecher(selectedSet.getNummer());

                if (faecher == null) {
                    showErrorDialog(VocableTrainerLocalization.ERROR_DATABASE_DROPPED);
                    return;
                }

                if (faecher.isEmpty()) {
                    // Create a new Fach if none exist
                    Fach newFach = new Fach();
                    newFach.setBeschreibung("1");
                    int result = VokabeltrainerDB.hinzufuegenFach(selectedSet.getNummer(), newFach);
                    if (result != 0) {
                        showErrorDialog(VocableTrainerLocalization.ERROR_NEW_BOX_CREATION_FAIL);
                        return;
                    }
                    faecher = VokabeltrainerDB.getFaecher(selectedSet.getNummer());
                }

                // Add the new card to the first Fach
                Fach firstFach = faecher.get(0);
                Karte newCard = new Karte(-1, input1, input2, selectedSet.getRichtung(), selectedSet.getGrossKleinschreibung());
                newCard.setFnummer(firstFach.getNummer());

                int result = VokabeltrainerDB.hinzufuegenKarte(selectedSet.getNummer(), newCard);

                handleNewCardResult(result);
            } else {
                showInputErrorDialog(selectedSet);
            }
        } else {
            showErrorDialog(VocableTrainerLocalization.ERROR_START_SELECT_SET);
        }
    }
    
    /**
     * Handles the result of adding a new card to the database.
     *
     * @param result the result code from the database operation
     */
    private void handleNewCardResult(int result) {
        switch (result) {
            case 0:
                // Success: Reset input fields and refresh UI
                text1.setText("");
                text2.setText("");
                vtf.changePanel(-1);
                break;
            case -5:
                showErrorDialog(VocableTrainerLocalization.ERROR_IMPORT_DUPLICATE_CARD);
                break;
            default:
                showErrorDialog(VocableTrainerLocalization.ERROR_NEW_CARD_CREATION_FAIL);
                break;
        }
    }
    
    /**
     * Displays an error dialog with the specified message key.
     *
     * @param messageKey the localization key for the error message
     */
    private void showErrorDialog(String messageKey) {
        new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, messageKey).setVisible(true);
    }
    
    /**
     * Displays an input error dialog when required fields are empty.
     *
     * @param set the selected Lernkartei
     */
    private void showInputErrorDialog(Lernkartei set) {
        String errorMessage = VocableTrainerLocalization.ERROR_NEW_ENTER_DATA;
        String part1 = errorMessage.substring(errorMessage.indexOf('}') + 1, errorMessage.indexOf('{'));
        String part2 = errorMessage.substring(errorMessage.indexOf('}'));
        errorMessage = set.getWortEinsBeschreibung() + part1 + set.getWortZweiBeschreibung() + part2;
        new VocableTrainerInfoDialog(vtf, VocableTrainerLocalization.ERROR, errorMessage).setVisible(true);
    }
    
    /**
     * Handles the renaming of the selected vocabulary set.
     */
    private void renameSet() {
        Lernkartei selectedSet = (Lernkartei) comboBox.getSelectedItem();
        if (selectedSet != null) {
            VocableTrainerInputDialog dialog = new VocableTrainerInputDialog(
                vtf, 
                VocableTrainerLocalization.DIALOG_INPUT_RENAME, 
                VocableTrainerLocalization.DIALOG_INPUT_QUESTION, 
                selectedSet.getBeschreibung()
            );
            dialog.setVisible(true);
            String newName = dialog.getInput();
            if (newName != null && !newName.trim().isEmpty()) {
                selectedSet.setBeschreibung(newName);
                VokabeltrainerDB.aendernLernkartei(selectedSet);
                retrive();
            }
            dialog.closeDialog();
        }
    }
    
    /**
     * Handles the deletion of the selected vocabulary set.
     */
    private void deleteSet() {
        Lernkartei selectedSet = (Lernkartei) comboBox.getSelectedItem();
        if (selectedSet != null) {
            VocableTrainer2OptionDialog dialog = new VocableTrainer2OptionDialog(
                vtf, 
                VocableTrainerLocalization.DIALOG_TWO_OPTION_DELETE_TITLE, 
                VocableTrainerLocalization.DIALOG_TWO_OPTION_DELETE_QUESTION, 
                VocableTrainerLocalization.DIALOG_TWO_OPTION_DELETE_CONFIRM, 
                VocableTrainerLocalization.DIALOG_TWO_OPTION_DELETE_CANCEL
            );
            dialog.setVisible(true);
            if (dialog.getAnswer()) {
                VokabeltrainerDB.loeschenLernkartei(selectedSet.getNummer());
                retrive();
                repaint();
            }
            dialog.closeDialog();
        }
    }
    
    /**
     * Retrieves the list of vocabulary sets from the database and updates the combo box.
     */
    @Override
    public void retrive() {
        sets = VokabeltrainerDB.getLernkarteien();
        comboBox.removeAllItems();
        if (sets != null) {
            for (Lernkartei set : sets) {
                comboBox.addItem(set);
            }
            retriveLabels();
        } else {
            showErrorDialog(VocableTrainerLocalization.ERROR_DATABASE_DROPPED);
        }
    }
    
    /**
     * Updates the labels based on the selected vocabulary set.
     */
    protected void retriveLabels() {
        Lernkartei selectedSet = (Lernkartei) comboBox.getSelectedItem();
        if (selectedSet != null) {
            label1.setText(selectedSet.getWortEinsBeschreibung());
            label2.setText(selectedSet.getWortZweiBeschreibung());
            
            label1.setEnabled(true);
            label2.setEnabled(true);
            text1.setEnabled(true);
            text2.setEnabled(true);
            save.setEnabled(true);
        }
    }
    
    /**
     * Sets the colors for all GUI components based on the current theme.
     */
    @Override
    public void setColors() {
        super.setColors();
        
        // Update button colors
        updateButtonColors(newSet);
        updateButtonColors(impo);
        updateButtonColors(expo);
        updateButtonColors(renameButton);
        updateButtonColors(deleteButton);
        updateButtonColors(save);
        
        // Update combo box colors
        comboBox.setForeground(textColor2);
        comboBox.setBackground(textColor1);
        
        // Update labels and text fields colors
        label1.setForeground(textColor2);
        label2.setForeground(textColor2);
        
        text1.setForeground(textColor2);
        text1.setBackground(textColor1);
        
        text2.setForeground(textColor2);
        text2.setBackground(textColor1);
    }
    
    /**
     * Updates the foreground and background colors of a button.
     *
     * @param button the JButton to update
     */
    private void updateButtonColors(JButton button) {
        button.setForeground(textColor1);
        button.setBackground(buttonBackgroundColor);
    }
    
    /**
     * Sets the localized text for all GUI components.
     */
    @Override
    public void setLocalisation() {
        barPane.setTitle(VocableTrainerLocalization.NEW_NAME);
        newSet.setText(VocableTrainerLocalization.NEW_NEW_SET);
        impo.setText(VocableTrainerLocalization.NEW_IMPORT);
        expo.setText(VocableTrainerLocalization.NEW_EXPORT);
        renameButton.setText(VocableTrainerLocalization.NEW_RENAME);
        deleteButton.setText(VocableTrainerLocalization.NEW_DELETE);
        save.setText(VocableTrainerLocalization.NEW_SAVE);
    }
    
    /**
     * Paints the component and sets the bounds and fonts for all GUI elements.
     *
     * @param g the Graphics object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = panel.getWidth();
        int h = panel.getHeight();
        
        // Set bounds and fonts for buttons
        setButtonBoundsAndFonts(w, h);
        
        // Set bounds and fonts for combo box
        comboBox.setBounds(16, h / 8 + 16, w * 7 / 10, h / 8);
        comboBox.setFont(new Font("Arial", Font.PLAIN, comboBox.getHeight() / 2 + 1));
        
        // Set bounds and fonts for labels
        label1.setBounds(16, (int)(h / 8.0 * 3) + 16, w / 2 - 32, h / 8);
        label1.setFont(new Font("Arial", Font.PLAIN, label1.getHeight() / 2 + 1));
        
        label2.setBounds(w / 2 + 16, (int)(h / 8.0 * 3) + 16, w / 2 - 32, h / 8);
        label2.setFont(new Font("Arial", Font.PLAIN, label2.getHeight() / 2 + 1));
        
        // Set bounds and fonts for text fields
        text1.setBounds(16, h / 2 + 16, w / 2 - 32, h / 8);
        text1.setFont(new Font("Arial", Font.PLAIN, text1.getHeight() / 2 + 1));
        
        text2.setBounds(w / 2 + 16, h / 2 + 16, w / 2 - 32, h / 8);
        text2.setFont(new Font("Arial", Font.PLAIN, text2.getHeight() / 2 + 1));
        
        // Set bounds and fonts for save button
        save.setBounds(16, panel.getHeight() / 4 * 3, panel.getWidth() - 32, panel.getHeight() / 4 - 16);
        save.setFont(new Font("Arial", Font.BOLD, save.getHeight() / 2 + 1));
    }
    
    /**
     * Sets the bounds and fonts for the action buttons.
     *
     * @param w the width of the panel
     * @param h the height of the panel
     */
    private void setButtonBoundsAndFonts(int w, int h) {
        newSet.setBounds(w / 16 + 32, h / 500 + 8, w / 6 + 45, h / 16 + 16);
        newSet.setFont(new Font("Arial", Font.BOLD, (int)(newSet.getHeight() / 1.5) + 1));
        
        impo.setBounds(w - 32 - 2 * w / 5, h / 500 + 8, w / 5, h / 16 + 16);
        impo.setFont(new Font("Arial", Font.BOLD, (int)(impo.getHeight() / 1.5) + 1));
        
        expo.setBounds(w - 16 - w / 5, h / 500 + 8, w / 5, h / 16 + 16);
        expo.setFont(new Font("Arial", Font.BOLD, (int)(expo.getHeight() / 1.5) + 1));
        
        renameButton.setBounds(w * 7 / 10 + 48, h / 8 + 16, w - w * 7 / 10 - 64, h / 16 - 2);
        renameButton.setFont(new Font("Arial", Font.PLAIN, renameButton.getHeight() / 2 + 1));
        
        deleteButton.setBounds(w * 7 / 10 + 48, h / 8 + 16 + h / 16 + 2, w - w * 7 / 10 - 64, h / 16 - 2);
        deleteButton.setFont(new Font("Arial", Font.PLAIN, deleteButton.getHeight() / 2 + 1));
    }
}
