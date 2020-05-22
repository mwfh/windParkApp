package windparkfx.view;

import windparkfx.presentationmodel.HydroDataPM;
import windparkfx.presentationmodel.RootPM;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Mario Wettstein
 */

public class SideListView extends VBox implements ViewMixin {
    private final RootPM rootPM;

    private TableView<HydroDataPM> hydroTable;

    public SideListView(RootPM model) {
        this.rootPM = model;
        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("style.css");
    }

    @Override
    public void initializeControls() {
        hydroTable = initializeResultatTabelle();
        hydroTable.setEditable(true);
    }

    @Override
    public void layoutControls() {

        //HBox.setHgrow(hydroTable, Priority.ALWAYS);
        setVgrow(hydroTable, Priority.ALWAYS);
        getChildren().addAll(hydroTable);
    }

    @Override
    public void setupBindings() {

    }

    @Override
    public void setupEventHandlers() {
    }

    private TableView<HydroDataPM> initializeResultatTabelle() {
        TableView<HydroDataPM> tableView = new TableView<>(rootPM.getHydro_result()); //- Hier muss angegeben werden: was angezeigt werden soll. Somit vom Model!!

        //- Init von Kraftwerk ID Spalte
        TableColumn<HydroDataPM, String> idKW = new TableColumn<>("ID");
        idKW.setCellValueFactory(cell -> cell.getValue().idProperty().asString());

        //- Init von Kraftwerk Namen Spalte
        TableColumn<HydroDataPM, String> nameKW = new TableColumn<>("Name");
        nameKW.setCellValueFactory(cell -> cell.getValue().nameProperty());

        //- Init von Kanton Namen Spalte
        TableColumn<HydroDataPM, String> nameCanton = new TableColumn<>("Kanton");
        nameCanton.setCellValueFactory(cell -> cell.getValue().cantonProperty());

        //- Init von MWatt
        TableColumn<HydroDataPM, String> megaWatt = new TableColumn<>("Leistung (MW)");
        megaWatt.setCellValueFactory(cell -> cell.getValue().maxMWattPowerProperty());


        //- Init von MWatt
        TableColumn<HydroDataPM, Number> startDate = new TableColumn<>("Erste in Betriebnahme");
        startDate.setCellValueFactory(cell -> cell.getValue().firstStartDatProperty());

        //- Spalten aneinander reihen
        tableView.getColumns().addAll(idKW,nameKW, nameCanton , megaWatt, startDate);
        tableView.setItems(rootPM.getHydroListFilter());
        tableView.getSortOrder().setAll(nameKW);

        return tableView;
    }


    @Override
    public void setupValueChangedListeners() {
        hydroTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try
            {
                rootPM.setSelectHydroId(newValue.getId());
            }
            catch (Exception e)
            {
                System.out.println("ChangeListener von ListView.java wirft einen Fehler: " + e);
            }

        });

        rootPM.selectHydroIdProperty() .addListener((observable, oldValue, newValue) -> {
            HydroDataPM hydro = rootPM.getHydroSelect(newValue.intValue());
            hydroTable.getSelectionModel().select(hydro);

            //- Scroll to Selected
            //hydroTable.scrollTo(hydro);
        });

        //- Search
        rootPM.filterProperty().addListener((observable, oldValue, newValue) -> {
            hydroTable.itemsProperty().set(rootPM.getHydroListFilter());
        });
    }
}
