package github.haqpah.oodio.musiclibrary;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import javafx.scene.media.Media;

/**
 * The music library loaded in-memory as a collection of {@link MusicLibrarySong}.
 * <p>
 * This is <strong>not</strong> a collection of playable media files! This is a collection of song metadata for
 * populating the system's main table view. When a row is clicked, this library will search for the metadata in
 * order to find the file in the user's file system
 *
 * @version 0.0.0.20170430
 * @since 0.0
 */
public class MusicLibrary
{
	/**
	 * The music library loaded in-memory as a collection of {@link MusicLibrarySong}.
	 * <p>
	 * This is <strong>not</strong> a collection of playable media files! This is a collection of song metadata for
	 * populating the system's main table view. When a row is clicked, this library will search for the metadata in
	 * order to find the file in the user's file system
	 */
	public static List<MusicLibrarySong> musicLibrary_;

	/**
	 * Holds the string representation of the key that {@link Media#getMetadata()} returns/expects
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 */
	public enum JavaFxMediaMetadataKey
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
		JavaFxMediaMetadataKey(String key)
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
	 * The music library loaded in-memory as a collection of {@link MusicLibrarySong}.
	 * <p>
	 * This is <strong>not</strong> a collection of playable media files! This is a collection of song metadata for
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
	 *             if the {@link MusicLibrarySong} load got interrupted
	 * @throws URISyntaxException
	 */
	public MusicLibrary(Path musicLibraryDirectory, Logger systemLogger) throws IOException, InterruptedException, URISyntaxException
	{
		musicLibrary_ = new ArrayList<MusicLibrarySong>();

		DirectoryStream<Path> musicLibrary = Files.newDirectoryStream(musicLibraryDirectory);
		for(Path artistPath : musicLibrary)
		{
			DirectoryStream<Path> artist = Files.newDirectoryStream(artistPath);
			for(Path albumPath : artist)
			{
				DirectoryStream<Path> album = Files.newDirectoryStream(albumPath);
				for(Path songPath : album)
				{
					MusicLibrarySong song = new MusicLibrarySong(songPath, systemLogger);
					musicLibrary_.add(song);
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
	public List<MusicLibrarySong> getLibrary()
	{
		return musicLibrary_;
	}
}
