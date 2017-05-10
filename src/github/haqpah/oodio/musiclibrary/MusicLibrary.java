package github.haqpah.oodio.musiclibrary;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import github.haqpah.oodio.musiclibrary.track.Track;
import github.haqpah.oodio.services.SystemPathService;

/**
 * The music library. Contains an in-memory collection of {@link Track}.
 * <p>
 * This is <strong>not</strong> a collection of playable media files!
 * <p>
 * This is essentially a list of music library track objects that also contains logic for
 * traversing through the user's music library directory. This object should be used on
 * start-up.
 *
 * @version 0.0.1.20170501
 * @since 0.0
 */
public class MusicLibrary
{
	/**
	 * The logger for this class
	 */
	public static Logger logger_ = LogManager.getLogger(MusicLibrary.class);

	/**
	 * A collection of {@link Track}.
	 * <p>
	 * This is <strong>not</strong> a collection of playable media files! This is a collection of track metadata for
	 * populating the system's main table view. When a row is clicked, this library will search for the metadata in
	 * order to find the file in the user's file system
	 */
	public static List<Track> musicLibrary_;

	/**
	 * The music library loaded in-memory as a collection of {@link Track}.
	 * <p>
	 * This is <strong>not</strong> a collection of playable media files! This is a collection of track metadata for
	 * populating the system's main table view. When a row is clicked, this library will search for the metadata in
	 * order to find the file in the user's file system
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @param musicLibraryDirectory
	 *            the path of the music library in the user's file system
	 * @throws IOException
	 *             if a file path cannot be used or discovered
	 * @throws InterruptedException
	 *             if the {@link Track} load got interrupted
	 * @throws URISyntaxException
	 */
	public MusicLibrary() throws IOException, URISyntaxException
	{
		musicLibrary_ = new ArrayList<Track>();

		discoverOrCreateMusicLibraryDirectory();

		logger_.debug("Traversing music library directory");
		DirectoryStream<Path> musicLibrary = Files.newDirectoryStream(SystemPathService.getMusicLibraryDirectory());
		for(Path artistPath : musicLibrary)
		{
			DirectoryStream<Path> artist = Files.newDirectoryStream(artistPath);
			for(Path albumPath : artist)
			{
				DirectoryStream<Path> album = Files.newDirectoryStream(albumPath);
				for(Path songPath : album)
				{
					Track track = new Track(songPath);
					musicLibrary_.add(track);
				}
			}
		}

		logger_.debug("Music library loaded with " + musicLibrary_.size() + " tracks");
	}

	/**
	 * Gets the in-memory music library
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @return the library
	 */
	public List<Track> getLibrary()
	{
		return musicLibrary_;
	}

	/**
	 * Returns the {@link Track} at the first position in this library.
	 * <p>
	 * Theoretically, this should be the first track whose metadata was discovered
	 * when this library was instantiated.
	 *
	 * @version 0.0.0.20170501
	 * @since 0.0
	 *
	 * @return the first track in the library
	 */
	public Track getFirst()
	{
		return musicLibrary_.get(0);
	}

	/**
	 * Returns true if this library contains no content
	 *
	 * @version 0.0.0.20170501
	 * @since 0.0
	 *
	 * @return {@code boolean} to indicate whether or not this library contains no content
	 */
	public boolean isEmpty()
	{
		if(musicLibrary_ == null || musicLibrary_.isEmpty())
		{
			return true;
		}

		return false;
	}

	/**
	 * Discovers an existing music library directory. If one is not found, creates it.
	 *
	 * @version 0.0.1.20170503
	 * @since 0.0
	 */
	private void discoverOrCreateMusicLibraryDirectory()
	{
		Path library = SystemPathService.getMusicLibraryDirectory();

		// Search for an existing library
		if(Files.exists(library))
		{
			logger_.info("Discovered existing music library at " + library.toString());
		}
		else
		{
			try
			{
				// Create the library
				Files.createDirectory(library);
				logger_.info("Created new music library at " + library.toString());
			}
			catch (IOException e)
			{
				logger_.error("Could not create music library directory at " + library.toString(), e);
			}
		}
	}
}
