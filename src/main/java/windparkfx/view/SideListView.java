package windparkfx.view;

import windparkfx.presentationmodel.RootPM;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import windparkfx.presentationmodel.WindDataPM;

/**
 * @author Mario Wettstein
 */

public class SideListView extends VBox implements ViewMixin {
    private final RootPM rootPM;

    private TableView<WindDataPM> windTable;

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
        windTable = initializeResultatTabelle();
        windTable.setEditable(true);
    }

    @Override
    public void layoutControls() {

        //HBox.setHgrow(hydroTable, Priority.ALWAYS);
        setVgrow(windTable, Priority.ALWAYS);
        getChildren().addAll(windTable);
    }

    @Override
    public void setupBindings() {

    }

    @Override
    public void setupEventHandlers() {
    }

    private TableView<WindDataPM> initializeResultatTabelle() {
        TableView<WindDataPM> tableView = new TableView<>(rootPM.getWind_result()); //- Hier muss angegeben werden: was angezeigt werden soll. Somit vom Model!!

        //- Init von Kraftwerk ID Spalte
        TableColumn<WindDataPM, String> idKW = new TableColumn<>("ID");
        idKW.setCellValueFactory(cell -> cell.getValue().idProperty().asString());

        //- Init von Kraftwerk Locationname Spalte
        TableColumn<WindDataPM, String> locName = new TableColumn<>("Location Name");
        locName.setCellValueFactory(cell -> cell.getValue().locationNameProperty());

        //- Init von Kraftwerk Status Spalte
        TableColumn<WindDataPM, String> state = new TableColumn<>("Status");
        state.setCellValueFactory(cell -> cell.getValue().statusProperty());

        //- Init von Kanton Kanton Spalte
        TableColumn<WindDataPM, String> nameCanton = new TableColumn<>("Kanton");
        nameCanton.setCellValueFactory(cell -> cell.getValue().cantonProperty());

        //- Init von MW 15
        TableColumn<WindDataPM, String> megaWatt15 = new TableColumn<>("MW 2015");
        megaWatt15.setCellValueFactory(cell -> cell.getValue().mw15Property().asString());

        //- Init von MW 16
        TableColumn<WindDataPM, String> megaWatt16 = new TableColumn<>("MW 2016");
        megaWatt16.setCellValueFactory(cell -> cell.getValue().mw16Property().asString());

        //- Init von MW 17
        TableColumn<WindDataPM, String> megaWatt17 = new TableColumn<>("MW 2017");
        megaWatt17.setCellValueFactory(cell -> cell.getValue().mw17Property().asString());

        //- Init von MW 17
        TableColumn<WindDataPM, String> megaWatt18 = new TableColumn<>("MW 2018");
        megaWatt18.setCellValueFactory(cell -> cell.getValue().mw18Property().asString());

        //- Init von MW Total
        TableColumn<WindDataPM, String> megaWattTotal = new TableColumn<>("MW Total");
        megaWattTotal.setCellValueFactory(cell -> cell.getValue().totalMegaWattProperty().asString());

        //- Spalten aneinander reihen
        tableView.getColumns().addAll(idKW,locName, nameCanton, state , megaWatt15, megaWatt16, megaWatt17, megaWatt18, megaWattTotal);
        tableView.setItems(rootPM.getWindListFilter());
        tableView.getSortOrder().setAll(locName);

        return tableView;
    }


    @Override
    public void setupValueChangedListeners() {
        windTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try
            {
                rootPM.setSelectWindID(newValue.getId());
            }
            catch (Exception e)
            {
                System.out.println("ChangeListener von ListView.java wirft einen Fehler: " + e);
            }

        });

        rootPM.selectWindIDProperty() .addListener((observable, oldValue, newValue) -> {
            WindDataPM wind = rootPM.getWindSelect(newValue.intValue());
            windTable.getSelectionModel().select(wind);

            //- Scroll to Selected
            //hydroTable.scrollTo(hydro);
        });

        //- Search
        rootPM.filterProperty().addListener((observable, oldValue, newValue) -> {
            windTable.itemsProperty().set(rootPM.getWindListFilter());
        });
    }
}
