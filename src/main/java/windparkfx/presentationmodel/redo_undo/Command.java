package windparkfx.presentationmodel.redo_undo;

/**
 * @author Mario Wettstein
 */
public interface Command {
	void undo();

	void redo();
}
