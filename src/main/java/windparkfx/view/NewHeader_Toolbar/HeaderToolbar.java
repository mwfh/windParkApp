package windparkfx.view.NewHeader_Toolbar;

import windparkfx.presentationmodel.RootPM;
import windparkfx.view.ViewMixin;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.util.Comparator;


public class HeaderToolbar extends ToolBar implements ViewMixin {

    private static final String FONT_PATH = "/fonts/fontawesome-webfont.ttf";

    private static final String STYLE_PATH = "toolbar-control.css";

    private static final int FONT_SIZE = 18;

    private final RootPM rootPM;

    private Font customFont;

    private Button saveButton;

    private Button addNewButton;

    private Button deleteButton;

    private Button undoButton;

    private Button redoButton;

    private Region horizontalRegionSpacer;

    private Button germanLanguageButton;

    private Button englishLanguageButton;

    private TextField searchTextField;

    public HeaderToolbar(RootPM model) {
        this.rootPM = model;
        init();
    }

    @Override
    public void initializeSelf() {

        try {
            InputStream fontInputStream = HeaderToolbar.class.getResourceAsStream(FONT_PATH);
            customFont = Font.loadFont(fontInputStream, FONT_SIZE);
        } catch (Exception exception) {
            throw new RuntimeException("Achtung: Die Datei " + FONT_PATH + " konnte nicht geladen/verarbeitet werden. Bitte füge Sie deinem Projekt hinzu!", exception);
        }

        try {
            String stylesheet = getClass().getResource(STYLE_PATH).toExternalForm();
            getStylesheets().add(stylesheet);
            getStyleClass().add("toolbar-control");
        } catch (Exception exception) {
            throw new RuntimeException("Achtung: Die Datei " + STYLE_PATH + " konnte nicht geladen/verarbeitet werden. Bitte füge Sie deinem Projekt hinzu!", exception);
        }

        getStyleClass().add("header-toolbar");

    }

    @Override
    public void initializeControls() {
        horizontalRegionSpacer = new Region();
        saveButton = createIconButton('\uf0c7');
        addNewButton = createIconButton('\uf067');
        deleteButton = createIconButton('\uf00d');
        undoButton = createIconButton('\uf0e2');
        redoButton = createIconButton('\uf01e');
        germanLanguageButton = createTextButton("DE");
        englishLanguageButton = createTextButton("EN");
        searchTextField = createTextField();
    }

    @Override
    public void layoutControls() {
        HBox.setHgrow(horizontalRegionSpacer, Priority.ALWAYS);
        //getItems().addAll(saveButton, addNewButton, deleteButton, undoButton, redoButton, horizontalRegionSpacer, germanLanguageButton, englishLanguageButton, searchTextField);
        getItems().addAll(saveButton, addNewButton, deleteButton, undoButton, redoButton, horizontalRegionSpacer, searchTextField);
    }

    @Override
    public void setupEventHandlers() {
        saveButton.setOnAction(event -> rootPM.wind_save());
        addNewButton.setOnAction(event -> rootPM.wind_add(rootPM.getWind_result().stream()
                                            .sorted(Comparator.comparingInt(value -> value.getId()))
                                            .mapToInt(value -> value.getId())
                                            .max()
                                            .getAsInt()));
        deleteButton.setOnAction(event -> rootPM.wind_delete());

        // TODO: Implement all button handlers bellow
        undoButton.setOnAction(event -> rootPM.undo());
        redoButton.setOnAction(event -> rootPM.redo());
        germanLanguageButton.setOnAction(event -> System.out.println("German language button clicked!"));
        englishLanguageButton.setOnAction(event -> System.out.println("English language button clicked!"));
        searchTextField.setOnAction(event -> System.out.println("Search text field submitted!"));
        //searchTextField.setOnKeyTyped(event -> System.out.println("Search text field typed!"));
        searchTextField.setOnKeyTyped(event -> {

        });
    }

    @Override
    public void setupValueChangedListeners() {
        // Do nothing
    }

    @Override
    public void setupBindings() {
        //- Search
        searchTextField.textProperty().bindBidirectional(rootPM.filterProperty());
    }

    private Button createIconButton(char iconName) {
        Button button = new Button();
        button.setText(String.valueOf(iconName));
        button.setFont(customFont);
        button.getStyleClass().add("button-icon");
        return button;
    }

    private Button createTextButton(String languageName) {
        Button button = new Button();
        button.setText(languageName);
        button.setFont(customFont);
        button.getStyleClass().add("button-text");
        return button;
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        textField.getStyleClass().add("textfield");
        textField.setPromptText('\uf00e' + " Search Location ");
        return textField;
    }
}


