package windparkfx.view;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.layout.VBox;
import windparkfx.presentationmodel.RootPM;
import windparkfx.view.GummibaerenDashboard.GummibaerenDashboard;
import windparkfx.view.NewHeader_Toolbar.HeaderToolbar;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;


/**
 * @author Mario Wettstein
 */

public class RootPanel extends BorderPane implements ViewMixin {


    private final RootPM rootPM;

    //- Window Part
    //private HeaderView headerView; //- Own Header

    //- NewHeader_HydroToolbar
    private HeaderToolbar newHeaderView;


    private ContentView         contentView;
    private SideListView        sideListView;

    private DashboardView dashboardView;
    private TitleBoardView titleBoardView;

    private SplitPane splitPane;
    private SplitPane splitPaneVertical;

    private Label test;

    private Button button;


    //- gummibaerenDashboard
    private GummibaerenDashboard gummibaerenDashboard;


    //- Constructro
    public RootPanel(RootPM model) {
        this.rootPM = model;
        init();
    }

    @Override
    public void initializeSelf() {
        //addStylesheetFiles("stylenight.css");
        addStylesheetFiles(rootPM.getStyleChoose());
        getStyleClass().add("root-panel-view");
    }

    @Override
    public void initializeControls() {
        splitPane           = new SplitPane();
        splitPane.setMinWidth(600);
        splitPane.setPrefWidth(1200);
        splitPane.setMinHeight(600);
        splitPane.setPrefHeight(800);
        splitPane.getStyleClass().add("root-panel-split-pane");

        //headerView          = new HeaderView(rootPM);

        splitPaneVertical   = new SplitPane();
        splitPaneVertical.setOrientation(Orientation.VERTICAL);

        //- NewHeader_HydroToolbar
        newHeaderView       = new HeaderToolbar(rootPM);

        sideListView        = new SideListView(rootPM);
        contentView         = new ContentView(rootPM);
        dashboardView       = new DashboardView(rootPM);
        titleBoardView      = new TitleBoardView(rootPM);

        //- gummibaerenDashboard
        gummibaerenDashboard = new GummibaerenDashboard();
    }

    @Override
    public void layoutControls() {
        splitPane.getItems().addAll(new VBox(titleBoardView,sideListView), new VBox(dashboardView,contentView));
        splitPaneVertical.getItems().addAll(splitPane);
        //splitPaneVertical.getItems().addAll(splitPane,gummibaerenDashboard);

        setTop(newHeaderView);
        setCenter(splitPaneVertical);

//        setTop(newHeaderView);
//        setCenter(splitPane);
//        setBottom(bottomListView);

    }

    @Override
    public void setupValueChangedListeners() {
        rootPM.changeDesignProperty().addListener((observable, oldValue, newValue) -> {
            setBackgroundColors(newValue);
        });
    }


    @Override
    public void setupBindings() {

    }

    private void setBackgroundColors(boolean val){
        if(val)
        {
            String nightcss = "stylenight.css";
            //this.setStyle("-fx-background-color: #1D1D1D");
            contentView.getStylesheets().clear();
            contentView.addStylesheetFiles(nightcss);

            dashboardView.getStylesheets().clear();
            dashboardView.addStylesheetFiles(nightcss);

            sideListView.getStylesheets().clear();
            sideListView.addStylesheetFiles(nightcss);

            titleBoardView.getStylesheets().clear();
            titleBoardView.addStylesheetFiles(nightcss);

        }
        else
        {
            String daycss = "styleday.css";
            //this.setStyle("-fx-background-color: #FFFEFA");
            contentView.getStylesheets().clear();
            contentView.addStylesheetFiles(daycss);

            dashboardView.getStylesheets().clear();
            dashboardView.addStylesheetFiles(daycss);

            sideListView.getStylesheets().clear();
            sideListView.addStylesheetFiles(daycss);

            titleBoardView.getStylesheets().clear();
            titleBoardView.addStylesheetFiles(daycss);
        }
    }


}
