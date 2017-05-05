package github.haqpah.oodio.application.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import github.haqpah.oodio.musiclibrary.MusicLibrary;
import github.haqpah.oodio.musiclibrary.MusicLibraryTrack;
import github.haqpah.oodio.musiclibrary.MusicLibraryTrackRow;
import github.haqpah.oodio.services.SystemPathService;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
	 * The {@link MediaPlayer} that this controller can control
	 */
	private MediaPlayer systemPlayer_;

	/**
	 * The current track loaded into the {@link #systemPlayer_}. This is not a playable
	 * media file! This is a metadata object
	 */
	private Optional<MusicLibraryTrack> currentTrack_;

	/**
	 * The pane where metadata about the {@link #currentTrack_} is displayed
	 */
	@FXML
	private VBox currentTrackDisplayPane_;

	/**
	 * The label displaying the {@link #currentTrack_}'s title
	 */
	@FXML
	private Label currentTrackTitleDisplay_;

	/**
	 * The label displaying the {@link #currentTrack_}'s artist
	 */
	@FXML
	private Label currentTrackArtistDisplay_;

	/**
	 * The label displaying the {@link #currentTrack_}'s album
	 */
	@FXML
	private Label currentTrackAlbumDisplay_;

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
	 * The {@link Button} responsible for loading the stopping the currently loaded track
	 */
	@FXML
	private Button btnStop_;

	/**
	 * The {@link Slider} responsible for changing the volume of the currently loaded track
	 */
	@FXML
	private Slider volumeSlider_;

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
		setupTableFactories();

		// Get all the rows to add to the table
		List<MusicLibraryTrackRow> rowList = createMusicLibraryTrackRows();

		// Add the rows to the table
		musicLibraryTable_.getItems().addAll(rowList);
		musicLibraryTable_.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		currentTrack_ = loadDefaultTrack();

		refreshCurrentTrackDisplay();

		// Setup volume slider
		volumeSlider_.setValue(systemPlayer_.getVolume() * 100);
		volumeSlider_.valueProperty().addListener(new InvalidationListener()
		{
			/**
			 * @version 0.0.0.20170503
			 * @since 0.0
			 */
			@Override
			public void invalidated(Observable observable)
			{
				if(volumeSlider_.isValueChanging())
				{
					systemPlayer_.setVolume(volumeSlider_.getValue() / 100.0);
				}
			}
		});
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
	protected void newPlaylist(ActionEvent event)
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
	protected void addTrackToLibrary(ActionEvent event) throws Exception
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
	protected void exitApplication(ActionEvent event)
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
	protected void about(ActionEvent event)
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
	protected void wiki(ActionEvent event)
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
	private Optional<MusicLibraryTrack> loadDefaultTrack()
	{
		String defaultMedia;
		MusicLibraryTrack defaultSong;
		if(!musicLibrary_.isEmpty())
		{
			defaultSong = musicLibrary_.getFirst();
			defaultMedia = defaultSong.getFilePath().toUri().toString();
		}
		else
		{
			defaultSong = null; // TODO Construct MusicLibraryTrack object from hello world media
			defaultMedia = SystemPathService.getHelloWorldMediaPath().toString();
		}

		Media media = new Media(defaultMedia);
		systemPlayer_ = new MediaPlayer(media);

		getSystemLogger().debug("Loaded default track: " + defaultMedia);

		return Optional.ofNullable(defaultSong);
	}

	/**
	 * Sets up the cell value factories with observable string
	 * properties in the {@link MusicLibraryTrackRow} object
	 *
	 * @version 0.0.0.20170503
	 * @since 0.0
	 */
	private void setupTableFactories()
	{
		getSystemLogger().debug("Setting up row factory");

		musicLibraryTable_.setRowFactory(tableView -> {
			TableRow<MusicLibraryTrackRow> row = new TableRow<>();

			row.setOnMouseClicked(event -> {
				if(event.getClickCount() == 2 && (!row.isEmpty())) // Double-click event
				{
					MusicLibraryTrackRow selectedRow = row.getItem();
					Media media = new Media(selectedRow.getTrack().getFilePath().toUri().toString());

					systemPlayer_.stop();
					systemPlayer_ = new MediaPlayer(media);

					currentTrack_ = Optional.of(selectedRow.getTrack());

					refreshCurrentTrackDisplay();

					systemPlayer_.play();
				}
			});

			return row;
		});

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
	 * Refreshes the current track display with updated information
	 *
	 * @version 0.0.0.20170504
	 * @since 0.0
	 */
	private void refreshCurrentTrackDisplay()
	{
		currentTrackDisplayPane_.getChildren().remove(currentTrackTitleDisplay_);
		currentTrackDisplayPane_.getChildren().remove(currentTrackArtistDisplay_);
		currentTrackDisplayPane_.getChildren().remove(currentTrackAlbumDisplay_);

		String title = currentTrack_.get().titleProperty().getValue();
		if(title != null)
		{
			currentTrackTitleDisplay_ = new Label(title);
		}
		else
		{
			currentTrackTitleDisplay_ = new Label("Unknown title");
		}

		String artist = currentTrack_.get().artistProperty().getValue();
		if(artist != null)
		{
			currentTrackArtistDisplay_ = new Label(artist);
		}
		else
		{
			currentTrackArtistDisplay_ = new Label("Unknown artist");
		}

		String album = currentTrack_.get().albumProperty().getValue();
		if(album != null)
		{
			currentTrackAlbumDisplay_ = new Label(album);
		}
		else
		{
			currentTrackAlbumDisplay_ = new Label("Unknown Album");
		}

		currentTrackDisplayPane_.getChildren().addAll(currentTrackTitleDisplay_, currentTrackArtistDisplay_, currentTrackAlbumDisplay_);
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
