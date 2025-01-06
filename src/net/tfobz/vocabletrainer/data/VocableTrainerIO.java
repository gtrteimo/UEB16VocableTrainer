package net.tfobz.vocabletrainer.data;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainer2OptionDialog;
import net.tfobz.vocabletrainer.gui.dialogs.VocableTrainerInfoDialog;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

public class VocableTrainerIO {
	public static void Export(JFrame parent, Lernkartei l) {
	    if (l == null) {
	        new VocableTrainerInfoDialog(parent, "Error", "Please select a set first!").setVisible(true);
	        return;
	    }

	    JFileChooser exportFileChooser = new JFileChooser();
	    exportFileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
	    exportFileChooser.setAcceptAllFileFilterUsed(false);
	    exportFileChooser.setSelectedFile(new File("export.txt")); // Default file name
	    	    
	    VocableTrainer2OptionDialog o = new VocableTrainer2OptionDialog(parent, VocableTrainerLocalization.EXPORT_EXPORT, VocableTrainerLocalization.EXPORT_QUESTION, VocableTrainerLocalization.DIALOG_TWO_OPTION_YES, VocableTrainerLocalization.DIALOG_TWO_OPTION_NO);
	    o.setVisible(true);
	    
	    if (o.getAnswered() == false) {
	    	return;
	    }
	    
	    if (exportFileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
	        String path = exportFileChooser.getSelectedFile().getAbsolutePath();
	        if (!path.endsWith(".txt")) {
	            path += ".txt"; // Ensure file has .txt extension
	        }
	        
	        int result = VokabeltrainerDB.exportierenKarten(l.getNummer(), path, o.getAnswer()); // Set 'true' for exporting with categories
	        switch (result) {
	            case 0:
	                new VocableTrainerInfoDialog(parent, "Info", "Data was successfully exported!").setVisible(true);
	                break;
	            case -1:
	                new VocableTrainerInfoDialog(parent, "Error", "An export error occurred!").setVisible(true);
	                break;
	            case -3:
	                new VocableTrainerInfoDialog(parent, "Error", "The flashcard set with the number " + l.getNummer() + " does not exist.").setVisible(true);
	                break;
	            default:
	                new VocableTrainerInfoDialog(parent, "Error", "An unknown error occurred during export!").setVisible(true);
	        }
	    }
	    o.closeDialog();
	}
	public static void Import(JFrame parent, Lernkartei l) {
	    if (l == null) {
	        new VocableTrainerInfoDialog(parent, "Error", "Please select a set first!").setVisible(true);
	        
	        
            new VocableTrainerInfoDialog(parent, "Error", "An import error occurred!").setVisible(true);
            new VocableTrainerInfoDialog(parent, "Error", "An import error occurred! The file does not exist.").setVisible(true);
            new VocableTrainerInfoDialog(parent, "Error", "An import error occurred! The flashcard set with the number " +"50"+ " does not exist.").setVisible(true);
            new VocableTrainerInfoDialog(parent, "Info", "Data was successfully imported!").setVisible(true);
            new VocableTrainerInfoDialog(parent, "Warning", "The file \"" + "" + "\" does not exist.").setVisible(true);
            new VocableTrainerInfoDialog(parent, "Warning", "The file \"" + "" + "\" is not a text file (*.txt).").setVisible(true);

	        
	        
	        return;
	    }
	    JFileChooser importFileChooser = new JFileChooser();
	    importFileChooser.setMultiSelectionEnabled(false);
	    importFileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
	    importFileChooser.setAcceptAllFileFilterUsed(false);
	    if (importFileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
	        String path = importFileChooser.getSelectedFile().getAbsolutePath();
	        if (path.endsWith(".txt")) {
	            File file = new File(path);
	            if (!file.isDirectory() && file.isFile()) {
	                switch (VokabeltrainerDB.importierenKarten(l.getNummer(), path)) {
	                    case -1:
	                        new VocableTrainerInfoDialog(parent, "Error", "An import error occurred!").setVisible(true);
	                        break;
	                    case -2:
	                        new VocableTrainerInfoDialog(parent, "Error", "An import error occurred! The file does not exist.").setVisible(true);
	                        break;
	                    case -3:
	                        new VocableTrainerInfoDialog(parent, "Error", "An import error occurred! The flashcard set with the number " + l.getNummer() + " does not exist.").setVisible(true);
	                        break;
	                    default:
	                        new VocableTrainerInfoDialog(parent, "Info", "Data was successfully imported!").setVisible(true);
	                        return;
	                }
	            } else {
	                new VocableTrainerInfoDialog(parent, "Warning", "The file \"" + path + "\" does not exist.").setVisible(true);
	            }
	        } else {
	            new VocableTrainerInfoDialog(parent, "Warning", "The file \"" + path + "\" is not a text file (*.txt).").setVisible(true);
	        }
	    }
	}

}
