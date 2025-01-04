package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainer2OptionDialog;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInfoDialog;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInputDialog;
import net.tfobz.vokabeltrainer.model.Karte;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

@SuppressWarnings("serial")
public class VocableTrainerInfoPanel extends VocableTrainerPanel {
	
	private final ItemListener a = (e -> {
		if (e.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Selected item: " + e.getItem());
            retriveTabel((Lernkartei) e.getItem());
        }
    });
	
	

    private List<Lernkartei> sets;
    private JLabel topic;
    private JComboBox<Lernkartei> comboBox;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton renameButton;
    private JButton deleteButton;
    DefaultTableModel model;
    DefaultTableCellRenderer dynamicRenderer;
    
    private Map<Integer, String[]> changedRows = new HashMap<>();

    public VocableTrainerInfoPanel(VocableTrainerFrame vtf) {
        super(vtf);
        barPane.setTitle("Info");
        panel.setLayout(null);
        topic = new JLabel("Topic:");
        topic.setForeground(C_nigth);
        sets = VokabeltrainerDB.getLernkarteien();
        comboBox = new JComboBox<>(sets.toArray(new Lernkartei[0]));
        comboBox.setForeground(C_nigth);
        comboBox.setBackground(C_platinum);
        renameButton = new JButton("Rename");
        renameButton.setFocusPainted(false);
        renameButton.setBorderPainted(false);
        renameButton.setForeground(C_platinum);
        renameButton.setBackground(C_slateGray);
        renameButton.setMnemonic('e');
        deleteButton = new JButton("Delete");
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setForeground(C_platinum);
        deleteButton.setBackground(C_slateGray);
        deleteButton.setMnemonic('D');
        renameButton.addActionListener(e -> {
            boolean cardSelected = false;
            for (int i = 0; i < table.getRowCount(); i++) {
                Boolean val = (Boolean) table.getValueAt(i, 0);
                if (val != null && val) {
                    cardSelected = true;
                    break;
                }
            }
            if (cardSelected) {
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
                for (int i = 0; i < table.getRowCount(); i++) {
                    table.setValueAt(false, i, 0);
                }
                renameButton.setText("Rename");
            } else {
                int selectedRow = -1;
                for (int i = 0; i < table.getRowCount(); i++) {
                    Boolean val = (Boolean) table.getValueAt(i, 0);
                    if (val != null && val) {
                        selectedRow = i;
                        break;
                    }
                }
                if (selectedRow != -1) {
                    table.changeSelection(selectedRow, 1, false, false);
                    table.editCellAt(selectedRow, 1);
                    Component editorComp = table.getEditorComponent();
                    if (editorComp instanceof JTextField) {
                        ((JTextField) editorComp).selectAll();
                        editorComp.requestFocusInWindow();
                    }
                } else {
                    Lernkartei s = (Lernkartei) comboBox.getSelectedItem();
                    if (s != null) {
                        VocableTrainerInputDialog d = new VocableTrainerInputDialog(vtf,
                            "Rename set","Enter new name for the set:", s.getBeschreibung());
                        d.setVisible(true);
                        if (d.getInput() != null && !d.getInput().trim().isEmpty()) {
                            s.setBeschreibung(d.getInput());
                            VokabeltrainerDB.aendernLernkartei(s);
                            retrive();
                        }
                        d.closeDialog();
                    }
                }
            }
        });
        deleteButton.addActionListener(e -> {
            boolean cardSelected = false;
            for (int i = 0; i < table.getRowCount(); i++) {
                Boolean val = (Boolean) table.getValueAt(i, 0);
                if (val != null && val) {
                    cardSelected = true;
                    break;
                }
            }
            if (cardSelected) {
                for (int i = table.getRowCount() - 1; i >= 0; i--) {
                    Boolean val = (Boolean) table.getValueAt(i, 0);
                    if (val != null && val) {
                        Integer knummer = (Integer) table.getValueAt(i, 6);
                        VokabeltrainerDB.loeschenKarte(knummer);
                        ((DefaultTableModel) table.getModel()).removeRow(i);
                    }
                }
                repaint();
            } else {
                Lernkartei s = (Lernkartei) comboBox.getSelectedItem();
                if (s != null) {
                    VocableTrainer2OptionDialog d = new VocableTrainer2OptionDialog(vtf, "Confirm Delete","Are you sure you want to delete this set?", "Yes", "No");
                    d.setVisible(true);
                    if (d.getAnswer()) {
                        VokabeltrainerDB.loeschenLernkartei(s.getNummer());
                        retrive();
                        repaint();
                    }
                    d.closeDialog();
                }
            }
        });
        panel.add(topic);
        panel.add(comboBox);
        panel.add(renameButton);
        panel.add(deleteButton);
        dynamicRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean hf, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, v, sel, hf, r, c);
                comp.setFont(new Font("Arial", Font.PLAIN, (scrollPane == null ? 30 : (scrollPane.getHeight() / 16) / 2 + 2)));
                comp.setBackground(C_platinum);
                setHorizontalAlignment(SwingConstants.CENTER);
                setVerticalAlignment(SwingConstants.CENTER);
                return comp;
            }
        };
        this.add(barPane);
        this.add(panel);
    }
   
    private DefaultTableModel fillTable(DefaultTableModel model, Lernkartei l) {

        if (l != null && l.getNummer() != -1) {
            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            List<Karte> cards = VokabeltrainerDB.getKartenUndBoxenVonLernkartei(l.getNummer());
            for (Karte card : cards) {
                model.addRow(new Object[]{
                    false,
                    card.getWortEins(),
                    card.getWortZwei(),
                    new Date(),
                    new Date(),
                    card.getFachBeschreibung(),
//                    card.getFnummer(),
                    card.getNummer()
                });
//                System.out.println(card.getFnummer());
            }
        }
        return model;
    }
    @Override
    public void retrive() {
		System.err.println("RETRIVE THREAD: " + Thread.currentThread());
		comboBox.removeItemListener(a);
        sets = VokabeltrainerDB.getLernkarteien();
        comboBox.removeAllItems();
        if (sets != null) {
            for (Lernkartei set : sets) comboBox.addItem(set);
        } else {
            new VocableTrainerInfoDialog(vtf, "Error", "The DatabaseTabels have disapperared!").setVisible(true);;
        }
        comboBox.addItemListener(a);
        retriveTabel((Lernkartei) comboBox.getSelectedItem());
        System.err.println("REPAINT");
        repaint();
    }

    protected void retriveTabel(Lernkartei l) {
		System.err.println("RETRIVE TABLE THREAD: " + Thread.currentThread());
        if (l == null) return;
        model = new DefaultTableModel(
            new Object[]{" ", l.getWortEinsBeschreibung(), l.getWortZweiBeschreibung(), "Date modified", "Card due on", "Box", "knummer"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return c == 0 || c == 1 || c == 2;
            }
            @Override
            public Class<?> getColumnClass(int i) {
                switch (i) {
                    case 0: return Boolean.class;
                    case 3:
                    case 4: return Date.class;
                    case 5:
                    case 6: return Integer.class;
                    default: return String.class;
                }
            }
        };
        ex[11] = Executors.newSingleThreadExecutor();
        Future<DefaultTableModel> future = ex[11].submit(() -> fillTable(model, l));
                
        table = new JTable();
        table.setBorder(null);
        table.getTableHeader().setReorderingAllowed(false);
        JCheckBox editorBox = new JCheckBox();
        editorBox.setBackground(C_slateGray);
        editorBox.setFocusPainted(false);
        editorBox.setBorderPainted(false);
        editorBox.setOpaque(true);
        editorBox.setForeground(C_nigth);
        table.setDefaultEditor(Boolean.class, new DefaultCellEditor(editorBox));
        table.setDefaultRenderer(Boolean.class, (t, v, sel, hf, r, c) -> {
            JCheckBox cb = new JCheckBox();
            cb.setSelected(v != null && (Boolean) v);
            cb.setFocusPainted(false);
            cb.setBorderPainted(false);
            cb.setOpaque(true);
            if (sel) {
                cb.setBackground(t.getSelectionBackground());
                cb.setForeground(t.getSelectionForeground());
            } else {
                cb.setBackground(C_platinum);
                cb.setForeground(C_nigth);
            }
            cb.setHorizontalAlignment(SwingConstants.CENTER);
            cb.setVerticalAlignment(SwingConstants.CENTER);
            cb.setFont(new Font("Arial", Font.PLAIN, (scrollPane == null ? 30 : (scrollPane.getHeight() / 16) / 2 + 2)));
            return cb;
        });
        table.setDefaultRenderer(Date.class, (t, v, sel, hf, r, c) -> {
            JLabel lab = new JLabel(new SimpleDateFormat(" dd.MM.yyyy  HH:mm:ss ").format((Date) v));
            lab.setHorizontalAlignment(SwingConstants.CENTER);
            lab.setVerticalAlignment(SwingConstants.CENTER);
            lab.setBackground(C_platinum);
            lab.setOpaque(true);
            return lab;
        });

        DefaultCellEditor textEditor = new DefaultCellEditor(new JTextField()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                JTextField field = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
                SwingUtilities.invokeLater(field::selectAll);
                return field;
            }
        };
        
        while (!future.isDone()) {
        	try {
        		System.out.println("Hello World");
				Thread.sleep(500);
			} catch (InterruptedException e1) {}
        }
        
        try {
			table.setModel(future.get());
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
        
        table.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxHeader(table, 0));
        table.getColumnModel().getColumn(5).setCellRenderer((t, v, sel, hf, r, c) -> {
            JLabel lab = new JLabel(String.valueOf(v));
            lab.setHorizontalAlignment(SwingConstants.CENTER);
            lab.setVerticalAlignment(SwingConstants.CENTER);
            lab.setBackground(C_platinum);
            lab.setOpaque(true);
            return lab;
        });
        for (int col = 1; col < table.getColumnCount() - 1; col++) {
            table.getColumnModel().getColumn(col).setCellRenderer(dynamicRenderer);
        }
        textEditor.setClickCountToStart(1);
        table.getColumnModel().getColumn(1).setCellEditor(textEditor);
        table.getColumnModel().getColumn(2).setCellEditor(textEditor);
        scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        table.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                if (e.getColumn() == 0) {
                    boolean atLeastOneSelected = false;
                    for (int i = 0; i < table.getRowCount(); i++) {
                        if (Boolean.TRUE.equals(table.getValueAt(i, 0))) {
                            atLeastOneSelected = true;
                            break;
                        }
                    }
                    renameButton.setText(atLeastOneSelected ? "Save" : "Rename");
                    table.getTableHeader().repaint();
                }
                if (e.getColumn() == 1 || e.getColumn() == 2) {
                    int row = e.getFirstRow();
                    String w1 = (String) table.getValueAt(row, 1);
                    String w2 = (String) table.getValueAt(row, 2);
                    changedRows.put(row, new String[]{w1, w2});
                }
            }
        });
                
        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setWidth(0);
        
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int w = panel.getWidth();
        int h = panel.getHeight();
        
        topic.setBounds(w / 12, h / 50, w / 4, h / 12);
        topic.setFont(new Font("Arial", Font.BOLD, topic.getHeight() / 2 + 1));
        
        comboBox.setBounds((int)(w / (24 / 5.0)), h / 50, (int)(w / (48 / 31.0)), h / 12);
        comboBox.setFont(new Font("Arial", Font.PLAIN, comboBox.getHeight() / 2 + 1));
        renameButton.setBounds((int)(w - 8 - (w / (4 / 3.0) / 6)), h / 50,
            (int)(w / (4 / 3.0) / 6), h / 24 - 1);
        renameButton.setFont(new Font("Arial", Font.PLAIN, renameButton.getHeight() / 2 + 1));
        deleteButton.setBounds((int)(w - 8 - (w / (4 / 3.0) / 6)), 
            h / 50 + h / 24 + 2,
            (int)(w / (4 / 3.0) / 6), h / 24 - 1);
        deleteButton.setFont(new Font("Arial", Font.PLAIN, deleteButton.getHeight() / 2 + 1));
        if (scrollPane != null) {
        	scrollPane.setBounds(16, h / 8, w - 32, h - 16 - h / 8);
        	int rowHeight = scrollPane.getHeight() / 16;
            table.setRowHeight(rowHeight);

            table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                    JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, v, s, f, r, c);
                    lbl.setOpaque(true);
                    lbl.setBackground(C_slateGray);
                    lbl.setForeground(C_platinum);
                    lbl.setFont(new Font("Arial", Font.BOLD, rowHeight / 2 + 2));
                    lbl.setHorizontalAlignment(SwingConstants.CENTER);
                    lbl.setVerticalAlignment(SwingConstants.CENTER);
                    return lbl;
                }
            });
            Dimension headerDim = table.getTableHeader().getPreferredSize();
            headerDim.height = rowHeight;
            table.getTableHeader().setPreferredSize(headerDim);

            int totalWidth = scrollPane.getWidth();
            int[] columnWidths = new int[table.getColumnCount()];
            int usedWidth = rowHeight;
            for (int col = 1; col < table.getColumnCount(); col++) {
                int maxWidth = 50;
                Component headerComp = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(
                    table, table.getColumnModel().getColumn(col).getHeaderValue(), false, false, -1, col
                );
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
    }


    private class CheckBoxHeader extends JCheckBox implements TableCellRenderer, MouseListener {
        private final JTable tbl;
        private final int col;
        CheckBoxHeader(JTable t, int c) {
            tbl = t;
            col = c;
            tbl.getTableHeader().addMouseListener(this);
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
            setBackground(C_slateGray);
            setFocusPainted(false);
            setForeground(C_platinum);
        }
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean hf, int r, int c) {
            return this;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            int idx = tbl.columnAtPoint(e.getPoint());
            if (idx == col) {
                boolean cur = !isSelected();
                setSelected(cur);
                tbl.getTableHeader().repaint();
                for (int i = 0; i < tbl.getRowCount(); i++) {
                    tbl.setValueAt(cur, i, col);
                }
            }
        }
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
    }
}