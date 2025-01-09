package net.tfobz.vocabletrainer.data;

import net.tfobz.vokabeltrainer.model.Fach;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

/**
 * This class manages the settings for running a session of the Vocable Trainer.
 * It allows customization of time limits, card limits, and other session parameters.
 */
public class VocableTrainerRunSettings {
	
    /**
     * Enumeration representing the time unit options for setting limits.
     */
	public static enum TimeUnit {
	    Seconds, Minutes, Hours;
	} 
	
	protected Fach box; // Represents the selected box for the session
	protected Lernkartei set; // Represents the set of flashcards for the session
	
	protected boolean cardTimeLimitState = false;
	protected int cardTimeLimit;
	protected boolean totalTimeLimitState = false;
	protected int totalTimeLimit;
	protected boolean cardLimitState = false;
	protected int cardLimit;
	protected boolean practiceRun = false;
	protected boolean caseSensitiv = false;
	protected int direction = 0;
	
    /**
     * Constructs a VocableTrainerRunSettings object with the given Fach and Lernkartei.
     * 
     * @param fach the box representing the Fach
     * @param lernkartei the set of flashcards
     * @throws NullPointerException if either fach or lernkartei is null
     */
	public VocableTrainerRunSettings(Fach fach, Lernkartei lernkartei) {
		if(fach == null || lernkartei == null) {
			throw new NullPointerException("Invalid Fach or Lernkartei");
		}
		this.box = fach;
		this.set = lernkartei;
	}
	
    /**
     * Returns whether case sensitivity is enabled.
     * 
     * @return true if case sensitivity is enabled, false otherwise
     */
	public boolean isCaseSensitiv() {
		return caseSensitiv;
	}

    /**
     * Sets the case sensitivity for the session.
     * 
     * @param caseSensitiv true to enable case sensitivity, false otherwise
     */
	public void setCaseSensitiv(boolean caseSensitiv) {
		this.caseSensitiv = caseSensitiv;
	}

    /**
     * Sets the time limit for each card in the session.
     * 
     * @param cardTimeLimit the time limit value
     * @param unit the unit of time for the limit (Seconds, Minutes, or Hours)
     * @throws IllegalArgumentException if the time limit is less than 1
     */
	public void setCardTimeLimit(int cardTimeLimit, TimeUnit unit) {
		if(cardTimeLimit < 1) {
			throw new IllegalArgumentException("Time Limit can't be 0 or smaller");
		}
		cardTimeLimitState = true;
		switch (unit) {
		case Seconds:
			this.cardTimeLimit = cardTimeLimit;
			break;
		case Minutes:
			this.cardTimeLimit = cardTimeLimit * 60;
			break;
		case Hours:
			this.cardTimeLimit = cardTimeLimit * 3600;
			break;
		}
	}
	
    /**
     * Sets the total time limit for the session.
     * 
     * @param totalTimeLimit the total time limit value
     * @param unit the unit of time for the limit (Seconds, Minutes, or Hours)
     * @throws IllegalArgumentException if the time limit is less than 1
     */
	public void setTotalTimeLimit(int totalTimeLimit, TimeUnit unit) {
		if(totalTimeLimit < 1) {
			throw new IllegalArgumentException("Time Limit can't be 0 or smaller");
		}
		this.totalTimeLimitState = true;
		switch (unit) {
		case Seconds:
			this.totalTimeLimit = totalTimeLimit;
			break;
		case Minutes:
			this.totalTimeLimit = totalTimeLimit * 60;
			break;
		case Hours:
			this.totalTimeLimit = totalTimeLimit * 3600;
			break;
		}
	}
	
    /**
     * Sets the card limit for the session.
     * 
     * @param cardLimit the maximum number of cards
     * @throws IllegalArgumentException if the card limit is less than 1
     */
	public void setCardLimit(int cardLimit) {
		if(cardLimit < 1) {
			throw new IllegalArgumentException("Card Limit can't be 0 or smaller");
		}
		cardLimitState = true;
		this.cardLimit = cardLimit;
	}
	
    /**
     * Enables or disables practice mode for the session.
     * 
     * @param practiceRun true to enable practice mode, false to disable it
     */
	public void setParcticeRun(boolean practiceRun) {
		this.practiceRun = practiceRun;
	}
	
    /**
     * Returns whether practice mode is enabled.
     * 
     * @return true if practice mode is enabled, false otherwise
     */
	public boolean isParcticeRun() {
		return practiceRun;
	}
	
    /**
     * Returns the time limit for each card in the session.
     * 
     * @return the card time limit in seconds
     */
	public int getCardTimeLimit() {
		return cardTimeLimit;
	}

    /**
     * Returns the total time limit for the session.
     * 
     * @return the total time limit in seconds
     */
	public int getTotalTimeLimit() {
		return totalTimeLimit;
	}

    /**
     * Returns the card limit for the session.
     * 
     * @return the maximum number of cards
     */
	public int getCardLimit() {
		return cardLimit;
	}

    /**
     * Returns whether a card time limit is set.
     * 
     * @return true if a card time limit is set, false otherwise
     */
	public boolean isCardTimeLimit() {
		return cardTimeLimitState;
	}

    /**
     * Returns whether a total time limit is set.
     * 
     * @return true if a total time limit is set, false otherwise
     */
	public boolean isTotalTimeLimit() {
		return totalTimeLimitState;
	}

    /**
     * Returns whether a card limit is set.
     * 
     * @return true if a card limit is set, false otherwise
     */
	public boolean isCardLimit() {
		return cardLimitState;
	}

    /**
     * Returns the selected Fach for the session.
     * 
     * @return the selected Fach
     */
	public Fach getBox() {
		return box;
	}

    /**
     * Returns the selected set of flashcards for the session.
     * 
     * @return the Lernkartei set
     */
	public Lernkartei getSet() {
		return set;
	}

    /**
     * Returns the current direction for the session.
     * 
     * @return the direction
     */
	public int getDirection() {
		return direction;
	}

    /**
     * Sets the direction for the session.
     * 
     * @param direction the direction to set
     */
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
}
