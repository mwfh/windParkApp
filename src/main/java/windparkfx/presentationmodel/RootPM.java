package windparkfx.presentationmodel;

import windparkfx.presentationmodel.redo_undo.AddCommand;
import windparkfx.presentationmodel.redo_undo.Command;
import windparkfx.presentationmodel.redo_undo.RemoveCommand;
import windparkfx.presentationmodel.redo_undo.ValueChangeCommand;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Mario Wettstein
 */

public class RootPM{
    private final StringProperty applicationTitle = new SimpleStringProperty("Wasserkraftwerk aus der Schweiz");
   // private final StringProperty greeting         = new SimpleStringProperty("Hello World!");

    //- FILES:
    private static final String FILE_NAME_MAIN = "/data/HYDRO_POWERSTATION.csv";
    private static final String FILE_NAME_SECOND = "/data/cantons.csv";
    private static final String DELIMITER = ";";

    //- INIT Wert
    private final IntegerProperty selectHydroId     = new SimpleIntegerProperty(-1);
    private final IntegerProperty selectCantonId    = new SimpleIntegerProperty(-1);

    //- Lists
    private final ObservableList<HydroDataPM> hydro_resultat  = FXCollections.observableArrayList();
    private final ObservableList<CantonDataPM> canton_resultat = FXCollections.observableArrayList();

    //- Proxys
    private HydroDataPM hydroProxy = new HydroDataPM();
    private CantonDataPM cantonProxy = new CantonDataPM();



    public RootPM() {

        hydro_resultat.addAll(Hydro_readFromFile());
        canton_resultat.addAll(Canton_readFromFile());

        undoDisabled.bind(Bindings.isEmpty(undoStack));
        redoDisabled.bind(Bindings.isEmpty(redoStack));


        refreshHydrosPerCantonList();

        selectHydroId.addListener(((observable, oldValue, newValue) -> {
            HydroDataPM oldHydroSelection = getHydroSelect(oldValue.intValue());
            HydroDataPM newHydroSelection = getHydroSelect(newValue.intValue());

            if(oldHydroSelection != null)
            {
                //- unbind
                unbindHydroData(oldHydroSelection);
                disableUndoSupport(oldHydroSelection);
            }

            if(newHydroSelection != null)
            {
                //- bind
                bindHydroData(newHydroSelection);
                enableUndoSupport(newHydroSelection);
            }
        }));
    }


    //- Bind Handling
    private void unbindHydroData(HydroDataPM oldHydroSelection)
    {
        hydroProxy.idProperty()             .unbindBidirectional(oldHydroSelection.idProperty());
        hydroProxy.nameProperty()           .unbindBidirectional(oldHydroSelection.nameProperty());
        hydroProxy.typeProperty()           .unbindBidirectional(oldHydroSelection.typeProperty());
        hydroProxy.siteProperty()           .unbindBidirectional(oldHydroSelection.siteProperty());
        hydroProxy.cantonProperty()         .unbindBidirectional(oldHydroSelection.cantonProperty());
        hydroProxy.maxWaterVolProperty()    .unbindBidirectional(oldHydroSelection.maxWaterVolProperty());
        hydroProxy.maxMWattPowerProperty()  .unbindBidirectional(oldHydroSelection.maxMWattPowerProperty());
        hydroProxy.firstStartDatProperty()  .unbindBidirectional(oldHydroSelection.firstStartDatProperty());
        hydroProxy.lastStartDatProperty()   .unbindBidirectional(oldHydroSelection.lastStartDatProperty());
        hydroProxy.latitudeProperty()       .unbindBidirectional(oldHydroSelection.latitudeProperty());
        hydroProxy.longitudeProperty()      .unbindBidirectional(oldHydroSelection.longitudeProperty());
        hydroProxy.statusProperty()         .unbindBidirectional(oldHydroSelection.statusProperty());
        hydroProxy.waterbodiesProperty()    .unbindBidirectional(oldHydroSelection.waterbodiesProperty());
        hydroProxy.imageUrlProperty()       .unbindBidirectional(oldHydroSelection.imageUrlProperty());

    }
    private void bindHydroData(HydroDataPM newHydroSelection)
    {
        hydroProxy.idProperty()             .bindBidirectional(newHydroSelection.idProperty());
        hydroProxy.nameProperty()           .bindBidirectional(newHydroSelection.nameProperty());
        hydroProxy.typeProperty()           .bindBidirectional(newHydroSelection.typeProperty());
        hydroProxy.siteProperty()           .bindBidirectional(newHydroSelection.siteProperty());
        hydroProxy.statusProperty()         .bindBidirectional(newHydroSelection.statusProperty());
        hydroProxy.cantonProperty()         .bindBidirectional(newHydroSelection.cantonProperty());
        hydroProxy.maxWaterVolProperty()    .bindBidirectional(newHydroSelection.maxWaterVolProperty());
        hydroProxy.maxMWattPowerProperty()  .bindBidirectional(newHydroSelection.maxMWattPowerProperty());
        hydroProxy.firstStartDatProperty()  .bindBidirectional(newHydroSelection.firstStartDatProperty());
        hydroProxy.lastStartDatProperty()   .bindBidirectional(newHydroSelection.lastStartDatProperty());
        hydroProxy.latitudeProperty()       .bindBidirectional(newHydroSelection.latitudeProperty());
        hydroProxy.longitudeProperty()      .bindBidirectional(newHydroSelection.longitudeProperty());
        hydroProxy.statusProperty()         .bindBidirectional(newHydroSelection.statusProperty());
        hydroProxy.waterbodiesProperty()    .bindBidirectional(newHydroSelection.waterbodiesProperty());
        hydroProxy.imageUrlProperty()       .bindBidirectional(newHydroSelection.imageUrlProperty());

    }


    public void refreshHydrosPerCantonList() {
        for(CantonDataPM canton : canton_resultat) {
            //- init countvalue
            double maxPower = 0.0;
            int anzahlHydros = 0;

            // Collect the values
            for(HydroDataPM h: hydro_resultat){
                try {
                    if(h.getCanton() != null && h.getCanton().equals(canton.getKurzForm())){ //find canton
                        if(h.getMaxMWattPower() != 0) { //- count MaxWatt
                            maxPower += h.getMaxMWattPower();
                        }
                        anzahlHydros++;
                    }
                }
                catch (Exception e)
                {
                    System.out.println("RootPM wirft einen Fehler beim Update der Übersichtsliste: " + e);
                }

            }

            //- Reset values
            canton.setTotalKWWatt(maxPower);
            canton.setAnzKWGesamt(anzahlHydros);
        }
    }

    //- all getters and setters
    public String getApplicationTitle() {
        return applicationTitle.get();
    }

    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }

    public HydroDataPM getHydroSelect(int id) {
        return hydro_resultat.stream()
                .filter(HydroPM -> HydroPM.getId() == id)
                .findAny().orElse(null);
    }

    private CantonDataPM getCantonSelect(String kname) {

        return canton_resultat.stream()
                .filter(HydroPM -> HydroPM.getKurzForm() == kname)
                .findAny().orElse(null);
    }

    public int getSelectHydroId() {
        return selectHydroId.get();
    }

    public IntegerProperty selectHydroIdProperty() {
        return selectHydroId;
    }

    public void setSelectHydroId(int selectHydroId) {
        this.selectHydroId.set(selectHydroId);
    }

    public int getSelectCantonId() {
        return selectCantonId.get();
    }

    public IntegerProperty selectCantonIdProperty() {
        return selectCantonId;
    }

    public void setSelectCantonId(int selectCantonId) {
        this.selectCantonId.set(selectCantonId);
    }

    public ObservableList<HydroDataPM> getHydro_resultat() {
        return hydro_resultat;
    }

    public ObservableList<CantonDataPM> getCanton_resultat() {
        return canton_resultat;
    }

    public HydroDataPM getHydroProxy() {
        return hydroProxy;
    }

    public void setHydroProxy(HydroDataPM hydroProxy) {
        this.hydroProxy = hydroProxy;
    }

    public CantonDataPM getCantonProxy() {
        return cantonProxy;
    }

    public void setCantonProxy(CantonDataPM cantonProxy) {
        this.cantonProxy = cantonProxy;
    }



    //- -------------------------------------------------------------------------
    //- Hydro-Data behandeln ####################################################

    private List<HydroDataPM> Hydro_readFromFile() {
        try (Stream<String> stream = getStreamOfLines(FILE_NAME_MAIN)) {
            return stream.skip(1)                                              // erste Zeile ist die Headerzeile; ueberspringen
                    .map(line -> new HydroDataPM(line.split(DELIMITER, 14))) // aus jeder Zeile ein Objekt machen
                    .collect(Collectors.toList());                        // alles aufsammeln
        }
    }

    public void hydro_save() {
        //System.out.println("Datei Hydro wird versucht zu schreiben");
        try (BufferedWriter writer = Files.newBufferedWriter(getPath(FILE_NAME_MAIN))) {
            writer.write("ENTITY_ID;NAME;TYPE;SITE;CANTON;MAX_WATER_VOLUME_M3_S;MAX_POWER_MW;START_OF_OPERATION_FIRST;START_OF_OPERATION_LAST;LATITUDE;LONGITUDE;STATUS;WATERBODIES;IMAGE_URL");
            writer.newLine();
            hydro_resultat.stream()
                    .map(hydro_resultat -> hydro_resultat.Hydro_InfoAsLine(DELIMITER))
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    });
            showInfoDialog("Daten Speicherung", "Alle Wasserwerke wurden gespeichert.");
        } catch (IOException e) {
            throw new IllegalStateException("save failed");
        }
    }


    public ObservableList<HydroDataPM> getHydro_result() {
        return hydro_resultat;
    }

    public void hydro_add(int last_id)
    {
        int new_id = hydro_resultat.size();
        HydroDataPM newHydro = new HydroDataPM(new_id);
        getHydro_result().add(newHydro);
        setSelectHydroId(new_id);

        redoStack.clear();
        undoStack.add(0, new AddCommand(this, newHydro, hydro_resultat.size() - 1));
        showInfoDialog("Neues Kraftwerk", "Kraftwerk wurde hinzugefügt");
    }

    public void hydro_delete()
    {
        HydroDataPM hydrosel = hydro_resultat.stream().filter(hydro -> hydro.getId() == getSelectHydroId()).findFirst().get();
        int currentPosition = hydro_resultat.indexOf(hydrosel);

        removeFromList(hydrosel);
        redoStack.clear();
        undoStack.add(0, new RemoveCommand(this, hydrosel, currentPosition));

        showInfoDialog("Kraftwerk Änderung", "Das Kraftwerk <" +hydrosel.getName()+ "> wurde gelöscht");
    }

    public void load_first_hydro_entry()
    {
        setSelectHydroId(getHydro_result().stream()
                .sorted(Comparator.comparingInt(value -> value.getId()))
                .mapToInt(value -> value.getId())
                .min()
                .getAsInt());

    }

    //- Hydro-Data behandeln ####################################################
    //- -------------------------------------------------------------------------

    //- -------------------------------------------------------------------------
    //- Canton-Data behandeln ####################################################


    public void canton_save() {
        //System.out.println("Datei Canton wird versucht zu schreiben");
        try (BufferedWriter writer = Files.newBufferedWriter(getPath(FILE_NAME_SECOND))) {
            writer.write("Kanton;K√ºrzel;Kantonsnummer;Standesstimme;Beitritt;Hauptort;Einwohner;Ausl√§nder;Fl√§che;Einwohnerdichte;Gemeinden;Amtssprache");
            writer.newLine();
            canton_resultat.stream()
                    .map(canton_resultat -> canton_resultat.Canton_InfoAsLine(DELIMITER))
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    });
            System.out.println("Datei Canton wurde geschrieben");
        } catch (IOException e) {
            throw new IllegalStateException("save failed");
        }
    }


    public ObservableList<CantonDataPM> getCanton_result() {
        return canton_resultat;
    }

    private List<CantonDataPM> Canton_readFromFile() {
        try (Stream<String> stream = getStreamOfLines(FILE_NAME_SECOND)) {
            return stream.skip(1)                                              // erste Zeile ist die Headerzeile; ueberspringen
                    .map(line -> new CantonDataPM(line.split(DELIMITER, 12))) // aus jeder Zeile ein Objekt machen
                    .collect(Collectors.toList());                        // alles aufsammeln
        }
    }

    //- Canton-Data behandeln ####################################################
    //- -------------------------------------------------------------------------

    //- -------------------------------------------------------------------------
    //- Default Methoden ########################################################


    private Stream<String> getStreamOfLines(String fileName) {
        try {
            return Files.lines(getPath(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Path getPath(String fileName)  {
        try {
            return Paths.get(getClass().getResource(fileName).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    //- Default Methoden ########################################################
    //- -------------------------------------------------------------------------



    //- ########################################################################################
    //- redo undo
    private final ObservableList<Command> undoStack     = FXCollections.observableArrayList();
    private final ObservableList<Command> redoStack     = FXCollections.observableArrayList();

    private final BooleanProperty undoDisabled          = new SimpleBooleanProperty();
    private final BooleanProperty redoDisabled          = new SimpleBooleanProperty();


    private final ChangeListener<Object> propertyChangeListenerForUndoSupport = (observable, oldValue, newValue) -> {
        redoStack.clear();
        undoStack.add(0, new ValueChangeCommand(RootPM.this, (Property) observable, oldValue, newValue));
    };


    public void undo() {
        if (undoStack.isEmpty()) {
            return;
        }
        Command cmd = undoStack.get(0);
        undoStack.remove(0);
        redoStack.add(0, cmd);

        cmd.undo();
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            return;
        }
        Command cmd = redoStack.get(0);
        redoStack.remove(0);
        undoStack.add(0, cmd);

        cmd.redo();
    }

    public void addToList(int position, HydroDataPM hydro){
        hydro_resultat.add(position, hydro);
        setSelectHydroId(hydro.getId());
    }

    public void removeFromList(HydroDataPM hydro){
        unbindHydroData(hydro);
        disableUndoSupport(hydro);

        hydro_resultat.remove(hydro);

        if(!hydro_resultat.isEmpty()){
            setSelectHydroId(hydro_resultat.get(0).getId());
        }
    }

    public void setPropertyValue(Property property, Object newValue){
        property.removeListener(propertyChangeListenerForUndoSupport);
        property.setValue(newValue);
        property.addListener(propertyChangeListenerForUndoSupport);
    }

    private void enableUndoSupport(HydroDataPM hydro) {
        hydro.idProperty()              .addListener(propertyChangeListenerForUndoSupport);
        hydro.nameProperty()            .addListener(propertyChangeListenerForUndoSupport);
        hydro.typeProperty()            .addListener(propertyChangeListenerForUndoSupport);
        hydro.siteProperty()            .addListener(propertyChangeListenerForUndoSupport);
        hydro.cantonProperty()          .addListener(propertyChangeListenerForUndoSupport);
        hydro.maxWaterVolProperty()     .addListener(propertyChangeListenerForUndoSupport);
        hydro.maxMWattPowerProperty()   .addListener(propertyChangeListenerForUndoSupport);
        hydro.firstStartDatProperty()   .addListener(propertyChangeListenerForUndoSupport);
        hydro.lastStartDatProperty()    .addListener(propertyChangeListenerForUndoSupport);
        hydro.latitudeProperty()        .addListener(propertyChangeListenerForUndoSupport);
        hydro.longitudeProperty()       .addListener(propertyChangeListenerForUndoSupport);
        hydro.statusProperty()          .addListener(propertyChangeListenerForUndoSupport);
        hydro.waterbodiesProperty()     .addListener(propertyChangeListenerForUndoSupport);
        hydro.imageUrlProperty()        .addListener(propertyChangeListenerForUndoSupport);
    }


    private void disableUndoSupport(HydroDataPM hydro) {
        hydro.idProperty()              .removeListener(propertyChangeListenerForUndoSupport);
        hydro.nameProperty()            .removeListener(propertyChangeListenerForUndoSupport);
        hydro.typeProperty()            .removeListener(propertyChangeListenerForUndoSupport);
        hydro.siteProperty()            .removeListener(propertyChangeListenerForUndoSupport);
        hydro.cantonProperty()          .removeListener(propertyChangeListenerForUndoSupport);
        hydro.maxWaterVolProperty()     .removeListener(propertyChangeListenerForUndoSupport);
        hydro.maxMWattPowerProperty()   .removeListener(propertyChangeListenerForUndoSupport);
        hydro.firstStartDatProperty()   .removeListener(propertyChangeListenerForUndoSupport);
        hydro.lastStartDatProperty()    .removeListener(propertyChangeListenerForUndoSupport);
        hydro.latitudeProperty()        .removeListener(propertyChangeListenerForUndoSupport);
        hydro.longitudeProperty()       .removeListener(propertyChangeListenerForUndoSupport);
        hydro.statusProperty()          .removeListener(propertyChangeListenerForUndoSupport);
        hydro.waterbodiesProperty()     .removeListener(propertyChangeListenerForUndoSupport);
        hydro.imageUrlProperty()        .removeListener(propertyChangeListenerForUndoSupport);
    }


    //- ######################################################################
    //- Info Dialog for Save, Delete
    public void showInfoDialog(String header, String message, String submessage)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(header);
        alert.setHeaderText(message);
        if(submessage != "")
        {
            alert.setContentText(submessage);
        }


        alert.showAndWait();
    }

    public void showInfoDialog(String header, String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(header);
        alert.setHeaderText(message);

        alert.showAndWait();
    }



    //- ######################################################################
    //- Search List
    private StringProperty filter = new SimpleStringProperty();

    public String getFilter() {
        return filter.get();
    }

    public StringProperty filterProperty() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter.set(filter);
    }

    public ObservableList<HydroDataPM> getHydroListFilter() {
        if (filter == null || filter.getValue() == null || filter.getValue().equals("")) {
            return hydro_resultat;
        }
        return hydro_resultat.stream()
                .filter(x -> x.getName().toLowerCase().contains(getFilter().toLowerCase()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }


}
