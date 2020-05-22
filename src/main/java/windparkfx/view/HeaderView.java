package windparkfx.view;

import windparkfx.presentationmodel.RootPM;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.util.Comparator;

/**
 * @author Mario Wettstein
 */


public class HeaderView extends GridPane implements ViewMixin{

    private final RootPM rootPM;

    private Button save;
    private Button add;
    private Button delete;
    private Button back;
    private Button forward;
    private Label applicationText;
    private TextField search;

    private Button button;

    public HeaderView(RootPM model) {
        this.rootPM = model;

        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("style.css");
    }

    @Override
    public void initializeControls() {
        save = new Button("save");
        add = new Button(" + ");
        delete = new Button(" X ");
        back = new Button("<--");
        forward = new Button("-->");
        applicationText = new Label("Wasserkraftwerke in der Schweiz");
        search = new TextField("Search");

        //- Button maximale Grösse
        save.setMaxWidth(Double.MAX_VALUE);
        add.setMaxWidth(Double.MAX_VALUE);
        delete.setMaxWidth(Double.MAX_VALUE);
        back.setMaxWidth(Double.MAX_VALUE);
        forward.setMaxWidth(Double.MAX_VALUE);

        GridPane.setHalignment(save, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(save, VPos.CENTER); // To align vertically in the cell

        GridPane.setHalignment(add, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(add, VPos.CENTER); // To align vertically in the cell

        GridPane.setHalignment(delete, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(delete, VPos.CENTER); // To align vertically in the cell

        GridPane.setHalignment(back, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(back, VPos.CENTER); // To align vertically in the cell

        GridPane.setHalignment(forward, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(forward, VPos.CENTER); // To align vertically in the cell

        GridPane.setHalignment(applicationText, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(applicationText, VPos.CENTER); // To align vertically in the cell
        GridPane.setHalignment(search, HPos.RIGHT); // To align horizontally in the cell
        GridPane.setValignment(search, VPos.CENTER); // To align vertically in the cell

        //setGridLinesVisible(true); //- Grind einblenden
    }

    @Override
    public void layoutControls() {

        ColumnConstraints borderSide = new ColumnConstraints(10,10, Double.MAX_VALUE);
        ColumnConstraints placeButton = new ColumnConstraints(50,50, Double.MAX_VALUE);
        ColumnConstraints spaceButton = new ColumnConstraints(5,5,5);
        ColumnConstraints placeText = new ColumnConstraints(300,300, Double.MAX_VALUE);
        ColumnConstraints spacecolumn = new ColumnConstraints(10,10, Double.MAX_VALUE);
        ColumnConstraints placeSearch = new ColumnConstraints(200,200, Double.MAX_VALUE);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        placeText.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(placeButton,spaceButton,placeButton,spaceButton,placeButton,spaceButton,placeButton,spaceButton,placeButton, spacecolumn, placeText, spacecolumn, placeSearch);

        RowConstraints rc = new RowConstraints();
        RowConstraints borderTopDown = new RowConstraints(15,15, Double.MAX_VALUE);
        RowConstraints inputHead = new RowConstraints(30,30, Double.MAX_VALUE);

        rc.setVgrow(Priority.ALWAYS);
        getRowConstraints().addAll( inputHead);

        add(save, 0, 0, 1, 1);
        add(add, 2, 0, 1, 1);
        add(delete, 4, 0, 1, 1);
        add(back, 6, 0, 1, 1);
        add(forward, 8, 0, 1, 1);
        add(applicationText, 10, 0, 1, 1);
        add(search, 12, 0, 1, 1);

    }

    @Override
    public void setupBindings() {

    }

    @Override
    public void setupEventHandlers() {
        save.setOnAction(event -> rootPM.hydro_save());

        add.setOnAction(event -> rootPM.hydro_add(rootPM.getHydro_result().stream()
                                                    .sorted(Comparator.comparingInt(value -> value.getId()))
                                                    .mapToInt(value -> value.getId())
                                                    .max()
                                                    .getAsInt()));


        delete.setOnAction(event -> rootPM.hydro_delete());

        back.setOnAction(event -> rootPM.undo());

        forward.setOnAction(event -> rootPM.redo());
    }
}
