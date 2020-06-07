package windparkfx.view.GummibaerenDashboard;

import javafx.animation.AnimationTimer;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.*;
import javafx.css.*;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Produktionsanzeige in MWh für die Jahre 2015 bis 2018 für die Windpark-Applikation
 *
 * @author Louisa Reinger
 * @author Mario Wettstein
 */

public class GummibaerenDashboard extends Region {

    private static final StyleablePropertyFactory<GummibaerenDashboard> FACTORY = new StyleablePropertyFactory<>(Region.getClassCssMetaData());

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return FACTORY.getCssMetaData();
    }

    //variables
    private static final String CHECK = "\uf00c";
    private static final String PLUS = "\uf067";
    private static final String LIGHTBULB = "\uF0EB";

    private static final double ARTBOARD_WIDTH  = 532;
    private static final double ARTBOARD_HEIGHT = 128;

    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

    private static final double MINIMUM_WIDTH  = 25;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH = 1200;
    //Pattern
    private static final String CONVERTIBLE_REGEX = "(\\d+([,’.])?\\d+([,’.])?\\d+|\\d+([,’.])?\\d+|\\d+|\\d )";

//------------Declaration:----------------
    private Circle circle1;
    private Circle circle2;
    private Circle circle3;
    private Circle circle4;

    private Circle button1;
    private Circle button2;
    private Circle button3;
    private Circle button4;

    private TextField   display1;
    private TextField   display2;
    private TextField   display3;
    private TextField   display4;

    private Text   year1;
    private Text   year2;
    private Text   year3;
    private Text   year4;

    private Text   ranking1;
    private Text   ranking2;
    private Text   ranking3;
    private Text   ranking4;

    private Text   displaySum;
    private Text   sumText;

    private Label   title;

    //icons for Select and Plus
    private Text buttonText1;
    private Text buttonText2;
    private Text buttonText3;
    private Text buttonText4;

    //switch
    private Label       lightBulbOn;
    private Label       lightBulbOff;
    private Circle      switchThumb;
    private Rectangle   switchFrame;

    // all animations
    private Transition onTransition;
    private Transition offTransition;

    //panes for positioning
    private StackPane   elementTitleStack;
    private GridPane    allElementsGrid;
    private Pane        element1Draw;
    private Pane        element2Draw;
    private Pane        element3Draw;
    private Pane        element4Draw;
    private Pane        elementSumDraw;
    private Pane        elementSwichDraw;
    private GridPane    sumAndswitchGrid;
    // needed for resizing
    private Pane        drawingPane;

//------------Properties:----------------
    private final DoubleProperty productionValue1       =  new SimpleDoubleProperty();
    private final DoubleProperty productionValue2       =  new SimpleDoubleProperty();
    private final DoubleProperty productionValue3       =  new SimpleDoubleProperty();
    private final DoubleProperty productionValue4       =  new SimpleDoubleProperty();
    private final DoubleProperty  productionSum          =  new SimpleDoubleProperty();
    private final StringProperty  SumTitle               =  new SimpleStringProperty("Gesamt (Gerundet)");
    private final StringProperty  rankingVal1            =  new SimpleStringProperty();
    private final StringProperty  rankingVal2            =  new SimpleStringProperty();
    private final StringProperty  rankingVal3            =  new SimpleStringProperty();
    private final StringProperty  rankingVal4            =  new SimpleStringProperty();
    private final StringProperty  ccTitle                =  new SimpleStringProperty("Produktionsleistung in MWh pro Jahr");
    private final BooleanProperty production1checked     =  new SimpleBooleanProperty(true);
    private final BooleanProperty production2checked     =  new SimpleBooleanProperty(true);
    private final BooleanProperty production3checked     =  new SimpleBooleanProperty(true);
    private final BooleanProperty production4checked     =  new SimpleBooleanProperty(true);
    private final StringProperty  userFacingString1      =  new SimpleStringProperty();
    private final StringProperty  userFacingString2      =  new SimpleStringProperty();
    private final StringProperty  userFacingString3      =  new SimpleStringProperty();
    private final StringProperty  userFacingString4      =  new SimpleStringProperty();
    private final BooleanProperty switchOn               =  new SimpleBooleanProperty();
    private final ObjectProperty<Duration> pulse         =  new SimpleObjectProperty<>(Duration.seconds(1.0));

    //- alle CSS stylable properties
    private static final CssMetaData<GummibaerenDashboard, Color> BASE_COLOR_META_DATA = FACTORY.createColorCssMetaData("-base-color", s -> s.baseColor);

    private final StyleableObjectProperty<Color> baseColor = new SimpleStyleableObjectProperty<Color>(BASE_COLOR_META_DATA) {
        @Override
        protected void invalidated() {
            setStyle(String.format("%s: %s;", getCssMetaData().getProperty(), colorToCss(get())));
            applyCss();
        }
    };

//------------Pseudo Classes:----------------
    private static final PseudoClass        UNCHECKSUM_CLASS1              =   PseudoClass.getPseudoClass("unchecked-sum1");
    private final BooleanProperty unCheckedSum1 =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(UNCHECKSUM_CLASS1, get());
        }
    };
    private static final PseudoClass        UNCHECKSUM_CLASS2              =   PseudoClass.getPseudoClass("unchecked-sum2");
    private final BooleanProperty unCheckedSum2 =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(UNCHECKSUM_CLASS2, get());
        }
    };
    private static final PseudoClass        UNCHECKSUM_CLASS3              =   PseudoClass.getPseudoClass("unchecked-sum3");
    private final BooleanProperty unCheckedSum3 =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(UNCHECKSUM_CLASS3, get());
        }
    };
    private static final PseudoClass        UNCHECKSUM_CLASS4              =   PseudoClass.getPseudoClass("unchecked-sum4");
    private final BooleanProperty unCheckedSum4 =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(UNCHECKSUM_CLASS4, get());
        }
    };

    private static final PseudoClass        NIGHT_CLASS              =   PseudoClass.getPseudoClass("night");
    private final BooleanProperty night =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(NIGHT_CLASS, get());
        }
    };

    private static final PseudoClass        INVALID_CLASS1              =   PseudoClass.getPseudoClass("invalid1");
    private final BooleanProperty invalid1 =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(INVALID_CLASS1, get());
        }
    };
    private static final PseudoClass        INVALID_CLASS2              =   PseudoClass.getPseudoClass("invalid2");
    private final BooleanProperty invalid2 =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(INVALID_CLASS2, get());
        }
    };
    private static final PseudoClass        INVALID_CLASS3              =   PseudoClass.getPseudoClass("invalid3");
    private final BooleanProperty invalid3 =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(INVALID_CLASS3, get());
        }
    };
    private static final PseudoClass        INVALID_CLASS4              =   PseudoClass.getPseudoClass("invalid4");
    private final BooleanProperty invalid4 =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(INVALID_CLASS4, get());
        }
    };

    private static final PseudoClass        CONVERTIBLE_CLASS1          =   PseudoClass.getPseudoClass("convertible1");
    private final BooleanProperty           convertible1         =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(CONVERTIBLE_CLASS1, get());
        }
    };

    private static final PseudoClass        CONVERTIBLE_CLASS2          =   PseudoClass.getPseudoClass("convertible2");
    private final BooleanProperty           convertible2         =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(CONVERTIBLE_CLASS2, get());
        }
    };

    private static final PseudoClass        CONVERTIBLE_CLASS3          =   PseudoClass.getPseudoClass("convertible3");
    private final BooleanProperty           convertible3         =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(CONVERTIBLE_CLASS3, get());
        }
    };

    private static final PseudoClass        CONVERTIBLE_CLASS4          =   PseudoClass.getPseudoClass("convertible4");
    private final BooleanProperty           convertible4         =   new SimpleBooleanProperty(){
        //connect PseudoClass:
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(CONVERTIBLE_CLASS4, get());
        }
    };


//------------Animation:----------------
    private final AnimationTimer timer = new AnimationTimer() {
        private long lastTimerCall;

        @Override
        public void handle(long now) {
            if (now > lastTimerCall + (getPulse().toMillis() * 1_000_000L)) {
                performPeriodicTask();
                lastTimerCall = now;
            }
        }
    };

//------------Constructor:----------------
    public GummibaerenDashboard() {
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        initializeAnimations();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();
    }

//------------initializeSelf:----------------
    private void initializeSelf() {
        loadFonts("/fonts/Lato/Lato-Lig.ttf", "/fonts/Lato/Lato-Reg.ttf", "/fonts/fontawesome-webfont.ttf");
        addStylesheetFiles("dashboardStyle.css");

        getStyleClass().add("gummibaeren-dashboard");
    }

//------------initializeParts:----------------
    private void initializeParts() {

        //-----variables:-------

        final double radiusMainCircle = 50;
        final double radiusSelectButton = 12;

        //-----initialisation:-------

        //circles
        //- Year 2015
        circle1 = new Circle(radiusMainCircle, radiusMainCircle ,radiusMainCircle);
        circle1.getStyleClass().add("active-circles1");
        //- Year 2016
        circle2 = new Circle(radiusMainCircle, radiusMainCircle ,radiusMainCircle);
        circle2.getStyleClass().add("active-circles2");
        //- Year 2017
        circle3 = new Circle(radiusMainCircle, radiusMainCircle ,radiusMainCircle);
        circle3.getStyleClass().add("active-circles3");
        //- Year 2018
        circle4 = new Circle(radiusMainCircle, radiusMainCircle ,radiusMainCircle);
        circle4.getStyleClass().add("active-circles4");


        //- Buttons
        button1 = new Circle(76+ radiusSelectButton, radiusSelectButton,radiusSelectButton);
        button1.getStyleClass().add("select-button1");

        button2 = new Circle(76+ radiusSelectButton, radiusSelectButton,radiusSelectButton);
        button2.getStyleClass().add("select-button2");

        button3 = new Circle(76+ radiusSelectButton, radiusSelectButton,radiusSelectButton);
        button3.getStyleClass().add("select-button3");

        button4 = new Circle(76+ radiusSelectButton, radiusSelectButton,radiusSelectButton);
        button4.getStyleClass().add("select-button4");

        //- Button Text
        buttonText1 = createCenteredText(76+ radiusSelectButton, radiusSelectButton,"button-text1");
        buttonText1.setText(CHECK);
        buttonText1.setMouseTransparent(true);

        buttonText2 = createCenteredText(76+ radiusSelectButton, radiusSelectButton,"button-text2");
        buttonText2.setText(CHECK);
        buttonText2.setMouseTransparent(true);

        buttonText3 = createCenteredText(76+ radiusSelectButton, radiusSelectButton,"button-text3");
        buttonText3.setText(CHECK);
        buttonText3.setMouseTransparent(true);

        buttonText4 = createCenteredText(76+ radiusSelectButton, radiusSelectButton,"button-text4");
        buttonText4.setText(CHECK);
        buttonText4.setMouseTransparent(true);

        //displays
        display1 = createCenteredTextField(radiusMainCircle, radiusMainCircle ,"displays1");
        display2 = createCenteredTextField(radiusMainCircle, radiusMainCircle ,"displays2");
        display3 = createCenteredTextField(radiusMainCircle, radiusMainCircle ,"displays3");
        display4 = createCenteredTextField(radiusMainCircle, radiusMainCircle ,"displays4");

        //sum
        displaySum = createCenteredText(0, 18+7, "display-sum");
        sumText = createCenteredText(0,46+7,"sum-text");

        //years
        year1 = createCenteredText("2015",radiusMainCircle,radiusMainCircle-30,"year");
        year2 = createCenteredText("2016",radiusMainCircle,radiusMainCircle-30,"year");
        year3 = createCenteredText("2017",radiusMainCircle,radiusMainCircle-30,"year");
        year4 = createCenteredText("2018",radiusMainCircle,radiusMainCircle-30,"year");

        //title
        title = new Label();
        title.getStyleClass().add("title-label");

        //ranking
        ranking1 = createCenteredTextRanking("1st",radiusMainCircle,radiusMainCircle + 32,"ranking1");
        ranking2 = createCenteredTextRanking("2nd",radiusMainCircle,radiusMainCircle + 32,"ranking2");
        ranking3 = createCenteredTextRanking("3rd",radiusMainCircle,radiusMainCircle + 32,"ranking3");
        ranking4 = createCenteredTextRanking("4th",radiusMainCircle,radiusMainCircle + 32,"ranking4");

        //switch:
        lightBulbOff = new Label(LIGHTBULB);
        lightBulbOff.getStyleClass().add("icon-lightbulb-off");

        lightBulbOn = new Label(LIGHTBULB);
        lightBulbOn.getStyleClass().add("icon-lightbulb-on");

        switchThumb = new Circle(10, 11, 10);
        switchThumb.getStyleClass().add("thumb");
        switchThumb.setMouseTransparent(true);

        switchFrame = new Rectangle(0, 1, 40, 20);
        switchFrame.getStyleClass().add("frame");
        switchFrame.setMouseTransparent(true);

        // Construct Dashboard with all elements
        //------All elements #########################################################################:
        //element for title:
        elementTitleStack = new StackPane();
        elementTitleStack.getStyleClass().add("element-title-stack");
        elementTitleStack.setPrefSize(ARTBOARD_WIDTH, 20);

        //elements for circles:
        element1Draw = new Pane();
        element2Draw = new Pane();
        element3Draw = new Pane();
        element4Draw = new Pane();
        element1Draw.getStyleClass().add("element-circle-draw");
        element2Draw.getStyleClass().add("element-circle-draw");
        element3Draw.getStyleClass().add("element-circle-draw");
        element4Draw.getStyleClass().add("element-circle-draw");
        element1Draw.setPrefSize(100,  100);
        element2Draw.setPrefSize(100,  100);
        element3Draw.setPrefSize(100,  100);
        element4Draw.setPrefSize(100,  100);

        //element for sum:
        elementSumDraw = new Pane();
        elementSumDraw.getStyleClass().add("element-sum-draw");
        elementSumDraw.setPrefSize(114,  100);

        //element for switch:
        elementSwichDraw = new Pane();
        elementSwichDraw.getStyleClass().add("element-switch-draw");
        elementSwichDraw.setPrefSize(50,  20);


        //------- ALL GRIDS #########################################################################:
        // Construct Grid Pane for all elements:
        allElementsGrid = new GridPane();
        allElementsGrid.getStyleClass().add("elements-grid");
        allElementsGrid.setPrefSize(ARTBOARD_WIDTH,  ARTBOARD_HEIGHT);

        ColumnConstraints sumColumn = new ColumnConstraints(114, 114, Double.MAX_VALUE);
        ColumnConstraints standardColumn = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        ColumnConstraints standardColumnSpace = new ColumnConstraints(6, 6, Double.MAX_VALUE);
        allElementsGrid.getColumnConstraints().addAll(sumColumn, standardColumn, standardColumnSpace, standardColumn, standardColumnSpace, standardColumn, standardColumnSpace, standardColumn);

        RowConstraints titleRow = new RowConstraints(20, 20, Double.MAX_VALUE);
        RowConstraints elementsRow = new RowConstraints(100, 100, Double.MAX_VALUE);
        RowConstraints rowSpace = new RowConstraints(8, 8, Double.MAX_VALUE);
        allElementsGrid.getRowConstraints().addAll(titleRow, rowSpace, elementsRow);

        // Construct Grid Pane for sum and switch elements
        sumAndswitchGrid = new GridPane();
        sumAndswitchGrid.getStyleClass().add("elements-grid");
        sumAndswitchGrid.setPrefSize(114, 100);

        ColumnConstraints c1 = new ColumnConstraints(29, 29, Double.MAX_VALUE);
        ColumnConstraints switchCol = new ColumnConstraints(56, 56, Double.MAX_VALUE);
        ColumnConstraints c3 = new ColumnConstraints(29, 29, Double.MAX_VALUE);
        sumAndswitchGrid.getColumnConstraints().addAll(c1, switchCol, c3);

        RowConstraints spaceToTop = new RowConstraints(7, 8, Double.MAX_VALUE);
        RowConstraints sumRow = new RowConstraints(59, 59, Double.MAX_VALUE);
        RowConstraints spaceBetween = new RowConstraints(14, 14, Double.MAX_VALUE);
        RowConstraints switchRow = new RowConstraints(20, 20, Double.MAX_VALUE);
        sumAndswitchGrid.getRowConstraints().addAll(spaceToTop, sumRow, spaceBetween, switchRow);
    }

//------------initializeDrawingPane:----------------
    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.getStyleClass().add("drawing-pane");
        drawingPane.setMaxSize(ARTBOARD_WIDTH,  ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH,  ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);

        checkRankingPlace();
    }

//------------initializeAnimations:----------------
    private void initializeAnimations(){
        //- deklarierten Animationen initialisieren
        TranslateTransition onTranslation = new TranslateTransition(Duration.millis(500), switchThumb);

        onTranslation.setFromX(0);
        onTranslation.setToX(20);

        onTransition = new ParallelTransition(onTranslation);

        TranslateTransition offTranslation = new TranslateTransition(Duration.millis(500), switchThumb);
        offTranslation.setFromX(20);
        offTranslation.setToX(0);

        offTransition = new ParallelTransition(offTranslation);

    }

//------------layoutParts:----------------
    private void layoutParts() {
        //Add Elements to SumAndSwitchGrid
        elementSumDraw.getChildren().addAll(displaySum,sumText);
        elementSwichDraw.getChildren().addAll(switchFrame, switchThumb);

        sumAndswitchGrid.add(elementSumDraw, 0, 1,3,1);
        sumAndswitchGrid.add(lightBulbOn, 0, 3,1,1);
        sumAndswitchGrid.add(elementSwichDraw, 1,3, 1, 1);
        sumAndswitchGrid.add(lightBulbOff, 2, 3, 1,1);

        // Add Elements to allElementsGrid
        elementTitleStack.getChildren().addAll(title);
        element1Draw.getChildren().addAll(circle1,display1,year1,button1,buttonText1,ranking1);
        element2Draw.getChildren().addAll(circle2,display2,year2,button2,buttonText2,ranking2);
        element3Draw.getChildren().addAll(circle3,display3,year3,button3,buttonText3,ranking3);
        element4Draw.getChildren().addAll(circle4,display4,year4,button4,buttonText4,ranking4);

        allElementsGrid.add(title,              0,0,8,1);
        allElementsGrid.add(sumAndswitchGrid,   0,2,1,1);
        allElementsGrid.add(element1Draw,       1,2,1,1);
        allElementsGrid.add(element2Draw,       3,2,1,1);
        allElementsGrid.add(element3Draw,       5,2,1,1);
        allElementsGrid.add(element4Draw,       7,2,1,1);

        //
        drawingPane.getChildren().addAll(elementTitleStack, allElementsGrid);
        getChildren().add(drawingPane);
    }

//------------setupEventHandlers:----------------
    private void setupEventHandlers() {
        button1.setOnMouseClicked(event -> {
            production1checked.setValue(!isProduction1checked());
            calculateSum();
        });
        button2.setOnMouseClicked(event -> {
            production2checked.setValue(!isProduction2checked());
            calculateSum();
        });
        button3.setOnMouseClicked(event -> {
            production3checked.setValue(!isProduction3checked());
            calculateSum();
        });
        button4.setOnMouseClicked(event -> {
            production4checked.setValue(!isProduction4checked());
            calculateSum();
        });

        //- Display Value KeyReleased
        display1.setOnKeyReleased(event -> {
            System.out.println("in 2015");
            calculateSum();
        });
        display2.setOnKeyReleased(event -> {
            System.out.println("in 2016");
            calculateSum();
        });
        display3.setOnKeyReleased(event -> {
            System.out.println("in 2017");
            calculateSum();
        });
        display4.setOnKeyReleased(event -> {
            System.out.println("in 2018");
            calculateSum();
        });

        //- Switch Control
        elementSwichDraw.setOnMouseClicked(event -> setSwitchOn(!getSwitchOn()));

        //forgiving format (convertible)
        display1.setOnAction(event -> convert(1));
        display2.setOnAction(event -> convert(2));
        display3.setOnAction(event -> convert(3));
        display4.setOnAction(event -> convert(4));

        display1.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            keyPressedCheck(event, 1);
        });
        display2.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            keyPressedCheck(event, 2);
        });
        display3.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            keyPressedCheck(event, 3);
        });
        display4.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            keyPressedCheck(event, 4);
        });
    }


//------------setupValueChangeListeners:----------------
    private void setupValueChangeListeners() {
        //- for Update Animation
        switchOnProperty().addListener((observable, oldValue, newValue) -> updateUI());

        //- ValueChangeListener ###########################
        userFacingString1.addListener((observable, oldValue, newValue) -> {
            System.out.println("war im changeListener 1");
            checkNewValueSet(newValue,1);

            //forgiving format - convertible true/false setzen:
            setConvertible1(setConvertible(newValue));

            //updateSum:
            calculateSum();
        });
        userFacingString2.addListener((observable, oldValue, newValue) -> {
            System.out.println("war im changeListener 2");
            checkNewValueSet(newValue,2);

            //forgiving format - convertible true/false setzen:
            setConvertible2(setConvertible(newValue));

            //updateSum:
            calculateSum();
        });
        userFacingString3.addListener((observable, oldValue, newValue) -> {
            System.out.println("war im changeListener 3");
            checkNewValueSet(newValue,3);

            //forgiving format - convertible true/false setzen:
            setConvertible3(setConvertible(newValue));

            //updateSum:
            calculateSum();
        });
        userFacingString4.addListener((observable, oldValue, newValue) -> {
            System.out.println("war im changeListener 4");
            checkNewValueSet(newValue,4);

            //forgiving format - convertible true/false setzen:
            setConvertible4(setConvertible(newValue));

            //updateSum:
            calculateSum();
        });

        //binding: userFacingString-productionValue
        productionValue1.addListener((observable, oldValue, newValue) -> {
            String newProdVal = newValue.toString();
            setUserFacingString1(newProdVal);
        });

        productionValue2.addListener((observable, oldValue, newValue) -> {
            String newProdVal = newValue.toString();
            setUserFacingString2(newProdVal);
        });

        productionValue3.addListener((observable, oldValue, newValue) -> {
            String newProdVal = newValue.toString();
            setUserFacingString3(newProdVal);
        });

        productionValue4.addListener((observable, oldValue, newValue) -> {
            String newProdVal = newValue.toString();
            setUserFacingString4(newProdVal);
        });

    }

//------------setupBindings:----------------
    private void setupBindings() {
        String format = "%.1f";

        display1.textProperty().bindBidirectional(userFacingString1Property());
        display2.textProperty().bindBidirectional(userFacingString2Property());
        display3.textProperty().bindBidirectional(userFacingString3Property());
        display4.textProperty().bindBidirectional(userFacingString4Property());

        displaySum.textProperty().bind(productionSum.asString(format));
        sumText.textProperty().bind(sumTitleProperty());
        title.textProperty().bind(ccTitle);

        ranking1.textProperty().bind(rankingVal1Property());
        ranking2.textProperty().bind(rankingVal2Property());
        ranking3.textProperty().bind(rankingVal3Property());
        ranking4.textProperty().bind(rankingVal4Property());
    }

//########------------Own Methods:----------------##########

    public void keyPressedCheck(KeyEvent key, int valNr)
    {
        System.out.println(key.getCode());
        switch (key.getCode())
        {
            case UP:
                if(valNr == 1) setProductionValue1(getProductionValue1() + 100);
                if(valNr == 2) setProductionValue2(getProductionValue2() + 100);
                if(valNr == 3) setProductionValue3(getProductionValue3() + 100);
                if(valNr == 4) setProductionValue4(getProductionValue4() + 100);
                key.consume();
                break;
            case DOWN:
                if(valNr == 1) setProductionValue1(getProductionValue1() - 100);
                if(valNr == 2) setProductionValue2(getProductionValue2() - 100);
                if(valNr == 3) setProductionValue3(getProductionValue3() - 100);
                if(valNr == 4) setProductionValue4(getProductionValue4() - 100);
                key.consume();
                break;
            case RIGHT:
                if(valNr == 1) setProductionValue1(getProductionValue1() + 1);
                if(valNr == 2) setProductionValue2(getProductionValue2() + 1);
                if(valNr == 3) setProductionValue3(getProductionValue3() + 1);
                if(valNr == 4) setProductionValue4(getProductionValue4() + 1);
                key.consume();
                break;
            case LEFT:
                if(valNr == 1) setProductionValue1(getProductionValue1() - 1);
                if(valNr == 2) setProductionValue2(getProductionValue2() - 1);
                if(valNr == 3) setProductionValue3(getProductionValue3() - 1);
                if(valNr == 4) setProductionValue4(getProductionValue4() - 1);
                key.consume();
                break;
        }
    }

    public void convert(int pos)
    {
        switch (pos)
        {
            case 1:
                if (isConvertible1()) {
                    System.out.println("Was in convert1()");
                    if(getUserFacingString1().equals("t")){
                        setProductionValue1(0);
                        setProductionValue1(1000);
                    }
                    if(getUserFacingString1().equals("h")){
                        setProductionValue1(0);
                        setProductionValue1(100);
                    }
                }
                break;
            case 2:
                if (isConvertible2()) {
                    System.out.println("Was in convert2()");
                    if(getUserFacingString2().equals("t")){
                        setProductionValue2(0);
                        setProductionValue2(1000);
                    }
                    if(getUserFacingString2().equals("h")){
                        setProductionValue2(0);
                        setProductionValue2(100);
                    }
                }
                break;
            case 3:
                if (isConvertible3()) {
                    System.out.println("Was in convert3()");
                    if(getUserFacingString3().equals("t")){
                        setProductionValue3(0);
                        setProductionValue3(1000);
                    }
                    if(getUserFacingString3().equals("h")){
                        setProductionValue3(0);
                        setProductionValue3(100);
                    }
                }
                break;
            case 4:
                if (isConvertible4()) {
                    System.out.println("Was in convert4()");
                    if(getUserFacingString4().equals("t")){
                        setProductionValue4(0);
                        setProductionValue4(1000);
                    }
                    if(getUserFacingString4().equals("h")){
                        setProductionValue4(0);
                        setProductionValue4(100);
                    }
                }
                break;
        }
    }

    private void calculateSum()
    {
        double sum = 0.0;
        if(isProduction1checked())
        {
            buttonText1.setText(CHECK);
            setUnCheckedSum1(false);
            if(getProductionValue1() >= 0.0)
            {
                sum += getProductionValue1();
            }
        }
        else
        {
            setUnCheckedSum1(true);
            buttonText1.setText(PLUS);
        }

        if(isProduction2checked())
        {
            buttonText2.setText(CHECK);
            setUnCheckedSum2(false);
            if(getProductionValue2() >= 0.0)
            {
                sum += getProductionValue2();
            }
        }
        else
        {
            buttonText2.setText(PLUS);
            setUnCheckedSum2(true);
        }

        if(isProduction3checked())
        {
            buttonText3.setText(CHECK);
            setUnCheckedSum3(false);
            if(getProductionValue3() >= 0.0)
            {
                sum += getProductionValue3();
            }
        }
        else
        {
            setUnCheckedSum3(true);
            buttonText3.setText(PLUS);
        }

        if(isProduction4checked())
        {
            buttonText4.setText(CHECK);
            setUnCheckedSum4(false);
            if(getProductionValue4() >= 0.0)
            {
                sum += getProductionValue4();
            }
        }
        else
        {
            buttonText4.setText(PLUS);
            setUnCheckedSum4(true);
        }

        //- Set Ranking
        checkRankingPlace();
        //- Calculat Sum
        setProductionSum(sum);
    }

    private boolean setConvertible(String newValue)
    {
        if(newValue.equals("t")){
            return true;
        }
        else if(newValue.equals("h")){
            return true;
        }
        else{
            return false;
        }
    }

    private void checkRankingPlace()
    {
        double[] valArray = {getProductionValue1(), getProductionValue2(), getProductionValue3(), getProductionValue4()};
        String[] rankArray = {"1st", "2nd", "3rd", "4th"};

        Arrays.sort(valArray);

        rankingVal1.setValue("_");
        rankingVal2.setValue("_");
        rankingVal3.setValue("_");
        rankingVal4.setValue("_");

        //- Value for index and Ranking
        int k = 0;
        boolean contVal = false;

        for(int i = 0; i < valArray.length; i++)
        {
            if(valArray[(valArray.length -1) - i] == getProductionValue1() & !isUnCheckedSum1() & ("_").equals(ranking1.getText()))
            {
                rankingVal1.setValue(rankArray[k]);
                contVal = true;
            }
            if(valArray[(valArray.length -1) - i] == getProductionValue2() & !isUnCheckedSum2() & ("_").equals(ranking2.getText()))
            {
                rankingVal2.setValue(rankArray[k]);
                contVal = true;
            }
            if(valArray[(valArray.length -1) - i] == getProductionValue3() & !isUnCheckedSum3() & ("_").equals(ranking3.getText()))
            {
                rankingVal3.setValue(rankArray[k]);
                contVal = true;
            }
            if(valArray[(valArray.length -1) - i] == getProductionValue4() & !isUnCheckedSum4() & ("_").equals(ranking4.getText()))
            {
                rankingVal4.setValue(rankArray[k]);
                contVal = true;
            }
            if(contVal)
            {
                k++;
                contVal = false;
            }
        }
    }

    public void checkNewValueSet(String newValue, int valNr)
    {
        boolean newValueValid1 = false;
        boolean newValueValid2 = false;
        double newprodVal = 0.0;
        //valid/invalid status:
        if(newValue.equals("t") || newValue.equals("h") || Pattern.matches(CONVERTIBLE_REGEX, newValue)){
            newValueValid1 = true;
            if(Pattern.matches(CONVERTIBLE_REGEX, newValue)){
                newprodVal = Double.valueOf(newValue);
                newValueValid2 = true;
            }
        }

        //- Set Values
        if(newValueValid1) {
            switch (valNr) {
                case 1:
                    if(newValueValid2){
                        setProductionValue1(newprodVal);
                    }
                    setInvalid1(false);
                    break;
                case 2:
                    if(newValueValid2){
                        setProductionValue2(newprodVal);
                    }
                    setInvalid2(false);
                    break;
                case 3:
                    if(newValueValid2){
                        setProductionValue3(newprodVal);
                    }
                    setInvalid3(false);
                    break;
                case 4:
                    if(newValueValid2){
                        setProductionValue4(newprodVal);
                    }
                    setInvalid4(false);
                    break;
            }
        }
        else
        {
            switch (valNr) {
                case 1:
                    setInvalid1(true);
                    break;
                case 2:
                    setInvalid2(true);
                    break;
                case 3:
                    setInvalid3(true);
                    break;
                case 4:
                    setInvalid4(true);
                    break;
            }
        }
    }


//########------------Special Methods:----------------##########

    private void updateUI(){
        onTransition.stop();  // wenn schon gestoppt macht diese Methode stop() garnichts
        offTransition.stop();

        if(getSwitchOn()){
            //thumb.setLayoutX(16);
            onTransition.play();
            setNight(true);
        }
        else {
            //thumb.setLayoutX(0);
            offTransition.play();
            setNight(false);
        }
        // thumb.setLayoutX(isOn() ? 16 : 0);
    }

    private void performPeriodicTask(){
        //ergaenzen mit dem was bei der getakteten Animation gemacht werden muss
        //normalerweise: den Wert einer der Status-Properties aendern
    }

    private void startClockedAnimation(boolean start) {
        if (start) {
            timer.start();
        } else {
            timer.stop();
        }
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        resize();
    }

    //- Resizing-Ansatz
    private void resize() {
        Insets padding         = getPadding();
        double availableWidth  = getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getHeight() - padding.getTop() - padding.getBottom();

        double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);

        double scalingFactor = width / ARTBOARD_WIDTH;

        if (availableWidth > 0 && availableHeight > 0) {
            //drawingPane immer zentriert
            relocateDrawingPaneCentered();
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    private void relocateDrawingPaneCentered() {
        drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
    }

    // Sammlung nuetzlicher Funktionen

    private Text createCenteredText(String year, double cx, double cy, String styleClass) {
        Text text = new Text(year);
        text.getStyleClass().add(styleClass);
        text.setTextOrigin(VPos.CENTER);
        text.setTextAlignment(TextAlignment.CENTER);
        double width = cx > ARTBOARD_WIDTH * 0.5 ? ((ARTBOARD_WIDTH - cx) * 2.0) : cx * 2.0;
        text.setWrappingWidth(width);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setY(cy);
        text.setX(cx - (width / 2.0));

        return text;
    }

    private Text createCenteredTextRanking(String ranking, double cx, double cy, String styleClass) {
        Text text = new Text(ranking);
        text.getStyleClass().add(styleClass);
        text.setTextOrigin(VPos.CENTER);
        text.setTextAlignment(TextAlignment.CENTER);
        double width = cx > ARTBOARD_WIDTH * 0.5 ? ((ARTBOARD_WIDTH - cx) * 2.0) : cx * 2.0;
        text.setWrappingWidth(width);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setY(cy);
        text.setX(cx - (width / 2.0));

        return text;
    }


    //- Funktionen für die Umsetzung des CustomControls

    private void loadFonts(String... font){
        for(String f : font){
            Font.loadFont(getClass().getResourceAsStream(f), 0);
        }
    }

    private void addStylesheetFiles(String... stylesheetFile){
        for(String file : stylesheetFile){
            String stylesheet = getClass().getResource(file).toExternalForm();
            getStylesheets().add(stylesheet);
        }
    }

    /**
     * Umrechnen einer Prozentangabe, zwischen 0 und 100, in den tatsaechlichen Wert innerhalb des angegebenen Wertebereichs.
     *
     * @param percentage Wert in Prozent
     * @param minValue untere Grenze des Wertebereichs
     * @param maxValue obere Grenze des Wertebereichs
     * @return value der akuelle Wert
     */
    private double percentageToValue(double percentage, double minValue, double maxValue){
        return ((maxValue - minValue) * percentage) + minValue;
    }

    /**
     * Umrechnen des angegebenen Werts in eine Prozentangabe zwischen 0 und 100.
     *
     * @param value der aktuelle Wert
     * @param minValue untere Grenze des Wertebereichs
     * @param maxValue obere Grenze des Wertebereichs
     * @return Prozentangabe des aktuellen Werts
     */
    private double valueToPercentage(double value, double minValue, double maxValue) {
        return (value - minValue) / (maxValue - minValue);
    }

    /**
     * Umrechnung eines Winkels, zwischen 0 und 360 Grad, in eine Prozentangabe.
     *
     * Diese Funktion ist sinnvoll nur fuer radiale Controls einsetzbar.
     *
     * @param angle der Winkel
     * @return die entsprechende Prozentangabe
     */
    private double angleToPercentage(double angle){
        return angle / 360.0;
    }

    /**
     * Umrechnung einer Prozentangabe, zwischen 0 und 100, in den entsprechenden Winkel.
     *
     * Diese Funktion ist sinnvoll nur fuer radiale Controls einsetzbar.
     *
     * @param percentage die Prozentangabe
     * @return der entsprechende Winkel
     */
    private double percentageToAngle(double percentage){
        return 360.0 * percentage;
    }

    /**
     * Berechnet den Winkel zwischen einem Zentrums-Punkt und einem Referenz-Punkt.
     *
     * @param cx x-Position des Zentrums
     * @param cy y-Position des Zentrums
     * @param x x-Position des Referenzpunkts
     * @param y y-Position des Referenzpunkts
     * @return winkel zwischen 0 und 360 Grad
     */
    private double angle(double cx, double cy, double x, double y) {
        double deltaX = x - cx;
        double deltaY = y - cy;
        double radius = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
        double nx     = deltaX / radius;
        double ny     = deltaY / radius;
        double theta  = Math.toRadians(90) + Math.atan2(ny, nx);

        return Double.compare(theta, 0.0) >= 0 ? Math.toDegrees(theta) : Math.toDegrees((theta)) + 360.0;
    }

    /**
     * Berechnet den Punkt auf einem Kreis mit gegebenen Radius im angegebenen Winkel
     *
     * @param cX x-Position des Zentrums
     * @param cY y-Position des Zentrums
     * @param radius Kreisradius
     * @param angle Winkel zwischen 0 und 360 Grad
     * @return Punkt auf dem Kreis
     */
    private Point2D pointOnCircle(double cX, double cY, double radius, double angle) {
        return new Point2D(cX - (radius * Math.sin(Math.toRadians(angle - 180))),
                           cY + (radius * Math.cos(Math.toRadians(angle - 180))));
    }

    /**
     * Erzeugt eine Text-Instanz mit dem angegebenen Zentrum.
     * Der Text bleibt zentriert auch wenn der angezeigte Text sich aendert.
     *
     * @param cx x-Position des Zentrumspunkt des Textes
     * @param cy y-Position des Zentrumspunkt des Textes
     * @param styleClass mit dieser StyleClass kann der erzeugte Text via css gestyled werden
     * @return Text
     */
    private Text createCenteredText(double cx, double cy, String styleClass) {
        Text text = new Text();
        text.getStyleClass().add(styleClass);
        text.setTextOrigin(VPos.CENTER);
        text.setTextAlignment(TextAlignment.CENTER);
        double width = cx > ARTBOARD_WIDTH * 0.5 ? ((ARTBOARD_WIDTH - cx) * 2.0) : cx * 2.0;
        text.setWrappingWidth(width);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setY(cy);
        text.setX(cx - (width / 2.0));

        return text;
    }
    private TextField createCenteredTextField(double cx, double cy, String styleClass) {
        TextField textField = new TextField();
        textField.getStyleClass().add(styleClass);
        textField.setAlignment(Pos.CENTER);
        textField.setLayoutY(30);
        textField.setLayoutX(10);

        return textField;
    }


    private String colorToCss(final Color color) {
  		return color.toString().replace("0x", "#");
  	}


    // compute sizes

    @Override
    protected double computeMinWidth(double height) {
        Insets padding           = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return MINIMUM_WIDTH + horizontalPadding;
    }

    @Override
    protected double computeMinHeight(double width) {
        Insets padding         = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return MINIMUM_HEIGHT + verticalPadding;
    }

    @Override
    protected double computePrefWidth(double height) {
        Insets padding           = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return ARTBOARD_WIDTH + horizontalPadding;
    }

    @Override
    protected double computePrefHeight(double width) {
        Insets padding         = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return ARTBOARD_HEIGHT + verticalPadding;
    }

    //- Getter und Setter  CustomControls ################################################


    public double getProductionValue1() {
        return productionValue1.get();
    }

    public DoubleProperty productionValue1Property() {
        return productionValue1;
    }

    public void setProductionValue1(double productionValue1) {
        this.productionValue1.set(productionValue1);
    }

    public double getProductionValue2() {
        return productionValue2.get();
    }

    public DoubleProperty productionValue2Property() {
        return productionValue2;
    }

    public void setProductionValue2(double productionValue2) {
        this.productionValue2.set(productionValue2);
    }

    public double getProductionValue3() {
        return productionValue3.get();
    }

    public DoubleProperty productionValue3Property() {
        return productionValue3;
    }

    public void setProductionValue3(double productionValue3) {
        this.productionValue3.set(productionValue3);
    }

    public double getProductionValue4() {
        return productionValue4.get();
    }

    public DoubleProperty productionValue4Property() {
        return productionValue4;
    }

    public void setProductionValue4(double productionValue4) {
        this.productionValue4.set(productionValue4);
    }

    public double getProductionSum() {
        return productionSum.get();
    }

    public DoubleProperty productionSumProperty() {
        return productionSum;
    }

    public void setProductionSum(double productionSum) {
        this.productionSum.set(productionSum);
    }

    public Color getBaseColor() {
        return baseColor.get();
    }

    public StyleableObjectProperty<Color> baseColorProperty() {
        return baseColor;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor.set(baseColor);
    }


    public String getCcTitle() {
        return ccTitle.get();
    }

    public StringProperty ccTitleProperty() {
        return ccTitle;
    }

    public void setCcTitle(String ccTitle) {
        this.ccTitle.set(ccTitle);
    }

    //- Animation
    public Duration getPulse() {
        return pulse.get();
    }

    public ObjectProperty<Duration> pulseProperty() {
        return pulse;
    }

    public void setPulse(Duration pulse) {
        this.pulse.set(pulse);
    }

    //- Check / unCheck production1 - 4 #######################################################################

    public boolean isProduction1checked() {
        return production1checked.get();
    }

    public BooleanProperty production1checkedProperty() {
        return production1checked;
    }

    public void setProduction1checked(boolean production1checked) {
        this.production1checked.set(production1checked);
    }

    public boolean isProduction2checked() {
        return production2checked.get();
    }

    public BooleanProperty production2checkedProperty() {
        return production2checked;
    }

    public void setProduction2checked(boolean production2checked) {
        this.production2checked.set(production2checked);
    }

    public boolean isProduction3checked() {
        return production3checked.get();
    }

    public BooleanProperty production3checkedProperty() {
        return production3checked;
    }

    public void setProduction3checked(boolean production3checked) {
        this.production3checked.set(production3checked);
    }

    public boolean isProduction4checked() {
        return production4checked.get();
    }

    public BooleanProperty production4checkedProperty() {
        return production4checked;
    }

    public void setProduction4checked(boolean production4checked) {
        this.production4checked.set(production4checked);
    }


    //- Unchecked Sum ###############################################################################################

    public boolean isUnCheckedSum1() {
        return unCheckedSum1.get();
    }

    public BooleanProperty unCheckedSum1Property() {
        return unCheckedSum1;
    }

    public void setUnCheckedSum1(boolean unCheckedSum1) {
        this.unCheckedSum1.set(unCheckedSum1);
    }

    public boolean isUnCheckedSum2() {
        return unCheckedSum2.get();
    }

    public BooleanProperty unCheckedSum2Property() {
        return unCheckedSum2;
    }

    public void setUnCheckedSum2(boolean unCheckedSum2) {
        this.unCheckedSum2.set(unCheckedSum2);
    }

    public boolean isUnCheckedSum3() {
        return unCheckedSum3.get();
    }

    public BooleanProperty unCheckedSum3Property() {
        return unCheckedSum3;
    }

    public void setUnCheckedSum3(boolean unCheckedSum3) {
        this.unCheckedSum3.set(unCheckedSum3);
    }

    public boolean isUnCheckedSum4() {
        return unCheckedSum4.get();
    }

    public BooleanProperty unCheckedSum4Property() {
        return unCheckedSum4;
    }

    public void setUnCheckedSum4(boolean unCheckedSum4) {
        this.unCheckedSum4.set(unCheckedSum4);
    }

    public String getSumTitle() {
        return SumTitle.get();
    }

    public StringProperty sumTitleProperty() {
        return SumTitle;
    }

    public void setSumTitle(String sumTitle) {
        this.SumTitle.set(sumTitle);
    }

    //- Rankging Value

    public String getRankingVal1() {
        return rankingVal1.get();
    }

    public StringProperty rankingVal1Property() {
        return rankingVal1;
    }

    public void setRankingVal1(String rankingVal1) {
        this.rankingVal1.set(rankingVal1);
    }

    public String getRankingVal2() {
        return rankingVal2.get();
    }

    public StringProperty rankingVal2Property() {
        return rankingVal2;
    }

    public void setRankingVal2(String rankingVal2) {
        this.rankingVal2.set(rankingVal2);
    }

    public String getRankingVal3() {
        return rankingVal3.get();
    }

    public StringProperty rankingVal3Property() {
        return rankingVal3;
    }

    public void setRankingVal3(String rankingVal3) {
        this.rankingVal3.set(rankingVal3);
    }

    public String getRankingVal4() {
        return rankingVal4.get();
    }

    public StringProperty rankingVal4Property() {
        return rankingVal4;
    }

    public void setRankingVal4(String rankingVal4) {
        this.rankingVal4.set(rankingVal4);
    }


    //- Switch Control

    public boolean getSwitchOn() {
        return switchOn.get();
    }

    public BooleanProperty switchOnProperty() {
        return switchOn;
    }

    public void setSwitchOn(boolean switchOn) {
        this.switchOn.set(switchOn);
    }

    public boolean isNight() {
        return night.get();
    }

    public BooleanProperty nightProperty() {
        return night;
    }

    public void setNight(boolean night) {
        this.night.set(night);
    }

    //- Is Valid

    public boolean isInvalid1() {
        return invalid1.get();
    }

    public BooleanProperty invalid1Property() {
        return invalid1;
    }

    public void setInvalid1(boolean invalid1) {
        this.invalid1.set(invalid1);
    }

    public boolean isInvalid2() {
        return invalid2.get();
    }

    public BooleanProperty invalid2Property() {
        return invalid2;
    }

    public void setInvalid2(boolean invalid2) {
        this.invalid2.set(invalid2);
    }

    public boolean isInvalid3() {
        return invalid3.get();
    }

    public BooleanProperty invalid3Property() {
        return invalid3;
    }

    public void setInvalid3(boolean invalid3) {
        this.invalid3.set(invalid3);
    }

    public boolean isInvalid4() {
        return invalid4.get();
    }

    public BooleanProperty invalid4Property() {
        return invalid4;
    }

    public void setInvalid4(boolean invalid4) {
        this.invalid4.set(invalid4);
    }

    public boolean isConvertible1() {
        return convertible1.get();
    }

    public BooleanProperty convertible1Property() {
        return convertible1;
    }

    public void setConvertible1(boolean convertible1) {
        this.convertible1.set(convertible1);
    }

    public boolean isConvertible2() {
        return convertible2.get();
    }

    public BooleanProperty convertible2Property() {
        return convertible2;
    }

    public void setConvertible2(boolean convertible2) {
        this.convertible2.set(convertible2);
    }

    public boolean isConvertible3() {
        return convertible3.get();
    }

    public BooleanProperty convertible3Property() {
        return convertible3;
    }

    public void setConvertible3(boolean convertible3) {
        this.convertible3.set(convertible3);
    }

    public boolean isConvertible4() {
        return convertible4.get();
    }

    public BooleanProperty convertible4Property() {
        return convertible4;
    }

    public void setConvertible4(boolean convertible4) {
        this.convertible4.set(convertible4);
    }

    public String getUserFacingString1() {
        return userFacingString1.get();
    }

    public StringProperty userFacingString1Property() {
        return userFacingString1;
    }

    public void setUserFacingString1(String userFacingString1) {
        this.userFacingString1.set(userFacingString1);
    }

    public String getUserFacingString2() {
        return userFacingString2.get();
    }

    public StringProperty userFacingString2Property() {
        return userFacingString2;
    }

    public void setUserFacingString2(String userFacingString2) {
        this.userFacingString2.set(userFacingString2);
    }

    public String getUserFacingString3() {
        return userFacingString3.get();
    }

    public StringProperty userFacingString3Property() {
        return userFacingString3;
    }

    public void setUserFacingString3(String userFacingString3) {
        this.userFacingString3.set(userFacingString3);
    }

    public String getUserFacingString4() {
        return userFacingString4.get();
    }

    public StringProperty userFacingString4Property() {
        return userFacingString4;
    }

    public void setUserFacingString4(String userFacingString4) {
        this.userFacingString4.set(userFacingString4);
    }
}