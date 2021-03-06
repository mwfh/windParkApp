package windparkfx.view;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import windparkfx.presentationmodel.RootPM;

/**
 * @author Mario Wettstein
 */

public class TitleBoardView extends GridPane implements ViewMixin{
    private final RootPM rootPM;

    //- Lable
    private Label titleApp;
    private Label titleAllWind;
    private Label titleAllWindKW;

    private Label labelAllWind;
    private Label labelAllWindKW;


    public TitleBoardView(RootPM model) {
        this.rootPM = model;

        init();

    }

    @Override
    public void initializeSelf() {
//        addStylesheetFiles("style.css");
        addStylesheetFiles(rootPM.getStyleChoose());
        getStyleClass().add("title-board-view");
    }

    @Override
    public void initializeControls() {

        titleApp        = new Label("Schweizer Windparks");
        titleApp.getStyleClass().add("title-board-text-app");

        titleAllWind         = new Label("Windräder");
        titleAllWind.getStyleClass().add("title-board-text");

        titleAllWindKW       = new Label("Gesamtproduktion");
        titleAllWindKW.getStyleClass().add("title-board-text");

        labelAllWind     = new Label();
        labelAllWind.getStyleClass().add("title-board-text");

        labelAllWindKW     = new Label();
        labelAllWindKW.getStyleClass().add("title-board-text");

       //setGridLinesVisible(true); //- Grind einblenden
    }

    @Override
    public void layoutControls() {
        ColumnConstraints Space                 = new ColumnConstraints(10,10, Double.MAX_VALUE);
        ColumnConstraints colHead01             = new ColumnConstraints(250,250, Double.MAX_VALUE);
        ColumnConstraints colHead02             = new ColumnConstraints(100,450, Double.MAX_VALUE);

        setMinSize(300,150);
        setMaxSize(1000,200);
        getColumnConstraints().addAll(Space,colHead01, colHead02, Space);

        RowConstraints SpaceTop           = new RowConstraints(10,10, Double.MAX_VALUE);
        RowConstraints rowHead01           = new RowConstraints(50,50, Double.MAX_VALUE);
        RowConstraints rowHead02           = new RowConstraints(50,50, Double.MAX_VALUE);
        RowConstraints rowHead03           = new RowConstraints(50,50, Double.MAX_VALUE);

        getRowConstraints().addAll(SpaceTop, rowHead01, rowHead02, rowHead03, SpaceTop);

        //- Head
        add(titleApp, 1, 1, 2, 1);
        add(titleAllWind, 1, 2, 1, 1);
        add(titleAllWindKW, 1, 3, 1, 1);
        add(labelAllWind, 2, 2, 1,1);
        add(labelAllWindKW, 2, 3, 1,1);



    }

    @Override
    public void setupBindings() {
        labelAllWind.textProperty()             .bind(rootPM.getWindProxy().anzKWGesamtProperty().asString());
        labelAllWindKW.textProperty()           .bind(Bindings.concat(rootPM.getWindProxy().totalMegaWattAllProperty().asString()," MWh"));
    }

    @Override
    public void setupEventHandlers() {
    }



}
