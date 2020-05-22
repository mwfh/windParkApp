package windparkfx.view;


import windparkfx.presentationmodel.CantonDataPM;
import windparkfx.presentationmodel.RootPM;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Mario Wettstein
 */

public class BottomListView extends VBox implements ViewMixin{

    private final RootPM rootPM;

    private TableView<CantonDataPM> cantonTable;

    public BottomListView(RootPM model) {
        this.rootPM = model;
        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("style.css");
    }


    @Override
    public void initializeControls() {
       cantonTable = initializeResultatTabelle();

    }

    @Override
    public void layoutControls() {
        setVgrow(cantonTable, Priority.SOMETIMES);

        getChildren().addAll(cantonTable);
    }

    @Override
    public void setupBindings() {
    }

    @Override
    public void setupEventHandlers() {
    }

    private TableView<CantonDataPM> initializeResultatTabelle() {
        TableView<CantonDataPM> tableView = new TableView<>(rootPM.getCanton_result()); //- Hier muss angegeben werden: was angezeigt werden soll. Somit vom Model!!

        //- Init von Namen Spalte
        TableColumn<CantonDataPM, String> nameCanton = new TableColumn<>("Kanton");
        nameCanton.setCellValueFactory(cell -> cell.getValue().nameProperty());

        //- Init Gesamtleistung
        TableColumn<CantonDataPM, Number> gesamtWatt = new TableColumn<>("Gesamtleistung MW");
        gesamtWatt.setCellValueFactory(cell -> cell.getValue().totalKWWattProperty());

        //- Init von Anzal KW
        TableColumn<CantonDataPM, Number> anzKW = new TableColumn<>("Anzahl Kraftwerke");
        anzKW.setCellValueFactory(cell -> cell.getValue().anzKWGesamtProperty());



        //- Spalten aneinander reihen
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(nameCanton, gesamtWatt,anzKW);

        tableView.setItems(rootPM.getCanton_result());
        tableView.getSortOrder().setAll(nameCanton);

        return tableView;
    }
}
