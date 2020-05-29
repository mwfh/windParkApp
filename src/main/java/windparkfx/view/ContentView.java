package windparkfx.view;

import javafx.util.converter.NumberStringConverter;
import windparkfx.presentationmodel.RootPM;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import windparkfx.view.gummibaerenDashboard.GummibaerenDashboard;

/**
 * @author Mario Wettstein
 */

public class ContentView extends GridPane implements ViewMixin{
    private final RootPM rootPM;


    //- Label Matrix Left Column
    private Label labelLocation;
    private Label labelCommun;
    private Label labelInstalKW;
    private Label labelLatitude;
    private Label labelConstructStart;
    private Label labelCount;
    private Label labelType;
    private Label label2015;
    private Label label2017;
    private Label labelImageURL;

    //- Label Matrix Right Column
    private Label labelCanton;
    private Label labelStatus;
    private Label labelLongitude;
    private Label labelConstructFinished;
    private Label label2016;
    private Label label2018;



    //- Input Matrix Left Column
    private TextField inputLocation;
    private TextField inputCommun;
    private TextField inputInstallKW;
    private TextField inputLatitude;
    private TextField inputConstructStart;
    private TextField inputCount;
    private TextField inputType;
    private TextField input2015;
    private TextField input2017;
    private TextField inputImageURL;

    //- Input Matrix Right Column
    private TextField inputCanton;
    private TextField inputStatus;
    private TextField inputLongitude;
    private TextField inputConstructFinished;
    private TextField input2016;
    private TextField input2018;

    private GummibaerenDashboard gummibaerenDashboard;

    public ContentView(RootPM model) {
        this.rootPM = model;

        init();
    }

    @Override
    public void initializeSelf() {
//        addStylesheetFiles("style.css");
        addStylesheetFiles(rootPM.getStyleChoose());
        getStyleClass().add("content-view");
    }

    @Override
    public void initializeControls() {

        //- Label Matrix left Column
        labelLocation           = new Label("Standort");
        labelLocation.getStyleClass().add("label-content-text");

        labelCommun             = new Label("Gemeinde");
        labelCommun.getStyleClass().add("label-content-text");

        labelInstalKW           = new Label("Leistung (kW)");
        labelInstalKW.getStyleClass().add("label-content-text");

        labelLatitude           = new Label("Breitengrad");
        labelLatitude.getStyleClass().add("label-content-text");

        labelConstructStart     = new Label("Baubeginn");
        labelConstructStart.getStyleClass().add("label-content-text");

        labelCount              = new Label("Windräder");
        labelCount.getStyleClass().add("label-content-text");

        labelType               = new Label("Anlagetyp");
        labelType.getStyleClass().add("label-content-text");

        label2015               = new Label("Produktion 2015 (MWh)");
        label2015.getStyleClass().add("label-content-text");

        label2017               = new Label("Produktion 2017 (MWh)");
        label2017.getStyleClass().add("label-content-text");

        labelImageURL           = new Label("Bild");
        labelImageURL.getStyleClass().add("label-content-text");


        //- Label Matrix right Column
        labelCanton             = new Label("Kanton");
        labelCanton.getStyleClass().add("label-content-text");

        labelStatus             = new Label("Status");
        labelStatus.getStyleClass().add("label-content-text");

        labelLongitude          = new Label("Längengrad");
        labelLongitude.getStyleClass().add("label-content-text");

        labelConstructFinished  = new Label("Fertigstellung");
        labelConstructFinished.getStyleClass().add("label-content-text");

        label2016               = new Label("Produktion 2016 (MWh)");
        label2016.getStyleClass().add("label-content-text");

        label2018               = new Label("Produktion 2019 (MWh)");
        label2018.getStyleClass().add("label-content-text");


        //- Input Matrix left Column
        inputLocation           = new TextField();
        inputCommun             = new TextField();
        inputInstallKW          = new TextField();
        inputLatitude           = new TextField();
        inputConstructStart     = new TextField();
        inputCount              = new TextField();
        inputType               = new TextField();
        input2015               = new TextField();
        input2017               = new TextField();
        inputImageURL           = new TextField();

        //- Input Matrix right Column
        inputCanton             = new TextField();
        inputStatus             = new TextField();
        inputLongitude          = new TextField();
        inputConstructFinished  = new TextField();
        input2016               = new TextField();
        input2018               = new TextField();

        //- gummibaerenDashboard
        gummibaerenDashboard    = new GummibaerenDashboard(rootPM);

        //setGridLinesVisible(true); //- Grind einblenden
    }

    @Override
    public void layoutControls() {
        ColumnConstraints spaceColumn           = new ColumnConstraints(10,10, Double.MAX_VALUE);
        ColumnConstraints columnLabelLeft       = new ColumnConstraints(180,200, Double.MAX_VALUE);
        ColumnConstraints columnSpaceLeft       = new ColumnConstraints(20,20, Double.MAX_VALUE);
        ColumnConstraints columnInputLeft       = new ColumnConstraints(130,220, Double.MAX_VALUE);
        ColumnConstraints columnSpaceMiddle     = new ColumnConstraints(20,20, Double.MAX_VALUE);
        ColumnConstraints columnLabelRight      = new ColumnConstraints(180,200, Double.MAX_VALUE);
        ColumnConstraints columnSpaceRight      = new ColumnConstraints(20,20, Double.MAX_VALUE);
        ColumnConstraints columnInputRight      = new ColumnConstraints(130,220, Double.MAX_VALUE);

        setMinSize(600,400);
        setMaxSize(1200,1200);
        getColumnConstraints().addAll(spaceColumn,columnLabelLeft, columnSpaceLeft, columnInputLeft, columnSpaceMiddle, columnLabelRight, columnSpaceRight, columnInputRight, spaceColumn);


        RowConstraints spaceRow   = new RowConstraints(10,10, Double.MAX_VALUE);
        RowConstraints row01      = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints row02      = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints row03      = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints row04      = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints row05      = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints row06      = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints row07      = new RowConstraints(40,40, Double.MAX_VALUE);

        // RowConstraints row08      = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints row08      = new RowConstraints(140,90, Double.MAX_VALUE);

        RowConstraints row09      = new RowConstraints(40,40, Double.MAX_VALUE);
        RowConstraints row10      = new RowConstraints(40,40, Double.MAX_VALUE);

        getRowConstraints().addAll(spaceRow, row01, row02, row03, row04, row05, row06, row07, row08, row09, row10 ,spaceRow);

        //- Content Label left Column #########################################
        add(labelLocation,          1,1,1,1);
        add(labelCommun,            1,2,1,1);
        add(labelInstalKW,          1,3,1,1);
        add(labelLatitude,          1,4,1,1);
        add(labelConstructStart,    1,5,1,1);
        add(labelCount,             1,6,1,1);
        add(labelType,              1,7,1,1);
//        add(label2015,              1,8,1,1);
//        add(label2017,              1,9,1,1);
        add(labelImageURL,          1,10,1,1);

        //- Content Input left Column #########################################
        add(inputLocation,          3,1,1,1);
        add(inputCommun,            3,2,5,1);
        add(inputInstallKW,         3,3,1,1);
        add(inputLatitude,          3,4,1,1);
        add(inputConstructStart,    3,5,1,1);
        add(inputCount,             3,6,1,1);
        add(inputType,              3,7,5,1);
//        add(input2015,              3,8,1,1);
//        add(input2017,              3,9,1,1);
        add(inputImageURL,          3,10,5,1);

        //- Content Label right Column #########################################
        add(labelCanton,            5,1,1,1);
        // --> Input from right Column (inputCommun)
        add(labelStatus,            5,3,1,1);
        add(labelLongitude,         5,4,1,1);
        add(labelConstructFinished, 5,5,1,1);
        // --> Space
        // --> Input from right Column (inputType)
//        add(label2016,              5,8,1,1);
//        add(label2018,              5,9,1,1);
        // --> Input from right Column (inputImageURL)

        //- Content Input right Column  #######################################
        add(inputCanton,            7,1,1,1);
        // --> Input from right Column (inputCommun)
        add(inputStatus,            7,3,1,1);
        add(inputLongitude,         7,4,1,1);
        add(inputConstructFinished, 7,5,1,1);
        // --> Space
        // --> Input from right Column (inputType)
//        add(input2016,              7,8,1,1);
//        add(input2018,              7,9,1,1);

        //- GummibaerenDashboard
        add(gummibaerenDashboard,              1,8,7,2);

    }

    @Override
    public void setupBindings() {

        //- Input Matrix right Column
        inputLocation.textProperty()              .bindBidirectional(rootPM.getWindProxy().locationNameProperty());
        inputCommun.textProperty()              .bindBidirectional(rootPM.getWindProxy().communesProperty());
        inputCanton.textProperty()              .bindBidirectional(rootPM.getWindProxy().cantonProperty());
        inputInstallKW.textProperty()           .bindBidirectional(rootPM.getWindProxy().kwIstallProperty(), new NumberStringConverter());
        inputLatitude.textProperty()            .bindBidirectional(rootPM.getWindProxy().latitudeProperty(), new NumberStringConverter());
        inputConstructStart.textProperty()      .bindBidirectional(rootPM.getWindProxy().constructStartProperty(), new NumberStringConverter());
        inputCount.textProperty()               .bindBidirectional(rootPM.getWindProxy().countProperty(), new NumberStringConverter());
        inputType.textProperty()                .bindBidirectional(rootPM.getWindProxy().typeProperty());
        input2015.textProperty()                .bindBidirectional(rootPM.getWindProxy().mw15Property(), new NumberStringConverter());
        input2017.textProperty()                .bindBidirectional(rootPM.getWindProxy().mw17Property(), new NumberStringConverter());
        inputImageURL.textProperty()            .bindBidirectional(rootPM.getWindProxy().imageUrlProperty());

        //- Input Matrix left Column
        inputCanton.textProperty()              .bindBidirectional(rootPM.getWindProxy().cantonProperty());
        inputStatus.textProperty()              .bindBidirectional(rootPM.getWindProxy().statusProperty());
        inputLongitude.textProperty()           .bindBidirectional(rootPM.getWindProxy().longitudeProperty(), new NumberStringConverter());
        inputConstructFinished.textProperty()   .bindBidirectional(rootPM.getWindProxy().constructFinishProperty(), new NumberStringConverter());
        input2016.textProperty()                .bindBidirectional(rootPM.getWindProxy().mw16Property(), new NumberStringConverter());
        input2018.textProperty()                .bindBidirectional(rootPM.getWindProxy().mw18Property(), new NumberStringConverter());

        //- Binding gummibaeren Dashboard


    }

    @Override
    public void setupEventHandlers() {

        //- ValueChangeListener ###########################
        input2015.textProperty()              .addListener((observable, oldValue, newValue) -> {
            rootPM.getWindProxy().calcNewMWSum();

        });

        input2016.textProperty()              .addListener((observable, oldValue, newValue) -> {
            rootPM.getWindProxy().calcNewMWSum();
        });

        input2017.textProperty()              .addListener((observable, oldValue, newValue) -> {
            rootPM.getWindProxy().calcNewMWSum();
        });

        input2018.textProperty()              .addListener((observable, oldValue, newValue) -> {
            rootPM.getWindProxy().calcNewMWSum();
        });

        //- KeyEvents ###########################
        input2015.setOnKeyReleased(event -> {
            System.out.println("in 2015");
            rootPM.getWindProxy().calcNewMWSum();
            rootPM.refreshDataOverview();
        });

        input2016.setOnKeyReleased(event -> {
            System.out.println("in 2016");
            rootPM.getWindProxy().calcNewMWSum();
            rootPM.refreshDataOverview();
        });

        input2017.setOnKeyReleased(event -> {
            System.out.println("in 2017");
            rootPM.getWindProxy().calcNewMWSum();
            rootPM.refreshDataOverview();
        });

        input2018.setOnKeyReleased(event -> {
            System.out.println("in 2018");
            rootPM.getWindProxy().calcNewMWSum();
            rootPM.refreshDataOverview();
        });
    }



}
