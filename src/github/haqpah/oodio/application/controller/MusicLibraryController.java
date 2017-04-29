package github.haqpah.oodio.application.controller;

import java.util.Map;

import org.apache.log4j.Logger;

import github.haqpah.oodio.musiclibrary.AlbumMetadata;
import github.haqpah.oodio.musiclibrary.ArtistMetadata;
import github.haqpah.oodio.musiclibrary.LibrarySongRow;
import github.haqpah.oodio.musiclibrary.MusicLibraryMetadata;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Controller for the music library UI elements defined in the MusicLibrary.fxml
 *
 * @version 0.0.0.20170429
 * @since 0.0
 */
public class MusicLibraryController extends AbstractController implements FxmlController
{
	/**
	 * The FXML file name for this controller
	 */
	private static final String FXML_FILENAME_ = "MusicLibrary.fxml";

	/**
	 * Controller for the music library UI elements defined in the MusicLibrary.fxml
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 */
	public MusicLibraryController(final Stage primaryStage, final Logger systemLogger, final MusicLibraryMetadata musicLibraryMetadata)
	{
		super(primaryStage, systemLogger, FXML_FILENAME_);

		// Add rows to the table
		if(getRootNode() instanceof TableView)
		{
			TableView<LibrarySongRow> tableView = (TableView<LibrarySongRow>) getRootNode();

			// Dig down into the in memory metadata list to get the song metadata for each song to populate the table
			for(ArtistMetadata artist : musicLibraryMetadata.getLibraryMetadata())
			{
				for(AlbumMetadata album : artist.getAlbumMetadata())
				{
					for(Map<String, Object> songMetadata : album.getSongMetadata())
					{
						LibrarySongRow row = new LibrarySongRow(songMetadata);
						tableView.getItems().add(row);
					}
				}
			}
		}
	}

	/**
	 * Adds a file to the library if it is discovered to be valid song file
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 */
	public void addSongDroppedOnLibrary()
	{
		// TODO implement
	}

	/**
	 * Plays the song that was clicked
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 */
	public void playSongClickedOnLibrary()
	{
		// TODO implement
	}

	/**
	 * @version 0.0.0.20170429
	 * @since 0.0
	 */
	@Override
	public String getFxmlFilename()
	{
		return FXML_FILENAME_;
	}

}
