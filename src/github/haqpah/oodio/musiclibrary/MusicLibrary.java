package github.haqpah.oodio.musiclibrary;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import github.haqpah.oodio.services.SystemPathService;
import javafx.scene.media.Media;

/**
 * The music library. Contains an in-memory collection of {@link MusicLibraryTrack}.
 * <p>
 * This is <strong>not</strong> a collection of playable media files!
 * <p>
 * This is essentially a list of music library track objects that also contains logic for
 * traversing through the user's music library directory. This object should be used on
 * start-up.
 *
 * TODO Look into leveraging this object for playlists
 *
 * @version 0.0.1.20170501
 * @since 0.0
 */
public class MusicLibrary
{
	/**
	 * A collection of {@link MusicLibraryTrack}.
	 * <p>
	 * This is <strong>not</strong> a collection of playable media files! This is a collection of track metadata for
	 * populating the system's main table view. When a row is clicked, this library will search for the metadata in
	 * order to find the file in the user's file system
	 */
	public static List<MusicLibraryTrack> musicLibrary_;

	/**
	 * Holds the string representation of the key that {@link Media#getMetadata()} returns/expects
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 */
	public enum MediaMetadataKey
	{
		ALBUM_ARTIST("album artist"),
		TRACK_NUMBER("track number"),
		IMAGE("image"),
		ARTIST("artist"),
		YEAR("year"),
		ALBUM("album"),
		COMPOSER("composer"),
		TITLE("title"),
		DISC_NUMBER("disc number"),
		GENRE("genre"),
		;

		/**
		 * The string representation of the key
		 */
		private String key_;

		/**
		 * Holds the string representation of the key that {@link Media#getMetadata()} returns/expects
		 *
		 * @version 0.0.0.20170429
		 * @since 0.0
		 *
		 * @param key
		 *            the key of the map returned from {@link Media#getMetadata()}
		 */
		MediaMetadataKey(String key)
		{
			key_ = key;
		}

		/**
		 * Return the metadata key
		 *
		 * @version 0.0.0.20170429
		 * @since 0.0
		 *
		 * @return
		 */
		public String getKey()
		{
			return key_;
		}
	}

	/**
	 * The music library loaded in-memory as a collection of {@link MusicLibraryTrack}.
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
	 *             if the {@link MusicLibraryTrack} load got interrupted
	 * @throws URISyntaxException
	 */
	public MusicLibrary(Path musicLibraryDirectory, Logger systemLogger) throws IOException, InterruptedException, URISyntaxException
	{
		musicLibrary_ = new ArrayList<MusicLibraryTrack>();

		discoverOrCreateMusicLibraryDirectory(systemLogger);

		DirectoryStream<Path> musicLibrary = Files.newDirectoryStream(musicLibraryDirectory);
		for(Path artistPath : musicLibrary)
		{
			DirectoryStream<Path> artist = Files.newDirectoryStream(artistPath);
			for(Path albumPath : artist)
			{
				DirectoryStream<Path> album = Files.newDirectoryStream(albumPath);
				for(Path songPath : album)
				{
					MusicLibraryTrack track = new MusicLibraryTrack(songPath, systemLogger);
					musicLibrary_.add(track);
				}
			}
		}
	}

	/**
	 * Gets the in-memory music library
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @return the library
	 */
	public List<MusicLibraryTrack> getLibrary()
	{
		return musicLibrary_;
	}

	/**
	 * Returns the {@link MusicLibraryTrack} at the first position in this library.
	 * <p>
	 * Theoretically, this should be the first track whose metadata was discovered
	 * when this library was instantiated.
	 *
	 * @version 0.0.0.20170501
	 * @since 0.0
	 *
	 * @return the first track in the library
	 */
	public MusicLibraryTrack getFirst()
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
	 * @version 0.0.0.20170501
	 * @since 0.0
	 */
	private void discoverOrCreateMusicLibraryDirectory(Logger systemLogger)
	{
		Path library = SystemPathService.getMusicLibraryDirectory();

		// Search for an existing library
		if(Files.exists(library))
		{
			systemLogger.info("Discovered existing music library at " + library.toString());
		}
		else
		{
			try
			{
				// Create the library
				Files.createDirectory(library);
			}
			catch (IOException e)
			{
				systemLogger.error("Could not create music library directory at " + library.toString(), e);
			}
		}
	}
}
