package windparkfx.view;

import windparkfx.presentationmodel.RootPM;
import windparkfx.view.NewHeader_HydroToolbar.HydroToolbar;
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
    private HeaderView headerView; //- Own Header

    //- NewHeader_HydroToolbar
    private HydroToolbar newHeaderView;


    private ContentView         contentView;
    private SideListView        sideListView;
    private BottomListView      bottomListView;

    private SplitPane splitPane;
    private SplitPane splitPaneVertical;

    private Label test;

    private Button button;

    public RootPanel(RootPM model) {
        this.rootPM = model;
        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("style.css");

    }

    @Override
    public void initializeControls() {
        splitPane           = new SplitPane();
        headerView          = new HeaderView(rootPM);

        splitPaneVertical   = new SplitPane();
        splitPaneVertical.setOrientation(Orientation.VERTICAL);

        //- NewHeader_HydroToolbar
        newHeaderView       = new HydroToolbar(rootPM);

        sideListView        = new SideListView(rootPM);
        contentView         = new ContentView(rootPM);
        bottomListView      = new BottomListView(rootPM);
        bottomListView.setMaxSize(Double.MAX_VALUE, 400);

    }

    @Override
    public void layoutControls() {
        setStyle("-fx-background-color: deepskyblue");
        splitPane.getItems().addAll(sideListView, contentView);
        //splitPaneVertical.getItems().addAll(splitPane,bottomListView);
        splitPaneVertical.getItems().addAll(splitPane);
        setTop(newHeaderView);
        setCenter(splitPaneVertical);
//        setTop(newHeaderView);
//        setCenter(splitPane);
//        setBottom(bottomListView);
        //setTop(headerView);
    }

    @Override
    public void setupValueChangedListeners() {

    }

    @Override
    public void setupBindings() {

    }
}
