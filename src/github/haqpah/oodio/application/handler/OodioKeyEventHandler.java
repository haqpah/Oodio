package github.haqpah.oodio.application.handler;

import github.haqpah.oodio.musiclibrary.MusicLibraryTrackRow;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;

/**
 * A central location for handle key press events in the application
 *
 * @version 0.0.0.20170507
 * @since 0.0
 */
public class OodioKeyEventHandler implements EventHandler<KeyEvent>
{
	/**
	 * The table view that has focus on the screen
	 *
	 * TODO Make this a more generic node
	 */
	private TableView<MusicLibraryTrackRow> tableView_;

	/**
	 * The system player that may need manipulation based on the pressed key
	 */
	private MediaPlayer systemPlayer_;

	/**
	 * A central location for handle key press events in the application
	 *
	 * @version 0.0.0.20170507
	 * @since 0.0
	 *
	 * @param tableView
	 *            The table view that has focus on the screen
	 * @param systemPlayer
	 *            The system player that may need manipulation based on the pressed key
	 */
	public OodioKeyEventHandler(final TableView<MusicLibraryTrackRow> tableView, final MediaPlayer systemPlayer)
	{
		tableView_ = tableView;
		systemPlayer_ = systemPlayer;
	}

	/**
	 * @version 0.0.0.20170507
	 * @since 0.0
	 */
	@Override
	public void handle(KeyEvent keyEvent)
	{

	}

	/**
	 * Handles an enter key press event
	 *
	 * @version 0.0.0.20170507
	 * @since 0.0
	 *
	 * @param row
	 *            the row that was focused while enter was pressed
	 */
	private void handleEnter(MusicLibraryTrackRow row)
	{

	}

	/**
	 * Handles a space key press event
	 *
	 * @version 0.0.0.20170507
	 * @since 0.0
	 */
	private void handleSpace()
	{

	}
}
