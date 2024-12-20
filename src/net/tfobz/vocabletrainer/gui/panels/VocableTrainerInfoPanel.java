package net.tfobz.vocabletrainer.gui.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.tfobz.vocabletrainer.gui.VocableTrainerFrame;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

@SuppressWarnings("serial")
public class VocableTrainerInfoPanel extends VocableTrainerPanel {
		
	private List<Lernkartei> sets;
	
	private JLabel topic;
	private JComboBox<String> comboBox;
	private JScrollPane scrollPane;
	private JTable table;
	
	public VocableTrainerInfoPanel (VocableTrainerFrame vtf) {
		super(vtf);
		
		barPane.setTitle("Info");
		
		panel.setLayout(null);

		topic = new JLabel("Topic:");
		topic.setForeground(C_nigth);
		
		sets = VokabeltrainerDB.getLernkarteien();
		if (sets != null) {
			List<String> stringsSets = sets.stream()
	                .map(Lernkartei::toString)
	                .collect(Collectors.toList());
			
			comboBox = new JComboBox<String>(stringsSets.toArray(new String[0]));
		} else {
			comboBox = new JComboBox<String>();
		}
		
		comboBox.setForeground(C_nigth);
		comboBox.setBackground(C_platinum);
		
		panel.add(topic);
		panel.add(comboBox);
		
		table = new JTable(new DefaultTableModel(new Object[]{"Column 1", "Column 2", "Column 3", "Column 4", "Column 5"}, 0));
		
		scrollPane = new JScrollPane(table);
		
		panel.add(scrollPane);
		
		this.add(barPane);
		this.add(panel);
	}
	
    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        topic.setBounds(panel.getWidth()/12, panel.getHeight()/50, panel.getWidth()/4, panel.getHeight()/12);
        topic.setFont(new Font ("Arial", Font.BOLD, topic.getHeight()/2 + 5));
        
        comboBox.setBounds((int)(panel.getWidth()/(24/5.0)), panel.getHeight()/50, (int)(panel.getWidth()/(4/3.0)), panel.getHeight()/12);
        comboBox.setFont(new Font ("Arial", Font.PLAIN, comboBox.getHeight()/2 + 5));
        
        scrollPane.setBounds(16, panel.getHeight()/8 , panel.getWidth()-32, panel.getHeight() - 16 - panel.getHeight()/8);
    }
}
