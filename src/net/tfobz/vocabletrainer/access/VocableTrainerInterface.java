package net.tfobz.vocabletrainer.access;

import net.tfobz.vocabletrainer.model.*;

import java.util.ArrayList;

public interface VocableTrainerInterface {
	
	public static ArrayList<Fach> getBoxes () {return null;}
	public static Fach getBox (int index) {return null;}
	
	public static void addBox (Fach box) {}
	
	
	public static ArrayList<Lernkartei> getSets () {return null;}
	public static Lernkartei getSet (int index) {return null;}
	
	public static void addSet (Lernkartei set) {}
	
	
	public static ArrayList<Karte> getCards (Lernkartei set) {return null;}
	public static Karte getCard (int index) {return null;}
	
	public static void addCard (Karte card, Lernkartei set) {}
}
