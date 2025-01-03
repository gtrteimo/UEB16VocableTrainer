package net.tfobz.vocabletrainer.main;	

import net.tfobz.vocabletrainer.gui.*;
import net.tfobz.vokabeltrainer.model.*;
import java.sql.*;
/**
 * @author gtrteimo && 21chinat
 */
public class VocableTrainerMain {
	/**
	 * The main of the Programm... what should i tell you?
	 * @param args
	 */
	public static void main(String[] args) {
		
		 try (Connection con = VokabeltrainerDB.getConnection();
	        Statement stmt = con.createStatement()) {
	        stmt.executeQuery("SELECT 1 FROM lernkarteien");
	    } catch (Exception e) {
	        VokabeltrainerDB.erstellenTabellen();
	    }
		
		VocableTrainerFrame vtf = new VocableTrainerFrame();
		vtf.setVisible(true);
	}
}
