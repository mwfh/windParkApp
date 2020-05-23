package windparkfx.presentationmodel;

import javafx.beans.property.*;
import javafx.scene.image.ImageView;

/**
 * @author Mario Wettstein
 */

public class WindDataPM {

    // ID: 1001;
    private final IntegerProperty id                = new SimpleIntegerProperty();
    // LOCATION_NAME: Mont Crosin;
    private final StringProperty locationName              = new SimpleStringProperty();
    // STATUS: in Betrieb;
    private final StringProperty status            = new SimpleStringProperty();
    // CONSTRUCTION_START: 1996;
    private final IntegerProperty constructStart = new SimpleIntegerProperty();
    // COMPLETION: 2016;
    private final IntegerProperty constructFinish = new SimpleIntegerProperty();
    // INSTALLED_POWER_KW: 37200;
    private final IntegerProperty kwIstall         = new SimpleIntegerProperty();
    // PRODUCTION_2015_MWH: 56951;
    private final DoubleProperty mw15 = new SimpleDoubleProperty();
    // PRODUCTION_2016_MWH: 57171;
    private final DoubleProperty mw16 = new SimpleDoubleProperty();
    // PRODUCTION_2017_MWH: 74041;
    private final DoubleProperty mw17 = new SimpleDoubleProperty();
    // PRODUCTION_2018_MWH: 66967;
    private final DoubleProperty mw18 = new SimpleDoubleProperty();
    // COUNT: 16;
    private  final IntegerProperty count           = new SimpleIntegerProperty();
    // TYPE: Vestas V90-2MW (12x), Vestas V112-3.3MW (4x);
    private final StringProperty type              = new SimpleStringProperty();
    // COMMUNES: Saint-Imier, Villeret, Cormoret, Courtelary;
    private final StringProperty communes          = new SimpleStringProperty();
    // CANTON: BE;
    private final StringProperty canton            = new SimpleStringProperty();
    // LATITUDE: 47.175833;
    private final DoubleProperty latitude          = new SimpleDoubleProperty();
    // LONGITUDE: 7.018889;
    private final DoubleProperty longitude         = new SimpleDoubleProperty();
    // IMAGE_URL: https://www.suisse-eole.ch/media/ul/medias/images/
    private final StringProperty imageUrl          = new SimpleStringProperty();


    //- Spezial Anzeige
    private final IntegerProperty anzKWGesamt     = new SimpleIntegerProperty();
    private final DoubleProperty totalMegaWatt = new SimpleDoubleProperty();

    private WindDataImagePM image;
    private ImageView imageView;

    //- cuie_custom_cotrol
    private final BooleanProperty isOnValue       = new SimpleBooleanProperty();



    // Construtctor
    public WindDataPM()
    {

    }

    public WindDataPM(int new_Id)
    {
        setId(Integer.valueOf(new_Id));
    }

    public WindDataPM(String[] line) {
        // ID: 1001;
        setId(line[0].equals("") ? 0 : Integer.valueOf(line[0]));
        // LOCATION_NAME: Mont Crosin;
        setLocationName(line[1]);
        // STATUS: in Betrieb;
        setStatus(line[2]);
        // CONSTRUCTION_START: 1996;
        setConstructStart(line[3].equals("") ? 0 : Integer.valueOf(line[3]));
        // COMPLETION: 2016;
        setConstructFinish(line[4].equals("") ? 0 : Integer.valueOf(line[4]));
        // INSTALLED_POWER_KW: 37200;
        setKwIstall(line[5].equals("") ? 0 : Integer.valueOf(line[5]));
        // PRODUCTION_2015_MWH: 56951;
        setMw15(line[6].equals("") ? 0 : Double.valueOf(line[6]));
        // PRODUCTION_2016_MWH: 57171;
        setMw16(line[7].equals("") ? 0 : Double.valueOf(line[7]));
        // PRODUCTION_2017_MWH: 74041;
        setMw17(line[8].equals("") ? 0 : Double.valueOf(line[8]));
        // PRODUCTION_2018_MWH: 66967;
        setMw18(line[9].equals("") ? 0 : Double.valueOf(line[9]));
        // COUNT: 16;
        setCount(line[10].equals("") ? 0 : Integer.valueOf(line[10]));
        // TYPE: Vestas V90-2MW (12x), Vestas V112-3.3MW (4x);
        setType(line[11]);
        // COMMUNES: Saint-Imier, Villeret, Cormoret, Courtelary;
        setCommunes(line[12]);
        // CANTON: BE;
        setCanton(line[13]);
        // LATITUDE: 47.175833;
        setLatitude(line[14].equals("") ? 0 : Double.valueOf(line[14]));
        // LONGITUDE: 7.018889;
        setLongitude(line[15].equals("") ? 0 : Double.valueOf(line[15]));
        // IMAGE_URL: https://www.suisse-eole.ch/media/ul/medias/images/
        setImageUrl(line[16]);

        //Total MWatt
        setTotalMegaWatt(getMw15() + getMw16() + getMw17() + getMw18());
    }

    public String windInfoAsLine(String delimiter) {
        return String.join(delimiter,
                Integer.toString(getId()),
                getLocationName(),
                getStatus(),
                Integer.toString(getConstructStart()),
                Integer.toString(getConstructFinish()),
                Integer.toString(getKwIstall()),
                Double.toString(getMw15()),
                Double.toString(getMw16()),
                Double.toString(getMw17()),
                Double.toString(getMw18()),
                Integer.toString(getCount()),
                getType(),
                getCommunes(),
                getCanton(),
                Double.toString(getLatitude()),
                Double.toString(getLongitude()),
                getImageUrl()
                //Double.toString(getTotalMegaWatt())
        );
    }



    //- Image Load
    public ImageView getImageView() {
        if (imageUrl.getValue() == null || imageUrl.getValue().equals("")) {
            image = new WindDataImagePM(new String(getClass().getResource("/images/wind03.jpg").toString()));
        } else {
            //- Image Pfad gefunden --> Image mit Pfad erstellen
            image = new WindDataImagePM(getImageUrl());
        }

        return new ImageView(image.getImage());
    }


    // Getter and Setter - Default Propertys
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getLocationName() {
        return locationName.get();
    }

    public StringProperty locationNameProperty() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName.set(locationName);
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

    public int getConstructStart() {
        return constructStart.get();
    }

    public IntegerProperty constructStartProperty() {
        return constructStart;
    }

    public void setConstructStart(int constructStart) {
        this.constructStart.set(constructStart);
    }

    public int getConstructFinish() {
        return constructFinish.get();
    }

    public IntegerProperty constructFinishProperty() {
        return constructFinish;
    }

    public void setConstructFinish(int constructFinish) {
        this.constructFinish.set(constructFinish);
    }

    public int getKwIstall() {
        return kwIstall.get();
    }

    public IntegerProperty kwIstallProperty() {
        return kwIstall;
    }

    public void setKwIstall(int kwIstall) {
        this.kwIstall.set(kwIstall);
    }

    public double getMw15() {
        return mw15.get();
    }

    public DoubleProperty mw15Property() {
        return mw15;
    }

    public void setMw15(double mw15) {
        this.mw15.set(mw15);
    }

    public double getMw16() {
        return mw16.get();
    }

    public DoubleProperty mw16Property() {
        return mw16;
    }

    public void setMw16(double mw16) {
        this.mw16.set(mw16);
    }

    public double getMw17() {
        return mw17.get();
    }

    public DoubleProperty mw17Property() {
        return mw17;
    }

    public void setMw17(double mw17) {
        this.mw17.set(mw17);
    }

    public double getMw18() {
        return mw18.get();
    }

    public DoubleProperty mw18Property() {
        return mw18;
    }

    public void setMw18(double mw18) {
        this.mw18.set(mw18);
    }

    public int getCount() {
        return count.get();
    }

    public IntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
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

    public String getCommunes() {
        return communes.get();
    }

    public StringProperty communesProperty() {
        return communes;
    }

    public void setCommunes(String communes) {
        this.communes.set(communes);
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

    public String getImageUrl() {
        return imageUrl.get();
    }

    public StringProperty imageUrlProperty() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }

    // Special Getter and Setter


    public int getAnzKWGesamt() {
        return anzKWGesamt.get();
    }

    public IntegerProperty anzKWGesamtProperty() {
        return anzKWGesamt;
    }

    public void setAnzKWGesamt(int anzKWGesamt) {
        this.anzKWGesamt.set(anzKWGesamt);
    }

    public double getTotalMegaWatt() {
        return totalMegaWatt.get();
    }

    public DoubleProperty totalMegaWattProperty() {
        return totalMegaWatt;
    }

    public void setTotalMegaWatt(double totalMegaWatt) {
        this.totalMegaWatt.set(totalMegaWatt);
    }

    public WindDataImagePM getImage() {
        return image;
    }

    public void setImage(WindDataImagePM image) {
        this.image = image;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
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
}
