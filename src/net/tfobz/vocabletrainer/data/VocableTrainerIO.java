package net.tfobz.vocabletrainer.data;

import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInfoDialog;
import net.tfobz.vokabeltrainer.model.Fach;
import net.tfobz.vokabeltrainer.model.Karte;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

public class VocableTrainerIO {
	public static void Export () {
		
	}
	public static void Import (JFrame parent, Lernkartei l) {
		 JFileChooser fc = new JFileChooser();
		    fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV or TXT Files", "csv", "txt"));
		    int selection = fc.showOpenDialog(parent);
		    if (selection == JFileChooser.APPROVE_OPTION) {
		        java.io.File file = fc.getSelectedFile();
		        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
		            String line;
		            if (l == null) {
		                new VocableTrainerInfoDialog(parent, "Error", "Please select a set first.").setVisible(true);
		                return;
		            }
		            List<Fach> faecher = VokabeltrainerDB.getFaecher(l.getNummer());
		            if (faecher == null || faecher.isEmpty()) {
		                Fach newFach = new Fach();
		                newFach.setBeschreibung("First Fach");
		                int r = VokabeltrainerDB.hinzufuegenFach(l.getNummer(), newFach);
		                if (r != 0) {
		                    new VocableTrainerInfoDialog(parent, "Error", "Failed to create a new category.").setVisible(true);
		                    return;
		                }
		                faecher = VokabeltrainerDB.getFaecher(l.getNummer());
		            }
		            Fach firstFach = faecher.get(0);
		            while ((line = br.readLine()) != null) {
		                String[] parts = line.split(";");
		                if (parts.length >= 2) {
		                    Fach currentFach = firstFach;
		                    if (parts.length == 3) {
		                        try {
		                            int index = Integer.parseInt(parts[2].trim());
		                            if (index >= 0 && index < faecher.size()) {
		                                currentFach = faecher.get(index);
		                            } else {
		                                Fach nf = new Fach();
		                                nf.setBeschreibung("Fach " + index);
		                                int r = VokabeltrainerDB.hinzufuegenFach(l.getNummer(), nf);
		                                if (r == 0) {
		                                    faecher.add(nf);
		                                    currentFach = nf;
		                                }
		                            }
		                        } catch (NumberFormatException ignored) {}
		                    }
		                    Karte c = new Karte(-1, parts[0].trim(), parts[1].trim(), l.getRichtung(), l.getGrossKleinschreibung());
		                    c.setFnummer(currentFach.getNummer());
		                    int r = VokabeltrainerDB.hinzufuegenKarte(l.getNummer(), c);
		                    if (r == -5) {
		                        new VocableTrainerInfoDialog(parent, "Error", "A card with the same content already exists: " + parts[0] + " | " + parts[1]).setVisible(true);
		                    } else if (r != 0) {
		                        new VocableTrainerInfoDialog(parent, "Error", "An error occurred while adding the card.").setVisible(true);
		                    }
		                }
		            }
		        } catch (Exception e) {
		            new VocableTrainerInfoDialog(parent, "Error", "An error occurred while importing.").setVisible(true);
		        }
		    }
	}
}
