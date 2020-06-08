package windparkfx.view;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import windparkfx.presentationmodel.RootPM;
import windparkfx.view.GummibaerenDashboard.GummibaerenDashboard;

/**
 * @author Mario Wettstein
 */

public class DashboardView extends GridPane implements ViewMixin{
    private final RootPM rootPM;

    //- Lable
    private Label titleVillage;
    private Label titleCanton;
    private Label titleInstalKW;
    private Label titleMWattTotal;
    private Label titleImgUrl;

    //- Image
    private ImageView imageView;

    //- gummibaerenDashboard
    private GummibaerenDashboard gummibaerenDashboard;

    public DashboardView(RootPM model) {
        this.rootPM = model;

        init();
    }

    @Override
    public void initializeSelf() {
//        addStylesheetFiles("style.css");
        addStylesheetFiles(rootPM.getStyleChoose());
        getStyleClass().add("dashboard-view");
    }

    @Override
    public void initializeControls() {

        titleVillage        = new Label();
        titleVillage.setWrapText(true);

        titleVillage.getStyleClass().add("labeltitletext");

        titleCanton         = new Label();
        titleCanton.getStyleClass().add("dashboardText");

        titleInstalKW       = new Label();
        titleInstalKW.getStyleClass().add("dashboardText");

        titleMWattTotal     = new Label();
        titleMWattTotal.getStyleClass().add("dashboardText");

        titleImgUrl         = new Label();

        imageView =         new ImageView();

        gummibaerenDashboard = new GummibaerenDashboard();

       //setGridLinesVisible(true); //- Grind einblenden
    }

    @Override
    public void layoutControls() {
        ColumnConstraints Space                         = new ColumnConstraints(10,10, Double.MAX_VALUE);
        ColumnConstraints colHead01                     = new ColumnConstraints(160,160, 160);
        ColumnConstraints colHead02                     = new ColumnConstraints(100,100, 100);
        ColumnConstraints colHead03                     = new ColumnConstraints(320,320, 320);

        //setMinSize(600,300);
        setMinSize(600,250);
        setMaxSize(1200,300);
        getColumnConstraints().addAll(Space,colHead01, colHead02, colHead03, Space);

        RowConstraints SpaceTop           = new RowConstraints(10,10, Double.MAX_VALUE);
        RowConstraints rowHead01           = new RowConstraints(50,50, Double.MAX_VALUE);
        RowConstraints rowHead02           = new RowConstraints(50,50, Double.MAX_VALUE);
        RowConstraints rowHead03           = new RowConstraints(115,115, Double.MAX_VALUE);
        RowConstraints rowHead04           = new RowConstraints(35,35, Double.MAX_VALUE);
        RowConstraints rowHead05           = new RowConstraints(35,35, Double.MAX_VALUE);

//        RowConstraints gummibaerenRow             = new RowConstraints(220,220, Double.MAX_VALUE);

        getRowConstraints().addAll(SpaceTop, rowHead01, rowHead02, rowHead03, rowHead04, rowHead05, SpaceTop);
        //getRowConstraints().addAll(SpaceTop, rowHead01, rowHead02, rowHead03, rowHead04, rowHead05, SpaceTop, gummibaerenRow, SpaceTop);

        //- Head
        add(titleVillage, 1, 1, 2, 1);

        //add(labOrt, 1,1,1,1);
        add(titleCanton, 1, 2, 1, 1);

        add(titleInstalKW, 1, 4, 1, 1);
        add(titleMWattTotal, 1, 5, 2,1);

        //- Image
        add(imageView, 3, 1, 1,5);

        //- Gummibaeren Dashboard
        //add(gummibaerenDashboard, 1, 7,3 , 1);

    }

    @Override
    public void setupBindings() {

        titleVillage.textProperty()                 .bind(rootPM.getWindProxy().communesProperty());
        titleCanton.textProperty()                  .bind(rootPM.getWindProxy().cantonProperty());
        titleInstalKW.textProperty()                .bind(Bindings.concat(rootPM.getWindProxy().kwIstallProperty().asString(), " kW"));
        titleMWattTotal.textProperty()              .bind(Bindings.concat(rootPM.getWindProxy().totalMegaWattProperty().asString(), " MWh"));
        titleImgUrl.textProperty()                  .bind(rootPM.getWindProxy().imageUrlProperty());

        //- Image Input
        imageView.setFitHeight(280);
        imageView.setFitWidth(320);
        imageView.imageProperty()               .bind(rootPM.getWindProxy().getImageView().imageProperty());

//        gummibaerenDashboard.productionValue1Property().bindBidirectional(rootPM.getWindProxy().mw15Property());
//        gummibaerenDashboard.productionValue2Property().bindBidirectional(rootPM.getWindProxy().mw16Property());
//        gummibaerenDashboard.productionValue3Property().bindBidirectional(rootPM.getWindProxy().mw17Property());
//        gummibaerenDashboard.productionValue4Property().bindBidirectional(rootPM.getWindProxy().mw18Property());
    }

    @Override
    public void setupEventHandlers() {

        titleImgUrl.textProperty()                .addListener((observable, oldValue, newValue) -> {
            imageView.imageProperty()           .unbind();
            imageView.imageProperty()           .bind(rootPM.getWindProxy().getImageView().imageProperty());
        });

    }

}
