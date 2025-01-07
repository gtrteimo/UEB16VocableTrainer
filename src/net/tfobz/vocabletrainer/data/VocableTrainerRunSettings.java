package net.tfobz.vocabletrainer.data;

import net.tfobz.vokabeltrainer.model.Fach;
import net.tfobz.vokabeltrainer.model.Lernkartei;
import net.tfobz.vokabeltrainer.model.VokabeltrainerDB;

public class VocableTrainerRunSettings {
	
	public static enum TimeUnit {
	    Seconds, Minutes, Hours;
	} 
	
	protected Fach box;
	protected Lernkartei set;
	
	protected boolean cardTimeLimitState = false;
	protected int cardTimeLimit;
	protected boolean totalTimeLimitState = false;
	protected int totalTimeLimit;
	protected boolean cardLimitState = false;
	protected int cardLimit;
	protected boolean practiceRun = false;
	protected boolean caseSensitiv = false;
	protected int direction = 0;
	
	public VocableTrainerRunSettings(Fach fach, Lernkartei lernkartei) {
		if(fach==null||lernkartei==null) {
			throw new NullPointerException("Invalid Fach or Lernkartei");
		}
		this.box = fach;
		this.set = lernkartei;
	}
	
	public boolean isCaseSensitiv() {
		return caseSensitiv;
	}

	public void setCaseSensitiv(boolean caseSensitiv) {
		this.caseSensitiv = caseSensitiv;
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
//		case Days:
//			this.totalTimeLimit = totalTimeLimit*3600*24;
//			break;
		}
		
	}
	
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
			this.totalTimeLimit = totalTimeLimit*60;
			break;
		case Hours:
			this.totalTimeLimit = totalTimeLimit*3600;
			break;
//		case Days:
//			this.totalTimeLimit = totalTimeLimit*3600*24;
//			break;
		}
	}
	
	public void setCardLimit(int cardLimit) {
		if(cardLimit < 1) {
			throw new IllegalArgumentException("Card Limit can't be 0 or smaller");
		}
		cardLimitState = true;
		this.cardLimit=cardLimit;
	}
	
	public void setParcticeRun(boolean practiceRun) {
		this.practiceRun = practiceRun;
	}
	
	public boolean isParcticeRun() {
		return practiceRun;
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

	public Fach getBox() {
		return box;
	}

	public Lernkartei getSet() {
		return set;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
}