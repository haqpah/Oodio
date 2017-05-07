package github.haqpah.oodio.application.handler;

import github.haqpah.oodio.musiclibrary.MusicLibraryTrack;
import github.haqpah.oodio.musiclibrary.MusicLibraryTrackRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class KeyEventHandler
{
	private KeyEvent keyEvent_;
	private TableView<MusicLibraryTrackRow> tableView_;
	private MediaPlayer systemPlayer_;

	public KeyEventHandler(KeyEvent keyEvent, final TableView<MusicLibraryTrackRow> tableView, MediaPlayer systemPlayer)
	{
		keyEvent_ = keyEvent;
		tableView_ = tableView;
		systemPlayer_ = systemPlayer;

		handle();
	}

	private void handle()
	{
		MusicLibraryTrackRow selectedItem = tableView_.getSelectionModel().getSelectedItem();

		if(keyEvent_.getCode().equals(KeyCode.ENTER))
		{
			MusicLibraryTrack track = selectedItem.getTrack();
			Media media = new Media(track.getFilePath().toUri().toString());
			systemPlayer_ = new MediaPlayer(media);
		}
	}
}
