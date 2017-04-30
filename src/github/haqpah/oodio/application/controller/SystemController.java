package github.haqpah.oodio.application.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import github.haqpah.oodio.musiclibrary.AlbumMetadata;
import github.haqpah.oodio.musiclibrary.ArtistMetadata;
import github.haqpah.oodio.musiclibrary.MusicLibraryMetadata;
import github.haqpah.oodio.musiclibrary.MusicLibrarySong;
import github.haqpah.oodio.musiclibrary.MusicLibrarySongRow;
import github.haqpah.oodio.services.ArtistMetadataLoaderService;
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
	 * The in-memory music library containing metadata
	 */
	private final MusicLibraryMetadata musicLibraryMetadata_;

	public SystemController(Stage primaryStage, Logger systemLogger, MusicLibraryMetadata musicLibraryMetadata)
	{
		super(primaryStage, systemLogger, FXML_FILENAME_);

		musicLibraryMetadata_ = musicLibraryMetadata;

		// TODO loading default song
		File file = new File("C:/Users/schel/Music/Instrumentals/Jay IDK - Two Hoes.mp3");
		Media media = new Media(file.toURI().toString());
		systemPlayer_ = new MediaPlayer(media);

		// Add rows to the table
		AnchorPane rootNode = (AnchorPane) getRootNode();
		List<Node> children = rootNode.getChildren();
		for(Node child : children)
		{
			if(child instanceof TableView)
			{
				@SuppressWarnings("unchecked")
				TableView<MusicLibrarySongRow> tableView = (TableView<MusicLibrarySongRow>) child;

				// Dig down into the in-memory metadata list to get the song metadata for each song to populate the table
				for(ArtistMetadata artist : musicLibraryMetadata.getLibraryMetadata())
				{
					for(AlbumMetadata album : artist.getAlbumMetadata())
					{
						for(Map<String, Object> songMetadata : album.getSongMetadata())
						{
							MusicLibrarySong song = new MusicLibrarySong(songMetadata);
							systemLogger.debug("Creating new row from song: " + song.toString());

							MusicLibrarySongRow row = new MusicLibrarySongRow(song);
							tableView.getItems().add(row);
						}
					}
				}
			}
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

	@FXML
	public void addTrackToLibrary(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(getPrimaryStage());

		if(file != null)
		{
			try
			{
				// Gets the songs metadata
				Media media = new Media(file.toURI().toString());
				Map<String, Object> metadata = media.getMetadata();

				String artistName = (String) metadata.get("artist name");
				String albumName = (String) metadata.get("album");

				// Discover the artist and album directory, create it if not found
				discoverOrCreateDirectory(artistName);
				discoverOrCreateDirectory(artistName, albumName);

				// Discover or store the artist's metadata in the in-memory music library metadata object
				discoverOrStoreArtistMetadata(metadata);

				// Place the song in the Music Library folder, unless its already in there
				// TODO does not work at the moment -> Files.copy(Paths.get(file.toURI()), SystemPathService.getMusicLibraryDirectory());
			}
			catch (IOException e)
			{
				// TODO inform user

				// Get the file's extension
				int beginIndex = file.getPath().lastIndexOf(".");
				String extension = file.getPath().substring(beginIndex, file.getPath().length());

				StringBuilder logMessage = new StringBuilder();
				logMessage.append("Could not add file to library" + System.lineSeparator())
						.append("  Path: " + file.getPath() + System.lineSeparator())
						.append("  Ext:  " + extension);

				addLog(logMessage.toString(), e);
			}
		}
	}

	/**
	 * Looks in the music library folder for the folder. If not found, creates it
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param folderName
	 *            the name of the folder to look for, likely an artist's name
	 *
	 * @return the passed directory as a {@link Path}
	 * @throws IOException
	 *             if the directory could not be created
	 */
	private Path discoverOrCreateDirectory(String folderName) throws IOException
	{
		// Create the artist directory if it does not exist
		Path artistDirectory = SystemPathService.getMusicLibraryDirectory().resolve(folderName);
		if(!Files.exists(artistDirectory))
		{
			// Create the artist directory in the system's music library folder
			Files.createDirectory(artistDirectory);
		}

		return artistDirectory;
	}

	/**
	 * Looks in the music library folder for the passed child folder underneath
	 * the passed parent folder. If not found, creates it.
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param parent
	 *            the name of the parent folder to resolve to, likely an artist's name
	 * @param child
	 *            the name of the child folder to resolve to, likely an album name
	 *
	 * @return the passed child folder name as a {@link Path}
	 * @throws IOException
	 *             if the directory could not be created
	 */
	private Path discoverOrCreateDirectory(String parent, String child) throws IOException
	{
		Path childDirectory = SystemPathService.getMusicLibraryDirectory().resolve(parent).resolve(child);

		return discoverOrCreateDirectory(childDirectory.toString());
	}

	/**
	 * Checks to see if the artist's metadata is stored in-memory. If not, adds it to the storage
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param metadata
	 *            the metadata to discover or store
	 */
	private void discoverOrStoreArtistMetadata(Map<String, Object> metadata)
	{
		String artistName = (String) metadata.get("artist name");

		boolean found = false;
		for(ArtistMetadata artistMetadata : musicLibraryMetadata_.getLibraryMetadata())
		{
			if(artistMetadata.getArtistName().equals(artistName))
			{
				found = true;
			}
		}

		if(!found)
		{
			// XXX: At this point of execution, the artist path is guaranteed to exist. Checks have already occurred
			Path artistDirectory = SystemPathService.getMusicLibraryDirectory().resolve(artistName);
			ArtistMetadataLoaderService artistLoader = new ArtistMetadataLoaderService(artistDirectory, getSystemLogger());

			try
			{
				ArtistMetadata artistMetadata = artistLoader.traverseArtistDirectory();
				musicLibraryMetadata_.getLibraryMetadata().add(artistMetadata);
			}
			catch (Exception e)
			{
				getSystemLogger().error("Could not store new artist metadata in-memory");
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
		// TODO hook into application stop somehow?
		getSystemLogger().info("Exiting application via system menu");
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
