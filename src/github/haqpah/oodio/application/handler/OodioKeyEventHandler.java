package github.haqpah.oodio.application.handler;

import github.haqpah.oodio.musiclibrary.MusicLibraryTrack;
import github.haqpah.oodio.musiclibrary.MusicLibraryTrackRow;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
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
	public OodioKeyEventHandler(final TableView<MusicLibraryTrackRow> tableView, MediaPlayer systemPlayer)
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
		MusicLibraryTrackRow selectedItem = tableView_.getSelectionModel().getSelectedItem();

		if(keyEvent.getCode().equals(KeyCode.ENTER))
		{
			handleEnter(selectedItem);
		}
		else if(keyEvent.getCode().equals(KeyCode.SPACE))
		{
			handleSpace();
		}
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
		if(row != null)
		{
			systemPlayer_.stop(); // May or may not be needed, but does not throw an exception

			MusicLibraryTrack track = row.getTrack();
			Media media = new Media(track.getFilePath().toUri().toString());
			systemPlayer_ = new MediaPlayer(media);
		}
	}

	/**
	 * Handles a space key press event
	 *
	 * @version 0.0.0.20170507
	 * @since 0.0
	 */
	private void handleSpace()
	{
		if(systemPlayer_.statusProperty().equals(MediaPlayer.Status.PLAYING))
		{
			systemPlayer_.pause();
		}
		else
		{
			systemPlayer_.play();
		}
	}
}
