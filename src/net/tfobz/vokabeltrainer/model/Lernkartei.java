package net.tfobz.vokabeltrainer.model;

import java.util.Hashtable;

/**
 * Represents a learning card in a vocabulary trainer application.
 * A learning card contains a description and descriptions of two associated words.
 * It also manages settings such as learning direction and case sensitivity.
 * 
 * <p>This class provides functionalities to validate input data and track validation errors.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 *     Lernkartei card = new Lernkartei("Basic Greetings", "Hello", "Hallo", true, false);
 *     card.validate();
 *     if (card.getFehler() != null) {
 *         // Handle validation errors
 *     }
 * </pre>
 */
public class Lernkartei {
    // Unique identifier for the learning card
    protected int nummer = -1;
    
    // Description of the learning card
    protected String beschreibung = null;
    
    // Description of the first word
    protected String wortEinsBeschreibung = null;
    
    // Description of the second word
    protected String wortZweiBeschreibung = null;
    
    // Direction of learning (true for one direction, false for both)
    protected boolean richtung = true;
    
    // Case sensitivity setting
    protected boolean grossKleinschreibung = false;

    // Hashtable to store validation errors
    protected Hashtable<String, String> fehler = null;

    /**
     * Default constructor.
     * Initializes a Lernkartei instance with default values.
     */
    public Lernkartei() {
    }

    /**
     * Constructs a Lernkartei with all fields specified.
     *
     * @param nummer The unique number of the learning card.
     * @param beschreibung The description of the learning card.
     * @param wortEinsBeschreibung The description of the first word.
     * @param wortZweiBeschreibung The description of the second word.
     * @param richtung The direction of learning (true for one direction, false for both).
     * @param grossKleinschreibung Whether case sensitivity is enabled.
     */
    public Lernkartei(int nummer, String beschreibung, String wortEinsBeschreibung, String wortZweiBeschreibung,
            boolean richtung, boolean grossKleinschreibung) {
        this.nummer = nummer;
        this.beschreibung = beschreibung;
        this.wortEinsBeschreibung = wortEinsBeschreibung;
        this.wortZweiBeschreibung = wortZweiBeschreibung;
        this.richtung = richtung;
        this.grossKleinschreibung = grossKleinschreibung;
    }

    /**
     * Constructs a Lernkartei without specifying the unique number.
     *
     * @param beschreibung The description of the learning card.
     * @param wortEinsBeschreibung The description of the first word.
     * @param wortZweiBeschreibung The description of the second word.
     * @param richtung The direction of learning (true for one direction, false for both).
     * @param grossKleinschreibung Whether case sensitivity is enabled.
     */
    public Lernkartei(String beschreibung, String wortEinsBeschreibung, String wortZweiBeschreibung,
            boolean richtung, boolean grossKleinschreibung) {
        this.beschreibung = beschreibung;
        this.wortEinsBeschreibung = wortEinsBeschreibung;
        this.wortZweiBeschreibung = wortZweiBeschreibung;
        this.richtung = richtung;
        this.grossKleinschreibung = grossKleinschreibung;
    }

    /**
     * Checks if this Lernkartei is equal to another object.
     * Two Lernkartei instances are considered equal if all their fields are equal.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lernkartei)) return false;
        Lernkartei k = (Lernkartei) o;
        return nummer == k.nummer &&
               richtung == k.richtung &&
               grossKleinschreibung == k.grossKleinschreibung &&
               ((beschreibung == null && k.beschreibung == null) || 
                (beschreibung != null && beschreibung.equals(k.beschreibung))) &&
               ((wortEinsBeschreibung == null && k.wortEinsBeschreibung == null) || 
                (wortEinsBeschreibung != null && wortEinsBeschreibung.equals(k.wortEinsBeschreibung))) &&
               ((wortZweiBeschreibung == null && k.wortZweiBeschreibung == null) || 
                (wortZweiBeschreibung != null && wortZweiBeschreibung.equals(k.wortZweiBeschreibung)));
    }

    /**
     * Returns the description of the learning card.
     *
     * @return The description string.
     */
    @Override
    public String toString() {
        return beschreibung;
    }

    /**
     * Validates that the descriptions for the learning card and both words are provided.
     * Populates the error hashtable with messages for any missing fields.
     */
    public void validate() {
        fehler = null;
        if (beschreibung == null || beschreibung.trim().isEmpty()) {
            fehler = new Hashtable<>();
            fehler.put("beschreibung", "Description must be provided.");
        }
        if (wortEinsBeschreibung == null || wortEinsBeschreibung.trim().isEmpty()) {
            if (fehler == null)
                fehler = new Hashtable<>();
            fehler.put("wortEinsBeschreibung", "First word description must be provided.");
        }
        if (wortZweiBeschreibung == null || wortZweiBeschreibung.trim().isEmpty()) {
            if (fehler == null)
                fehler = new Hashtable<>();
            fehler.put("wortZweiBeschreibung", "Second word description must be provided.");
        }
    }

    /**
     * Gets the unique number of the learning card.
     *
     * @return The number.
     */
    public int getNummer() {
        return nummer;
    }

    /**
     * Gets the description of the learning card.
     *
     * @return The description string.
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Sets the description of the learning card.
     * Trims the input string if it is not null.
     *
     * @param beschreibung The description to set.
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = (beschreibung != null) ? beschreibung.trim() : null;
    }

    /**
     * Gets the description of the first word.
     *
     * @return The first word description string.
     */
    public String getWortEinsBeschreibung() {
        return wortEinsBeschreibung;
    }

    /**
     * Sets the description of the first word.
     * Trims the input string if it is not null.
     *
     * @param wortEinsBeschreibung The first word description to set.
     */
    public void setWortEinsBeschreibung(String wortEinsBeschreibung) {
        this.wortEinsBeschreibung = (wortEinsBeschreibung != null) ? wortEinsBeschreibung.trim() : null;
    }

    /**
     * Gets the description of the second word.
     *
     * @return The second word description string.
     */
    public String getWortZweiBeschreibung() {
        return wortZweiBeschreibung;
    }

    /**
     * Sets the description of the second word.
     * Trims the input string if it is not null.
     *
     * @param wortZweiBeschreibung The second word description to set.
     */
    public void setWortZweiBeschreibung(String wortZweiBeschreibung) {
        this.wortZweiBeschreibung = (wortZweiBeschreibung != null) ? wortZweiBeschreibung.trim() : null;
    }

    /**
     * Gets the learning direction.
     *
     * @return true if one-directional, false if bidirectional.
     */
    public boolean getRichtung() {
        return richtung;
    }

    /**
     * Sets the learning direction.
     *
     * @param richtung true for one-directional, false for bidirectional.
     */
    public void setRichtung(boolean richtung) {
        this.richtung = richtung;
    }

    /**
     * Gets whether case sensitivity is enabled.
     *
     * @return true if case-sensitive, false otherwise.
     */
    public boolean getGrossKleinschreibung() {
        return grossKleinschreibung;
    }

    /**
     * Sets whether case sensitivity is enabled.
     *
     * @param grossKleinschreibung true to enable case sensitivity, false to disable.
     */
    public void setGrossKleinschreibung(boolean grossKleinschreibung) {
        this.grossKleinschreibung = grossKleinschreibung;
    }

    /**
     * Gets the hashtable of validation errors.
     *
     * @return A hashtable containing field names as keys and error messages as values.
     */
    public Hashtable<String, String> getFehler() {
        return fehler;
    }
}
