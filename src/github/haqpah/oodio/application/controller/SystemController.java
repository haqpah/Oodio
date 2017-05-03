package github.haqpah.oodio.application.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import github.haqpah.oodio.musiclibrary.MusicLibrary;
import github.haqpah.oodio.musiclibrary.MusicLibraryTrack;
import github.haqpah.oodio.musiclibrary.MusicLibraryTrackRow;
import github.haqpah.oodio.services.SystemPathService;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * A concrete implementation of the abstract controller for the system. Contains all UI elements
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
	 * The {@link MusicLibrary} containing metadata on the files in the user's system
	 */
	private MusicLibrary musicLibrary_;

	/**
	 * The {@link MediaPlayer} that this controller can control
	 */
	private MediaPlayer systemPlayer_;

	/**
	 * The {@link Pane} that is the parent of all UI elements
	 * <p>
	 * This is equivalent to {@link AbstractController.#getRootNode()} but
	 * allows for direct access and FXML compatibility!
	 */
	@FXML
	private AnchorPane systemAnchorPane_;

	/**
	 * The {@link MenuBar} with user actions for operating on the system
	 */
	@FXML
	private MenuBar systemMenuBar_;

	/**
	 * The {@link TableView} that shows the users music files
	 */
	@FXML
	private TableView<MusicLibraryTrackRow> musicLibraryTable_;

	/**
	 * The {@link TableColumn} the shows the title of a track
	 */
	@FXML
	private TableColumn<MusicLibraryTrackRow, String> colTitle_;

	/**
	 * The {@link TableColumn} the shows the artist of a track
	 */
	@FXML
	private TableColumn<MusicLibraryTrackRow, String> colArtist_;

	/**
	 * The {@link TableColumn} the shows the album of a track
	 */
	@FXML
	private TableColumn<MusicLibraryTrackRow, String> colAlbum_;

	/**
	 * The {@link TableColumn} the shows the genre of a track
	 */
	@FXML
	private TableColumn<MusicLibraryTrackRow, String> colGenre_;

	/**
	 * The {@link TableColumn} the shows the year of a track
	 */
	@FXML
	private TableColumn<MusicLibraryTrackRow, String> colYear_;

	/**
	 * The {@link Button} responsible for loading the previous track in the queue
	 */
	@FXML
	private Button btnPrevious_;

	/**
	 * The {@link Button} responsible for loading the next track in the queue
	 */
	@FXML
	private Button btnNext_;

	/**
	 * The {@link Button} responsible for loading the playing the currently loaded track
	 */
	@FXML
	private Button btnPlay_;

	/**
	 * The {@link Button} responsible for loading the pausing the currently loaded track
	 */
	@FXML
	private Button btnPause_;

	/**
	 * The {@link Button} responsible for loading the stoppings the currently loaded track
	 */
	@FXML
	private Button btnStop_;

	/**
	 * Constructor
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @param primaryStage
	 *            The stage this controller's parent pane is attached to
	 * @param systemLogger
	 *            Responsible for logging information to the console and log file
	 * @param musicLibrary
	 *            The {@link MusicLibrary} containing metadata on the files in the user's system
	 */
	public SystemController(Stage primaryStage, Logger systemLogger, MusicLibrary musicLibrary)
	{
		super(primaryStage, systemLogger, FXML_FILENAME_);

		musicLibrary_ = musicLibrary;

		// Setup cell value factories with observable values
		setupCellValueFactories();

		// Get all the rows to add to the table
		List<MusicLibraryTrackRow> rowList = createMusicLibraryTrackRows();

		try
		{
			// TODO Figure out how to get rid of this
			Thread.sleep(100); // Sleeping an extra 100ms to let metadata populate
		}
		catch (InterruptedException e)
		{
			systemLogger.error("Thread woke up unexpectedly, some rows may be missing metadata.");
		}

		musicLibraryTable_.getItems().addAll(rowList);
		musicLibraryTable_.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		loadDefaultTrack();

	}

	/*************************************************
	 *
	 * SYSTEM MENU ACTIONS
	 *
	 *************************************************/

	/**
	 * Creates a new playlist in the system
	 *
	 * @version 0.0.0.20170503
	 * @since 0.0
	 *
	 * @param event
	 *            the {@link ActionEvent} that triggered this method
	 */
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
	 * @throws Exception
	 *             if the track could not be added to the library
	 */
	@FXML
	public void addTrackToLibrary(ActionEvent event) throws Exception
	{
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(getPrimaryStage());

		if(file != null)
		{
			try
			{
				MusicLibraryTrack track = new MusicLibraryTrack(file, getSystemLogger());

				// Add it to the in-memory library
				if(!musicLibrary_.getLibrary().contains(track))
				{
					musicLibrary_.getLibrary().add(track);
				}

				// TODO Add track to the music library directory
				getSystemLogger().debug("TODO Track added to music library directory in file system: " + track.toString());

				// Add it to the table view once its been added to more important systems
				MusicLibraryTrackRow row = new MusicLibraryTrackRow(track);
				musicLibraryTable_.getItems().add(row);
			}
			catch (Exception e)
			{
				throw new Exception("Could not add track to library", e);
			}
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
		getSystemLogger().info("Exiting application via system menu");

		getSystemLogger().shutdown(); // TODO Research logger shutdown
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

	/*************************************************
	 *
	 * SYSTEM PLAYER ACTIONS
	 *
	 *************************************************/

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

	/**
	 * Creates a list of populated rows that track metadata can be displayed on the {@link #musicLibraryTable_}
	 *
	 * @version 0.0.0.20170503
	 * @since 0.0
	 *
	 * @return the list of rows that was created
	 */
	private List<MusicLibraryTrackRow> createMusicLibraryTrackRows()
	{
		List<MusicLibraryTrackRow> list = new ArrayList<MusicLibraryTrackRow>();

		musicLibrary_.getLibrary().forEach(track -> {
			MusicLibraryTrackRow row = new MusicLibraryTrackRow(track);
			list.add(row);
		});

		getSystemLogger().debug("Created " + list.size() + " rows for library table view");

		return list;
	}

	/**
	 * Loads the hello world media into the system player. This likely only plays the
	 * first time the user ever launches the application. Any song in the music library
	 * will take precedence.
	 *
	 * @version 0.0.0.20170503
	 * @since 0.0
	 */
	private void loadDefaultTrack()
	{
		String defaultMedia;
		if(!musicLibrary_.isEmpty())
		{
			MusicLibraryTrack defaultSong = musicLibrary_.getFirst();
			defaultMedia = defaultSong.getFilePath().toUri().toString();
		}
		else
		{
			defaultMedia = SystemPathService.getHelloWorldMediaPath().toString();
		}

		Media media = new Media(defaultMedia);
		systemPlayer_ = new MediaPlayer(media);

		getSystemLogger().debug("Loaded default track: " + defaultMedia);
	}

	/**
	 * Sets up the cell value factories with observable string
	 * properties in the {@link MusicLibraryTrackRow} object
	 *
	 * @version 0.0.0.20170503
	 * @since 0.0
	 */
	private void setupCellValueFactories()
	{
		getSystemLogger().debug("Setting up cell value factories");

		// Title column
		colTitle_.setCellValueFactory(
				new Callback<CellDataFeatures<MusicLibraryTrackRow, String>, ObservableValue<String>>()
				{
					@Override
					public ObservableValue<String> call(CellDataFeatures<MusicLibraryTrackRow, String> row)
					{
						return row.getValue().titleProperty();
					}
				});

		// Artist column
		colArtist_.setCellValueFactory(
				new Callback<CellDataFeatures<MusicLibraryTrackRow, String>, ObservableValue<String>>()
				{
					@Override
					public ObservableValue<String> call(CellDataFeatures<MusicLibraryTrackRow, String> row)
					{
						return row.getValue().artistProperty();
					}
				});

		// Album column
		colAlbum_.setCellValueFactory(
				new Callback<CellDataFeatures<MusicLibraryTrackRow, String>, ObservableValue<String>>()
				{
					@Override
					public ObservableValue<String> call(CellDataFeatures<MusicLibraryTrackRow, String> row)
					{
						return row.getValue().albumProperty();
					}
				});

		// Genre column
		colGenre_.setCellValueFactory(
				new Callback<CellDataFeatures<MusicLibraryTrackRow, String>, ObservableValue<String>>()
				{
					@Override
					public ObservableValue<String> call(CellDataFeatures<MusicLibraryTrackRow, String> row)
					{
						return row.getValue().genreProperty();
					}
				});

		// Year column
		colYear_.setCellValueFactory(
				new Callback<CellDataFeatures<MusicLibraryTrackRow, String>, ObservableValue<String>>()
				{
					@Override
					public ObservableValue<String> call(CellDataFeatures<MusicLibraryTrackRow, String> row)
					{
						return row.getValue().yearProperty();
					}
				});
	}

	/**
	 * Convenience method to get all the columns of the {@link #musicLibraryTable_} in a {@link List}
	 *
	 * @version 0.0.0.20170503
	 * @since 0.0
	 *
	 * @return the list
	 */
	private List<TableColumn<MusicLibraryTrackRow, String>> getColumnsAsList()
	{
		List<TableColumn<MusicLibraryTrackRow, String>> columnList = new ArrayList<TableColumn<MusicLibraryTrackRow, String>>();

		columnList.add(colTitle_);
		columnList.add(colArtist_);
		columnList.add(colAlbum_);
		columnList.add(colGenre_);
		columnList.add(colYear_);

		return columnList;
	}
}
