package github.haqpah.oodio.application.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import github.haqpah.oodio.musiclibrary.MusicLibrary;
import github.haqpah.oodio.musiclibrary.track.Track;
import github.haqpah.oodio.musiclibrary.track.TrackRow;
import github.haqpah.oodio.services.SystemPathService;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
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
	 * The logger for this class
	 */
	public static Logger logger_ = LogManager.getLogger(SystemController.class);

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
	private Optional<Track> currentTrack_;

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
	private TableView<TrackRow> musicLibraryTable_;

	/**
	 * The {@link TableColumn} the shows the title of a track
	 */
	@FXML
	private TableColumn<TrackRow, String> colTitle_;

	/**
	 * The {@link TableColumn} the shows the artist of a track
	 */
	@FXML
	private TableColumn<TrackRow, String> colArtist_;

	/**
	 * The {@link TableColumn} the shows the album of a track
	 */
	@FXML
	private TableColumn<TrackRow, String> colAlbum_;

	/**
	 * The {@link TableColumn} the shows the genre of a track
	 */
	@FXML
	private TableColumn<TrackRow, String> colGenre_;

	/**
	 * The {@link TableColumn} the shows the year of a track
	 */
	@FXML
	private TableColumn<TrackRow, String> colYear_;

	/**
	 * Constructor
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @param primaryStage
	 *            The stage this controller's parent pane is attached to
	 * @param logger_
	 *            Responsible for logging information to the console and log file
	 * @param musicLibrary
	 *            The {@link MusicLibrary} containing metadata on the files in the user's system
	 */
	public SystemController(MusicLibrary musicLibrary)
	{
		super(FXML_FILENAME_);

		musicLibrary_ = musicLibrary;

		setupCellValueFactories();
		setupRowEventHandlers();

		populateMusicLibraryTable();

		currentTrack_ = loadDefaultTrack();
		refreshCurrentTrackDisplay();

		setupVolumeSlider();
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
		File file = fileChooser.showOpenDialog(getRootPane().getScene().getWindow());

		if(file != null)
		{
			try
			{
				Path path = file.toPath();
				Track track = new Track(path);

				// Add it to the in-memory library
				if(!musicLibrary_.getLibrary().contains(track))
				{
					musicLibrary_.getLibrary().add(track);
				}

				// Copy the file to the music library directory
				Path source = file.toPath();
				Path destination = SystemPathService.getMusicLibraryDirectory()
						.resolve(track.artistProperty().getValue())
						.resolve(track.albumProperty().getValue())
						.resolve(source.getFileName());

				Files.copy(source, destination);

				// Add it to the table view once its been added to more important systems
				TrackRow row = new TrackRow(track);
				musicLibraryTable_.getItems().add(row);
				musicLibraryTable_.refresh();
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
		logger_.info("Exiting application via system menu");

		LogManager.shutdown();
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
				logger_.error("Failed to load github link to the README", e);
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
				logger_.error("Failed to load github link to the Wiki", e);
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
	 * Creates an {@link ObservableList} of populated rows that track metadata can be displayed on the {@link #musicLibraryTable_}.
	 *
	 * @version 0.0.0.20170503
	 * @since 0.0
	 *
	 * @return the list of rows that was created
	 */
	private ObservableList<TrackRow> createMusicLibraryTrackRows()
	{
		List<TrackRow> list = musicLibrary_.getLibrary()
				.stream()
				.map(track -> new TrackRow(track))
				.collect(Collectors.toList());

		// Make the list observable and add a listener for changes
		ObservableList<TrackRow> obsList = FXCollections.observableArrayList(list);
		obsList.addListener(new ListChangeListener<TrackRow>()
		{
			/**
			 * @version 0.0.0.20170507
			 * @since 0.0
			 */
			@Override
			public void onChanged(Change<? extends TrackRow> change)
			{
				// TODO This change event doesn't seem to get hit ever
				while (change.next())
				{
					logger_.info("Refreshing table after MusicLibraryTrackRow change event");
					musicLibraryTable_.refresh();
				}
			}
		});

		logger_.info("Created " + obsList.size() + " rows for library table view");

		return obsList;
	}

	/**
	 * Loads the hello world media into the system player. This likely only plays the
	 * first time the user ever launches the application. Any song in the music library
	 * will take precedence.
	 *
	 * @version 0.0.0.20170503
	 * @since 0.0
	 */
	private Optional<Track> loadDefaultTrack()
	{
		String defaultMedia;
		Track defaultSong;
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

		logger_.info("Loaded default track: " + defaultMedia);

		return Optional.ofNullable(defaultSong);
	}

	/**
	 * Sets up the cell values in each column with observable
	 * string properties in the {@link TrackRow}
	 *
	 * @version 0.0.0.20170503
	 * @since 0.0
	 */
	private void setupCellValueFactories()
	{
		logger_.info("Setting up cell value factories");
		colTitle_.setCellValueFactory(
				new Callback<CellDataFeatures<TrackRow, String>, ObservableValue<String>>()
				{
					/**
					 * @version 0.0.0.20170503
					 * @since 0.0
					 */
					@Override
					public ObservableValue<String> call(CellDataFeatures<TrackRow, String> row)
					{
						return row.getValue().titleProperty();
					}
				});

		colArtist_.setCellValueFactory(
				new Callback<CellDataFeatures<TrackRow, String>, ObservableValue<String>>()
				{
					/**
					 * @version 0.0.0.20170503
					 * @since 0.0
					 */
					@Override
					public ObservableValue<String> call(CellDataFeatures<TrackRow, String> row)
					{
						return row.getValue().artistProperty();
					}
				});

		colAlbum_.setCellValueFactory(
				new Callback<CellDataFeatures<TrackRow, String>, ObservableValue<String>>()
				{
					/**
					 * @version 0.0.0.20170503
					 * @since 0.0
					 */
					@Override
					public ObservableValue<String> call(CellDataFeatures<TrackRow, String> row)
					{
						return row.getValue().albumProperty();
					}
				});

		colGenre_.setCellValueFactory(
				new Callback<CellDataFeatures<TrackRow, String>, ObservableValue<String>>()
				{
					/**
					 * @version 0.0.0.20170503
					 * @since 0.0
					 */
					@Override
					public ObservableValue<String> call(CellDataFeatures<TrackRow, String> row)
					{
						return row.getValue().genreProperty();
					}
				});

		colYear_.setCellValueFactory(
				new Callback<CellDataFeatures<TrackRow, String>, ObservableValue<String>>()
				{
					/**
					 * @version 0.0.0.20170503
					 * @since 0.0
					 */
					@Override
					public ObservableValue<String> call(CellDataFeatures<TrackRow, String> row)
					{
						return row.getValue().yearProperty();
					}
				});
	}

	/**
	 * Sets up the event handlers on rows in the music library
	 * table. This includes click and keypress events
	 *
	 * @see {@link OodioKeyEventHandler}
	 *
	 * @version 0.0.1.20170510
	 * @since 0.0
	 */
	private void setupRowEventHandlers()
	{
		musicLibraryTable_.setRowFactory(tableView -> {
			TableRow<TrackRow> tableRow = new TableRow<>();
			tableRow.setOnMouseClicked(event -> {
				if(event.getClickCount() == 2 && (!tableRow.isEmpty()))
				{
					playNewTrack(tableRow.getItem().getTrack());
				}
			});

			return tableRow;
		});

		musicLibraryTable_.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			/**
			 * @version 0.0.0.20170509
			 * @since 0.0
			 */
			@Override
			public void handle(KeyEvent keyEvent)
			{
				TrackRow row = musicLibraryTable_.getSelectionModel().getSelectedItem();
				if(keyEvent.getCode().equals(KeyCode.ENTER))
				{
					// Play a new track
					playNewTrack(row.getTrack());
				}
				else if(keyEvent.getCode().equals(KeyCode.SPACE))
				{
					if(systemPlayer_.getMedia() != null)
					{
						// Toggle the paused state of the player
						if(systemPlayer_.statusProperty().getValue().equals(MediaPlayer.Status.PLAYING))
						{
							systemPlayer_.pause();
						}
						else
						{
							systemPlayer_.play();
						}
					}
					else
					{
						// Play the highlighted track, no track is loaded and the hello world is not loaded
						playNewTrack(row.getTrack());
					}
				}
			}
		});
	}

	/**
	 * Sets up the volume slider with an initial value, as well as the event
	 * listener to handle the user changing the volume during runtime
	 *
	 * @version 0.0.0.20170509
	 * @since 0.0
	 */
	private void setupVolumeSlider()
	{
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

	/**
	 * Populates the library on startup.
	 *
	 * @see {@link #addTrackToLibrary(ActionEvent)
	 *
	 * @version 0.0.0.20170509
	 * @since 0.0
	 */
	private void populateMusicLibraryTable()
	{
		// Get all the rows to add to the table
		ObservableList<TrackRow> observableRowList = createMusicLibraryTrackRows();

		musicLibraryTable_.getItems().addAll(observableRowList);
		musicLibraryTable_.refresh();
	}

	/**
	 * Plays the track. Stops any already playing track first
	 *
	 * @version 1.0.0.20170510
	 * @since 0.0
	 *
	 * @param track
	 *            the track to play
	 */
	private void playNewTrack(Track track)
	{
		Media media = new Media(track.getFilePath().toUri().toString());

		systemPlayer_.stop();
		systemPlayer_ = new MediaPlayer(media);

		currentTrack_ = Optional.of(track);

		refreshCurrentTrackDisplay();

		systemPlayer_.play();
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
}
