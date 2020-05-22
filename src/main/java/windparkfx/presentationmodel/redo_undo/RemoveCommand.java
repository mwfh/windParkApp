package windparkfx.presentationmodel.redo_undo;

import windparkfx.presentationmodel.HydroDataPM;
import windparkfx.presentationmodel.RootPM;

/**
 * @author Mario Wettstein
 */
public class RemoveCommand implements Command {
	private final RootPM rootPM;
	private final HydroDataPM removed;
	private final int       position;

	public RemoveCommand(RootPM rootPM, HydroDataPM removed, int position) {
		this.rootPM = rootPM;
		this.removed = removed;
		this.position = position;
	}

	@Override
	public void undo() {
		rootPM.getHydro_result().add(position, removed);
		rootPM.setSelectHydroId(removed.getId());
	}

	@Override
	public void redo() {
		rootPM.removeFromList(removed);
	}
}