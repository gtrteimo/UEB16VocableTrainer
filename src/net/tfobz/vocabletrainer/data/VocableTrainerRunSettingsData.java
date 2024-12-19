package net.tfobz.vocabletrainer.data;

import java.util.List;

import net.tfobz.vokabeltrainer.model.*;

public class VocableTrainerRunSettingsData {
	
	public static enum TimeUnit {
	    Seconds, Minutes, Hours, Days;
	} 
	
	public Lernkartei set;
	public Fach boxes;
	
	public boolean timePerCardState = true;
	public int timePerCard = 30;
	public TimeUnit timeUnit1 = TimeUnit.Seconds;
	
	public boolean timeForCardsState = false;
	public int timeForCards = 5;
	public TimeUnit timeUnit2 = TimeUnit.Minutes;
	
	public boolean caseSensitiveState = true;
	
	public boolean cardLimitState = true;
	public int amountOfCards = 10;
	
	public boolean practiceRunState = false;
}
