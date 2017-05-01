package github.haqpah.oodio.application.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.log4j.Logger;

import github.haqpah.oodio.musiclibrary.MusicLibrary;
import github.haqpah.oodio.musiclibrary.MusicLibrarySong;
import github.haqpah.oodio.musiclibrary.MusicLibrarySongRow;
import github.haqpah.oodio.services.SystemPathService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * TODO
 *
 * @version 0.0.0.20170430
 * @since 0.0
 */
public class SystemController extends AbstractController implements FxmlController
{
	/**
	 * The FXML file name for this controller
	 */
	private final static String FXML_FILENAME_ = "System.fxml";

	/**
	 * The {@link MediaPlayer} that this controller can control
	 */
	private MediaPlayer systemPlayer_;

	/**
	 * TODO
	 */
	private TableView<MusicLibrarySongRow> musicLibraryTableView_;

	/**
	 * TODO
	 */
	private MusicLibrary musicLibrary_;

	/**
	 * TODO
	 * Constructor
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @param primaryStage
	 * @param systemLogger
	 * @param musicLibrary
	 */
	public SystemController(Stage primaryStage, Logger systemLogger, MusicLibrary musicLibrary)
	{
		super(primaryStage, systemLogger, FXML_FILENAME_);

		musicLibrary_ = musicLibrary;

		// Load default song
		if(musicLibrary.getLibrary() != null)
		{
			MusicLibrarySong defaultSong = musicLibrary.getLibrary().get(0);
			Media defaultMedia = new Media(defaultSong.getFilePath().toUri().toString());
			systemPlayer_ = new MediaPlayer(defaultMedia);
		}
		else
		{
			Media helloWorldMedia = new Media(SystemPathService.getHelloWorldMediaPath().toString());
			systemPlayer_ = new MediaPlayer(helloWorldMedia);
		}

		// Get the system music library table view object
		AnchorPane rootNode = (AnchorPane) getRootNode();
		List<Node> children = rootNode.getChildren();
		for(Node child : children)
		{
			if(child instanceof TableView)
			{
				musicLibraryTableView_ = (TableView<MusicLibrarySongRow>) child;
			}
		}

		// Add songs to the in-memory music library
		addSongsToTableView(musicLibrary_.getLibrary());
	}

	/**
	 * Adds a group of {@link MusicLibrarySong} to the table view.
	 * <p>
	 * Does <strong>not</strong> add the file to the music library directory
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @param songsToAdd
	 *            the songs to add to the table view
	 */
	private void addSongsToTableView(List<MusicLibrarySong> songsToAdd)
	{
		for(MusicLibrarySong song : songsToAdd)
		{
			getSystemLogger().debug("Building new row from song: " + song.toString());
			MusicLibrarySongRow row = new MusicLibrarySongRow(song);
			musicLibraryTableView_.getItems().add(row);
		}
	}

	/*************************************************
	 *
	 * SYSTEM MENU ACTIONS
	 *
	 *************************************************/
	@FXML
	public void newPlaylist(ActionEvent event)
	{
		throw new UnsupportedOperationException("Oodio does not yet support playlists");
	}

	/**
	 * Adds the selected file to the in-memory music library, the table view, and to the music library directory
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @param event
	 *            the {@link ActionEvent} that triggered this method
	 */
	@FXML
	public void addTrackToLibrary(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(getPrimaryStage());

		if(file != null)
		{
			MusicLibrarySong song = null;
			try
			{
				song = new MusicLibrarySong(file, getSystemLogger());
				getSystemLogger().debug("Adding new song to music library: " + song.toString());
			}
			catch (InterruptedException | UnsupportedEncodingException e)
			{
				getSystemLogger().error("Could not add song to library", e);
			}

			// Add it to the in-memory library
			if(!musicLibrary_.getLibrary().contains(song))
			{
				musicLibrary_.getLibrary().add(song);
				getSystemLogger().debug("Song added to in-memory library: " + song.toString());
			}

			// Add it to the table view
			musicLibraryTableView_.getItems().add(new MusicLibrarySongRow(song));
			getSystemLogger().debug("Song added to table view: " + song.toString());

			// Add it to the music library directory
			// TODO
			getSystemLogger().debug("TODO Song added to music library directory in file system: " + song.toString());
		}
	}

	/**
	 * Exits the applcation
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param event
	 */
	@FXML
	public void exitApplication(ActionEvent event)
	{
		// TODO hook into application stop somehow?
		getSystemLogger().info("Exiting application via system menu");

		// TODO
		getSystemLogger().shutdown();

		System.exit(0);
	}

	/**
	 * Takes the user to the github readme
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param event
	 */
	@FXML
	public void about(ActionEvent event)
	{
		if(Desktop.isDesktopSupported())
		{
			try
			{
				Desktop.getDesktop().browse(new URI("https://github.com/haqpah/Oodio/blob/master/README.md"));
			}
			catch (IOException | URISyntaxException e)
			{
				getSystemLogger().error("Failed to load github link to the README", e);
			}
		}
	}

	/**
	 * Takes the user to the github wiki
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param event
	 */
	@FXML
	public void wiki(ActionEvent event)
	{
		if(Desktop.isDesktopSupported())
		{
			try
			{
				Desktop.getDesktop().browse(new URI("http:/www.github.com/haqpah/Oodio/wiki"));
			}
			catch (IOException | URISyntaxException e)
			{
				getSystemLogger().error("Failed to load github link to the Wiki", e);
			}
		}
	}

	/**
	 *
	 * SYSTEM PLAYER ACTIONS
	 *
	 */

	/**
	 * Plays the loaded track
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 */
	@FXML
	protected void playTrack(ActionEvent event)
	{
		systemPlayer_.play();
	}

	/**
	 * Pauses the loaded track
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 */
	@FXML
	protected void pauseTrack(ActionEvent event)
	{
		systemPlayer_.pause();
	}

	/**
	 * Stops the loaded track
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 */
	@FXML
	protected void stopTrack(ActionEvent event)
	{
		systemPlayer_.stop();
	}

	/**
	 * @version 0.0.0.20170430
	 * @since 0.0
	 */
	@Override
	public String getFxmlFilename()
	{

		return FXML_FILENAME_;
	}

}
