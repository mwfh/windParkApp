package windparkfx.view;


import javafx.scene.layout.VBox;
import windparkfx.presentationmodel.RootPM;
import windparkfx.view.NewHeader_Toolbar.HeaderToolbar;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import windparkfx.view.gummibaerenDashboard.GummibaerenDashboard;

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
        splitPane.setMinWidth(800);
        splitPane.setPrefWidth(1400);
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
        gummibaerenDashboard = new GummibaerenDashboard(rootPM);
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

    }

    @Override
    public void setupBindings() {

    }
}
