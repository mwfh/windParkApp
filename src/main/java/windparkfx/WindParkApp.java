package windparkfx;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import windparkfx.presentationmodel.RootPM;
import windparkfx.view.RootPanel;


/**
 * @author Mario Wettstein
 */

public class WindParkApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("Bin in Start:");

		RootPM rootPM = new RootPM();
		Parent rootPanel = new RootPanel(rootPM);

		Scene scene = new Scene(rootPanel);

		primaryStage.titleProperty().bind(rootPM.applicationTitleProperty());
		primaryStage.setScene(scene);
		primaryStage.show();

		rootPM.load_first_wind_entry();


		//- Customer - Control
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		System.out.println("Hello" );
		launch(args);
	}



}