package github.haqpah.oodio.application.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.apache.log4j.Logger;

import github.haqpah.oodio.musiclibrary.ArtistMetadata;
import github.haqpah.oodio.musiclibrary.MusicLibraryMetadata;
import github.haqpah.oodio.services.ArtistMetadataLoaderService;
import github.haqpah.oodio.services.SystemPathService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller for the system menu UI elements defined by SystemMenu.fxml
 *
 * @version 0.0.0.20170427
 * @since 0.0
 */
public final class SystemMenuController extends AbstractController
{
	/**
	 * The FXML file name for this controller
	 */
	private static final String FXML_FILENAME_ = "SystemMenu.fxml";

	/**
	 * The in memory music library containing metadata
	 */
	private final MusicLibraryMetadata musicLibraryMetadata_;

	/**
	 * Controller for the system menu UI elements defined by SystemMenu.fxml
	 *
	 * @version 0.0.0.20170426
	 * @since 0.0
	 *
	 * @param musicLibrary
	 *            an in memory list of {@link Media}
	 */
	public SystemMenuController(final Stage primaryStage, final Logger systemLogger, final MusicLibraryMetadata musicLibraryMetadata)
	{
		super(primaryStage, systemLogger, FXML_FILENAME_);
		musicLibraryMetadata_ = musicLibraryMetadata;
	}

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

				// Discover or store the artist's metadata in the in memory music library metadata object
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
	 * Checks to see if the artist's metadata is stored in memory. If not, adds it to the storage
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
			ArtistMetadataLoaderService artistLoader = new ArtistMetadataLoaderService(artistDirectory);

			try
			{
				ArtistMetadata artistMetadata = artistLoader.call();
				musicLibraryMetadata_.getLibraryMetadata().add(artistMetadata);
			}
			catch (Exception e)
			{
				getSystemLogger().error("Could not store new artist metadata in memory");
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
	 * @version 0.0.0.20170426
	 * @since 0.0
	 */
	@Override
	public String getFxmlFilename()
	{
		return FXML_FILENAME_;
	}
}
