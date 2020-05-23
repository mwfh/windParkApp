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
    private final StringProperty applicationTitle = new SimpleStringProperty("Windkraftwerke aus der Schweiz");
   // private final StringProperty greeting         = new SimpleStringProperty("Hello World!");

    //- FILES:
    private static final String FILE_NAME_MAIN = "/data/WINDPARK.csv";
    private static final String FILE_NAME_SECOND = "/data/cantons.csv";
    private static final String DELIMITER = ";";

    //- INIT Wert
    private final IntegerProperty selectHydroId     = new SimpleIntegerProperty(-1);
    private final IntegerProperty selectCantonId    = new SimpleIntegerProperty(-1);
    private final IntegerProperty selectWindID      = new SimpleIntegerProperty(-1);

    //- Lists
    private final ObservableList<CantonDataPM> canton_resultat = FXCollections.observableArrayList();
    private final ObservableList<WindDataPM>   wind_resultat     = FXCollections.observableArrayList();

    //- Proxys
    private CantonDataPM cantonProxy = new CantonDataPM();
    private WindDataPM   windProxy =   new WindDataPM();



    public RootPM() {

        //hydro_resultat.addAll(Hydro_readFromFile());
        canton_resultat.addAll(Canton_readFromFile());
        wind_resultat.addAll(wind_readFromFile());

        undoDisabled.bind(Bindings.isEmpty(undoStack));
        redoDisabled.bind(Bindings.isEmpty(redoStack));


        //refreshWindPerCantonList();

        selectWindID.addListener(((observable, oldValue, newValue) -> {
            WindDataPM oldWindSelection = getWindSelect(oldValue.intValue());
            WindDataPM newWindSelection = getWindSelect(newValue.intValue());

            if(oldWindSelection != null)
            {
                //- unbind
                unbindWindData(oldWindSelection);
                disableUndoSupport(oldWindSelection);
            }

            if(newWindSelection != null)
            {
                //- bind
                bindWindData(newWindSelection);
                enableUndoSupport(newWindSelection);
            }
        }));
    }


    //- Bind Handling
    private void unbindWindData(WindDataPM oldWindSelection)
    {
        windProxy.idProperty()             .unbindBidirectional(oldWindSelection.idProperty());
        windProxy.locationNameProperty()   .unbindBidirectional(oldWindSelection.locationNameProperty());
        windProxy.statusProperty()         .unbindBidirectional(oldWindSelection.statusProperty());
        windProxy.constructStartProperty() .unbindBidirectional(oldWindSelection.constructStartProperty());
        windProxy.constructFinishProperty().unbindBidirectional(oldWindSelection.constructFinishProperty());
        windProxy.kwIstallProperty()       .unbindBidirectional(oldWindSelection.kwIstallProperty());
        windProxy.mw15Property()           .unbindBidirectional(oldWindSelection.mw15Property());
        windProxy.mw16Property()           .unbindBidirectional(oldWindSelection.mw16Property());
        windProxy.mw17Property()           .unbindBidirectional(oldWindSelection.mw17Property());
        windProxy.mw18Property()           .unbindBidirectional(oldWindSelection.mw18Property());
        windProxy.countProperty()          .unbindBidirectional(oldWindSelection.countProperty());
        windProxy.typeProperty()           .unbindBidirectional(oldWindSelection.typeProperty());
        windProxy.communesProperty()       .unbindBidirectional(oldWindSelection.communesProperty());
        windProxy.cantonProperty()         .unbindBidirectional(oldWindSelection.cantonProperty());
        windProxy.latitudeProperty()       .unbindBidirectional(oldWindSelection.latitudeProperty());
        windProxy.longitudeProperty()      .unbindBidirectional(oldWindSelection.longitudeProperty());
        windProxy.imageUrlProperty()       .unbindBidirectional(oldWindSelection.imageUrlProperty());

    }
    private void bindWindData(WindDataPM newWindSelection)
    {
        windProxy.idProperty()             .bindBidirectional(newWindSelection.idProperty());
        windProxy.locationNameProperty()   .bindBidirectional(newWindSelection.locationNameProperty());
        windProxy.statusProperty()         .bindBidirectional(newWindSelection.statusProperty());
        windProxy.constructStartProperty() .bindBidirectional(newWindSelection.constructStartProperty());
        windProxy.constructFinishProperty().bindBidirectional(newWindSelection.constructFinishProperty());
        windProxy.kwIstallProperty()       .bindBidirectional(newWindSelection.kwIstallProperty());
        windProxy.mw15Property()           .bindBidirectional(newWindSelection.mw15Property());
        windProxy.mw16Property()           .bindBidirectional(newWindSelection.mw16Property());
        windProxy.mw17Property()           .bindBidirectional(newWindSelection.mw17Property());
        windProxy.mw18Property()           .bindBidirectional(newWindSelection.mw18Property());
        windProxy.countProperty()          .bindBidirectional(newWindSelection.countProperty());
        windProxy.typeProperty()           .bindBidirectional(newWindSelection.typeProperty());
        windProxy.communesProperty()       .bindBidirectional(newWindSelection.communesProperty());
        windProxy.cantonProperty()         .bindBidirectional(newWindSelection.cantonProperty());
        windProxy.latitudeProperty()       .bindBidirectional(newWindSelection.latitudeProperty());
        windProxy.longitudeProperty()      .bindBidirectional(newWindSelection.longitudeProperty());
        windProxy.imageUrlProperty()       .bindBidirectional(newWindSelection.imageUrlProperty());

    }



    public void refreshWindPerCantonList() {
        for(CantonDataPM canton : canton_resultat) {
            //- init countvalue
            double maxPower = 0.0;
            int anzahlWinds = 0;

            // Collect the values
            for(WindDataPM w: wind_resultat){
                try {
                    if(w.getCanton() != null && w.getCanton().equals(canton.getKurzForm())){ //find canton
                        if(w.getMw15() != 0) { //- check kwYear 15
                            maxPower += w.getMw15();
                        }
                        if(w.getMw16() != 0) { //- check kwYear 16
                            maxPower += w.getMw15();
                        }
                        if(w.getMw17() != 0) { //- check kwYear 17
                            maxPower += w.getMw15();
                        }
                        if(w.getMw18() != 0) { //- check kwYear 18
                            maxPower += w.getMw15();
                        }

                        anzahlWinds++;
                    }
                }
                catch (Exception e)
                {
                    System.out.println("RootPM wirft einen Fehler beim Update der Übersichtsliste: " + e);
                }

            }

            //- Reset values
            canton.setTotalKWWatt(maxPower);
            canton.setAnzKWGesamt(anzahlWinds);
        }
    }




    //- -------------------------------------------------------------------------
    //- Hydro-Data behandeln ####################################################
//
//    private List<HydroDataPM> Hydro_readFromFile() {
//        try (Stream<String> stream = getStreamOfLines(FILE_NAME_MAIN)) {
//            return stream.skip(1)                                              // erste Zeile ist die Headerzeile; ueberspringen
//                    .map(line -> new HydroDataPM(line.split(DELIMITER, 14))) // aus jeder Zeile ein Objekt machen
//                    .collect(Collectors.toList());                        // alles aufsammeln
//        }
//    }
//
//    public void hydro_save() {
//        //System.out.println("Datei Hydro wird versucht zu schreiben");
//        try (BufferedWriter writer = Files.newBufferedWriter(getPath(FILE_NAME_MAIN))) {
//            writer.write("ENTITY_ID;NAME;TYPE;SITE;CANTON;MAX_WATER_VOLUME_M3_S;MAX_POWER_MW;START_OF_OPERATION_FIRST;START_OF_OPERATION_LAST;LATITUDE;LONGITUDE;STATUS;WATERBODIES;IMAGE_URL");
//            writer.newLine();
//            hydro_resultat.stream()
//                    .map(hydro_resultat -> hydro_resultat.Hydro_InfoAsLine(DELIMITER))
//                    .forEach(line -> {
//                        try {
//                            writer.write(line);
//                            writer.newLine();
//                        } catch (IOException e) {
//                            throw new IllegalStateException(e);
//                        }
//                    });
//            showInfoDialog("Daten Speicherung", "Alle Wasserwerke wurden gespeichert.");
//        } catch (IOException e) {
//            throw new IllegalStateException("save failed");
//        }
//    }
//
//
//    public ObservableList<HydroDataPM> getHydro_result() {
//        return hydro_resultat;
//    }

//    public void hydro_add(int last_id)
//    {
//        int new_id = hydro_resultat.size();
//        HydroDataPM newHydro = new HydroDataPM(new_id);
//        getHydro_result().add(newHydro);
//        setSelectHydroId(new_id);
//
//        redoStack.clear();
//        undoStack.add(0, new AddCommand(this, newHydro, hydro_resultat.size() - 1));
//        showInfoDialog("Neues Kraftwerk", "Kraftwerk wurde hinzugefügt");
//    }

//    public void hydro_delete()
//    {
//        HydroDataPM hydrosel = hydro_resultat.stream().filter(hydro -> hydro.getId() == getSelectHydroId()).findFirst().get();
//        int currentPosition = hydro_resultat.indexOf(hydrosel);
//
//        removeFromList(hydrosel);
//        redoStack.clear();
//        undoStack.add(0, new RemoveCommand(this, hydrosel, currentPosition));
//
//        showInfoDialog("Kraftwerk Änderung", "Das Kraftwerk <" +hydrosel.getName()+ "> wurde gelöscht");
//    }
//
//    public void load_first_hydro_entry()
//    {
//        setSelectHydroId(getHydro_result().stream()
//                .sorted(Comparator.comparingInt(value -> value.getId()))
//                .mapToInt(value -> value.getId())
//                .min()
//                .getAsInt());
//
//    }

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
    //- Wind-Data behandeln ####################################################

    private List<WindDataPM> wind_readFromFile() {
        try (Stream<String> stream = getStreamOfLines(FILE_NAME_MAIN)) {
            return stream.skip(1)                                              // erste Zeile ist die Headerzeile; ueberspringen
                    .map(line -> new WindDataPM(line.split(DELIMITER, 17))) // aus jeder Zeile ein Objekt machen
                    .collect(Collectors.toList());                        // alles aufsammeln
        }
        catch (Exception e)
        {
            System.out.println("Fileproblem (" + FILE_NAME_MAIN + "): " + e);
        }
        return null;
    }

    public void wind_save() {
        //System.out.println("Datei Hydro wird versucht zu schreiben");
        try (BufferedWriter writer = Files.newBufferedWriter(getPath(FILE_NAME_MAIN))) {
            writer.write("ID;LOCATION_NAME;STATUS;CONSTRUCTION_START;COMPLETION;INSTALLED_POWER_KW;PRODUCTION_2015_MWH;PRODUCTION_2016_MWH;PRODUCTION_2017_MWH;PRODUCTION_2018_MWH;COUNT;TYPE;COMMUNES;CANTON;LATITUDE;LONGITUDE;IMAGE_URL");
            writer.newLine();
            wind_resultat.stream()
                    .map(wind_resultat -> wind_resultat.windInfoAsLine(DELIMITER))
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    });
            showInfoDialog("Daten Speicherung", "Alle Windkraftwerke wurden gespeichert.");
        } catch (IOException e) {
            throw new IllegalStateException("save failed");
        }
    }


    public ObservableList<WindDataPM> getWind_result() {
        return wind_resultat;
    }

    public void wind_add(int last_id)
    {
        int new_id = wind_resultat.size();
        WindDataPM newWind = new WindDataPM(new_id);
        getWind_resultat().add(newWind);
        setSelectWindID(new_id);

        redoStack.clear();
        undoStack.add(0, new AddCommand(this, newWind, wind_resultat.size() - 1));
        showInfoDialog("Neues Kraftwerk", "Kraftwerk wurde hinzugefügt");
    }

    public void wind_delete()
    {
        WindDataPM windselect =  wind_resultat.stream().filter(wind -> wind.getId() == getSelectWindID()).findFirst().get();
        int currentPosition = wind_resultat.indexOf(windselect);

        removeFromList(windselect);
        redoStack.clear();
        undoStack.add(0, new RemoveCommand(this, windselect, currentPosition));

        showInfoDialog("Kraftwerk Änderung", "Das Kraftwerk bei <" +windselect.getLocationName()+ "> wurde gelöscht");
    }

    public void load_first_wind_entry()
    {
        setSelectWindID(getWind_result().stream()
                .sorted(Comparator.comparingInt(value -> value.getId()))
                .mapToInt(value -> value.getId())
                .min()
                .getAsInt());

    }

    //- Wind-Data behandeln ####################################################
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

    public void addToList(int position, WindDataPM wind){
        wind_resultat.add(position, wind);
        setSelectWindID(wind.getId());
    }

    public void removeFromList(WindDataPM wind){
        unbindWindData(wind);
        disableUndoSupport(wind);

        wind_resultat.remove(wind);

        if(!wind_resultat.isEmpty()){
            setSelectWindID(wind_resultat.get(0).getId());
        }
    }

    public void setPropertyValue(Property property, Object newValue){
        property.removeListener(propertyChangeListenerForUndoSupport);
        property.setValue(newValue);
        property.addListener(propertyChangeListenerForUndoSupport);
    }

    private void enableUndoSupport(WindDataPM wind) {
        wind.idProperty()              .addListener(propertyChangeListenerForUndoSupport);
        wind.locationNameProperty()    .addListener(propertyChangeListenerForUndoSupport);
        wind.statusProperty()          .addListener(propertyChangeListenerForUndoSupport);
        wind.constructStartProperty()  .addListener(propertyChangeListenerForUndoSupport);
        wind.constructFinishProperty() .addListener(propertyChangeListenerForUndoSupport);
        wind.kwIstallProperty()        .addListener(propertyChangeListenerForUndoSupport);
        wind.mw15Property()            .addListener(propertyChangeListenerForUndoSupport);
        wind.mw16Property()            .addListener(propertyChangeListenerForUndoSupport);
        wind.mw17Property()            .addListener(propertyChangeListenerForUndoSupport);
        wind.mw18Property()            .addListener(propertyChangeListenerForUndoSupport);
        wind.countProperty()           .addListener(propertyChangeListenerForUndoSupport);
        wind.typeProperty()            .addListener(propertyChangeListenerForUndoSupport);
        wind.communesProperty()        .addListener(propertyChangeListenerForUndoSupport);
        wind.cantonProperty()          .addListener(propertyChangeListenerForUndoSupport);
        wind.latitudeProperty()        .addListener(propertyChangeListenerForUndoSupport);
        wind.longitudeProperty()       .addListener(propertyChangeListenerForUndoSupport);
        wind.imageUrlProperty()        .addListener(propertyChangeListenerForUndoSupport);
    }


    private void disableUndoSupport(WindDataPM wind) {
        wind.idProperty()              .removeListener(propertyChangeListenerForUndoSupport);
        wind.locationNameProperty()    .removeListener(propertyChangeListenerForUndoSupport);
        wind.statusProperty()          .removeListener(propertyChangeListenerForUndoSupport);
        wind.constructStartProperty()  .removeListener(propertyChangeListenerForUndoSupport);
        wind.constructFinishProperty() .removeListener(propertyChangeListenerForUndoSupport);
        wind.kwIstallProperty()        .removeListener(propertyChangeListenerForUndoSupport);
        wind.mw15Property()            .removeListener(propertyChangeListenerForUndoSupport);
        wind.mw16Property()            .removeListener(propertyChangeListenerForUndoSupport);
        wind.mw17Property()            .removeListener(propertyChangeListenerForUndoSupport);
        wind.mw18Property()            .removeListener(propertyChangeListenerForUndoSupport);
        wind.countProperty()           .removeListener(propertyChangeListenerForUndoSupport);
        wind.typeProperty()            .removeListener(propertyChangeListenerForUndoSupport);
        wind.communesProperty()        .removeListener(propertyChangeListenerForUndoSupport);
        wind.cantonProperty()          .removeListener(propertyChangeListenerForUndoSupport);
        wind.latitudeProperty()        .removeListener(propertyChangeListenerForUndoSupport);
        wind.longitudeProperty()       .removeListener(propertyChangeListenerForUndoSupport);
        wind.imageUrlProperty()        .removeListener(propertyChangeListenerForUndoSupport);
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

    public ObservableList<WindDataPM> getWindListFilter() {
        if (filter == null || filter.getValue() == null || filter.getValue().equals("")) {
            return wind_resultat;
        }
        return wind_resultat.stream()
                .filter(x -> x.getLocationName().toLowerCase().contains(getFilter().toLowerCase()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }


    //- all getters and setters

    // Application Definition ########################################################
    public String getApplicationTitle() {
        return applicationTitle.get();
    }

    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }


    // Hydro ########################################################
//    public HydroDataPM getHydroSelect(int id) {
//        return hydro_resultat.stream()
//                .filter(HydroPM -> HydroPM.getId() == id)
//                .findAny().orElse(null);
//    }
//
//
//    public int getSelectHydroId() {
//        return selectHydroId.get();
//    }
//
//    public IntegerProperty selectHydroIdProperty() {
//        return selectHydroId;
//    }
//
//    public void setSelectHydroId(int selectHydroId) {
//        this.selectHydroId.set(selectHydroId);
//    }
//
//    public int getSelectCantonId() {
//        return selectCantonId.get();
//    }
//
//    public ObservableList<HydroDataPM> getHydro_resultat() {
//        return hydro_resultat;
//    }
//
//    public HydroDataPM getHydroProxy() {
//        return hydroProxy;
//    }
//
//    public void setHydroProxy(HydroDataPM hydroProxy) {
//        this.hydroProxy = hydroProxy;
//    }
    
    // Wind ########################################################
    public WindDataPM getWindSelect(int id) {
        return wind_resultat.stream()
                .filter(w -> w.getId() == id)
                .findAny().orElse(null);
    }

    public int getSelectWindID() {
        return selectWindID.get();
    }

    public IntegerProperty selectWindIDProperty() {
        return selectWindID;
    }

    public void setSelectWindID(int selectWindID) {
        this.selectWindID.set(selectWindID);
    }

    public ObservableList<WindDataPM> getWind_resultat() {
        return wind_resultat;
    }

    public WindDataPM getWindProxy() {
        return windProxy;
    }

    public void setWindProxy(WindDataPM windProxy) {
        this.windProxy = windProxy;
    }


    // Canton ########################################################
    private CantonDataPM getCantonSelect(String kname) {

        return canton_resultat.stream()
                .filter(HydroPM -> HydroPM.getKurzForm() == kname)
                .findAny().orElse(null);
    }

    public ObservableList<CantonDataPM> getCanton_resultat() {
        return canton_resultat;
    }
    public IntegerProperty selectCantonIdProperty() {
        return selectCantonId;
    }

    public void setSelectCantonId(int selectCantonId) {
        this.selectCantonId.set(selectCantonId);
    }
    
    public CantonDataPM getCantonProxy() {
        return cantonProxy;
    }

    public void setCantonProxy(CantonDataPM cantonProxy) {
        this.cantonProxy = cantonProxy;
    }


}
