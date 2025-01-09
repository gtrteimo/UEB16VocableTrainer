package net.tfobz.vokabeltrainer.model;

import java.util.Date;
import java.util.Hashtable;

/**
 * Represents a flashcard containing two words, a question direction, and information 
 * on whether case sensitivity should be considered when comparing the words. 
 * Both words must be provided to create a valid card.
 * 
 * <p>This class is used within the vocabulary trainer application to facilitate 
 * the learning and testing of word pairs in different directions.</p>
 */
public class Karte {
    protected int nummer = -1;
    protected String wortEins = null;
    protected String wortZwei = null;
    protected boolean richtung = true;
    protected boolean grossKleinschreibung = false;
    
    protected String fachBeschreibung;
    protected Date gerlerntAm;
    protected int fnummer = -1;

    protected Hashtable<String, String> fehler = null;
    
    /**
     * Default constructor for Karte.
     */
    public Karte() {
    }

    /**
     * Constructs a Karte with the specified parameters.
     * 
     * @param nummer The unique identifier for the card.
     * @param wortEins The first word of the card.
     * @param wortZwei The second word of the card.
     * @param richtung The direction of the question (true for wortEins to wortZwei, false otherwise).
     * @param grossKleinschreibung Whether to consider case sensitivity when comparing words.
     */
    public Karte(int nummer, String wortEins, String wortZwei, boolean richtung, boolean grossKleinschreibung) {
        this.nummer = nummer;
        this.setWortEins(wortEins);
        this.setWortZwei(wortZwei);
        this.richtung = richtung;
        this.grossKleinschreibung = grossKleinschreibung;
    }
    
    /**
     * Constructs a Karte with the specified parameters, including a fach number.
     * 
     * @param nummer The unique identifier for the card.
     * @param wortEins The first word of the card.
     * @param wortZwei The second word of the card.
     * @param richtung The direction of the question.
     * @param grossKleinschreibung Whether to consider case sensitivity when comparing words.
     * @param fnummer The fach number associated with the card.
     */
    public Karte(int nummer, String wortEins, String wortZwei, boolean richtung, boolean grossKleinschreibung, int fnummer) {
        this.nummer = nummer;
        this.setWortEins(wortEins);
        this.setWortZwei(wortZwei);
        this.richtung = richtung;
        this.grossKleinschreibung = grossKleinschreibung;
        this.fnummer = fnummer;
    }
    
    /**
     * Constructs a Karte with detailed parameters including fach description and learning date.
     * 
     * @param nummer The unique identifier for the card.
     * @param wortEins The first word of the card.
     * @param wortZwei The second word of the card.
     * @param fachBeschreibung Description of the subject area.
     * @param gelerntAm The date when the card was learned.
     * @param fnummer The fach number associated with the card.
     * @param richtung The direction of the question.
     * @param grossKleinschreibung Whether to consider case sensitivity when comparing words.
     */
    public Karte(int nummer, String wortEins, String wortZwei, String fachBeschreibung, Date gelerntAm, int fnummer, boolean richtung, boolean grossKleinschreibung) {
        this.nummer = nummer;
        this.setWortEins(wortEins);
        this.setWortZwei(wortZwei);
        this.fachBeschreibung = fachBeschreibung;
        this.gerlerntAm = gelerntAm;
        this.fnummer = fnummer;
        this.richtung = richtung;
        this.grossKleinschreibung = grossKleinschreibung;
    }
    
    /**
     * Constructs a Karte with fach description and direction settings.
     * 
     * @param nummer The unique identifier for the card.
     * @param wortEins The first word of the card.
     * @param wortZwei The second word of the card.
     * @param fachBeschreibung Description of the subject area.
     * @param richtung The direction of the question.
     * @param grossKleinschreibung Whether to consider case sensitivity when comparing words.
     */
    public Karte(int nummer, String wortEins, String wortZwei, String fachBeschreibung, boolean richtung, boolean grossKleinschreibung) {
        this.nummer = nummer;
        setWortEins(wortEins);
        setWortZwei(wortZwei);
        this.richtung = richtung;
        this.grossKleinschreibung = grossKleinschreibung;
        this.fachBeschreibung = fachBeschreibung;
    }
    
    /**
     * Retrieves the date when the card was learned.
     * 
     * @return The learning date.
     */
    public Date getGerlerntAm() {
        return gerlerntAm;
    }

    /**
     * Retrieves the description of the subject area.
     * 
     * @return The fach description.
     */
    public String getFachBeschreibung() {
        return fachBeschreibung;
    }
  
    /**
     * Validates that both wortEins and wortZwei have been entered.
     * If either word is missing, an error message is added to the fehler hashtable.
     */
    public void validiere() {
        fehler = null;
        if (wortEins == null || wortEins.trim().isEmpty()) {
            fehler = new Hashtable<>();
            fehler.put("wortEins", "Must be provided");
        }
        if (wortZwei == null || wortZwei.trim().isEmpty()) {
            if (fehler == null)
                fehler = new Hashtable<>();
            fehler.put("wortZwei", "Must be provided");
        }
    }
    
    /**
     * Determines whether the provided word matches the expected word based on the question direction 
     * and case sensitivity settings.
     * 
     * @param wort The word to be checked.
     * @return {@code true} if the word is correct, {@code false} otherwise.
     */
    public boolean getRichtig(String wort) {
        if (wort == null) {
            return false;
        }
        if (richtung) {
            // wortEins is known, wortZwei must be provided
            if (grossKleinschreibung) {
                return wort.equals(this.wortZwei);
            } else {
                return wort.equalsIgnoreCase(this.wortZwei);
            }
        } else {
            // wortZwei is known, wortEins must be provided
            if (grossKleinschreibung) {
                return wort.equals(this.wortEins);
            } else {
                return wort.equalsIgnoreCase(this.wortEins);
            }
        }
    }

    /**
     * Checks if this Karte is equal to another object.
     * Two Karten are considered equal if they have the same nummer, wortEins, and wortZwei.
     * 
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Karte k = (Karte) o;

        if (nummer != k.nummer) return false;
        if (wortEins != null ? !wortEins.equals(k.wortEins) : k.wortEins != null) return false;
        return wortZwei != null ? wortZwei.equals(k.wortZwei) : k.wortZwei == null;
    }
    
    /**
     * Retrieves the error messages generated during validation.
     * 
     * @return A hashtable containing error messages, or {@code null} if there are no errors.
     */
    public Hashtable<String, String> getFehler() {
        return fehler;
    }
    
    /**
     * Returns a string representation of the Karte.
     * 
     * @return A string containing the nummer, wortEins, wortZwei, grossKleinschreibung, and richtung.
     */
    @Override
    public String toString() {
        return nummer + " " + wortEins + " " + wortZwei + " " + grossKleinschreibung + " " + richtung;
    }
    
    /**
     * Retrieves the unique identifier of the card.
     * 
     * @return The nummer of the card.
     */
    public int getNummer() {
        return nummer;
    }

    /**
     * Retrieves the first word of the card.
     * 
     * @return The first word (wortEins).
     */
    public String getWortEins() {
        return wortEins;
    }

    /**
     * Sets the first word of the card.
     * Trims any leading or trailing whitespace from the input.
     * 
     * @param wortEins The first word to set.
     */
    public void setWortEins(String wortEins) {
        this.wortEins = (wortEins != null) ? wortEins.trim() : null;
    }

    /**
     * Retrieves the second word of the card.
     * 
     * @return The second word (wortZwei).
     */
    public String getWortZwei() {
        return wortZwei;
    }

    /**
     * Sets the second word of the card.
     * Trims any leading or trailing whitespace from the input.
     * 
     * @param wortZwei The second word to set.
     */
    public void setWortZwei(String wortZwei) {
        this.wortZwei = (wortZwei != null) ? wortZwei.trim() : null;
    }

    /**
     * Determines the direction of the question.
     * 
     * @return {@code true} if the question direction is from wortEins to wortZwei, {@code false} otherwise.
     */
    public boolean getRichtung() {
        return richtung;
    }

    /**
     * Checks whether case sensitivity is considered when comparing words.
     * 
     * @return {@code true} if case sensitivity is enabled, {@code false} otherwise.
     */
    public boolean getGrossKleinschreibung() {
        return grossKleinschreibung;
    }

    /**
     * Retrieves the fach number associated with the card.
     * 
     * @return The fach number (fnummer).
     */
    public int getFnummer() {
        return fnummer;
    }

    /**
     * Sets the fach number for the card.
     * 
     * @param fnummer The fach number to set.
     */
    public void setFnummer(int fnummer) {
        this.fnummer = fnummer;
    }
}
