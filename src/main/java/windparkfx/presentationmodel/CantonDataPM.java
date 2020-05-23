package windparkfx.presentationmodel;

import javafx.beans.property.*;


/**
 * @author Mario Wettstein
 */

public class CantonDataPM {

    private final StringProperty name            = new SimpleStringProperty();
    private final StringProperty kurzForm        = new SimpleStringProperty();
    private final IntegerProperty kantNr          = new SimpleIntegerProperty();
    private final DoubleProperty stdRat          = new SimpleDoubleProperty();
    private final IntegerProperty kantBeitritt    = new SimpleIntegerProperty();
    private final StringProperty kantHauptort    = new SimpleStringProperty();
    private final StringProperty anzEinwohner    = new SimpleStringProperty();
    private final StringProperty anzAuslaender   = new SimpleStringProperty();
    private final StringProperty kantFlaeche     = new SimpleStringProperty();
    private final IntegerProperty einwDichte      = new SimpleIntegerProperty();
    private final IntegerProperty anzGemeinden    = new SimpleIntegerProperty();
    private final StringProperty AmtSprache      = new SimpleStringProperty();

    //- Spezial Anzeige
    private final IntegerProperty anzKWGesamt   = new SimpleIntegerProperty();
    private final DoubleProperty totalKWWatt   = new SimpleDoubleProperty();

    public CantonDataPM(String[] line, int i, double v)
    {
        setName(line[0]);
        setKurzForm(line[1]);
        setKantNr(Integer.valueOf(line[2]));
        setStdRat(Double.valueOf(line[3]));
        setKantBeitritt(Integer.valueOf(line[4]));
        setKantHauptort(line[5]);
        setAnzEinwohner(line[6]);
        setAnzAuslaender(line[7]);
        setKantFlaeche(line[8]);
        setEinwDichte(Integer.valueOf(line[9]));
        setAnzGemeinden(Integer.valueOf(line[10]));
        setAmtSprache(line[11]);

        setAnzKWGesamt(i);
        setTotalKWWatt(v);

    }

    public CantonDataPM()
    {

    }

    public CantonDataPM(String[] line) {
        setName(line[0]);
        setKurzForm(line[1]);
        setKantNr(Integer.valueOf(line[2]));
        setStdRat(Double.valueOf(line[3]));
        setKantBeitritt(Integer.valueOf(line[4]));
        setKantHauptort(line[5]);
        setAnzEinwohner(line[6]);
        setAnzAuslaender(line[7]);
        setKantFlaeche(line[8]);
        setEinwDichte(Integer.valueOf(line[9]));
        setAnzGemeinden(Integer.valueOf(line[10]));
        setAmtSprache(line[11]);

    }

    public String Canton_InfoAsLine(String delimiter) {
        return String.join(delimiter,
                getName(),
                getKurzForm(),
                Integer.toString(getKantNr()),
                Double.toString(getStdRat()),
                Integer.toString(getKantBeitritt()),
                getKantHauptort(),
                getAnzEinwohner(),
                getAnzAuslaender(),
                getKantFlaeche(),
                Integer.toString(getEinwDichte()),
                Integer.toString(getAnzGemeinden()),
                getAmtSprache()
        );
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getKurzForm() {
        return kurzForm.get();
    }

    public StringProperty kurzFormProperty() {
        return kurzForm;
    }

    public void setKurzForm(String kurzForm) {
        this.kurzForm.set(kurzForm);
    }

    public int getKantNr() {
        return kantNr.get();
    }

    public IntegerProperty kantNrProperty() {
        return kantNr;
    }

    public void setKantNr(int kantNr) {
        this.kantNr.set(kantNr);
    }

    public double getStdRat() {
        return stdRat.get();
    }

    public DoubleProperty stdRatProperty() {
        return stdRat;
    }

    public void setStdRat(double stdRat) {
        this.stdRat.set(stdRat);
    }

    public int getKantBeitritt() {
        return kantBeitritt.get();
    }

    public IntegerProperty kantBeitrittProperty() {
        return kantBeitritt;
    }

    public void setKantBeitritt(int kantBeitritt) {
        this.kantBeitritt.set(kantBeitritt);
    }

    public String getKantHauptort() {
        return kantHauptort.get();
    }

    public StringProperty kantHauptortProperty() {
        return kantHauptort;
    }

    public void setKantHauptort(String kantHauptort) {
        this.kantHauptort.set(kantHauptort);
    }

    public String getAnzEinwohner() {
        return anzEinwohner.get();
    }

    public StringProperty anzEinwohnerProperty() {
        return anzEinwohner;
    }

    public void setAnzEinwohner(String anzEinwohner) {
        this.anzEinwohner.set(anzEinwohner);
    }

    public String getAnzAuslaender() {
        return anzAuslaender.get();
    }

    public StringProperty anzAuslaenderProperty() {
        return anzAuslaender;
    }

    public void setAnzAuslaender(String anzAuslaender) {
        this.anzAuslaender.set(anzAuslaender);
    }

    public String getKantFlaeche() {
        return kantFlaeche.get();
    }

    public StringProperty kantFlaecheProperty() {
        return kantFlaeche;
    }

    public void setKantFlaeche(String kantFlaeche) {
        this.kantFlaeche.set(kantFlaeche);
    }

    public int getEinwDichte() {
        return einwDichte.get();
    }

    public IntegerProperty einwDichteProperty() {
        return einwDichte;
    }

    public void setEinwDichte(int einwDichte) {
        this.einwDichte.set(einwDichte);
    }

    public int getAnzGemeinden() {
        return anzGemeinden.get();
    }

    public IntegerProperty anzGemeindenProperty() {
        return anzGemeinden;
    }

    public void setAnzGemeinden(int anzGemeinden) {
        this.anzGemeinden.set(anzGemeinden);
    }

    public String getAmtSprache() {
        return AmtSprache.get();
    }

    public StringProperty amtSpracheProperty() {
        return AmtSprache;
    }

    public void setAmtSprache(String amtSprache) {
        this.AmtSprache.set(amtSprache);
    }

    //- Spezial Werte


    public int getAnzKWGesamt() {
        return anzKWGesamt.get();
    }

    public IntegerProperty anzKWGesamtProperty() {
        return anzKWGesamt;
    }

    public void setAnzKWGesamt(int anzKWGesamt) {
        this.anzKWGesamt.set(anzKWGesamt);
    }

    public double getTotalKWWatt() {
        return totalKWWatt.get();
    }

    public DoubleProperty totalKWWattProperty() {
        return totalKWWatt;
    }

    public void setTotalKWWatt(double totalKWWatt) {
        this.totalKWWatt.set(totalKWWatt);
    }
}
