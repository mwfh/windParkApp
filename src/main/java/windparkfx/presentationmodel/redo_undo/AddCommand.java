package windparkfx.presentationmodel.redo_undo;

import windparkfx.presentationmodel.HydroDataPM;
import windparkfx.presentationmodel.RootPM;

import static javafx.application.Application.launch;

/**
 * @author Mario Wettstein
 */
public class AddCommand implements Command {

	private final RootPM rootPM;
	private final HydroDataPM added;
	private final int       position;

	public AddCommand(RootPM rootPM, HydroDataPM added, int position) {
		this.rootPM = rootPM;
		this.added = added;
		this.position = position;
	}

	@Override
	public void undo() {
		rootPM.removeFromList(added);
	}

	@Override
	public void redo() {
		rootPM.addToList(position, added);
	}

	public static void main(String[] args) {
		//System.out.println("Hello" );
		launch(args);
	}



}


