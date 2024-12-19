package net.tfobz.vocabletrainer.gui.panels;

import net.tfobz.vokabeltrainer.model.Fach;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

public class VocableTrainerRunSettings {
	
	public static enum TimeUnit {
	    Seconds, Minutes, Hours;
	} 
	
	protected Fach fach;
	protected Lernkartei lernkartei;
	
	protected boolean cardTimeLimitState = false;
	protected int cardTimeLimit;
	protected boolean totalTimeLimitState = false;
	protected int totalTimeLimit;
	protected boolean cardLimitState = false;
	protected int cardLimit;
	protected boolean practiceRun = false;
	
	/**
	 * 
	 * 
	 * @param fach
	 * @param lernkartei
	 * @throws NullPointerException
	 */
	public VocableTrainerRunSettings(Fach fach, Lernkartei lernkartei) {
		if(fach==null||lernkartei==null) {
			throw new NullPointerException("Invalid Fach or Lernkartei");
		}
		this.fach = fach;
		this.lernkartei = lernkartei;
	}
	
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
			this.cardTimeLimit = cardTimeLimit*60;
			break;
		case Hours:
			this.cardTimeLimit = cardTimeLimit*3600;
			break;
		}
		
	}
	
	public void TotalTimeLimit(int totalTimeLimit, TimeUnit unit) {
		if(totalTimeLimit < 1) {
			throw new IllegalArgumentException("Time Limit can't be 0 or smaller");
		}
		this.totalTimeLimitState = true;
		switch (unit) {
		case Seconds:
			this.totalTimeLimit = totalTimeLimit;
			break;
		case Minutes:
			this.totalTimeLimit = totalTimeLimit*60;
			break;
		case Hours:
			this.totalTimeLimit = totalTimeLimit*3600;
			break;
		}
	}
	
	public void setCardLimit(int cardLimit) {
		if(cardLimit < 1 || cardLimit > VokabeltrainerDB.getKarten(fach.getNummer()).size()) {
			throw new IllegalArgumentException("Card Limit can't be 0 or smaller");
		}
		cardLimitState = true;
		this.cardLimit=cardLimit;
	}
	
	public void setParcticeRun(boolean practiceRun) {
		this.practiceRun = practiceRun;
	}
	
	public int getCardTimeLimit() {
		return cardTimeLimit;
	}

	public int getTotalTimeLimit() {
		return totalTimeLimit;
	}

	public int getCardLimit() {
		return cardLimit;
	}

	public boolean isCardTimeLimit() {
		return cardTimeLimitState;
	}

	public boolean isTotalTimeLimit() {
		return totalTimeLimitState;
	}

	public boolean isCardLimit() {
		return cardLimitState;
	}

	public Fach getFach() {
		return fach;
	}

	public Lernkartei getLernkartei() {
		return lernkartei;
	}
	
}
