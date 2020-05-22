package windparkfx.presentationmodel;

import javafx.beans.property.*;
import javafx.scene.image.ImageView;

/**
 * @author Mario Wettstein
 */

public class HydroDataPM {

    //- Hydro Property
    private final IntegerProperty id                = new SimpleIntegerProperty();
    private final StringProperty name              = new SimpleStringProperty();
    private final StringProperty type              = new SimpleStringProperty();
    private final StringProperty site              = new SimpleStringProperty();
    private final StringProperty canton            = new SimpleStringProperty();
    private final DoubleProperty maxWaterVol       = new SimpleDoubleProperty();
    //private final DoubleProperty  maxMWattPower     = new SimpleDoubleProperty();
    private final StringProperty maxMWattPower     = new SimpleStringProperty();
    private final IntegerProperty firstStartDat     = new SimpleIntegerProperty();
    private final IntegerProperty lastStartDat      = new SimpleIntegerProperty();
    private final DoubleProperty latitude          = new SimpleDoubleProperty();
    private final DoubleProperty longitude         = new SimpleDoubleProperty();
    private final StringProperty status            = new SimpleStringProperty();
    private final StringProperty waterbodies       = new SimpleStringProperty();
    private final StringProperty imageUrl          = new SimpleStringProperty();


    //- Spezial Anzeige
    private final IntegerProperty anzKWGesamt     = new SimpleIntegerProperty();
    private final DoubleProperty totalKWWatt     = new SimpleDoubleProperty();

    private HydroDataImagePM image;
    private ImageView imageView;

    //- cuie_custom_cotrol
    private final BooleanProperty isOnValue       = new SimpleBooleanProperty();


    public HydroDataPM()
    {

    }

    public HydroDataPM(int new_Id)
    {
        setId(Integer.valueOf(new_Id));
    }

    public HydroDataPM(String[] line) {
        setId(Integer.valueOf(line[0]));
        setName(line[1]);
        setType(line[2]);
        setSite(line[3]);
        setCanton(line[4]);
        setMaxWaterVol(Double.valueOf(line[5]));
        setMaxMWattPower(line[6]);
        setFirstStartDat(Integer.valueOf(line[7]));
        setLastStartDat(Integer.valueOf(line[8]));
        setLatitude(Double.valueOf(line[9]));
        setLongitude(Double.valueOf(line[10]));
        setStatus(line[11]);
        setWaterbodies(line[12]);
        setImageUrl(line[13]);

    }

    public String Hydro_InfoAsLine(String delimiter) {
        return String.join(delimiter,
                Integer.toString(getId()),
                getName(),
                getType(),
                getSite(),
                getCanton(),
                Double.toString(getMaxWaterVol()),
                Double.toString(getMaxMWattPower()),
                Integer.toString(getFirstStartDat()),
                Integer.toString(getLastStartDat()),
                Double.toString(getLatitude()),
                Double.toString(getLongitude()),
                getStatus(),
                getWaterbodies(),
                getImageUrl()
        );
    }


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
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

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getSite() {
        return site.get();
    }

    public StringProperty siteProperty() {
        return site;
    }

    public void setSite(String site) {
        this.site.set(site);
    }

    public String getCanton() {
        return canton.get();
    }

    public StringProperty cantonProperty() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton.set(canton);
    }

    public double getMaxWaterVol() {
        return maxWaterVol.get();
    }

    public DoubleProperty maxWaterVolProperty() {
        return maxWaterVol;
    }

    public void setMaxWaterVol(double maxWaterVol) {
        this.maxWaterVol.set(maxWaterVol);
    }

    public Double getMaxMWattPower() {
        return Double.parseDouble(maxMWattPower.get());
    }

    public StringProperty maxMWattPowerProperty() {
        return maxMWattPower;
    }

    public void setMaxMWattPower(String maxMWattPower) {
        this.maxMWattPower.set(maxMWattPower);
    }

    public int getFirstStartDat() {
        return firstStartDat.get();
    }

    public IntegerProperty firstStartDatProperty() {
        return firstStartDat;
    }

    public void setFirstStartDat(int firstStartDat) {
        this.firstStartDat.set(firstStartDat);
    }

    public int getLastStartDat() {
        return lastStartDat.get();
    }

    public IntegerProperty lastStartDatProperty() {
        return lastStartDat;
    }

    public void setLastStartDat(int lastStartDat) {
        this.lastStartDat.set(lastStartDat);
    }

    public double getLatitude() {
        return latitude.get();
    }

    public DoubleProperty latitudeProperty() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude.set(latitude);
    }

    public double getLongitude() {
        return longitude.get();
    }

    public DoubleProperty longitudeProperty() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude.set(longitude);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getWaterbodies() {
        return waterbodies.get();
    }

    public StringProperty waterbodiesProperty() {
        return waterbodies;
    }

    public void setWaterbodies(String waterbodies) {
        this.waterbodies.set(waterbodies);
    }

    public String getImageUrl() {
        return imageUrl.get();
    }

    public StringProperty imageUrlProperty() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }


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

    public boolean isIsOnValue() {
        return isOnValue.get();
    }

    public BooleanProperty isOnValueProperty() {
        return isOnValue;
    }

    public void setIsOnValue(boolean isOnValue) {
        this.isOnValue.set(isOnValue);
    }

    //- Image Load
    public ImageView getImageView() {
        if (imageUrl.getValue() == null || imageUrl.getValue().equals("")) {
            image = new HydroDataImagePM(new String(getClass().getResource("/images/wind03.jpg").toString()));
        } else {
            //- Image Pfad gefunden --> Image mit Pfad erstellen
            image = new HydroDataImagePM(getImageUrl());
        }

        return new ImageView(image.getImage());
    }


}
