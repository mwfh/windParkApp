package windparkfx.view;

import windparkfx.presentationmodel.RootPM;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.converter.NumberStringConverter;

/**
 * @author Mario Wettstein
 */

public class ContentView extends GridPane implements ViewMixin{
    private final RootPM rootPM;

    //- Lable
    private Label titleKwName;
    private Label titleOrtName;
    private Label titleOrtKanton;
    private Label titleMWatt;
    private Label titleYear;

    //- Label Matrix
    private Label labName;
    private Label labOrt;
    private Label labWassermenge;
    private Label labInBetriebSeit;
    private Label labBreitengrad;
    private Label labStatus;
    private Label labGenGew;
    private Label labImgUrl;

    private Label labTyp;
    private Label labKanton;
    private Label labLeistung;
    private Label labLetztSeit;
    private Label labLaengegrad;

    //- Input Matrix
    private TextField inpName;
    private TextField inpOrt;
    private TextField inpWassermenge;
    private TextField inpInBetriebSeit;
    private TextField inpBreitengrad;
    private TextField inpStatus;
    private TextField inpGenGew;
    private TextField inpImgUrl;
    private TextField inpTyp;
    private TextField inpKanton;
    private TextField inpLeistung;
    private TextField inpLetztSeit;
    private TextField inpLaengegrad;


    //- Image
    private ImageView imageView;


    public ContentView(RootPM model) {
        this.rootPM = model;

        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("style.css");
    }

    @Override
    public void initializeControls() {

        //imgView = new ImageView();
        setStyle("-fx-background-color:white");

        titleKwName     = new Label();
        titleKwName.setId("labeltitletext");
        titleOrtName    = new Label();
        titleOrtKanton  = new Label();
        titleMWatt      = new Label();
        titleYear       = new Label();

        //- Label Matrix
        labName             = new Label("Name*");
        labOrt              = new Label("Standort");
        labWassermenge      = new Label("Massermenge");
        labInBetriebSeit    = new Label("Inbetriebnahme");
        labBreitengrad      = new Label("Breitengrad");
        labStatus           = new Label("Status");
        labGenGew           = new Label("Genutzte Gewässer");
        labImgUrl           = new Label("Image URL");

        labTyp              = new Label("Typ");
        labKanton           = new Label("Kanton");
        labLeistung         = new Label("Leistung(MW)");
        labLetztSeit        = new Label("Letzte Inbetriebnahme");
        labLaengegrad       = new Label("Längengrad");

        //- Input Matrix
        inpName             = new TextField();
        inpOrt              = new TextField();
        inpWassermenge      = new TextField();
        inpInBetriebSeit    = new TextField();
        inpBreitengrad      = new TextField();
        inpStatus           = new TextField();
        inpGenGew           = new TextField();
        inpImgUrl           = new TextField();

        inpTyp              = new TextField();
        inpKanton           = new TextField();
        inpLeistung         = new TextField();
        inpLetztSeit        = new TextField();
        inpLaengegrad       = new TextField();

        imageView = new ImageView();


        //setGridLinesVisible(true); //- Grind einblenden
    }

    @Override
    public void layoutControls() {
        ColumnConstraints Space                 = new ColumnConstraints(10,10, Double.MAX_VALUE);
        ColumnConstraints Column1Label          = new ColumnConstraints(120,120, Double.MAX_VALUE);
        ColumnConstraints Column2Label          = new ColumnConstraints(100,100, Double.MAX_VALUE);
        ColumnConstraints Column3SpezInpSpace   = new ColumnConstraints(100,100, Double.MAX_VALUE);
        ColumnConstraints Column4SpezInpSpace   = new ColumnConstraints(40,40, Double.MAX_VALUE);
        ColumnConstraints Column5Input          = new ColumnConstraints(100,100, Double.MAX_VALUE);
        ColumnConstraints Column6Label          = new ColumnConstraints(100,100, Double.MAX_VALUE);
        ColumnConstraints Column7SpezInpSpace   = new ColumnConstraints(100,100, Double.MAX_VALUE);
        ColumnConstraints Column8Input          = new ColumnConstraints(100,100, Double.MAX_VALUE);

        setMinSize(830,500);
        setMaxSize(1000,1200);
        getColumnConstraints().addAll(Space,Column1Label, Column2Label, Column3SpezInpSpace, Column4SpezInpSpace,
                Column5Input, Column6Label, Column7SpezInpSpace, Column8Input, Space);


        RowConstraints Row1HLabel           = new RowConstraints(50,50, Double.MAX_VALUE);
        RowConstraints Row2HLabel           = new RowConstraints(50,50, Double.MAX_VALUE);
        RowConstraints Row3HLabel           = new RowConstraints(50,50, Double.MAX_VALUE);
        RowConstraints Row4HLabel           = new RowConstraints(50,50, Double.MAX_VALUE);
        RowConstraints Row5HSpace           = new RowConstraints(80,80, Double.MAX_VALUE);

        RowConstraints Row6InputMatrix      = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints Row7InputMatrix      = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints Row8InputMatrix      = new RowConstraints(50,50, Double.MAX_VALUE);
        RowConstraints Row9InputMatrix      = new RowConstraints(50,50, Double.MAX_VALUE);
        RowConstraints Row10InputMatrix     = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints Row11nputMatrix      = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints Row12nputMatrix      = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints Row13InputMatrix     = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints Row14InputMatrix     = new RowConstraints(40,40, Double.MAX_VALUE);

        getRowConstraints().addAll(Row1HLabel, Row2HLabel, Row3HLabel, Row4HLabel, Row5HSpace,
                Row6InputMatrix, Row7InputMatrix, Row8InputMatrix, Row9InputMatrix,
                Row10InputMatrix,Row11nputMatrix, Row12nputMatrix, Row13InputMatrix, Row14InputMatrix);

        //- Head
        add(titleKwName, 1, 0, 4, 1);

        //add(labOrt, 1,1,1,1);
        add(titleOrtName, 1, 1, 4, 1);

        add(titleOrtKanton, 1, 2, 1, 1);
        add(titleMWatt, 1, 3, 1,1);
        add(titleYear, 1, 4, 1,1);

        //- Image
        add(imageView, 6, 1, 2,4);

        //- Content
        add(labName, 1, 6, 1,1);
        add(labOrt, 1, 7, 1,1);
        add(labWassermenge, 1, 8, 1,1);
        add(labInBetriebSeit, 1, 9, 1,1);
        add(labBreitengrad, 1, 10, 1,1);
        add(labStatus, 1, 11, 1,1);
        add(labGenGew, 1, 12, 1,1);
        add(labImgUrl, 1, 13, 1,1);

        add(labTyp, 5, 6, 2,1);
        add(labKanton, 5, 7, 2,1);
        add(labLeistung, 5, 8, 2,1);
        add(labLetztSeit, 5, 9, 2,1);
        add(labLaengegrad, 5, 10, 2,1);

        add(inpName, 2, 6, 2,1);
        add(inpOrt, 2, 7, 2,1);

        add(inpWassermenge, 2, 8, 1,1);

        add(inpInBetriebSeit, 2, 9, 1,1);


        add(inpBreitengrad, 2, 10, 1,1);
        add(inpStatus, 2, 11, 2,1);
        add(inpGenGew, 2, 12, 2,1);
        add(inpImgUrl, 2, 13, 4,1);

        add(inpTyp, 7, 6, 2,1);
        add(inpKanton, 7, 7, 2,1);
        add(inpLeistung, 7, 8, 2,1);

        add(inpLetztSeit, 7, 9, 2,1);
        add(inpLaengegrad, 7, 10, 2,1);


    }

    @Override
    public void setupBindings() {

        titleKwName.textProperty()              .bind(rootPM.getHydroProxy().nameProperty());
        titleOrtName.textProperty()             .bind(rootPM.getHydroProxy().siteProperty());
        titleOrtKanton.textProperty()           .bind(rootPM.getHydroProxy().cantonProperty());
        titleMWatt.textProperty()               .bind(rootPM.getHydroProxy().maxMWattPowerProperty());
        titleYear.textProperty()                .bind(rootPM.getHydroProxy().firstStartDatProperty().asString());

        //- Input Matrix
        inpName.textProperty()                  .bindBidirectional(rootPM.getHydroProxy().nameProperty());
        inpOrt.textProperty()                   .bindBidirectional(rootPM.getHydroProxy().siteProperty());

        inpWassermenge.textProperty()         .bindBidirectional(rootPM.getHydroProxy().maxWaterVolProperty(), new NumberStringConverter());

        inpInBetriebSeit.textProperty()         .bindBidirectional(rootPM.getHydroProxy().firstStartDatProperty(), new NumberStringConverter());

        inpBreitengrad.textProperty()           .bindBidirectional(rootPM.getHydroProxy().latitudeProperty(), new NumberStringConverter());
        inpStatus.textProperty()                .bindBidirectional(rootPM.getHydroProxy().statusProperty());
        inpGenGew.textProperty()                .bindBidirectional(rootPM.getHydroProxy().waterbodiesProperty());
        inpImgUrl.textProperty()                .bindBidirectional(rootPM.getHydroProxy().imageUrlProperty());

        inpTyp.textProperty()                   .bindBidirectional(rootPM.getHydroProxy().typeProperty());
        inpKanton.textProperty()                .bindBidirectional(rootPM.getHydroProxy().cantonProperty());


        inpLeistung.textProperty()              .bindBidirectional(rootPM.getHydroProxy().maxMWattPowerProperty());
        inpLetztSeit.textProperty()           .bindBidirectional(rootPM.getHydroProxy().lastStartDatProperty(), new NumberStringConverter());

        inpLaengegrad.textProperty()            .bindBidirectional(rootPM.getHydroProxy().longitudeProperty(), new NumberStringConverter());

        //- Image Input
        imageView.setFitHeight(280);
        imageView.setFitWidth(320);
        imageView.imageProperty()               .bind(rootPM.getHydroProxy().getImageView().imageProperty());

    }

    @Override
    public void setupEventHandlers() {
        inpKanton.textProperty()                .addListener((observable, oldValue, newValue) -> {
            rootPM.getHydroProxy()              .setCanton(newValue);
            rootPM.refreshHydrosPerCantonList();
        });

        inpLeistung.textProperty()              .addListener((observable, oldValue, newValue) -> {
            rootPM.getHydroProxy()              .setMaxMWattPower(newValue);
            rootPM.refreshHydrosPerCantonList();
        });

        inpImgUrl.textProperty()                .addListener((observable, oldValue, newValue) -> {
            imageView.imageProperty()           .unbind();
            imageView.imageProperty()           .bind(rootPM.getHydroProxy().getImageView().imageProperty());
        });

    }



}
