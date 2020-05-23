package windparkfx.presentationmodel.redo_undo;

import windparkfx.presentationmodel.RootPM;
import windparkfx.presentationmodel.WindDataPM;

/**
 * @author Mario Wettstein
 */
public class RemoveCommand implements Command {
	private final RootPM rootPM;
	private final WindDataPM removed;
	private final int       position;

	public RemoveCommand(RootPM rootPM, WindDataPM removed, int position) {
		this.rootPM = rootPM;
		this.removed = removed;
		this.position = position;
	}

	@Override
	public void undo() {
		rootPM.getWind_result().add(position, removed);
		rootPM.setSelectWindID(removed.getId());
	}

	@Override
	public void redo() {
		rootPM.removeFromList(removed);
	}
}