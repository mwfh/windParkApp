package windparkfx.view;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import windparkfx.presentationmodel.RootPM;

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


    public DashboardView(RootPM model) {
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
        setStyle("-fx-background-color:#1D1D1D; ");

        titleVillage        = new Label();
        titleVillage.setId("labeltitletext");

        titleCanton         = new Label();
        titleCanton.setId("dashboardText");

        titleInstalKW       = new Label();
        titleInstalKW.setId("dashboardText");

        titleMWattTotal     = new Label();
        titleMWattTotal.setId("dashboardText");

        titleImgUrl         = new Label();

        imageView =         new ImageView();


       //setGridLinesVisible(true); //- Grind einblenden
    }

    @Override
    public void layoutControls() {
        ColumnConstraints Space                 = new ColumnConstraints(10,10, Double.MAX_VALUE);
        ColumnConstraints colHead01             = new ColumnConstraints(250,250, Double.MAX_VALUE);
        ColumnConstraints colHead02             = new ColumnConstraints(100,100, Double.MAX_VALUE);
        ColumnConstraints colHead03             = new ColumnConstraints(328,328, Double.MAX_VALUE);

        setMinSize(600,300);
        setMaxSize(1200,500);
        getColumnConstraints().addAll(Space,colHead01, colHead02, colHead03, Space);

        RowConstraints SpaceTop           = new RowConstraints(10,10, Double.MAX_VALUE);
        RowConstraints rowHead01           = new RowConstraints(50,50, Double.MAX_VALUE);
        RowConstraints rowHead02           = new RowConstraints(50,50, Double.MAX_VALUE);
        RowConstraints rowHead03           = new RowConstraints(115,115, Double.MAX_VALUE);
        RowConstraints rowHead04           = new RowConstraints(35,35, Double.MAX_VALUE);
        RowConstraints rowHead05           = new RowConstraints(35,35, Double.MAX_VALUE);


        getRowConstraints().addAll(SpaceTop, rowHead01, rowHead02, rowHead03, rowHead04, rowHead05, SpaceTop);

        //- Head
        add(titleVillage, 1, 1, 2, 1);

        //add(labOrt, 1,1,1,1);
        add(titleCanton, 1, 2, 1, 1);

        add(titleInstalKW, 1, 4, 1, 1);
        add(titleMWattTotal, 1, 5, 1,1);

        //- Image
        add(imageView, 3, 1, 1,5);



    }

    @Override
    public void setupBindings() {

        titleVillage.textProperty()                 .bind(rootPM.getWindProxy().communesProperty());
        titleCanton.textProperty()                  .bind(rootPM.getWindProxy().cantonProperty());
        titleInstalKW.textProperty()                .bind(rootPM.getWindProxy().kwIstallProperty().asString());
        titleMWattTotal.textProperty()              .bind(rootPM.getWindProxy().totalMegaWattProperty().asString());
        titleImgUrl.textProperty()                  .bind(rootPM.getWindProxy().imageUrlProperty());

        //- Image Input
        imageView.setFitHeight(280);
        imageView.setFitWidth(320);
        imageView.imageProperty()               .bind(rootPM.getWindProxy().getImageView().imageProperty());
    }

    @Override
    public void setupEventHandlers() {

        titleImgUrl.textProperty()                .addListener((observable, oldValue, newValue) -> {
            imageView.imageProperty()           .unbind();
            imageView.imageProperty()           .bind(rootPM.getWindProxy().getImageView().imageProperty());
        });

    }



}
