package net.tfobz.vokabeltrainer.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

/**
 * Represents a subject (Fach) that can contain multiple vocabulary cards.
 * 
 * <p>
 * Each subject tracks the date when it was last studied. The {@code erinnerungsIntervall}
 * determines the number of days that must pass since the last study session before a
 * reminder is triggered. For example, an interval of 1 means a reminder is generated if
 * the subject was last studied yesterday. If the interval is set to 0, no reminders
 * will be generated.
 * </p>
 * 
 * <p>
 * If no description is provided when adding the subject to the database, it will be
 * automatically assigned the description "Fach &lt;Number&gt;", where &lt;Number&gt; is
 * the sequential number of the subject in the vocabulary trainer.
 * </p>
 * 
 * <p>
 * This class provides methods to manage subject properties, validate data integrity,
 * and determine if a reminder is due based on the specified interval.
 * </p>
 */
public class Fach {
    protected int nummer = -1;
    protected String beschreibung = null;
    protected int erinnerungsIntervall = 1;
    protected Date gelerntAm = null;

    protected Hashtable<String, String> fehler = null;

    /**
     * Default constructor for Fach.
     */
    public Fach() {}

    /**
     * Constructs a Fach with the specified number and description.
     * 
     * @param nummer        the unique number identifying the subject
     * @param beschreibung  the description of the subject
     */
    public Fach(int nummer, String beschreibung) {
        this.nummer = nummer;
        this.beschreibung = beschreibung;
    }

    /**
     * Constructs a Fach with the specified number, description, reminder interval, and last learned date.
     * 
     * @param nummer                 the unique number identifying the subject
     * @param beschreibung           the description of the subject
     * @param erinnerungsIntervall   the number of days before a reminder is triggered
     * @param gelerntAm              the date when the subject was last learned
     */
    public Fach(int nummer, String beschreibung, int erinnerungsIntervall, Date gelerntAm) {
        this.nummer = nummer;
        this.setBeschreibung(beschreibung);
        this.setErinnerungsIntervall(erinnerungsIntervall);
        this.setGelerntAm(gelerntAm);
    }

    /**
     * Determines whether this Fach is equal to another object.
     * 
     * Two Fach instances are considered equal if they have the same number,
     * description, reminder interval, and last learned date.
     * 
     * @param o the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fach)) return false;

        Fach f = (Fach) o;

        if (f.nummer != nummer) return false;
        if (f.erinnerungsIntervall != erinnerungsIntervall) return false;
        if (beschreibung != null ? !beschreibung.equals(f.beschreibung) : f.beschreibung != null)
            return false;
        return gelerntAm != null ? VokabeltrainerDB.convertToString(gelerntAm).equals(VokabeltrainerDB.convertToString(f.gelerntAm)) : f.gelerntAm == null;
    }

    /**
     * Returns a string representation of the Fach.
     * 
     * The format includes the description, the last learned date in European format,
     * and indicates if a reminder is due.
     * 
     * @return a string representation of the Fach
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(beschreibung)
           .append(", last learned on ")
           .append(getGelerntAmEuropaeischString());
        if (getErinnerungFaellig()) {
            ret.append(" REMINDER");
        }
        return ret.toString();
    }

    /**
     * Validates the Fach instance.
     * 
     * Ensures that for an existing Fach (nummer != -1), the description is not null or empty.
     * If validation fails, error messages are populated accordingly.
     */
    public void validiere() {
        fehler = null;
        if (nummer != -1 && (beschreibung == null || beschreibung.trim().isEmpty())) {
            fehler = new Hashtable<>();
            fehler.put("beschreibung", "Description must be provided.");
        }
    }

    /**
     * Retrieves the validation errors.
     * 
     * @return a hashtable containing field-specific error messages, or {@code null} if there are no errors
     */
    public Hashtable<String, String> getFehler() {
        return fehler;
    }

    /**
     * Gets the unique number of the Fach.
     * 
     * @return the number identifying the Fach
     */
    public int getNummer() {
        return nummer;
    }

    /**
     * Gets the description of the Fach.
     * 
     * @return the description of the Fach
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Sets the description of the Fach.
     * 
     * Trims leading and trailing whitespace from the description.
     * 
     * @param beschreibung the new description for the Fach
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = (beschreibung != null) ? beschreibung.trim() : null;
    }

    /**
     * Gets the reminder interval in days.
     * 
     * @return the number of days before a reminder is triggered
     */
    public int getErinnerungsIntervall() {
        return erinnerungsIntervall;
    }

    /**
     * Sets the reminder interval.
     * 
     * The interval must be non-negative. An interval of 0 disables reminders.
     * 
     * @param erinnerungsIntervall the new reminder interval in days
     */
    public void setErinnerungsIntervall(int erinnerungsIntervall) {
        if (erinnerungsIntervall >= 0) {
            this.erinnerungsIntervall = erinnerungsIntervall;
        }
    }

    /**
     * Gets the date when the Fach was last learned.
     * 
     * @return the date of the last study session
     */
    public Date getGelerntAm() {
        return gelerntAm;
    }

    /**
     * Gets the last learned date as a string in the format "yyyy-MM-dd".
     * 
     * @return the formatted date string, or an empty string if the date is {@code null}
     */
    public String getGelerntAmString() {
        if (gelerntAm == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(gelerntAm);
    }

    /**
     * Gets the last learned date as a string in European format "dd.MM.yyyy".
     * 
     * @return the formatted date string, or "NULL" if the date is {@code null}
     */
    public String getGelerntAmEuropaeischString() {
        if (gelerntAm == null) {
            return "NULL";
        }
        return new SimpleDateFormat("dd.MM.yyyy").format(gelerntAm);
    }

    /**
     * Sets the date when the Fach was last learned.
     * 
     * @param gelerntAm the new date of the last study session
     */
    public void setGelerntAm(Date gelerntAm) {
        this.gelerntAm = gelerntAm;
    }

    /**
     * Determines whether a reminder is due based on the reminder interval and last learned date.
     * 
     * @return {@code true} if a reminder should be triggered; {@code false} otherwise
     */
    public boolean getErinnerungFaellig() {
        if (erinnerungsIntervall == 0) {
            return false;
        }

        Calendar calHeute = Calendar.getInstance();
        calHeute.setTime(new Date());
        calHeute.add(Calendar.DAY_OF_YEAR, -erinnerungsIntervall);
        // Reset time components to midnight for accurate comparison
        calHeute.set(Calendar.HOUR_OF_DAY, 0);
        calHeute.set(Calendar.MINUTE, 0);
        calHeute.set(Calendar.SECOND, 0);
        calHeute.set(Calendar.MILLISECOND, 0);

        if (gelerntAm == null) {
            return false;
        }

        Calendar calGelerntAm = Calendar.getInstance();
        calGelerntAm.setTime(gelerntAm);
        // Reset time components to midnight for accurate comparison
        calGelerntAm.set(Calendar.HOUR_OF_DAY, 0);
        calGelerntAm.set(Calendar.MINUTE, 0);
        calGelerntAm.set(Calendar.SECOND, 0);
        calGelerntAm.set(Calendar.MILLISECOND, 0);

        return calHeute.getTime().getTime() - calGelerntAm.getTime().getTime() >= 0;
    }
}
