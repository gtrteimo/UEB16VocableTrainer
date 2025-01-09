package net.tfobz.vocabletrainer.gui.dialogs;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.panels.VocableTrainerPanel;
import net.tfobz.vokabeltrainer.model.Lernkartei;

/**
 * Dialog for creating a new vocabulary set in the Vocable Trainer application.
 * Provides input fields for the set name and descriptions in two languages.
 */
@SuppressWarnings("serial")
public class VocableTrainerNewSetDialog extends VocableTrainerInfoDialog {	

    // UI Components
    protected JLabel text;
    protected JTextField inputField;
    protected JLabel dLabel1;
    protected JLabel dLabel2;
    protected JTextField dInputField1;
    protected JTextField dInputField2;
    protected JButton confirmButton;
    protected JButton cancelButton;

    /**
     * Constructs a new VocableTrainerNewSetDialog.
     *
     * @param parent The parent JFrame of this dialog.
     */
    public VocableTrainerNewSetDialog(JFrame parent) {
        super(parent, VocableTrainerLocalization.DIALOG_NEW_SET_TITLE);

        // Prevent dialog from closing automatically
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Add a window listener to handle the closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeDialog();
            }
        });    

        // Get dimensions of the component panel
        int w = componentPanel.getWidth();
        int h = componentPanel.getHeight();

        // Initialize and configure the set name label
        text = new JLabel(VocableTrainerLocalization.DIALOG_NEW_SET_ENTER_SET_NAME);
        text.setBounds(16, 16, w - 32, h / 8);
        text.setFont(new Font("Arial", Font.PLAIN, text.getHeight() / 2 + 1));
        text.setForeground(VocableTrainerPanel.textColor2);
        text.setHorizontalAlignment(SwingConstants.CENTER);

        // Initialize and configure the set name input field
        inputField = new JTextField();
        inputField.setBounds(16, 16 + h / 8, w - 32, h / 8);
        inputField.setFont(new Font("Arial", Font.PLAIN, inputField.getHeight() / 2 + 1));
        inputField.setBackground(VocableTrainerPanel.textColor1);
        inputField.setForeground(VocableTrainerPanel.textColor2);
        inputField.setBorder(null);

        // Initialize and configure the first description label
        dLabel1 = new JLabel(VocableTrainerLocalization.DIALOG_NEW_SET_DESCRIPTION_1);         	
        dLabel1.setBounds(16, 16 + h / 3, w / 2 - 32, h / 8);
        dLabel1.setFont(new Font("Arial", Font.PLAIN, dLabel1.getHeight() / 2 + 1));
        dLabel1.setBackground(VocableTrainerPanel.textColor1);
        dLabel1.setForeground(VocableTrainerPanel.textColor2);
        dLabel1.setBorder(null);
        dLabel1.setDisplayedMnemonic('1');
        dLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        // Initialize and configure the second description label
        dLabel2 = new JLabel(VocableTrainerLocalization.DIALOG_NEW_SET_DESCRIPTION_2);         	
        dLabel2.setBounds(w / 2 + 16, 16 + h / 3, w / 2 - 32, h / 8);
        dLabel2.setFont(new Font("Arial", Font.PLAIN, dLabel2.getHeight() / 2 + 1));
        dLabel2.setBackground(VocableTrainerPanel.textColor1);
        dLabel2.setForeground(VocableTrainerPanel.textColor2);
        dLabel2.setBorder(null);
        dLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        dLabel2.setDisplayedMnemonic('2');

        // Initialize and configure the first description input field
        dInputField1 = new JTextField();
        dInputField1.setBounds(16, 16 + h / 2, w / 2 - 32, h / 8);
        dInputField1.setFont(new Font("Arial", Font.PLAIN, dInputField1.getHeight() / 2 + 1));
        dInputField1.setBackground(VocableTrainerPanel.textColor1);
        dInputField1.setForeground(VocableTrainerPanel.textColor2);
        dInputField1.setBorder(null);
        dInputField1.setHorizontalAlignment(SwingConstants.CENTER);
        dLabel1.setLabelFor(dInputField1);

        // Initialize and configure the second description input field
        dInputField2 = new JTextField();
        dInputField2.setBounds(w / 2 + 16, 16 + h / 2, w / 2 - 32, h / 8);
        dInputField2.setFont(new Font("Arial", Font.PLAIN, dInputField2.getHeight() / 2 + 1));
        dInputField2.setBackground(VocableTrainerPanel.textColor1);
        dInputField2.setForeground(VocableTrainerPanel.textColor2);
        dInputField2.setBorder(null);
        dInputField2.setHorizontalAlignment(SwingConstants.CENTER);
        dLabel2.setLabelFor(dInputField2);

        // Initialize and configure the confirm (save) button
        confirmButton = new JButton(VocableTrainerLocalization.DIALOG_NEW_SET_SAVE);
        confirmButton.setBounds(w / 2 + 16, h - h / 6 - 16, w / 2 - 32, h / 6);
        confirmButton.setFont(new Font("Arial", Font.PLAIN, confirmButton.getHeight() / 2));
        confirmButton.setForeground(VocableTrainerPanel.textColor1);
        confirmButton.setBackground(VocableTrainerPanel.buttonBackgroundColor);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.addActionListener(e -> check());

        // Initialize and configure the cancel button
        cancelButton = new JButton(VocableTrainerLocalization.DIALOG_NEW_SET_CANCEL);
        cancelButton.setBounds(16, h - h / 6 - 16, w / 2 - 32, h / 6);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, cancelButton.getHeight() / 2));
        cancelButton.setForeground(VocableTrainerPanel.textColor1);
        cancelButton.setBackground(VocableTrainerPanel.buttonBackgroundColor);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.addActionListener(e -> closeDialog());

        // Add focus listeners to input fields to select all text on focus
        addFocusListeners();

        // Add all components to the panel
        addComponentsToPanel();

        // Add the component panel to the dialog
        add(componentPanel);
    }

    /**
     * Adds focus listeners to the input fields to select all text when focused.
     */
    private void addFocusListeners() {
        FocusListener selectAllListener = new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                // No action needed on focus lost
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (e.getSource() instanceof JTextField) {
                    ((JTextField) e.getSource()).selectAll();
                }
            }
        };

        inputField.addFocusListener(selectAllListener);
        dInputField1.addFocusListener(selectAllListener);
        dInputField2.addFocusListener(selectAllListener);
    }

    /**
     * Adds all UI components to the component panel.
     */
    private void addComponentsToPanel() {
        componentPanel.add(text);
        componentPanel.add(inputField);
        componentPanel.add(dLabel1);
        componentPanel.add(dLabel2);
        componentPanel.add(dInputField1);
        componentPanel.add(dInputField2);
        componentPanel.add(confirmButton);
        componentPanel.add(cancelButton);
    }

    /**
     * Validates the input and closes the dialog if the set name is not empty.
     * If the set name is empty, the dialog is closed without saving.
     */
    private void check() {
        if (!inputField.getText().trim().isEmpty()) {
            setVisible(false);
        } else {
            closeDialog();
        }
    }

    /**
     * Checks for SQL injection patterns in the provided Lernkartei object.
     *
     * @param l The Lernkartei object to validate.
     * @return True if potential SQL injection patterns are found; otherwise, false.
     */
    public static boolean sqlInjection(Lernkartei l) {
        if (containsSqlInjectionPatterns(l.getBeschreibung()) ||
            containsSqlInjectionPatterns(l.getWortEinsBeschreibung()) ||
            containsSqlInjectionPatterns(l.getWortZweiBeschreibung())) {
            return true;
        }
        return false;
    }

    /**
     * Helper method to detect SQL injection patterns in a string.
     *
     * @param input The string to check.
     * @return True if SQL injection patterns are found; otherwise, false.
     */
    private static boolean containsSqlInjectionPatterns(String input) {
        return input.contains("'") || input.contains(";") || input.contains("--");
    }

    /**
     * Retrieves the user input from the dialog and constructs a Lernkartei object.
     *
     * @return A Lernkartei object containing the input data.
     */
    public Lernkartei getInput() {
        Lernkartei ret = new Lernkartei();
        ret.setBeschreibung(inputField.getText().trim());
        ret.setWortEinsBeschreibung(dInputField1.getText().trim());
        ret.setWortZweiBeschreibung(dInputField2.getText().trim());
        return ret;
    }

    /**
     * Closes the dialog by clearing input fields and disposing of the dialog.
     */
    @Override
    public void closeDialog() {
        inputField.setText("");
        dInputField1.setText("");
        dInputField2.setText("");
        setVisible(false);
        dispose();
    }
}
