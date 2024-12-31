// Custom Renderer Initialization: The DefaultTableCellRenderer is created once and applied to all columns
// avoiding repeated calls during paintComponent.

package net.tfobz.vocabletrainer.gui.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vokabeltrainer.model.Karte;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class VocableTrainerInfoPanel extends VocableTrainerPanel {

	private List<Lernkartei> sets;
	
    private JLabel topic;
    private JComboBox<Lernkartei> comboBox;
    private JScrollPane scrollPane;
    private JTable table;

    public VocableTrainerInfoPanel(VocableTrainerFrame vtf) {
        super(vtf);

        barPane.setTitle("Info");

        panel.setLayout(null);

        // Topic Label
        topic = new JLabel("Topic:");
        topic.setForeground(C_nigth);

        // ComboBox
		sets = VokabeltrainerDB.getLernkarteien();
        comboBox = new JComboBox<>(sets.toArray(new Lernkartei[0]));
        comboBox.setForeground(C_nigth);
        comboBox.setBackground(C_platinum);

        panel.add(topic);
        panel.add(comboBox);

        // Table Model
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Word 1", "Word 2", "Date modified", "Card due on", "Box"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column < 2;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 2:
                    case 3:
                        return Date.class;
                    case 4:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };

        fillTable(model);
        

        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false); // Disable column reordering

        // Renderer for Date columns
        table.setDefaultRenderer(Date.class, (table, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel(new SimpleDateFormat(" dd.MM.yyyy  HH:mm:ss ").format((Date) value));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            return label;
        });

        // Ensure Positive Integer column has centered data
        table.getColumnModel().getColumn(4).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel(value.toString());
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            return label;
        });

        // Set dynamic font renderer once
        DefaultTableCellRenderer dynamicRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setFont(new Font("Arial", Font.PLAIN, (scrollPane.getHeight() / 16) / 2 + 2)); // Dynamically calculated font
                setHorizontalAlignment(SwingConstants.CENTER);
                setVerticalAlignment(SwingConstants.CENTER);
                return c;
            }
        };

        // Apply dynamic renderer to all columns
        for (int col = 0; col < table.getColumnCount(); col++) {
            table.getColumnModel().getColumn(col).setCellRenderer(dynamicRenderer);
        }

        scrollPane = new JScrollPane(table);

        panel.add(scrollPane);

        this.add(barPane);
        this.add(panel);
    }

    private void fillTable(DefaultTableModel model) {

    	Lernkartei l = (Lernkartei) comboBox.getSelectedItem();
    	
    	if (l != null && l.getNummer() != -1) {
    	    List<Karte> cards = VokabeltrainerDB.getKartenFromLernkartei(l.getNummer());
    	 for (Karte card: cards) {
    		 model.addRow(new Object[]{
                   card.getWortEins(),
                   card.getWortZwei(),
                   new Date(),  
                   new Date(), 
                   card.getFnummer() 
               });
    	 }
    	}
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set topic label bounds and font
        topic.setBounds(panel.getWidth() / 12, panel.getHeight() / 50, panel.getWidth() / 4, panel.getHeight() / 12);
        topic.setFont(new Font("Arial", Font.BOLD, topic.getHeight() / 2 + 1));

        // Set combo box bounds and font
        comboBox.setBounds((int) (panel.getWidth() / (24 / 5.0)), panel.getHeight() / 50, (int) (panel.getWidth() / (4 / 3.0)), panel.getHeight() / 12);
        comboBox.setFont(new Font("Arial", Font.PLAIN, comboBox.getHeight() / 2 + 1));

        // Set scroll pane bounds
        scrollPane.setBounds(16, panel.getHeight() / 8, panel.getWidth() - 32, panel.getHeight() - 16 - panel.getHeight() / 8);

        table.setRowHeight(scrollPane.getHeight() / 16);

        // Column width adjustment logic
        int totalWidth = scrollPane.getWidth();
        int[] columnWidths = new int[table.getColumnCount()];
        int usedWidth = 0;

        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumnModel().getColumn(col);
            int maxWidth = 50; // Minimum width, includes some padding

            // Include header width
            Component headerComp = table.getTableHeader().getDefaultRenderer()
                                       .getTableCellRendererComponent(table, column.getHeaderValue(), false, false, -1, col);
            maxWidth = Math.max(maxWidth, headerComp.getPreferredSize().width + 10);

            // Include cell content width
            for (int row = 0; row < table.getRowCount(); row++) {
                Component cellComp = table.prepareRenderer(table.getCellRenderer(row, col), row, col);
                maxWidth = Math.max(maxWidth, cellComp.getPreferredSize().width + 10);
            }

            columnWidths[col] = maxWidth;
            usedWidth += maxWidth;
        }

        // Redistribute extra space to the first two columns
        if (usedWidth < totalWidth) {
            int extraSpace = totalWidth - usedWidth;
            columnWidths[0] += extraSpace / 2;
            columnWidths[1] += extraSpace / 2;
        }

        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumnModel().getColumn(col);
            column.setPreferredWidth(columnWidths[col]);
        }
    }
}
