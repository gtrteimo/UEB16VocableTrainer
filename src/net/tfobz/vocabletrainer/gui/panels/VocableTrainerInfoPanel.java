package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.tfobz.vocabletrainer.data.VocableTrainerLocalization;
import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainer2OptionDialog;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInfoDialog;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInputDialog;
import net.tfobz.vokabeltrainer.model.Karte;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

/**
 * VocableTrainerInfoPanel is a GUI panel that displays and manages vocabulary sets
 * within the Vocable Trainer application. It allows users to view, rename, and delete
 * vocabulary sets and individual vocabulary cards.
 * 
 * @see VocableTrainerPanel
 * @see VocableTrainerFrame
 */
@SuppressWarnings("serial")
public class VocableTrainerInfoPanel extends VocableTrainerPanel {
    
    private List<Lernkartei> sets;
    private JLabel topic;
    private JComboBox<Lernkartei> comboBox;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton renameButton;
    private JButton deleteButton;
    private DefaultTableModel model;
    private DefaultTableCellRenderer dynamicRenderer;
    
    private Map<Integer, String[]> changedRows = new HashMap<>();

    /**
     * Constructs a new VocableTrainerInfoPanel with the specified frame.
     * Initializes GUI components and sets up event listeners.
     *
     * @param vtf the VocableTrainerFrame to which this panel belongs
     */
    public VocableTrainerInfoPanel(VocableTrainerFrame vtf) {
        super(vtf);
        initializeComponents();
        setLocalisation();
        setColors();
        this.add(barPane);
        this.add(panel);
    }
   
    /**
     * Initializes GUI components and layouts.
     */
    private void initializeComponents() {
        panel.setLayout(null);
        
        // Initialize and configure topic label
        topic = new JLabel();
        topic.setForeground(textColor2);
        
        // Retrieve vocabulary sets and initialize combo box
        sets = VokabeltrainerDB.getLernkarteien();
        comboBox = new JComboBox<>(sets.toArray(new Lernkartei[0]));
        comboBox.setForeground(textColor2);
        comboBox.setBackground(textColor1);
        comboBox.addActionListener(e -> retriveTabel());
        
        // Initialize and configure rename button
        renameButton = new JButton();
        renameButton.setFocusPainted(false);
        renameButton.setBorderPainted(false);
        renameButton.setForeground(textColor1);
        renameButton.setBackground(buttonBackgroundColor);
        renameButton.setMnemonic('e');
        renameButton.addActionListener(e -> handleRenameAction());
        
        // Initialize and configure delete button
        deleteButton = new JButton();
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setForeground(textColor1);
        deleteButton.setBackground(buttonBackgroundColor);
        deleteButton.setMnemonic('D');
        deleteButton.addActionListener(e -> handleDeleteAction());
        
        // Add components to the panel
        panel.add(topic);
        panel.add(comboBox);
        panel.add(renameButton);
        panel.add(deleteButton);
        
        // Initialize dynamic renderer for table cells
        dynamicRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean hf, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, v, sel, hf, r, c);
                comp.setFont(new Font("Arial", Font.PLAIN, (scrollPane == null ? 30 : (scrollPane.getHeight() / 16) / 2 + 2)));
                comp.setBackground(textColor1);
                setHorizontalAlignment(SwingConstants.CENTER);
                setVerticalAlignment(SwingConstants.CENTER);
                return comp;
            }
        };
    }
    
    /**
     * Handles the action performed when the rename button is clicked.
     * Allows renaming of selected vocabulary sets or individual cards.
     */
    private void handleRenameAction() {
        boolean cardSelected = isAnyCardSelected();
        if (cardSelected) {
            saveChangedRows();
            clearSelection();
            renameButton.setText(VocableTrainerLocalization.INFO_RENAME);
        } else {
            editSelectedRow();
        }
        repaint();
    }
    
    /**
     * Handles the action performed when the delete button is clicked.
     * Allows deletion of selected vocabulary sets or individual cards.
     */
    private void handleDeleteAction() {
        boolean cardSelected = isAnyCardSelected();
        if (cardSelected) {
            deleteSelectedCards();
            repaint();
        } else {
            deleteSelectedSet();
        }
    }
    
    /**
     * Checks if any card is selected in the table.
     *
     * @return true if at least one card is selected, false otherwise
     */
    private boolean isAnyCardSelected() {
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean val = (Boolean) table.getValueAt(i, 0);
            if (val != null && val) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Saves the changes made to the selected rows.
     */
    private void saveChangedRows() {
        for (Map.Entry<Integer, String[]> entry : changedRows.entrySet()) {
            if (Boolean.TRUE.equals(table.getValueAt(entry.getKey(), 0))) {
                Integer knummer = (Integer) table.getValueAt(entry.getKey(), 6);
                Karte card = VokabeltrainerDB.getKarte(knummer);
                if (card != null) {
                    String[] words = entry.getValue();
                    card.setWortEins(words[0]);
                    card.setWortZwei(words[1]);
                    VokabeltrainerDB.aendernKarte(card);
                }
            }
        }
        changedRows.clear();
    }
    
    /**
     * Clears the selection in the table by unchecking all checkboxes.
     */
    private void clearSelection() {
        for (int i = 0; i < table.getRowCount(); i++) {
            table.setValueAt(false, i, 0);
        }
    }
    
    /**
     * Initiates editing of the selected row in the table.
     */
    private void editSelectedRow() {
        int selectedRow = getFirstSelectedRow();
        if (selectedRow != -1) {
            table.changeSelection(selectedRow, 1, false, false);
            table.editCellAt(selectedRow, 1);
            Component editorComp = table.getEditorComponent();
            if (editorComp instanceof JTextField) {
                ((JTextField) editorComp).selectAll();
                editorComp.requestFocusInWindow();
            }
        } else {
            renameSelectedSet();
        }
    }
    
    /**
     * Retrieves the first selected row in the table.
     *
     * @return the index of the first selected row, or -1 if none are selected
     */
    private int getFirstSelectedRow() {
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean val = (Boolean) table.getValueAt(i, 0);
            if (val != null && val) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Prompts the user to rename the selected vocabulary set.
     */
    private void renameSelectedSet() {
        Lernkartei selectedSet = (Lernkartei) comboBox.getSelectedItem();
        if (selectedSet != null) {
            VocableTrainerInputDialog dialog = new VocableTrainerInputDialog(
                vtf,
                VocableTrainerLocalization.DIALOG_INPUT_RENAME,
                VocableTrainerLocalization.DIALOG_INPUT_QUESTION,
                selectedSet.getBeschreibung()
            );
            dialog.setVisible(true);
            if (dialog.getInput() != null && !dialog.getInput().trim().isEmpty()) {
                selectedSet.setBeschreibung(dialog.getInput());
                VokabeltrainerDB.aendernLernkartei(selectedSet);
                retrive();
            }
            dialog.closeDialog();
        }
    }
    
    /**
     * Deletes the selected vocabulary cards from the database and table.
     */
    private void deleteSelectedCards() {
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            Boolean val = (Boolean) table.getValueAt(i, 0);
            if (val != null && val) {
                Integer knummer = (Integer) table.getValueAt(i, 6);
                VokabeltrainerDB.loeschenKarte(knummer);
                ((DefaultTableModel) table.getModel()).removeRow(i);
            }
        }
    }
    
    /**
     * Prompts the user to confirm and delete the selected vocabulary set.
     */
    private void deleteSelectedSet() {
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
     * Fills the table model with vocabulary cards from the selected set.
     *
     * @param model the table model to be filled
     */
    private void fillTable(DefaultTableModel model) {
        Lernkartei selectedSet = (Lernkartei) comboBox.getSelectedItem();
        if (selectedSet != null && selectedSet.getNummer() != -1) {
            clearTableModel(model);
            List<Karte> cards = VokabeltrainerDB.getKartenUndBoxenVonLernkartei(selectedSet.getNummer());
            for (Karte card : cards) {
                model.addRow(new Object[]{
                    false,
                    card.getWortEins(),
                    card.getWortZwei(),
                    new Date(),
                    (card.getGerlerntAm() != null ? card.getGerlerntAm() : "-"),
                    card.getFachBeschreibung(),
                    card.getNummer()
                });
            }
        }
    }
    
    /**
     * Clears all rows from the table model.
     *
     * @param model the table model to be cleared
     */
    private void clearTableModel(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
    
    /**
     * Retrieves and displays the vocabulary sets in the combo box and updates the table.
     */
    @Override
    public void retrive() {
        sets = VokabeltrainerDB.getLernkarteien();
        comboBox.removeAllItems();
        if (sets != null) {
            for (Lernkartei set : sets) {
                comboBox.addItem(set);
            }
        } else {
            new VocableTrainerInfoDialog(
                vtf,
                VocableTrainerLocalization.ERROR,
                VocableTrainerLocalization.ERROR_DATABASE_DROPPED
            ).setVisible(true);
        }
        retriveTabel();
    }

    /**
     * Retrieves and populates the table with vocabulary cards based on the selected set.
     */
    protected void retriveTabel() {
        if (scrollPane != null) {
            panel.remove(scrollPane);
        }
        Lernkartei selectedSet = (Lernkartei) comboBox.getSelectedItem();
        if (selectedSet == null) {
            return;
        }
        initializeTableModel(selectedSet);
        configureTable();
        addScrollPaneToPanel();
        configureTableListeners();
        adjustTableColumns();
    }
    
    /**
     * Initializes the table model with appropriate columns and data types.
     *
     * @param selectedSet the currently selected vocabulary set
     */
    private void initializeTableModel(Lernkartei selectedSet) {
        model = new DefaultTableModel(
            new Object[]{
                " ",
                selectedSet.getWortEinsBeschreibung(),
                selectedSet.getWortZweiBeschreibung(),
                VocableTrainerLocalization.INFO_DATE_MODIFIED,
                VocableTrainerLocalization.INFO_LAST_ASKED,
                VocableTrainerLocalization.INFO_BOX,
                "knummer"
            }, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1 || column == 2;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return Boolean.class;
                    case 3:
                    case 4: return java.sql.Date.class;
                    case 5:
                    case 6: return Integer.class;
                    default: return String.class;
                }
            }
        };
    }
    
    /**
     * Configures the JTable with the model and sets up renderers and editors.
     */
    private void configureTable() {
        fillTable(model);
        table = new JTable(model);
        table.setBorder(null);
        table.getTableHeader().setReorderingAllowed(false);
        
        // Configure checkbox editor for selection column
        JCheckBox editorBox = new JCheckBox();
        editorBox.setBackground(buttonBackgroundColor);
        editorBox.setFocusPainted(false);
        editorBox.setBorderPainted(false);
        editorBox.setOpaque(true);
        editorBox.setForeground(textColor2);
        table.setDefaultEditor(Boolean.class, new DefaultCellEditor(editorBox));
        
        // Configure checkbox renderer for selection column
        table.setDefaultRenderer(Boolean.class, (t, v, sel, hf, r, c) -> {
            JCheckBox cb = new JCheckBox();
            cb.setSelected(v != null && (Boolean) v);
            cb.setFocusPainted(false);
            cb.setBackground(textColor1);
            cb.setForeground(textColor2);
            cb.setBorderPainted(false);
            cb.setOpaque(true);
            if (sel) {
                cb.setBackground(t.getSelectionBackground());
                cb.setForeground(t.getSelectionForeground());
            } else {
                cb.setBackground(textColor1);
                cb.setForeground(textColor2);
            }
            cb.setHorizontalAlignment(SwingConstants.CENTER);
            cb.setVerticalAlignment(SwingConstants.CENTER);
            cb.setFont(new Font("Arial", Font.PLAIN, (scrollPane == null ? 30 : (scrollPane.getHeight() / 16) / 2 + 2)));
            return cb;
        });
        
        // Configure date renderer for modified date column
        table.setDefaultRenderer(java.sql.Date.class, (t, v, sel, hf, r, c) -> {
            JLabel lab = new JLabel(new SimpleDateFormat(" dd.MM.yyyy ").format((java.sql.Date) v));
            lab.setHorizontalAlignment(SwingConstants.CENTER);
            lab.setVerticalAlignment(SwingConstants.CENTER);
            lab.setBackground(textColor1);
            lab.setOpaque(true);
            return lab;
        });
        
        // Set header renderer for selection column
        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxHeader(table, 0));
        
        // Configure renderer for box description column
        table.getColumnModel().getColumn(5).setCellRenderer((t, v, sel, hf, r, c) -> {
            JLabel lab = new JLabel(String.valueOf(v));
            lab.setHorizontalAlignment(SwingConstants.CENTER);
            lab.setVerticalAlignment(SwingConstants.CENTER);
            lab.setBackground(textColor1);
            lab.setOpaque(true);
            return lab;
        });
        
        // Apply dynamic renderer to relevant columns
        for (int col = 1; col < table.getColumnCount() - 1; col++) {
            table.getColumnModel().getColumn(col).setCellRenderer(dynamicRenderer);
        }
        
        // Configure text editors for editable columns
        configureTextEditors();
        
        // Set table background
        table.setBackground(textColor1);
    }
    
    /**
     * Configures text editors for editable table columns.
     */
    private void configureTextEditors() {
        DefaultCellEditor textEditor = new DefaultCellEditor(new JTextField()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                repaint();
                JTextField field = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
                field.setFont(new Font("Arial", Font.PLAIN, scrollPane.getHeight() / 32 + 1));
                field.setHorizontalAlignment(SwingConstants.CENTER);
                field.setBackground(textColor1);
                field.setForeground(textColor2);
                SwingUtilities.invokeLater(field::selectAll);
                return field;
            }
        };
        textEditor.setClickCountToStart(1);
        table.getColumnModel().getColumn(1).setCellEditor(textEditor);
        table.getColumnModel().getColumn(2).setCellEditor(textEditor);
    }
    
    /**
     * Adds the scroll pane containing the table to the panel and configures focus behavior.
     */
    private void addScrollPaneToPanel() {
        scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(textColor1);
        scrollPane.setFocusable(true);
        scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                scrollPane.requestFocusInWindow();
            }
        });
        panel.setFocusable(true);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panel.requestFocusInWindow();
            }
        });
        barPane.setFocusable(true);
        barPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                barPane.requestFocusInWindow();
            }
        });
        panel.add(scrollPane);
    }
    
    /**
     * Configures listeners for table model changes to handle updates.
     */
    private void configureTableListeners() {
        table.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                if (e.getColumn() == 0) {
                    updateRenameButtonText();
                }
                if (e.getColumn() == 1 || e.getColumn() == 2) {
                    int row = e.getFirstRow();
                    String w1 = (String) table.getValueAt(row, 1);
                    String w2 = (String) table.getValueAt(row, 2);
                    changedRows.put(row, new String[]{w1, w2});
                }
            }
        });
    }
    
    /**
     * Updates the rename button text based on the selection state.
     */
    private void updateRenameButtonText() {
        boolean atLeastOneSelected = isAnyCardSelected();
        renameButton.setText(atLeastOneSelected ? VocableTrainerLocalization.INFO_SAVE : VocableTrainerLocalization.INFO_RENAME);
        table.getTableHeader().repaint();
    }
    
    /**
     * Adjusts the table columns to fit the content and panel size.
     */
    private void adjustTableColumns() {
        table.getColumnModel().getColumn(3).setMinWidth(0);
        table.getColumnModel().getColumn(3).setMaxWidth(0);
        table.getColumnModel().getColumn(3).setWidth(0);
        
        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setWidth(0);
    }

    /**
     * Sets the color scheme for the panel and its components.
     */
    @Override
    public void setColors() {
        super.setColors();
        
        topic.setForeground(textColor2);
        
        comboBox.setForeground(textColor2);
        comboBox.setBackground(textColor1);

        renameButton.setForeground(textColor1);
        renameButton.setBackground(buttonBackgroundColor);
        
        deleteButton.setForeground(textColor1);
        deleteButton.setBackground(buttonBackgroundColor);
    }
    
    /**
     * Sets the localized text for the panel's components.
     */
    @Override
    public void setLocalisation() {		
        barPane.setTitle(VocableTrainerLocalization.INFO_NAME);
        
        topic.setText(VocableTrainerLocalization.INFO_TOPIC);
        renameButton.setText(VocableTrainerLocalization.INFO_RENAME);
        deleteButton.setText(VocableTrainerLocalization.INFO_DELETE);
    }
    
    /**
     * Paints the component and arranges the layout of its subcomponents.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int w = panel.getWidth();
        int h = panel.getHeight();
        
        // Set bounds and fonts for labels and buttons
        layoutComponents(w, h);
        
        if (scrollPane != null) {
            scrollPane.setBounds(16, h / 8, w - 32, h - 16 - h / 8);
            int rowHeight = scrollPane.getHeight() / 16;
            table.setRowHeight(rowHeight);
            configureTableHeader(rowHeight);
            adjustColumnWidths(w, rowHeight);
        }
    }
    
    /**
     * Lays out the components within the panel based on the current width and height.
     *
     * @param w the width of the panel
     * @param h the height of the panel
     */
    private void layoutComponents(int w, int h) {
        topic.setBounds(32 + h/16, h / 50, w / 4, h / 12);
        topic.setFont(new Font("Arial", Font.BOLD, topic.getHeight() / 2 + 1));
        
        comboBox.setBounds((int)(w / (24 / 5.0)), h / 50, (int)(w / (48 / 31.0)), h / 12);
        comboBox.setFont(new Font("Arial", Font.PLAIN, comboBox.getHeight() / 2 + 1));
        
        renameButton.setBounds(
            (int)(w - 8 - (w / (4 / 3.0) / 6)),
            h / 50,
            (int)(w / (4 / 3.0) / 6),
            h / 24 - 1
        );
        renameButton.setFont(new Font("Arial", Font.PLAIN, renameButton.getHeight() / 2 + 1));
        
        deleteButton.setBounds(
            (int)(w - 8 - (w / (4 / 3.0) / 6)), 
            h / 50 + h / 24 + 2,
            (int)(w / (4 / 3.0) / 6),
            h / 24 - 1
        );
        deleteButton.setFont(new Font("Arial", Font.PLAIN, deleteButton.getHeight() / 2 + 1));
    }
    
    /**
     * Configures the table header renderer.
     *
     * @param rowHeight the height of each row in the table
     */
    private void configureTableHeader(int rowHeight) {
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, v, s, f, r, c);
                lbl.setOpaque(true);
                lbl.setBackground(buttonBackgroundColor);
                lbl.setForeground(textColor1);
                lbl.setFont(new Font("Arial", Font.BOLD, rowHeight / 2 + 2));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setVerticalAlignment(SwingConstants.CENTER);
                return lbl;
            }
        });
        Dimension headerDim = table.getTableHeader().getPreferredSize();
        headerDim.height = rowHeight;
        table.getTableHeader().setPreferredSize(headerDim);
    }
    
    /**
     * Adjusts the widths of the table columns based on content and available space.
     *
     * @param totalWidth the total width available for the table
     * @param rowHeight the height of each row in the table
     */
    private void adjustColumnWidths(int totalWidth, int rowHeight) {
        int[] columnWidths = new int[table.getColumnCount()];
        int usedWidth = rowHeight;
        for (int col = 1; col < table.getColumnCount(); col++) {
            int maxWidth = 50;
            Component headerComp = table.getTableHeader().getDefaultRenderer()
                .getTableCellRendererComponent(table, table.getColumnModel().getColumn(col).getHeaderValue(), false, false, -1, col);
            maxWidth = Math.max(maxWidth, headerComp.getPreferredSize().width + 10);
            for (int row = 0; row < table.getRowCount(); row++) {
                Component cellComp = table.prepareRenderer(table.getCellRenderer(row, col), row, col);
                maxWidth = Math.max(maxWidth, cellComp.getPreferredSize().width + 10);
            }
            columnWidths[col] = maxWidth;
            usedWidth += maxWidth;
        }
        if (usedWidth < totalWidth) {
            int extraSpace = totalWidth - usedWidth;
            columnWidths[1] += extraSpace / 2;
            columnWidths[2] += extraSpace / 2;
        }
        columnWidths[0] = rowHeight;
        for (int col = 0; col < table.getColumnCount(); col++) {
            table.getColumnModel().getColumn(col).setPreferredWidth(columnWidths[col]);
        }
    }

    /**
     * Inner class representing a header with a checkbox to select/deselect all rows.
     */
    private class CheckBoxHeader extends JCheckBox implements javax.swing.table.TableCellRenderer, java.awt.event.MouseListener {
        private final JTable tbl;
        private final int col;

        /**
         * Constructs a CheckBoxHeader for the specified table and column.
         *
         * @param t the JTable to which this header belongs
         * @param c the column index for the checkbox
         */
        CheckBoxHeader(JTable t, int c) {
            tbl = t;
            col = c;
            tbl.getTableHeader().addMouseListener(this);
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
            setBackground(buttonBackgroundColor);
            setFocusPainted(false);
            setForeground(textColor1);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean hf, int r, int c) {
            return this;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            int idx = tbl.columnAtPoint(e.getPoint());
            if (idx == col) {
                boolean currentState = !isSelected();
                setSelected(currentState);
                tbl.getTableHeader().repaint();
                for (int i = 0; i < tbl.getRowCount(); i++) {
                    tbl.setValueAt(currentState, i, col);
                }
            }
        }
        
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
}
