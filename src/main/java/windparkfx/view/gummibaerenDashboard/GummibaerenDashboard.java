package windparkfx.view.gummibaerenDashboard;

import javafx.animation.*;
import javafx.beans.property.*;
import javafx.css.*;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;
import windparkfx.presentationmodel.RootPM;

import java.util.List;

/**
 * Produktionsanzeige in MWh für die Jahre 2015 bis 2018 für die Windpark-Applikation
 *
 * @author Louisa Reinger
 * @author Mario Wettstein
 */

public class GummibaerenDashboard extends Region {
    private final RootPM rootPM;

    // wird gebraucht fuer StyleableProperties
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

    private static final double MINIMUM_WIDTH  = 25;    // ToDo: Anpassen
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH = 1200;    // ToDo: Anpassen

    //Declaration:
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

    //- Icons for Select and Plus
    private Text buttonText1;
    private Text buttonText2;
    private Text buttonText3;
    private Text buttonText4;

    //panes for positioning
    private StackPane elementTitleStack;
    private GridPane allElementsGrid;
    private Pane element1Draw;
    private Pane element2Draw;
    private Pane element3Draw;
    private Pane element4Draw;
    private Pane elementSumDraw;
    private Pane elementSwichDraw;

    private GridPane sumAndswitchGrid;

    //For Switch: Icons
    private Label lightBulbOn;
    private Label lightBulbOff;

    //- For Switch: Control
    // all parts
    private Circle    thumb;
    private Rectangle frame;
//    private static final Color THUMB_ON  = Color.rgb( 62, 130, 247);
//    private static final Color THUMB_OFF = Color.rgb(250, 250, 250);
//    private static final Color FRAME_ON  = Color.rgb(162, 197, 255);
//    private static final Color FRAME_OFF = Color.rgb(153, 153, 153);
    // all properties
    private final BooleanProperty on = new SimpleBooleanProperty();
    // all animations
    private Transition onTransition;
    private Transition offTransition;


    //todo: nach binding initialValues rausnehmen
    //Properties:
    private final DoubleProperty productionValue1       =  new SimpleDoubleProperty();
    private final DoubleProperty productionValue2       =  new SimpleDoubleProperty();
    private final DoubleProperty productionValue3       =  new SimpleDoubleProperty();
    private final DoubleProperty productionValue4       =  new SimpleDoubleProperty();
    private final DoubleProperty productionSum          =  new SimpleDoubleProperty();
    private final StringProperty SumTitle               =  new SimpleStringProperty();
    private final StringProperty ccTitle                =  new SimpleStringProperty();
    private final BooleanProperty production1checked    =  new SimpleBooleanProperty(true);
    private final BooleanProperty production2checked    =  new SimpleBooleanProperty(true);
    private final BooleanProperty production3checked    =  new SimpleBooleanProperty(true);
    private final BooleanProperty production4checked    =  new SimpleBooleanProperty(true);

    // ToDo: ergänzen mit allen CSS stylable properties
    private static final CssMetaData<GummibaerenDashboard, Color> BASE_COLOR_META_DATA = FACTORY.createColorCssMetaData("-base-color", s -> s.baseColor);

    private final StyleableObjectProperty<Color> baseColor = new SimpleStyleableObjectProperty<Color>(BASE_COLOR_META_DATA) {
        @Override
        protected void invalidated() {
            setStyle(String.format("%s: %s;", getCssMetaData().getProperty(), colorToCss(get())));
            applyCss();
        }
    };



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


    // ToDo: Loeschen falls keine getaktete Animation benoetigt wird
    private final BooleanProperty          blinking = new SimpleBooleanProperty(false);
    private final ObjectProperty<Duration> pulse    = new SimpleObjectProperty<>(Duration.seconds(1.0));

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

    // ToDo: alle Animationen und Timelines deklarieren
    //private final Timeline timeline = new Timeline();

    // needed for resizing
    private Pane drawingPane;

    //- Construtor
    public GummibaerenDashboard(RootPM model) {
        this.rootPM = model;
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        initializeAnimations();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();

        //- Temporäre Value Sets
        initializeStartValues();

    }

    private void initializeSelf() {
        loadFonts("/fonts/Lato/Lato-Lig.ttf", "/fonts/Lato/Lato-Reg.ttf", "/fonts/fontawesome-webfont.ttf");
        addStylesheetFiles("style.css");

        getStyleClass().add("gummibaeren-dashboard");
    }

    private void initializeParts() {
        //ToDo: alle deklarierten Parts initialisieren
        //-----variables:-------
        //general
        double centerWidth = ARTBOARD_WIDTH * 0.5;
        double centerHeight = ARTBOARD_HEIGHT * 0.5;
        final double space = 8;

        //circles:
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

        //Switch:
        lightBulbOff = new Label(LIGHTBULB);
        lightBulbOff.getStyleClass().add("icon-lightbulb-off");

        lightBulbOn = new Label(LIGHTBULB);
        lightBulbOn.getStyleClass().add("icon-lightbulb-on");

        // Construct Dashboard with all Element
        //------All elements:
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


        //-------all grids:
        // Construct Grid Pane for all elements:
        allElementsGrid = new GridPane();
//        allElementsGrid.getStyleClass().add("elements-grid");
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

        //- Switch Control
        thumb = new Circle(10, 10, 10);
        thumb.getStyleClass().add("thumb");
        thumb.setMouseTransparent(true);

        frame = new Rectangle(0, 0, 40, 20);
        frame.getStyleClass().add("frame");
        frame.setMouseTransparent(true);

        elementSwichDraw = new Pane();
        elementSwichDraw.getStyleClass().add("element-switch-draw");
        elementSwichDraw.setPrefSize(50,  20);
    }

    private void initializeStartValues()
    {
        setCcTitle("Produktionsleistung in MWh pro Jahr");
        setSumTitle("Gesamt");
//        setProductionValue1(1000);
//        setProductionValue2(2000);
//        setProductionValue3(3000);
//        setProductionValue4(4000);

        setProductionSum(getProductionValue1() + getProductionValue2() + getProductionValue3() + getProductionValue4());
        setProduction1checked(true);
        setProduction2checked(true);
        setProduction3checked(true);
        setProduction4checked(true);


    }

    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.getStyleClass().add("drawing-pane");
        drawingPane.setMaxSize(ARTBOARD_WIDTH,  ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH,  ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    }

    private void initializeAnimations(){
        //ToDo: alle deklarierten Animationen initialisieren
        TranslateTransition onTranslation = new TranslateTransition(Duration.millis(500), thumb);

        onTranslation.setFromX(0);
        onTranslation.setToX(20);

        FillTransition onFill = new FillTransition(Duration.millis(500), frame);
//        onFill.setFromValue(FRAME_OFF);
//        onFill.setToValue(FRAME_ON);

        FillTransition onFillThumb = new FillTransition(Duration.millis(500), thumb);
//        onFillThumb.setFromValue(THUMB_OFF);
//        onFillThumb.setToValue(THUMB_ON);


        onTransition = new ParallelTransition(onTranslation, onFill, onFillThumb);

        TranslateTransition offTranslation = new TranslateTransition(Duration.millis(500), thumb);
        offTranslation.setFromX(20);
        offTranslation.setToX(0);

        FillTransition offFill = new FillTransition(Duration.millis(500), frame);
//        offFill.setFromValue(FRAME_ON);
//        offFill.setToValue(FRAME_OFF);

        FillTransition offFillThumb = new FillTransition(Duration.millis(500), thumb);
//        offFillThumb.setFromValue(THUMB_ON);
//        offFillThumb.setToValue(THUMB_OFF);

        offTransition = new ParallelTransition(offTranslation, offFill, onFillThumb);

    }

    private void layoutParts() {
        //Add Elements to Sum%SwitchGrid
        elementSumDraw.getChildren().addAll(displaySum,sumText);
        elementSwichDraw.getChildren().addAll(frame, thumb);

        sumAndswitchGrid.add(elementSumDraw, 0, 1,3,1);
        sumAndswitchGrid.add(lightBulbOff, 0, 3,1,1);
        sumAndswitchGrid.add(elementSwichDraw, 1,3, 1, 1);
        sumAndswitchGrid.add(lightBulbOn, 2, 3, 1,1);

        // Add Elements to allElementsGrid
        elementTitleStack.getChildren().addAll(title);
        element1Draw.getChildren().addAll(circle1,display1,year1,button1,buttonText1);
        element2Draw.getChildren().addAll(circle2,display2,year2,button2,buttonText2);
        element3Draw.getChildren().addAll(circle3,display3,year3,button3,buttonText3);
        element4Draw.getChildren().addAll(circle4,display4,year4,button4,buttonText4);

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

    private void setupEventHandlers() {
        //ToDo: bei Bedarf ergänzen

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
        elementSwichDraw.setOnMouseClicked(event -> setOn(!isOn()));
    }

    private void setupValueChangeListeners() {
        //ToDo: durch die Listener auf die Properties des Custom Controls ersetzen

        // fuer die getaktete Animation
        blinking.addListener((observable, oldValue, newValue) -> startClockedAnimation(newValue));
        onProperty().addListener((observable, oldValue, newValue) -> updateUI());

        //- ValueChangeListener ###########################
        display1.textProperty()              .addListener((observable, oldValue, newValue) -> {
            calculateSum();
        });

        display2.textProperty()              .addListener((observable, oldValue, newValue) -> {
            calculateSum();
        });

        display3.textProperty()              .addListener((observable, oldValue, newValue) -> {
            calculateSum();
        });

        display4.textProperty()              .addListener((observable, oldValue, newValue) -> {
            calculateSum();
        });
    }

    private void setupBindings() {
        display1.textProperty().bindBidirectional(productionValue1Property(), new NumberStringConverter());
        display2.textProperty().bindBidirectional(productionValue2Property(), new NumberStringConverter());
        display3.textProperty().bindBidirectional(productionValue3Property(), new NumberStringConverter());
        display4.textProperty().bindBidirectional(productionValue4Property(), new NumberStringConverter());
        displaySum.textProperty().bind(productionSum.asString("%.0f"));
        sumText.textProperty().bind(sumTitleProperty());
        title.textProperty().bind(ccTitle);

        productionValue1.bindBidirectional(rootPM.getWindProxy().mw15Property());
        productionValue2.bindBidirectional(rootPM.getWindProxy().mw16Property());
        productionValue3.bindBidirectional(rootPM.getWindProxy().mw17Property());
        productionValue4.bindBidirectional(rootPM.getWindProxy().mw18Property());
    }

    //- Own Methode
    private void calculateSum()
    {
        double sum = 0.0;
        if(isProduction1checked())
        {
            buttonText1.setText(CHECK);
            setUnCheckedSum1(false);
            sum += getProductionValue1();
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
            sum += getProductionValue2();
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
            sum += getProductionValue3();
        }
        else
        {
            setUnCheckedSum3(true);
            buttonText3.setText(PLUS);
            setUnCheckedSum3(true);
        }

        if(isProduction4checked())
        {
            buttonText4.setText(CHECK);
            setUnCheckedSum4(false);
            sum += getProductionValue4();
        }
        else
        {
            buttonText4.setText(PLUS);
            setUnCheckedSum4(true);
        }

        setProductionSum(sum);

        //setProductionSum(getProductionValue1() + getProductionValue2() + getProductionValue3() + getProductionValue4());
    }




    // Special Methodes

    private void updateUI(){
        onTransition.stop();  // wenn schon gestoppt macht diese Methode stop() garnichts
        offTransition.stop();

        if(isOn()){
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
        //ToDo: ergaenzen mit dem was bei der getakteten Animation gemacht werden muss
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

    //ToDo: ueberpruefen ob dieser Resizing-Ansatz anwendbar ist.
    private void resize() {
        Insets padding         = getPadding();
        double availableWidth  = getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getHeight() - padding.getTop() - padding.getBottom();

        double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);

        double scalingFactor = width / ARTBOARD_WIDTH;

        if (availableWidth > 0 && availableHeight > 0) {
            //ToDo: ueberpruefen ob die drawingPane immer zentriert werden soll (eventuell ist zum Beispiel linksbuendig angemessener)
            relocateDrawingPaneCentered();
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    private void relocateDrawingPaneCentered() {
        drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
    }

    private void relocateDrawingPaneCenterBottom(double scaleY, double paddingBottom) {
        double visualHeight = ARTBOARD_HEIGHT * scaleY;
        double visualSpace  = getHeight() - visualHeight;
        double y            = visualSpace + (visualHeight - ARTBOARD_HEIGHT) * 0.5 - paddingBottom;

        drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, y);
    }

    private void relocateDrawingPaneCenterTop(double scaleY, double paddingTop) {
        double visualHeight = ARTBOARD_HEIGHT * scaleY;
        double y            = (visualHeight - ARTBOARD_HEIGHT) * 0.5 + paddingTop;

        drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, y);
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




    //ToDo: diese Funktionen anschauen und für die Umsetzung des CustomControls benutzen

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
     * Berechnet den Winkel zwischen 0 und 360 Grad, 0 Grad entspricht "Nord", der dem value
     * innerhalb des Wertebereichs zwischen minValue und maxValue entspricht.
     *
     * @param value der aktuelle Wert
     * @param minValue untere Grenze des Wertebereichs
     * @param maxValue obere Grenze des Wertebereichs
     * @return angle Winkel zwischen 0 und 360 Grad
     */
    private double valueToAngle(double value, double minValue, double maxValue) {
        return percentageToAngle(valueToPercentage(value, minValue, maxValue));
    }

    /**
     * Umrechnung der Maus-Position auf den aktuellen Wert.
     *
     * Diese Funktion ist sinnvoll nur fuer radiale Controls einsetzbar.
     *
     * Lineare Controls wie Slider müssen auf andere Art die Mausposition auf den value umrechnen.
     *
     * @param mouseX x-Position der Maus
     * @param mouseY y-Position der Maus
     * @param cx x-Position des Zentrums des radialen Controls
     * @param cy y-Position des Zentrums des radialen Controls
     * @param minValue untere Grenze des Wertebereichs
     * @param maxValue obere Grenze des Wertebereichs
     * @return value der dem Winkel entspricht, in dem die Maus zum Mittelpunkt des radialen Controls steht
     */
    private double radialMousePositionToValue(double mouseX, double mouseY, double cx, double cy, double minValue, double maxValue){
        double percentage = angleToPercentage(angle(cx, cy, mouseX, mouseY));

        return percentageToValue(percentage, minValue, maxValue);
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
     * Erzeugt eine Text-Instanz in der Mitte des CustomControls.
     * Der Text bleibt zentriert auch wenn der angezeigte Text sich aendert.
     *
     * @param styleClass mit dieser StyleClass kann der erzeugte Text via css gestyled werden
     * @return Text
     */
    private Text createCenteredText(String styleClass) {
        return createCenteredText(ARTBOARD_WIDTH * 0.5, ARTBOARD_HEIGHT * 0.5, styleClass);
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
        textField.setLayoutX(13);

        return textField;
    }

    /**
     * Erzeugt eine Group von Lines, die zum Beispiel fuer Skalen oder Zifferblaetter verwendet werden koennen.
     *
     * Diese Funktion ist sinnvoll nur fuer radiale Controls einsetzbar.
     * @param cx x-Position des Zentrumspunkts
     * @param cy y-Position des Zentrumspunkts
     * @param radius radius auf dem die Anfangspunkte der Ticks liegen
     * @param numberOfTicks gewuenschte Anzahl von Ticks
     * @param startingAngle Wickel in dem der erste Tick liegt, zwischen 0 und 360 Grad
     * @param overallAngle gewuenschter Winkel zwischen den erzeugten Ticks, zwischen 0 und 360 Grad
     * @param tickLength Laenge eines Ticks
     * @param styleClass Name der StyleClass mit der ein einzelner Tick via css gestyled werden kann
     * @return Group mit allen Ticks
     */
    private Group createTicks(double cx, double cy, double radius, int numberOfTicks, double startingAngle, double overallAngle,  double tickLength, String styleClass) {
        Group group = new Group();

        double degreesBetweenTicks = overallAngle == 360 ?
                                     overallAngle / numberOfTicks :
                                     overallAngle /(numberOfTicks - 1);
        double innerRadius         = radius - tickLength;

        for (int i = 0; i < numberOfTicks; i++) {
            double angle = startingAngle + i * degreesBetweenTicks;

            Point2D startPoint = pointOnCircle(cx, cy, radius,      angle);
            Point2D endPoint   = pointOnCircle(cx, cy, innerRadius, angle);

            Line tick = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
            tick.getStyleClass().add(styleClass);
            group.getChildren().add(tick);
        }

        return group;
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

    // alle getter und setter  (generiert via "Code -> Generate... -> Getter and Setter)

    // ToDo: ersetzen durch die Getter und Setter Ihres CustomControls

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

    public boolean isBlinking() {
        return blinking.get();
    }

    public BooleanProperty blinkingProperty() {
        return blinking;
    }

    public void setBlinking(boolean blinking) {
        this.blinking.set(blinking);
    }

    public Duration getPulse() {
        return pulse.get();
    }

    public ObjectProperty<Duration> pulseProperty() {
        return pulse;
    }

    public void setPulse(Duration pulse) {
        this.pulse.set(pulse);
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

    //- Switch Control

    public boolean isOn() {
        return on.get();
    }

    public BooleanProperty onProperty() {
        return on;
    }

    public void setOn(boolean on) {
        this.on.set(on);
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
}